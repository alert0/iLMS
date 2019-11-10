package com.hotent.form.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.dao.BpmFormFieldDao;
import com.hotent.form.persistence.dao.BpmFormRightDao;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.manager.BpmFormRightManager;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.BpmFormField;
import com.hotent.form.persistence.model.BpmFormRight;
import com.hotent.form.persistence.model.BpmFormRightXml;
import com.hotent.sys.api.permission.PermissionCalc;

/**
 * 
 * <pre> 
 * 描述：BPM_FORM_RIGHT 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-19 14:22:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmFormRightManager")
public class BpmFormRightManagerImpl extends AbstractManagerImpl<String, BpmFormRight> implements BpmFormRightManager{
	@Resource
	BpmFormRightDao bpmFormRightDao;
	@Resource
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BpmFormManager bpmFormManager;
	@Resource
	BpmFormFieldDao bpmFormFieldDao;
	@Resource(name="formPermissionCalc")
	PermissionCalc permssionCalc;
	
	 
	@Override
	protected Dao<String, BpmFormRight> getDao() {
		return bpmFormRightDao;
	}
	
	
	
	/**
	 * 根据表单key获取默认的权限设置json。
	 * @param formKey	这个字段对应BPM_FROM_DEF的key字段。
	 * @param isInstance	是否实例表单
	 * @return
	 */
	@Override
	public JSONObject getDefaultByFormDefKey(String formKey,boolean isInstance) {
		BpmFormDef formDef = bpmFormDefDao.getByKey(formKey);
		String formDefId=formDef.getId();
		//实体map列表。
		List<Map<String,String>>  lowList=convertMapLower(formDef.getExpand());
		
		//只获取字段名称描述和实体名称。
		//A.name_,B.NAME_ ENT_NAME,A.desc_, A.sn_
		List<BpmFormField> fieldList= bpmFormFieldDao.getExtByFormId(formDefId);
		//字段按照表进行分组。
		Map<String,List<BpmFormField>> fieldMap=convertFormGroup(fieldList);
		
		JSONObject jsonObj=new JSONObject();
		
		//构建表的JSON
		JSONObject tableJson=new JSONObject();
		for(Map<String,String>  entMap:lowList ){
			JSONObject json= buildTable(entMap,fieldMap,isInstance);
			if(json==null) continue;
			String entName=entMap.get("name_");
			tableJson.put(entName, json);
		}
		jsonObj.put("table", tableJson);
		
		//构建意见的JSON。
		String opinionJson=formDef.getOpinionConf();
		JSONObject opinionJsonObj=buildOpinion(opinionJson,isInstance);
		if(opinionJsonObj!=null){
			jsonObj.put("opinion", opinionJsonObj);
		}
		
		return jsonObj;
	}
	
	/**
	 * {"bumenjingli": {
		            "description": "部门经理",
		            "read": [
		                {
		                    "type": "everyone"
		                }
		            ],
		            "write": [
		                {
		                    "type": "none"
		                }
		            ],
		            "required": [
		                {
		                    "type": "none"
		                }
		            ]
		        },
		        "caiwu": {
		            "description": "财务意见",
		            "read": [
		                {
		                    "type": "everyone"
		                }
		            ],
		            "write": [
		                {
		                    "type": "none"
		                }
		            ],
		            "required": [
		                {
		                    "type": "none"
		                }
		            ]
		        }
		    }
	 * @param json
	 * @return
	 */
	private JSONObject buildOpinion(String json,boolean isInstance){
		if(StringUtil.isEmpty(json)) return null;
		
		JSONObject rtnJson=new JSONObject();
		
		JSONArray ary=JSONArray.parseArray(json);
		for(Object obj:ary){
			JSONObject jsonObj=(JSONObject)obj;
			String name=jsonObj.getString("name");
			String desc=jsonObj.getString("desc");
			
			JSONObject permissionJson=getPermissionJson(desc);
			
			if(isInstance){
				permissionJson=getInstPermissionJson(desc);
			}
			else{
				permissionJson=getPermissionJson(desc);
			}
			
			rtnJson.put(name, permissionJson);
			
		}
		
		return rtnJson;
	}
	
	/**
	 * 构建JSON格式如下:
	 * {
            "description": "个人简历",
            "fields":{
		            "name": {
		                "description": "姓名",
		                "read": [
		                    {
		                        "type": "everyone"
		                    }
		                ],
		                "write": [
		                    {
		                        "type": "none"
		                    }
		                ],
		                "required": [
		                    {
		                        "type": "none"
		                    }
		                ]
		            },
		            "age": {
		                "description": "年龄",
		                "read": [
		                    {
		                        "type": "everyone"
		                    }
		                ],
		                "write": [
		                    {
		                        "type": "none"
		                    }
		                ],
		                "required": [
		                    {
		                        "type": "none"
		                    }
		                ]
		            }
		    }
            ,
            "main": true
        }
	 * @param entMap
	 * @param fieldMap
	 * @param defaultPermission
	 * @return
	 */
	private JSONObject buildTable(Map<String,String>  entMap,Map<String,List<BpmFormField>> fieldMap,boolean isInstance){
		
		String entName=entMap.get("name_");
		String entDesc=entMap.get("desc_");
		String type=entMap.get("type_");
		
		List<BpmFormField> list=fieldMap.get(entName);
		if(BeanUtils.isEmpty(list)) return null;
		
		JSONObject jsonObj=new JSONObject();
		
		jsonObj.put("description", entDesc);
		//主表类型。
		if(BaseBoDef.BOENT_RELATION.MAIN.equalsIgnoreCase(type)){
			jsonObj.put("main", true);
		}
		/*
		 * "main": false,
        "rights": {
            "hidden": false,
            "add": true,
            "del": true,
            "required": true
        }*/
		else{
			JSONObject rightJson=new JSONObject();
			rightJson.put("hidden", false);
			rightJson.put("add", !isInstance);
			rightJson.put("del", !isInstance);
			rightJson.put("required", false);
			
			jsonObj.put("main", false);
			jsonObj.put("rights", rightJson);
		}
		
		//构建字段。
		JSONObject fieldsJson=new JSONObject();
		
		for(BpmFormField field:list){
			JSONObject permissonJson=null;
			if(!isInstance){
				permissonJson=getPermissionJson(field.getDesc(),field.getSn());
			}
			else{
				permissonJson=getInstPermissionJson(field.getDesc(),field.getSn());
			}
			
			fieldsJson.put(field.getName(), permissonJson);
		} 
		
		jsonObj.put("fields", fieldsJson);
		
		return jsonObj;
	}
	
	/**
	 * 获取默认的权限。
	 * @param desc
	 * @return
	 */
	private JSONObject getPermissionJson(String desc){
		String json="{\"description\": \""+desc+"\",\"read\": [{\"type\": \"everyone\"}],"
				+ "\"write\": [{\"type\": \"everyone\"}],\"required\": [{\"type\": \"none\"}]}";
		
		return JSONObject.parseObject(json);
	}
	
	private JSONObject getPermissionJson(String desc,Integer sn){
		JSONObject json = getPermissionJson(desc);
		json.put("sn", sn);
		return json;
	}
	
	/**
	 * 获取只读权限。
	 * @param desc
	 * @return
	 */
	private JSONObject getInstPermissionJson(String desc){
		String json="{\"description\": \""+desc+"\",\"read\": [{\"type\": \"everyone\"}]}";
		
		return JSONObject.parseObject(json);
	}
	
	private JSONObject getInstPermissionJson(String desc, Integer sn){
		JSONObject json = getInstPermissionJson(desc);
		json.put("sn", sn);
		return json;
	}
	
	
	/**
	 * 将表单字段按照表进行分组。
	 * @param fieldList
	 * @return
	 */
	private Map<String,List<BpmFormField>> convertFormGroup(List<BpmFormField> fieldList){
		Map<String,List<BpmFormField>> map=new HashMap<String, List<BpmFormField>>();
		
		for(BpmFormField field:fieldList){
			String entName=field.getEntName();
			if(map.containsKey(entName)){
				List<BpmFormField> list=map.get(entName);
				list.add(field);
			}
			else{
				List<BpmFormField> list=new ArrayList<BpmFormField>();
				list.add(field);
				map.put(entName, list);
			}
		}
		return map;
	}
	
	/**
	 * map 转换 ，将map的键值转换成小写。
	 * @param entList
	 * @return
	 */
	private List<Map<String,String>> convertMapLower(String expand) {
		 List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		JSONObject jsonObject = JSONObject.parseObject(expand,Feature.OrderedField);
		JSONArray jsonArray = jsonObject.getJSONArray("fields");
		for (Object object : jsonArray) {
			JSONObject obj = (JSONObject) object;
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("name_", obj.getString("name"));
			map.put("desc_", obj.getString("desc"));
			map.put("type_", obj.getString("type"));
			map.put("sn_", obj.getString("sn"));
			list.add(map);
		}
		return list;
	}

	/**
	 * 根据流程获取表单权限。
	 * @param flowKey
	 * @param parentFlowKey
	 * @return
	 */
	private JSONObject getByFlowKey(String flowKey, String parentFlowKey) {
		
		BpmFormRight right= bpmFormRightDao.getByFlowKey(flowKey, parentFlowKey,1);
		if(right!=null){
			JSONObject rtnJson=JSONObject.parseObject(right.getPermission());
			return rtnJson;
		}
		return null;
	}

	/**
	 * 根据节点获取表单权限。
	 * @param flowKey
	 * @param nodeId
	 * @param parentFlowKey
	 * @return
	 */
	private JSONObject getByFlowNodeId(String flowKey, String nodeId, String parentFlowKey) {
		BpmFormRight right= bpmFormRightDao.getByFlowNodeId(flowKey, nodeId, parentFlowKey,1);
		if(right!=null){
			JSONObject rtnJson=JSONObject.parseObject(right.getPermission());
			return rtnJson;
		}
		return null;
	}
	
	@Override
	public JSONObject getByFormKey(String formKey, boolean isReadOnly) {
		BpmFormRight right= bpmFormRightDao.getByFormKey(formKey,isReadOnly);
		JSONObject rtnJson = null;
		if(right!=null){
			rtnJson=JSONObject.parseObject(right.getPermission());
			return rtnJson;
		}else{
			String formMetaKey=bpmFormDefDao.getMetaKeyByFormKey(formKey);
			rtnJson = getDefaultByFormDefKey(formMetaKey,isReadOnly);
			return rtnJson;
		}
	}
	

	/**
	 * 根据流程定义获取实例权限配置。
	 * @param flowKey
	 * @return
	 */
	private JSONObject getByInst(String flowKey) {
		BpmFormRight right= bpmFormRightDao.getByFlowKey(flowKey, "",2);
		if(right!=null){
			JSONObject rtnJson=JSONObject.parseObject(right.getPermission());
			return rtnJson;
		}
		return null;
	}


	@Override
	public void removeInst(String flowKey) {
		bpmFormRightDao.removeByFlowKey(flowKey, "", 2);
	}
	
	/*
	 * 如果流程表单，节点已经授权了表单， 但已经更换了表单， 则删除原来的
	 * 
	 */
	@Override
	public void remove(String formKey, String flowKey, String nodeId,
			String parentFlowKey) {
		BpmFormRight right = null;
		if(StringUtil.isNotEmpty(flowKey)){
			if(StringUtil.isNotEmpty(nodeId)){
				right = bpmFormRightDao.getByFlowNodeId(flowKey, nodeId, parentFlowKey,1);
			}else{
				right= bpmFormRightDao.getByFlowKey(flowKey, parentFlowKey,1);
			}
		}
		if( right!=null && !formKey.equals(right.getFormKey())){
			remove(flowKey,nodeId,parentFlowKey);
		}
	}

	@Override
	public void remove(String flowKey, String nodeId, String parentFlowKey) {
		bpmFormRightDao.removeByFlowNode(flowKey, nodeId, parentFlowKey);
	}


	@Override
	public void remove(String flowKey, String parentFlowKey) {
		bpmFormRightDao.removeByFlowKey(flowKey,parentFlowKey, 1);
	}


	@Override
	public void save(String formKey, String flowKey, String parentFlowKey, String nodeId, String permission, int type) {
		
		//清除之前的流程设置。
		if(StringUtil.isNotEmpty(flowKey)){//表单已绑定流程
			if(type==1){
				remove(flowKey, nodeId, parentFlowKey);
			}
			else{
				removeInst(flowKey);
			}
		}else{//表单还没绑定流程
			removeByFormKey(formKey);
		}
		
		//添加表单权限
		String id=UniqueIdUtil.getSuid();
		BpmFormRight right=new BpmFormRight();
		right.setId(id);
		
		right.setFormKey(formKey);
		right.setFlowKey(flowKey);
		right.setNodeId(nodeId);
		right.setParentFlowKey(parentFlowKey);
		right.setPermission(permission);
		right.setPermissionType(type);
		bpmFormRightDao.create(right);
	}



	/**
	 * formKey bpm_form的key字段。
	 */
	@Override
	public JSONObject getPermissionSetting(String formKey, String flowKey, String parentFlowKey, String nodeId,
			int type) {
		
		JSONObject json=null;
		//流程权限
		if(StringUtil.isNotEmpty(flowKey)){
			if(type==1){
				if(StringUtil.isEmpty(nodeId)){
					json=getByFlowKey(flowKey, parentFlowKey);
				}
				else{
					json=getByFlowNodeId(flowKey, nodeId, parentFlowKey) ;
					//获取下全局的权限配置。
					if(json==null){
						json=getByFlowKey(flowKey, parentFlowKey);
					}
				}
			}
			//实例
			else{
				json=getByInst(flowKey);
			}
		}
		
		
		//如果未配置节点全局权限，获取表单配置权限,或者表单基础权限
		boolean isReadOnly=type!=1;
		if(json == null){
			json=getByFormKey(formKey,isReadOnly);
		}
		
		return json;
	}

	

	/**
	 * 计算权限。
	 * formKey : bpm_form的key。
	 * <pre>
	 * {
		    "fields": {
		        "table1": {
		            "name": "w",
		            "age": "b"
		        },
		        "table2": {
		            "name": "w",
		            "age": "r"
		        },
		        "table3": {
		            "name": "w",
		            "age": "w"
		        }
		    },
		    "table":{
		     "table1":{"hidden":true}
		     "table2":{"hidden":false,"add":"true","del":"true","required":"true"}
		    },
		    "opinion":{"jzyj":"w","zxyj":"r","zxyj":"b"}
		</pre>
	 */
	@Override
	public JSONObject getPermission(String formKey, String flowKey, String parentFlowKey, String nodeId, int type) {
		
		JSONObject json= getPermissionSetting(formKey,flowKey,parentFlowKey,nodeId,type);
		
		return calcFormPermission(json);
		
	}
	/**
	 *  通过获取的permissionJson 获取表单权限
	 * @param permissionConf
	 * @return
	 */
	@Override
	public JSONObject calcFormPermission(JSONObject permissionConf){
		Map<String,Set<String>> profilesMap= permssionCalc.getCurrentProfiles();
		
		JSONObject rtnJson=new JSONObject();
		//获取表单权限设定。
		//获取表
		JSONObject tableJsons=permissionConf.getJSONObject("table");
		
		//1.构建字段权限JSON，构建子表权限。
		JSONObject rtnTableFieldJson=new JSONObject();
		JSONObject rtnTableJson=new JSONObject();
		
		for(Iterator<String> tableIt= tableJsons.keySet().iterator();tableIt.hasNext();){
			String tableName=tableIt.next();
			JSONObject tableJson= tableJsons.getJSONObject(tableName);
			JSONObject tableFieldJson= buildTablePermission(tableJson,profilesMap);
			rtnTableFieldJson.put(tableName, tableFieldJson);
			boolean isMain= tableJson.getBoolean("main");
			if(!isMain){
				JSONObject tableRights=tableJson.getJSONObject("rights") ;
				rtnTableJson.put(tableName, tableRights);
			}
		} 
		//字段权限。
		rtnJson.put("fields", rtnTableFieldJson);
		//2.构建子表权限JSON。
		if(rtnTableJson.size()>0){
			rtnJson.put("table", rtnTableJson);
		}
		if(!permissionConf.containsKey("opinion") || StringUtil.isEmpty(permissionConf.getString("opinion")))return rtnJson;
		
		//3.构建意见权限JSON。
		JSONObject rtnOpinionJson=new JSONObject();
		JSONObject opinionJson=permissionConf.getJSONObject("opinion");
		for(Iterator<String> opinionIt= opinionJson.keySet().iterator();opinionIt.hasNext();){
			String opinionName=opinionIt.next();
			JSONObject perJson= opinionJson.getJSONObject(opinionName);
			//进行权限计算
			String permission=calcPermission(perJson,profilesMap);
			rtnOpinionJson.put(opinionName, permission);
		} 
		rtnJson.put("opinion", rtnOpinionJson);
		
		return rtnJson;
	}
	
	/**
	 * 字段1:权限，权限的值(n:没有权限,r:只读,w:编辑,b:必填)
	 * 如果有必填就不再继续判断，没有就判断判断编辑权限，再判断只读权限，没有就没有权限。
	 * 所有人权限和无权限只能有一项。
	 * @param tableJson
	 * @return
	 */
	private JSONObject buildTablePermission(JSONObject tableJson,Map<String,Set<String>> profilesMap){
		JSONObject rtnJson=new JSONObject();
		JSONObject fieldJsons=tableJson.getJSONObject("fields");
		//对每一个字段计算权限。
		for(Iterator<String> fieldIt= fieldJsons.keySet().iterator();fieldIt.hasNext();){
			String fieldName=fieldIt.next();
			JSONObject perJson=fieldJsons.getJSONObject(fieldName);
			String permission=calcPermission(perJson,profilesMap);
			rtnJson.put(fieldName, permission);
		}
		return rtnJson;
	}
	
	/**
	 * 权限计算。
	 * <pre>
	 * 1.先判断是否有必填权限。
	 * 2.再判断是否有编辑权限。
	 * 3.再判断是否有读的权限。
	 * 4.无权限。
	 * 
	 * 权限的值：
	 * n: 没有权限
	 * b: 必填
	 * w: 编辑
	 * r: 只读
	 * </pre>
	 * @param perJson
	 * @return
	 */
	private String calcPermission(JSONObject perJson,Map<String,Set<String>> profilesMap){
		//判断必填权限
		boolean hasRequired=hasRight(perJson, "required",profilesMap);
		
		String permission="n";
		
		if(hasRequired){
			permission="b";
		}
		else{
			//判断编辑
			boolean hasWrite=hasRight(perJson, "write",profilesMap);
			if(hasWrite){
				permission="w";
			}
			else{
				//判断只读
				boolean hasRead=hasRight(perJson, "read",profilesMap);
				if(hasRead){
					permission="r";
				}
			}
		}
		return permission;
	}

	/**
	 * 判断是否有权限。
	 * 
	 * 参数：
	 * jsonObj: 格式如下
	 * {
                "description": "姓名",
                "read": [
                    {
                        "type": "everyone"
                    }
                ],
                "write": [
                    {
                        "type": "none"
                    }
                ],
                "required": [
                    {
                        "type": "none"
                    }
                ]
            }
	 * @param jsonObj
	 * @param type 可能的值required,write,read
	 * @return
	 */
	private boolean hasRight(JSONObject jsonObj, String type,Map<String,Set<String>> profilesMap) {
		if(!jsonObj.containsKey(type)) return false;
		
		JSONArray jsonArray = (JSONArray) jsonObj.get(type);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			
			boolean hasRight=permssionCalc.hasRight(json.toJSONString(), profilesMap);
			
			if(hasRight) return true;
		} 
		return false;
	}
	
	@Override
	public void removeByFormKey(String formKey) {
		bpmFormRightDao.removeByFormKey(formKey);
	}
	
	/**
	 * 
	 * TODO方法名称描述
	 * 获取流程启动时的流程权限
	 * 1. 获取开始节点
	 * 2. 获取第一个任务节点
	 * 3. 获取全局
	 * 4. 在bpm_form_rights中根据formKey获取
	 * 5. 获取表单元数据的授权
	 * @param formKey
	 * @param flowKey
	 * @param nodeId
	 * @param nextNodeId
	 * @return 
	 * JSONObject
	 * @exception 
	 * @since  1.0.0
	 */
	@Override
	public JSONObject getStartPermission(String formKey, String flowKey, String nodeId, String nextNodeId) {
		JSONObject jsonObject = getByFlowNodeId(flowKey, nodeId, "");
		if(BeanUtils.isEmpty(jsonObject)){
			jsonObject = getByFlowNodeId(flowKey, nextNodeId, "");
		}
		if(BeanUtils.isEmpty(jsonObject)){
			jsonObject = getByFlowKey(flowKey, "");
		}
		if(BeanUtils.isEmpty(jsonObject)){
			jsonObject = getByFormKey(formKey, false);
		}
		return calcFormPermission(jsonObject);
	}



	@Override
	public List<BpmFormRight> getByFlowKey(String flowKey) {
		return bpmFormRightDao.getByFlowKey(flowKey);
	}
	
	@Override
	public void importFormRights(String formRightsXml) {
		try {
			BpmFormRightXml formRightList = (BpmFormRightXml) JAXBUtil.unmarshall(formRightsXml, BpmFormRightXml.class);
			List<BpmFormRight> list = formRightList.getRightList();
			for (BpmFormRight bpmFormRight : list) {
				BpmFormRight right = bpmFormRightDao.getByFlowNodeId(bpmFormRight.getFlowKey(), bpmFormRight.getNodeId(), bpmFormRight.getParentFlowKey(),bpmFormRight.getPermissionType());
				if(BeanUtils.isNotEmpty(right)){
					bpmFormRight.setId(right.getId());
					bpmFormRightDao.update(bpmFormRight);
					ThreadMsgUtil.addMsg("流程["+bpmFormRight.getFlowKey()+"]中节点[ "+bpmFormRight+" ]的表单授权已经存在,更新成功");
				}else{
					bpmFormRightDao.create(bpmFormRight);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("导入表单权限失败"+e.getMessage(),e);
		} 
	}


	/**
	 * 获取表单排序
	 */
	@Override
	public List<Map<String, String>> getTableOrderBySn(String formKey) {
		String formMetaKey=bpmFormDefDao.getMetaKeyByFormKey(formKey);
		BpmFormDef formDef = bpmFormDefDao.getByKey(formMetaKey);
		List<Map<String,String>>  retList=convertMapLower(formDef.getExpand());
		return retList;
	}


}

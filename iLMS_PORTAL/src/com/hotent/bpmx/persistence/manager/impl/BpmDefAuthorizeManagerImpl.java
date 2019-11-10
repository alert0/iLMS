package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmDefActDao;
import com.hotent.bpmx.persistence.dao.BpmDefAuthorizeDao;
import com.hotent.bpmx.persistence.dao.BpmDefAuthorizeTypeDao;
import com.hotent.bpmx.persistence.dao.BpmDefUserDao;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.model.AuthorizeRight;
import com.hotent.bpmx.persistence.model.BpmDefAct;
import com.hotent.bpmx.persistence.model.BpmDefAuthorize;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType;
import com.hotent.bpmx.persistence.model.BpmDefUser;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.sys.api.curuser.CurrentUserService;


/**
 * 对象功能:流程定义权限明细 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 10:10:50
 */
@Service
public class BpmDefAuthorizeManagerImpl extends AbstractManagerImpl<String, BpmDefAuthorize> implements BpmDefAuthorizeManager{

	@Resource
	private BpmDefUserDao bpmDefUserDao;
	@Resource
	private BpmDefActDao bpmDefActDao;
	@Resource
	private BpmDefAuthorizeTypeDao bpmDefAuthorizeTypeDao;
	@Resource
	private BpmDefAuthorizeDao bpmDefAuthorizeDao;
	@Resource
	private BpmDefinitionManager defaultBpmDefinitionManager;
	@Resource
	private CurrentUserService currentUserService;
	

	
	@Override
	protected Dao<String, BpmDefAuthorize> getDao() {
		return bpmDefAuthorizeDao;
	}
	
	
	/**
	 * 获取流程分管授权列表信息
	 * @param queryFilter
	 * @return 
	 * List<BpmDefAuthorize>
	 */
	public List<BpmDefAuthorize> getAuthorizeListByFilter(QueryFilter queryFilter){
		PageList<BpmDefAuthorize> list= (PageList<BpmDefAuthorize>) bpmDefAuthorizeDao.query(queryFilter);
		return list;
	}
	
	
	
	/**
	 * 获取流程分管授权所有信息
	 * @param id
	 * @return 
	 * BpmDefAuthorize
	 */
	public BpmDefAuthorize getAuthorizeById(String id){
		BpmDefAuthorize bpmDefAuthorize = getAuthorizeById(id,true);
		return bpmDefAuthorize;
	}
	

	/**
	 * 根据参数内容获取流程分管授权所有信息(即bpm_def_authorize及其相关表的所有授权信息)
	 * @param id
	 * @param isNeedjson
	 * @return 
	 * BpmDefAuthorize
	 */
	public BpmDefAuthorize getAuthorizeById(String id,boolean isNeedjson){
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("authorizeId", id);
		params.put("objType", BpmDefUser.BPMDEFUSER_OBJ_TYPE.BPM_DEF);
		
		BpmDefAuthorize	bpmDefAuthorize = bpmDefAuthorizeDao.get(id);
		
		//获取授权类型信息
		List<BpmDefAuthorizeType> bpmDefAuthorizeTypeList = bpmDefAuthorizeTypeDao.getAuthorizeTypeByMap(params);
		bpmDefAuthorize.setBpmDefAuthorizeTypeList(bpmDefAuthorizeTypeList);
		
		//获取子表授权用户信息
		List<BpmDefUser> bpmDefUserList = bpmDefUserDao.getUserByMap(params);
		bpmDefAuthorize.setBpmDefUserList(bpmDefUserList);

		//获取子表授权流程信息
		List<BpmDefAct> bpmDefActList = bpmDefActDao.getActByMap(params);
		bpmDefAuthorize.setBpmDefActList(bpmDefActList);
		
		//子表信息需要转JSON数据时
		if(isNeedjson){
			String ownerNameJson = toOwnerNameJson(bpmDefUserList);
			bpmDefAuthorize.setOwnerNameJson(ownerNameJson);
			String  defNameJson = toDefNameJson(bpmDefActList);
			bpmDefAuthorize.setDefNameJson(defNameJson);
		}
		return bpmDefAuthorize;
	}
	
	
	
	/**
	 * 按ID数据删除流程分管授权所有信息
	 * @param bpmDefAuthorize
	 * @return 
	 */
	public void deleteAuthorizeByIds(String[] lAryId){
		for (String id : lAryId){
			//删除授权主表信息
			bpmDefAuthorizeDao.remove(id);
			//删除原来的从表信息，包括授权类型表、被授权用户子表及流程权限子表
			bpmDefAuthorizeTypeDao.delByAuthorizeId(id);
			bpmDefUserDao.delByAuthorizeId(id,BpmDefUser.BPMDEFUSER_OBJ_TYPE.BPM_DEF);
			bpmDefActDao.delByAuthorizeId(id);
		}
	}
	
	
	/**
	 * 保存或修改流程分管授权所有信息
	 * @param bpmDefAuthorize
	 * @return 
	 * Long
	 */
	public String saveOrUpdateAuthorize(BpmDefAuthorize bpmDefAuthorize){
		//保存或修改流程分管授权主表信息（如果是修改的话先删除原来的从表信息，包括被授权用户子表及流程权限子表）
		String authorizeId = bpmDefAuthorize.getId();
		if(StringUtil.isNotEmpty(authorizeId)){
			bpmDefAuthorizeDao.update(bpmDefAuthorize);
			//删除原来的从表信息，包括授权类型表、被授权用户子表及流程权限子表
			bpmDefAuthorizeTypeDao.delByAuthorizeId(authorizeId);
			bpmDefUserDao.delByAuthorizeId(authorizeId,BpmDefUser.BPMDEFUSER_OBJ_TYPE.BPM_DEF);
			bpmDefActDao.delByAuthorizeId(authorizeId);
		}else{
			authorizeId = UniqueIdUtil.getSuid();
			bpmDefAuthorize.setId(authorizeId);
			bpmDefAuthorizeDao.create(bpmDefAuthorize);
		}

		//保存分管授权类型表信息
		String authorizeTypes = bpmDefAuthorize.getAuthorizeTypes();
		List<BpmDefAuthorizeType> bpmDefAuthorizeTypeList = toBpmDefAuthorizeTypeList(authorizeTypes, authorizeId);
		for (BpmDefAuthorizeType bpmDefAuthorizeType : bpmDefAuthorizeTypeList){
			bpmDefAuthorizeTypeDao.create(bpmDefAuthorizeType);
		}
		
		//保存流程分管授权用户子表信息
		String myOwnerNameJson = bpmDefAuthorize.getOwnerNameJson();
		List<BpmDefUser> bpmDefUserList = toBpmDefUserList(myOwnerNameJson, authorizeId);
		for (BpmDefUser bpmDefUser : bpmDefUserList){
			bpmDefUser.setObjType(BpmDefUser.BPMDEFUSER_OBJ_TYPE.BPM_DEF);
			bpmDefUserDao.create(bpmDefUser);
		}
		
		//保存流程分管授权流程子表信息
		String myDefNameJson = bpmDefAuthorize.getDefNameJson();
		List<BpmDefAct> bpmDefActList = toBpmDefActList(myDefNameJson, authorizeId);
		for (BpmDefAct bpmDefAct : bpmDefActList){
			bpmDefActDao.create(bpmDefAct);
		}
		
		return authorizeId;
	}
	
	/**
	 * 以逗号隔开授权类型字符串转成授权类型列表
	 * @param authorizeTypes
	 * 格式 {"start":false,"management":true,"task":false,"instance":false}
	 * @param authorizeId 授权主表ID
	 * @return 
	 * List<BpmDefAuthorizeType>
	 */
	private List<BpmDefAuthorizeType> toBpmDefAuthorizeTypeList(String authorizeTypes,String authorizeId){
		List<BpmDefAuthorizeType> authTypeList = new ArrayList<BpmDefAuthorizeType>();
		if(StringUtil.isEmpty(authorizeTypes)) return authTypeList;
		//{"start":false,"management":true,"task":false,"instance":false}
		JSONObject jsonObject=JSONObject.parseObject(authorizeTypes);
		Set<String> set= jsonObject.keySet();
		for(Iterator<String> it=set.iterator();it.hasNext();){
			String key=it.next();
			boolean blnSet=jsonObject.getBooleanValue(key);
			if(!blnSet) continue;
			
			BpmDefAuthorizeType authType = new BpmDefAuthorizeType();
			authType.setId(UniqueIdUtil.getSuid());
			authType.setAuthorizeId(authorizeId);
			authType.setAuthorizeType(key);
			authTypeList.add(authType);
		}
		return authTypeList;
	}
	
	/**
	 * 授权人员列表转成按RightType分组授权人员JSON (单个authorize_id_的人员列表)
	 * [{type:"everyone"},{type:"user",id:"",name:""}]
	 * @param myBpmDefUserList
	 * @return 
	 * String
	 */
	private String toOwnerNameJson(List<BpmDefUser> bpmDefUsers){
		if(BeanUtils.isEmpty(bpmDefUsers)) return "[]";
		Map<String,List<BpmDefUser>> map = new HashMap<String, List<BpmDefUser>>();
		
		Map<String,String> userTypeMap= currentUserService.getUserTypeMap(CurrentUserService.DEFAULT_OBJECT_RIGHTTYPE_BEAN);
		
		
		for(BpmDefUser user:bpmDefUsers){
			String rightType=user.getRightType();
			if(map.containsKey(rightType)){
				List<BpmDefUser> list=map.get(rightType);
				list.add(user);
			}
			else{
				List<BpmDefUser> list =new ArrayList<BpmDefUser>();
				list.add(user);
				map.put(rightType, list);
			}
		}
		JSONArray jsonArray=new JSONArray();
		
		for (Map.Entry<String, List<BpmDefUser>> entry : map.entrySet()) {
			JSONObject json= userEntToJson(entry,userTypeMap);
			jsonArray.add(json);
		}
		return jsonArray.toString();
	}
	
	
	private JSONObject userEntToJson(Map.Entry<String, List<BpmDefUser>> entry,Map<String,String> userTypeMap){
		JSONObject jsonObj=new JSONObject();
		String type=entry.getKey();
		String title=userTypeMap.get(type);
		jsonObj.put("type", type);
		jsonObj.put("title", title);
		if(type.equals("everyone")) {
			return jsonObj;
		}
		List<BpmDefUser> list=entry.getValue(); 
		String ids="";
		String names="";
		
		for(int i=0;i<list.size();i++){
			BpmDefUser user=list.get(i);
			if(i==0){
				ids+=user.getOwnerId();
				names+=user.getOwnerName();
			}
			else{
				ids+="," +user.getOwnerId();
				names+="," + user.getOwnerName();
			}
		}
		jsonObj.put("id", ids);
		jsonObj.put("name", names);
		
		return jsonObj;
	}
	

	/**
	 * 授权人员JSON转成授权人员列表
	 * @param ownNameJson
	 * @param authorizeId
	 * @return 
	 * List<BpmDefUser>
	 * 以下为JSON格式：
	 * [{type:"everyone"},{type:"user",id:"",name:""}]
	 * @exception 
	 * @since  1.0.0
	 */
	private List<BpmDefUser> toBpmDefUserList(String ownNameJson,String authorizeId){
		List<BpmDefUser> userList = new ArrayList<BpmDefUser>();
		if(StringUtil.isEmpty(ownNameJson)){
			return userList;
		}
		
		JSONArray aryJson=JSONArray.parseArray(ownNameJson);
		for(Object obj:aryJson){
			JSONObject jsonObject=(JSONObject)obj;
			List<BpmDefUser>  list= getList(jsonObject, authorizeId);
			userList.addAll(list);
		}
		return userList;
	}
	
	private List<BpmDefUser> getList(JSONObject json,String authorizeId){
		List<BpmDefUser> bpmDefUsers = new ArrayList<BpmDefUser>();
		String type=json.getString("type");
		
		if("everyone".equals(type)){
			BpmDefUser defUser = new BpmDefUser();
	        defUser.setId(UniqueIdUtil.getSuid());
	        defUser.setAuthorizeId(authorizeId);
	        defUser.setRightType(type);
	        bpmDefUsers.add(defUser);
		}
		else{
			String ids=json.getString("id");
			String names=json.getString("name");
			
			String[] aryId=ids.split(",");
			String[] aryName=names.split(",");
			for(int i=0;i<aryId.length;i++){
				BpmDefUser defUser = new BpmDefUser();
		        defUser.setId(UniqueIdUtil.getSuid());
		        defUser.setAuthorizeId(authorizeId);
		        defUser.setRightType(type);
		        defUser.setOwnerId(aryId[i]);
		        defUser.setOwnerName(aryName[i]);
		        
		        bpmDefUsers.add(defUser);
			}
		}
		return bpmDefUsers;
	}
	
	
	/**
	 * 授权流程列表转成授权流程JSON（仅一个authorize_id_时的流程列表）
	 * @param defActList
	 * @return 
	 * String
	 */
	public String toDefNameJson(List<BpmDefAct> defActList){
		if(BeanUtils.isEmpty(defActList) ){
			return "[]";
		}
		JSONArray jsonArray = new JSONArray(); 
		for(BpmDefAct act:defActList){
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("defKey", act.getDefKey());
			jsonObj.put("defName", act.getDefName());
			jsonObj.put("right", act.getRightContent());
			jsonArray.add(jsonObj);
		}
		return jsonArray.toString();
	}
	
	
	
	/**
	 * 授权流程JSON转成授权流程列表
	 * @param defNameJson
	 * @param authorizeId
	 * @return 
	 * List<BpmDefAct>
	 * JSON格式：
	 * 	  [
	 * 	   { 
	 *         "defKey":"zchz",
	 *         "defName":"周程汇总",
	 *         "right":{"m_edit":"Y","m_del":"N","m_start":"Y","m_set":"N"}
	 *       },
	 *       {
	 *        "defKey":"csjdsz",
	 *        "defName":"测试节点设置",
	 *        "right":{"m_edit":"Y","m_del":"N","m_start":"Y","m_set":"N"}
	 *       },
	 *       {
	 *        "defKey":"gxzlc",
	 *        "defName":"共享子流程",
	 *        "right":{"m_edit":"Y","m_del":"N","m_start":"Y","m_set":"N"}
	 *        }
	 *      ]
	 */
	private List<BpmDefAct> toBpmDefActList(String defNameJson, String authorizeId){
		List<BpmDefAct> myBpmDefActList = new ArrayList<BpmDefAct>();
		if(StringUtil.isEmpty(defNameJson)){
			return myBpmDefActList;
		}
		
		JSONArray myJsonArray = JSONArray.parseArray(defNameJson);
		//分析JSON,生成对应的BpmDefAct对象
		for(int i = 0; i < myJsonArray.size(); i++){     
            JSONObject jsonObject = myJsonArray.getJSONObject(i);     
            BpmDefAct bpmDefAct = new BpmDefAct();
           
        	String defKey = jsonObject.getString("defKey");
        	bpmDefAct.setDefKey(defKey);
        	String defName = jsonObject.getString("defName");
            bpmDefAct.setDefName(defName);
        	String rightContent = jsonObject.getString("right");
        	bpmDefAct.setRightContent(rightContent);
        	
            bpmDefAct.setId(UniqueIdUtil.getSuid());
            bpmDefAct.setAuthorizeId(authorizeId);
            myBpmDefActList.add(bpmDefAct);
        } 
		
		return myBpmDefActList;
	}

	

	/**
	 * 查询自己相关的分管授权的流程权限
	 * @param userId 指定用户ID
	 * @param authorizeType 授权类型(management,task,start,instance)
	 * @param isRight（是否包括流程操作细化的权限）
	 * @param isMyDef（是否包括自己创建的流程的所有权限，即自己创建的流程就拥有所有权限）
	 * @return 
	 * <pre>
	 * Map<String,Object> :包括：  defKeys授权的流程定义key 和authorizeRightMap对象
	 * defKeys:授权定义的KEY，以逗号隔开
	 * authorizeRightMap：流程授权的对象，即Map<String,JSONObject> 
	 * 	键：流程定义KEY
	 *  值：authorizeRight 流程明细权限 {"m_edit":true,"m_edit":true}
	 * </pre>
	 */	
	@Override
	public Map<String, Object> getActRightByUserId(String userId,String authorizeType, boolean isRight, boolean isMyDef) {
		Map<String,Set<String>> userRightMap=currentUserService.getUserRightMap();
		//用户权限列表
		Map<String, String> userRightMapStr=currentUserService.getMapStringByMayList(userRightMap);
		
		String defKeys = "";
		//转换流程授权的内容
		Map<String,JSONObject> authorizeRightMap = new HashMap<String, JSONObject>();
		
		//查询自己创建的流程
		if(isMyDef){
			List<DefaultBpmDefinition> myList = defaultBpmDefinitionManager.queryByCreateBy(userId);
			if(myList.size()>0){
				//如果需要所有权限的就直接虚拟一个有处理权限的对象
				for (DefaultBpmDefinition def : myList){
					String defKey = def.getDefKey(); 
					defKeys += "'"+defKey+"',";
					
					if(!isRight) continue;
						
					authorizeRightMap.put(defKey, AuthorizeRight.getCreateRight());
				}
			}
		}
		
		//获取流程授权的列表内容
		List<BpmDefAct> list =bpmDefActDao.getActRightByUserMap(userRightMapStr,authorizeType,"");
		if(list.size()>0){
			for (BpmDefAct bpmDefAct : list){
				String defKey = bpmDefAct.getDefKey();
				defKeys += "'"+defKey+"',";
				
				if(!isRight) continue;
				
				String rightContent = bpmDefAct.getRightContent();
				JSONObject authorizeRight = authorizeRightMap.get(defKey);
				if(authorizeRight!=null){
					AuthorizeRight.mergeJson(authorizeRight, rightContent);
				}else{
					authorizeRight=JSONObject.parseObject(rightContent);
					authorizeRightMap.put(defKey, authorizeRight);
				}	
			}
		}
		
		if(StringUtil.isNotEmpty(defKeys)){
			defKeys = defKeys.substring(0, defKeys.length()-1);
		}
		
		Map<String,Object>  resultMap=new HashMap<String,Object>();
		resultMap.put("defKeys", defKeys);
		resultMap.put("authorizeRightMap", authorizeRightMap);
		return resultMap;
	}


	@Override
	public JSONObject getRight(String flowKey, String authorizeType) {
		Map<String,Set<String>> userRightMap=currentUserService.getUserRightMap();
		//用户权限列表
		Map<String, String> userRightMapStr=currentUserService.getMapStringByMayList(userRightMap);
		JSONObject resultJson=null;
		//获取流程授权的列表内容
		List<BpmDefAct> list =bpmDefActDao.getActRightByUserMap(userRightMapStr,authorizeType,flowKey);
		if(BeanUtils.isEmpty(list)) return null;
		
		for (BpmDefAct bpmDefAct : list){
			String rightContent = bpmDefAct.getRightContent();
			if(resultJson!=null){
				AuthorizeRight.mergeJson(resultJson, rightContent);
			}
			else{
				resultJson=JSONObject.parseObject(rightContent);
			}
		}
		return resultJson;
	}
}

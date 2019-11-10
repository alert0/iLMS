package com.hotent.mini.ext.script;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hotent.base.core.engine.script.IScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.listener.BusDataUtil;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.webservice.api.IBoService;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgRelManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.Role;
import com.hotent.service.api.model.InvokeResult;
import com.hotent.sys.api.identity.IdentityService;
import com.hotent.sys.util.ContextUtil;

/**
 * 系统配置中的常用脚本。
 * @author ray
 *
 */
@Component
public class ScriptImpl implements IScript {
	
	@Resource
	IdentityService identityService;
	@Resource
	RoleManager roleManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BoDataService boDataService;
	@Resource
	OrgManager orgManager;
	@Resource
	OrgRelManager orgRelManager;
	@Resource
	private IBoService boServive;
	
	/**
	 * 获取当前用户对象。
	 * @return
	 */
	public IUser getCurrentUser(){
		return ContextUtil.getCurrentUser();
	}

	
	/**
	 * 获取当前用户ID。
	 * @return
	 */
	public String getCurrentUserId(){
		return ContextUtil.getCurrentUser().getUserId();
	}
	
	/**
	 * 获取当前用户帐号。
	 * @return
	 */
	public String getCurrentAccount(){
		return ContextUtil.getCurrentUser().getAccount();
	}
	
	/**
	 * 获取当前用户的当前组织id
	 * @return
	 */
	public String getCurrentGroupId(){
		return ContextUtil.getCurrentGroupId();
	}
	
	/**
	 * 获取当前用户的当前组织名称。
	 * @return
	 */
	public String getCurrentGroupName(){
		IGroup currentGroup = ContextUtil.getCurrentGroup();
		if (currentGroup == null) {
			return null;
		}
		return currentGroup.getName();
	}
	
	/**
	 * 根据别名生成流水号。
	 * <pre>
	 * 使用方法：
	 * scriptImpl.getNextNo("globalNo");
	 * </pre>
	 * @param alias	流水号配置别名。
	 * @return
	 */
	public String getNextNo(String alias) {
		return identityService.genNextNo(alias);
	}
	
	/**
	 * 判断命令是否是 DefaultTaskFinishCmd
	 * @param processCmd
	 * @return
	 */
	public  boolean isDefaultTaskFinishCmd(ActionCmd processCmd)
	{
		return DefaultTaskFinishCmd.class.getName().equals(processCmd.getClass().getName());
	}
	
	/**
	 * 判断数据是否在系统中存在。
	 * @param boData 
	 * @param fieldName 字段名
	 * @messages 消息
	 * @return
	 */
	public void validBoDataExist(BoData boData,String fieldName,String messages){
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		boolean isAdd=boData.isAdd();
		
		BaseBoEnt boEnt = boData.getBoEnt();
		String sql="";
		Object[] aryObj=null;
		Object obj = boData.getByKey(fieldName);
		String val = "";
		if(BeanUtils.isNotEmpty(obj)){
			val = obj.toString().trim();
		}
		
		//添加
		if(isAdd){
			aryObj=new Object[1];
			aryObj[0]= val;
			sql = "select count(*) from " + boEnt.getTableName() +" where " +BaseBoEnt.FIELD_PREFIX+ fieldName +"=?" ;
		}
		//更新数据
		else{
			aryObj=new Object[2];
			aryObj[0] = val;
			aryObj[1] = boData.getByKey(boEnt.getPkKey());
			sql="select  count(*) from " + boEnt.getTableName() +" where " + BaseBoEnt.FIELD_PREFIX+fieldName +"=? and "
					+boEnt.getPkKey()+"!=?" ;
		}
		Integer rtn=jdbcTemplate.queryForObject(sql, aryObj,Integer.class);
		if(rtn>0){
			if(StringUtil.isNotEmpty(messages)){
				throw new RuntimeException(messages);
			}else{
				throw new RuntimeException(val +"数据已经存在,请检查表单数据!");
			}
		}
	}
	
	/**
	 * 全部角色
	 * @return
	 */
	public Set<BpmIdentity> getRoles(){
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		List<Role> listRole= roleManager.getAll();
		for(Role role:listRole){
			BpmIdentity identity=new DefaultBpmIdentity(role.getId(),role.getName(),BpmIdentity.TYPE_GROUP);
			identity.setType(BpmIdentity.TYPE_GROUP);
			identity.setExtractType(ExtractType.EXACT_NOEXACT);
			identity.setGroupType( GroupTypeConstant.ROLE.key());
			set.add(identity);
		}
		return set;
	}
	
	/**
	 * 判断某个用户是否属于某个角色
	 * @param userId 用户ID
	 * @param roleAlias 角色别名
	 * @return
	 */
	public boolean isUserInRole(String userId, String roleAlias){
		if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(roleAlias)){
			return false;
		}
		List<Role> listRole = roleManager.getListByUserId(userId);
		if(BeanUtils.isNotEmpty(listRole)){
			for(Role role:listRole){
				if(roleAlias.equals(role.getAlias())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据流程实例id获取  表单内容， 审批意见， 审批结果， 审批人
	 */
	public  void getCmdData(String instanceId){
//		DefaultBpmProcessInstance bpmProcessInstance =  bpmProcessInstanceManager.get(instanceId);
//		// 表单内容
//		List<BoData> boDatas = boDataService.getDataByInst(bpmProcessInstance);
//		System.out.println(boDatas);
		
		ActionCmd cmd =  ContextThreadUtil.getActionCmd();
		System.out.println(cmd);
		
		//  审批结果  cmd  
		System.out.println(OpinionStatus.fromKey(cmd.getActionName()));
		
		// 从cmd中获取表单内容 
		System.out.println(cmd.getBusData());
		
		// 审批人
		System.out.println(ContextUtil.getCurrentUserId());
		
		// 审批意见
		if( cmd instanceof TaskFinishCmd){
			TaskFinishCmd taskCmd = (TaskFinishCmd) cmd;
			System.out.println(taskCmd.getApprovalOpinion());
		}
		
		// httpClient   
		
	}
	
	
	
	
	/**
	 * 获取cmd对象
	 * @return
	 */
	public ActionCmd getActionCmd(){
		ActionCmd cmd =  ContextThreadUtil.getActionCmd();
		return cmd;
	}
	
	/**
	 * 获取当前用户的主组织名称
	 * @return
	 */
	public String getIUserMainOrgName(){
		Org org = orgManager.getMainGroup(ContextUtil.getCurrentUser().getUserId());
		if(BeanUtils.isNotEmpty(org)){
			return org.getName();
		}
		return null;
	}
	
	/**
	 * 获取当前用户的主组织ID
	 * @return
	 */
	public String getIUserMainOrgID(){
		Org org = orgManager.getMainGroup(ContextUtil.getCurrentUser().getUserId());
		if(BeanUtils.isNotEmpty(org)){
			return org.getId();
		}
		return null;
	}
	
	
	/**
	 * 获取当前用户的岗位名称
	 * @return
	 */
	public Set<String> getIUserPostName(){
		Set<String> set = new HashSet<String>();
		List<OrgRel> orgRels = orgRelManager.getListByUserId(ContextUtil.getCurrentUser().getUserId());
		if(BeanUtils.isNotEmpty(orgRels)){
			for(OrgRel orgRel:orgRels){
				set.add(orgRel.getRelName());
			}
		}
		return set;
	}
	
	/**
	 * 获取当前用户的岗位ID
	 * @return
	 */
	public Set<String> getIUserPostID(){
		Set<String> set = new HashSet<String>();
		List<OrgRel> orgRels = orgRelManager.getListByUserId(ContextUtil.getCurrentUser().getUserId());
		if(BeanUtils.isNotEmpty(orgRels)){
			for(OrgRel orgRel:orgRels){
				set.add(orgRel.getId());
			}
		}
		return set;
	}
	
	/**
	 * 获取当前用户的角色名称
	 * @return
	 */
	public Set<String> getIUserRoleName(){
		Set<String> set = new HashSet<String>();
		List<Role> roles = roleManager.getListByUserId(ContextUtil.getCurrentUser().getUserId());
		if(BeanUtils.isNotEmpty(roles)){
			for(Role role:roles){
				set.add(role.getName());
			}
		}
		return set;
	}
	
	/**
	 * 获取当前用户的角色ID
	 * @return
	 */
	public Set<String> getIUserRoleID(){
		Set<String> set = new HashSet<String>();
		List<Role> roles = roleManager.getListByUserId(ContextUtil.getCurrentUser().getUserId());
		if(BeanUtils.isNotEmpty(roles)){
			for(Role role:roles){
				set.add(role.getId());
			}
		}
		return set;
	}
	
	/**
	 * 获取当前用户名称
	 * @return
	 */
	public String getCurrentUserName(){
		return ContextUtil.getCurrentUser().getFullname();
	}
	
	
	/**
	 * 获取当前日期，例如"2002-11-06"
	 * 
	 * @return
	 */
	public String getCurrentDate() {
		return TimeUtil.getCurrentDate();
	}
	
	
	/**
	 * 获取当前日期，按指定格式输出
	 * 
	 * @return
	 */
	public String getCurrentDateByStyle(String style) {
		return DateUtil.getCurrentTime(style);
	}
	
	/**
	 * 处理返回值
	 * @param invokeResult
	 * @param fwrwdx
	 * @param instanceId
	 */
	public void handlerResult(InvokeResult invokeResult, BoData fwrwdx, String instanceId){
		if(invokeResult.isFault()){
			throw new RuntimeException("SAP服务调用异常");
		}
		// 获得WebService接口调用返回值（这里是json格式的字符串）
		String json = invokeResult.getJson();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		JsonObject jsonObject = element.getAsJsonObject();
		// 解析json并获取到其中的result属性值
		JsonElement resultElement = jsonObject.get("result");
		if(resultElement!=null){
			// 将result赋值给BO的指定属性
			fwrwdx.set("type",resultElement.getAsString());
		}
		// 更新bo数据
		BusDataUtil.updateBoData(instanceId, fwrwdx);
	}
	
	/**
	 * 更新主表字段值
	 * @param defCode  业务对象编码
	 * @param field 字段名称
	 * @param value 字段值
	 */
	public void updateMainField(String defCode,String field,String value){
		ActionCmd cmd =  ContextThreadUtil.getActionCmd();
		if(StringUtil.isNotEmpty(defCode)&&StringUtil.isNotEmpty(field)){
			String busData = cmd.getBusData();
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(busData);
			Iterator iterator = jsonObject.keys();
			boolean isUpdate = false;
			while(iterator.hasNext()){
			     String key = (String) iterator.next();
			     if(key.equals(defCode)){
			    	 net.sf.json.JSONObject ywdxObj = jsonObject.getJSONObject(key);
			    	 if(BeanUtils.isNotEmpty(ywdxObj)){
			    		 Iterator fieldIterator = ywdxObj.keys();
			    		 while(fieldIterator.hasNext()){
			    			 String fieldKey = (String) fieldIterator.next();
			    			 if(fieldKey.equals(field)){
			    				 ywdxObj.put(field, value);
			    				 isUpdate = true;
			    			 }
			    		 }
			    	 }
			     }
			}
			if(isUpdate&&BeanUtils.isNotEmpty(bpmProcessInstanceManager.get(cmd.getInstId()))){
				boServive.updataBoData(cmd.getInstId(), jsonObject.toString());
			}
		}
	}
	
	
	/**
	 * 获取子表字段值
	 * @param ywdxCode 业务对象编码
	 * @param subTableName 子表表名
	 * @param field 字段名称
	 * @return
	 */
	public Object getSubFiledValue(String defCode,String subTableName,String field){
		ActionCmd cmd =  ContextThreadUtil.getActionCmd();
		if(StringUtil.isNotEmpty(defCode)&&StringUtil.isNotEmpty(subTableName)&&StringUtil.isNotEmpty(field)){
			String busData = cmd.getBusData();
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(busData);
			Iterator iterator = jsonObject.keys();
			while(iterator.hasNext()){
			     String key = (String) iterator.next();
			     if(key.equals(defCode)){
			    	 net.sf.json.JSONObject ywdxObj = jsonObject.getJSONObject(key);
			    	 if(BeanUtils.isNotEmpty(ywdxObj)){
			    		 Iterator fieldIterator = ywdxObj.keys();
			    		 while(fieldIterator.hasNext()){
			    			 String fieldKey = (String) fieldIterator.next();
			    			 String subName = "sub_"+subTableName;
			    			 if(fieldKey.equals(subName)){
			    				 net.sf.json.JSONObject subObj = jsonObject.getJSONObject(subName);
						    	 if(BeanUtils.isNotEmpty(subObj)){
						    		 Iterator subIterator = ywdxObj.keys();
						    		 while(subIterator.hasNext()){
						    			 String subFiledKey = (String) fieldIterator.next();
						    			 if(subFiledKey.equals(field)){
						    				 return ywdxObj.get(field);
						    			 }
						    		 }
						    	 }
			    			 }
			    		 }
			    	 }
			     }
			}
		}
		return null;
	}
}

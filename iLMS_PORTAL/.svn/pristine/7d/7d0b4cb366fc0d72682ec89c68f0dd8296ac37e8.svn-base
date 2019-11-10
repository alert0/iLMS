package com.hotent.bpmx.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmDefUserDao;
import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
import com.hotent.bpmx.persistence.model.BpmDefAct;
import com.hotent.bpmx.persistence.model.BpmDefUser;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.curuser.CurrentUserService;
import com.hotent.sys.util.ContextUtil;






/**
 * 对象功能:流程定义权限明细 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 14:10:50
 */
@Service
public class BpmDefUserManagerImpl extends AbstractManagerImpl<String,  BpmDefUser> implements  BpmDefUserManager{

	@Resource
	private CurrentUserService currentUserService;
	@Resource
	private BpmDefUserDao bpmDefUserDao;
	

	@Override
	protected Dao<String, BpmDefUser> getDao() {
		return bpmDefUserDao;
	}


	@Override
	public JSONArray getRights(String authorizeId,String objType) {
		String ownerNameJson = "[]";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("authorizeId", authorizeId);
		params.put("objType", objType);
		List<BpmDefUser> bpmDefUsers = bpmDefUserDao.getUserByMap(params);
		ownerNameJson = toOwnerNameJson(bpmDefUsers);
		return JSONArray.parseArray(ownerNameJson);
	}

	@Override
	public void saveRights(String authorizeId,String objType,String ownerNameJson) {
		// TODO Auto-generated method stub
		if(StringUtil.isNotEmpty(ownerNameJson)){
			bpmDefUserDao.delByAuthorizeId(authorizeId,objType);
			List<BpmDefUser> bpmDefUserList = toBpmDefUserList(ownerNameJson, authorizeId);
			for (BpmDefUser bpmDefUser : bpmDefUserList){
				bpmDefUser.setObjType(objType);
				bpmDefUserDao.create(bpmDefUser);
			}
		}
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


	@Override
	public List<String> getAuthorizeIdsByUserMap(String objType) {
		// 获得流程分管授权与用户相关的信息集合的流程权限内容
		Map<String,Set<String>> userRightMap=currentUserService.getUserRightMap();
		//用户权限列表
		Map<String, String> userRightMapStr=currentUserService.getMapStringByMayList(userRightMap);
		List<String> list = bpmDefUserDao.getAuthorizeIdsByUserMap(userRightMapStr,objType);
		return list;
	}
	

	@Override
	public boolean hasRights(String authorizeId) {
		// 获得流程分管授权与用户相关的信息集合的流程权限内容
		Map<String,Set<String>> userRightMap=currentUserService.getUserRightMap();
		//用户权限列表
		Map<String, String> userRightMapStr=currentUserService.getMapStringByMayList(userRightMap);
		List<String> list = bpmDefUserDao.getAuthByAuthorizeId(userRightMapStr, authorizeId);
		if(BeanUtils.isNotEmpty(list)){
			return true;
		}
		return false;
	}
}

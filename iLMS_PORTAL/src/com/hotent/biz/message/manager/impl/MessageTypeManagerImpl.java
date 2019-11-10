package com.hotent.biz.message.manager.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.biz.message.dao.MessageTypeDao;
import com.hotent.biz.message.manager.MessageTypeManager;
import com.hotent.biz.message.model.MessageType;
import com.hotent.bpmx.persistence.dao.BpmDefUserDao;
import com.hotent.bpmx.persistence.model.BpmDefUser;
import com.hotent.sys.api.curuser.CurrentUserService;

/**
 * 
 * <pre> 
 * 描述：xq_message_type 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 15:26:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("messageTypeManager")
public class MessageTypeManagerImpl extends AbstractManagerImpl<String, MessageType> implements MessageTypeManager{
	@Resource
	private MessageTypeDao messageTypeDao;
	@Resource
	private BpmDefUserDao bpmDefUserDao;
	@Resource
	private CurrentUserService currentUserService;
	@Override
	protected Dao<String, MessageType> getDao() {
		return messageTypeDao;
	}
	@Override
	public JSONArray getAuthority(String id,String objType){
		String ownerNameJson = "[]";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("authorizeId", id);
		params.put("objType", objType);
		List<BpmDefUser> list =  bpmDefUserDao.getUserByMap(params);
		ownerNameJson = toOwnerNameJson(list);
		return JSONArray.parseArray(ownerNameJson);
		
		
	}
	
	public String toOwnerNameJson(List<BpmDefUser> bpmDefUsers){
		if(BeanUtils.isEmpty(bpmDefUsers))return "[]";
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
	private JSONObject userEntToJson(Entry<String, List<BpmDefUser>> entry, Map<String, String> typeMap) {
		JSONObject jsonObj=new JSONObject();
		String key = entry.getKey();
		String title = typeMap.get(key);
		jsonObj.put("type", key);
		jsonObj.put("title", title);
		if(key.equals("everyone")) {
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
				
			}else{
				ids+="," +user.getOwnerId();
				names+="," + user.getOwnerName();
			}
			
		}
		jsonObj.put("id", ids);
		jsonObj.put("name", names);
		return jsonObj;
	}
	@Override
	public void getSaveAuth(String authorizeId,String objType,String ownerNameJson){
		if(StringUtil.isNotEmpty(ownerNameJson)){
			bpmDefUserDao.delByAuthorizeId(authorizeId,objType);
			List<BpmDefUser> bpmDefUserList =toBpmDefUserList(authorizeId,ownerNameJson);
			for(BpmDefUser bpmDefUser :bpmDefUserList){
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
	private List<BpmDefUser> toBpmDefUserList(String authorizeId, String ownerNameJson) {
		List<BpmDefUser> bpmDefList = new ArrayList<BpmDefUser>();
		if(StringUtil.isEmpty(ownerNameJson)){
			return bpmDefList;
		}
		JSONArray aryJson=JSONArray.parseArray(ownerNameJson);
		for(Object obj:aryJson){
			JSONObject jsonObject=(JSONObject)obj;
			List<BpmDefUser>  list= getList(jsonObject, authorizeId);
			bpmDefList.addAll(list);
		}
		return bpmDefList;
	}
	private List<BpmDefUser> getList(JSONObject jsonObject, String authorizeId) {
		List<BpmDefUser> bpmDefUsers = new ArrayList<BpmDefUser>();
		String type=jsonObject.getString("type");
		if("everyone".equals(type)){
			BpmDefUser defUser = new BpmDefUser();
			defUser.setId(UniqueIdUtil.getSuid());
	        defUser.setAuthorizeId(authorizeId);
	        defUser.setRightType(type);
	        bpmDefUsers.add(defUser);
		}else{
			String ids=jsonObject.getString("id");
			String names=jsonObject.getString("name");
			String[] arrId=ids.split(",");
			String[] arrName =names.split(",");
			for(int i=0;i<arrId.length;i++){
				BpmDefUser defUser = new BpmDefUser();
		        defUser.setId(UniqueIdUtil.getSuid());
		        defUser.setAuthorizeId(authorizeId);
		        defUser.setRightType(type);
		        defUser.setOwnerId(arrId[i]);
		        defUser.setOwnerName(arrName[i]);
		        
		        bpmDefUsers.add(defUser);
				
			}
		}
		return bpmDefUsers;
	}
	
	public List<String> getAuthorizeIdsByUserMap(String objType){
		// 获得流程分管授权与用户相关的信息集合的流程权限内容
		Map<String,Set<String>> userRightMap=currentUserService.getUserRightMap();
		//用户权限列表
		Map<String, String> userRightMapStr=currentUserService.getMapStringByMayList(userRightMap);
		List<String> list = bpmDefUserDao.getAuthorizeIdsByUserMap(userRightMapStr,objType);
		return list;
	}
	
	/**
	 * 保存或修改流程分管授权所有信息
	 * @param bpmDefAuthorize
	 * @return 
	 * Long
	 */
	public String saveOrUpdateAuthorize(MessageType messageType){
		String authorizeId = messageType.getId();
		if(StringUtil.isNotEmpty(authorizeId)){
			messageTypeDao.update(messageType);
			
		}else{
			authorizeId = UniqueIdUtil.getSuid();
			messageType.setId(authorizeId);
			messageTypeDao.create(messageType);
		}
		bpmDefUserDao.delByAuthorizeId(authorizeId,BpmDefUser.BPMDEFUSER_OBJ_TYPE.INDICATOR_COLUMN);
		List<BpmDefUser> bpmDefUserList =toBpmDefUserList(authorizeId,messageType.getOwnerNameJson());
		for(BpmDefUser bpmDefUser :bpmDefUserList){
			bpmDefUser.setObjType(BpmDefUser.BPMDEFUSER_OBJ_TYPE.INDICATOR_COLUMN);
			bpmDefUserDao.create(bpmDefUser);
			
		}
		
		return authorizeId;
	}
	@Override
	public List<Map<String, Object>> queryAll(QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return messageTypeDao.queryAll(queryFilter);
	}
	@Override
	public MessageType getByClassificationCode(String classificationCode) {
		return messageTypeDao.getByClassificationCode(classificationCode);
	}
	@Override
	public Boolean isExistCode(String classificationCode, String id) {
		return messageTypeDao.isExistCode(classificationCode,id);
	}
	
}

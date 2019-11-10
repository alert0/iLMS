/**
 * 对象功能:流程分管授权限用户中间表明细 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 10:10:53
 */
package com.hotent.bpmx.persistence.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmDefUserDao;
import com.hotent.bpmx.persistence.model.BpmDefAct;
import com.hotent.bpmx.persistence.model.BpmDefUser;

@Repository
public class BpmDefUserDaoImpl extends MyBatisDaoImpl<String, BpmDefUser> implements BpmDefUserDao{

	@Override
	public String getNamespace()
	{		
		return BpmDefUser.class.getName();
	}
	
	/**
	 * 获取所有的授权的对象用户
	 * @param params
	 * @return
	 */
	public  List<BpmDefUser>  getUserByMap(Map<String,Object> params){
		return getByKey("getAll", params); 
	}
	
	/**
	 * 根据授权ID删除流程用户子表的权限信息
	 * @param typeId
	 * @return
	 */
	public void delByAuthorizeId(String authorizeId, String objType){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("authorizeId", authorizeId);
		params.put("objType", objType);
		getByKey("delByAuthorizeId", params);
	}

	@Override
	public List<String> getAuthorizeIdsByUserMap(Map<String, String> userRightMap,String objType) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userRightMap", userRightMap);
		params.put("objType", objType);
		return  (List)getByKey("getAuthorizeIdsByUserMap", params); 
	}

	@Override
	public List<String> getAuthByAuthorizeId(Map<String, String> userRightMap,
			String authorizeId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userRightMap", userRightMap);
		params.put("authorizeId", authorizeId);
		return  (List)getByKey("getAuthByAuthorizeId", params); 
	}
}
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
import com.hotent.bpmx.persistence.dao.BpmDefActDao;
import com.hotent.bpmx.persistence.model.BpmDefAct;



@Repository
public class BpmDefActDaoImpl extends MyBatisDaoImpl<String, BpmDefAct> implements BpmDefActDao{

	@Override
	public String getNamespace()
	{		
		return BpmDefAct.class.getName();
	}
	
	/**
	 * 获取所有的授权的流程
	 * @param params
	 * @return
	 */
	public  List<BpmDefAct>  getActByMap(Map<String,Object> params){
		return getByKey("getAll", params); 
	}
	
	/**
	 * 根据授权ID删除流程授权的权限信息
	 * @param typeId
	 * @return
	 */
	public void delByAuthorizeId(String authorizeId){
		getByKey("delByAuthorizeId", authorizeId);
	}
	
	/**
	 * 根据流程相关信息删除流程授权的权限信息
	 * @param params
	 * @return
	 */
	public void delByMap(Map<String,Object> params){
		getByKey("delByMap", params);
	}
	
	/**
	 * 根据用户的权限获取获取与用户相关的授权的流程
	 * @param params key为RightType，Vlaue为以逗号隔开值的字符中
	 * @return
	 */
	public  List<BpmDefAct>  getActRightByUserMap(Map<String,String> userRightMap,String authorizeType,String defKey){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("authorizeType", authorizeType);
		params.put("userRightMap", userRightMap);
		if(StringUtil.isNotEmpty(defKey)){
			params.put("defKey", defKey);
		}
		return  getByKey("getActRightByUserMap", params); 
	}
	
}
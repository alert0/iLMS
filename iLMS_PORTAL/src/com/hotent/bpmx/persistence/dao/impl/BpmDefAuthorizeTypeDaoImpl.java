/**
 * 对象功能:流程分管授权限用户中间表明细 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 10:10:53
 */
package com.hotent.bpmx.persistence.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmDefAuthorizeTypeDao;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType;


@Repository
public class BpmDefAuthorizeTypeDaoImpl extends MyBatisDaoImpl<String, BpmDefAuthorizeType> implements BpmDefAuthorizeTypeDao{

	@Override
	public String getNamespace()
	{		
		return BpmDefAuthorizeType.class.getName();
	}

	@Override
	public List<BpmDefAuthorizeType> getAuthorizeTypeByMap(Map<String, Object> params)
	{
		return getByKey("getAll", params);
	}

	@Override
	public void delByAuthorizeId(String authorizeId)
	{
		getByKey("delByAuthorizeId", authorizeId);
	}
	
}
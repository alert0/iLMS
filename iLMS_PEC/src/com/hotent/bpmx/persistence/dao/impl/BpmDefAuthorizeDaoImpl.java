/**
 * 对象功能:流程分管授权限用户中间表明细 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 10:10:53
 */
package com.hotent.bpmx.persistence.dao.impl;


import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmDefAuthorizeDao;
import com.hotent.bpmx.persistence.model.BpmDefAuthorize;


@Repository
public class BpmDefAuthorizeDaoImpl extends MyBatisDaoImpl<String, BpmDefAuthorize> implements BpmDefAuthorizeDao{
	
	@Override
	public String getNamespace()
	{		
		return BpmDefAuthorize.class.getName();
	}
	
}
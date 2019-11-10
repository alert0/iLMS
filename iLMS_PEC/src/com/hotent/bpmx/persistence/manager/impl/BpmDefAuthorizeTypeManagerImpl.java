package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmDefAuthorizeTypeDao;
import com.hotent.bpmx.persistence.manager.BpmDefAuthorizeTypeManager;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType;






/**
 * 对象功能:流程定义权限明细 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 14:10:50
 */
@Service
public class BpmDefAuthorizeTypeManagerImpl extends AbstractManagerImpl<String,  BpmDefAuthorizeType> implements  BpmDefAuthorizeTypeManager{

	@Resource
	private BpmDefAuthorizeTypeDao bpmDefAuthorizeTypeDao;
	

	@Override
	protected Dao<String, BpmDefAuthorizeType> getDao() {
		return bpmDefAuthorizeTypeDao;
	}
}

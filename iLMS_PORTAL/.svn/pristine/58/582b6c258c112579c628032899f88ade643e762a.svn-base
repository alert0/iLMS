package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.ResUrlDao;
import com.hotent.sys.persistence.manager.ResUrlManager;
import com.hotent.sys.persistence.model.ResUrl;
 

@Service("resUrlManager")
public class ResUrlManagerImpl extends AbstractManagerImpl<String, ResUrl> implements ResUrlManager{
	@Resource
	ResUrlDao resUrlDao;
	@Override
	protected Dao<String, ResUrl> getDao() {
		return resUrlDao;
	}
	
	
	
	@Override
	public void deleteByResId(String resId) {
		resUrlDao.deleteByResId(resId);
	}



	@Override
	public List<ResUrl> getByResId(String resId) {
		return resUrlDao.getByResId(resId);
	}
}

package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.ResUrlDao;
import com.hotent.sys.persistence.model.ResUrl;



@Repository

public class ResUrlDaoImpl extends MyBatisDaoImpl<String, ResUrl> implements ResUrlDao{

    @Override
    public String getNamespace() {
        return ResUrl.class.getName();
    }

	@Override
	public void deleteByResId(String resId) {
		this.deleteByKey("deleteByResId", resId);
	}

	@Override
	public List<ResUrl> getByResId(String resId) {
		return this.getByKey("getByResId", resId);
	}
	
}


package com.hotent.portal.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.portal.persistence.dao.SysIndexMyLayoutDao;
import com.hotent.portal.persistence.model.SysIndexMyLayout;
@Repository
public class SysIndexMyLayoutDaoImpl extends MyBatisDaoImpl<String, SysIndexMyLayout> implements SysIndexMyLayoutDao{

	@Override
    public String getNamespace() {
        return SysIndexMyLayout.class.getName();
    }
	@Override
	public SysIndexMyLayout getByUserId(String userId) {
		return this.getUnique("getByUserId", userId);
		
	}

	
	

}

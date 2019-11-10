package com.hotent.bpmx.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmDefDataDao;
import com.hotent.bpmx.persistence.model.BpmDefData;

@Repository
public class BpmDefDataDaoImpl extends MyBatisDaoImpl<String, BpmDefData> implements BpmDefDataDao{

    @Override
    public String getNamespace() {
        return BpmDefData.class.getName();
    }

	@Override
	public void delByDefKey(String defKey) {
		this.deleteByKey("delByDefKey", defKey);
	}
	
}

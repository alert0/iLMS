package com.hotent.bo.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bo.api.model.BOService;
import com.hotent.bo.persistence.dao.BOServiceDao;

@Repository
public class BOServiceDaoImpl extends MyBatisDaoImpl<String, BOService> implements BOServiceDao{
    @Override
    public String getNamespace() {
        return BOService.class.getName();
    }

	@Override
	public List<BOService> getServicesByDefId(String defId) {
		return getByKey("getServicesByDefId", buildMap("defId", defId));
	}

	@Override
	public void removeServicesByDefId(String defId) {
		deleteByKey("removeServicesByDefId", buildMap("defId", defId));
	}
}

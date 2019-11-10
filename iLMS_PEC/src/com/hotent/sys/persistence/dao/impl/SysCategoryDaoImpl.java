package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysCategoryDao;
import com.hotent.sys.persistence.model.SysCategory;


@Repository
public class SysCategoryDaoImpl extends MyBatisDaoImpl<String, SysCategory> implements SysCategoryDao{

    @Override
    public String getNamespace() {
        return SysCategory.class.getName();
    }

    /**
     * 判断groupKey是否已经存在
     */
	@Override
	public Boolean isKeyExist(String id, String groupKey) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		params.put("groupKey", groupKey);
		Integer sn=(Integer)getOne("isKeyExist", params);
		return sn>0?true:false;
	}

	@Override
	public SysCategory getByKey(String key) {
		return this.getUnique("getByKey", key);
	}
	
}


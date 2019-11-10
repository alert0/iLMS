package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmSelectorDefDao;
import com.hotent.bpmx.persistence.model.BpmSelectorDef;


@Repository
public class BpmSelectorDefDaoImpl extends MyBatisDaoImpl<String, BpmSelectorDef> implements BpmSelectorDefDao{

    @Override
    public String getNamespace() {
        return BpmSelectorDef.class.getName();
    }
    @Override
	public boolean isExistAlias(String alias, String id) {
		
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("alias", alias);
		if (StringUtil.isNotEmpty(id))
			params.put("id", id);
		Integer rtn=(Integer) this.getOne("isExistAlias", params);
		return rtn >0;
	}
	@Override
	public BpmSelectorDef getByAlias(String alias) {
		return (BpmSelectorDef) this.getOne("getByAlias", alias);
	}
	
}


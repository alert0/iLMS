package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.ConditionScriptDao;
import com.hotent.sys.persistence.model.ConditionScript;

@Repository
public class ConditionScriptDaoImpl extends MyBatisDaoImpl<String, ConditionScript> implements ConditionScriptDao {

	@Override
	public String getNamespace() {
		return ConditionScript.class.getName();
	}

}

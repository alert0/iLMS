package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.PersonScriptDao;
import com.hotent.sys.persistence.model.PersonScript;

@Repository
public class PersonScriptDaoImpl extends MyBatisDaoImpl<String, PersonScript> implements PersonScriptDao {

	@Override
	public String getNamespace() {
		return PersonScript.class.getName();
	}

}

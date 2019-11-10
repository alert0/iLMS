package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.AliasScriptDao;
import com.hotent.sys.persistence.model.AliasScript;


@Repository
public class AliasScriptDaoImpl extends MyBatisDaoImpl<String, AliasScript> implements AliasScriptDao {

	@Override
	public String getNamespace() {
		return AliasScript.class.getName();
	}

	@Override
	public AliasScript getByAlias(String alias) {
		return this.getUnique("getByAlias", alias);
	}


}

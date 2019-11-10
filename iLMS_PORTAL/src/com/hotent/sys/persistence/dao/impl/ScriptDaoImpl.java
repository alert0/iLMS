package com.hotent.sys.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.ScriptDao;
import com.hotent.sys.persistence.model.Script;


@Repository
public class ScriptDaoImpl extends MyBatisDaoImpl<String, Script> implements ScriptDao{

	@Override
	public String getNamespace() {
		return Script.class.getName();
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> getDistinctCategory() {
		List list= this.getByKey("getDistinctCategory", null);
		return list;
	}

	@Override
	public Integer isNameExists(String name) {
		return (Integer)getOne("isExistWithName",name);
	}


}

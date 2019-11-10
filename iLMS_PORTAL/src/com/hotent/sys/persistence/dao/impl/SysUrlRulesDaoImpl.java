package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysUrlRulesDao;
import com.hotent.sys.persistence.model.SysUrlRules;



@Repository

public class SysUrlRulesDaoImpl extends MyBatisDaoImpl<String, SysUrlRules> implements SysUrlRulesDao{

    @Override
    public String getNamespace() {
        return SysUrlRules.class.getName();
    }

	@Override
	public List<SysUrlRules> getByUrlPerId(String sysUrlId) {
		return this.sqlSessionTemplate.selectList("getByUrlPerId", sysUrlId);
	}

	@Override
	public void delRuelByPermId(String permId) {
		this.deleteByKey("delRuelByPermId", permId);
	}

	@Override
	public List<SysUrlRules> getAllByEnableAndPermId(Short enable, String permId) {
		Map param = new HashMap();
		param.put("enable", enable);
		param.put("permId", permId);
		return this.sqlSessionTemplate.selectList("getAllByEnableAndPermId",param);
	}
	
}


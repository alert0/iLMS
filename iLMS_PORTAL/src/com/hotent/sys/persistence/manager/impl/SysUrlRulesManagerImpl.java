package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import oracle.net.aso.p;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysUrlRulesDao;
import com.hotent.sys.persistence.manager.SysUrlRulesManager;
import com.hotent.sys.persistence.model.SysUrlRules;
 

@Service("sysUrlRulesManager")
public class SysUrlRulesManagerImpl extends AbstractManagerImpl<String, SysUrlRules> implements SysUrlRulesManager{
	@Resource
	SysUrlRulesDao sysUrlRulesDao;
	@Override
	protected Dao<String, SysUrlRules> getDao() {
		return sysUrlRulesDao;
	}
	@Override
	public List<SysUrlRules> getByUrlPerId(String id) {
		List<SysUrlRules> sysUrlRules = sysUrlRulesDao.getByUrlPerId(id);
		return sysUrlRules;
	}
	//根据ruleList批量添加脚本规则
	@Override
	public void addRules(List<SysUrlRules> sysUrlRulesList, String permId) {
		if (BeanUtils.isEmpty(sysUrlRulesList)) return ;
		for (SysUrlRules sysUrlRules : sysUrlRulesList ) {
			sysUrlRules.setSysUrlId(permId);
			sysUrlRulesDao.create(sysUrlRules);
		}
		
	}
	@Override
	public void updateRules(List<SysUrlRules> sysUrlRulesList, String permId) {
		if (BeanUtils.isEmpty(sysUrlRulesList)) return ;
		sysUrlRulesDao.delRuelByPermId(permId);
		addRules(sysUrlRulesList, permId);
		
	}
	@Override
	public List<SysUrlRules> getAllByEnableAndPermId(Short enable, String permId) {
		return sysUrlRulesDao.getAllByEnableAndPermId(enable,permId);
	}
}

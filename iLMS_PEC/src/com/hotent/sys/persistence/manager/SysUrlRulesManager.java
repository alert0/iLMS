package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysUrlRules;
 

public interface SysUrlRulesManager extends Manager<String, SysUrlRules>{

	List<SysUrlRules> getByUrlPerId(String id);

	void addRules(List<SysUrlRules> sysUrlRulesList,String permId);

	void updateRules(List<SysUrlRules> sysUrlRulesList,String permId);

	List<SysUrlRules> getAllByEnableAndPermId(Short enable, String permId);
	
}

package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysUrlRules;
 


public interface SysUrlRulesDao extends Dao<String, SysUrlRules> {

	List<SysUrlRules> getByUrlPerId(String id);

	void delRuelByPermId(String permId);

	List<SysUrlRules> getAllByEnableAndPermId(Short enable, String permId);
}

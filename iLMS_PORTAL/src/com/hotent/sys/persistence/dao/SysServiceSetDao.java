package com.hotent.sys.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysServiceSet;
 


public interface SysServiceSetDao extends Dao<String, SysServiceSet> {

	SysServiceSet getByAlias(String alias);
}

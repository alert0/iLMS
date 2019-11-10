package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.Resources;



public interface ResourcesDao  extends Dao<String, Resources> {

	List<Resources> getBySystemId(String systemId);

	Integer isAliasExists(String systemId, String alias);

	Integer isAliasExistsForUpd(String systemId, String id, String alias);
	/**
	 * 通过 url 查询  resource Id
	 * @param url
	 * @return
	 */
	List<Resources> getResourceByUrl(String url);

	List<Resources> getByRoleIdsSystemId(List<String> roleIds, String systemId);
	
}



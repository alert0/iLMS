package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysUrlPermission;
 


public interface SysUrlPermissionDao extends Dao<String, SysUrlPermission> {

	//根据enable获取所有的禁用或者可用的Url拦截配置
	List<SysUrlPermission> getAllByEnable(Short enable);
}

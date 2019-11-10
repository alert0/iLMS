package com.hotent.mini.controller.system.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
 
import com.hotent.service.api.model.InvokeResult;
import com.hotent.sys.persistence.model.SysServiceParam;
import com.hotent.sys.persistence.model.SysServiceSet;

public interface SysServiceSetManager extends Manager<String, SysServiceSet>{
	List<SysServiceParam> getParams(String setId);
	
	InvokeResult invokeService(String serviceSetId,String params);

	SysServiceSet getByAlias(String alias);
}

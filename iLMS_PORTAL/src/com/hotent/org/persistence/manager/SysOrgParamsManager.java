package com.hotent.org.persistence.manager;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.SysOrgParams;

/**
 * 
 * <pre> 
 * 描述：组织参数 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-04 11:39:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysOrgParamsManager extends Manager<String, SysOrgParams>{

	List<SysOrgParams> getByOrgId(String id);

	void saveParams(String orgId, List<JSONObject> lists);

	SysOrgParams getByOrgIdAndAlias(String groupId, String key);
	
}

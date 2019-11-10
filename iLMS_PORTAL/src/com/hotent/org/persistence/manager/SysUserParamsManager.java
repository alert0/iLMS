package com.hotent.org.persistence.manager;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.SysUserParams;

/**
 * 
 * <pre> 
 * 描述：用户参数 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-01 17:11:33
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysUserParamsManager extends Manager<String, SysUserParams>{

	List<SysUserParams> getByUserId(String id);

	void saveParams(String userId, List<JSONObject> lists);

	SysUserParams getByUserIdAndAlias(String userId, String key);
	
}

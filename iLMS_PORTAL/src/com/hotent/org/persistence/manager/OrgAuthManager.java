package com.hotent.org.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.OrgAuth;

/**
 * 
 * <pre> 
 * 描述：分级组织管理 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-20 14:30:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgAuthManager extends Manager<String, OrgAuth>{
	
	/**
	 * 获取所有的分级组织
	 * @param queryFilter
	 * @return
	 */
	List<OrgAuth> getAllOrgAuth(QueryFilter queryFilter);
	
	/**
	 * 根据组织id和人员id获取分级管理
	 * @param map
	 * @return
	 */
	OrgAuth getByOrgIdAndUserId(Map<String,String> map);
	/**
	 * 根据用户id获取获取当前用户的组织布局管理权限
	 * @param userId
	 * @return
	 */
	List<OrgAuth> getLayoutOrgAuth(String userId);
	
	/**
	 * 通过用户获取所有授权的组
	 * @param userId
	 * @return
	 */
	List<OrgAuth> getByUserId(String userId);
}

package com.hotent.base.persistence.manager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hotent.base.manager.api.Manager;
import com.hotent.base.persistence.model.ResRole;
import com.hotent.sys.api.system.ResRoleService;

/**
 * 
 * <pre> 
 * 描述：角色资源分配 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface ResRoleManager extends Manager<String, ResRole>,ResRoleService{

	List<ResRole> getAllByRoleId(String roleId);
	
	/**
	 * 分配角色资源。
	 * @param resIds
	 * @param systemId
	 * @param roleId
	 */
	void assignResByRoleSys(String resIds,String systemId,String roleId);
	
	/**
	 * 根据系统id获取资源URL和角色的映射。
	 * @param systemId
	 * @return
	 */
	Map<String,Set<String>> getUrlRoleBySystem(String systemId);
	
	
	/**
	 * 根据系统id获取资源和角色的映射。
	 * @param systemId
	 * @return
	 */
	Map<String,Set<String>> getResRoleBySystem(String systemId);
	
	/**
	 * 判断当前登录用户是否有权限访问某个资源。
	 * <pre>
	 * 1.先获指定资源的角色映射。
	 * 2.获取当前用户的角色列表。
	 * 3.判断当前用户角色是否在资源的角色列表中，如果在则表示有权限。
	 * </pre>
	 * @param systemId		子系统ID
	 * @param alias			资源别名
	 * @return
	 */
	boolean hasRight(String systemId,String alias);
	
}

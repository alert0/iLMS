package com.hotent.base.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.base.persistence.model.ResRole;

/**
 * 
 * <pre> 
 * 描述：角色资源分配 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface ResRoleDao extends Dao<String, ResRole> {
	
	List<ResRole> getAllByRoleId(String roleId);

	void removeByRoleAndSystem(String roleId, String systemId);
	
	/**
	 * 根据系统id获取关联的角色和URL资源。
	 * @param systemId
	 * @return
	 */
	List<ResRole> getUrlRoleBySystemId(String systemId);
	
	/**
	 * 根据系统Id获取资源和角色的映射关系。
	 * @param systemId
	 * @return
	 */
	List<ResRole> getResRoleBySystemId(String systemId);
}

package com.hotent.base.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.base.persistence.model.SysResource;

/**
 * 
 * <pre> 
 * 描述：子系统资源 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysResourceDao extends Dao<String, SysResource> {
	/**
	 * 根据子系统ID取定义对象。
	 * @param id
	 * @return
	 */
	List<SysResource> getBySystemId(String systemId);
	
	/**
	 * 根据角色和系统id获取资源。
	 * @param systemId
	 * @param roleId
	 * @return
	 */
	List<SysResource> getBySystemAndRole(String systemId, String roleId);
	
	/**
	 * 判断资源是否存在。
	 * @param resource
	 * @return
	 */
	boolean isExist(SysResource resource);
	
	/**
	 * 根据父ID获取下级节点。
	 * @param parentId
	 * @return
	 */
	List<SysResource> getByParentId(String parentId);
	
	/**
	 * 根据系统id和用户id获取资源列表。
	 * @param systemId	系统id
	 * @param userId	用户id
	 * @return
	 */
	List<SysResource> getBySystemAndUser(String systemId,String userId);

}

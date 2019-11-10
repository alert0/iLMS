package com.hotent.base.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.base.persistence.model.SysResource;
import com.hotent.org.api.model.IUser;

/**
 * 
 * <pre> 
 * 描述：子系统资源 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysResourceManager extends Manager<String, SysResource>{
	
	/**
	 * 根据子系统ID获取实体列表。
	 */
	List<SysResource> getBySystemId(String id);
	
	/**
	 * 根据资源ID获取资源对象，包括关联资源数据。
	 * @param id
	 * @return
	 */
	SysResource getByResId(String id);
	
	/**
	 * 根据资源id获取包括自身的下级数据。
	 * @param resId
	 * @return
	 */
	List<SysResource> getRecursionById(String resId);

	/**
	 * 根据系统和角色ID获取资源。
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
	 * 根据资源id递归删除资源数据。
	 * @param resId
	 */
	void removeByResId(String resId);
	
	/**
	 * 根据系统id和用户id获取资源。
	 * @param systemId
	 * @param userId
	 * @return
	 */
	List<SysResource> getBySystemAndUser(String systemId,String userId);
	
	/**
	 * 判断用户是否有资源权限
	 * @param systemId
	 * @param userId
	 * @param alias
	 * @return
	 * @author ZUOSL
	 * <p>Date: 2018年8月6日 下午1:17:37</p>
	 */
	boolean hasAlias(String systemId, IUser user, String alias);
	
	/**
	 * 获取操作请求类型
	 * @param systemId
	 * @param actionPath
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年10月31日 上午10:41:28
	 */
	String getReqActionResType(String systemId, String actionPath);
	
	/**
	 * 判断用户是否有请求操作权限
	 * @param systemId
	 * @param user
	 * @param actionPath
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年10月31日 上午10:42:44
	 */
	boolean hasReqAction(String systemId, IUser user, String actionPath);
}

package com.hotent.org.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.Role;

/**
 * 
 * <pre> 
 * 描述：角色管理 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:04
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface RoleManager extends Manager<String, Role>{
	
	
	   Role getByAlias(String alias);
	   
	    /**
	     * 根据用户ID获取角色列表
	     * @param userId
	     * @return
	     */
	    List<Role> getListByUserId(String userId);
	   
	    /**
	     * 根据用户账号获取角色列表
	     * @param userId
	     * @return
	     */
	    List<Role> getListByAccount(String account);
	    
	    /**
	     * 判断角色是否存在。
	     * @param role
	     * @return
	     */
	    boolean isRoleExist(Role role);
	
}

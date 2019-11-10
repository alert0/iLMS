package com.hotent.org.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.SysUserRel;

/**
 * 
 * <pre> 
 * 描述：用户关系 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-06-12 09:21:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysUserRelManager extends Manager<String, SysUserRel>{
	
	/**
	 * 获取typeId下的用户 ， 某汇报线下的所有用户
	 * @param typeId
	 * @return
	 */
	List<SysUserRel> getByTypeId(String typeId);
	
	/**
	 * 判断在typeId 中是否存在 
	 * @param typeId
	 * @param userId
	 * @param parentId
	 * @return
	 */
	SysUserRel getByUserIdAndParentId(String typeId, String userId,
			String parentId);
	
	/**
	 * 获取用户userId的上级
	 * @param userId
	 * @param level
	 * @param typeId
	 * @return
	 */
	List<SysUserRel> getSuperUser(String userId,String level, String typeId);
	
}

package com.hotent.org.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.org.persistence.model.SysUserRel;

/**
 * 
 * <pre> 
 * 描述：用户关系 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-06-12 09:21:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysUserRelDao extends Dao<String, SysUserRel> {

	List<SysUserRel> getByTypeId(String typeId);

	SysUserRel getByUserIdAndParentId(String typeId, String userId,
			String parentId);
	
	List<SysUserRel> getSuperUser(String userId, String level,String typeId);
}

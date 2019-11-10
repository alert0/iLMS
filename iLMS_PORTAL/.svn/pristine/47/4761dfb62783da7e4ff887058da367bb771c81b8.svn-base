package com.hotent.org.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.SysUserRelDao;
import com.hotent.org.persistence.manager.SysUserRelManager;
import com.hotent.org.persistence.model.SysUserRel;

/**
 * 
 * <pre> 
 * 描述：用户关系 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-06-12 09:21:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysUserRelManager")
public class SysUserRelManagerImpl extends AbstractManagerImpl<String, SysUserRel> implements SysUserRelManager{
	@Resource
	SysUserRelDao sysUserRelDao;
	@Override
	protected Dao<String, SysUserRel> getDao() {
		return sysUserRelDao;
	}
	@Override
	public List<SysUserRel> getByTypeId(String typeId) {
		return sysUserRelDao.getByTypeId(typeId);
	}
	@Override
	public SysUserRel getByUserIdAndParentId(String typeId, String userId,
			String parentId) {
		return sysUserRelDao.getByUserIdAndParentId(typeId, userId, parentId);
	}
	@Override
	public List<SysUserRel> getSuperUser(String userId,String level, String typeId) {
		return sysUserRelDao.getSuperUser(userId,level,typeId);
	}
}

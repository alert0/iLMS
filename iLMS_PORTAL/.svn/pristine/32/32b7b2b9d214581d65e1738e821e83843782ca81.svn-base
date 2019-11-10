package com.hotent.base.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.base.persistence.dao.RelResourceDao;
import com.hotent.base.persistence.model.RelResource;
import com.hotent.base.persistence.manager.RelResourceManager;

/**
 * 
 * <pre> 
 * 描述：关联资源 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("relResourceManager")
public class RelResourceManagerImpl extends AbstractManagerImpl<String, RelResource> implements RelResourceManager{
	@Resource
	RelResourceDao relResourceDao;
	@Override
	protected Dao<String, RelResource> getDao() {
		return relResourceDao;
	}
	
	@Override
	public List<RelResource> getByResourceId(String resId) {
		return relResourceDao.getByResourceId(resId);
	}

	@Override
	public void removeByResId(String resId) {
		relResourceDao.removeByResId(resId);
	}
	
}

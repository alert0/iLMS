package com.hotent.portal.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.portal.persistence.dao.SysIndexLayoutDao;
import com.hotent.portal.persistence.manager.SysIndexLayoutManager;
import com.hotent.portal.persistence.model.SysIndexLayout;

/**
 * 
 * <pre> 
 * 描述：sys_index_layout 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:15
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysIndexLayoutManager")
public class SysIndexLayoutManagerImpl extends AbstractManagerImpl<Long, SysIndexLayout> implements SysIndexLayoutManager{
	@Resource
	SysIndexLayoutDao sysIndexLayoutDao;
	@Override
	protected Dao<Long, SysIndexLayout> getDao() {
		return sysIndexLayoutDao;
	}
}

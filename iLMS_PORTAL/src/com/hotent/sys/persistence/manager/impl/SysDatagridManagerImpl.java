package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
 
import com.hotent.sys.persistence.dao.SysDatagridDao;
import com.hotent.sys.persistence.manager.SysDataGridManager;
import com.hotent.sys.persistence.model.SysDataGrid;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;
 

/**
 * 
 * <pre> 
 * 描述：sys_datagrid 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:14:19
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysDatagridManager")
public class SysDatagridManagerImpl extends AbstractManagerImpl<String, SysDataGrid> implements SysDataGridManager{
	@Resource
	SysDatagridDao sysDatagridDao;
	@Override
	protected Dao<String, SysDataGrid> getDao() {
		return sysDatagridDao;
	}
	@Override
	public SysDataGrid getByGridName(String gridName)
	{
		return sysDatagridDao.getByGridName(gridName);
	}
 
}

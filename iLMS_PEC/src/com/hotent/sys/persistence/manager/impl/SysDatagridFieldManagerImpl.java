package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysDatagridFieldDao;
import com.hotent.sys.persistence.manager.SysDataGridFieldManager;
import com.hotent.sys.persistence.model.SysDataGridField;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;
 

/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:12:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysDatagridFieldManager")
public class SysDatagridFieldManagerImpl extends AbstractManagerImpl<String, SysDataGridField> implements SysDataGridFieldManager{
	@Resource
	SysDatagridFieldDao sysDatagridFieldDao;
	@Override 
	protected Dao<String, SysDataGridField> getDao() {
		return sysDatagridFieldDao;
	}
 
	@Override
	public List<SysDataGridFieldInfo> queryListInfo(QueryFilter queryFilter)
	{
		return sysDatagridFieldDao.queryListInfo(queryFilter);
	}
	@Override
	public SysDataGridFieldInfo getInfo(String id)
	{
		return sysDatagridFieldDao.getInfo(id);
	}
 
}

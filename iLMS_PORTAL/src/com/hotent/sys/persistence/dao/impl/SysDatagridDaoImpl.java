package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
 
import com.hotent.sys.persistence.dao.SysDatagridDao;
import com.hotent.sys.persistence.model.SysDataGrid;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;




/**
 * 
 * <pre> 
 * 描述：sys_datagrid DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:14:19
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysDatagridDaoImpl extends MyBatisDaoImpl<String, SysDataGrid> implements SysDatagridDao{

    @Override
    public String getNamespace() {
        return SysDataGrid.class.getName();
    }
	@Override
	public SysDataGrid getByGridName(String gridName)
	{
		return (SysDataGrid)this.getOne("getByGridName",gridName);
	}
}


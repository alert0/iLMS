package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysDataGridFieldRightDao;
import com.hotent.sys.persistence.model.SysDataGrid;
import com.hotent.sys.persistence.model.SysDataGridFieldRight;

/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field_right DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-27 16:04:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysDataGridFieldRightDaoImpl extends MyBatisDaoImpl<String, SysDataGridFieldRight> implements SysDataGridFieldRightDao{

    @Override
    public String getNamespace() {
        return SysDataGridFieldRight.class.getName();
    }
    
    public boolean removeByGridId(String gridId)
    {
    	return this.sqlSessionTemplate.delete("removeByGridId", gridId)>-1;
    }
	
}


package com.hotent.sys.persistence.manager;

import java.util.HashMap;
import java.util.Hashtable;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysDataGrid;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;
 

/**
 * 
 * <pre> 
 * 描述：sys_datagrid 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:14:19
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysDataGridManager extends Manager<String, SysDataGrid>{
    public SysDataGrid getByGridName(String gridName);
 
}

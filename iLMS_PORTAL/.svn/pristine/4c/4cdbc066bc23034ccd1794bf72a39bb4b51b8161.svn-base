package com.hotent.sys.persistence.manager;

import java.util.List;
 

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysDataGridField;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;
 

/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:12:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysDataGridFieldManager extends Manager<String, SysDataGridField>{
    /**
     * 查询实体对象
     * @param queryFilter
     * @return 
     */
    public SysDataGridFieldInfo getInfo(String id);
 
    /**
     * 
     * @param queryFilter
     * @return 
     */
	public List<SysDataGridFieldInfo> queryListInfo(QueryFilter queryFilter);
}

package com.hotent.sys.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysDataGridFieldRight;

/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field_right DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-27 16:04:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysDataGridFieldRightDao extends Dao<String, SysDataGridFieldRight> {
    /**
     * 按实体ID删除对象
     * @param entityId 
     */
    public boolean removeByGridId(String gridId);    
}

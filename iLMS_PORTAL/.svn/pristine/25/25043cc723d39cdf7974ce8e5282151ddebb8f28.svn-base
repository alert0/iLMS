package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.sys.persistence.model.SysDataGridField;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;
 

/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:12:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysDatagridFieldDao extends Dao<String, SysDataGridField> {

	List<SysDataGridFieldInfo> queryListInfo(QueryFilter queryFilter);
	SysDataGridFieldInfo getInfo(String id);
	
}

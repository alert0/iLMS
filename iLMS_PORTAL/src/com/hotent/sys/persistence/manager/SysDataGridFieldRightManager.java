package com.hotent.sys.persistence.manager;

import net.sf.json.JSONObject;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysDataGridFieldRight;

/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field_right 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-27 16:04:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysDataGridFieldRightManager extends Manager<String, SysDataGridFieldRight>{
	public   JSONObject getEmptyItemJson(String field, String memo);
	public SysDataGridFieldRight getJsonObjectConvertRight(String gridId,Object obj);
	public boolean removeByGridId(String gridId);
	
	 
	 
 
}

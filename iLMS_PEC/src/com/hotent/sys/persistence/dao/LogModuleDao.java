package com.hotent.sys.persistence.dao;

import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.LogModule;

/**
 * 
 * <pre>
 * 描述：日志模块管理 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 16:57:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface LogModuleDao extends Dao<String, LogModule> {

	/**
	 * 根据模块名获取该模块是否启动的值
	 * @param module
	 * @return
	 */
	public Short getEnabledByName(String name);

	/**
	 * 获取模块名	
	 * @return
	 */
	public List<String> getNames();

	/**
	 * 设置是否启动
	 * @param ids
	 * @param flag 值
	 */
	public void setEnable(String[] ids, Short flag);
}

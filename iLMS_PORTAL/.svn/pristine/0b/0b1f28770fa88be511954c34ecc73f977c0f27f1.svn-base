package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.api.event.LogModuleCons;
import com.hotent.sys.persistence.model.LogModule;

/**
 * 
 * <pre>
 * 描述：日志模块管理 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 16:57:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface LogModuleManager extends Manager<String, LogModule> {
	
	/**
	 * 判断该模块是是启动的，默认不启动
	 * 
	 * @param module
	 * @return
	 */
	public Boolean isEnabled(String module);
	
	/**
	 * 获取模块名	
	 * @return
	 */
	public List<String> getNames();
	
	/**
	 * 插入数据，如果数据库已经存在了，则不再插入
	 * @param cons
	 * @param bdNames
	 */
	public void innsert(LogModuleCons[] cons, List<String> bdNames);
	
	/**
	 * 根据常量集合，插入所有数据
	 * @param cons
	 */
	public void innsertAll(LogModuleCons[] cons);

	/**
	 * 设置是否启动
	 * @param ids
	 * @param flag 值
	 */
	public void setEnable(String[] ids, Short flag);
	
}

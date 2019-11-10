package com.hotent.org.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.SysDemension;

/**
 * 
 * <pre> 
 * 描述：维度管理 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-19 15:30:09
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysDemensionManager extends Manager<String, SysDemension>{
	/**
	 * 根据Code取定义对象。
	 * @param code
	 * @return
	 */
	SysDemension getByCode(String code);
	
	/**
	 * 获取默认维度
	 * @return
	 */
	SysDemension getDefaultDemension();
	
	/**
	 * 设置默认维度
	 * @param id
	 */
	void setDefaultDemension(String id);
}

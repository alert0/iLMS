package com.hotent.bo.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bo.persistence.model.BoAttribute;

/**
 * 
 * <pre>
 *  
 * 描述：业务实体定义属性 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BoAttributeManager extends Manager<String, BoAttribute> {
	
	/**
	 * 根据实体ID获取BO属性。
	 * @param entId
	 * @return
	 */
	List<BoAttribute> getByEntId(String entId);

	/**
	 * 根据实体ID删除属性。
	 * @param entId
	 */
	void removeByEntId(String entId);
}

package com.hotent.form.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.FormBusSet;

/**
 * 
 * <pre> 
 * 描述：form_bus_set 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:54:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface FormBusSetManager extends Manager<String, FormBusSet>{

	FormBusSet getByFormKey(String formKey);
	
	/**
	 * 判断业务数据保存设置是否存在。
	 * @param formSet
	 * @return
	 */
	boolean isExist(FormBusSet formSet);
}

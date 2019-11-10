package com.hotent.form.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.FormBusSet;

/**
 * 
 * <pre> 
 * 描述：表单业务数据保存设置 DAO
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:54:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface FormBusSetDao extends Dao<String, FormBusSet> {

	FormBusSet getByFormKey(String formKey);
	
	/**
	 * 判断业务数据保存设置是否存在。
	 * @param formSet
	 * @return
	 */
	boolean isExist(FormBusSet formSet);
	
	/**
	 * 根据表单键删除业务数据设置。
	 * @param formKey
	 */
	void removeByFormKey(String formKey);
}

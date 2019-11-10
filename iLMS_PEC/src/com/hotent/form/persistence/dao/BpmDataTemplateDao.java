package com.hotent.form.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.BpmDataTemplate;

/**
 * 
 * <pre> 
 * 描述：业务数据模板 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-03-15 14:52:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmDataTemplateDao extends Dao<String, BpmDataTemplate> {
	
	BpmDataTemplate getByFormKey(String formKey);

	Integer getCountByFormKey(String formKey, String id);
	
	/**
	 * 根据表单key删除
	 * @param formKey
	 */
	void removeByFormKey(String formKey);
	
}

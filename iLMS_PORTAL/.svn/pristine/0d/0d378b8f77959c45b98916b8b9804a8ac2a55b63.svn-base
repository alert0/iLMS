package com.hotent.form.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.BpmDataTemplateDao;
import com.hotent.form.persistence.model.BpmDataTemplate;

/**
 * 
 * <pre> 
 * 描述：业务数据模板 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-03-15 14:52:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmDataTemplateDaoImpl extends MyBatisDaoImpl<String, BpmDataTemplate> implements BpmDataTemplateDao{

    @Override
    public String getNamespace() {
        return BpmDataTemplate.class.getName();
    }

	@Override
	public BpmDataTemplate getByFormKey(String formKey) {
		return this.getUnique("getByFormKey", formKey);
	}

	@Override
	public Integer getCountByFormKey(String formKey, String id) {
		return (Integer) this.getOne("getCountByFormKey", buildMap("formKey", formKey));
	}

	@Override
	public void removeByFormKey(String formKey) {
		this.deleteByKey("removeByFormKey", formKey);
	}
	
}


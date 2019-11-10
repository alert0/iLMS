package com.hotent.form.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.FormBusSetDao;
import com.hotent.form.persistence.model.FormBusSet;

/**
 * 
 * <pre> 
 * 描述：表单业务数据保存设置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:54:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class FormBusSetDaoImpl extends MyBatisDaoImpl<String, FormBusSet> implements FormBusSetDao{

    @Override
    public String getNamespace() {
        return FormBusSet.class.getName();
    }

    
    
	@Override
	public FormBusSet getByFormKey(String formKey) {
		return getUnique("getByFormKey", formKey);
	}



	@Override
	public boolean isExist(FormBusSet formSet) {
		Integer rtn=(Integer) this.getOne("isExist", formSet);
		return rtn>0;
	}



	@Override
	public void removeByFormKey(String formKey) {
		this.deleteByKey("removeByFormKey", formKey);
	}
	
}


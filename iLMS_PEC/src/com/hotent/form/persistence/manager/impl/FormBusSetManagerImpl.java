package com.hotent.form.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.form.persistence.dao.FormBusSetDao;
import com.hotent.form.persistence.model.FormBusSet;
import com.hotent.form.persistence.manager.FormBusSetManager;

/**
 * 
 * <pre> 
 * 描述：form_bus_set 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:54:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("formBusSetManager")
public class FormBusSetManagerImpl extends AbstractManagerImpl<String, FormBusSet> implements FormBusSetManager{
	@Resource
	FormBusSetDao formBusSetDao;
	@Override
	protected Dao<String, FormBusSet> getDao() {
		return formBusSetDao;
	}
	@Override
	public FormBusSet getByFormKey(String formKey) {
		return formBusSetDao.getByFormKey(formKey);
	}
	
	@Override
	public boolean isExist(FormBusSet formSet) {
		return formBusSetDao.isExist(formSet);
	}
}

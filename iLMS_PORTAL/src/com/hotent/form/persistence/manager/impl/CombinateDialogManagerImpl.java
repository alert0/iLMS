package com.hotent.form.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.form.persistence.dao.CombinateDialogDao;
import com.hotent.form.persistence.model.CombinateDialog;
import com.hotent.form.persistence.model.CustomDialog;
import com.hotent.form.persistence.manager.CombinateDialogManager;

/**
 * 
 * <pre>
 * 描述：combinate_dialog 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:liyijun@jee-soft.cn
 * 日期:2016-03-04 15:48:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("combinateDialogManager")
public class CombinateDialogManagerImpl extends AbstractManagerImpl<String, CombinateDialog> implements CombinateDialogManager {
	@Resource
	CombinateDialogDao combinateDialogDao;

	@Override
	protected Dao<String, CombinateDialog> getDao() {
		return combinateDialogDao;
	}

	@Override
	public CombinateDialog getByAlias(String alias) {
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("alias_", alias, QueryOP.EQUAL);
		List<CombinateDialog> combinateDialogs = query(queryFilter);
		if (combinateDialogs == null || combinateDialogs.isEmpty())
			return null;
		return combinateDialogs.get(0);
	}
}

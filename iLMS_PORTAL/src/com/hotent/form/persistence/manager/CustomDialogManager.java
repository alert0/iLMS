package com.hotent.form.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.CustomDialog;

public interface CustomDialogManager extends Manager<String, CustomDialog> {

	public CustomDialog getByAlias(String alias);

	/**
	 * 
	 * @param customDialog
	 * @param pid
	 *            :找出id=pid的子节点
	 * @param dsType
	 * @return List
	 * @exception
	 * @since 1.0.0
	 */
	public List geTreetData(CustomDialog customDialog, Map<String, Object> param, String dbType);

	public List getListData(CustomDialog customDialog, Map<String, Object> param, String dbType);
}

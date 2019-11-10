package com.hotent.form.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.CustomDialog;


public interface CustomDialogDao extends Dao<String, CustomDialog> {

	CustomDialog getByAlias(String alias);
}

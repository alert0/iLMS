package com.hotent.form.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.CustomDialogDao;
import com.hotent.form.persistence.model.CustomDialog;


@Repository

public class CustomDialogDaoImpl extends MyBatisDaoImpl<String, CustomDialog> implements CustomDialogDao{

    @Override
    public String getNamespace() {
        return CustomDialog.class.getName();
    }

	@Override
	public CustomDialog getByAlias(String alias) {
		return this.getUnique("getByAlias", alias);
	}
	
}


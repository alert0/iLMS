package com.hotent.form.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.CustomQueryDao;
import com.hotent.form.persistence.model.CustomQuery;


@Repository

public class CustomQueryDaoImpl extends MyBatisDaoImpl<String, CustomQuery> implements CustomQueryDao{

    @Override
    public String getNamespace() {
        return CustomQuery.class.getName();
    }
	
}


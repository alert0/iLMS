package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysAcceptIpDao;
import com.hotent.sys.persistence.model.SysAcceptIp;


@Repository

public class SysAcceptIpDaoImpl extends MyBatisDaoImpl<String, SysAcceptIp> implements SysAcceptIpDao{

    @Override
    public String getNamespace() {
        return SysAcceptIp.class.getName();
    }
	
}


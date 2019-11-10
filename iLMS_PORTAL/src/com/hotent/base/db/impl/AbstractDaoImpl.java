package com.hotent.base.db.impl;


import java.io.Serializable;

import javax.annotation.Resource;

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.api.IdGenerator;

/**
 *
 * @author csx
 */
public abstract class AbstractDaoImpl<PK extends Serializable,T extends Serializable> implements Dao<PK,T>{
	  @Resource
	  protected IdGenerator idGenerator;
}

package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.IdentityDao;
import com.hotent.sys.persistence.model.Identity;


@Repository

public class IdentityDaoImpl extends MyBatisDaoImpl<String, Identity> implements IdentityDao{

	
    @Override
    public String getNamespace() {
        return Identity.class.getName();
    }
    

	/**
	 * 判读流水号别名是否已经存在
	 * @param id  id为null 表明是新增的流水号，否则为更新流水号
	 * @param alias
	 * @return
	 */
	@Override
	public boolean isAliasExisted(String id, String alias) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		params.put("alias", alias);
		Integer sn=(Integer)getOne("isAliasExisted", params);
		return sn>0?true:false;
	}

	@Override
	public Identity getByAlias(String alias) {
		return this.getUnique("getByAlias", alias); 
	}

	@Override
	public int updByAlias(Identity identity) {
		return this.updateByKey("updByAlias", identity);
	}
}

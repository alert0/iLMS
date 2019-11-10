package com.hotent.sys.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.Identity;


public interface IdentityDao extends Dao<String, Identity> {


	/**
	 * 判读流水号别名是否已经存在
	 * @param id  id为null 表明是新增的流水号，否则为更新流水号
	 * @param alias
	 * @return
	 */
	boolean isAliasExisted(String id, String alias);

	/**
	 * 根据别名获取流水号数据（数据库锁定了对应的行数据）
	 * @param alias
	 * @return
	 */
	Identity getByAlias(String alias);
	
	
	/**
	 * 根据流程别名 。
	 * @param identity 
	 * void
	 */
	int updByAlias(Identity identity);
}

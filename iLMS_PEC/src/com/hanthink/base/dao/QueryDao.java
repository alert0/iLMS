package com.hanthink.base.dao;

import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc		: 通用查询DAO
 * @FileName	: QueryDao.java
 * @CreateOn	: 2018年11月25日 下午8:04:46
 * @author 		: ZUOSL
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
public interface QueryDao {

	/**
	 * 获取序列值
	 * @param sequenceName
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午8:17:02
	 */
	public String getSequenceNextVal(String sequenceName);
	
	/**
	 * 简单查询-直接查询
	 * @param nameSpace SQL命名空间
	 * @param sqlKey SQL id
	 * @param params 查询参数
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午8:41:53
	 */
	public List<Map<String, Object>> queryBySimpleQuery(String nameSpace, String sqlKey, Map<String, Object> params);
	
	/**
	 * 简单分页查询-直接查询
	 * @param nameSpace SQL命名空间
	 * @param sqlKey  SQL id
	 * @param params  查询参数
	 * @param page  分页对象
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午8:44:48
	 */
	public PageList<Map<String, Object>> queryBySimpleQuery(String nameSpace, String sqlKey, Map<String, Object> params, Page page);
	
}

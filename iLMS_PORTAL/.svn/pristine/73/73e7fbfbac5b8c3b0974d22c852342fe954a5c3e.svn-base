package com.hanthink.base.manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface AbstractManager<PK extends Serializable, T extends Serializable> extends Manager<PK, T> {
	
	/**
	 * 简单查询-直接查询
	 * @param namespace SQL命名空间
	 * @param sqlKey SQL id
	 * @param params 查询参数
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午8:41:53
	 */
	public List<Map<String, Object>> queryBySimpleQuery(String namespace, String sqlKey, Map<String, Object> params);
	
	/**
	 * 简单查询-直接查询
	 * @param sqlKey SQL id
	 * @param params 查询参数
	 * @return
	 * @author ZUOSL		
	 * @DATE	2018年11月25日 下午9:22:58
	 */
	public List<Map<String, Object>> queryBySimpleQuery(String sqlKey, Map<String, Object> params);
	
	/**
	 * 简单分页查询-直接查询
	 * @param namespace SQL命名空间
	 * @param sqlKey  SQL id
	 * @param params  查询参数
	 * @param page  分页对象
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午8:44:48
	 */
	public PageList<Map<String, Object>> queryBySimpleQuery(String namespace, String sqlKey, Map<String, Object> params, Page page);
	
	/**
	 * 简单分页查询-直接查询
	 * @param sqlKey  SQL id
	 * @param params  查询参数
	 * @param page  分页对象
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午9:44:04
	 */
	public PageList<Map<String, Object>> queryBySimpleQuery(String sqlKey, Map<String, Object> params, Page page);

}

package com.hanthink.base.manager.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hanthink.base.dao.QueryDao;
import com.hanthink.base.dao.TableDataLogDao;
import com.hanthink.base.manager.AbstractManager;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @Desc    : 扩展实现Manager(含记录数据日志方法)
 * @FileName: AbstractManagerImpl.java 
 * @CreateOn: 2018-8-30 上午11:38:40
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2017-8-30	V1.0		zuosl		新建
 * 
 *
 */
public abstract class AbstractManagerImpl<PK extends Serializable, T extends Serializable>  
				extends com.hotent.base.manager.impl.AbstractManagerImpl<PK, T> implements AbstractManager<PK, T>{
	
	@Resource
	protected TableDataLogDao tableDataLogDao;
	
	@Resource
	protected QueryDao queryDao;
	

	@Override
	public List<Map<String, Object>> queryBySimpleQuery(String namespace, String sqlKey, Map<String, Object> params){
		return queryDao.queryBySimpleQuery(namespace, sqlKey, params);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String, Object>> queryBySimpleQuery(String sqlKey, Map<String, Object> params){
		if(getDao() instanceof MyBatisDaoImpl){
			MyBatisDaoImpl dao = (MyBatisDaoImpl) getDao();
			return queryDao.queryBySimpleQuery(dao.getNamespace(), sqlKey, params);
		}else{
			throw new RuntimeException("namespace error!");
		}
	}
	
	@Override
	public PageList<Map<String, Object>> queryBySimpleQuery(String namespace, String sqlKey, Map<String, Object> params, Page page){
		return queryDao.queryBySimpleQuery(namespace, sqlKey, params, page);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public PageList<Map<String, Object>> queryBySimpleQuery(String sqlKey, Map<String, Object> params, Page page){
		if(getDao() instanceof MyBatisDaoImpl){
			MyBatisDaoImpl dao = (MyBatisDaoImpl) getDao();
			return queryDao.queryBySimpleQuery(dao.getNamespace(), sqlKey, params, page);
		}else{
			throw new RuntimeException("namespace error!");
		}
	}

}

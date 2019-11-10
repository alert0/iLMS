package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.QueryMetafieldDao;
import com.hotent.sys.persistence.model.QueryMetafield;
import com.hotent.sys.persistence.manager.QueryMetafieldManager;

/**
 * 
 * <pre> 
 * 描述：sys_query_metafield 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 16:42:01
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("queryMetafieldManager")
public class QueryMetafieldManagerImpl extends AbstractManagerImpl<String, QueryMetafield> implements QueryMetafieldManager{
	@Resource
	QueryMetafieldDao queryMetafieldDao;
	@Override
	protected Dao<String, QueryMetafield> getDao() {
		return queryMetafieldDao;
	}
	
	@Override
	public List<QueryMetafield> getBySqlId(String sqlId){
		return queryMetafieldDao.getBySqlId(sqlId);
	}
	
	@Override
	public void removeBySqlId(String sqlId){
		queryMetafieldDao.removeBySqlId(sqlId);
	}
}

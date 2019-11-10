package com.hotent.sys.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.QueryViewDao;
import com.hotent.sys.persistence.model.QueryView;

/**
 * 
 * <pre> 
 * 描述：sys_query_view DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:26:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class QueryViewDaoImpl extends MyBatisDaoImpl<String, QueryView> implements QueryViewDao{

    @Override
    public String getNamespace() {
        return QueryView.class.getName();
    }
	
    @Override
    public List<QueryView> getBySqlAlias(String sqlAlias){
    	return getByKey("getBySqlAlias", buildMap("sqlAlias", sqlAlias));
    }
    
    @Override
    public QueryView getBySqlAliasAndAlias(String sqlAlias,String alias){
    	Map<String,Object> param = buildMap("sqlAlias", sqlAlias);
    	param.put("alias", alias);
    	return getUnique("getBySqlAliasAndAlias", param);
    }
    
    @Override
    public void removeBySqlAlias(String sqlAlias){
    	deleteByKey("removeBySqlAlias", buildMap("sqlAlias", sqlAlias));
    }
}


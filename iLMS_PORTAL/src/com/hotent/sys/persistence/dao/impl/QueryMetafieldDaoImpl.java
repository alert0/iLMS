package com.hotent.sys.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.QueryMetafieldDao;
import com.hotent.sys.persistence.model.QueryMetafield;

/**
 * 
 * <pre> 
 * 描述：sys_query_metafield DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 16:42:01
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class QueryMetafieldDaoImpl extends MyBatisDaoImpl<String, QueryMetafield> implements QueryMetafieldDao{

    @Override
    public String getNamespace() {
        return QueryMetafield.class.getName();
    }
	
    @Override
    public List<QueryMetafield> getBySqlId(String sqlId){
    	return getByKey("getBySqlId", buildMap("sqlId", sqlId));
    }
    
    @Override
    public void removeBySqlId(String sqlId){
    	deleteByKey("removeBySqlId", buildMap("sqlId", sqlId));
    }
}


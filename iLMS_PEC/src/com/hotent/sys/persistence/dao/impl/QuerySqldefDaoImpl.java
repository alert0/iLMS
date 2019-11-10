package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.QuerySqldefDao;
import com.hotent.sys.persistence.model.QuerySqldef;

/**
 * 
 * <pre> 
 * 描述：sys_query_sqldef DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-17 16:44:25
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class QuerySqldefDaoImpl extends MyBatisDaoImpl<String, QuerySqldef> implements QuerySqldefDao{

    @Override
    public String getNamespace() {
        return QuerySqldef.class.getName();
    }
	
}


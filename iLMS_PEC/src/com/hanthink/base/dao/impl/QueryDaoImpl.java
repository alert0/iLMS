package com.hanthink.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hanthink.base.dao.QueryDao;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc		: 通用查询DAO实现类
 * @FileName	: QueryDaoImpl.java
 * @CreateOn	: 2018年11月25日 下午8:05:16
 * @author 		: ZUOSL
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
@Repository
public class QueryDaoImpl implements QueryDao {
	
	private static String DEFAULT_NAME_SPACE = "com.hanthink.base.CommQuery";
	
	@Resource
    protected SqlSessionTemplate sqlSessionTemplate;

	@Override
	public String getSequenceNextVal(String sequenceName) {
		return sqlSessionTemplate.selectOne(DEFAULT_NAME_SPACE + ".select_sequencenextval", sequenceName);
	}

	@Override
	public List<Map<String, Object>> queryBySimpleQuery(String nameSpace, String sqlKey, Map<String, Object> params) {
		return sqlSessionTemplate.selectList(nameSpace + "." + sqlKey, params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageList queryBySimpleQuery(String nameSpace, String sqlKey, Map<String, Object> params, Page page) {
		RowBounds r = new RowBounds(page.getStartIndex(), page.getPageSize());
		return (PageList) sqlSessionTemplate.selectList(nameSpace + "." + sqlKey, params, r);
	}
	
}

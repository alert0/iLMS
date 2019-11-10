package com.hotent.base.db.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageResult;

/**
 * 通用查询操作。
 * 
 * <pre>
 * 构建组：x5-base-db
 * 作者：lyj
 * 邮箱:liyj@jee-soft.cn
 * 日期:2014-7-9-上午11:15:52
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 * 
 * @param <T>
 */
public class CommonDao<T> {
	@Resource
	protected SqlSessionTemplate sqlSessionTemplate;

	private static final String NAME_SPACE = "com.hotent.sql.common"; // mybatis命名空间

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 *            void
	 */
	public void execute(String sql) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		String key = getNameSpace("execute");
		sqlSessionTemplate.update(key, map);
	}

	/**
	 * 查询列表
	 * 
	 * @param sql
	 * @return List
	 */
	public List query(String sql) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		String key = getNameSpace("query");
		return sqlSessionTemplate.selectList(key, map);
	}

	/**
	 * 查询分页列表
	 * 
	 * @param sql
	 * @return List
	 */
	public PageList query(String sql, Page page) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		String key = getNameSpace("query");
		return (PageList) sqlSessionTemplate.selectList(key, map,
				new RowBounds(page.getStartIndex(), page.getPageSize()));
	}

	// ==================start by ouxb=====================

	/**
	 * 合并mybatis 命名空间
	 * 
	 * @param sqlKey
	 * @return
	 */
	private String getNameSpace(String sqlKey) {
		return NAME_SPACE + "." + sqlKey;
	}

	/**
	 * 自定义SQL,分页由系统,可自动过滤排序grid规则，详细运用请看：com.hotent.demo.querysql.controller
	 * 
	 * <pre>
	 * QueryFilter queryFilter = getQuerFilter(request);
	 * String sql = &quot;SELECT * FROM xog_user WHERE 1=#{test} order by user_id_ &quot;;
	 * Map&lt;String, Object&gt; params = new HashMap&lt;String, Object&gt;();
	 * params.put(&quot;test&quot;, 1);
	 * List list = commonDao.queryForList(sql, queryFilter, params);
	 * return new PageJson(list);
	 * </pre>
	 * 
	 * @param sql
	 * @param queryFilter
	 * @param params
	 * @return
	 */
	public List<T> queryForList(String sql, QueryFilter queryFilter, Map<String, Object> params) {
		Assert.notNull(sql);
		if (CollectionUtils.isEmpty(params)) {
			params = new HashMap<String, Object>();
		}
		params.put("sql", sql);
		Map<String, Object> filter = this.parseGridFilter(queryFilter);
		params.putAll(filter);
		return sqlSessionTemplate.selectList(this.getNameSpace("queryFormList"), params);
	}

	/**
	 * 自定义SQL,分页由系统,可自动过滤排序grid规则，详细运用请看：com.hotent.demo.querysql.controller
	 * 
	 * <pre>
	 * QueryFilter queryFilter = getQuerFilter(request);
	 * String sql = &quot;SELECT * FROM xog_user WHERE 1=#{test} order by user_id_ &quot;;
	 * Map&lt;String, Object&gt; params = new HashMap&lt;String, Object&gt;();
	 * params.put(&quot;test&quot;, 1);
	 * PageList list = commonDao.queryForListPage(sql, queryFilter, params);
	 * return new PageJson(list);
	 * </pre>
	 * 
	 * @param sql
	 * @param queryFilter
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageList<T> queryForListPage(String sql, QueryFilter queryFilter, Map<String, Object> params) {
		Assert.notNull(sql);
		if (CollectionUtils.isEmpty(params)) {
			params = new HashMap<String, Object>();
		}
		params.put("sql", sql);
		Map<String, Object> filter = this.parseGridFilter(queryFilter);
		params.putAll(filter);
		DefaultPage p = new DefaultPage(new RowBounds(queryFilter.getPage().getStartIndex(), queryFilter.getPage().getPageSize()));
		List<Object> selectList = sqlSessionTemplate.selectList(this.getNameSpace("queryFormList"), params, p);
		return (PageList) selectList ;
	}

	/**
	 * 
	 * 自定义SQL 自分页,不可自动过滤排序grid规则，需要用户自己动手构建grid过滤排序规则，
	 * </br>详细运用请看：com.hotent.demo.querysql.controller
	 * </br>因为用户自定义分页的话，会造出Grid的过滤规则和统计规则出错,即自定义分页的语句必须在过滤条件的后面 </br>例如：select *
	 * from (select * from xog_user limit 0,10 ) where sex like '%男%' 和
	 * </br>select * from (select * from xog_user where sex like '%男%' limit
	 * 0,10 )的两种不同 结果集
	 * 
	 * <pre>
	 * <h1>用户自分页可以优化查询语句的执行效率，例如
	 * </br>select * from (select t.*,rownum as rn from xog_user t) a where a.rn >=1 and a.rn<=10 和
	 * </br>select * from (select t.*,rownum as rn from xog_user t where rownum <=10) a where a.rn>=1
	 * </br>后者要比前者快多
	 * </h1>
	 * <h1>mysql：</h1>
	 * QueryFilter queryFilter = getQuerFilter(request);
	 * int startIndex = queryFilter.getPage().getStartIndex();
	 * int pageSize = queryFilter.getPage().getPageSize();
	 * Map&lt;String, Object&gt; params = commonDao.parseGridFilter(queryFilter);
	 * params.put(&quot;test&quot;, 1); // 自定义的参数
	 * params.put(&quot;startIndex&quot;, startIndex); // 设置分页参数
	 * params.put(&quot;pageSize&quot;, pageSize); // 设置分页参数
	 * String whereSql = (String) params.get(&quot;whereSql&quot;); // 字段过滤规则
	 * String orderBySql = (String) params.get(&quot;orderBySql&quot;); // 字段排序规则
	 * 
	 * StringBuffer sbf = new StringBuffer(&quot;SELECT * FROM xog_user&quot;);
	 * sbf.append(&quot; WHERE 1=#{test} &quot;);
	 * if (StringUtil.isNotEmpty(whereSql)) {
	 * 	sbf.append(&quot; and &quot;).append(whereSql); // grid过滤的固定写法
	 * }
	 * if (StringUtil.isNotEmpty(orderBySql)) {
	 * 	sbf.append(&quot; ORDER BY &quot;).append(orderBySql); // grid过滤的固定写法
	 * } else {
	 * 	sbf.append(&quot; ORDER BY user_id_ &quot;);
	 * }
	 * String querySql = sbf.toString() + &quot; limit #{startIndex},#{pageSize} &quot;;
	 * String countSql = sbf.toString();
	 * 
	 * PageList list = commonDao.queryByCusPage(querySql, countSql, params,
	 * 		startIndex, pageSize);
	 * return new PageJson(list);
	 * 
	 * <h1>oracle:</h1>
	 * 
	 * QueryFilter queryFilter = getQuerFilter(request);
	 * int startIndex = queryFilter.getPage().getStartIndex();
	 * int pageSize = queryFilter.getPage().getPageSize();
	 * Map&lt;String, Object&gt; params = commonDao.parseGridFilter(queryFilter);
	 * params.put(&quot;test&quot;, 1); // 自定义的参数
	 * params.put(&quot;startIndex&quot;, startIndex); // 设置分页参数
	 * params.put(&quot;pageSize&quot;, pageSize); // 设置分页参数
	 * String whereSql = (String) params.get(&quot;whereSql&quot;); // 字段过滤规则
	 * String orderBySql = (String) params.get(&quot;orderBySql&quot;); // 字段排序规则
	 * 
	 * StringBuffer sbf = new StringBuffer("SELECT * FROM ( SELECT T.*,ROWNUM AS RN FROM SYS_USER T  ");
	 * sbf.append(&quot; WHERE 1=#{test} &quot;);
	 * if (StringUtil.isNotEmpty(whereSql)) {
	 * 	sbf.append(&quot; and &quot;).append(whereSql); // grid过滤的固定写法
	 * }
	 * if (StringUtil.isNotEmpty(orderBySql)) {
	 * 	sbf.append(&quot; ORDER BY &quot;).append(orderBySql); // grid过滤的固定写法
	 * } else {
	 * 	sbf.append(&quot; ORDER BY user_id_ &quot;);
	 * }
	 * String querySql =  sbf.toString() + " ROWNUM <=10) A WHERE A.RN>=1  ";
	 * String countSql = sbf.toString() + ")";
	 * 
	 * PageList list = commonDao.queryByCusPage(querySql, countSql, params,
	 * 		startIndex, pageSize);
	 * return new PageJson(list);
	 * </pre>
	 * 
	 * @param sql
	 * @param countSql
	 * @param queryFilter
	 * @param params
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageList queryByCusPage(String sql, String countSql, Map<String, Object> params, Integer startIndex, Integer pageSize) {
		List<T> list = this.queryByCusPage(sql, params);
		Integer tc = this.getCount(countSql, params);
		return new PageList(list, new PageResult(startIndex, pageSize, tc));
	}

	/**
	 * 自定义SQL 非分页,不可自动过滤grid的操作规则
	 * 
	 * @param sql
	 * @param queryFilter
	 * @param params
	 * @return
	 */
	private List<T> queryByCusPage(String sql, Map<String, Object> params) {
		Assert.notNull(sql);
		if (CollectionUtils.isEmpty(params)) {
			params = new HashMap<String, Object>();
		}
		params.put("sql", sql);
		return sqlSessionTemplate.selectList(this.getNameSpace("query"), params);
	}

	/**
	 * 返回统计数据
	 * 
	 * @param sql
	 * @param queryFilter
	 * @param params
	 * @return
	 */
	private Integer getCount(String sql, Map<String, Object> params) {
		Assert.notNull(sql);
		if (CollectionUtils.isEmpty(params)) {
			params = new HashMap<String, Object>();
		}
		params.put("sql", sql);
		return sqlSessionTemplate.selectOne(this.getNameSpace("getCount"), params);
	}

	/**
	 * 解析easyui grid 过滤、排序规则
	 * 
	 * @param queryFilter
	 * @return
	 */
	public Map<String, Object> parseGridFilter(QueryFilter queryFilter) {
		Map<String, Object> params = queryFilter.getParams();
		// 构建动态条件SQL
		String dynamicWhereSql = queryFilter.getFieldLogic().getSql();
		if (StringUtil.isNotEmpty(dynamicWhereSql)) {
			params.put("whereSql", dynamicWhereSql);
		}
		// 构建排序SQL
		if (queryFilter.getFieldSortList().size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (FieldSort fieldSort : queryFilter.getFieldSortList()) {
				sb.append(fieldSort.getField()).append(" ").append(fieldSort.getDirection()).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			params.put("orderBySql", sb.toString());
		}
		return params;
	}

}

package com.hotent.sys.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.QueryView;

/**
 * 
 * <pre> 
 * 描述：sys_query_view 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:26:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface QueryViewManager extends Manager<String, QueryView>{

	List<QueryView> getBySqlAlias(String sqlAlias);

	void removeBySqlAlias(String sqlAlias);

	QueryView getBySqlAliasAndAlias(String sqlAlias, String alias);
	
	/**
	 * <pre>
	 * 获取SQL，利用sqlbuilder工具，工作重点是拼装sqlbuildermodel中
	 * 1 拼装select语句 
	 * 2 拼装 条件字段
	 * 3 拼装 排序字段
	 * </pre>
	 * @param queryView
	 * @param queryParams
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	String getShowSql(QueryView queryView, Map<String, Object> queryParams);
	
	/**
	 * <pre>
	 * 这里做的操作是拼装显示字段，因为上个sql是查询出所有字段的
	 * 主要是为了虚拟列字段
	 * </pre>
	 * @param queryView
	 * @param list 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void handleShowData(QueryView queryView, List list);
}

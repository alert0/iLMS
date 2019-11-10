package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.QueryView;

/**
 * 
 * <pre> 
 * 描述：sys_query_view DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:26:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface QueryViewDao extends Dao<String, QueryView> {

	List<QueryView> getBySqlAlias(String sqlAlias);

	void removeBySqlAlias(String sqlAlias);

	QueryView getBySqlAliasAndAlias(String sqlAlias, String alias);
}

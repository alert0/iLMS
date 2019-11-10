package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.QuerySqldef;

import net.sf.json.JSONObject;

/**
 * 
 * <pre> 
 * 描述：sys_query_sqldef 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:28:47
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface QuerySqldefManager extends Manager<String, QuerySqldef>{
	
	/**
	 * 验证sql是否正确，为防止恶意sql修改，验证完会自动回滚
	 * @param dsName	：数据源别名
	 * @param sql 	：sql语句
	 * void
	 * @return  
	 * @exception 
	 * @since  1.0.0
	 */
	JSONObject checkSql(String dsName, String sql);
	
	void save(QuerySqldef querySqldef);

	QuerySqldef getByAlias(String alias);
	
	/**
	 * 导出自定义查询列表。
	 * @param idList
	 * @return
	 */
	String export(List<String> idList) throws Exception;
	
	/**
	 * 导入流程定义。
	 * @param path
	 */
	void importDef(String path);
	
}

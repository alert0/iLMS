package com.hotent.base.api.db;

import java.sql.SQLException;
import java.util.List;

import com.hotent.base.api.Page;
import com.hotent.base.api.db.model.Table;

/**
 * 视图接口定义类。
 * <p>
 * 1.获取数据库视图列表数据。
 * <p>
 * 2.获取某个视图的具体信息，数据保存到Table中。
 * 
 * <pre>
 * 构建组：x5-base-api
 * 作者：hugh zhuang
 * 邮箱:zhuangxh@jee-soft.cn
 * 日期:2014-01-22-上午11:35:40
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 * 
 */
public interface IViewOperator extends IDbType{
	

	/**
	 * 创建或者替换视图
	 * 
	 * @param viewName
	 * @param sql
	 * @throws Exception
	 */
	public void createOrRep(String viewName, String sql) throws Exception;

	/**
	 * 使用模糊匹配，获取系统视图名称。
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List<String> getViews(String viewName) throws  Exception;

	/**
	 * 使用模糊匹配，获取系统视图名称。
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> getViews(String viewName, Page page)
			throws SQLException, Exception;

	/**
	 * 根据视图名称，使用精确匹配，获取视图详细信息。
	 * 
	 * @param viewName
	 * @return
	 */
	public Table getModelByViewName(String viewName) throws SQLException;

	/**
	 * 根据视图名，使用模糊匹配，称获取视图详细信息。
	 * 
	 * @param viewName
	 *            视图名称
	 * @param page
	 *            分页
	 * @return
	 */
	public List<Table> getViewsByName(String viewName, Page page)
			throws Exception;

}

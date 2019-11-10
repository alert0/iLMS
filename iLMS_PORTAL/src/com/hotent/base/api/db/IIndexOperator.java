package com.hotent.base.api.db;

import java.sql.SQLException;
import java.util.List;

import com.hotent.base.api.db.model.Index;

/**
 * 索引操作接口
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
public interface IIndexOperator extends IDbType {

	void createIndex(Index index) throws SQLException;

	/**
	 * 根据表名和索引名，删除表名和索引名对应的索引.所有实现使用精确匹配方式。
	 * 
	 * @param tableName
	 *            表名
	 * @param indexName
	 *            索引名
	 */
	void dropIndex(String tableName, String indexName);

	/**
	 * 根据表名和索引名，取得表名和索引名对应的索引.所有实现使用精确匹配方式。
	 * 
	 * @param tableName
	 *            表名
	 * @param indexName
	 *            索引名
	 * @return
	 * @throws SQLException
	 */
	Index getIndex(String tableName,String indexName) throws SQLException;


	List<Index> getIndexByFuzzyMatch(String indexName) throws SQLException;

	/**
	 * 根据表名，取得表名对应的索引列表.所有实现使用精确匹配方式。
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 * @throws SQLException
	 */
	List<Index> getIndexsByTable(String tableName) throws SQLException;

	/**
	 * 重建索引
	 * 
	 * @param tableName
	 *            表名
	 * @param indexName
	 *            索引名
	 */
	void rebuildIndex(String tableName, String indexName);

}

package com.hotent.base.db.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.hotent.base.api.Page;
import com.hotent.base.api.db.IViewOperator;
import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.db.model.Table;
import com.hotent.base.db.mybatis.Dialect;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageResult;
import com.hotent.base.db.table.factory.DatabaseFactory;
import com.hotent.base.db.table.model.DefaultColumn;
import com.hotent.base.db.table.model.DefaultTable;

/**
 * 获取视图信息基类。
 * 
 * <pre>
 * 构建组：x5-base-db
 * 作者：hugh zhuang
 * 邮箱:zhuangxh@jee-soft.cn
 * 日期:2014-01-25-上午11:35:40
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 * 
 */
public abstract class BaseViewOperator extends BaseDbType implements  IViewOperator{
	
	/**
	 * 获取数据类型
	 * 
	 * @param type
	 *            数据类型
	 * @return
	 */
	public abstract String getType(String type);

	/**
	 * 根据视图名称获取model。
	 * 
	 * @param viewName
	 *            视图名
	 * @return
	 * @throws SQLException
	 */
	public Table getModelByViewName(String viewName) throws SQLException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();

		Statement stmt = null;
		ResultSet rs = null;

		Table table = new DefaultTable();
		table.setTableName(viewName);
		table.setComment(viewName);

		try {
			stmt = (Statement) conn.createStatement();
			stmt.setMaxRows(1);
			rs = stmt.executeQuery("select * from " + viewName); 
			ResultSetMetaData metadata = rs.getMetaData();
			int count = metadata.getColumnCount();
			// 从第二条记录开始
			for (int i = 1; i <= count; i++) {
				Column column = new DefaultColumn();
				String columnName = metadata.getColumnName(i);
				String typeName = metadata.getColumnTypeName(i);
				String dataType = getType(typeName);
				column.setFieldName(columnName);
				column.setColumnType(dataType);
				column.setComment(columnName);
				table.addColumn(column);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return table;
	}

	/**
	 * 获取查询的列表
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param page
	 *            分页
	 * @param elementType
	 *            返回实体
	 * @param dbType
	 *            数据库类型
	 * @return
	 * @throws Exception
	 */
	protected <T> List<T> getForList(String sql, Page page,
			Class<T> elementType, String dbType) throws Exception {
		if (page != null) {
			int pageSize = page.getPageSize();
			int offset = page.getStartIndex();
			Dialect dialect = DatabaseFactory.getDialect(dbType);
			String pageSql = dialect.getLimitString(sql, offset, pageSize);
			String totalSql = dialect.getCountString(sql);
			List<T> list = this.jdbcTemplate.queryForList(pageSql, elementType);
			int total = this.jdbcTemplate.queryForObject(totalSql,
					Integer.class);
			return new PageList<T>(list,
					new PageResult(offset, pageSize, total));
		} else {
			return this.jdbcTemplate.queryForList(sql, elementType);
		}
	}

	/**
	 * 获取查询的列表
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param page
	 *            分页
	 * @param rowMapper
	 *            返回行数据
	 * @param dbType
	 *            数据库类型
	 * @return
	 * @throws Exception
	 */
	protected <T> List<T> getForList(String sql, Page page,
			RowMapper<T> rowMapper, String dbType) throws Exception {
		if (page != null) {
			int pageSize = page.getPageSize();
			int offset = page.getStartIndex();
			Dialect dialect = DatabaseFactory.getDialect(dbType);
			String pageSql = dialect.getLimitString(sql, offset, pageSize);
			String totalSql = dialect.getCountString(sql);
			List<T> list = this.jdbcTemplate.query(pageSql, rowMapper);
			int total = this.jdbcTemplate.queryForObject(totalSql,
					Integer.class);
			return new PageList<T>(list,
					new PageResult(offset, pageSize, total));
		} else {
			return this.jdbcTemplate.query(sql, rowMapper);
		}
	}

}

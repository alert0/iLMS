package com.hotent.base.db.mybatis.dialect;

import com.hotent.base.db.mybatis.Dialect;

/**
 * SQLServer2005方言。
 * <pre> 
 * 描述：SQLServer2005方言。
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-1-10-上午11:10:16
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class SQLServer2005Dialect extends Dialect{

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

    /**
	 * Add a LIMIT clause to the given SQL SELECT
	 *
	 * The LIMIT SQL will look like:
	 *
	 * WITH query AS
	 *      (SELECT TOP 100 percent ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) as __row_number__, * from table_name)
	 * SELECT *
	 * FROM query
	 * WHERE __row_number__ BETWEEN :offset and :lastRows
	 * ORDER BY __row_number__
	 * 
	 * @param querySqlString The SQL statement to base the limit query off of.
	 * @param offset         Offset of the first row to be returned by the query (zero-based)
	 * @param limit           Maximum number of rows to be returned by the query
	 * @return A new SQL statement with the LIMIT clause applied.
	 */
	@Override
	public String getLimitString(String querySqlString, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		//将sql语句的多个空格转换为一个空格。
		querySqlString=querySqlString.replaceAll("\\s+", " ");
		StringBuffer pagingBuilder = new StringBuffer();
		String orderby = getOrderByPart(querySqlString);
		String distinctStr = "";

		String loweredString = querySqlString.toLowerCase();
		String sqlPartString = querySqlString;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		result.append("WITH query AS (SELECT ")
				.append(distinctStr)
				.append("TOP 100 PERCENT ")
				.append(" ROW_NUMBER() OVER (")
				.append(orderby)
				.append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ BETWEEN ")
				.append(offset+1).append(" AND ").append(offset+limit)
				.append(" ORDER BY __row_number__");

		return result.toString();
	}

	private static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.lastIndexOf("order by");
		if (orderByIndex != -1) {
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}

	/**
	 * 截掉最后的order by 子句。
	 * 避免错误：
	 *  sql:除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询
	 */
	@Override
	public String getCountString(String sql) {
		sql=sql.replaceAll("\\s+", " ").toLowerCase();
		int orderByIndex = sql.lastIndexOf("order by");
		if (orderByIndex != -1) {
			sql= sql.substring(0,orderByIndex);
		}
		
		return super.getCountString(sql);
		
	}
}

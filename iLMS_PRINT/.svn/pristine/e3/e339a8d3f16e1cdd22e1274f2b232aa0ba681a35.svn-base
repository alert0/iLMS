package com.hanthink.mes.common.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Lei Pan
 * @version 2016-01-06
 */
public class JdbcUtil {
	
	private static ComboPooledDataSource ds = null;
	static{
		ds = new ComboPooledDataSource();
	}

	/**
	 * get connection
	 * 
	 * @return Connection
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Connection conn = null;
		if(null != ds){
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				System.out.println("获取数据库连接失败！");
				e.printStackTrace();
			}
		}
		return conn;
		
	}

	/**
	 * exectute query sql
	 * 
	 * @param dataSource
	 * @param sql
	 * @return
	 * @throws Exception
	 *             List<String[]>
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static List<String[]> executeQuery(DataSource dataSource, String sql)
			throws Exception {
		// Check data source is ready.
		if (dataSource == null) {
			throw new Exception("Data source must not be null!!!");
		}

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			return executeQuery(connection, sql);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * execute query sql
	 * 
	 * @param connection
	 * @param sql
	 * @return
	 * @throws Exception
	 *             List<String[]>
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static List<String[]> executeQuery(Connection connection, String sql)
			throws Exception {
		List<String[]> arrayData = new ArrayList<String[]>();

		if (connection == null) {
			throw new Exception("Connection must not be null!!!");
		}

		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				String[] row = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					row[i] = rs.getString(i + 1);
				}
				arrayData.add(row);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return arrayData;
	}

	/**
	 * execute query sql
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 *             List<String[]>
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static List<String[]> executeQuery(String sql) throws Exception {
		Connection connection = getConnection();
		List<String[]> arrayData;
		try {
			arrayData = executeQuery(connection, sql);
		} catch (Exception e) {
			throw e;
		} finally {
			connection.close();
		}
		return arrayData;
	}

	/**
	 * execute data manipulation sql
	 * 
	 * @param dataSource
	 * @param sql
	 * @return
	 * @throws Exception
	 *             int
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static int executeUpdate(DataSource dataSource, String sql)
			throws Exception {
		// Check data source is ready.
		if (dataSource == null) {
			throw new Exception("Data source must not be null!!!");
		}

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			return executeUpdate(connection, sql);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * execute data manipulation sql
	 * 
	 * @param connection
	 * @param sql
	 * @return
	 * @throws Exception
	 *             int
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static int executeUpdate(Connection connection, String sql)
			throws Exception {
		// Check database is ready.
		if (connection == null) {
			throw new Exception("Connection must not be null!!!");
		}

		Statement statement = null;
		int result = 0;
		try {
			statement = connection.createStatement();
			result = statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return result;
	}

	/**
	 * execute data manipulation sql
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 *             int
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static int executeUpdate(String sql) throws Exception {
		Connection connection = getConnection();
		int result;
		try {
			result = executeUpdate(connection, sql);
		} catch (Exception e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	/**
	 * batch execute data manipulation sql
	 * 
	 * @param dataSource
	 * @param batchSql
	 * @return
	 * @throws Exception
	 *             int[]
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static int[] executeBatch(DataSource dataSource, String[] batchSql)
			throws Exception {
		if (dataSource == null) {
			throw new Exception("Database data source must not be null!!!");
		}
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			return executeBatch(connection, batchSql);
		} catch (Exception e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * batch execute data manipulation sql
	 * 
	 * @param connection
	 * @param batchSql
	 * @return
	 * @throws Exception
	 *             int[]
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static int[] executeBatch(Connection connection, String[] batchSql)
			throws Exception {
		return executeBatch(connection, batchSql, false);
	}

	/**
	 * batch execute data manipulation sql
	 * 
	 * @param batchSql
	 * @return
	 * @throws Exception
	 *             int[]
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static int[] executeBatch(String[] batchSql) throws Exception {
		Connection connection = getConnection();
		try {
			int[] result = executeBatch(connection, batchSql, false);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			connection.close();
		}
	}

	/**
	 * batch execute data manipulation sql
	 * 
	 * @param connection
	 * @param batchSql
	 * @param transacted
	 * @return
	 * @throws Exception
	 *             int[]
	 * 
	 * @author Lei Pan
	 * @version 2016-4-29
	 */
	public static int[] executeBatch(Connection connection, String[] batchSql,
			boolean transacted) throws Exception {
		if (connection == null) {
			throw new Exception("Database connection must not be null!!!");
		}

		Statement statement = null;
		try {
			connection.setAutoCommit(!transacted);

			statement = connection.createStatement();
			for (String sql : batchSql) {
				statement.addBatch(sql);
			}
			int[] result = statement.executeBatch();
			if (transacted) {
				connection.commit();
			}

			return result;
		} catch (SQLException e) {
			if (connection != null && !connection.getAutoCommit()) {
				connection.rollback();
			}
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	/**
	* batch execute data manipulation sql
	* 
	* @param batchSql
	* @param transacted
	* @return
	* @throws Exception int[]
	*  
	* @author Lei Pan
	* @version 2016-4-29
	*/
	public static int[] executeBatch(String[] batchSql, boolean transacted)
			throws Exception {
		Connection connection = getConnection();

		try {
			int[] result = executeBatch(batchSql, transacted);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			connection.close();
		}
	}
}

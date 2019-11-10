package com.hotent.base.db.table.factory;

import org.apache.commons.lang.StringUtils;

import com.hotent.base.api.db.IIndexOperator;
import com.hotent.base.api.db.ITableOperator;
import com.hotent.base.api.db.IViewOperator;
import com.hotent.base.db.mybatis.Dialect;
import com.hotent.base.db.mybatis.dialect.DB2Dialect;
import com.hotent.base.db.mybatis.dialect.H2Dialect;
import com.hotent.base.db.mybatis.dialect.MySQLDialect;
import com.hotent.base.db.mybatis.dialect.OracleDialect;
import com.hotent.base.db.mybatis.dialect.SQLServer2005Dialect;
import com.hotent.base.db.table.BaseTableMeta;
import com.hotent.base.db.table.impl.db2.DB2IndexOperator;
import com.hotent.base.db.table.impl.db2.DB2TableMeta;
import com.hotent.base.db.table.impl.db2.DB2TableOperator;
import com.hotent.base.db.table.impl.db2.DB2ViewOperator;
import com.hotent.base.db.table.impl.dm.DmIndexOperator;
import com.hotent.base.db.table.impl.dm.DmTableMeta;
import com.hotent.base.db.table.impl.dm.DmTableOperator;
import com.hotent.base.db.table.impl.dm.DmViewOperator;
import com.hotent.base.db.table.impl.h2.H2IndexOperator;
import com.hotent.base.db.table.impl.h2.H2TableMeta;
import com.hotent.base.db.table.impl.h2.H2TableOperator;
import com.hotent.base.db.table.impl.h2.H2ViewOperator;
import com.hotent.base.db.table.impl.mysql.MySQLIndexOperator;
import com.hotent.base.db.table.impl.mysql.MySQLTableMeta;
import com.hotent.base.db.table.impl.mysql.MySQLTableOperator;
import com.hotent.base.db.table.impl.mysql.MySQLViewOperator;
import com.hotent.base.db.table.impl.oracle.OracleIndexOperator;
import com.hotent.base.db.table.impl.oracle.OracleTableMeta;
import com.hotent.base.db.table.impl.oracle.OracleTableOperator;
import com.hotent.base.db.table.impl.oracle.OracleViewOperator;
import com.hotent.base.db.table.impl.sqlserver.SQLServer2008TableMeta;
import com.hotent.base.db.table.impl.sqlserver.SQLServerIndexOperator;
import com.hotent.base.db.table.impl.sqlserver.SQLServerTableMeta;
import com.hotent.base.db.table.impl.sqlserver.SQLServerTableOperator;
import com.hotent.base.db.table.impl.sqlserver.SQLServerViewOperator;
import com.hotent.base.db.table.util.SQLConst;
import com.hotent.base.db.table.util.SQLUtil;

/**
 * 元数据读取工厂。
 * 
 * @author ray
 * 
 */
public class DatabaseFactory {

	public static String EXCEPTION_MSG = "没有设置合适的数据库类型";

	/**
	 * 通过数据库类型获得表操作
	 * 
	 * @param dbType
	 *            数据库类型
	 * @return
	 * @throws Exception
	 */
	public static ITableOperator getTableOperator(String dbType)
			throws Exception {
		ITableOperator tableOperator = null;
		if (dbType.equals(SQLConst.DB_ORACLE)) {
			tableOperator = new OracleTableOperator();
		} else if (dbType.equals(SQLConst.DB_MYSQL)) {
			tableOperator = new MySQLTableOperator();
		} else if (dbType.equals(SQLConst.DB_SQLSERVER) || dbType.equals(SQLConst.DB_SQLSERVER2008)) {
			tableOperator = new SQLServerTableOperator();
		} else if (dbType.equals(SQLConst.DB_DB2)) {
			tableOperator = new DB2TableOperator();
		} else if (dbType.equals(SQLConst.DB_H2)) {
			tableOperator = new H2TableOperator();
		} else if (dbType.equals(SQLConst.DB_DM)) {
			tableOperator = new DmTableOperator();
		} else {
			throw new Exception(EXCEPTION_MSG);
		}
		return tableOperator;
	}

	

	/**
	 * 通过数据库类型获得表元操作
	 * 
	 * @param dbType
	 *            数据库类型
	 * @return
	 * @throws Exception
	 */
	public static BaseTableMeta getTableMetaByDbType(String dbType)
			throws Exception {
		BaseTableMeta meta = null;
		if (dbType.equals(SQLConst.DB_ORACLE)) {
			meta = new OracleTableMeta();
		} else if (dbType.equals(SQLConst.DB_MYSQL)) {
			meta = new MySQLTableMeta();
		} else if (dbType.equals(SQLConst.DB_SQLSERVER)) {
			meta = new SQLServerTableMeta();
		}
		else if (dbType.equals(SQLConst.DB_SQLSERVER2008)) {
			meta = new SQLServer2008TableMeta();
		}
		else if (dbType.equals(SQLConst.DB_DB2)) {
			meta = new DB2TableMeta();
		} else if (dbType.equals(SQLConst.DB_H2)) {
			meta = new H2TableMeta();
		} else if (dbType.equals(SQLConst.DB_DM)) {
			meta = new DmTableMeta();
		} else {
			throw new Exception(EXCEPTION_MSG);
		}
		return meta;
	}

	/**
	 * 根据数据类型获取 视图操作
	 * 
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	public static IIndexOperator getIndexOperator(String dbType)
			throws Exception {
		IIndexOperator indexOperator = null;
		if (dbType.equals(SQLConst.DB_ORACLE)) {
			indexOperator = new OracleIndexOperator();
		} else if (dbType.equals(SQLConst.DB_MYSQL)) {
			indexOperator = new MySQLIndexOperator();
		} else if (dbType.equals(SQLConst.DB_SQLSERVER)) {
			indexOperator = new SQLServerIndexOperator();
		} else if (dbType.equals(SQLConst.DB_DB2)) {
			indexOperator = new DB2IndexOperator();
		} else if (dbType.equals(SQLConst.DB_H2)) {
			indexOperator = new H2IndexOperator();
		} else if (dbType.equals(SQLConst.DB_DM)) {
			indexOperator = new DmIndexOperator();
		} else {
			throw new Exception(EXCEPTION_MSG);
		}
		return indexOperator;

	}

	/**
	 * 根据数据源获取 视图操作
	 * 
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	public static IViewOperator getViewOperator(String dbType)
			throws Exception {
		
		IViewOperator view = null;
		if (dbType.equals(SQLConst.DB_ORACLE)) {
			view = new OracleViewOperator();
		} else if (dbType.equals(SQLConst.DB_MYSQL)) {
			view = new MySQLViewOperator();
		} else if (dbType.equals(SQLConst.DB_SQLSERVER) || dbType.equals(SQLConst.DB_SQLSERVER2008)) {
			view = new SQLServerViewOperator();
		} else if (dbType.equals(SQLConst.DB_DB2)) {
			view = new DB2ViewOperator();
		} else if (dbType.equals(SQLConst.DB_H2)) {
			view = new H2ViewOperator();
		} else if (dbType.equals(SQLConst.DB_DM)) {
			view = new DmViewOperator();
		} else {
			throw new Exception(EXCEPTION_MSG);
		}
		
		return view;
	}

	/**
	 * 
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	public static Dialect getDialect(String dbType) throws Exception {
		if (StringUtils.isEmpty(dbType))
			throw new Exception(EXCEPTION_MSG);
		Dialect dialect = null;
		if (dbType.equals(SQLConst.DB_ORACLE)) {
			dialect = new OracleDialect();
		} else if (dbType.equals(SQLConst.DB_MYSQL)) {
			dialect = new MySQLDialect();
		} else if (dbType.equals(SQLConst.DB_SQLSERVER) || dbType.equals(SQLConst.DB_SQLSERVER2008)) {
			dialect = new SQLServer2005Dialect();
		} 
	
		else if (dbType.equals(SQLConst.DB_DB2)) {
			dialect = new DB2Dialect();
		} else if (dbType.equals(SQLConst.DB_H2)) {
			dialect = new H2Dialect();
		} else if (dbType.equals(SQLConst.DB_DM)) {
			// dialect = new DmDialect();
		} else {
			throw new Exception(EXCEPTION_MSG);
		}
		return dialect;
	}

	
	

}

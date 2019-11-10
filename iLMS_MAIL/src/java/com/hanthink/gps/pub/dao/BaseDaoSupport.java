package com.hanthink.gps.pub.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.DynamicSql;
import com.ibatis.sqlmap.engine.mapping.sql.raw.RawSql;
import com.ibatis.sqlmap.engine.mapping.sql.simple.SimpleDynamicSql;
import com.ibatis.sqlmap.engine.mapping.sql.stat.StaticSql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.hanthink.gps.pub.vo.PageObject;

public class BaseDaoSupport extends SqlMapClientDaoSupport {
	
	/**
	 * 
	 * <dd>概要: BaseDaoSupport.executeBatchInsert
	 * <dd>说明: 批量插入
	 *
	 * @param sqlId
	 * @param params 
	 *
	 */
	public void executeBatchInsert(String sqlId, List<?> params) {
		SqlMapClient sqlMap = this.getSqlMapClient();
		try {
			sqlMap.startTransaction();
			sqlMap.startBatch();
			for (Object param : params) {
				sqlMap.insert(sqlId, param);
			}
			// ... execute statements in between
			sqlMap.executeBatch();
//			sqlMap.commitTransaction();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 批量删除
	 * 
	 * @param sqlId
	 * @param params
	 */
	public void executeBatchDelete(String sqlId, List<?> params) {
		SqlMapClient sqlMap = this.getSqlMapClient();
		try {
			sqlMap.startTransaction();
			sqlMap.startBatch();
			for (Object param : params) {
				this.getSqlMapClientTemplate().delete(sqlId, param);
			}
//			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询
	 */
	public List<?> executeQueryForList(String sqlId, Object params) {
		return this.getSqlMapClientTemplate().queryForList(sqlId, params);
	}

	public List<?> executeQueryForList(String sqlId) {
		return this.getSqlMapClientTemplate().queryForList(sqlId);
	}

	public Object executeQueryForObject(String sqlId) {
		return this.getSqlMapClientTemplate().queryForObject(sqlId);
	}

	public Object executeQueryForObject(String sqlId, Object params) {
		return this.getSqlMapClientTemplate().queryForObject(sqlId, params);
	}

	/**
	 * 分页查询
	 * 
	 * @param sqlId
	 *            sqoId
	 * @param params
	 *            查询条件
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject executeQueryForPage(String sqlId, Object params,
			final int offset, final int length) {
//		long start = System.currentTimeMillis(); // 获取最初时间
//
//		int totalCount = this.getSqlMapClientTemplate().queryForList(sqlId,
//				params).size();
//		long end = System.currentTimeMillis(); // 获取运行结束时间
//		System.out.println("pre 执行时间" + (end - start));
		int totalCount = (Integer)this.getSqlMapClientTemplate().queryForObject("comm.getTotalCount",getExecuteSQL(sqlId,params));
//		long end2 = System.currentTimeMillis(); // 获取运行结束时间
//		System.out.println("now  执行时间" + (end2 - end));
		List<?> items = this.getSqlMapClientTemplate().queryForList(sqlId,
				params, offset, length);
		PageObject pageObject = new PageObject(items, totalCount);

		return pageObject;
	}

	public PageObject executeQueryForPage(String sqlId, final int offset,
			final int length) {

		int totalCount = this.getSqlMapClientTemplate().queryForList(sqlId)
				.size();
		List<?> items = this.getSqlMapClientTemplate().queryForList(sqlId,
				offset, length);
		PageObject pageObject = new PageObject(items, totalCount);
		return pageObject;
	}

	/**
	 * 更新
	 */
	public int executeUpdate(String sqlId, Object params) {
		return this.getSqlMapClientTemplate().update(sqlId, params);
	}

	public int executeUpdate(String sqlId) {
		return this.getSqlMapClientTemplate().update(sqlId);
	}

	/**
	 * 插入
	 */
	public Object executeInsert(String sqlId, Object params) {
		return this.getSqlMapClientTemplate().insert(sqlId, params);
	}

	public Object executeInsert(String sqlId) {
		return this.getSqlMapClientTemplate().insert(sqlId);
	}

	public void executeInsertBatch(final String sqlId, final List<?> objectList)
			throws DataAccessException {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for (Object creditItem : objectList) {

					// 参数1为：ibatis中需要执行的语句的id
					executor.insert(sqlId, creditItem);
					batch++;
					// 每100条批量提交一次。
					if (batch == 100) {
						executor.executeBatch();
						batch = 0;
					}
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	/**
	 * 删除
	 */
	public int executeDelete(String sqlId) {
		return this.getSqlMapClientTemplate().delete(sqlId);
	}

	public int executeDelete(String sqlId, Object params) {
		return this.getSqlMapClientTemplate().delete(sqlId, params);
	}

	/**
	 * 取得系统时间
	 * 
	 * @return 系统时间
	 */
	public Date getSysdate() {
		return new Date();
	}

	/**
	 * 取得Sequence
	 * 
	 * @param seqType
	 *            sequence类型
	 * @return sequence
	 */
	// public String getSeq(SeqType seqType) {
	// SeqVO seqVO = new SeqVO();
	// switch (seqType) {
	// case SEQ_TYPE_USER_ID:
	// seqVO.setSeqId("SEQ_USER_ID");
	// seqVO.setSeqLen(5);
	// break;
	// case SEQ_TYPE_BASIC_DATA_ID:
	// seqVO.setSeqId("SEQ_BASIC_DATA_ID");
	// seqVO.setSeqLen(3);
	// break;
	// case SEQ_TYPE_ROLE_ID:
	// seqVO.setSeqId("SEQ_ROLE_ID");
	// seqVO.setSeqLen(6);
	// break;
	// case SEQ_TYPE_DEPARTMENT_ID:
	// seqVO.setSeqId("SEQ_DEPARTMENT_ID");
	// seqVO.setSeqLen(6);
	// break;
	// default:
	// break;
	// }
	//
	// if (StringUtil.isNullOrEmpty(seqVO.getSeqId()))
	// return "";
	// return (String) executeQueryForObject("comm.select_sequence",seqVO);
	// }
	protected String getExecuteSQL(String sqlId, Object object) {
		// SqlMapExecutorDelegate是一个相当核心的类,他存放了配置文件所有信息和java连接对象,有一个会话池和一个请求池,还有sql解析其,以及一个具体sql语句的执行者,大家可以看看
		// SqlMapExecutorDelegate
		// delegate=((ExtendedSqlMapClient)(getSqlMapClientTemplate().getSqlMapClient())).getDelegate();
		// //这个类用来存放某个id名的Statement信息,如这个当中的getProduct就是一条语句的配置id名
		// MappedStatement ms = delegate.getMappedStatement(sqlId);
		// //sql类就是某一类型 Statement的对应sql拼装解析类
		// Sql sql=ms.getSql();
		// //然后调用getSql方法,把参数值数组传进去,如下面只有一个参数productId,便可以生成 形如 select * from
		// xxx where
		// xx=?的sql语句了,代理类的sql执行者便可以根据这个sql和参数值数组进行参数查询了,前面那个参数的类型是com.ibatis.sqlmap.engine.scope.RequestScope,这个要从上面提到的代理类里面获取,但是因为是保护成员,而且发现拼凑sql的时候并没有多大作用,所以传了个null进去,结果居然通过了,不过这部分我还要确认一下.个人感觉RequestScope还是很重要的,可以会影响其他类型的Statement
		// System.out.println(sql.getSql(null,object));
		// return null;

		SqlMapClientImpl sqlmap = (SqlMapClientImpl) getSqlMapClientTemplate()
				.getSqlMapClient();

		MappedStatement stmt = sqlmap.getMappedStatement(sqlId);
		Sql sql = stmt.getSql();

		String sqlStr = null;
		SessionScope sessionScope = new SessionScope();
		sessionScope.incrementRequestStackDepth();
		StatementScope statementScope = new StatementScope(sessionScope);
		stmt.initRequest(statementScope);
		ParameterMap parameterMap = sql.getParameterMap(statementScope, object);
		// 获取sql语句(含?号)
		if (sql instanceof DynamicSql) {
			DynamicSql dynamicSql = (DynamicSql) sql;
			sqlStr = dynamicSql.getSql(statementScope, object);
//			System.out
//					.println("DynamicSql####################################################"
//							+ sqlStr);
		} else if (sql instanceof StaticSql) {
			StaticSql staticSql = (StaticSql) sql;
			sqlStr = staticSql.getSql(statementScope, object);
//			System.out
//					.println("StaticSql####################################################"
//							+ sqlStr);
		}else if (sql instanceof SimpleDynamicSql) {
			SimpleDynamicSql simpleDynamicSql = (SimpleDynamicSql) sql;
			sqlStr = simpleDynamicSql.getSql(null, object);
//			System.out
//					.println("SimpleDynamicSql####################################################"
//							+ sqlStr);
		}else if (sql instanceof RawSql) {
			RawSql rawSql = (RawSql) sql;
			sqlStr = rawSql.getSql(statementScope, object);
//			System.out
//					.println("RawSql####################################################"
//							+ sqlStr);
		}
		// 有动态参数
		if (parameterMap != null) {
			Object[] parameterMappings = parameterMap
					.getParameterObjectValues(statementScope, object);
			// 这个对象就是送到DB端取数据的参数数组
			// 我们要做的就是拼凑?和参数数据对应,此处使用的是ibatis的解析后的结果,
			// 不存在?和参数个数不匹配的问题,有的话在前面执行sql出已经以异常形式抛出
			for (int j = 0; j < parameterMappings.length; j++) {
				if (parameterMappings[j] instanceof String) {
					sqlStr = sqlStr.replaceFirst("\\?", " '"
							+ ((String) parameterMappings[j]) + "' ");
				} else if (parameterMappings[j] instanceof Integer) {
					sqlStr = sqlStr.replaceFirst("\\?", " "
							+ ((Integer) parameterMappings[j]) + " ");
				} else if (parameterMappings[j] instanceof BigDecimal) {
					sqlStr = sqlStr.replaceFirst("\\?", " '"
							+ ((BigDecimal) parameterMappings[j])
									.toPlainString() + "' ");
				}else if (parameterMappings[j] instanceof Long) {
					sqlStr = sqlStr.replaceFirst("\\?", " "
							+ ((Long) parameterMappings[j]) + " ");
				}
			}
//			System.out.println("#####################end of replace sql :"
//					+ sqlStr);
		}
		sql.cleanup(statementScope);
		sessionScope.cleanup();
		stmt = null;
		sql = null;
		sessionScope = null;
		statementScope = null;
		return "select count(*) from ("+ sqlStr+")" ;
	}
	
	/**
	 * 批量更新
	 * 
	 * @param sqlId
	 * @param params
	 */
	public void executeBatchUpdate(String sqlId, List<?> params) {
		SqlMapClient sqlMap = this.getSqlMapClient();
		try {
			sqlMap.startTransaction();
			sqlMap.startBatch();
			for (Object param : params) {
				this.getSqlMapClientTemplate().update(sqlId, param);
			}
			// ... execute statements in between
//			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 不分页查询 （全部显示）
	 * @param sqlId sqoId
	 * @param params 查询条件
	 * @param start 开始记录
	 * @param limit 查询条数
	 * @return 查询结果
	 */
	public PageObject executeQueryForAllPage(String sqlId, Object params,
			final int offset, final int length) {

//		int totalCount = this.getSqlMapClientTemplate().queryForList(sqlId,
//				params).size();
		int totalCount = (Integer)this.getSqlMapClientTemplate().queryForObject("system.getTotalCount",getExecuteSQL(sqlId,params));		
		List<?> items = this.getSqlMapClientTemplate().queryForList(sqlId,
				params, 0, totalCount);
		PageObject pageObject = new PageObject(items, totalCount);
		return pageObject;
	}
	
	
}
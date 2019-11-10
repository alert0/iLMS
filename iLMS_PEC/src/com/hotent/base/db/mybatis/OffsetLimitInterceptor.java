package com.hotent.base.db.mybatis;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageResult;
import com.hotent.base.db.mybatis.support.PropertiesHelper;
import com.hotent.base.db.mybatis.support.SQLHelp;



/**
 * 为MyBatis提供基于方言(Dialect)的分页查询的插件,将拦截Executor.query()方法实现分页方言的插入.
 * <pre> 
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-1-3-下午4:03:56
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Intercepts({@Signature(
		type= Executor.class,
		method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class OffsetLimitInterceptor implements Interceptor{
    private static Logger logger = LoggerFactory.getLogger(OffsetLimitInterceptor.class);
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;

    static ExecutorService Pool;
    
    boolean asyncTotalCount = false;
    
    private static String getSql(String sql){
    	return sql.trim().replaceAll("(?si)\\s+", " ");
    }
    
    private String defaultDbType="";
    
    
    private Map<String, Dialect> dialectMap=new HashMap<String, Dialect>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object intercept(final Invocation invocation) throws Throwable {
		final Dialect dialect=getDialect();
        final Executor executor = (Executor) invocation.getTarget();
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
        final DefaultPage pageBounds = new DefaultPage(rowBounds);
        //开始记录号
        final int offset = pageBounds.getOffset();
        //分页大小
        final int limit = pageBounds.getLimit();
        //页码
        final int page = pageBounds.getPage();

        final BoundSql boundSql = ms.getBoundSql(parameter);
        final StringBuffer bufferSql = new StringBuffer(boundSql.getSql().trim());
        if(bufferSql.lastIndexOf(";") == bufferSql.length()-1){
            bufferSql.deleteCharAt(bufferSql.length()-1);
        }
        //sql语句。
        String sql = getSql(bufferSql.toString().trim());

        if(pageBounds.getOrders() != null && !pageBounds.getOrders().isEmpty()){
            sql = dialect.getSortString(sql, pageBounds.getOrders());
        }

        Callable<PageResult> countTask = null;
        //支持分页
        if(dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
        	//是否支持分页
            if(pageBounds.isContainsTotalCount()){
                countTask = new Callable() {
                    public Object call() throws Exception {
                        Integer count = null;
                        Cache cache = ms.getCache();
                        if(cache != null && ms.isUseCache()){
                            CacheKey cacheKey = executor.createCacheKey(ms,parameter,new DefaultPage(),copyFromBoundSql(ms,boundSql,bufferSql.toString()));
                            count = (Integer)cache.getObject(cacheKey);
                            if(count == null){
                                count = SQLHelp.getCount(bufferSql.toString(),ms,parameter,boundSql,dialect);
                                cache.putObject(cacheKey, count);
                            }
                        }else{
                            count = SQLHelp.getCount(bufferSql.toString(),ms,parameter,boundSql,dialect);
                        }
                        return new PageResult(page, limit, count);
                    }
                };
            }

            if (dialect.supportsLimitOffset()) {
                sql = dialect.getLimitString(sql, offset, limit);
            } else {
                sql = dialect.getLimitString(sql, 0, limit);
            }
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET,RowBounds.NO_ROW_LIMIT);
        }

        queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms,boundSql,sql);


        Boolean async = pageBounds.getAsyncTotalCount() == null ? asyncTotalCount : pageBounds.getAsyncTotalCount();
        //处理分页
        Future<List> listFuture = call(new Callable<List>() {
            public List call() throws Exception {
                return (List)invocation.proceed();
            }
        }, async);
        //处理总数。
        if(countTask!=null){
            Future<PageResult> countFutrue = call(countTask, async);
            return new PageList(listFuture.get(),countFutrue.get());
        }

        return listFuture.get();
	}
	
	private Dialect getDialect(){
		String dbType= DbContextHolder.getDbType();
		if(StringUtil.isEmpty(dbType)){
			return dialectMap.get(defaultDbType);
		}
		return dialectMap.get(dbType);
		
	}

    private <T> Future<T> call(Callable callable, boolean async){
        if(async){
             return Pool.submit(callable);
        }else{
            FutureTask<T> future = new FutureTask(callable);
            future.run();
            return future;
        }
    }

    private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql,
                                           String sql){
    
    	
        BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
        return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
    }

	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
			String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
		    String prop = mapping.getProperty();
		    if (boundSql.hasAdditionalParameter(prop)) {
		        newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
		    }
		}
		return newBoundSql;
	}
	
	//see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());
		
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if(ms.getKeyProperties() != null && ms.getKeyProperties().length !=0){
            StringBuffer keyProperties = new StringBuffer();
            for(String keyProperty : ms.getKeyProperties()){
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length()-1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		
		//setStatementTimeout()
		builder.timeout(ms.getTimeout());
		
		//setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());
		
		//setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
	    
		//setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		
		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		//获取数据库类型
		this.defaultDbType=properties.getProperty("dbType");
		
        PropertiesHelper propertiesHelper = new PropertiesHelper(properties);
        //获取方言
        Properties p= propertiesHelper.getStartsWithProperties("Dialect.");
        
        for(Iterator it=p.keySet().iterator();it.hasNext();){
        	String key=(String) it.next();
        	String className=(String) p.get(key);
        	try {
				Dialect dialect= (Dialect) Class.forName(className).newInstance();
				dialectMap.put(key, dialect);
			} catch (Exception e) {
				e.printStackTrace();
			} 
        }
		setAsyncTotalCount(false);
		setPoolMaxSize(0);
        

	}
	
	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

    

    public void setAsyncTotalCount(boolean asyncTotalCount) {
        logger.debug("asyncTotalCount: {} ", asyncTotalCount);
        this.asyncTotalCount = asyncTotalCount;
    }

    public void setPoolMaxSize(int poolMaxSize) {

        if(poolMaxSize > 0){
            logger.debug("poolMaxSize: {} ", poolMaxSize);
            Pool = Executors.newFixedThreadPool(poolMaxSize);
        }else{
            Pool = Executors.newCachedThreadPool();
        }


    }
    
   
    
    
}

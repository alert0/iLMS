package com.hotent.base.db.table.factory;

import org.springframework.beans.factory.FactoryBean;

import com.hotent.base.db.mybatis.Dialect;
import com.hotent.base.db.table.util.SQLConst;

/**
 * 方言FactoryBean，通过统一的接口取得不同数据库的分页Sql语句。
 * 
 * <pre>
 * 在app-beans.xml中的配置。
 * 
 * &lt;bean id="dialect" class="com.hotent.base.db.table.factory.DialectFactoryBean">
 * 		&lt;property name="dbType" value="${jdbc.dbType}"/>
 * &lt;/bean>
 * </pre>
 * 
 * @author ray
 * 
 */
public class DialectFactoryBean implements FactoryBean<Dialect> {

	/**
	 * 方言
	 */
	private Dialect dialect;

	/**
	 * 数据类型
	 */
	private String dbType = SQLConst.DB_MYSQL;

	/**
	 * 设置数据库类型
	 * 
	 * @param dbType
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	@Override
	public Dialect getObject() throws Exception {
		dialect = DatabaseFactory.getDialect(this.dbType);
		return dialect;
	}

	@Override
	public Class<?> getObjectType() {
		return Dialect.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}

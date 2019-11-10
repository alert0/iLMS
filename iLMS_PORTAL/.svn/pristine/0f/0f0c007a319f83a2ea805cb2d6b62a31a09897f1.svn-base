package com.hotent.base.api.db;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库类型接口。
 * <pre>
 * 1.设置JdbcTemplate。
 * 2.设置方言。
 * 
 * 构建组：x5-base-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-11-26-下午11:01:26
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface IDbType {

	/**
	 * 设置spring 的JDBCTemplate
	 * 
	 * @param template
	 */
	void setJdbcTemplate(JdbcTemplate template);
	
	/**
	 * 设置方言。
	 * 
	 * @param dialect
	 *            
	 */
	public void setDialect(IDialect dialect);
}

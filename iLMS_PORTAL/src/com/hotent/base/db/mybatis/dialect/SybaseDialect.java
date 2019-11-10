package com.hotent.base.db.mybatis.dialect;

import com.hotent.base.db.mybatis.Dialect;

/**
 * Sybase方言
 * <pre> 
 * 描述：Sybase方法，不支持limit分页。
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-1-10-上午11:13:14
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class SybaseDialect extends Dialect{

	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return false;
	}

	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}

}

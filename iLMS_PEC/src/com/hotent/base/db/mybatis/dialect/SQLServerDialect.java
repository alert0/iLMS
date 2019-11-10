package com.hotent.base.db.mybatis.dialect;

import com.hotent.base.db.mybatis.Dialect;

/**
 * SQLServer 2000 方言实现。
 * <pre> 
 * 描述：不支持limit分页。
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-1-10-上午11:11:42
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class SQLServerDialect extends Dialect{

	public boolean supportsLimitOffset(){
		return false;
	}
	
	public boolean supportsLimit() {
		return true;
	}
	
	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf( "select" );
		final int selectDistinctIndex = sql.toLowerCase().indexOf( "select distinct" );
		return selectIndex + ( selectDistinctIndex == selectIndex ? 15 : 6 );
	}

	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql,offset,null,limit,null);
	}

	public String getLimitString(String querySelect, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		if ( offset > 0 ) {
			throw new UnsupportedOperationException( "sql server has no offset" );
		}
//		if(limitPlaceholder != null) {
//			throw new UnsupportedOperationException(" sql server not support variable limit");
//		}
		
		return new StringBuffer( querySelect.length() + 8 )
				.append( querySelect )
				.insert( getAfterSelectInsertPoint( querySelect ), " top " + limit )
				.toString();
	}
	
	// TODO add Dialect.supportsVariableLimit() for sqlserver 
//	public boolean supportsVariableLimit() {
//		return false;
//	}
	
	
	

}

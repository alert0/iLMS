package com.hotent.base.db.mybatis.dialect;

import com.hotent.base.db.mybatis.Dialect;

/**
 * oracle 方言实现。
 * <pre> 
 * 描述：oracle 方言实现。
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-1-10-上午11:08:05
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class OracleDialect extends Dialect{
	
	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}
	
	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") ) {
			sql = sql.substring( 0, sql.length()-11 );
			isForUpdate = true;
		}
		
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
		//排序号
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}
		else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
//			int end = offset+limit;
			String endString = offsetPlaceholder+"+"+limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= " + endString + " and rownum_ > " + offsetPlaceholder);
		}
		else {
			pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
		}

		if ( isForUpdate ) {
			pagingSelect.append( " for update" );
		}
		
		return pagingSelect.toString();
	}

}

package com.hotent.sys.api.sqlbuilder;

public interface ISqlBuilderService {
	
	String getSql(SqlBuilderModel model);

	ISqlBuilder getSqlBuilder(SqlBuilderModel model);
	
}

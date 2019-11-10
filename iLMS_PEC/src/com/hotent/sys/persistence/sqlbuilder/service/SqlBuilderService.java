/**
 * 描述：TODO
 * 包名：com.hotent.platform.bpm.sqlbuilder
 * 文件名：AbstractHandlerSqlBuilder.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2014-7-16-下午6:23:30
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.sys.persistence.sqlbuilder.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hotent.base.core.cache.ICache;
import com.hotent.sys.api.sqlbuilder.ISqlBuilder;
import com.hotent.sys.api.sqlbuilder.ISqlBuilderService;
import com.hotent.sys.api.sqlbuilder.SqlBuilderModel;

@Service("sqlBuilderService")
public class SqlBuilderService implements ISqlBuilderService{
	protected  Map<String,ISqlBuilder> builderMap=new HashMap<String, ISqlBuilder>() ;
	
	
	/**
	 * @param builderMap the builderMap to set
	 */
	public void setBuilderMap(Map<String, ISqlBuilder> builderMap) {
		this.builderMap = builderMap;
	}
	
	@Override
	public String getSql(SqlBuilderModel model) {
		return getSqlBuilder(model).getSql();
	}
	
	@Override
	public ISqlBuilder getSqlBuilder(SqlBuilderModel model){
		String dbType=model.getDbType();
		ISqlBuilder builder=builderMap.get(dbType);
		builder.setModel(model);
		return builder;
	}
}

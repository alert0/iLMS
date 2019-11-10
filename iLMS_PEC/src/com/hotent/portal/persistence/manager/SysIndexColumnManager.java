package com.hotent.portal.persistence.manager;


import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.portal.persistence.model.SysIndexColumn;

public interface  SysIndexColumnManager extends Manager<String, SysIndexColumn>{
	
	public String parserDesignHtml(String designHtml,
			List<SysIndexColumn> columnList);
	
	public Boolean isExistAlias(String alias, String id);
	
	/**
	 * 通过别名获取栏目
	 * 
	 * @param alias
	 * @return
	 */
	public SysIndexColumn getByColumnAlias(String alias);
	/**
	 * 
	 * @param alias
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getHtmlByColumnAlias(String alias, Map<String, Object> params) throws Exception;
	
	public Map<String, List<SysIndexColumn>> getColumnMap(
			List<SysIndexColumn> columnList) throws Exception;
	
	public List<SysIndexColumn> getHashRightColumnList(QueryFilter filter,
			Map<String, Object> params, Boolean isParse, short type) throws Exception;
	
}

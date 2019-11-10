package com.hotent.form.persistence.manager;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.CustomQuery;

public interface CustomQueryManager extends Manager<String, CustomQuery> {
	
	/**
	 * 传入自定义查询参数，返回自定义查询结果。
	 * @param customQuery
	 * @param queryData
	 * @param dsType
	 * @param page
	 * @param pageSize
	 * @return customQueryResult
	 */
	public PageList getData(CustomQuery customQuery, String queryData, String dsType, int page, int pageSize);

	/**
	 * @param alias
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	public CustomQuery getByAlias(String alias);

}

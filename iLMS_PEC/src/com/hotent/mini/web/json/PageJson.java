package com.hotent.mini.web.json;

import java.util.List;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageResult;

@SuppressWarnings("rawtypes")
public class PageJson {
	// 总记录数
	private Integer total = 0;

	private PageResult pageResult = null;

	// 行记录
	
	private List rows = null;

	public PageJson() {

	}

	public PageJson(List rows, Integer total) {
		this.rows = rows;
		this.total = total;
	}

	public PageJson(PageList pageList) {
		this.rows = pageList;
		try {
			this.total = pageList.getPageResult().getTotalCount();
			this.pageResult = pageList.getPageResult();
		} catch (Exception e) {
		}
	}

	public PageJson(List arrayList) {
		this.rows = arrayList;
		this.total = arrayList.size();
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	/**
	 * pageResult
	 * 
	 * @return the pageResult
	 * @since 1.0.0
	 */

	public PageResult getPageResult() {
		return pageResult;
	}
	
	public void setPageResult(PageResult pageResult)
	{
		this.pageResult=pageResult;
	}

}

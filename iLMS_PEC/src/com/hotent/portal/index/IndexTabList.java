package com.hotent.portal.index;

import java.util.List;

import com.hotent.base.db.mybatis.domain.PageResult;

public class IndexTabList {

	protected String curTab;

	private PageResult pageBean = new PageResult(0, 10, 0);

	protected List<IndexTab> indexTabList;

	public List<IndexTab> getIndexTabList() {
		return indexTabList;
	}

	public void setIndexTabList(List<IndexTab> indexTabList) {
		this.indexTabList = indexTabList;
	}

	public String getCurTab() {
		return curTab;
	}

	public void setCurTab(String curTab) {
		this.curTab = curTab;
	}

	public PageResult getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageResult pageBean) {
		this.pageBean = pageBean;
	}

}

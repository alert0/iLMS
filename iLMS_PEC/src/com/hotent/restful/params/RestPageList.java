package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageResult;

/**
 * Restful接口返回的分页列表数据
 * @author heyifan
 */
@ApiModel(description="任务查询参数对象")
public class RestPageList<E>{
	@ApiModelProperty(name="rows",notes="结果列表")
	List<E> rows;
	@ApiModelProperty(name="totals",notes="总记录数")
	Integer totals;
	@ApiModelProperty(name="page",notes="当前页码")
	Integer page;
	@ApiModelProperty(name="pageSize",notes="每页条数")
	Integer pageSize;
	
	public RestPageList(){
	}
	
	public RestPageList(PageList<E> pageList){
		this.rows = pageList;
		PageResult pageResult = pageList.getPageResult();
		this.totals = pageResult.getTotalCount();
		this.page = pageResult.getPage();
		this.pageSize = pageResult.getLimit();
	}
	
	public List<E> getRows() {
		return rows;
	}
	public void setRows(List<E> rows) {
		this.rows = rows;
	}
	public Integer getTotals() {
		return totals;
	}
	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

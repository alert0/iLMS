package com.hotent.restful.params;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询参数对象
 * @author liygui
 *
 */
@ApiModel(description="流程列表查询")
public class BaseQueryFilter {
	
	@ApiModelProperty(name="account",notes="用户帐号",example="admin",required=true)
	private String account;
	
	@ApiModelProperty(name="orderField",notes="排序字段")
	private String orderField;
	
	@ApiModelProperty(name="orderSeq",notes="排序方式",allowableValues="desc,asc")
	private String orderSeq;
	
	@ApiModelProperty(name="currentPage",notes="当前页，默认第一页",example="1")
	private Integer currentPage;
	
	@ApiModelProperty(name="pageSize",notes="分页大小，默认值20",example="20")
	private Integer pageSize;
	
	@ApiModelProperty(name="paramMap",notes="查询条件",example="")
	private Map<String,Object> paramMap;
	
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

}

package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户组织模块查询参数对象
 * @author liangqf
 *
 */
@ApiModel
public class UserOrgQueryFilter {
	
	@ApiModelProperty(name="name",notes="名称")
	private String name;
	
	@ApiModelProperty(name="code",notes="代码")
	private String code;
	
	@ApiModelProperty(name="orderField",notes="排序字段")
	private String orderField;
	
	@ApiModelProperty(name="orderSeq",notes="排序方式",allowableValues="desc,asc")
	private String orderSeq;
	
	@ApiModelProperty(name="currentPage",notes="当前页，默认第一页",example="1")
	private Integer currentPage;
	
	@ApiModelProperty(name="pageSize",notes="分页大小，默认值20",example="20")
	private Integer pageSize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

}

package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 组织参数对象
 * @author Administrator
 *
 */
@ApiModel
public class OrgRestfulObject {
	
	@ApiModelProperty(name="name",notes="组织名称",required=true)
	private String name;
	
	@ApiModelProperty(name="parentId",notes="父组织id")
	private String parentId;
	
	@ApiModelProperty(name="code",notes="组织代码",required=true)
	private String code;
	
	@ApiModelProperty(name="grade",notes="组织级别")
	private String grade;
	
	@ApiModelProperty(name="demId",notes="维度id",required=true)
	private String demId;
	
	@ApiModelProperty(name="orderNo",notes="排序号")
	private Long orderNo;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDemId() {
		return demId;
	}

	public void setDemId(String demId) {
		this.demId = demId;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	
	public String toString(){
		return "{"
				+ "\""+"name"+"\""+":"+"\""+this.name+"\","
				+"\""+"parentId"+"\""+":"+"\""+this.parentId+"\","
				+"\""+"demId"+"\""+":"+"\""+this.demId+"\","
				+"\""+"grade"+"\""+":"+"\""+this.grade+"\","
				+"\""+"orderNo"+"\""+":"+"\""+this.orderNo+"\","
				+"\""+"code"+"\""+":"+"\""+this.code+"\""
				+ "}";
	}

}

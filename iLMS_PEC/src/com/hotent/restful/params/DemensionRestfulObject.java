package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 维度参数对象
 * @author liangqf
 *
 */
@ApiModel
public class DemensionRestfulObject {
	
	@ApiModelProperty(name="id",notes="维度id")
	protected String id; 
	
	@ApiModelProperty(name="demName",notes="维度名称",required=true)
	protected String demName; 
	
	@ApiModelProperty(name="demDesc",notes="描述")
	protected String demDesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDemName() {
		return demName;
	}

	public void setDemName(String demName) {
		this.demName = demName;
	}

	public String getDemDesc() {
		return demDesc;
	}

	public void setDemDesc(String demDesc) {
		this.demDesc = demDesc;
	} 
	
	public String toString(){
		return "{"
				+ "\""+"id"+"\""+":"+"\""+this.id+"\","
				+"\""+"demDesc"+"\""+":"+"\""+this.demDesc+"\","
				+"\""+"demName"+"\""+":"+"\""+this.demName+"\""
				+ "}";
	}

}

package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 岗位参数对象
 * @author liangqf
 *
 */
@ApiModel
public class OrgRelRestfulObject {
	
	@ApiModelProperty(name="relName",notes="岗位名称",required=true)
	protected String relName; 
	
	@ApiModelProperty(name="relCode",notes="岗位代码",required=true)
	protected String relCode;

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelCode() {
		return relCode;
	}

	public void setRelCode(String relCode) {
		this.relCode = relCode;
	} 
	public String toString(){
		return "{"
				+ "\""+"relCode"+"\""+":"+"\""+this.relCode+"\","
				+"\""+"relName"+"\""+":"+"\""+this.relName+"\""
				+ "}";
	}

}

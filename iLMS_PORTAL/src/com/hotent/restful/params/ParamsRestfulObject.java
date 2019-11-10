package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户组织参数对象
 * @author liangqf
 *
 */
@ApiModel
public class ParamsRestfulObject {
	
	@ApiModelProperty(name="name",notes="参数名",required=true)
	protected String name; 
	
	@ApiModelProperty(name="alias",notes="参数key",required=true)
	protected String alias; 
	
	@ApiModelProperty(name="type",notes="参数类型，1（用户），2（组织）",allowableValues="1,2",required=true)
	protected String type; 
	
	@ApiModelProperty(name = "ctlType",required=true,
			notes = "数据来源，input（手动录入）dic（数据字典）select（下拉框）date（日期）checkbox（复选框）radio（单选框）number（数字）customdialog（自定义对话框）"
			,allowableValues="input,dic,select,date,checkbox,radio,number,customdialog")
	protected String ctlType; 
	
	@ApiModelProperty(name="json",notes="数据")
	protected String json;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCtlType() {
		return ctlType;
	}

	public void setCtlType(String ctlType) {
		this.ctlType = ctlType;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	} 
	
	public String toString(){
		return "{"
				+ "\""+"name"+"\""+":"+"\""+this.name+"\","
				+"\""+"alias"+"\""+":"+"\""+this.alias+"\","
				+"\""+"type"+"\""+":"+"\""+this.type+"\","
				+"\""+"ctlType"+"\""+":"+"\""+this.ctlType+"\","
				+"\""+"json"+"\""+":"+"\""+this.json+"\""
				+ "}";
	}

}

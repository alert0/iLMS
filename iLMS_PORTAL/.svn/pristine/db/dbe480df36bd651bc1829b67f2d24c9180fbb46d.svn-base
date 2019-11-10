package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色参数对象
 * @author liangqf
 *
 */
@ApiModel
public class RoleRestfulObject {
	
	@ApiModelProperty(name="name",notes="角色名称",required=true)
	protected String name; 
	
	@ApiModelProperty(name="alias",notes="角色别名",required=true)
	protected String alias; 
	
	@ApiModelProperty(name="enabled",notes="0：禁用，1：启用")
	protected Integer enabled; 
	
	@ApiModelProperty(name="description",notes="角色描述")
	protected String description;


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

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		return "{"
				+ "\""+"name"+"\""+":"+"\""+this.name+"\","
				+"\""+"alias"+"\""+":"+"\""+this.alias+"\","
				+"\""+"enabled"+"\""+":"+"\""+this.enabled+"\","
				+"\""+"description"+"\""+":"+"\""+this.description+"\""
				+ "}";
	}

}

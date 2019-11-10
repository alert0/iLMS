package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 职务参数对象
 * @author liangqf
 *
 */
@ApiModel
public class OrgReldefRestfulObject {
	
	@ApiModelProperty(name="name",notes="职务名称",required=true)
	protected String name; 
	
	@ApiModelProperty(name="code",notes="职务代码",required=true)
	protected String code; 
	
	@ApiModelProperty(name="postLevel",notes="职务级别")
	protected String postLevel; 
	
	@ApiModelProperty(name="description",notes="职务描述")
	protected String description;

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

	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
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
				+"\""+"postLevel"+"\""+":"+"\""+this.postLevel+"\","
				+"\""+"description"+"\""+":"+"\""+this.description+"\","
				+"\""+"code"+"\""+":"+"\""+this.code+"\""
				+ "}";
	}

}

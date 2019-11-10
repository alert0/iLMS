package com.hotent.restful.params;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="测试对象")
public class FacadeObject {
	
	@ApiModelProperty(name="name", notes="姓名", required=true, allowableValues="a,b,c")
	private String name;
	
	@ApiModelProperty(name="age", notes="年龄", required=true)
	private Integer age;
	
	@ApiModelProperty(name="born", notes="出生日期")
	private Date born;
	
	@ApiModelProperty(name="address", notes="家庭住址")
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBorn() {
		return born;
	}
	public void setBorn(Date born) {
		this.born = born;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}

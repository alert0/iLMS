package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户参数对象
 * @author liangqf
 *
 */
@ApiModel
public class UserRestfulObject {
	
	@ApiModelProperty(name="account",notes="登录帐号",required=true)
	private String account;
	
	@ApiModelProperty(name="fullname",notes="用户名",required=true)
	private String fullname;
	
	@ApiModelProperty(name="password",notes="登录密码",required=true)
	private String password;
	
	@ApiModelProperty(name="email",notes="邮箱地址")
	private String email;
	
	@ApiModelProperty(name="mobile",notes="手机号码")
	private String mobile;
	
	@ApiModelProperty(name="weixin",notes="微信号")
	private String weixin;
	
	@ApiModelProperty(name="address",notes="地址")
	private String address;
	
	@ApiModelProperty(name="sex",notes="性别",allowableValues="男,女,未知")
	private String sex;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String toString() {
		return "{"
				+ "\""+"account"+"\""+":"+"\""+this.account+"\","
				+"\""+"fullname"+"\""+":"+"\""+this.fullname+"\","
				+"\""+"password"+"\""+":"+"\""+this.password+"\","
				+"\""+"email"+"\""+":"+"\""+this.email+"\","
				+"\""+"mobile"+"\""+":"+"\""+this.mobile+"\","
				+"\""+"weixin"+"\""+":"+"\""+this.weixin+"\","
				+"\""+"address"+"\""+":"+"\""+this.address+"\","
				+"\""+"sex"+"\""+":"+"\""+this.sex+"\""
				+ "}";
	}
}

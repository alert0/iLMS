package com.hotent.service.model.impl;

import com.hotent.service.api.model.ServiceBean;

/**
 * 服务基类
 * @author heyifan
 * @version 创建时间: 2014-8-18
 */
public abstract class AbstractServiceBean implements ServiceBean{
	private String url;  		/*服务地址*/
	private String name; 		/*服务名称*/
	private String description; /*服务描述*/
	public abstract String getType();
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

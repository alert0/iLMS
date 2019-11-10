package com.hotent.service.api.model;

/**
 * 服务
 * @author heyifan
 * @version 创建时间: 2014-8-18
 */
public interface ServiceBean {
	String getType();

	String getUrl();

	void setUrl(String url);

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);
}
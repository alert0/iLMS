package com.hanthink.demo.model;

/**
 * 列属性详细配置
 * <p>Title: ColumnOption</p>  
 * <p>Description: </p>  
 * @author whh  
 * @date 2018年11月1日
 * @version 1.0
 */
public class ColumnOption {
	
	/**
	 * 列的名称
	 */
	private String label;
	
	/**
	 * 列的prop属性
	 */
	private String prop;
	
	/**
	 * 列的宽度
	 */
	private String width;
	
	/**
	 * 是否作为搜索条件
	 */
	private boolean search = false;
	
	/**
	 * 列的类型 Such As:input text select textarea tree data......
	 */
	private String type;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

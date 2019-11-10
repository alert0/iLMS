package com.hotent.base.core.scheduler;

/**
 * 任务参数对象。
 * 
 * @author ray
 * 
 */
public class ParameterObj {
	public static final String TYPE_INT = "int";
	public static final String TYPE_LONG = "long";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_BOOLEAN = "boolean";

	/**
	 * 参数数据类型： int long float boolean
	 */
	private String type = "";
	/**
	 * 参数名称
	 */
	private String name = "";
	/**
	 * 参数值
	 */
	private String value = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

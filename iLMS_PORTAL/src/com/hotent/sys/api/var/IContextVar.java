package com.hotent.sys.api.var;

public interface IContextVar {
	
	/**
	 * 命名
	 * @return 
	 * String
	 */
	String getTitle();
	/**
	 * 别名
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	String getAlias();
	
	/**
	 * 值
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	String getValue();

}

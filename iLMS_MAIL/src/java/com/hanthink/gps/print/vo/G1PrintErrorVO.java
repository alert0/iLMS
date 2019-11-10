/**
 * 
 */
package com.hanthink.gps.print.vo;

import java.io.Serializable;

/**
 * 自动打印异常邮件提醒VO
 * @author  chenyong
 *date 2016-09-21
 */
public class G1PrintErrorVO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String errorMsg;  //错误信息
    private String factory;    //工厂
	

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}
	
	

}

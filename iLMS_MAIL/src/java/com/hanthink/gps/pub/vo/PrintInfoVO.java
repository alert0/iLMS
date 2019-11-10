package com.hanthink.gps.pub.vo;

import java.io.Serializable;

/**
 * 打印信息VO
 * @author Administrator
 *
 */
public class PrintInfoVO implements Serializable {
	private static final long serialVersionUID = -1355311643065042425L;
	
	/** 用于打印的HTML */
	private String printHtml;
	
	

	/**
	 * 获取用于打印的HTML
	 * @return the printHtml 用于打印的HTML
	 */
	public String getPrintHtml() {
		return printHtml;
	}

	/**
	 * 设置用于打印的HTML
	 * @param printHtml the printHtml 用于打印的HTML
	 */
	public void setPrintHtml(String printHtml) {
		this.printHtml = printHtml;
	}

}

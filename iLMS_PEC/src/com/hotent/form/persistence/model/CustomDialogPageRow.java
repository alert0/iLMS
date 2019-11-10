/**
 * 描述：TODO
 * 包名：com.hotent.platform.customdialog.controller.helper
 * 文件名：CustomDialogPageList.java
 * 作者：User-mailto:chensx@jee-soft.cn
 * 日期2014-7-31-上午11:36:56
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.form.persistence.model;

import java.io.Serializable;
import java.util.List;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-bpmx-platform
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-7-31-上午11:36:56
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class CustomDialogPageRow implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Row> displayRow;
	private List<Row> resultRow;
	/**
	 * displayRow
	 * @return  the displayRow
	 * @since   1.0.0
	 */
	
	public List<Row> getDisplayRow() {
		return displayRow;
	}
	/**
	 * @param displayRow the displayRow to set
	 */
	public void setDisplayRow(List<Row> displayRow) {
		this.displayRow = displayRow;
	}
	/**
	 * resultRow
	 * @return  the resultRow
	 * @since   1.0.0
	 */
	
	public List<Row> getResultRow() {
		return resultRow;
	}
	/**
	 * @param resultRow the resultRow to set
	 */
	public void setResultRow(List<Row> resultRow) {
		this.resultRow = resultRow;
	}
	
}

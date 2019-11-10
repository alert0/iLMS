package com.hanthink.gps.pub.vo;



/**
 * Excel导入数据数量统计VO
 * @author Administrator
 *
 */
public class ExcelImportCountVO extends BaseVO{
private static final long serialVersionUID = -5761563430213565522L;
	
	private String totalCount; //总计
	private String okCount; //通过
	private String existCount; //已存在
	private String errorCount; //错误
	private String importCount; //已导入
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getOkCount() {
		return okCount;
	}
	public void setOkCount(String okCount) {
		this.okCount = okCount;
	}
	public String getExistCount() {
		return existCount;
	}
	public void setExistCount(String existCount) {
		this.existCount = existCount;
	}
	public String getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}
	public String getImportCount() {
		return importCount;
	}
	public void setImportCount(String importCount) {
		this.importCount = importCount;
	}
	
}


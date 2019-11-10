package com.hotent.base.core.model;

/**
 * 在导入数据时，每条数据返回的结果。
 * @author ray
 *
 */
public class ImportResult {
	
	public ImportResult(){
		
	}
	
	public ImportResult(String title, boolean isSuccess, String detail) {
		this.title = title;
		this.isSuccess = isSuccess;
		this.detail = detail;
	}



	/**
	 * 标题
	 */
	private String title="";
	
	/**
	 * 是否成功
	 */
	private boolean isSuccess=false;
	
	/**
	 * 详细信息。
	 */
	private String detail="";
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	

}

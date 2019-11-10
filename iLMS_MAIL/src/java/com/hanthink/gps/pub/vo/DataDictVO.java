package com.hanthink.gps.pub.vo;

public class DataDictVO extends BaseVO{

	private static final long serialVersionUID = 102513541638416L;
	/** 类型名称 */
	private String codeTypeName;
	
	private String pkId;
	
	/** 类型 */
	private String codeType;
	
	/** 编码 */
	private String codeValue;
	
	/** 编码名称 */
	private String codeValueName;
	
	/** 第三方系统代码*/
	private String otherCodeValue;
	
	/** 备注*/
	private String remark;
	
	/** 顺序 */
	private String sortNo;
	
	/** 是否可编辑 */
	private String isEdit;

	
	public String getCodeTypeName() {
		return codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}

	public String getOtherCodeValue() {
		return otherCodeValue;
	}

	public void setOtherCodeValue(String otherCodeValue) {
		this.otherCodeValue = otherCodeValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	
	
	

	
	

}

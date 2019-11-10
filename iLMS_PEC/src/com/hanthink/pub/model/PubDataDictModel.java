package com.hanthink.pub.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：数据字典表 实体对象
 * 作者:dtp
 * 日期:2018-10-19 21:46:00
 * </pre>
 */
public class PubDataDictModel extends AbstractModel<Integer>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年11月24日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 925795106921804922L;

	/**
	 * 逻辑主键     
	 */
	protected Integer id; 
	
	/**
	 * 类型代码     
	 */
	protected String codeType; 
	
	/**
	 * 类型名称     
	 */
	protected String codeTypeName; 
	
	/**
	 * 编码     
	 */
	protected String codeValue; 
	
	/**
	 * 编码名称     
	 */
	protected String codeValueName; 
	
	/**
	 * 编码名称     
	 */
	protected String codeValueNameB; 
	
	/**
	 * 第三方系统代码     
	 */
	protected String otherCodeValue; 
	
	/**
	 * 备注     
	 */
	protected String remark; 
	
	/**
	 * 顺序     
	 */
	protected Integer sortNo; 
	
	/**
	 * 可否编辑     
	 */
	protected Integer isEdit; 
	
	/**
	 * 工厂     
	 */
	protected String factoryCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeTypeName() {
		return codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
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

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getCodeValueNameB() {
		return codeValueNameB;
	}

	public void setCodeValueNameB(String codeValueNameB) {
		this.codeValueNameB = codeValueNameB;
	} 
	
	
}

package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：生产工作日历表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubPartModel extends AbstractModel<Integer>{
	
	

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月29日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -4774730312502154381L;

	/**
	 * 主表  物料主数据表
	 */
	/**
	* 零件号
	*/
	protected String partNo ; 
	
	/**
	* 简号
	*/
	protected String partShortNo; 
	
	/**
	* 零件中文名称
	*/
	protected String partNameCn; 
	
	/**
	* 零件英文名称
	*/
	protected String partNameEn; 
	
	/**
	* 资材规格信息
	*/
	protected String partSpec; 
	
	/**
	* 采购类型
	*/
	protected String purchaseType; 
	
	/**
	* 零件单位
	*/
	protected String partUnit; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	protected java.util.Date lastModifiedTime;
	
	/**
	 * 工厂
	 */
	protected String factoryCode;

	/**
	 * 主表 物料主数据
	 */
	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getPartNameEn() {
		return partNameEn;
	}

	public void setPartNameEn(String partNameEn) {
		this.partNameEn = partNameEn;
	}

	public String getPartSpec() {
		return partSpec;
	}

	public void setPartSpec(String partSpec) {
		this.partSpec = partSpec;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getPartUnit() {
		return partUnit;
	}

	public void setPartUnit(String partUnit) {
		this.partUnit = partUnit;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	} 
	
}
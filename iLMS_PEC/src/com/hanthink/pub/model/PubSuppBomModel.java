package com.hanthink.pub.model;

import com.hotent.base.core.model.AbstractModel;

public class PubSuppBomModel extends AbstractModel<String>{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6342999864672819415L;

	private String id;
	
	/**工厂**/
	private String factoryCode;

	/**零件号**/
	private String partNo;
	/**简号**/
	private String partShortNo;
	/**零件名称**/
	private String partName;
	/**零件行号**/
	private String partRowNo;
	/**父件号**/
	private String partfId;
	/**父件简号**/
	private String partShortNoId;
	/**父件零件名称**/
	private String partNameId;
	/**用量**/
	private String num;
	/**用量单位**/
	private String useAgeAmountUnit;
	/**装配线工位**/
	private String lineStation;
	/**下一落点**/
	private String nextPlacement;
	/**创建时间**/
	private String creationTime;
	/**创建时间结束**/
	private String creationTimeEnd;
	/**最后修改时间**/
	private String lastModifiedTime;
	/**采购类型**/
	private String purchaseType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
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
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getUseAgeAmountUnit() {
		return useAgeAmountUnit;
	}
	public void setUseAgeAmountUnit(String useAgeAmountUnit) {
		this.useAgeAmountUnit = useAgeAmountUnit;
	}
	public String getLineStation() {
		return lineStation;
	}
	public void setLineStation(String lineStation) {
		this.lineStation = lineStation;
	}
	public String getNextPlacement() {
		return nextPlacement;
	}
	public void setNextPlacement(String nextPlacement) {
		this.nextPlacement = nextPlacement;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getPartfId() {
		return partfId;
	}
	public void setPartfId(String partfId) {
		this.partfId = partfId;
	}
	public String getPartShortNoId() {
		return partShortNoId;
	}
	public void setPartShortNoId(String partShortNoId) {
		this.partShortNoId = partShortNoId;
	}
	public String getPartNameId() {
		return partNameId;
	}
	public void setPartNameId(String partNameId) {
		this.partNameId = partNameId;
	}
	public String getCreationTimeEnd() {
		return creationTimeEnd;
	}
	public void setCreationTimeEnd(String creationTimeEnd) {
		this.creationTimeEnd = creationTimeEnd;
	}
	public String getPartRowNo() {
		return partRowNo;
	}
	public void setPartRowNo(String partRowNo) {
		this.partRowNo = partRowNo;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	
}

package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * <pre> 
 * 功能描述:出库管理数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvOutLogModel extends AbstractModel<String>{

	private static final long serialVersionUID = -2664131228378974187L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂 */
	private String factoryCode;
	/** 出库单号 */
	private String outNo;
	/** 出库仓库 */
	private String toDepotNo;
	/** 出库类型 */
	private String outType;
	/** 零件号 */
	private String partNo;
	/** 简号 */
	private String partShortNo;
	/** 零件名称 */
	private String partName;
	/** 出库数量(数量) */
	private String recNum;
	/** 收容数 */
	private String standardPac;
	/** 出库数量(箱) */
	private String recQty;
	/** 出库时间 */
	private String outTime;
	private String outTimeStart;
	private String outTimeEnd;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getOutNo() {
		return outNo;
	}
	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}
	public String getToDepotNo() {
		return toDepotNo;
	}
	public void setToDepotNo(String toDepotNo) {
		this.toDepotNo = toDepotNo;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
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
	public String getRecNum() {
		return recNum;
	}
	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}
	public String getStandardPac() {
		return standardPac;
	}
	public void setStandardPac(String standardPac) {
		this.standardPac = standardPac;
	}
	public String getRecQty() {
		return recQty;
	}
	public void setRecQty(String recQty) {
		this.recQty = recQty;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getOutTimeStart() {
		return outTimeStart;
	}
	public void setOutTimeStart(String outTimeStart) {
		this.outTimeStart = outTimeStart;
	}
	public String getOutTimeEnd() {
		return outTimeEnd;
	}
	public void setOutTimeEnd(String outTimeEnd) {
		this.outTimeEnd = outTimeEnd;
	}
	//出库类型下拉框
	private String label;
	private String value;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

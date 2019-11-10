package com.hanthink.gps.jit.vo;

import java.io.Serializable;

/**
 * @Desc    : 拉动零件提醒VO
 * @FileName: JitPartVO.java 
 * @CreateOn: 2016-7-27 下午03:03:48
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 * updated  by   chenyong   2016-11-10
 */
public class JitPartVO implements Serializable {

	private static final long serialVersionUID = 5218377544816800456L;
	
	/** 工厂代码 */
	private String factoryCode;
	
	/** 零件编码 */
	private String partNo;
	/** 零件简号 */
	private String partShortNo;
	/** 零件中文名 */
	private String partNameZh;
	/** MTO */
	private String mto;
	/** 落点 */
	private String location;
	/** 工作中心 */
	private String workcenter;
	/** MTOC前4位*/
	private String modelList;
	/** 最小生效日期*/
	private String effDate;
	/** 多供应商*/
	private String supplierList;
	/** 信息点 */
	private String planCode;
	/** 工厂 */
	private String factory;
	/** 修改前余量 */
	private String partRemainOld;
	/** 修改后余量 */
	private String partRemainNew;
	/** 手工调整量 */
	private String modNum;
	/** 操作人 */
	private String opeUser;
	/** 操作时间 */
	private String opeTime;
	/** 操作IP */
	private String opeIp;
	/** 序号 */
	private int no;
	/** 供应商类型  1-总装供应商未收货  2-焊装供应商未收货  3-发动机供应商未收货 4-外协未收货 **/
	private String repType;
	/** 报表日期 */
	private String repDate;
	/** 看板批次 */
	private String kbBatch;
	/** 单据生产时间 */
	private String creationTime;
	/** 拉动订单号 */
	private String orderNo;
	/** 到货批次 */
	private String arriveBatch;
	/** 供应商代码 */
	private String supplierNo;
	/** 供应商名称 */
	private String supplierName;
	/** 零件名称 */
	private String partName;
	/** 包装数 */
	private String standardPackage;
	/** 配送量 */
	private String orderQty;
	/** 未收货数量 */
	private String notRecQty;
	/** 看板代码 */
	private String kbCode;
	/** 看板生产进度 */
	private String kbProductSeqNo;
	/** 仓库类型 */
	private String shipDepotType;
	
	/**
	 * 获取工厂代码
	 * @return the factoryCode 工厂代码
	 */
	public String getFactoryCode() {
		return factoryCode;
	}
	/**
	 * 设置工厂代码
	 * @param factoryCode the factoryCode 工厂代码
	 */
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	/**
	 * 获取零件编码
	 * @return the partNo 零件编码
	 */
	public String getPartNo() {
		return partNo;
	}
	/**
	 * 设置零件编码
	 * @param partNo the partNo 零件编码
	 */
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	/**
	 * 获取零件简号
	 * @return the partShortNo 零件简号
	 */
	public String getPartShortNo() {
		return partShortNo;
	}
	/**
	 * 设置零件简号
	 * @param partShortNo the partShortNo 零件简号
	 */
	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}
	/**
	 * 获取零件中文名
	 * @return the partNameZh 零件中文名
	 */
	public String getPartNameZh() {
		return partNameZh;
	}
	/**
	 * 设置零件中文名
	 * @param partNameZh the partNameZh 零件中文名
	 */
	public void setPartNameZh(String partNameZh) {
		this.partNameZh = partNameZh;
	}
	/**
	 * 获取MTO
	 * @return the mto MTO
	 */
	public String getMto() {
		return mto;
	}
	/**
	 * 设置MTO
	 * @param mto the mto MTO
	 */
	public void setMto(String mto) {
		this.mto = mto;
	}
	/**
	 * 获取落点
	 * @return the location 落点
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * 设置落点
	 * @param location the location 落点
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * 获得工作中心
	 * @return the workcenter 工作中心
	 */
	public String getWorkcenter() {
		return workcenter;
	}
	/**
	 * 设置工作中心
	 * @param workcenter 工作中心
	 */
	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	/**
	 * 获得MTOC前4位
	 * @return the modelList MTOC前4位
	 */
	public String getModelList() {
		return modelList;
	}
	/**
	 * 设置MTOC前4位
	 * @param modelList  MTOC前4位
	 */
	public void setModelList(String modelList) {
		this.modelList = modelList;
	}
	/**
	 * 获得最小生效日期
	 * @return the effDate 最小生效日期
	 */
	public String getEffDate() {
		return effDate;
	}
	/**
	 * 设置最小生效日期
	 * @param effDate 最小生效日期
	 */
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	/**
	 * 获得供应商列表
	 * @return the supplierList 供应商列表
	 */
	public String getSupplierList() {
		return supplierList;
	}
	/**
	 * 设置多供应商
	 * @param supplierList 多供应商
	 */
	public void setSupplierList(String supplierList) {
		this.supplierList = supplierList;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getPartRemainOld() {
		return partRemainOld;
	}
	public void setPartRemainOld(String partRemainOld) {
		this.partRemainOld = partRemainOld;
	}
	public String getPartRemainNew() {
		return partRemainNew;
	}
	public void setPartRemainNew(String partRemainNew) {
		this.partRemainNew = partRemainNew;
	}
	public String getOpeUser() {
		return opeUser;
	}
	public void setOpeUser(String opeUser) {
		this.opeUser = opeUser;
	}
	public String getOpeTime() {
		return opeTime;
	}
	public void setOpeTime(String opeTime) {
		this.opeTime = opeTime;
	}
	public String getOpeIp() {
		return opeIp;
	}
	public void setOpeIp(String opeIp) {
		this.opeIp = opeIp;
	}
	public String getModNum() {
		return modNum;
	}
	public void setModNum(String modNum) {
		this.modNum = modNum;
	}
	public String getRepType() {
		return repType;
	}
	public void setRepType(String repType) {
		this.repType = repType;
	}
	public String getKbBatch() {
		return kbBatch;
	}
	public void setKbBatch(String kbBatch) {
		this.kbBatch = kbBatch;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getArriveBatch() {
		return arriveBatch;
	}
	public void setArriveBatch(String arriveBatch) {
		this.arriveBatch = arriveBatch;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getStandardPackage() {
		return standardPackage;
	}
	public void setStandardPackage(String standardPackage) {
		this.standardPackage = standardPackage;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getNotRecQty() {
		return notRecQty;
	}
	public void setNotRecQty(String notRecQty) {
		this.notRecQty = notRecQty;
	}
	public String getRepDate() {
		return repDate;
	}
	public void setRepDate(String repDate) {
		this.repDate = repDate;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getKbCode() {
		return kbCode;
	}
	public void setKbCode(String kbCode) {
		this.kbCode = kbCode;
	}
	public String getKbProductSeqNo() {
		return kbProductSeqNo;
	}
	public void setKbProductSeqNo(String kbProductSeqNo) {
		this.kbProductSeqNo = kbProductSeqNo;
	}
	public String getShipDepotType() {
		return shipDepotType;
	}
	public void setShipDepotType(String shipDepotType) {
		this.shipDepotType = shipDepotType;
	}
	
	

}

package com.hanthink.gps.pub.vo;



/**
 * 订单统计数量VO
 * 
 * 
 */
public class OrderInfoVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3428760141622141370L;
	// 订单类型
	private String orderType;
	//订单类型名称
	private String orderTypeName;
	// 订单总数
	private String totalNum;
	// 未打印订单数量
	private String unPrintNum;
	// 已打印订单数量
	private String printNum;
	// 未发货订单数量
	private String unsendNum;
	// 已发货订单数量
	private String sendNum;
	// 部分发货订单数量
	private String sendPartNum;
	// 供应商代码
	private String supplierNo;
	// 未收货订单数量
	private String unreceiveNum;
	// 部分收货订单数量
	private String partreceiveNum;
	// 已收货订单数量
	private String receiveNum;
	// 未过期订单数量
	private String unoverdueNum;
	// 已过期订单数量
	private String overdueNum;
	// 存储用户类型
	private String userType;
	// 用户id
	private String userId;
	// 内部用户id zhangye 20100802追加
	private String gamcId;
	// 未下载订单数量 zhangye 20101029追加
	private String unDownLoadNum;
	// 样件订单管理权限标识
	private String pieceOrderManageFlg;
	// 样件订单查询权限标识
	private String piectOrderQueryFlg;
	// 资材订单管理权限标识
	private String norderManageFlg;
	// 资材订单查询权限标识
	private String norderQueryFlg;
	// 零件订单管理权限标识
	private String partsManageFlg;
	// 例外订单查询权限标识
	private String exceptManageFlg;
	// 销售订单查询权限标识
	private String saleManageFlg;
	// jit订单查询权限标识
	private String jitManageFlg;
	// 同步物流订单查询权限标识
	private String synManageFlg;
	//用户类型
	private String curSupplierNo;
	//工厂
	private String factory;
	
	
	
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	
	
	public String getCurSupplierNo() {
		return curSupplierNo;
	}

	public void setCurSupplierNo(String curSupplierNo) {
		this.curSupplierNo = curSupplierNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the gamcId
	 */
	public String getGamcId() {
		return gamcId;
	}

	/**
	 * @param gamcId the gamcId to set
	 */
	public void setGamcId(String gamcId) {
		this.gamcId = gamcId;
	}

	// public Number getTotalNum() {
	// return totalNum;
	// }
	// public void setTotalNum(Number totalNum) {
	// this.totalNum = totalNum;
	// }
	public String getTotalNum() {
		return totalNum;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getUnPrintNum() {
		return unPrintNum;
	}

	public void setUnPrintNum(String unPrintNum) {
		this.unPrintNum = unPrintNum;
	}

	public String getPrintNum() {
		return printNum;
	}

	public void setPrintNum(String printNum) {
		this.printNum = printNum;
	}

	public String getUnsendNum() {
		return unsendNum;
	}

	public void setUnsendNum(String unsendNum) {
		this.unsendNum = unsendNum;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public String getUnreceiveNum() {
		return unreceiveNum;
	}

	public void setUnreceiveNum(String unreceiveNum) {
		this.unreceiveNum = unreceiveNum;
	}

	public String getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(String receiveNum) {
		this.receiveNum = receiveNum;
	}

	public String getUnoverdueNum() {
		return unoverdueNum;
	}

	public void setUnoverdueNum(String unoverdueNum) {
		this.unoverdueNum = unoverdueNum;
	}

	public String getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(String overdueNum) {
		this.overdueNum = overdueNum;
	}

	public String getPartreceiveNum() {
		return partreceiveNum;
	}

	public void setPartreceiveNum(String partreceiveNum) {
		this.partreceiveNum = partreceiveNum;
	}

	/**
	 * 获取 pieceOrderManageFlg
	 *
	 * @return pieceOrderManageFlg
	 */
	public String getPieceOrderManageFlg() {
		return pieceOrderManageFlg;
	}

	/**
	 * 设定pieceOrderManageFlg
	 *
	 * @param pieceOrderManageFlg pieceOrderManageFlg
	 */
	public void setPieceOrderManageFlg(String pieceOrderManageFlg) {
		this.pieceOrderManageFlg = pieceOrderManageFlg;
	}

	/**
	 * 获取 piectOrderQueryFlg
	 *
	 * @return piectOrderQueryFlg
	 */
	public String getPiectOrderQueryFlg() {
		return piectOrderQueryFlg;
	}

	/**
	 * 设定piectOrderQueryFlg
	 *
	 * @param piectOrderQueryFlg piectOrderQueryFlg
	 */
	public void setPiectOrderQueryFlg(String piectOrderQueryFlg) {
		this.piectOrderQueryFlg = piectOrderQueryFlg;
	}

	/**
	 * 获取 userType
	 *
	 * @return userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 设定userType
	 *
	 * @param userType userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 获取 userId
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设定userId
	 *
	 * @param userId userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取 norderManageFlg
	 *
	 * @return norderManageFlg
	 */
	public String getNorderManageFlg() {
		return norderManageFlg;
	}

	/**
	 * 设定norderManageFlg
	 *
	 * @param norderManageFlg norderManageFlg
	 */
	public void setNorderManageFlg(String norderManageFlg) {
		this.norderManageFlg = norderManageFlg;
	}

	/**
	 * 获取 norderQueryFlg
	 *
	 * @return norderQueryFlg
	 */
	public String getNorderQueryFlg() {
		return norderQueryFlg;
	}

	/**
	 * 设定norderQueryFlg
	 *
	 * @param norderQueryFlg norderQueryFlg
	 */
	public void setNorderQueryFlg(String norderQueryFlg) {
		this.norderQueryFlg = norderQueryFlg;
	}

	/**
	 * 获取 partsManageFlg
	 *
	 * @return partsManageFlg
	 */
	public String getPartsManageFlg() {
		return partsManageFlg;
	}

	/**
	 * 设定partsManageFlg
	 *
	 * @param partsManageFlg partsManageFlg
	 */
	public void setPartsManageFlg(String partsManageFlg) {
		this.partsManageFlg = partsManageFlg;
	}

	/**
	 * 获取 exceptManageFlg
	 *
	 * @return exceptManageFlg
	 */
	public String getExceptManageFlg() {
		return exceptManageFlg;
	}

	/**
	 * 设定exceptManageFlg
	 *
	 * @param exceptManageFlg exceptManageFlg
	 */
	public void setExceptManageFlg(String exceptManageFlg) {
		this.exceptManageFlg = exceptManageFlg;
	}

	public String getSendPartNum() {
		return sendPartNum;
	}

	public void setSendPartNum(String sendPartNum) {
		this.sendPartNum = sendPartNum;
	}

	public String getSaleManageFlg() {
		return saleManageFlg;
	}

	public void setSaleManageFlg(String saleManageFlg) {
		this.saleManageFlg = saleManageFlg;
	}

	public String getJitManageFlg() {
		return jitManageFlg;
	}

	public void setJitManageFlg(String jitManageFlg) {
		this.jitManageFlg = jitManageFlg;
	}

	public String getSynManageFlg() {
		return synManageFlg;
	}

	public void setSynManageFlg(String synManageFlg) {
		this.synManageFlg = synManageFlg;
	}

	/**
	 * @return the unDownLoadNum
	 */
	public String getUnDownLoadNum() {
		return unDownLoadNum;
	}

	/**
	 * @param unDownLoadNum the unDownLoadNum to set
	 */
	public void setUnDownLoadNum(String unDownLoadNum) {
		this.unDownLoadNum = unDownLoadNum;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	

}

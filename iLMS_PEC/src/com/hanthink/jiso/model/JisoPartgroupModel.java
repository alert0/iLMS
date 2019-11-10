package com.hanthink.jiso.model;

/**
  * <pre> 
  * 描述：MM_JISO_PARTGROUP 实体对象
  * 作者:dtp
  * 日期:2018-09-12 15:44:45
  * </pre>
  */
public class JisoPartgroupModel {
	
	/**
	 * ID
     */
	protected Long id; 
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 零件组代码
     */
	protected String partgroupNo; 
	
	/**
	 * 零件组代码显示
	 */
	protected String partgroupNoDisplay;
	
	/**
	 * 零件组名称
     */
	protected String partgroupName; 
	
	/**
	 * 待组票车数
	 */
	protected String notDealVeh;
	
	/**
	 * 到货仓库
     */
	protected String arrDepot; 
	
	/**
	 * 组票台套数
     */
	protected String insProductNum; 
	
	/**
	 * 组单指示票数
     */
	protected String orderInsNum; 
	
	/**
	 * 备件提前台套数
     */
	protected String prepareNum; 
	
	/**
	 * 发车提前台套数
     */
	protected String dispatchNum; 
	
	/**
	 * 发货提前台套数
     */
	protected String deliveryNum; 
	
	/**
	 * 到货提前台套数
     */
	protected String arriveNum; 
	
	/**
	 * 配送提前台套数
     */
	protected String distributionNum; 
	
	/**
	 * 组票是否考虑出货地切换
		0：不考虑出货地切换
		1：考虑出货地切换
     */
	protected String genInsWay; 
	
	/**
	 * 是否生成订单数据字典类型“TRUE_FALSE"
     */
	protected String orderFlg; 
	
	/**
	 * 生效时间
     */
	protected String effStart; 
	
	/**
	 * 失效时间
     */
	protected String effEnd; 
	
	/**
	 * 创建人
     */
	protected String creationUser; 
	
	/**
	 * 创建时间
     */
	protected String creationTime; 
	
	/**
	 * 最后修改用户
     */
	protected String lastModifiedUser; 
	
	/**
	 * 最后修改时间
     */
	protected String lastModifiedTime; 
	
	/**
	 * 最后修改IP
     */
	protected String lastModifiedIp; 
	

	/**
	 * 手工组票请求时间
	 */
	protected String manuReqTime;
	
	/**
	 * 手工组票时间
	 */
	protected String dealTime;
	
	/**
	 * 手工组票操作人
	 */
	protected String manuReqUser;
	
	/**
	 * 下一组票方式
	 */
	protected String nextGroupInsWay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPartgroupNo() {
		return partgroupNo;
	}

	public void setPartgroupNo(String partgroupNo) {
		this.partgroupNo = partgroupNo;
	}

	public String getPartgroupName() {
		return partgroupName;
	}

	public void setPartgroupName(String partgroupName) {
		this.partgroupName = partgroupName;
	}

	public String getArrDepot() {
		return arrDepot;
	}

	public void setArrDepot(String arrDepot) {
		this.arrDepot = arrDepot;
	}

	public String getInsProductNum() {
		return insProductNum;
	}

	public void setInsProductNum(String insProductNum) {
		this.insProductNum = insProductNum;
	}

	public String getOrderInsNum() {
		return orderInsNum;
	}

	public void setOrderInsNum(String orderInsNum) {
		this.orderInsNum = orderInsNum;
	}

	public String getPrepareNum() {
		return prepareNum;
	}

	public void setPrepareNum(String prepareNum) {
		this.prepareNum = prepareNum;
	}

	public String getDispatchNum() {
		return dispatchNum;
	}

	public void setDispatchNum(String dispatchNum) {
		this.dispatchNum = dispatchNum;
	}

	public String getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(String deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public String getArriveNum() {
		return arriveNum;
	}

	public void setArriveNum(String arriveNum) {
		this.arriveNum = arriveNum;
	}

	public String getDistributionNum() {
		return distributionNum;
	}

	public void setDistributionNum(String distributionNum) {
		this.distributionNum = distributionNum;
	}

	public String getGenInsWay() {
		return genInsWay;
	}

	public void setGenInsWay(String genInsWay) {
		this.genInsWay = genInsWay;
	}

	public String getOrderFlg() {
		return orderFlg;
	}

	public void setOrderFlg(String orderFlg) {
		this.orderFlg = orderFlg;
	}

	public String getEffStart() {
		return effStart;
	}

	public void setEffStart(String effStart) {
		this.effStart = effStart;
	}

	public String getEffEnd() {
		return effEnd;
	}

	public void setEffEnd(String effEnd) {
		this.effEnd = effEnd;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public String getManuReqTime() {
		return manuReqTime;
	}

	public void setManuReqTime(String manuReqTime) {
		this.manuReqTime = manuReqTime;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getManuReqUser() {
		return manuReqUser;
	}

	public void setManuReqUser(String manuReqUser) {
		this.manuReqUser = manuReqUser;
	}

	public String getNextGroupInsWay() {
		return nextGroupInsWay;
	}

	public void setNextGroupInsWay(String nextGroupInsWay) {
		this.nextGroupInsWay = nextGroupInsWay;
	}

	public String getPartgroupNoDisplay() {
		return partgroupNoDisplay;
	}

	public void setPartgroupNoDisplay(String partgroupNoDisplay) {
		this.partgroupNoDisplay = partgroupNoDisplay;
	}

	public String getNotDealVeh() {
		return notDealVeh;
	}

	public void setNotDealVeh(String notDealVeh) {
		this.notDealVeh = notDealVeh;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	
	
}
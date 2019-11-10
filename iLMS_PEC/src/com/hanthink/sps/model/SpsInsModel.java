package com.hanthink.sps.model;
import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：MM_SPS_INS 实体对象
  * 作者:dtp
  * 日期:2018-10-16 12:00:33
  * </pre>
  */
public class SpsInsModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 3722647565275200379L;

	protected String id;
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * 模板编码
	 */
	protected String mouldCode;
	
	/**
	 * 版本号
	 */
	protected String version;
	
	/**
	 * SPS指示票号
	 */
	protected String insNo; 
	
	/**
	 * 信息点
	 */
	protected String planCode; 
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
	/**
	 * 模板ID
	 */
	protected String mouldId; 
	
	/**
	 * 产品订单号
	 */
	protected String orderNo; 
	
	/**
	 * VIN码
	 */
	protected String vin; 
	
	/**
	 * 车型
	 */
	protected String modelCode; 
	
	/**
	 * 车间投入号
	 */
	protected String wcSeqno; 
	
	/**
	 * 生产投入号
	 */
	protected String plSeqno; 
	
	/**
	 * 过点时间
	 */
	protected String passTime; 
	
	/**
	 * 过点时间至
	 */
	protected String passTimeTo;
	
	/**
	 * 打印状态 0：未打印 1：已打印     
	 */
	protected String printStatus; 
	
	/**
	 * 打印时间
	 */
	protected String printTime; 
	
	/**
	 * 打印人员
	 */
	protected String printUser; 
	
	/**
	 * 打印IP
	 */
	protected String printIp;
	
	/**
	 * 创建时间
	 */
	protected String creationTime; 
	
	/**
	 * 是否发送打印平台
	 */
	protected String isLoad; 
	
	/**
	 * 发送打印平台时间
	 */
	protected String loadTime;
	
	/**
	 * 打印机
	 */
	protected String printer;
	
	/**
	 * 模板名称
	 */
	protected String mouldName;
	
	/**
	 * 打印机
	 */
	protected String printerName;
	
	/**
	 * 模板位置号
	 */
	protected Integer mouldPlace; 
	
	/**
	 * 零件号
	 */
	protected String partNo; 
	
	/**
	 * 零件名称
	 */
	protected String partNameCn;
	
	/**
	 * 简号
	 */
	protected String partShortNo; 
	
	/**
	 * 数量
	 */
	protected Long quantity; 
	
	/**
	 * 零件记号
	 */
	protected String partMark; 
	
	/**
	 * 货架号
	 */
	protected String shelfNo;

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getInsNo() {
		return insNo;
	}

	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanCodeDesc() {
		return planCodeDesc;
	}

	public void setPlanCodeDesc(String planCodeDesc) {
		this.planCodeDesc = planCodeDesc;
	}

	public String getMouldId() {
		return mouldId;
	}

	public void setMouldId(String mouldId) {
		this.mouldId = mouldId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getWcSeqno() {
		return wcSeqno;
	}

	public void setWcSeqno(String wcSeqno) {
		this.wcSeqno = wcSeqno;
	}

	public String getPlSeqno() {
		return plSeqno;
	}

	public void setPlSeqno(String plSeqno) {
		this.plSeqno = plSeqno;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getPassTimeTo() {
		return passTimeTo;
	}

	public void setPassTimeTo(String passTimeTo) {
		this.passTimeTo = passTimeTo;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getPrintUser() {
		return printUser;
	}

	public void setPrintUser(String printUser) {
		this.printUser = printUser;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getIsLoad() {
		return isLoad;
	}

	public void setIsLoad(String isLoad) {
		this.isLoad = isLoad;
	}

	public String getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(String loadTime) {
		this.loadTime = loadTime;
	}

	public String getPrinter() {
		return printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public Integer getMouldPlace() {
		return mouldPlace;
	}

	public void setMouldPlace(Integer mouldPlace) {
		this.mouldPlace = mouldPlace;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getPartMark() {
		return partMark;
	}

	public void setPartMark(String partMark) {
		this.partMark = partMark;
	}

	public String getShelfNo() {
		return shelfNo;
	}

	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}

	public String getMouldName() {
		return mouldName;
	}

	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getMouldCode() {
		return mouldCode;
	}

	public void setMouldCode(String mouldCode) {
		this.mouldCode = mouldCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPrintIp() {
		return printIp;
	}

	public void setPrintIp(String printIp) {
		this.printIp = printIp;
	}
	
	
}
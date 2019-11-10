package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：供应商主数据表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubSupplierModel extends AbstractModel<Integer>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月30日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 1149376492370420074L;

	private String factoryCode;
	
	/**
	 * 主表  供应商主数据表
	 */
	/**
	* 出货地编码
	*/
	private String supFactory ; 
	
	/**
	* 供应商代码
	*/
	private String supplierNo; 
	
	/**
	* 供应商名称
	*/
	private String supplierName; 
	
	/**
	* 详细地址
	*/
	private String detailAddr; 
	
	/**
	* 供应商状态
	*/
	private String supStatus; 
	
	/**
	* 邮箱
	*/
	private String email; 
	
	/**
	* 联系人
	*/
	private String contact; 
	
	/**
	* 手机号码
	*/
	private String mobileNo; 
	
	/**
	* 电话号码
	*/
	private String telNo; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime;
	
	/**用户提出需要添加的字段************************************/
	/**
	 * 副表，供应商明细表
	 */
	/**
	* 出货地地址
	*/
	private String supFactoryAddr ;
	
	/**
	* 重要联络人部门
	*/
	private String importDep;
	
	/**
	* 重要联络人职位
	*/
	private String importPosition;
	
	/**
	* 重要联络人姓名
	*/
	private String importName;
	
	/**
	* 重要联络人移动电话
	*/
	private String importMobile;
	
	/**
	* 重要联络人固定电话
	*/
	private String importTel;
	
	/**
	* 重要联络人邮箱地址
	*/
	private String importMail;
	
	/**
	* PT订单联络人部门
	*/
	private String ptDep;
	
	/**
	* PT订单联络人职位
	*/
	private String ptPosition;
	
	/**
	* PT订单联络人姓名
	*/
	private String ptName;
	
	/**
	* PT订单联络人移动电话
	*/
	private String ptMobile;
	
	/**
	* PT订单联络人固定电话
	*/
	private String ptTel;
	
	/**
	* PT订单联络人邮箱地址
	*/
	private String ptMail;
	
	/**
	* 量产订单联络人部门
	*/
	private String massDep;
	
	/**
	* 量产订单联络人职位
	*/
	private String massPosition;
	
	/**
	* 量产订单联络人姓名
	*/
	private String massName;
	
	/**
	* 量产订单联络人移动电话
	*/
	private String massMobile;
	
	/**
	* 量产订单联络人固定电话
	*/
	private String massTel;
	
	/**
	* 量产订单联络人邮箱地址
	*/
	private String massMail;

	/**异常联络人部门A**/
	private String excepDepA;
	/**异常联络人部门B**/
	private String excepDepB;
	/**异常联络人职位A**/
	private String excepPositionA;
	/**异常联络人职位B**/
	private String excepPositionB;
	/**异常联络人姓名A**/
	private String excepNameA;
	/**异常联络人姓名B**/
	private String excepNameB;
	
	/**异常联络人移动电话A**/
	private String excepMobileA;
	/**异常联络人移动电话B**/
	private String excepMobileB;
	/**异常联络人固定电话A**/
	private String excepTelA;
	/**异常联络人固定电话B**/
	private String excepTelB;
	/**异常联络人邮箱地址A**/
	private String excepMailA;
	/**异常联络人邮箱地址B**/
	private String excepMailB;
	
	/**设变联络人信息*********************/
	/**设变联络人部门**/
	private String deviceDep;
	/**设变联络人职位**/
	private String devicePosition;
	/**设变联络人姓名**/
	private String deviceName;
	/**设变联络人移动电话**/
	private String deviceMobile;
	/**设变联络人固定电话**/
	private String deviceTel;
	/**设变联络人邮箱地址**/
	private String deviceMail;
	
	/**重要联络人信息**********************/
	/**重要联络人部门1**/
	private String importDepA;
	/**重要联络人职位1**/
	private String importPositionA;
	/**重要联络人姓名1**/
	private String importNameA;
	/**重要联络人移动电话1**/
	private String importMobileA;
	/**重要联络人固定电话1**/
	private String importTelA;
	/**重要联络人邮箱地址1**/
	private String importMailA;
	
	/**PT订单联络人部门1**/
	private String ptDepA;
	/**PT订单联络人职位1**/
	private String ptPositionA;
	/**PT订单联络人姓名1**/
	private String ptNameA;
	/**PT订单联络人移动电话1**/
	private String ptMobileA;
	/**PT订单联络人固定电话1**/
	private String ptTelA;
	/**PT订单联络人邮箱地址1**/
	private String ptMailA;
	
	/**量产订单联络人信息***************************/
	/**量产订单联络人部门1**/
	private String massDepA;
	/**量产订单联络人职位1**/
	private String massPositionA;
	/**量产订单联络人姓名1**/
	private String massNameA;
	/**量产订单联络人移动电话1**/
	private String massMobileA;
	/**量产订单联络人固定电话1**/
	private String massTelA;
	/**量产订单联络人邮箱地址1**/
	private String massMailA;
	
	/**设变联络人信息***************************/
	/**设变联络人部门1**/
	private String deviceDepA;
	/**设变联络人职位1**/
	private String devicePositionA;
	/**设变联络人姓名1**/
	private String deviceNameA;
	/**设变联络人移动电话1**/
	private String deviceMobileA;
	/**设变联络人固定电话1**/
	private String deviceTelA;
	/**设变联络人邮箱地址1**/
	private String deviceMailA;
	
	/**包装联络人信息***************************/
	/**包装联络人部门1**/
	private String packDepA;
	/**包装联络人职位1**/
	private String packPositionA;
	/**包装联络人姓名1**/
	private String packNameA;
	/**包装联络人移动电话1**/
	private String packMobileA;
	/**包装联系人固定电话1**/
	private String packTelA;
	/**包装联络人邮箱地址1**/
	private String packMailA;
	
	/**包装联络人信息***************************/
	/**包装联络人部门2**/
	private String packDepB;
	/**包装联络人职位2**/
	private String packPositionB;
	/**包装联络人姓名2**/
	private String packNameB;
	/**包装联络人移动电话2**/
	private String packMobileB;
	/**包装联系人固定电话2**/
	private String packTelB;
	/**包装联络人邮箱地址2**/
	private String packMailB;
	
	/**PT物流对应联络人信息***************************/
	/**PT物流对应联络人部门**/
	private String ptLogisticsDep;
	/**PT物流对应联络人姓名**/
	private String ptLogisticsName;
	/**PT物流对应联络人职位**/
	private String ptLogisticsPosition;
	/**PT物流对应联络人移动电话**/
	private String ptLogisticsMobile;
	/**PT物流对应联络人固定电话**/
	private String ptLogisticsTel;
	/**PT物流对应联络人邮箱地址**/
	private String ptLogisticsMail;
	
	/**PT物流对应联络人信息***************************/
	/**PT物流对应联络人部门1**/
	private String ptLogisticsDepA;
	/**PT物流对应联络人姓名1**/
	private String ptLogisticsNameA;
	/**PT物流对应联络人职位1**/
	private String ptLogisticsPositionA;
	/**PT物流对应联络人移动电话1**/
	private String ptLogisticsMobileA;
	/**PT物流对应联络人固定电话1**/
	private String ptLogisticsTelA;
	/**PT物流对应联络人邮箱地址1**/
	private String ptLogisticsMailA;
	
	/**量产物流对应联络人信息***************************/
	/**量产物流对应联络人部门**/
	private String massLogisticsDep;
	/**量产物流对应联络人职位**/
	private String massLogisticsPosition;
	/**量产物流对应联络人姓名**/
	private String massLogisticsName;
	/**量产物流对应联络人移动电话**/
	private String massLogisticsMobile;
	/**量产物流对应联络人固定电话**/
	private String massLogisticsTel;
	/**量产物流对应联络人邮箱地址**/
	private String massLogisticsMail;
	
	/**量产物流对应联络人信息***************************/
	/**量产物流对应联络人部门1**/
	private String massLogisticsDepA;
	/**量产物流对应联络人职位1**/
	private String massLogisticsPositionA;
	/**量产物流对应联络人姓名1**/
	private String massLogisticsNameA;
	/**量产物流对应联络人移动电话1**/
	private String massLogisticsMobileA;
	/**量产物流对应联络人固定电话1**/
	private String massLogisticsTelA;
	/**量产物流对应联络人邮箱地址1**/
	private String massLogisticsMailA;
	
	/**用户提出需要添加的字段************************************/
	
	/**
	 * 副表 数据字典表
	 */
	private String codeValueName;

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
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

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getSupStatus() {
		return supStatus;
	}

	public void setSupStatus(String supStatus) {
		this.supStatus = supStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
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

	public String getSupFactoryAddr() {
		return supFactoryAddr;
	}

	public void setSupFactoryAddr(String supFactoryAddr) {
		this.supFactoryAddr = supFactoryAddr;
	}

	public String getImportDep() {
		return importDep;
	}

	public void setImportDep(String importDep) {
		this.importDep = importDep;
	}

	public String getImportPosition() {
		return importPosition;
	}

	public void setImportPosition(String importPosition) {
		this.importPosition = importPosition;
	}

	public String getImportName() {
		return importName;
	}

	public void setImportName(String importName) {
		this.importName = importName;
	}

	public String getImportMobile() {
		return importMobile;
	}

	public void setImportMobile(String importMobile) {
		this.importMobile = importMobile;
	}

	public String getImportTel() {
		return importTel;
	}

	public void setImportTel(String importTel) {
		this.importTel = importTel;
	}

	public String getImportMail() {
		return importMail;
	}

	public void setImportMail(String importMail) {
		this.importMail = importMail;
	}

	public String getPtDep() {
		return ptDep;
	}

	public void setPtDep(String ptDep) {
		this.ptDep = ptDep;
	}

	public String getPtPosition() {
		return ptPosition;
	}

	public void setPtPosition(String ptPosition) {
		this.ptPosition = ptPosition;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtMobile() {
		return ptMobile;
	}

	public void setPtMobile(String ptMobile) {
		this.ptMobile = ptMobile;
	}

	public String getPtTel() {
		return ptTel;
	}

	public void setPtTel(String ptTel) {
		this.ptTel = ptTel;
	}

	public String getPtMail() {
		return ptMail;
	}

	public void setPtMail(String ptMail) {
		this.ptMail = ptMail;
	}

	public String getMassDep() {
		return massDep;
	}

	public void setMassDep(String massDep) {
		this.massDep = massDep;
	}

	public String getMassPosition() {
		return massPosition;
	}

	public void setMassPosition(String massPosition) {
		this.massPosition = massPosition;
	}

	public String getMassName() {
		return massName;
	}

	public void setMassName(String massName) {
		this.massName = massName;
	}

	public String getMassMobile() {
		return massMobile;
	}

	public void setMassMobile(String massMobile) {
		this.massMobile = massMobile;
	}

	public String getMassTel() {
		return massTel;
	}

	public void setMassTel(String massTel) {
		this.massTel = massTel;
	}

	public String getMassMail() {
		return massMail;
	}

	public void setMassMail(String massMail) {
		this.massMail = massMail;
	}

	public String getExcepDepA() {
		return excepDepA;
	}

	public void setExcepDepA(String excepDepA) {
		this.excepDepA = excepDepA;
	}

	public String getExcepDepB() {
		return excepDepB;
	}

	public void setExcepDepB(String excepDepB) {
		this.excepDepB = excepDepB;
	}

	public String getExcepPositionA() {
		return excepPositionA;
	}

	public void setExcepPositionA(String excepPositionA) {
		this.excepPositionA = excepPositionA;
	}

	public String getExcepPositionB() {
		return excepPositionB;
	}

	public void setExcepPositionB(String excepPositionB) {
		this.excepPositionB = excepPositionB;
	}

	public String getExcepNameA() {
		return excepNameA;
	}

	public void setExcepNameA(String excepNameA) {
		this.excepNameA = excepNameA;
	}

	public String getExcepNameB() {
		return excepNameB;
	}

	public void setExcepNameB(String excepNameB) {
		this.excepNameB = excepNameB;
	}

	public String getExcepMobileA() {
		return excepMobileA;
	}

	public void setExcepMobileA(String excepMobileA) {
		this.excepMobileA = excepMobileA;
	}

	public String getExcepMobileB() {
		return excepMobileB;
	}

	public void setExcepMobileB(String excepMobileB) {
		this.excepMobileB = excepMobileB;
	}

	public String getExcepTelA() {
		return excepTelA;
	}

	public void setExcepTelA(String excepTelA) {
		this.excepTelA = excepTelA;
	}

	public String getExcepTelB() {
		return excepTelB;
	}

	public void setExcepTelB(String excepTelB) {
		this.excepTelB = excepTelB;
	}

	public String getExcepMailA() {
		return excepMailA;
	}

	public void setExcepMailA(String excepMailA) {
		this.excepMailA = excepMailA;
	}

	public String getExcepMailB() {
		return excepMailB;
	}

	public void setExcepMailB(String excepMailB) {
		this.excepMailB = excepMailB;
	}

	public String getDeviceDep() {
		return deviceDep;
	}

	public void setDeviceDep(String deviceDep) {
		this.deviceDep = deviceDep;
	}

	public String getDevicePosition() {
		return devicePosition;
	}

	public void setDevicePosition(String devicePosition) {
		this.devicePosition = devicePosition;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceMobile() {
		return deviceMobile;
	}

	public void setDeviceMobile(String deviceMobile) {
		this.deviceMobile = deviceMobile;
	}

	public String getDeviceTel() {
		return deviceTel;
	}

	public void setDeviceTel(String deviceTel) {
		this.deviceTel = deviceTel;
	}

	public String getDeviceMail() {
		return deviceMail;
	}

	public void setDeviceMail(String deviceMail) {
		this.deviceMail = deviceMail;
	}

	public String getImportDepA() {
		return importDepA;
	}

	public void setImportDepA(String importDepA) {
		this.importDepA = importDepA;
	}

	public String getImportPositionA() {
		return importPositionA;
	}

	public void setImportPositionA(String importPositionA) {
		this.importPositionA = importPositionA;
	}

	public String getImportNameA() {
		return importNameA;
	}

	public void setImportNameA(String importNameA) {
		this.importNameA = importNameA;
	}

	public String getImportMobileA() {
		return importMobileA;
	}

	public void setImportMobileA(String importMobileA) {
		this.importMobileA = importMobileA;
	}

	public String getImportTelA() {
		return importTelA;
	}

	public void setImportTelA(String importTelA) {
		this.importTelA = importTelA;
	}

	public String getImportMailA() {
		return importMailA;
	}

	public void setImportMailA(String importMailA) {
		this.importMailA = importMailA;
	}

	public String getPtDepA() {
		return ptDepA;
	}

	public void setPtDepA(String ptDepA) {
		this.ptDepA = ptDepA;
	}

	public String getPtPositionA() {
		return ptPositionA;
	}

	public void setPtPositionA(String ptPositionA) {
		this.ptPositionA = ptPositionA;
	}

	public String getPtNameA() {
		return ptNameA;
	}

	public void setPtNameA(String ptNameA) {
		this.ptNameA = ptNameA;
	}

	public String getPtMobileA() {
		return ptMobileA;
	}

	public void setPtMobileA(String ptMobileA) {
		this.ptMobileA = ptMobileA;
	}

	public String getPtTelA() {
		return ptTelA;
	}

	public void setPtTelA(String ptTelA) {
		this.ptTelA = ptTelA;
	}

	public String getPtMailA() {
		return ptMailA;
	}

	public void setPtMailA(String ptMailA) {
		this.ptMailA = ptMailA;
	}

	public String getMassDepA() {
		return massDepA;
	}

	public void setMassDepA(String massDepA) {
		this.massDepA = massDepA;
	}

	public String getMassPositionA() {
		return massPositionA;
	}

	public void setMassPositionA(String massPositionA) {
		this.massPositionA = massPositionA;
	}

	public String getMassNameA() {
		return massNameA;
	}

	public void setMassNameA(String massNameA) {
		this.massNameA = massNameA;
	}

	public String getMassMobileA() {
		return massMobileA;
	}

	public void setMassMobileA(String massMobileA) {
		this.massMobileA = massMobileA;
	}

	public String getMassTelA() {
		return massTelA;
	}

	public void setMassTelA(String massTelA) {
		this.massTelA = massTelA;
	}

	public String getMassMailA() {
		return massMailA;
	}

	public void setMassMailA(String massMailA) {
		this.massMailA = massMailA;
	}

	public String getDeviceDepA() {
		return deviceDepA;
	}

	public void setDeviceDepA(String deviceDepA) {
		this.deviceDepA = deviceDepA;
	}

	public String getDevicePositionA() {
		return devicePositionA;
	}

	public void setDevicePositionA(String devicePositionA) {
		this.devicePositionA = devicePositionA;
	}

	public String getDeviceNameA() {
		return deviceNameA;
	}

	public void setDeviceNameA(String deviceNameA) {
		this.deviceNameA = deviceNameA;
	}

	public String getDeviceMobileA() {
		return deviceMobileA;
	}

	public void setDeviceMobileA(String deviceMobileA) {
		this.deviceMobileA = deviceMobileA;
	}

	public String getDeviceTelA() {
		return deviceTelA;
	}

	public void setDeviceTelA(String deviceTelA) {
		this.deviceTelA = deviceTelA;
	}

	public String getDeviceMailA() {
		return deviceMailA;
	}

	public void setDeviceMailA(String deviceMailA) {
		this.deviceMailA = deviceMailA;
	}

	public String getPackDepA() {
		return packDepA;
	}

	public void setPackDepA(String packDepA) {
		this.packDepA = packDepA;
	}

	public String getPackPositionA() {
		return packPositionA;
	}

	public void setPackPositionA(String packPositionA) {
		this.packPositionA = packPositionA;
	}

	public String getPackNameA() {
		return packNameA;
	}

	public void setPackNameA(String packNameA) {
		this.packNameA = packNameA;
	}

	public String getPackMobileA() {
		return packMobileA;
	}

	public void setPackMobileA(String packMobileA) {
		this.packMobileA = packMobileA;
	}

	public String getPackTelA() {
		return packTelA;
	}

	public void setPackTelA(String packTelA) {
		this.packTelA = packTelA;
	}

	public String getPackMailA() {
		return packMailA;
	}

	public void setPackMailA(String packMailA) {
		this.packMailA = packMailA;
	}

	public String getPackDepB() {
		return packDepB;
	}

	public void setPackDepB(String packDepB) {
		this.packDepB = packDepB;
	}

	public String getPackPositionB() {
		return packPositionB;
	}

	public void setPackPositionB(String packPositionB) {
		this.packPositionB = packPositionB;
	}

	public String getPackNameB() {
		return packNameB;
	}

	public void setPackNameB(String packNameB) {
		this.packNameB = packNameB;
	}

	public String getPackMobileB() {
		return packMobileB;
	}

	public void setPackMobileB(String packMobileB) {
		this.packMobileB = packMobileB;
	}

	public String getPackTelB() {
		return packTelB;
	}

	public void setPackTelB(String packTelB) {
		this.packTelB = packTelB;
	}

	public String getPackMailB() {
		return packMailB;
	}

	public void setPackMailB(String packMailB) {
		this.packMailB = packMailB;
	}

	public String getPtLogisticsDep() {
		return ptLogisticsDep;
	}

	public void setPtLogisticsDep(String ptLogisticsDep) {
		this.ptLogisticsDep = ptLogisticsDep;
	}

	public String getPtLogisticsName() {
		return ptLogisticsName;
	}

	public void setPtLogisticsName(String ptLogisticsName) {
		this.ptLogisticsName = ptLogisticsName;
	}

	public String getPtLogisticsPosition() {
		return ptLogisticsPosition;
	}

	public void setPtLogisticsPosition(String ptLogisticsPosition) {
		this.ptLogisticsPosition = ptLogisticsPosition;
	}

	public String getPtLogisticsMobile() {
		return ptLogisticsMobile;
	}

	public void setPtLogisticsMobile(String ptLogisticsMobile) {
		this.ptLogisticsMobile = ptLogisticsMobile;
	}

	public String getPtLogisticsTel() {
		return ptLogisticsTel;
	}

	public void setPtLogisticsTel(String ptLogisticsTel) {
		this.ptLogisticsTel = ptLogisticsTel;
	}

	public String getPtLogisticsMail() {
		return ptLogisticsMail;
	}

	public void setPtLogisticsMail(String ptLogisticsMail) {
		this.ptLogisticsMail = ptLogisticsMail;
	}

	public String getPtLogisticsDepA() {
		return ptLogisticsDepA;
	}

	public void setPtLogisticsDepA(String ptLogisticsDepA) {
		this.ptLogisticsDepA = ptLogisticsDepA;
	}

	public String getPtLogisticsNameA() {
		return ptLogisticsNameA;
	}

	public void setPtLogisticsNameA(String ptLogisticsNameA) {
		this.ptLogisticsNameA = ptLogisticsNameA;
	}

	public String getPtLogisticsPositionA() {
		return ptLogisticsPositionA;
	}

	public void setPtLogisticsPositionA(String ptLogisticsPositionA) {
		this.ptLogisticsPositionA = ptLogisticsPositionA;
	}

	public String getPtLogisticsMobileA() {
		return ptLogisticsMobileA;
	}

	public void setPtLogisticsMobileA(String ptLogisticsMobileA) {
		this.ptLogisticsMobileA = ptLogisticsMobileA;
	}

	public String getPtLogisticsTelA() {
		return ptLogisticsTelA;
	}

	public void setPtLogisticsTelA(String ptLogisticsTelA) {
		this.ptLogisticsTelA = ptLogisticsTelA;
	}

	public String getPtLogisticsMailA() {
		return ptLogisticsMailA;
	}

	public void setPtLogisticsMailA(String ptLogisticsMailA) {
		this.ptLogisticsMailA = ptLogisticsMailA;
	}

	public String getMassLogisticsDep() {
		return massLogisticsDep;
	}

	public void setMassLogisticsDep(String massLogisticsDep) {
		this.massLogisticsDep = massLogisticsDep;
	}

	public String getMassLogisticsPosition() {
		return massLogisticsPosition;
	}

	public void setMassLogisticsPosition(String massLogisticsPosition) {
		this.massLogisticsPosition = massLogisticsPosition;
	}

	public String getMassLogisticsName() {
		return massLogisticsName;
	}

	public void setMassLogisticsName(String massLogisticsName) {
		this.massLogisticsName = massLogisticsName;
	}

	public String getMassLogisticsMobile() {
		return massLogisticsMobile;
	}

	public void setMassLogisticsMobile(String massLogisticsMobile) {
		this.massLogisticsMobile = massLogisticsMobile;
	}

	public String getMassLogisticsTel() {
		return massLogisticsTel;
	}

	public void setMassLogisticsTel(String massLogisticsTel) {
		this.massLogisticsTel = massLogisticsTel;
	}

	public String getMassLogisticsMail() {
		return massLogisticsMail;
	}

	public void setMassLogisticsMail(String massLogisticsMail) {
		this.massLogisticsMail = massLogisticsMail;
	}

	public String getMassLogisticsDepA() {
		return massLogisticsDepA;
	}

	public void setMassLogisticsDepA(String massLogisticsDepA) {
		this.massLogisticsDepA = massLogisticsDepA;
	}

	public String getMassLogisticsPositionA() {
		return massLogisticsPositionA;
	}

	public void setMassLogisticsPositionA(String massLogisticsPositionA) {
		this.massLogisticsPositionA = massLogisticsPositionA;
	}

	public String getMassLogisticsNameA() {
		return massLogisticsNameA;
	}

	public void setMassLogisticsNameA(String massLogisticsNameA) {
		this.massLogisticsNameA = massLogisticsNameA;
	}

	public String getMassLogisticsMobileA() {
		return massLogisticsMobileA;
	}

	public void setMassLogisticsMobileA(String massLogisticsMobileA) {
		this.massLogisticsMobileA = massLogisticsMobileA;
	}

	public String getMassLogisticsTelA() {
		return massLogisticsTelA;
	}

	public void setMassLogisticsTelA(String massLogisticsTelA) {
		this.massLogisticsTelA = massLogisticsTelA;
	}

	public String getMassLogisticsMailA() {
		return massLogisticsMailA;
	}

	public void setMassLogisticsMailA(String massLogisticsMailA) {
		this.massLogisticsMailA = massLogisticsMailA;
	}

	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	
	
}
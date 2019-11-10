package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：新车型需求计算 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpTrialDemandModel extends AbstractModel<String>{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -376169851154673260L;

	/**
     * 主表 新车型需求计算
     */
	/**
	 * ID
	 */
	private String id;	
	
	/**
	* 供应商代码
	*/
	private String supplierNo; 
	
	/**
	* 出货地
	*/
	private String supFactory; 
	
	/**
	* 零件号
	*/
	private String partNo; 
	
	/**
	* 到货时间
	*/
	private java.util.Date arriveTime; 
	
	/**
	* 到货时间
	*/
	private String  arriveTimeStr; 
	
	/**
	* 到货时间(开始）
	*/
	private String  arriveTimeStrStart;
	
	/**
	* 到货时间(结束）
	*/
	private String  arriveTimeStrEnd;
	
	/**
	* 净需求
	*/
	private String orderNum; 
	
	/**
	* 例外需求
	*/
	private String excOrderNum; 
	
	/**
	* 总需求
	*/
	private String totalOrderNum; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
	/**
	 * 发布状态
	 */
	private String relStatus;
	
	/**
	 * 发布时间
	 */
	private java.util.Date  relTime;
	
	/**
	 * 工厂
	 */
	private String factoryCode;
	
	/**
	 * 车间
	 */
	private String workcenter;
	
	/**
	 * 临时订单号
	 */
	private String logisticsOrder;
	
	/**
	* 采购类型
	*/
	private String purchaseType; 
	
	/**
	 * 副表 物料主数据
	 */
	/**
	* 简号
	*/
	private String partShortNo;
	
	/**
	* 零件中文名称
	*/
	private String partNameCn; 

	/**
	 * 副表 供应商主数据
	 */
	/**
	* 供应商名称
	*/
	private String supplierName;
	
	/**
	 * 副表  数据字典表
	 */
	/**
	 * 数据值
	 */
	private String codeValueNameB;
	/**
	 * 数据值
	 */
	private String codeValueNameC;
	/**
	 * 
	 */
	private String codeValueNameD;

	/**
	 * 主表  零件新车型需求计算
	 * @return
	 */
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public java.util.Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(java.util.Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getArriveTimeStr() {
		return arriveTimeStr;
	}

	public void setArriveTimeStr(String arriveTimeStr) {
		this.arriveTimeStr = arriveTimeStr;
	}

	public String getArriveTimeStrStart() {
		return arriveTimeStrStart;
	}

	public void setArriveTimeStrStart(String arriveTimeStrStart) {
		this.arriveTimeStrStart = arriveTimeStrStart;
	}

	public String getArriveTimeStrEnd() {
		return arriveTimeStrEnd;
	}

	public void setArriveTimeStrEnd(String arriveTimeStrEnd) {
		this.arriveTimeStrEnd = arriveTimeStrEnd;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getExcOrderNum() {
		return excOrderNum;
	}

	public void setExcOrderNum(String excOrderNum) {
		this.excOrderNum = excOrderNum;
	}

	public String getTotalOrderNum() {
		return totalOrderNum;
	}

	public void setTotalOrderNum(String totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getRelStatus() {
		return relStatus;
	}

	public void setRelStatus(String relStatus) {
		this.relStatus = relStatus;
	}

	public java.util.Date getRelTime() {
		return relTime;
	}

	public void setRelTime(java.util.Date relTime) {
		this.relTime = relTime;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getLogisticsOrder() {
		return logisticsOrder;
	}

	public void setLogisticsOrder(String logisticsOrder) {
		this.logisticsOrder = logisticsOrder;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	/**
	 * 副表 物料主数据
	 */

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

	/**
	 * 副表 供应商主数据
	 */
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	/**
	 * 数据字典表
	 * @return
	 */
	public String getCodeValueNameB() {
		return codeValueNameB;
	}
	public void setCodeValueNameB(String codeValueNameB) {
		this.codeValueNameB = codeValueNameB;
	}
	public String getCodeValueNameC() {
		return codeValueNameC;
	}
	public void setCodeValueNameC(String codeValueNameC) {
		this.codeValueNameC = codeValueNameC;
	}

	public String getCodeValueNameD() {
		return codeValueNameD;
	}

	public void setCodeValueNameD(String codeValueNameD) {
		this.codeValueNameD = codeValueNameD;
	}
}
	
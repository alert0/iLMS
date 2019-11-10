package com.hanthink.mp.model;

import java.util.Date;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：剩余量主数据 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpResidualModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5417815498221120722L;

	/**
     * 主表 剩余量主数据
     */
	private String id;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	* 零件号
	*/
	private String partNo; 
	
	/**
	* 供应商代码
	*/
	private String supplierNo; 
	
	/**
	* 出货地
	*/
	private String supFactory; 
	
	/**
	* 计算队列
	*/
	private String unloadPort; 
	
	/**
	* 订购数量
	*/
	private Integer orderNum; 
	
	/**
	* 净需求数量
	*/
	private Integer necessaryOrderNum; 
	
	/**
	* 实际剩余量
	*/
	private Integer realResidualNum; 
	
	/**
	* 订购前余量
	*/
	private Integer preResidualNum; 
	
	/**
	* 计划变更差异量
	*/
	private Integer adjDiffNum; 
	
	/**
	* 安全库存
	*/
	private Integer safeNum; 
	
	/**
	* 不良品库存
	*/
	private Integer defectNum; 
	
	/**
	* 手工调整余量
	*/
	private String manuResidual; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 创建时间(字符)
	*/
	private String creationTimeStr; 
	
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
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
	 * 中间表 MM_MP_RESIDUAL_IMP
	 */
	/**
	* 中间表主键
	*/
	private String busiId;
	
	/**
	* 手工调整余量
	*/
	private String adjResidual;
	
	/**
	 * 数据表   
	 */
	/**
	* 零件单位
	*/
	private String partUnit;
	
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
	 * 主表  零件剩余量主数据
	 * @return
	 */
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
	public String getUnloadPort() {
		return unloadPort;
	}
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getNecessaryOrderNum() {
		return necessaryOrderNum;
	}
	public void setNecessaryOrderNum(Integer necessaryOrderNum) {
		this.necessaryOrderNum = necessaryOrderNum;
	}
	public Integer getRealResidualNum() {
		return realResidualNum;
	}
	public void setRealResidualNum(Integer realResidualNum) {
		this.realResidualNum = realResidualNum;
	}
	public Integer getPreResidualNum() {
		return preResidualNum;
	}
	public void setPreResidualNum(Integer preResidualNum) {
		this.preResidualNum = preResidualNum;
	}
	public Integer getAdjDiffNum() {
		return adjDiffNum;
	}
	public void setAdjDiffNum(Integer adjDiffNum) {
		this.adjDiffNum = adjDiffNum;
	}
	public Integer getSafeNum() {
		return safeNum;
	}
	public void setSafeNum(Integer safeNum) {
		this.safeNum = safeNum;
	}
	public Integer getDefectNum() {
		return defectNum;
	}
	public void setDefectNum(Integer defectNum) {
		this.defectNum = defectNum;
	}
	public String getManuResidual() {
		return manuResidual;
	}
	public void setManuResidual(String manuResidual) {
		this.manuResidual = manuResidual;
	}
	
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getCreationTimeStr() {
		return creationTimeStr;
	}
	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	/**
	 * 副表 物料主数据
	 * @return
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
	 * @return
	 */
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	/**
	 * 中间表  MM_MP_RESIDUAL_IMP
	 * @return
	 */
	public String getAdjResidual() {
		return adjResidual;
	}
	public void setAdjResidual(String adjResidual) {
		this.adjResidual = adjResidual;
	}
	
	public String getBusiId() {
		return busiId;
	}
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
	/**
	 * 数据表
	 * @return
	 */
	public String getPartUnit() {
		return partUnit;
	}
	public void setPartUnit(String partUnit) {
		this.partUnit = partUnit;
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
	
}
package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：BOM基础数据表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubModelBomModel extends AbstractModel<Integer>{
	
	
	

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年10月1日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -662419686258463907L;

	/**
	 * 主表  BOM基础数据表
	 */
	/**
	* 工厂
	*/
	private String factoryCode ; 
	
	/**
	* 车型代码
	*/
	private String mto; 
	
	/**
	* 零件号
	*/
	private String partNo;  
	
	/**
	* 车型
	*/
	private String modelCode; 
	
	/**
	* 装配线工位
	*/
	private String stationCode; 
	
	/**
	* 层级
	*/
	private Integer stage; 
	
	/**
	* 版本
	*/
	private String version; 
	
	/**
	* 采购类型
	*/
	private String purchaseType; 
	
	/**
	* 特性
	*/
	private String feature; 
	
	/**
	* 用量
	*/
	private Integer num; 
	
	/**
	* 用量单位
	*/
	private String partUnit; 
	
	/**
	* 供应商代码
	*/
	private String supplierNo; 
	
	/**
	* 下一配送地址（无）
	*/
	private String nextPlacement; 
	
	/**
	* 工序配送地址
	*/
	private String process; 
	
	/**
	* 是否国内售后件
	*/
	private String a; 
	
	/**
	* 是否国外售后件
	*/
	private String b; 
	
	/**
	* 是否售后专用件
	*/
	private String c; 
	
	/**
	* 生效日期
	*/
	private java.util.Date effStart; 
	
	/**
	* 生效日期
	*/
	private String effStartStr; 
	
	/**
	* 失效日期
	*/
	private java.util.Date effEnd; 
	
	/**
	* 失效日期
	*/
	private String effEndStr; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime;

	/**
	 * 副表  物料主数据表
	 */
	/**
	 * 零件中文名称
	 */
	private String partNameCn;
	
	/**
	 * 零件英文名称
	 */
	private String partNameEn;
  
	/**
	 * 副表  供应商主数据表
	 */
	private String supplierName;
	
	/**
	 * 副表  数据字典表
	 */
	/**
	 * 数据值
	 */
	private String codeValueNameA;
	
	private String codeValueNameB;
	
	private String codeValueNameC;

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getMto() {
		return mto;
	}

	public void setMto(String mto) {
		this.mto = mto;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getPartUnit() {
		return partUnit;
	}

	public void setPartUnit(String partUnit) {
		this.partUnit = partUnit;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getNextPlacement() {
		return nextPlacement;
	}

	public void setNextPlacement(String nextPlacement) {
		this.nextPlacement = nextPlacement;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public java.util.Date getEffStart() {
		return effStart;
	}

	public void setEffStart(java.util.Date effStart) {
		this.effStart = effStart;
	}

	public String getEffStartStr() {
		return effStartStr;
	}

	public void setEffStartStr(String effStartStr) {
		this.effStartStr = effStartStr;
	}

	public java.util.Date getEffEnd() {
		return effEnd;
	}

	public void setEffEnd(java.util.Date effEnd) {
		this.effEnd = effEnd;
	}

	public String getEffEndStr() {
		return effEndStr;
	}

	public void setEffEndStr(String effEndStr) {
		this.effEndStr = effEndStr;
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

	/**
	 * 物料主数据表
	 */
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

	/**
	 * 供应商主数据表
	 */
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * 数据字典表
	 */
	public String getCodeValueNameA() {
		return codeValueNameA;
	}

	public void setCodeValueNameA(String codeValueNameA) {
		this.codeValueNameA = codeValueNameA;
	}

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
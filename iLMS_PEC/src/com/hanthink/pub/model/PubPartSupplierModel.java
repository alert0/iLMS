package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：物料与供应商关系表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubPartSupplierModel extends AbstractModel<Integer>{
	
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月30日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 1514073542536893581L;

	/**
	 * 主表  物料与供应商关系表
	 */
	/**
	* 工厂
	*/
	protected String factoryCode ; 
	
	/**
	* 供应商代码
	*/
	protected String supplierNo; 
	
	/**
	* 出货地
	*/
	protected String supFactory; 
	
	/**
	* 零件号
	*/
	protected String partNo; 
	
	/**
	* 零件名称
	*/
	protected String partNameCn; 
	
	/**
	* 供应商名称
	*/
	protected String supplierName; 
	
	/**
	* 最小采购量
	*/
	protected Integer minOrderNum; 
	
	/**
	* 收容数
	*/
	protected Integer standardPackage; 
	
	/**
	* 提前期 单位(天)
	*/
	protected Integer inPlanForwardTime; 
	
	/**
	* 生效日期
	*/
	protected java.util.Date effStart; 
	
	/**
	* 生效日期
	*/
	protected String effStartStr; 
	
	/**
	* 失效日期
	*/
	protected java.util.Date effEnd; 
	
	/**
	* 失效日期
	*/
	protected String effEndStr; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	protected java.util.Date lastModifiedTime;

	/**
	 * 副表  物料主数据表
	 */
	/**
	 * 简号
	 */
	protected String partShortNo;
  
	/**
	 * 副表  物料主数据表
	 */
	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}
	
	/**
	 * 主表 物料供应商关系数据
	 */
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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

	public Integer getMinOrderNum() {
		return minOrderNum;
	}

	public void setMinOrderNum(Integer minOrderNum) {
		this.minOrderNum = minOrderNum;
	}

	public Integer getStandardPackage() {
		return standardPackage;
	}

	public void setStandardPackage(Integer standardPackage) {
		this.standardPackage = standardPackage;
	}

	public Integer getInPlanForwardTime() {
		return inPlanForwardTime;
	}

	public void setInPlanForwardTime(Integer inPlanForwardTime) {
		this.inPlanForwardTime = inPlanForwardTime;
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

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
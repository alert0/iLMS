package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;
/**
 * <pre> 
 * 功能描述:空容器库存查询业务数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvEmptyModel extends AbstractModel<String>{

	private static final long serialVersionUID = 364925953219324373L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 车间 */
	private String workCenter;
	/** 业务主键ID*/
	private String busiId;
	/** 箱种*/
	private String codeValueNameD;
	/** 车间*/
	private String codeValueNameE;
	/** 工厂 */
	private String factoryCode;
	/** 供应商代码 */
	private String supplierNo;
	/** 箱种 */
	private String boxType;
	private String boxTypeExcel;
	/** 空容器数量 */
	private String boxQty;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 创建时间 */
	private String creationTimeStr;
	/** 最后修改人 */
	private String lastModifiedUser;
	/** 最后修改时间 */
	private String lastModifiedTime;
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
	
	
	public String getCodeValueNameE() {
		return codeValueNameE;
	}
	public void setCodeValueNameE(String codeValueNameE) {
		this.codeValueNameE = codeValueNameE;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getCodeValueNameD() {
		return codeValueNameD;
	}
	public void setCodeValueNameD(String codeValueNameD) {
		this.codeValueNameD = codeValueNameD;
	}
	public String getBusiId() {
		return busiId;
	}
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
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
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public String getBoxTypeExcel() {
		return boxTypeExcel;
	}
	public void setBoxTypeExcel(String boxTypeExcel) {
		this.boxTypeExcel = boxTypeExcel;
	}
	public String getBoxQty() {
		return boxQty;
	}
	public void setBoxQty(String boxQty) {
		this.boxQty = boxQty;
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
	public String getCreationTimeStr() {
		return creationTimeStr;
	}
	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
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
	/** 导入UUID */
	private String  uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String  checkResult;
	/** 检查结果信息 */
	private String  checkInfo;
	/** 导入状态 */
	private String  importStatus;
	/** 操作类型状态 */
	private String  opeType;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	

	/**
     * 导入数据检查
     * @param vo
     * @author linzhuo  2018-9-12
     */
    public static void checkImportData(InvEmptyModel m) {
        StringBuffer checkInfo = new StringBuffer();
        
        if(m.getSupplierNo()== null || "".equals(m.getSupplierNo())){
            checkInfo.append("供应商代码为空;");
        }
        if(m.getBoxType() == null || "".equals(m.getBoxType())){
            checkInfo.append("箱种为空;");
        }
        if(m.getBoxQty() == null || "".equals(m.getBoxQty())){
            checkInfo.append("数量为空;");
        }
        
        if(checkInfo == null || "".equals(checkInfo.toString())){
            m.setCheckResult("1");
            m.setOpeType("I");
            m.setCheckInfo("");
        }else{
            m.setCheckResult("0");
            m.setOpeType("U");
            m.setCheckInfo(checkInfo.toString());
        }
        
    }
	
}

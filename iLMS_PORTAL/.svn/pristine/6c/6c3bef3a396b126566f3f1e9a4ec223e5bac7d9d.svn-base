package com.hanthink.jit.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.model.AbstractModel;
import com.mysql.jdbc.StringUtils;

/**
 * <pre> 
 * 描述：MM_JIT_PART_REMAIN 实体对象
 * 作者:dtp
 * 日期:2018-09-21 11:51:44
 * </pre>
 */
@SuppressWarnings("serial")
public class JitPartRemainModel extends AbstractModel<Long>{
	
	/** 导入UUID */
	private String uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String checkResult;
	/** 检查结果信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	/** 是否继续检查 */
	private String continueCheck;
	/** 导入数据操作类型 */
	private String opeType;
	/** 业务表主键字段值 */
	private String busiId;
	
	/**
	 * ID
     */
	protected Long id; 
	
	/**
	 * 零件号
     */
	protected String partNo; 
	
	/**
	 * 信息点
     */
	protected String planCode; 
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
	/**
	 * 配送地址
     */
	protected String location; 
	
	/**
	 * 当前余量
     */
	protected String partRemain; 
	
	/**
	 * 安全库存
     */
	protected String safetyInventory; 
	
	/**
	 * 截止需求日期
     */
	protected String endRequireDate; 
	
	/**
	 * 截止需求量
     */
	protected String endRequireNum; 
	
	/**
	 * 期初余量
     */
	protected String startRemain; 
	
	/**
	 * 期末余量
     */
	protected String endRemain; 
	
	/**
	 * 截止看板产品流水号
     */
	protected String kbProductSeqno; 
	
	/**
	 * 创建时间
     */
	protected String creationTime; 
	
	/**
	 * 创建人
     */
	protected String creationUser; 
	
	/**
	 * 最后修改用户
     */
	protected String lastModifiedUser; 
	
	/**
	 * 最后修改IP
     */
	protected String lastModifiedIp; 
	
	/**
	 * 最后修改时间
     */
	protected String lastModifiedTime;

	/**
	 * 出货仓库
	 */
	protected String shipDepot;
	
	/**
	 * 简号
	 */
	protected String partShortNo;
	
	/**
	 * 仅显示使用中配送地址复选框
	 */
	protected String isSelectLocation;
	
	/**
	 * 零件中文名称
	 */
	protected String partNameCn;

	/**
	 * 车间
	 */
	protected String workcenter;
	
	/**
	 * 截止看板产品流水号
	 */
	protected String productSeqno;
	
	/**
	 * 非空校验
	 */
	public static void checkImportData(JitPartRemainModel model) {
		StringBuffer checkInfo = new StringBuffer();
		if(StringUtils.isNullOrEmpty(model.getPlanCode())) {
			checkInfo.append("信息点不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getLocation())) {
			checkInfo.append("配送地址不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getPartNo())) {
			checkInfo.append("零件号不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getPartRemain())) {
			checkInfo.append("零件余量不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getSafetyInventory())) {
			checkInfo.append("安全库存不能为空;");
		}
		String endRequireDateStr = model.getEndRequireDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date endRequireDate;
		try {
			endRequireDate = sdf.parse(endRequireDateStr);
			Date nowDate = new Date();
			if(endRequireDate.getTime() < nowDate.getTime()) {
				checkInfo.append("截止日期不能小于当前时间");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(StringUtils.isNullOrEmpty(model.getEndRequireNum())) {
			checkInfo.append("截止所需零件量不能为空;");
		} 
		if(!String.valueOf(model.getEndRequireNum()).matches("^-?\\d*.?\\d*$")){
            checkInfo.append("截止所需零件量数据格式错误;");
		}
		if(StringUtils.isNullOrEmpty(checkInfo.toString())){
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
		}else{
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
			model.setCheckInfo(checkInfo.toString());
		}
		
	}
	
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

	public String getContinueCheck() {
		return continueCheck;
	}

	public void setContinueCheck(String continueCheck) {
		this.continueCheck = continueCheck;
	}

	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPartRemain() {
		return partRemain;
	}

	public void setPartRemain(String partRemain) {
		this.partRemain = partRemain;
	}

	public String getSafetyInventory() {
		return safetyInventory;
	}

	public void setSafetyInventory(String safetyInventory) {
		this.safetyInventory = safetyInventory;
	}

	public String getEndRequireDate() {
		return endRequireDate;
	}

	public void setEndRequireDate(String endRequireDate) {
		this.endRequireDate = endRequireDate;
	}

	public String getEndRequireNum() {
		return endRequireNum;
	}

	public void setEndRequireNum(String endRequireNum) {
		this.endRequireNum = endRequireNum;
	}

	public String getStartRemain() {
		return startRemain;
	}

	public void setStartRemain(String startRemain) {
		this.startRemain = startRemain;
	}

	public String getEndRemain() {
		return endRemain;
	}

	public void setEndRemain(String endRemain) {
		this.endRemain = endRemain;
	}

	public String getKbProductSeqno() {
		return kbProductSeqno;
	}

	public void setKbProductSeqno(String kbProductSeqno) {
		this.kbProductSeqno = kbProductSeqno;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getShipDepot() {
		return shipDepot;
	}

	public void setShipDepot(String shipDepot) {
		this.shipDepot = shipDepot;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getIsSelectLocation() {
		return isSelectLocation;
	}

	public void setIsSelectLocation(String isSelectLocation) {
		this.isSelectLocation = isSelectLocation;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getPlanCodeDesc() {
		return planCodeDesc;
	}

	public void setPlanCodeDesc(String planCodeDesc) {
		this.planCodeDesc = planCodeDesc;
	}

	public String getProductSeqno() {
		return productSeqno;
	}

	public void setProductSeqno(String productSeqno) {
		this.productSeqno = productSeqno;
	}
	
	
}

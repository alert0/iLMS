package com.hanthink.pup.model;

import com.hanthink.pup.util.PupUtil;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

 /**
 * 
 * <pre>
 * 描述：生产计划主数据 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:zmj
 * 日期:2018-09-13 10:49:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PupProPlanModel extends AbstractModel<String> {

	private static final long serialVersionUID = -4890724952863468205L;
	
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	* 主表  生产计划主数据
	*/
	/** 工厂 */
	private String factoryCode;

	/** 顺序号 */
	private String sortId;

	/** 订单号 */
	private String orderNo;

	/** 车型 */
	private String carType;

	/** 标识 */
	private String mark;

	/** 混合车型排序 */
	private String mixSortId;

	/** 分车型排序 */
	private String singleSortId;

	/** 计划下线日期 */
	private String afoffDate;
	/** 计划下线时间 */
	private String afoffTime;
	/** 创建用户 */
	private String creationUser;

	/** 创建时间 */
	private String creationTime;

	/** 最后修改人 */
	private String lastModifiedUser;

	/** 最后修改时间 */
	private String lastModifiedTime;
	
	/** 已发标识 */
	private String sendFlag;
	
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

	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getMixSortId() {
		return mixSortId;
	}
	public void setMixSortId(String mixSortId) {
		this.mixSortId = mixSortId;
	}
	public String getSingleSortId() {
		return singleSortId;
	}
	public void setSingleSortId(String singleSortId) {
		this.singleSortId = singleSortId;
	}
	public String getAfoffDate() {
		return afoffDate;
	}
	public void setAfoffDate(String afoffDate) {
		this.afoffDate = afoffDate;
	}
	public String getAfoffTime() {
		return afoffTime;
	}
	public void setAfoffTime(String afoffTime) {
		this.afoffTime = afoffTime;
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
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
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
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	
	public static void checkImportData(PupProPlanModel planModel)throws Exception {
		StringBuffer checkInfo = new StringBuffer();
		
		if(StringUtil.isEmpty(planModel.getSortId())) {
			checkInfo.append("生产订单顺序号为空;");
		}
		if(StringUtil.isEmpty(planModel.getOrderNo())) {
			checkInfo.append("生产订单号为空;");
		}
		if(StringUtil.isEmpty(planModel.getCarType())) {
			checkInfo.append("车型为空;");
		}
		if(StringUtil.isEmpty(planModel.getMark())) {
			checkInfo.append("标识为空;");
		}
		if(StringUtil.isEmpty(planModel.getMixSortId())) {
			checkInfo.append("混合车型排序为空;");
		}
		if(StringUtil.isEmpty(planModel.getSingleSortId())) {
			checkInfo.append("分车型排序为空");
		}
		if(StringUtil.isEmpty(planModel.getAfoffDate())) {
			checkInfo.append("计划下线日期为空;");
		}else {
			try {
				PupUtil.String2Date(planModel.getAfoffDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				checkInfo.append("计划下线日期格式有误;");
			}
		}
		if(StringUtil.isEmpty(planModel.getAfoffTime())) {
			checkInfo.append("计划下线时间为空;");
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            planModel.setCheckResult("1");
            planModel.setOpeType("I");
            planModel.setCheckInfo("");
        }else{
            planModel.setCheckResult("0");
            planModel.setOpeType("U");
            planModel.setCheckInfo(checkInfo.toString());
        }
	}
	@Override
	public String toString() {
		return "PupProPlanModel [id=" + id + ", factoryCode=" + factoryCode + ", sortId=" + sortId + ", orderNo="
				+ orderNo + ", carType=" + carType + ", mark=" + mark + ", mixSortId=" + mixSortId + ", singleSortId="
				+ singleSortId + ", afoffDate=" + afoffDate + ", afoffTime=" + afoffTime + ", creationUser="
				+ creationUser + ", creationTime=" + creationTime + ", lastModifiedUser=" + lastModifiedUser
				+ ", lastModifiedTime=" + lastModifiedTime + ", sendFlag=" + sendFlag + ", uuid=" + uuid
				+ ", checkResult=" + checkResult + ", checkInfo=" + checkInfo + ", importStatus=" + importStatus
				+ ", opeType=" + opeType + "]";
	}
}

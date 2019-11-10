package com.hanthink.pup.model;
/**
 * 
 * @Desc		: 计划订单 Excel导入类
 * @FileName	: PupProPlanImportModel.java
 * @CreateOn	: 2018年9月13日 上午10:30:10
 * @author 		: zmj
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年9月2日		V1.0		zmj		新建
 */
public class PupProPlanImportModel extends PupProPlanModel{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5484304659003628812L;
	
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
	 * 
	 *@Description 校验信息
	 *@author zmj
	 *@param plan
	 *@date 2018年9月15日 下午12:16:36
	 */
	public static void checkImportData(PupProPlanImportModel plan) {
		StringBuffer checkInfo = new StringBuffer();
		
		if(null == plan.getFactoryCode() || "".equals(plan.getFactoryCode())) {
			checkInfo.append("工厂代码为空");
		}
		if (null == plan.getSortId() || "".equals(plan.getSortId())) {
			checkInfo.append("生产订单顺序号为空");
		}
		if (null == plan.getOrderNo() || "".equals(plan.getOrderNo())) {
			checkInfo.append("生产订单号为空");
		}
		if (null == plan.getCarType() || "".equals(plan.getCarType())) {
			checkInfo.append("车型为空");
		}
		if (null == plan.getAfoffTime() || "".equals(plan.getAfoffTime())) {
			checkInfo.append("计划下线时间为空");
		}
		if (null == plan.getMixSortId() || "".equals(plan.getMixSortId())) {
			checkInfo.append("混合车型排序号为空");
		}
		if (null == plan.getSingleSortId() || "".equals(plan.getSingleSortId())) {
			checkInfo.append("分车型排序号为空");
		}
		if (null == plan.getMark() || "".equals(plan.getMark())) {
			checkInfo.append("描述为空");
		}
		if(checkInfo == null || "".equals(checkInfo.toString())){
            plan.setCheckResult("1");
            plan.setCheckInfo("");
        }else{
            plan.setCheckResult("0");
            plan.setCheckInfo(checkInfo.toString());
        }
	}
}

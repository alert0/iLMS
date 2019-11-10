package com.hanthink.pup.model;

import com.hanthink.pup.util.PupUtil;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
/**
 * <pre> 
 * 功能描述:取货计划查询业务实体对象
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupPlanModel extends AbstractModel<String>{

	private static final long serialVersionUID = -6626989202688586784L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/** 订单物流模式 */
	private String pickupType;
	/** 工厂代码 */
	private String factoryCode;
	/** 区域 */
	private String area;
	/** 车型 */
	private String carType;
	/** 集货线路 */
	private String routeCode;
	/** 累计车次 */
	private String totalBatchs;
	/** 合并车次 */
	private String mergeBatchs;
	/** 出货地 */
	private String supFactory;
	/** 供应商代码 */
	private String supplierNo;
	/** 供应商名称 */
	private String supplierName;
	/** 物流单号 */
	private String orderNo;
	/** 采购订单号 */
	private String purchaseNo;
	/** 工作日 */
	private String workDate;
	/** 当日车次 */
	private String todayCarBatch;
	/** 计划取货日期 */
	private String planPickupDate;
	/** 计划取货时间 */
	private String planPickupTime;
	/** 计划到货日期 */
	private String planArrDate;
	/** 计划到货时间 */
	private String planArrTime;
	/** 计划装配日期 */
	private String planAssembleDate;
	/** 计划装配时间 */
	private String planAssembleTime;
	/** 订购用途 */
	private String orderUse;
	/** 用户(创建人) */
	private String creationUser;
	/**  最后修改人 */
	private String lastModifiedUser;
	private String outerLogisManager;
	/** 内物流管理员 */
	private String interLogisManager;
	/** 确认天数 */
	private String confirmDays;
	/** 反馈状态 */
	private String feedBackStatus;
	private String excelFeedBackStatus;
	/** 下载状态 */
	private String downloadStatus;
	private String excelDownloadStatus;
	/** 下载时间 */
	private String downloadTime;
	/** 计划取货日期开始时间 */
	private String planPickupTimeStart;
	/** 计划取货日期结束时间 */
	private String planPickupTimeEnd;
	/** 零件号 */
	private String partNo;
	/** 发车状态 */
	private String deqStatus;
	private String excelDeqStatus;
	
	private String impUUID;
	/** 校验结果 */
	private String checkResult;
	/** 校验信息 */
	private String checkInfo;
	/** 导入状态 */
	private String importStatus;
	/** 操作类型 */
	private String opeType;
	/** 创建时间 */
	private String creationTime;
	/** 差异标识 */
	private String diffFlag;
	
	/**
	 * 计算队列
	 */
	private String unloadPort;
	/** 车牌号 */
	private String plateNum;
	public String getUnloadPort() {
        return unloadPort;
    }
    public void setUnloadPort(String unloadPort) {
        this.unloadPort = unloadPort;
    }
    public String getPickupType() {
		return pickupType;
	}
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getTotalBatchs() {
		return totalBatchs;
	}
	public void setTotalBatchs(String totalBatchs) {
		this.totalBatchs = totalBatchs;
	}
	public String getMergeBatchs() {
		return mergeBatchs;
	}
	public void setMergeBatchs(String mergeBatchs) {
		this.mergeBatchs = mergeBatchs;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getTodayCarBatch() {
		return todayCarBatch;
	}
	public void setTodayCarBatch(String todayCarBatch) {
		this.todayCarBatch = todayCarBatch;
	}
	public String getPlanPickupTime() {
		return planPickupTime;
	}
	public String getPlanPickupDate() {
		return planPickupDate;
	}
	public void setPlanPickupDate(String planPickupDate) {
		this.planPickupDate = planPickupDate;
	}
	public void setPlanPickupTime(String planPickupTime) {
		this.planPickupTime = planPickupTime;
	}
	public String getPlanArrDate() {
		return planArrDate;
	}
	public void setPlanArrDate(String planArrDate) {
		this.planArrDate = planArrDate;
	}
	public String getPlanArrTime() {
		return planArrTime;
	}
	public void setPlanArrTime(String planArrTime) {
		this.planArrTime = planArrTime;
	}
	public String getPlanAssembleDate() {
		return planAssembleDate;
	}
	public void setPlanAssembleDate(String planAssembleDate) {
		this.planAssembleDate = planAssembleDate;
	}
	public String getPlanAssembleTime() {
		return planAssembleTime;
	}
	public void setPlanAssembleTime(String planAssembleTime) {
		this.planAssembleTime = planAssembleTime;
	}
	public String getOrderUse() {
		return orderUse;
	}
	public void setOrderUse(String orderUse) {
		this.orderUse = orderUse;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUserId(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getOuterLogisManager() {
		return outerLogisManager;
	}
	public void setOuterLogisManager(String outerLogisManager) {
		this.outerLogisManager = outerLogisManager;
	}
	public String getInterLogisManager() {
		return interLogisManager;
	}
	public void setInterLogisManager(String interLogisManager) {
		this.interLogisManager = interLogisManager;
	}
	public String getConfirmDays() {
		return confirmDays;
	}
	public void setConfirmDays(String confirmDays) {
		this.confirmDays = confirmDays;
	}
	public String getFeedBackStatus() {
		return feedBackStatus;
	}
	public void setFeedBackStatus(String feedBackStatus) {
		this.feedBackStatus = feedBackStatus;
	}
	public String getExcelFeedBackStatus() {
		return excelFeedBackStatus;
	}
	public void setExcelFeedBackStatus(String excelFeedBackStatus) {
		this.excelFeedBackStatus = excelFeedBackStatus;
	}
	public String getDownloadStatus() {
		return downloadStatus;
	}
	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	public String getExcelDownloadStatus() {
		return excelDownloadStatus;
	}
	public void setExcelDownloadStatus(String excelDownloadStatus) {
		this.excelDownloadStatus = excelDownloadStatus;
	}
	public String getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	public String getPlanPickupTimeStart() {
		return planPickupTimeStart;
	}
	public void setPlanPickupTimeStart(String planPickupTimeStart) {
		this.planPickupTimeStart = planPickupTimeStart;
	}
	public String getPlanPickupTimeEnd() {
		return planPickupTimeEnd;
	}
	public void setPlanPickupTimeEnd(String planPickupTimeEnd) {
		this.planPickupTimeEnd = planPickupTimeEnd;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getDeqStatus() {
		return deqStatus;
	}
	public void setDeqStatus(String deqStatus) {
		this.deqStatus = deqStatus;
	}
	public String getExcelDeqStatus() {
		return excelDeqStatus;
	}
	public void setExcelDeqStatus(String excelDeqStatus) {
		this.excelDeqStatus = excelDeqStatus;
	}
	public String getImpUUID() {
		return impUUID;
	}
	public void setImpUUID(String impUUID) {
		this.impUUID = impUUID;
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
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
	public String getDiffFlag() {
		return diffFlag;
	}
	public void setDiffFlag(String diffFlag) {
		this.diffFlag = diffFlag;
	}	
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	/**
	 * 导入数据校验
	 * @param pupPlanModel
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	public void checkImportMessage(PupPlanModel pupPlanModel)throws Exception {
		StringBuffer checkInfo = new StringBuffer();
		
		if (StringUtil.isEmpty(pupPlanModel.getOrderNo())) {
			checkInfo.append("物流单号为空;");
		}
		if(StringUtil.isEmpty(pupPlanModel.getPurchaseNo())) {
			checkInfo.append("采购订单号为空;");
		}
		
		if (StringUtil.isNotEmpty(pupPlanModel.getTodayCarBatch())) {
			if (!PupUtil.validateInteger(pupPlanModel.getTodayCarBatch())) {
				pupPlanModel.setTotalBatchs(null);
				checkInfo.append("累计车次录入格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getMergeBatchs())) {
			if (!pupPlanModel.getMergeBatchs().equals("0")) {
				if (!PupUtil.validateInteger(pupPlanModel.getMergeBatchs())) {
					pupPlanModel.setMergeBatchs(null);
					checkInfo.append("合并车次录入格式有误;");
				}
			}
		}
		
		if (StringUtil.isNotEmpty(pupPlanModel.getTodayCarBatch())) {
			if (!PupUtil.validateInteger(pupPlanModel.getTodayCarBatch())) {
				pupPlanModel.setTodayCarBatch(null);
				checkInfo.append("当日车次录入格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getConfirmDays())) {
			if (!PupUtil.validateInteger(pupPlanModel.getConfirmDays())) {
				pupPlanModel.setConfirmDays(null);
				checkInfo.append("确认天数录入格式有误;");
			}
		}
		
		if (StringUtil.isNotEmpty(pupPlanModel.getWorkDate())) {
			try {
				if (pupPlanModel.getWorkDate().length() > 11) {
					pupPlanModel.setWorkDate(pupPlanModel.getWorkDate().substring(0,10));
				}
				PupUtil.String2Date(pupPlanModel.getWorkDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				pupPlanModel.setWorkDate(null);
				checkInfo.append("工作日格式录入有误");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getPlanPickupDate())) {
			try {
				if (pupPlanModel.getPlanPickupDate().length() > 11) {
					pupPlanModel.setPlanPickupDate(pupPlanModel.getPlanPickupDate().substring(0,10));
				}
				PupUtil.String2Date(pupPlanModel.getPlanPickupDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				pupPlanModel.setPlanPickupDate(null);
				checkInfo.append("计划取货日期格式录入有误");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getPlanPickupTime())) {
			try {
				if (pupPlanModel.getPlanPickupTime().length() > 11) {
					pupPlanModel.setPlanPickupTime(pupPlanModel.getPlanPickupTime().substring(11));
				}
				PupUtil.String2Date(pupPlanModel.getPlanPickupTime(), "HH:mm:ss");
			} catch (Exception e) {
				pupPlanModel.setPlanPickupTime(null);
				checkInfo.append("计划取货时间格式录入有误");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getPlanArrDate())) {
			try {
				if (pupPlanModel.getPlanArrDate().length() > 11) {
					pupPlanModel.setPlanArrDate(pupPlanModel.getPlanArrDate().substring(0, 10));
				}
				PupUtil.String2Date(pupPlanModel.getPlanArrDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				pupPlanModel.setPlanArrDate(null);
				checkInfo.append("计划到货日期格式录入有误");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getPlanArrTime())) {
			try {
				if (pupPlanModel.getPlanArrTime().length() > 11) {
					pupPlanModel.setPlanArrTime(pupPlanModel.getPlanArrTime().substring(11));
				}
				PupUtil.String2Date(pupPlanModel.getPlanArrTime(), "HH:mm:ss");
			} catch (Exception e) {
				pupPlanModel.setPlanArrTime(null);
				checkInfo.append("计划到货时间格式录入有误");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getPlanAssembleDate())) {
			try {
				if (pupPlanModel.getPlanAssembleDate().length() > 11) {
					pupPlanModel.setPlanAssembleDate(pupPlanModel.getPlanAssembleDate().substring(0, 10));
				}
				PupUtil.String2Date(pupPlanModel.getPlanAssembleDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				pupPlanModel.setPlanAssembleDate(null);
				checkInfo.append("计划装配日期格式录入有误");
			}
		}
		if (StringUtil.isNotEmpty(pupPlanModel.getPlanAssembleTime())) {
			try {
				if (pupPlanModel.getPlanAssembleTime().length() > 11) {
					pupPlanModel.setPlanAssembleTime(pupPlanModel.getPlanAssembleTime().substring(11));
				}
				PupUtil.String2Date(pupPlanModel.getPlanAssembleTime(), "HH:mm:ss");
			} catch (Exception e) {
				pupPlanModel.setPlanAssembleTime(null);
				checkInfo.append("计划装配时间格式录入有误");
			}
		}

		if(checkInfo == null || "".equals(checkInfo.toString())){
            pupPlanModel.setCheckResult("1");
            pupPlanModel.setOpeType("I");
            pupPlanModel.setCheckInfo("");
        }else{
        	pupPlanModel.setCheckResult("0");
        	pupPlanModel.setCheckInfo(checkInfo.toString());
        }
	}
}

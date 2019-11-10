package com.hanthink.pup.model;

import com.hanthink.pup.util.PupUtil;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
 * <pre> 
 * 功能描述:手工调整订单实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月24日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupManualOrderModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = -3403499973822293678L;

	protected String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	/** 订单号    */
	protected String purchaseNo; 
	
	/** 物流单号     */
	protected String orderNo; 
	
	/** 工厂代码     */
	protected String factoryCode; 
	
	/** 0:不取货   1:取货     */
	protected String pickupFlag; 
	/** 0:不取货   1:取货     */
	protected String excelPickupFlag;
	/**
	 * 合并车次     */
	protected String mergeNo; 
	
	/** 区域     */
	protected String area; 
	
	/** 订单描述     */
	protected String orderDesc; 
	
	/** 出货地     */
	protected String supFactory; 
	/** 供应商代码 */
	protected String supplierNo;
	/**
	 * 订单物流模式     */
	protected String pickupType; 
	
	/** 车型     */
	protected String carType; 
	
	/** 工作日     */
	protected String workday; 
	protected String workdayStart;
	protected String workdayEnd;
	/** 取货日期     */
	protected String pickDate;
	/** 取货时间 */
	protected String pickTime;
	/** 到货日期 */
	protected String arriveDate; 
	/** 到货时间 */
	protected String arriveTime;
	
	/** 订单交货时间     */
	protected String orderDate; 
	
	/** 集货线路     */
	protected String routeCode; 
	
	/** 累计车次     */
	protected String totalNo; 
	
	/** 当日车次     */
	protected String todayNo; 
	
	/** 创建人   */
	protected String creationUser; 
	
	/** 创建时间     */
	protected String creationTime; 
	
	/** 最后修改人 */
	protected String lastModifiedUser; 
	
	/** 最后修改时间 */
	protected String lastModifiedTime;
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getPickupFlag() {
		return pickupFlag;
	}
	public void setPickupFlag(String pickupFlag) {
		this.pickupFlag = pickupFlag;
	}
	public String getExcelPickupFlag() {
		return excelPickupFlag;
	}
	public void setExcelPickupFlag(String excelPickupFlag) {
		this.excelPickupFlag = excelPickupFlag;
	}
	public String getMergeNo() {
		return mergeNo;
	}
	public void setMergeNo(String mergeNo) {
		this.mergeNo = mergeNo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
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
	public String getPickupType() {
		return pickupType;
	}
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	public String getWorkdayStart() {
		return workdayStart;
	}
	public void setWorkdayStart(String workdayStart) {
		this.workdayStart = workdayStart;
	}
	public String getWorkdayEnd() {
		return workdayEnd;
	}
	public void setWorkdayEnd(String workdayEnd) {
		this.workdayEnd = workdayEnd;
	}
	public String getPickDate() {
		return pickDate;
	}
	public void setPickDate(String pickDate) {
		this.pickDate = pickDate;
	}
	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getTotalNo() {
		return totalNo;
	}
	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}
	public String getTodayNo() {
		return todayNo;
	}
	public void setTodayNo(String todayNo) {
		this.todayNo = todayNo;
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
	
	private String uuid;
	private String checkResult;
	private String excelCheckResult;
	private String checkInfo;
	private String importStatus;
	private String excelImportStatus;
	private String opeType;

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
	public String getExcelCheckResult() {
		return excelCheckResult;
	}
	public void setExcelCheckResult(String excelCheckResult) {
		this.excelCheckResult = excelCheckResult;
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
	public String getExcelImportStatus() {
		return excelImportStatus;
	}
	public void setExcelImportStatus(String excelImportStatus) {
		this.excelImportStatus = excelImportStatus;
	}
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public static void checkImportManualOrder(PupManualOrderModel orderModel) {
		StringBuffer checkInfo = new StringBuffer();
		
		if(StringUtil.isEmpty(orderModel.getPurchaseNo())) {
			checkInfo.append("订单号为空;");
		}
		if(StringUtil.isEmpty(orderModel.getOrderNo())) {
			checkInfo.append("物流单号为空;");
		}
		if (StringUtil.isEmpty(orderModel.getSupFactory())) {
			checkInfo.append("出货地为空;");
		}
		if (StringUtil.isEmpty(orderModel.getSupplierNo())) {
			checkInfo.append("供应商代码为空;");
		}
		if (StringUtil.isEmpty(orderModel.getRouteCode())) {
			checkInfo.append("集货线路为空;");
		}
		if (StringUtil.isNotEmpty(orderModel.getWorkday())) {
			try {
				if (orderModel.getWorkday().length() > 11) {
					orderModel.setWorkday(orderModel.getWorkday().substring(0,10));
				}
				PupUtil.String2Date(orderModel.getWorkday(), "yyyy-MM-dd");
			} catch (Exception e) {
				orderModel.setWorkday(null);
				checkInfo.append("工作日日期格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getPickDate())) {
			try {
				if (orderModel.getPickDate().length() > 11) {
					orderModel.setPickDate(orderModel.getPickDate().substring(0,11));
				}
				PupUtil.String2Date(orderModel.getPickDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				orderModel.setPickDate(null);
				checkInfo.append("计划取货日期格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getPickTime())) {
			try {
				if (orderModel.getPickTime().length() > 11) {
					orderModel.setPickTime(orderModel.getPickTime().substring(11));
				}
				PupUtil.String2Date(orderModel.getPickTime(), "HH:mm:ss");
			} catch (Exception e) {
				orderModel.setPickTime(null);
				checkInfo.append("计划取货时间格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getArriveDate())) {
			try {
				if (orderModel.getArriveDate().length() > 11) {
					orderModel.setArriveDate(orderModel.getArriveDate().substring(0, 10));
				}
				PupUtil.String2Date(orderModel.getArriveDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				orderModel.setArriveDate(null);
				checkInfo.append("计划到货日期格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getArriveTime())) {
			try {
				if (orderModel.getArriveTime().length() > 11) {
					orderModel.setArriveTime(orderModel.getArriveTime().substring(11));
				}
				PupUtil.String2Date(orderModel.getArriveTime(), "HH:mm:ss");
			} catch (Exception e) {
				orderModel.setArriveTime(null);
				checkInfo.append("计划到货时间格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getOrderDate())) {
			if (orderModel.getOrderDate().length() > 11) {
				orderModel.setOrderDate(orderModel.getOrderDate().substring(0, 10));
			}
			try {
				PupUtil.String2Date(orderModel.getOrderDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				orderModel.setOrderDate(null);
				checkInfo.append("订单到货日期格式有误");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getTodayNo())) {
			if (!PupUtil.validateInteger(orderModel.getTodayNo())) {
				orderModel.setTodayNo(null);
				checkInfo.append("当日车次格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getTotalNo())) {
			if (!PupUtil.validateInteger(orderModel.getTotalNo())) {
				orderModel.setTotalNo(null);
				checkInfo.append("累计车次格式有误;");
			}
		}
		if (StringUtil.isNotEmpty(orderModel.getMergeNo())) {
			if (!PupUtil.validateInteger(orderModel.getMergeNo())) {
				orderModel.setMergeNo(null);
				checkInfo.append("合并车次格式有误;");
			}
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            orderModel.setCheckResult("1");
            orderModel.setOpeType("I");
            orderModel.setCheckInfo("");
        }else{
            orderModel.setCheckResult("0");
            orderModel.setOpeType("U");
            orderModel.setCheckInfo(checkInfo.toString());
        }
	}
}
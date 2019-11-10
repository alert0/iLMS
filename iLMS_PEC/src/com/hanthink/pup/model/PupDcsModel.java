package com.hanthink.pup.model;

import java.util.List;

import com.hotent.base.core.model.AbstractModel;

/**
 *          
 * @Desc    : DCS管理MODEL 
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class PupDcsModel extends AbstractModel<String>{


    /**
     * 
     */
    private static final long serialVersionUID = -5330664257853514772L;

    @Override
    public void setId(String id) {
        
    }

    @Override
    public String getId() {
        return null;
    }
    
    /**
     * 工作日
     */
    private String workDay;
    private String workDayStr;
    
    /**
     * 工作日起始
     */
    private String workDayStart;
    private String workDayEnd;
    
    /**
     * DCS单号
     */
    private String planSheetNo;
    private String[] planSheetNoArr;
    
    /**
     * 工厂
     */
    private String factoryCode;
    
    /**
     * 路线代码
     */
    private String routeCode;
    
    /**
     * 计划发车时间
     */
    private String planStartTime;
    
    /**
     * 计划到厂时间
     */
    private String planArriveTime;
    
    /**
     * 省内外区分
     */
    private String planType;
    
    /**
     * 订单物流模式 0 累计 1 合并
     */
    private String pickType;
    private String pickTypeStr;
    
    /**
     * 车次
     */
    private String carNum;
    
    /**
     * 取货车辆
     */
    private String takeCar;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 路线名称
     */
    private String routeName;
    
    /**
     * 打印状态
     */
    private String status;
    private String statusStr;
    
    /**
     * 任务执行状态
     */
    private String excuteStatus;
    private String excuteStatusStr;
    
    /**
     * 创建人
     */
    private String createUser;
    
    /**
     * 操作人
     * @return
     */
    private String opeId;
    
    /**
     * 区域 省内省外
     */
    private String area;
    
    /**
     * 车型
     */
    private String carType;
    
    /**
     * 累计车次
     */
    private String totalNo;
    
    /**
     * 合并车次
     */
    private String mergeNo;
    
    /**
     * 出货地代码
     */
    private String supFactory;
    
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 订单号
     */
    private String purchaseNo;
    
    /**
     * 当日车次
     */
    private String todayNo;
    
    /**
     * 取货日期
     */
    private String pickDate;
    
    /**
     * 计划取货时间带时分秒
     */
    private String planPickDate;
    
    /**
     * 取货时间
     */
    private String pickTime;
    
    /**
     * 到货日期
     */
    private String arriveDate;
    
    /**
     * 到货时间
     */
    private String arriveTime;
    
    /**
     * 装配日期
     */
    private String assembleDate;
    
    /**
     * 计划装配时间
     */
    private String planAssembleTime;
    
    /**
     * 装配时间
     */
    private String assembleTime;
    
    /**
     * 订单用途
     */
    private String orderUse;
    
    /**
     * 外物流管理员
     */
    private String wwlManager;
    
    /**
     * 内物流管理员
     */
    private String nwlManager;
    
    /**
     * 供应商数量
     * @return
     */
    private Integer supPickNum; 
    private String supPickNumStr;
    
    /**
     * 需要的封条号数量
     */
    private Integer sealNum;
    
    /**
     * 唯一标识
     * @return
     */
    private String sessionNo;
    
    /**
     * 打印HTML
     */
    private String printHtml;
    
    /**
     * 封条号
     */
    private String sealNo;
    
    /**
     * 到货仓库
     */
    private String orderDepot;
    
    /**
     * 供应商代码
     */
    private String supplierNo;
    
    /**
     * 操作人
     */
    private String operationUser;
    
    /**
     * 计划发车日期
     */
    private String planStartDate;
    
    /**
     * 计划到厂日期
     */
    private String planEndDate;
    
    /**
     * 计划到厂时间
     */
    private String planEndTime;
    
    /**
     * 工厂名称
     */
    private String factoryName;
    
    /**
     * 该DCS单对应的订单号
     */
    private List<PupDcsModel> orderList;
    
    /**
     * logo
     */
    private Object logoImg;
    
    /**
     * logo
     */
    private Object gacneLogoImg;
    
    /**
     * 取货车辆类型 0飞翼车 1厢车 2其他  存在其他的时候报错
     */
    private String takeCarType;
    
    /**
     * 车牌号
     */
    private String plateNum;
    
    /**
     * 返空站台
     * @return
     */
    private String retEmptyPlatform;
    
    /**
     * 计算队列
     */
    private String unloadPort;
    
    public String getUnloadPort() {
        return unloadPort;
    }

    public void setUnloadPort(String unloadPort) {
        this.unloadPort = unloadPort;
    }

    public String getRetEmptyPlatform() {
        return retEmptyPlatform;
    }

    public void setRetEmptyPlatform(String retEmptyPlatform) {
        this.retEmptyPlatform = retEmptyPlatform;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getTakeCarType() {
        return takeCarType;
    }

    public void setTakeCarType(String takeCarType) {
        this.takeCarType = takeCarType;
    }

    public Object getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(Object logoImg) {
        this.logoImg = logoImg;
    }

    public List<PupDcsModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<PupDcsModel> orderList) {
        this.orderList = orderList;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public String getOrderDepot() {
        return orderDepot;
    }

    public void setOrderDepot(String orderDepot) {
        this.orderDepot = orderDepot;
    }

    public String getOpeId() {
        return opeId;
    }

    public void setOpeId(String opeId) {
        this.opeId = opeId;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getPlanSheetNo() {
        return planSheetNo;
    }

    public void setPlanSheetNo(String planSheetNo) {
        this.planSheetNo = planSheetNo;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(String planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPickType() {
        return pickType;
    }

    public void setPickType(String pickType) {
        this.pickType = pickType;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getTakeCar() {
        return takeCar;
    }

    public void setTakeCar(String takeCar) {
        this.takeCar = takeCar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getPickTypeStr() {
        return pickTypeStr;
    }

    public void setPickTypeStr(String pickTypeStr) {
        this.pickTypeStr = pickTypeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getWorkDayStart() {
        return workDayStart;
    }

    public void setWorkDayStart(String workDayStart) {
        this.workDayStart = workDayStart;
    }

    public String getWorkDayStr() {
        return workDayStr;
    }

    public void setWorkDayStr(String workDayStr) {
        this.workDayStr = workDayStr;
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

    public String getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(String totalNo) {
        this.totalNo = totalNo;
    }

    public String getMergeNo() {
        return mergeNo;
    }

    public void setMergeNo(String mergeNo) {
        this.mergeNo = mergeNo;
    }

    public String getSupFactory() {
        return supFactory;
    }

    public void setSupFactory(String supFactory) {
        this.supFactory = supFactory;
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

    public String getTodayNo() {
        return todayNo;
    }

    public void setTodayNo(String todayNo) {
        this.todayNo = todayNo;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getPlanPickDate() {
        return planPickDate;
    }

    public void setPlanPickDate(String planPickDate) {
        this.planPickDate = planPickDate;
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

    public String getAssembleDate() {
        return assembleDate;
    }

    public void setAssembleDate(String assembleDate) {
        this.assembleDate = assembleDate;
    }

    public String getPlanAssembleTime() {
        return planAssembleTime;
    }

    public void setPlanAssembleTime(String planAssembleTime) {
        this.planAssembleTime = planAssembleTime;
    }

    public String getAssembleTime() {
        return assembleTime;
    }

    public void setAssembleTime(String assembleTime) {
        this.assembleTime = assembleTime;
    }

    public String getOrderUse() {
        return orderUse;
    }

    public void setOrderUse(String orderUse) {
        this.orderUse = orderUse;
    }

    public String getWwlManager() {
        return wwlManager;
    }

    public void setWwlManager(String wwlManager) {
        this.wwlManager = wwlManager;
    }

    public String getNwlManager() {
        return nwlManager;
    }

    public void setNwlManager(String nwlManager) {
        this.nwlManager = nwlManager;
    }

    public String getWorkDayEnd() {
        return workDayEnd;
    }

    public void setWorkDayEnd(String workDayEnd) {
        this.workDayEnd = workDayEnd;
    }

    public String[] getPlanSheetNoArr() {
        return planSheetNoArr;
    }

    public void setPlanSheetNoArr(String[] planSheetNoArr) {
        this.planSheetNoArr = planSheetNoArr;
    }

    public Integer getSupPickNum() {
        return supPickNum;
    }

    public void setSupPickNum(Integer supPickNum) {
        this.supPickNum = supPickNum;
    }

    public Integer getSealNum() {
        return sealNum;
    }

    public void setSealNum(Integer sealNum) {
        this.sealNum = sealNum;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getPrintHtml() {
        return printHtml;
    }

    public void setPrintHtml(String printHtml) {
        this.printHtml = printHtml;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getSupPickNumStr() {
        return supPickNumStr;
    }

    public void setSupPickNumStr(String supPickNumStr) {
        this.supPickNumStr = supPickNumStr;
    }

    public String getExcuteStatus() {
        return excuteStatus;
    }

    public void setExcuteStatus(String excuteStatus) {
        this.excuteStatus = excuteStatus;
    }

    public String getExcuteStatusStr() {
        return excuteStatusStr;
    }

    public void setExcuteStatusStr(String excuteStatusStr) {
        this.excuteStatusStr = excuteStatusStr;
    }

    public Object getGacneLogoImg() {
        return gacneLogoImg;
    }

    public void setGacneLogoImg(Object gacneLogoImg) {
        this.gacneLogoImg = gacneLogoImg;
    }
    
    
}

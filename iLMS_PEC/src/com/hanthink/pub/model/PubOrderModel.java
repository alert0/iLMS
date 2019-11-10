package com.hanthink.pub.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 零件订购基础数据
 * @Desc    : 
 * @CreateOn: 2018年11月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public class PubOrderModel extends AbstractModel<String> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5417815498221120722L;
    
    /**
     * 箱号
     */
    protected String xs;

    /**
     * 逻辑主键
     */
    protected String id;

    // 零件号
    private String partNo;
    
    //工厂
    private String factoryCode;

    // 简号
    private String partShortNo;

    // 零件名称
    private String partName;

    // 供应商代码
    private String supplierNo;

    // 供应商名称
    private String supplierName;

    // 出货地代码
    private String supFactory;

    // 物流模式
    private String logisticsModel;

    // 车间
    private String workcenter;

    // 出货仓库
    private String shipDepot;

    // 出货仓库类别
    private String shipDepotType;

    // 信息点
    private String planCode;
    private String planCode1;

    // 到货仓库
    private String arrDepot;

    // 到货仓库类别
    private String arrDepotType;

    // 收容数
    private String standardPackage;
    private Integer standardPackageInt;

    // 订购包装数
    private String orderPackage;
    private Integer orderPackageInt;

    // 组单台套数
    private String orderProductNum;
    private Integer orderProductNumInt;

    // 组单提前台套数
    private String aheadProductNum;
    private Integer aheadProductNumInt;

    // 配送提前台套数
    private String distributionNum;
    private Integer distributionNumInt;

    // 到货提前台套数
    private String arriveNum;
    private Integer arriveNumInt;

    // 组单提前台套数
    private String deliveryNum;
    private Integer deliveryNumInt;

    // 发车提前台套数
    private String dispatchNum;
    private Integer dispatchNumInt;

    // 备件提前台套数
    private String prepareNum;
    private Integer prepareNumInt;

    // 零件组代码
    private String partgroupNo;

    // 零件组名称
    private String partgroupName;

    // 组票台套数
    private String insProductNum;
    private Integer insProductNumInt;

    // 组单指示票数
    private String orderInsNum;
    private Integer orderInsNumInt;

    // 是否考虑出货地切换
    private String genInsWay;

    // 是否生成订单
    private String orderFlag;

    // 装车代码
    private String routeCode;

    // 装车代码描述
    private String routeDesc;

    // 零件记号
    private String partMark;

    // 计算队列
    private String unloadPort;

    // 初始sortId
    private String firstSortId;
    private Integer firstSortIdInt;

    // 安全库存
    private String safeNum;
    private Integer safeNumInt;

    // 生效日期
    private String effStartStr;

    // 失效日期
    private String effEndStr;
    
    //供货比率
    private String supplyRate;
    
    //物流标识
    private String logistcsFlag;
    
    //创建人
    private String createUser;
    
    //卸货口
    private String mpUnloadPort;
    
    //车型
    private String modelCode;
    
    //同步零件组Id
    private String partGroupId;
    
    //创建人
    private String creationUser;
    
    //最后修改人
    private String lastModifiedUser;
    
    //工程深度
    private String locDepth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getPartShortNo() {
        return partShortNo;
    }

    public void setPartShortNo(String partShortNo) {
        this.partShortNo = partShortNo;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
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

    public String getSupFactory() {
        return supFactory;
    }

    public void setSupFactory(String supFactory) {
        this.supFactory = supFactory;
    }

    public String getLogisticsModel() {
        return logisticsModel;
    }

    public void setLogisticsModel(String logisticsModel) {
        this.logisticsModel = logisticsModel;
    }

    public String getWorkcenter() {
        return workcenter;
    }

    public void setWorkcenter(String workcenter) {
        this.workcenter = workcenter;
    }

    public String getShipDepot() {
        return shipDepot;
    }

    public void setShipDepot(String shipDepot) {
        this.shipDepot = shipDepot;
    }

    public String getShipDepotType() {
        return shipDepotType;
    }

    public void setShipDepotType(String shipDepotType) {
        this.shipDepotType = shipDepotType;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getArrDepot() {
        return arrDepot;
    }

    public void setArrDepot(String arrDepot) {
        this.arrDepot = arrDepot;
    }

    public String getArrDepotType() {
        return arrDepotType;
    }

    public void setArrDepotType(String arrDepotType) {
        this.arrDepotType = arrDepotType;
    }

    public String getStandardPackage() {
        return standardPackage;
    }

    public void setStandardPackage(String standardPackage) {
        this.standardPackage = standardPackage;
    }

    public Integer getStandardPackageInt() {
        return standardPackageInt;
    }

    public void setStandardPackageInt(Integer standardPackageInt) {
        this.standardPackageInt = standardPackageInt;
    }

    public String getOrderPackage() {
        return orderPackage;
    }

    public void setOrderPackage(String orderPackage) {
        this.orderPackage = orderPackage;
    }

    public Integer getOrderPackageInt() {
        return orderPackageInt;
    }

    public void setOrderPackageInt(Integer orderPackageInt) {
        this.orderPackageInt = orderPackageInt;
    }

    public String getOrderProductNum() {
        return orderProductNum;
    }

    public void setOrderProductNum(String orderProductNum) {
        this.orderProductNum = orderProductNum;
    }

    public Integer getOrderProductNumInt() {
        return orderProductNumInt;
    }

    public void setOrderProductNumInt(Integer orderProductNumInt) {
        this.orderProductNumInt = orderProductNumInt;
    }

    public String getAheadProductNum() {
        return aheadProductNum;
    }

    public void setAheadProductNum(String aheadProductNum) {
        this.aheadProductNum = aheadProductNum;
    }

    public Integer getAheadProductNumInt() {
        return aheadProductNumInt;
    }

    public void setAheadProductNumInt(Integer aheadProductNumInt) {
        this.aheadProductNumInt = aheadProductNumInt;
    }

    public String getDistributionNum() {
        return distributionNum;
    }

    public void setDistributionNum(String distributionNum) {
        this.distributionNum = distributionNum;
    }

    public Integer getDistributionNumInt() {
        return distributionNumInt;
    }

    public void setDistributionNumInt(Integer distributionNumInt) {
        this.distributionNumInt = distributionNumInt;
    }

    public String getArriveNum() {
        return arriveNum;
    }

    public void setArriveNum(String arriveNum) {
        this.arriveNum = arriveNum;
    }

    public Integer getArriveNumInt() {
        return arriveNumInt;
    }

    public void setArriveNumInt(Integer arriveNumInt) {
        this.arriveNumInt = arriveNumInt;
    }

    public String getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Integer getDeliveryNumInt() {
        return deliveryNumInt;
    }

    public void setDeliveryNumInt(Integer deliveryNumInt) {
        this.deliveryNumInt = deliveryNumInt;
    }

    public String getDispatchNum() {
        return dispatchNum;
    }

    public void setDispatchNum(String dispatchNum) {
        this.dispatchNum = dispatchNum;
    }

    public Integer getDispatchNumInt() {
        return dispatchNumInt;
    }

    public void setDispatchNumInt(Integer dispatchNumInt) {
        this.dispatchNumInt = dispatchNumInt;
    }

    public String getPrepareNum() {
        return prepareNum;
    }

    public void setPrepareNum(String prepareNum) {
        this.prepareNum = prepareNum;
    }

    public Integer getPrepareNumInt() {
        return prepareNumInt;
    }

    public void setPrepareNumInt(Integer prepareNumInt) {
        this.prepareNumInt = prepareNumInt;
    }

    public String getPartgroupNo() {
        return partgroupNo;
    }

    public void setPartgroupNo(String partgroupNo) {
        this.partgroupNo = partgroupNo;
    }

    public String getPartgroupName() {
        return partgroupName;
    }

    public void setPartgroupName(String partgroupName) {
        this.partgroupName = partgroupName;
    }

    public String getInsProductNum() {
        return insProductNum;
    }

    public void setInsProductNum(String insProductNum) {
        this.insProductNum = insProductNum;
    }

    public Integer getInsProductNumInt() {
        return insProductNumInt;
    }

    public void setInsProductNumInt(Integer insProductNumInt) {
        this.insProductNumInt = insProductNumInt;
    }

    public String getOrderInsNum() {
        return orderInsNum;
    }

    public void setOrderInsNum(String orderInsNum) {
        this.orderInsNum = orderInsNum;
    }

    public Integer getOrderInsNumInt() {
        return orderInsNumInt;
    }

    public void setOrderInsNumInt(Integer orderInsNumInt) {
        this.orderInsNumInt = orderInsNumInt;
    }

    public String getGenInsWay() {
        return genInsWay;
    }

    public void setGenInsWay(String genInsWay) {
        this.genInsWay = genInsWay;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public String getPartMark() {
        return partMark;
    }

    public void setPartMark(String partMark) {
        this.partMark = partMark;
    }

    public String getUnloadPort() {
        return unloadPort;
    }

    public void setUnloadPort(String unloadPort) {
        this.unloadPort = unloadPort;
    }

    public String getFirstSortId() {
        return firstSortId;
    }

    public void setFirstSortId(String firstSortId) {
        this.firstSortId = firstSortId;
    }

    public Integer getFirstSortIdInt() {
        return firstSortIdInt;
    }

    public void setFirstSortIdInt(Integer firstSortIdInt) {
        this.firstSortIdInt = firstSortIdInt;
    }

    public String getSafeNum() {
        return safeNum;
    }

    public void setSafeNum(String safeNum) {
        this.safeNum = safeNum;
    }

    public Integer getSafeNumInt() {
        return safeNumInt;
    }

    public void setSafeNumInt(Integer safeNumInt) {
        this.safeNumInt = safeNumInt;
    }

    public String getEffStartStr() {
        return effStartStr;
    }

    public void setEffStartStr(String effStartStr) {
        this.effStartStr = effStartStr;
    }

    public String getEffEndStr() {
        return effEndStr;
    }

    public void setEffEndStr(String effEndStr) {
        this.effEndStr = effEndStr;
    }

    public String getSupplyRate() {
        return supplyRate;
    }

    public void setSupplyRate(String supplyRate) {
        this.supplyRate = supplyRate;
    }

    public String getLogistcsFlag() {
        return logistcsFlag;
    }

    public void setLogistcsFlag(String logistcsFlag) {
        this.logistcsFlag = logistcsFlag;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getMpUnloadPort() {
        return mpUnloadPort;
    }

    public void setMpUnloadPort(String mpUnloadPort) {
        this.mpUnloadPort = mpUnloadPort;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getPartGroupId() {
        return partGroupId;
    }

    public void setPartGroupId(String partGroupId) {
        this.partGroupId = partGroupId;
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

    public String getLocDepth() {
        return locDepth;
    }

    public void setLocDepth(String locDepth) {
        this.locDepth = locDepth;
    }

	public String getXs() {
		return xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

    public String getPlanCode1() {
        return planCode1;
    }

    public void setPlanCode1(String planCode1) {
        this.planCode1 = planCode1;
    }
    
}
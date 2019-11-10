package com.hanthink.pub.model;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc : 例外订购需求导入Model
 * @CreateOn: 2018年9月11日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
public class PubOrderModelImport extends PubOrderModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3644918350353655988L;

    /** 导入UUID */
    private String uuid;

    /** 检查结果(0错误;1：通过;2:重复存在) */
    private String checkResult;

    /** 检查结果信息 */
    private String checkInfo;

    /** 导入状态 */
    private String importStatus;

    /**
     * OPE_TYPE
     * 
     * @return
     */
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
     * 校验导入EXCEL数据
     * 
     * @param m
     */
    public static void checkImportData(PubOrderModelImport m) throws Exception {
        StringBuffer checkInfo = new StringBuffer();
        if (m.getLogisticsModel() == null || "".equals(m.getLogisticsModel())) {
            checkInfo.append("物流模式不能为空;");
        } else {
            if ("JIT".equals(m.getLogisticsModel())) {
                if (m.getPlanCode() == null || "".equals(m.getPlanCode())) {
                    checkInfo.append("物流模式为厂外拉动,信息点不能为空;");
                }
                if (m.getArrDepot() == null || "".equals(m.getArrDepot())) {
                    checkInfo.append("物流模式为厂外拉动,到货仓库不能为空;");
                }
                if (m.getShipDepot() == null || "".equals(m.getShipDepot())) {
                    checkInfo.append("物流模式为厂外拉动,出货仓库不能为空;");
                }
                if (m.getShipDepotType() == null || "".equals(m.getShipDepotType())) {
                    checkInfo.append("物流模式为厂外拉动,出货仓库类别不能为空;");
                }
//                if (m.getArrDepotType() == null || "".equals(m.getArrDepotType())) {
//                    checkInfo.append("物流模式为厂外拉动,到货仓库类别不能为空;");
//                }
                if (m.getStandardPackage() == null || "".equals(m.getStandardPackage())) {
                    checkInfo.append("物流模式为厂外拉动,收容数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getStandardPackage());
                        if (i < 0) {
                            m.setStandardPackage("");
                            checkInfo.append("物流模式为厂外拉动,收容数大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setStandardPackage("");
                        checkInfo.append("物流模式为厂外拉动,收容数大于0的整数;");
                    }
                }
                if (m.getOrderPackage() == null || "".equals(m.getOrderPackage())) {
                    checkInfo.append("物流模式为厂外拉动,订购包装不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getOrderPackage());
                        if (i < 0) {
                            m.setOrderPackage("");
                            checkInfo.append("物流模式为厂外拉动,订购包装大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setOrderPackage("");
                        checkInfo.append("物流模式为厂外拉动,订购包装大于0的整数;");
                    }
                }
                if (m.getOrderProductNum() == null || "".equals(m.getOrderProductNum())) {
                    checkInfo.append("物流模式为厂外拉动,组单台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getOrderProductNum());
                        if (i < 0) {
                            m.setOrderProductNum("");
                            checkInfo.append("物流模式为厂外拉动,组单台套数大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setOrderProductNum("");
                        checkInfo.append("物流模式为厂外拉动,组单台套数大于0的整数;");
                    }
                }
                if (m.getAheadProductNum() == null || "".equals(m.getAheadProductNum())) {
                    checkInfo.append("物流模式为厂外拉动,组单提前台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getAheadProductNum());
                        if (i < 0) {
                            m.setAheadProductNum("");
                            checkInfo.append("物流模式为厂外拉动,组单提前台套数需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setAheadProductNum("");
                        checkInfo.append("物流模式为厂外拉动,组单提前台套数需大于等于0;");
                    }
                }
                if (m.getDistributionNum() == null || "".equals(m.getDistributionNum())) {
                    checkInfo.append("物流模式为厂外拉动,配送提前台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDistributionNum());
                        if (i < 0) {
                            m.setDistributionNum("");
                            checkInfo.append("物流模式为厂外拉动,配送提前台套数需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDistributionNum("");
                        checkInfo.append("物流模式为厂外拉动,组单提前台套数需大于等于0;");
                    }
                }
                if (m.getArriveNum() == null || "".equals(m.getArriveNum())) {
                    checkInfo.append("物流模式为厂外拉动,到货提前台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getArriveNum());
                        if (i < 0) {
                            m.setArriveNum("");
                            checkInfo.append("物流模式为厂外拉动,到货提前台套数需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setArriveNum("");
                        checkInfo.append("物流模式为厂外拉动,到货提前台套数需大于等于0;");
                    }
                }
                if (m.getDeliveryNum() == null || "".equals(m.getDeliveryNum())) {
                    checkInfo.append("物流模式为厂外拉动,装车运输(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDeliveryNum());
                        if (i < 0) {
                            m.setDeliveryNum("");
                            checkInfo.append("物流模式为厂外拉动,装车运输(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDeliveryNum("");
                        checkInfo.append("物流模式为厂外拉动,装车运输(台套数)需大于等于0;");
                    }
                }
                if (m.getDispatchNum() == null || "".equals(m.getDispatchNum())) {
                    checkInfo.append("物流模式为厂外拉动,发车取货(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDispatchNum());
                        if (i < 0) {
                            m.setDispatchNum("");
                            checkInfo.append("物流模式为厂外拉动,发车取货(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDispatchNum("");
                        checkInfo.append("物流模式为厂外拉动,发车取货(台套数)需大于等于0;");
                    }
                }
                if (m.getPrepareNum() == null || "".equals(m.getPrepareNum())) {
                    checkInfo.append("物流模式为厂外拉动,备件(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getPrepareNum());
                        if (i < 0) {
                            m.setPrepareNum("");
                            checkInfo.append("物流模式为厂外拉动,备件(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setPrepareNum("");
                        checkInfo.append("物流模式为厂外拉动,备件(台套数)需大于等于0;");
                    }
                }
            } else if ("JISO".equals(m.getLogisticsModel())) {
                if (m.getPlanCode() == null || "".equals(m.getPlanCode())) {
                    checkInfo.append("物流模式为厂外同步,信息点不能为空;");
                }
                if (m.getDistributionNum() == null || "".equals(m.getDistributionNum())) {
                    checkInfo.append("物流模式为厂外同步,配送提前台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDistributionNum());
                        if (i < 0) {
                            m.setDistributionNum("");
                            checkInfo.append("物流模式为厂外同步,配送提前台套数需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDistributionNum("");
                        checkInfo.append("物流模式为厂外同步,配送提前台套数需大于等于0;");
                    }
                }
                if (m.getArriveNum() == null || "".equals(m.getArriveNum())) {
                    checkInfo.append("物流模式为厂外同步,到货提前台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getArriveNum());
                        if (i < 0) {
                            m.setArriveNum("");
                            checkInfo.append("物流模式为厂外同步,到货提前台套数需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setArriveNum("");
                        checkInfo.append("物流模式为厂外同步,到货提前台套数需大于等于0;");
                    }
                }
                if (m.getDeliveryNum() == null || "".equals(m.getDeliveryNum())) {
                    checkInfo.append("物流模式为厂外同步,装车运输(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDeliveryNum());
                        if (i < 0) {
                            m.setDeliveryNum("");
                            checkInfo.append("物流模式为厂外同步,装车运输(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDeliveryNum("");
                        checkInfo.append("物流模式为厂外同步,装车运输(台套数)需大于等于0;");
                    }
                }
                if (m.getDispatchNum() == null || "".equals(m.getDispatchNum())) {
                    checkInfo.append("物流模式为厂外同步,发车取货(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDispatchNum());
                        if (i < 0) {
                            m.setDispatchNum("");
                            checkInfo.append("物流模式为厂外同步,发车取货(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDispatchNum("");
                        checkInfo.append("物流模式为厂外同步,发车取货(台套数)需大于等于0;");
                    }
                }
                if (m.getPrepareNum() == null || "".equals(m.getPrepareNum())) {
                    checkInfo.append("物流模式为厂外同步,备件(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getPrepareNum());
                        if (i < 0) {
                            m.setPrepareNum("");
                            checkInfo.append("物流模式为厂外同步,备件(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setPrepareNum("");
                        checkInfo.append("物流模式为厂外同步,备件(台套数)需大于等于0;");
                    }
                }
                if (m.getPartgroupNo() == null || "".equals(m.getPartgroupNo())) {
                    checkInfo.append("物流模式为厂外同步,零件组代码不能为空;");
                }
                if (m.getPartgroupName() == null || "".equals(m.getPartgroupName())) {
                    checkInfo.append("物流模式为厂外同步,零件组名称不能为空;");
                }
                if (m.getInsProductNum() == null || "".equals(m.getInsProductNum())) {
                    checkInfo.append("物流模式为厂外同步,组票台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getInsProductNum());
                        if (i < 0) {
                            m.setInsProductNum("");
                            checkInfo.append("物流模式为厂外同步,组票台套数大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setInsProductNum("");
                        checkInfo.append("物流模式为厂外同步,组票台套数大于0的整数;");
                    }
                }
                if (m.getOrderInsNum() == null || "".equals(m.getOrderInsNum())) {
                    checkInfo.append("物流模式为厂外同步,组单指示票数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getOrderInsNum());
                        if (i < 0) {
                            m.setOrderInsNum("");
                            checkInfo.append("物流模式为厂外同步,组单指示票数大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setOrderInsNum("");
                        checkInfo.append("物流模式为厂外同步,组单指示票数大于0的整数;");
                    }
                }
                if (m.getStandardPackage() == null || "".equals(m.getStandardPackage())) {
                    checkInfo.append("物流模式为厂外同步,收容数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getStandardPackage());
                        if (i < 0) {
                            m.setStandardPackage("");
                            checkInfo.append("物流模式为厂外同步,收容数大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setStandardPackage("");
                        checkInfo.append("物流模式为厂外同步,收容数大于0的整数;");
                    }
                }
                if (m.getGenInsWay() == null || "".equals(m.getGenInsWay())) {
                    checkInfo.append("物流模式为厂外同步,是否考虑出货地切换不能为空;");
                }
                if (m.getOrderFlag() == null || "".equals(m.getOrderFlag())) {
                    checkInfo.append("物流模式为厂外同步,是否生成订单不能为空;");
                }
                if (m.getRouteCode() == null || "".equals(m.getRouteCode())) {
                    checkInfo.append("物流模式为厂外同步,装车代码不能为空;");
                }
            } else if ("SW".equals(m.getLogisticsModel())) {
                if (m.getMpUnloadPort() == null || "".equals(m.getMpUnloadPort())) {
                    checkInfo.append("物流模式为循环取货,计算队列不能为空;");
                }
                if (m.getStandardPackage() == null || "".equals(m.getStandardPackage())) {
                    checkInfo.append("物流模式为循环取货,收容数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getStandardPackage());
                        if (i < 0) {
                            m.setStandardPackage("");
                            checkInfo.append("物流模式为循环取货,收容数大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setStandardPackage("");
                        checkInfo.append("物流模式为循环取货,收容数大于0的整数;");
                    }
                }
                if (m.getPrepareNum() == null || "".equals(m.getPrepareNum())) {
                    checkInfo.append("物流模式为循环取货,备件(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getPrepareNum());
                        if (i < 0) {
                            m.setPrepareNum("");
                            checkInfo.append("物流模式为循环取货,备件(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setPrepareNum("");
                        checkInfo.append("物流模式为循环取货,备件(台套数)需大于等于0;");
                    }
                }
                if (m.getDistributionNum() == null || "".equals(m.getDistributionNum())) {
                    checkInfo.append("物流模式为循环取货,配送提前台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDistributionNum());
                        if (i < 0) {
                            m.setDistributionNum("");
                            checkInfo.append("物流模式为循环取货,配送提前台套数需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDistributionNum("");
                        checkInfo.append("物流模式为循环取货,配送提前台套数需大于等于0;");
                    }
                }
                if (m.getArriveNum() == null || "".equals(m.getArriveNum())) {
                    checkInfo.append("物流模式为循环取货,到货提前台套数不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getArriveNum());
                        if (i < 0) {
                            m.setArriveNum("");
                            checkInfo.append("物流模式为循环取货,到货提前台套数需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setArriveNum("");
                        checkInfo.append("物流模式为循环取货,到货提前台套数需大于等于0;");
                    }
                }
                if (m.getDeliveryNum() == null || "".equals(m.getDeliveryNum())) {
                    checkInfo.append("物流模式为循环取货,装车运输(台套数)不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getDeliveryNum());
                        if (i < 0) {
                            m.setDeliveryNum("");
                            checkInfo.append("物流模式为循环取货,装车运输(台套数)需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setDeliveryNum("");
                        checkInfo.append("物流模式为循环取货,装车运输(台套数)需大于等于0;");
                    }
                }
                if (m.getOrderPackage() == null || "".equals(m.getOrderPackage())) {
                    checkInfo.append("物流模式为循环取货,订购包装不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getOrderPackage());
                        if (i < 0) {
                            m.setOrderPackage("");
                            checkInfo.append("物流模式为循环取货,订购包装大于0的整数;");
                        }
                    } catch (Exception e) {
                        m.setOrderPackage("");
                        checkInfo.append("物流模式为循环取货,订购包装大于0的整数;");
                    }
                }
                if (m.getArrDepot() == null || "".equals(m.getArrDepot())) {
                    checkInfo.append("物流模式为循环取货,到货仓库不能为空;");
                }
                if (m.getSafeNum() == null || "".equals(m.getSafeNum())) {
                    checkInfo.append("物流模式为循环取货,安全库存不能为空;");
                } else {
                    try {
                        Integer i = Integer.parseInt(m.getSafeNum());
                        if (i < 0) {
                            m.setSafeNum("");
                            checkInfo.append("物流模式为循环取货,安全库存需大于等于0;");
                        }
                    } catch (Exception e) {
                        m.setSafeNum("");
                        checkInfo.append("物流模式为循环取货,安全库存需大于等于0;");
                    }
                }
            }
        }

        if (m.getWorkcenter() == null || "".equals(m.getWorkcenter())) {
            checkInfo.append("车间不能为空;");
        }

        if (m.getPartNo() == null || "".equals(m.getPartNo())) {
            checkInfo.append("零件号不能为空;");
        }

        if (m.getSupplierNo() == null || "".equals(m.getSupplierNo())) {
            checkInfo.append("供应商代码不能为空;");
        }

        if (m.getSupFactory() == null || "".equals(m.getSupFactory())) {
            checkInfo.append("出货地代码不能为空;");
        }

        // 到货仓库需要根据卸货口关联
        // if(m.getArrDepot() == null || "".equals(m.getArrDepot())){
        // checkInfo.append("到货仓库不能为空;");
        // }

        if (m.getPartShortNo() == null || "".equals(m.getPartShortNo())) {
            checkInfo.append("简号不能为空;");
        }

        if (m.getEffStartStr() == null || "".equals(m.getEffStartStr())) {
            checkInfo.append("生效日期不能为空;");
        } else {
            SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
            s.parse(m.getEffStartStr());
        }

        if (m.getEffEndStr() == null || "".equals(m.getEffEndStr())) {
            checkInfo.append("失效日期不能为空;");
        } else {
            SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
            s.parse(m.getEffEndStr());
        }

        if (m.getUnloadPort() == null || "".equals(m.getUnloadPort())) {
            checkInfo.append("卸货口不能为空;");
        }

        if (m.getModelCode() == null || "".equals(m.getModelCode())) {
            checkInfo.append("车型不能为空;");
        }

        if (checkInfo == null || "".equals(checkInfo.toString())) {
            m.setCheckResult("1");
            m.setCheckInfo("");
        } else {
            m.setCheckResult("0");
            m.setCheckInfo(checkInfo.toString());
        }
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 利用正则表达式判断字符串是否是正整数
     * 
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0）$");
        boolean isDouble = pattern.matcher(str).find();
        return isDouble;
    }
}

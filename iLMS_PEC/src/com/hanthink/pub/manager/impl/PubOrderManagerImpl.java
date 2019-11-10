package com.hanthink.pub.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.dao.PubOrderDao;
import com.hanthink.pub.manager.PubOrderManager;
import com.hanthink.pub.model.PubOrderModel;
import com.hanthink.pub.model.PubOrderModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.dao.SysTypeDao;
import com.hotent.sys.util.ContextUtil;

/**
 * 订购零件基础数据业务处理
 * 
 * @Desc :
 * @CreateOn: 2018年11月4日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
@Service("pubOrderManager")
public class PubOrderManagerImpl extends AbstractManagerImpl<String, PubOrderModel> implements PubOrderManager {
    @Resource
    PubOrderDao pubOrderDao;

    @Resource
    SysTypeDao sysTypeDao;

    @Resource
    PubOrderManager pubOrderManager;

    public PubOrderManager getPubOrderManager() {
        return pubOrderManager;
    }

    @Override
    protected Dao<String, PubOrderModel> getDao() {
        return pubOrderDao;
    }

    public SysTypeDao getSysTypeDao() {
        return sysTypeDao;
    }

    @Override
    public PageList<PubOrderModel> querySwPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) pubOrderDao.querySwPubOrderForPage(model, p);
    }

    @Override
    public PageList<PubOrderModel> queryJitPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) pubOrderDao.queryJitPubOrderForPage(model, p);
    }

    @Override
    public PageList<PubOrderModel> queryJisoPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) pubOrderDao.queryJisoPubOrderForPage(model, p);
    }

    @Override
    public PageList<PubOrderModel> queryAllPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) pubOrderDao.queryAllPubOrderForPage(model, p);
    }

    /**
     * 更新数据并记录日志
     * 
     * @param demoModel
     * @param ipAddr
     * @author linzhuo
     * @DATE 2018年9月10日 上午10:54:52
     */
    // @Override
    // public void updateAndLog(PubOrderModel PubOrderModel, String ipAddr) {
    //
    // TableOpeLogVO logVO = new TableOpeLogVO();
    // logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
    // logVO.setOpeIp(ipAddr);
    // logVO.setFromName("界面更新");
    // logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
    // logVO.setTableName("MM_MP_RESIDUAL");
    // TableColumnVO pkColumnVO = new TableColumnVO();
    // pkColumnVO.setColumnName("ID");
    // pkColumnVO.setColumnVal(PubOrderModel.getId());
    // this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
    //
    // pubOrderDao.update(PubOrderModel);
    //
    // }

    /**
     * 导出临时表数据
     */
    @Override
    public List<PubOrderModelImport> queryPubOrderImportTempDataForExport(Map<String, String> paramMap) {
        return pubOrderDao.queryPubOrderImportTempDataForExport(paramMap);
    }

    @Override
    public void insertPubOrderImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
        String factoryCode = (String) paramMap.get("factoryCode");
        paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
        paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

        List<PubOrderModel> list = pubOrderDao.queryForInsertList(paramMap);
        if (list.size() < 1) {
            throw new Exception("没有正确数据可导入或已全部导入");
        }

        // 查询物流模式更新的数据
        List<PubOrderModelImport> modelMList = pubOrderDao.queryMList(paramMap);
        List<String> mIdList = new ArrayList<>();
        if (modelMList != null && modelMList.size() > 0) {
            //用于记录日志
            for (PubOrderModelImport m : modelMList) {
                mIdList.add(m.getId());
            }
            String[] mIdp = new String[mIdList.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_PUB_PART_UNLOAD");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(mIdList.toArray(mIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
            for (PubOrderModelImport m : modelMList) {
                if ("JIT".equals(m.getLogisticsModel())) {
                    // 根据车间和零件删除零件基础信息的数据
                    pubOrderDao.deleteJitPartInfo(m);
                } else if ("JISO".equals(m.getLogisticsModel())) {
                    // 根据车间和零件删除零件基础信息的数据
                    pubOrderDao.deleteJisoPartInfo(m);
                } else if ("SW".equals(m.getLogisticsModel())) {
                    // 根据车间和零件删除零件基础信息的数据
                    pubOrderDao.deleteSwPartInfo(m);
                }
            }
        }

        // 查询协同需要更新的数据
        List<String> swIds = pubOrderDao.querySwUpdateData(paramMap);
        if (swIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] swIdp = new String[swIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_MP_PART");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(swIds.toArray(swIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }

        // 查询拉动需要更新的数据
        List<String> jitIds = pubOrderDao.queryJitUpdateData(paramMap);
        if (jitIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] jitIdp = new String[jitIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_JIT_PART");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jitIds.toArray(jitIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }

        // 查询拉动需要更新的数据
        List<String> jitOrderIds = pubOrderDao.queryJitOrderUpdateData(paramMap);
        if (jitOrderIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] jitOrderIdp = new String[jitOrderIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_JIT_ORDER_CONFIG");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jitOrderIds.toArray(jitOrderIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }

        // 查询同步需要更新的数据
        List<String> jisoIds = pubOrderDao.queryJisoUpdateData(paramMap);
        if (jisoIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] jisoIdp = new String[jisoIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_JISO_PART");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jisoIds.toArray(jisoIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }

        // 查询同步需要更新的同步零件组数据
        List<String> jisoPartGroupIds = pubOrderDao.queryJisoPartGroupUpdateData(paramMap);
        if (jisoPartGroupIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] jisoPartGroupIdp = new String[jisoPartGroupIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_JISO_PARTGROUP");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jisoPartGroupIds.toArray(jisoPartGroupIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }

        // 查询同步路线需要更新的数据
        List<String> jisoPartGroupRouteIds = pubOrderDao.queryJisoPartGroupRouteUpdateData(paramMap);
        if (jisoPartGroupRouteIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] jisoPartGroupRouteIdp = new String[jisoPartGroupRouteIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_JISO_PARTGROUP_ROUTE");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jisoPartGroupRouteIds.toArray(jisoPartGroupRouteIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }

        // 写入循环取货零件基础信息数据,存在更新,不存在新增
        pubOrderDao.importSwPurOrderImportData(paramMap);

        // 写入厂外拉动零件基础信息数据,存在更新,不存在新增
        pubOrderDao.importJitPurOrderImportData(paramMap);

        // 写入厂外同步零件基础信息数据,存在更新,不存在新增
        pubOrderDao.importJisoPurOrderImportData(paramMap);

        // 写入厂外拉动零件组单基础信息数据,存在更新不存在新增
        pubOrderDao.importJitOrderPurOrderImportData(paramMap);

        // 写入厂外同步零件组基础信息数据,存在更新不存在新增
        pubOrderDao.importJisoPartGroupPurOrderImportData(paramMap);

        // 写入厂外同步零件组路线基础信息数据,存在更新不存在新增
        pubOrderDao.importJisoPartGroupRoutePurOrderImportData(paramMap);

        // 协同的新增更新数据过后,需要将MM_MP_PART中和当前导入同一计算队列而工作中心和到货仓库不同的数据的车间和到货仓库更新
        pubOrderDao.updateSwPurOrder(paramMap);

        /**
         * 处理同步零件组不存在的情况
         */
        List<PubOrderModel> jisoNotExistsPartGroupList = pubOrderDao.queryAllJisoData(paramMap, false);
        if (jisoNotExistsPartGroupList != null && jisoNotExistsPartGroupList.size() > 0) {
            for (int i = 0; i < jisoNotExistsPartGroupList.size(); i++) {
                PubOrderModel tempVo = jisoNotExistsPartGroupList.get(i);
                if (tempVo != null) {
                    // 先获取当前序列的下一个值
                    String id = pubOrderDao.queryJisoPartGroupIdFromSeq();
                    paramMap.put("id", id);
                    paramMap.put("partgroupNo", tempVo.getPartgroupNo());
                    paramMap.put("planCode", tempVo.getPlanCode());
                    // 先写零件组数据
                    pubOrderDao.insertJisoPartGroupData(paramMap);
                    // 再写厂外拉动零件数据
                    pubOrderDao.insertJisoPartData(paramMap);
                    // 再写日厂外拉动路线数据
                    pubOrderDao.insertJisoPartGroupRouteData(paramMap);
                }
            }

        }

        // 记录修改零件基础信息的日志
        List<PubOrderModel> unloadIds = pubOrderDao.queryUnloadRelationUpdateData(paramMap);
        if (unloadIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] unloadIdp = new String[unloadIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_PUB_PART_UNLOAD");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(unloadIds.toArray(unloadIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }
        // 记录卸货口和零件物流模式的关系
        pubOrderDao.mergeUnloadRelationToPartNo(paramMap);

        // 记录修改零件基础信息的日志
        List<PubOrderModel> partIds = pubOrderDao.queryPartUpdateData(paramMap);
        if (partIds.size() > 0) {
            /**
             * 声明一个String数组，用于存放List
             */
            String[] partIdp = new String[partIds.size()];
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("导入更新");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_PUB_PART_UDA");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(partIds.toArray(partIdp));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
        }
        // 取货,拉动,同步订购零件基础数据需要更新零件基础信息表
        pubOrderDao.updatePartInfo(paramMap);

        // 更新临时数据导入状态
        pubOrderDao.updatePubOrderImportStatus(paramMap);

        // 删除厂外同步不存在于同步零件的同步零件组数据
        deleteNotExistsPartJisoPartGroupData(factoryCode, ipAddr);

        // 删除厂外同步不存在于同步零件组的同步零件组路线信息数据
        deleteNotExistsPartJisoPartGroupRouteData(factoryCode, ipAddr);

        // 删除在拉动零件基础数据不存在的JIT_ORDER_CONFIG数据
        deleteNotExistsPartJitOrderConfigData(factoryCode, ipAddr);

        // 删除所有不存在于零件表的MM_PUB_PART_UDA数据
        deleteNotExistsPartUdaData(factoryCode, ipAddr);

        // 删除所有不存在于零件表的MM_PUB_PART_UNLOAD数据
        deleteNotExistsPartUnloadData(factoryCode, ipAddr);

    }

    /**
     * 根据UUID删除临时表数据
     */
    @Override
    public void deletePubOrderImportTempDataByUUID(String uuid) {
        pubOrderDao.deletePubOrderImportTempDataByUUID(uuid);
    }

    /**
     * 查询需要导出的数据
     * 
     * @throws Exception
     */
    @Override
    public List<PubOrderModel> queryPubOrderByKey(PubOrderModel model) throws Exception {
        return pubOrderDao.queryPubOrderByKey(model);
    }

    @Override
    public List<DictVO> queryPlanCode() {
        return pubOrderDao.queryPlanCode();
    }
    
    @Override
    public List<DictVO> queryPlanCode1() {
        return pubOrderDao.queryPlanCode1();
    }

    @Override
    public List<DictVO> queryArriveDepot() {
        return pubOrderDao.queryArriveDepot();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> importPubOrderModel(MultipartFile file, String uuid, String ipAddr, IUser user) {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        Boolean result = true;
        String console = "";

        if (file == null || file.isEmpty()) {
            result = false;
            console = "文件为空！";
            throw new RuntimeException(console);
        }

        // 读取Excel数据
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String[] columns = { "partNo", "modelCode", "workcenter", "supplierNo",
        		"supFactory", "partShortNo", "orderPackage", "safeNum",
                "effStartStr", "effEndStr", "logisticsModel", "shipDepot",
                "shipDepotType", "planCode", "orderProductNum",
                "aheadProductNum", "dispatchNum", "prepareNum", "deliveryNum",
                "partgroupNo", "partgroupName", "insProductNum", "orderInsNum",
                "genInsWay", "orderFlag", "routeCode", "routeDesc", "mpUnloadPort",
                "firstSortId", "standardPackage", "arrDepot","arrDepotType",
                "distributionNum", "arriveNum", "partMark", "unloadPort" };
        List<PubOrderModelImport> importList = null;
        try {
            if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
                importList = (List<PubOrderModelImport>) ExcelUtil.importExcelXLS(new PubOrderModelImport(), file.getInputStream(), columns, 2, 0);
            } else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
                importList = (List<PubOrderModelImport>) ExcelUtil.importExcelXLSX(new PubOrderModelImport(), file.getInputStream(), columns, 2, 0);
            } else {
                result = false;
                console = "上传文件不是excel类型！";
                throw new RuntimeException(console);
            }

            // 查询需要转换的的数据字典
            List<DictVO> trueFalseList = sysTypeDao.queryPubDataDictByCodeType("PUB_TRUE_FALSE");
            List<DictVO> logisticsModelList = sysTypeDao.queryPubDataDictByCodeType("PUB_MM_MODEL");
            List<DictVO> shipDepotTypeList = sysTypeDao.queryPubDataDictByCodeType("PUB_SHIP_DEPOT_TYPE");
            List<DictVO> arriveDepotTypeList = sysTypeDao.queryPubDataDictByCodeType("PUB_ARR_DEPOT_TYPE");
            List<DictVO> workCenterList = sysTypeDao.queryPubDataDictByCodeType("PUB_WORKCENTER");
            // 数据导入初始化，格式初步检查
            for (PubOrderModelImport m : importList) {
                if (shipDepotTypeList != null && shipDepotTypeList.size() > 0) {
                    for (DictVO d : shipDepotTypeList) {
                        if (d.getValueName() != null && d.getValueName().equals(m.getShipDepotType())) {
                            m.setShipDepotType(d.getValueKey());
                        }
                    }
                }
                if (workCenterList != null && workCenterList.size() > 0) {
                    for (DictVO d : workCenterList) {
                        if (d.getValueName() != null && d.getValueName().equals(m.getWorkcenter())) {
                            m.setWorkcenter(d.getValueKey());
                        }
                    }
                }
                if (arriveDepotTypeList != null && arriveDepotTypeList.size() > 0) {
                    for (DictVO d : arriveDepotTypeList) {
                        if (d.getValueName() != null && d.getValueName().equals(m.getArrDepotType())) {
                            m.setArrDepotType(d.getValueKey());
                        }
                    }
                }
                if (trueFalseList != null && trueFalseList.size() > 0) {
                    for (DictVO d : trueFalseList) {
                        if (d.getValueName() != null && d.getValueName().equals(m.getGenInsWay())) {
                            m.setGenInsWay(d.getValueKey());
                        }
                    }
                }
                if (trueFalseList != null && trueFalseList.size() > 0) {
                    for (DictVO d : trueFalseList) {
                        if (d.getValueName() != null && d.getValueName().equals(m.getOrderFlag())) {
                            m.setOrderFlag(d.getValueKey());
                        }
                    }
                }
                if (logisticsModelList != null && logisticsModelList.size() > 0) {
                    for (DictVO d : logisticsModelList) {
                        if (d.getValueName() != null && d.getValueName().equals(m.getLogisticsModel())) {
                            m.setLogisticsModel(d.getValueKey());
                        }
                    }
                }
                m.setUuid(uuid);
                m.setFactoryCode(user.getCurFactoryCode());
                m.setCreateUser(user.getAccount());
                m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
                m.setOpeType("I");
                PubOrderModelImport.checkImportData(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            console = e.getMessage();
            throw new RuntimeException(console);
        }

        // 导入数据写入到临时表
        pubOrderDao.insertImportTempData(importList);

        // 调用存储过程等检查数据准确性7
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ipAddr);
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        pubOrderDao.checkImportData(ckParamMap);
        result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
        if (!result && StringUtil.isEmpty(console)) {
            console = String.valueOf(ckParamMap.get("errorMsg"));
        }
        String flag = pubOrderDao.queryIsImportFlag(uuid);
        // 临时导入情况返回
        rtnMap.put("result", result);
        rtnMap.put("console", console);
        rtnMap.put("flag", flag);
        return rtnMap;
    }

    @Override
    public PageList<PubOrderModelImport> queryPubOrderPartImportTempData(Map<String, String> paramMap, Page page) {
        return pubOrderDao.queryPubOrderPartImportTempData(paramMap, page);
    }

    @Override
    public List<DictVO> queryUnloadPort() {
        return pubOrderDao.queryUnloadPort();
    }

    @Override
    public void batchRemovePurOrder(List<PubOrderModel> purOrderList, String ipAddr, IUser user) {
        List<PubOrderModel> swPurOrderList = new ArrayList<>();
        List<PubOrderModel> jitPurOrderList = new ArrayList<>();
        List<PubOrderModel> jisoPurOrderList = new ArrayList<>();
        // 循环需要删除的订购零件基础数据
        for (int i = 0; i < purOrderList.size(); i++) {
            PubOrderModel tempModel = purOrderList.get(i);
            if ("SW".equals(tempModel.getLogisticsModel())) {
                swPurOrderList.add(tempModel);
            } else if ("JIT".equals(tempModel.getLogisticsModel())) {
                jitPurOrderList.add(tempModel);
            } else if ("JISO".equals(tempModel.getLogisticsModel())) {
                jisoPurOrderList.add(tempModel);
            }
        }

        // 循环取货处理
        if (swPurOrderList != null && swPurOrderList.size() > 0) {
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < swPurOrderList.size(); i++) {
                // 将所有ID存到List
                idList.add(swPurOrderList.get(i).getId());
            }
            // 将list id转为数组
            String[] ids = new String[idList.size()];
            // 记录删除循环取货零件日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_MP_PART");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(idList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

            // 删除循环取货零件订购数据
            pubOrderDao.batchRemovePurOrder(idList.toArray(ids), "SW");
        }

        // 厂外拉动处理
        if (jitPurOrderList != null && jitPurOrderList.size() > 0) {
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < jitPurOrderList.size(); i++) {
                // 将所有ID存到List
                idList.add(jitPurOrderList.get(i).getId());
            }
            // 将list id转为数组
            String[] ids = new String[idList.size()];

            /**
             * 批量删除记录日志
             */
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_JIT_PART");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(idList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

            // 删除厂外拉动零件订购数据
            pubOrderDao.batchRemovePurOrder(idList.toArray(ids), "JIT");

            // 删除在拉动零件基础数据不存在的JIT_ORDER_CONFIG数据
            deleteNotExistsPartJitOrderConfigData(user.getCurFactoryCode(), ipAddr);

        }

        // 厂外同步处理
        if (jisoPurOrderList != null && jisoPurOrderList.size() > 0) {
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < jisoPurOrderList.size(); i++) {
                // 将所有ID存到List
                idList.add(jisoPurOrderList.get(i).getId());
            }
            // 将list id转为数组
            String[] ids = new String[idList.size()];

            /**
             * 批量删除记录日志
             */
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_JISO_PART");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(idList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

            // 删除厂外同步零件订购数据
            pubOrderDao.batchRemovePurOrder(idList.toArray(ids), "JISO");

            // 删除厂外同步不存在于同步零件的同步零件组数据
            deleteNotExistsPartJisoPartGroupData(user.getCurFactoryCode(), ipAddr);

            // 删除厂外同步不存在于同步零件组的同步零件组路线信息数据
            deleteNotExistsPartJisoPartGroupRouteData(user.getCurFactoryCode(), ipAddr);

        }
        // 删除所有不存在于零件表的MM_PUB_PART_UDA数据
        deleteNotExistsPartUdaData(user.getCurFactoryCode(), ipAddr);
        // 删除所有不存在于零件表的MM_PUB_PART_UNLOAD数据
        deleteNotExistsPartUnloadData(user.getCurFactoryCode(), ipAddr);

    }

    @Override
    public void updatePurOrder(PubOrderModelImport pubOrderModelImport, IUser user, String ip) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 设置UUID、属性
        pubOrderModelImport.setUuid(uuid);
        pubOrderModelImport.setFactoryCode(user.getCurFactoryCode());
        pubOrderModelImport.setCreateUser(user.getAccount());
        pubOrderModelImport.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
        pubOrderModelImport.setOpeType("I");
        PubOrderModelImport.checkImportData(pubOrderModelImport);
        // 导入单条数据的方法
        pubOrderDao.insertSimgleImportTempData(pubOrderModelImport);
        // 调用存储过程等检查数据准确性7
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ip);
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        pubOrderDao.checkImportData(ckParamMap);
        // 根据这个查询数据是否正确,如果数据正确修改数据
        String flag = pubOrderDao.queryIsImportFlag(uuid);
        if ("1".equals(flag)) {
            // 表明数据正确,直接执行导入操作
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("uuid", uuid);
            paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
            paramMap.put("factoryCode", user.getCurFactoryCode());
            pubOrderManager.insertPubOrderImportData(paramMap, ip);
            // pubOrderManager.deletePubOrderImportTempDataByUUID(uuid);
            /**
             * 2019-06-25 mod by zmj
             * 删除数据影响拉动同步组单,调整不删除
             */
            /*// 删除在拉动零件基础数据不存在的JIT_ORDER_CONFIG数据
            deleteNotExistsPartJitOrderConfigData(user.getCurFactoryCode(), ip);
            // 删除厂外同步不存在于同步零件的同步零件组数据
            deleteNotExistsPartJisoPartGroupData(user.getCurFactoryCode(), ip);
            // 删除厂外同步不存在于同步零件组的同步零件组路线信息数据
            deleteNotExistsPartJisoPartGroupRouteData(user.getCurFactoryCode(), ip);
            // 删除所有不存在于零件表的MM_PUB_PART_UDA数据*/
            deleteNotExistsPartUdaData(user.getCurFactoryCode(), ip);
            // 删除所有不存在于零件表的MM_PUB_PART_UNLOAD数据
            //deleteNotExistsPartUnloadData(user.getCurFactoryCode(), ip);
        } else {
            String errorInfo = pubOrderDao.queryErrorInfoByUUID(uuid);
            throw new RuntimeException(errorInfo);
        }
    }

    /**
     * 批量删除不存在于零件表的MM_PUB_PART_UNLOAD_数据
     * 
     * @param factoryCode
     */
    public void deleteNotExistsPartUnloadData(String factoryCode, String ipAddr) {
        // 查询所有不存在于零件表的UNLOAD数据
        List<String> unloadIdList = pubOrderDao.queryNotExistsPartUnloadIdList(factoryCode);
        if (unloadIdList != null && unloadIdList.size() > 0) {
            // 将list id转为数组
            String[] ids = new String[unloadIdList.size()];

            // 记录删除循环取货零件日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_PUB_PART_UNLOAD");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(unloadIdList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
            // 删除MM_PUB_PART_UDA的数据
            Map<String, Object> map = new HashMap<>();
            map.put("factoryCode", factoryCode);
            map.put("unloadIdList", unloadIdList);
            pubOrderDao.deleteNotExistsPartUnloadData(map);
        }
    }

    /**
     * 批量删除不存在于零件表的MM_PUB_PART_UDA数据
     * 
     * @param factoryCode
     */
    public void deleteNotExistsPartUdaData(String factoryCode, String ipAddr) {
        // 查询所有不存在于零件表的UDA ID
        List<String> udaIdList = pubOrderDao.queryNotExistsPartUdaIdList(factoryCode);
        if (udaIdList != null && udaIdList.size() > 0) {
            // 将list id转为数组
            String[] ids = new String[udaIdList.size()];

            // 记录删除循环取货零件日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量修改");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
            logVO.setTableName("MM_PUB_PART_UDA");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(udaIdList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
            
            if(udaIdList.size() > 1000){
            	Integer count = (int) Math.ceil((double)udaIdList.size() / 1000);
            	for(int i = 0; i < count; i++){
        			List<String> newUdaList = new ArrayList<>();
            		// 删除MM_PUB_PART_UDA的数据
            		if(i == count - 1){
                		for(int j = i * 1000; j < udaIdList.size(); j++){
                			newUdaList.add(udaIdList.get(j));
                		}
            		}else{
                		for(int j = i * 1000; j < (i + 1) * 1000; j++){
                			newUdaList.add(udaIdList.get(j));
                		}
            		}
            		
            		Map<String, Object> map = new HashMap<>();
                    map.put("factoryCode", factoryCode);
                    map.put("udaIdList", newUdaList);
                    map.put("opeUserName", ContextUtil.getCurrentUser().getAccount());
                    pubOrderDao.deleteNotExistsPartUdaData(map);
            	}
            }
            
        }
    }

    /**
     * 批量删除不存在于拉动零件表的MM_JIT_ORDER_CONFIG数据
     * 
     * @param factoryCode
     */
    public void deleteNotExistsPartJitOrderConfigData(String factoryCode, String ipAddr) {
        // 查询所有不存在于零件表的UNLOAD数据
        List<String> jitOrderConfigList = pubOrderDao.queryNotExistsPartJitOrderConfigIdList(factoryCode);
        if (jitOrderConfigList != null && jitOrderConfigList.size() > 0) {
            // 将list id转为数组
            String[] ids = new String[jitOrderConfigList.size()];

            // 记录删除循环取货零件日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_JIT_ORDER_CONFIG");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jitOrderConfigList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
            // 删除MM_PUB_PART_UDA的数据
            Map<String, Object> map = new HashMap<>();
            map.put("factoryCode", factoryCode);
            map.put("jitOrderConfigList", jitOrderConfigList);
            pubOrderDao.deleteNotExistsPartJitOrderConfigData(map);
        }
    }

    /**
     * 批量删除不存在于同步零件表的MM_JISO_PARTGROUP数据
     * 
     * @param factoryCode
     */
    public void deleteNotExistsPartJisoPartGroupData(String factoryCode, String ipAddr) {
        // 查询所有不存在于零件表的UNLOAD数据
        List<String> jisoPartGroupList = pubOrderDao.queryNotExistsPartJisoPartGroupIdList(factoryCode);
        if (jisoPartGroupList != null && jisoPartGroupList.size() > 0) {
            // 将list id转为数组
            String[] ids = new String[jisoPartGroupList.size()];

            // 记录删除循环取货零件日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_JISO_PARTGROUP");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jisoPartGroupList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
            // 删除MM_PUB_PART_UDA的数据
            Map<String, Object> map = new HashMap<>();
            map.put("factoryCode", factoryCode);
            map.put("jisoPartGroupList", jisoPartGroupList);
            pubOrderDao.deleteNotExistsPartJisoPartGroupData(map);
        }
    }

    /**
     * 批量删除不存在于同步零件组的MM_JISO_PARTGROUP_ROUTE数据
     * 
     * @param factoryCode
     */
    public void deleteNotExistsPartJisoPartGroupRouteData(String factoryCode, String ipAddr) {
        // 查询所有不存在于不存在于同步零件组的MM_JISO_PARTGROUP_ROUTE数据
        List<String> jisoPartGroupRouteList = pubOrderDao.queryNotExistsPartJisoPartGroupRouteIdList(factoryCode);
        if (jisoPartGroupRouteList != null && jisoPartGroupRouteList.size() > 0) {
            // 将list id转为数组
            String[] ids = new String[jisoPartGroupRouteList.size()];

            // 记录删除循环取货零件日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_JISO_PARTGROUP_ROUTE");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(jisoPartGroupRouteList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
            // 删除MM_PUB_PART_UDA的数据
            Map<String, Object> map = new HashMap<>();
            map.put("factoryCode", factoryCode);
            map.put("jisoPartGroupRouteList", jisoPartGroupRouteList);
            pubOrderDao.deleteNotExistsPartJisoPartGroupRouteData(map);
        }
    }
}

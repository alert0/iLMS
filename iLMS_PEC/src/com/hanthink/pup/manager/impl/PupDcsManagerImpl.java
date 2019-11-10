package com.hanthink.pup.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pup.dao.PupDcsDao;
import com.hanthink.pup.manager.PupDcsManager;
import com.hanthink.pup.model.PupDcsModel;
import com.hanthink.pup.model.PupDcsModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * 根据表名实现的类
 */
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * @Desc :
 * @CreateOn: 2019年1月4日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
@Service("PupDcsManager")
public class PupDcsManagerImpl extends AbstractManagerImpl<String, PupDcsModel> implements PupDcsManager {
    @Resource
    PupDcsDao pupDcsDao;

    @Override
    protected Dao<String, PupDcsModel> getDao() {
        return pupDcsDao;
    }

    @Override
    public PageList<PupDcsModel> queryPupDcsForPage(PupDcsModel model, DefaultPage p) {
        return pupDcsDao.queryPupDcsForPage(model, p);
    }

    @Override
    public List<PupDcsModel> queryPupDcsByKey(PupDcsModel model) {
        return pupDcsDao.queryPupDcsByKey(model);
    }

    @Override
    public String genDcs(PupDcsModel pupDcsModel) {
        Map<String, Object> m = new HashMap<>();
        m.put("opeId", pupDcsModel.getOpeId());
        m.put("factoryCode", pupDcsModel.getFactoryCode());
        m.put("workDayStart", pupDcsModel.getWorkDayStart());
        m.put("workDayEnd", pupDcsModel.getWorkDayEnd());
        m.put("outCode", "");
        return pupDcsDao.genDcs(m);
    }

    @Override
    public void deleteDcsByUUID(String uuid) {
        pupDcsDao.deleteDcsByUUID(uuid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> importDcsToTempTable(MultipartFile file, String uuid, String ipAddr, IUser user) {
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
        String[] columns = { "pickType", "area", "unloadPort", "carType", "routeCode", "totalNo", "mergeNo", "supplierNo", "supFactory", "supplierName", "orderNo",
                "purchaseNo", "workDay", "todayNo", "pickDate", "pickTime", "arriveDate", "arriveTime", "assembleDate", "assembleTime", "orderUse",
                "wwlManager", "nwlManager" };
        List<PupDcsModelImport> importList = null;
        try {
            if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
                importList = (List<PupDcsModelImport>) ExcelUtil.importExcelXLS(new PupDcsModelImport(), file.getInputStream(), columns, 1, 0);
            } else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
                importList = (List<PupDcsModelImport>) ExcelUtil.importExcelXLSX(new PupDcsModelImport(), file.getInputStream(), columns, 1, 0);
            } else {
                result = false;
                console = "上传文件不是excel类型！";
                throw new RuntimeException(console);
            }

            // 数据导入初始化，格式初步检查
            for (PupDcsModelImport m : importList) {
                m.setCreateUser(user.getAccount());
                m.setUuid(uuid);
                m.setFactoryCode(user.getCurFactoryCode());
                if (StringUtil.isEmpty(m.getPickTime()) || StringUtil.isEmpty(m.getPickTime())) {
                    m.setPickTime(null);
                }else {
                    m.setPickTime(m.getPickDate()+" "+m.getPickTime());
                }
                
                if (StringUtil.isEmpty(m.getArriveDate()) || StringUtil.isEmpty(m.getArriveTime())) {
                    m.setArriveTime(null);
                }else {
                    m.setArriveTime(m.getArriveDate()+" "+m.getArriveTime());
                }
                
                if (StringUtil.isEmpty(m.getAssembleDate()) || StringUtil.isEmpty(m.getAssembleTime())) {
                    m.setAssembleTime(null);
                }else {
                    m.setAssembleTime(m.getAssembleDate()+" "+m.getAssembleTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            console = e.getMessage();
            throw new RuntimeException(console);
        }

        // 导入数据写入到临时表
        pupDcsDao.insertImportTempData(importList);

        // 调用存储过程等检查数据准确性7
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ipAddr);
        ckParamMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        pupDcsDao.checkImportData(ckParamMap);
        result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
        if (!result && StringUtil.isEmpty(console)) {
            console = String.valueOf(ckParamMap.get("errorMsg"));
        }
        String flag = pupDcsDao.queryIsImportFlag(uuid);
        // 临时导入情况返回
        rtnMap.put("result", result);
        rtnMap.put("console", console);
        rtnMap.put("flag", flag);
        return rtnMap;
    }

    @Override
    public PageList<PupDcsModelImport> queryPupDcsImportTempData(Map<String, String> paramMap, Page page) {
        return pupDcsDao.queryPupDcsImportTempData(paramMap, page);
    }

    @Override
    public String insertPupDcsImportData(Map<String, Object> paramMap, String ipAddr) {
        paramMap.put("outCode", "0");
        return pupDcsDao.insertPupDcsImportData(paramMap);
    }

    @Override
    public List<PupDcsModel> queryDcsForListToPrint(PupDcsModel pupDcsModel) {
        return pupDcsDao.queryDcsForListToPrint(pupDcsModel);
    }

    @Override
    public List<PupDcsModel> queryDcsDetailForList(PupDcsModel pupDcsModel) {
        return pupDcsDao.queryDcsDetailForList(pupDcsModel);
    }

    @Override
    public List<PupDcsModel> querySealForList(PupDcsModel pupDcsModel) {
        return pupDcsDao.querySealForList(pupDcsModel);
    }

    @Override
    public List<PupDcsModel> queryOrderForList(PupDcsModel detailVo) {
        return pupDcsDao.queryOrderForList(detailVo);
    }

    @Override
    public void insertSealTemp(List<PupDcsModel> sealList) {
        pupDcsDao.insertSealTemp(sealList);
        pupDcsDao.updateSealStatus(sealList);
    }

    @Override
    public void updateDcsPrintStatus(PupDcsModel pupDcsModel) {
        pupDcsDao.updateDcsPrintStatus(pupDcsModel);
    }

    @Override
    public void updaetDcsPlateNum(PupDcsModel pupDcsModel, String ipAddr) {
        TableOpeLogVO logVO = new TableOpeLogVO();
        logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
        logVO.setOpeIp(ipAddr);
        logVO.setFromName("界面更新");
        logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
        logVO.setTableName("MM_PUP_DCS_PICK_PLAN");
        TableColumnVO pkColumnVO = new TableColumnVO();
        pkColumnVO.setColumnName("PLAN_SHEET_NO");
        pkColumnVO.setColumnVal(pupDcsModel.getPlanSheetNo());
        this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

        pupDcsDao.updaetDcsPlateNum(pupDcsModel);

        if (pupDcsModel.getPlanSheetNo() != null && !"".equals(pupDcsModel.getPlanSheetNo())) {
            // 更新DCS任务的状态
            pupDcsDao.updateDcsExecuteStatus(pupDcsModel);
        }
    }

    @Override
    public String queryPlateNumByPlanSheetNo(PupDcsModel pupDcsModel) {
        return pupDcsDao.queryPlateNumByPlanSheetNo(pupDcsModel);
    }

    @Override
    public List<PupDcsModel> queryPrintedSeals(PupDcsModel vo) {
        return pupDcsDao.queryPrintedSeals(vo);
    }

    @Override
    public List<PupDcsModel> queryPupDcsDetailByKey(PupDcsModel model) {
        return pupDcsDao.queryPupDcsDetailByKey(model);
    }
}

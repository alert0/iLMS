package com.hanthink.mp.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mp.manager.MpAdjOrderDiffCheckManager;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModel;
import com.hanthink.mp.model.MpAdjOrderDiffCheckModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
/**
 * 根据表名实现的类
 */
import com.hanthink.mp.dao.MpAdjOrderDiffCheckDao;

/**
 * <pre>
 * 描述：计划对比差异查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpAdjOrderDiffCheckManager")
public class MpAdjOrderDiffCheckManagerImpl extends AbstractManagerImpl<String, MpAdjOrderDiffCheckModel> implements MpAdjOrderDiffCheckManager {
    @Resource
    MpAdjOrderDiffCheckDao mpAdjOrderDiffCheckDao;

    @Override
    protected Dao<String, MpAdjOrderDiffCheckModel> getDao() {
        return mpAdjOrderDiffCheckDao;
    }

    /**
     * 分页查询方法
     * 
     * @param uuid
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:17:56
     */
    @Override
    public PageList<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckForPage(MpAdjOrderDiffCheckModel model, DefaultPage p) {
        return (PageList<MpAdjOrderDiffCheckModel>) mpAdjOrderDiffCheckDao.queryMpAdjOrderDiffCheckForPage(model, p);
    }

    /**
     * 导出数据查询方法
     * 
     * @param uuid
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:17:56
     */
    @Override
    public List<MpAdjOrderDiffCheckModel> queryMpAdjOrderDiffCheckByKey(MpAdjOrderDiffCheckModel model) {
        return mpAdjOrderDiffCheckDao.queryMpAdjOrderDiffCheckByKey(model);
    }

    /**
     * 生成USP_MP_ZSB_DIFF
     */
    @Override
    public Integer getMpZsbDiff(String curFactoryCode) {
        return mpAdjOrderDiffCheckDao.getMpZsbDiff(curFactoryCode);
    }

    @Override
    public void clearOrderDiffData(String curFactoryCode) {
        mpAdjOrderDiffCheckDao.clearOrderDiffData(curFactoryCode);
    }

    @Override
    public void updateManuNum(MpAdjOrderDiffCheckModel adjOrderDiffCheckModel) {
        mpAdjOrderDiffCheckDao.updateManuNum(adjOrderDiffCheckModel);
    }

    @Override
    public void deleteMpAdjOrderDiffImportTempDataByUUID(String uuid) {
        mpAdjOrderDiffCheckDao.deleteMpAdjOrderDiffImportTempDataByUUID(uuid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> importMpAdjOrderModel(MultipartFile file, String uuid, String ipAddr, IUser user) throws Exception {
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
        String[] columns = { "supplierNo", "supFactory", "unloadPort", "modelCode", "workcenter", "partNo", "partShortNo", "partName", "adjDiffNum",
                "manuNum", "afAdjNum" };
        List<MpAdjOrderDiffCheckModelImport> importList = null;

        if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
            importList = (List<MpAdjOrderDiffCheckModelImport>) ExcelUtil.importExcelXLS(
                    new MpAdjOrderDiffCheckModelImport(),
                    file.getInputStream(),
                    columns,
                    1,
                    0);
        } else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
            importList = (List<MpAdjOrderDiffCheckModelImport>) ExcelUtil.importExcelXLSX(
                    new MpAdjOrderDiffCheckModelImport(),
                    file.getInputStream(),
                    columns,
                    1,
                    0);
        } else {
            result = false;
            console = "上传文件不是excel类型！";
            throw new RuntimeException(console);
        }

        // 数据导入初始化，格式初步检查
        for (MpAdjOrderDiffCheckModelImport m : importList) {
            m.setId(UniqueIdUtil.getSuid());
            m.setUuid(uuid);
            m.setFactoryCode(user.getCurFactoryCode());
            m.setCreationUser(user.getAccount());
            m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
            m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
            m.setCheckInfo("");
        }

        // 导入数据写入到临时表
        mpAdjOrderDiffCheckDao.insertMpAdjOrderDiffImportTempData(importList);

        // 调用存储过程等检查数据准确性
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ipAddr);
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        mpAdjOrderDiffCheckDao.checkMpAdjOrderDiffImportData(ckParamMap);
        result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
        if (!result && StringUtil.isEmpty(console)) {
            console = String.valueOf(ckParamMap.get("errorMsg"));
        }
        /**
         * 检查是否可以批量导入
         */
        String flag = mpAdjOrderDiffCheckDao.queryMpaAdjOrderIsImportFlag(uuid);
        // 临时导入情况返回
        rtnMap.put("result", result);
        rtnMap.put("console", console);
        rtnMap.put("flag", flag);
        return rtnMap;
    }

    @Override
    public PageList<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempData(Map<String, String> paramMap, Page page) {
        return mpAdjOrderDiffCheckDao.queryMpAdjOrderImportTempData(paramMap, page);
    }

    @Override
    public void insertMpAdjOrderImportData(Map<String, Object> paramMap, String ipAddr) {
        mpAdjOrderDiffCheckDao.updateMpAdjOrderImportDate(paramMap);

        // 更新临时数据导入状态
        mpAdjOrderDiffCheckDao.updateImportDataImpStatus(paramMap);
    }

    @Override
    public List<MpAdjOrderDiffCheckModelImport> queryMpAdjOrderImportTempDataForExport(Map<String, String> paramMap) {
        return mpAdjOrderDiffCheckDao.queryMpAdjOrderImportTempDataForExport(paramMap);
    }

}

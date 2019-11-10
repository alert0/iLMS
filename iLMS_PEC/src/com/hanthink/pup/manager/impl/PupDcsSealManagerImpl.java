package com.hanthink.pup.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pup.dao.PupDcsSealDao;
import com.hanthink.pup.manager.PupDcsSealManager;
import com.hanthink.pup.model.PupDcsSealModel;
import com.hanthink.pup.model.PupDcsSealModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
/**
 * 根据表名实现的类
 */
import com.hotent.sys.util.ContextUtil;

/**
 * @Desc :
 * @CreateOn: 2019年1月4日
 * @author : WY
 * @ChangeList Date Version Editor ChangeReasons
 */
@Service("PupDcsSealManager")
public class PupDcsSealManagerImpl extends AbstractManagerImpl<String, PupDcsSealModel> implements PupDcsSealManager {
    @Resource
    PupDcsSealDao pupDcsSealDao;

    @Override
    protected Dao<String, PupDcsSealModel> getDao() {
        return pupDcsSealDao;
    }

    @Override
    public PageList<PupDcsSealModel> queryPupDcsSealForPage(PupDcsSealModel model, DefaultPage p) {
        return pupDcsSealDao.queryPupDcsSealForPage(model, p);
    }

    @Override
    public List<PupDcsSealModel> queryPupDcsSealByKey(PupDcsSealModel model) {
        return pupDcsSealDao.queryPupDcsSealByKey(model);
    }

    @Override
    public void batchDelDcsSeal(String curFactoryCode) {
        pupDcsSealDao.batchDelDcsSeal(curFactoryCode);
    }

    @Override
    public void deleteDcsSealByUUID(String uuid) {
        pupDcsSealDao.deleteDcsSealByUUID(uuid);
    }

    @Override
    public Map<String, Object> importDcsSealToTempTable(MultipartFile file, String uuid, String ipAddr, IUser user) {
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
        String[] columns = { "sealNo"};
        List<PupDcsSealModelImport> importList = null;
        try {
            if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
                importList = (List<PupDcsSealModelImport>) ExcelUtil.importExcelXLS(new PupDcsSealModelImport(), file.getInputStream(), columns, 1, 0);
            } else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
                importList = (List<PupDcsSealModelImport>) ExcelUtil.importExcelXLSX(new PupDcsSealModelImport(), file.getInputStream(), columns, 1, 0);
            } else {
                result = false;
                console = "上传文件不是excel类型！";
                throw new RuntimeException(console);
            }
            
            // 数据导入初始化，格式初步检查
            for (PupDcsSealModelImport m : importList) {
                m.setUuid(uuid);
                m.setFactoryCode(user.getCurFactoryCode());
                m.setCreateUser(user.getAccount());
                m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
                m.setOpeType("I");
                if("".equals(m.getSealNo()) || m.getSealNo() == null){
                    m.setCheckInfo("封条号不能为空;");
                    m.setCheckResult("0");
                }else{
                    m.setCheckInfo("");
                    m.setCheckResult("1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            console = e.getMessage();
            throw new RuntimeException(console);
        }

        // 导入数据写入到临时表
        pupDcsSealDao.insertImportTempData(importList);

        // 调用存储过程等检查数据准确性7
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ipAddr);
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        pupDcsSealDao.checkImportData(ckParamMap);
        result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
        if (!result && StringUtil.isEmpty(console)) {
            console = String.valueOf(ckParamMap.get("errorMsg"));
        }
        String flag = pupDcsSealDao.queryIsImportFlag(uuid);
        // 临时导入情况返回
        rtnMap.put("result", result);
        rtnMap.put("console", console);
        rtnMap.put("flag", flag);
        return rtnMap;
    }

    @Override
    public PageList<PupDcsSealModelImport> queryPupDcsSealImportTempData(Map<String, String> paramMap, Page page) {
        return pupDcsSealDao.queryPupDcsSealImportTempData(paramMap, page);
    }

    @Override
    public void insertPupDcsSealImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
        paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
        paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
        
        List<PupDcsSealModelImport> list = pupDcsSealDao.queryForInsertList(paramMap);
        if (list.size() < 1) {
            throw new Exception("没有正确数据可导入或已全部导入");
        }
        
        //先删除正式表的未使用的数据
        pupDcsSealDao.deleteNotUseDcsSeal(paramMap);
        
        //插入本次导入的数据
        pupDcsSealDao.insertPupDcsSealFromTemp(paramMap);
        
        //修改导入状态
        pupDcsSealDao.updateImportStatus(paramMap);
    }

}

package com.hanthink.mp.manager.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.demo.model.DemoModelImport;
import com.hanthink.inv.model.InvStockTakModel;
import com.hanthink.mp.dao.ExcepOrderDao;
import com.hanthink.mp.manager.ExcepOrderManager;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hanthink.mp.model.ExcepOrderModel;
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
 * 
 * @Desc    : 
 * @CreateOn: 2018年9月10日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Service
public class ExcepOrderManagerImpl extends AbstractManagerImpl<String, ExcepOrderModel> implements ExcepOrderManager{
    
    @Resource
    private ExcepOrderDao dao;

    @Override
    protected Dao<String, ExcepOrderModel> getDao() {
        return dao;
    }

    @Override
    public PageList<ExcepOrderModel> queryExcepOrderForPage(ExcepOrderModel model, DefaultPage p) {
        return (PageList<ExcepOrderModel>) dao.queryExcepOrderForPage(model, p);
    }

    @Override
    public void deleteImportTempDataByUUID(String uuid) {
        dao.deleteImportTempDataByUUID(uuid);
    }

    @Override
    public Map<String, Object> importDemoModel(MultipartFile file, String uuid, String ipAddr, IUser user) throws Exception{
        Map<String,Object> rtnMap = new HashMap<String, Object>();
        Boolean result = true;
        String console = "";
        
        if(file == null || file.isEmpty()){
            result = false;
            console = "文件为空！";
            throw new RuntimeException(console);
        }
        
        //读取Excel数据
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String[] columns = {"storage","partNo", "supplierNo", "supFactory", "orderNum", "arriveDateStr"};
        List<ExcepOrderModelImport> importList = null;
      
            if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
                importList = (List<ExcepOrderModelImport>) ExcelUtil.importExcelXLS(new ExcepOrderModelImport(), file.getInputStream(), columns, 1, 0);
            }else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
                importList = (List<ExcepOrderModelImport>) ExcelUtil.importExcelXLSX(new ExcepOrderModelImport(), file.getInputStream(), columns, 1, 0);
            }else{
                result = false;
                console = "上传文件不是excel类型！";
                throw new RuntimeException(console);
            }
        
        
        //数据导入初始化，格式初步检查
        for(ExcepOrderModelImport m : importList){
            m.setUuid(uuid);
            m.setFactoryCode(user.getCurFactoryCode());
            m.setCreateUser(user.getAccount());
            m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
            ExcepOrderModelImport.checkImportData(m);
        }
        
        //导入数据写入到临时表
        dao.insertImportTempData(importList);
        
        //调用存储过程等检查数据准确性
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ipAddr);
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        dao.checkImportData(ckParamMap);
        result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
        if(!result && StringUtil.isEmpty(console)){
            console = String.valueOf(ckParamMap.get("errorMsg"));
        }
        String flag = dao.queryIsImportFlag(uuid);
        //临时导入情况返回
        rtnMap.put("result", result);
        rtnMap.put("console", console);
        rtnMap.put("flag", flag);
        return rtnMap;
    }

    @Override
    public PageList<ExcepOrderModelImport> queryImportTempData
    (Map<String, String> paramMap, Page page) {
        return dao.queryImportTempData(paramMap, page);
    }

    @Override
    public void insertImportData(Map<String, String> paramMap) throws Exception {
        paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
        paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
        
     			
			List<ExcepOrderModelImport> list = dao.queryForInsertList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
        
        dao.deleteNotDealData(paramMap);
        dao.insertImportData(paramMap);
        
        //更新临时数据导入状态
        dao.updateImportDataImpStatus(paramMap);
        
    }

    
    /**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<ExcepOrderModel> queryExcepOrderByKey(ExcepOrderModel model) {
		return dao.queryExcepOrderByKey(model);
	}

	/**
	 * 导出临时数据信息
	 */
	@Override
	public List<ExcepOrderModelImport> queryExcepOrderModelImportTempDataForExport(Map<String, String> paramMap) {
		return dao.queryExcepOrderModelImportTempDataForExport(paramMap);
	}
	
}

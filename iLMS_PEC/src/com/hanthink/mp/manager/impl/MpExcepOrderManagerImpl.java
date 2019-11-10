package com.hanthink.mp.manager.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.mp.dao.MpExcepOrderDao;
import com.hanthink.mp.manager.MpExcepOrderManager;
import com.hanthink.mp.model.MpExcepOrderModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
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
public class MpExcepOrderManagerImpl extends AbstractManagerImpl<String, MpExcepOrderModel> implements MpExcepOrderManager{
    
    @Resource
    private MpExcepOrderDao mpExcepOrderDao;

    @Override
    protected Dao<String, MpExcepOrderModel> getDao() {
        return mpExcepOrderDao;
    }

    @Override
    public PageList<MpExcepOrderModel> queryMpExcepOrderForPage(MpExcepOrderModel model, DefaultPage p) {
        return (PageList<MpExcepOrderModel>) mpExcepOrderDao.queryMpExcepOrderForPage(model, p);
    }
    
    /**
     * 例外订单生成
     */
	@Override
	public Integer generateMpExcepOrder(String curFactoryCode) {
		return mpExcepOrderDao.generateMpExcepOrder(curFactoryCode);		
	}

	/**
	 * 例外订单发布
	 */
	@Override
	public Integer releaseMpExcepOrder(String curFactoryCode,String opeId) {
		return mpExcepOrderDao.releaseMpExcepOrder(curFactoryCode,opeId);	
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<MpExcepOrderModel> queryMpExcepOrderByKey(MpExcepOrderModel model) {
		return mpExcepOrderDao.queryMpExcepOrderByKey(model);
	}


	/**
	 * 根据UUID删除临时表中数据
	 */
    @Override
    public void deleteImportTempDataByUUID(String uuid) {
    	mpExcepOrderDao.deleteImportTempDataByUUID(uuid);
    }

    @Override
    public Map<String, Object> importExcepOrderModel(MultipartFile file, String uuid, String ipAddr, IUser user) throws Exception{
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
        String[] columns = {"storage","partNo", "supplierNo", "supFactory", "orderNum",
        		"arriveDateStr",
        		"use","demandDepartment", "demander", "conNumber"
        		};
        List<MpExcepOrderModel> importList = null;
      
            if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
                importList = (List<MpExcepOrderModel>) ExcelUtil.importExcelXLS(new MpExcepOrderModel(), file.getInputStream(), columns, 1, 0);
            }else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
                importList = (List<MpExcepOrderModel>) ExcelUtil.importExcelXLSX(new MpExcepOrderModel(), file.getInputStream(), columns, 1, 0);
            }else{
                result = false;
                console = "上传文件不是excel类型！";
                throw new RuntimeException(console);
            }
        
        
        //数据导入初始化，格式初步检查
        for(MpExcepOrderModel m : importList){
            m.setUuid(uuid);
            m.setFactoryCode(user.getCurFactoryCode());
            m.setCreateUser(user.getAccount());
            m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
            m.setOptUser(user.getAccount());
            m.setOptTime(new Date());
            MpExcepOrderModel.checkImportData(m);
        }
        
        //导入数据写入到临时表
        mpExcepOrderDao.insertImportTempData(importList);
        
        //调用存储过程等检查数据准确性
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ipAddr);
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        mpExcepOrderDao.checkImportData(ckParamMap);
        result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
        if(!result && StringUtil.isEmpty(console)){
            console = String.valueOf(ckParamMap.get("errorMsg"));
        }
        String flag = mpExcepOrderDao.queryIsImportFlag(uuid);
        //临时导入情况返回
        rtnMap.put("result", result);
        rtnMap.put("console", console);
        rtnMap.put("flag", flag);
        return rtnMap;
    }

    @Override
    public PageList<MpExcepOrderModel> queryImportTempData
    (Map<String, String> paramMap, Page page) {
        return mpExcepOrderDao.queryImportTempData(paramMap, page);
    }

    @Override
    public void insertImportData(Map<String, String> paramMap) throws Exception {
        paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
        paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
        
     			
			List<MpExcepOrderModel> list = mpExcepOrderDao.queryForInsertList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
        
		mpExcepOrderDao.deleteNotDealData(paramMap);
        mpExcepOrderDao.insertImportData(paramMap);
        
        //更新临时数据导入状态
        mpExcepOrderDao.updateImportDataImpStatus(paramMap);
        
    }

	/**
	 * 导出临时数据信息
	 */
	@Override
	public List<MpExcepOrderModel> queryMpExcepOrderModelTempDataForExport(Map<String, String> paramMap) {
		return mpExcepOrderDao.queryMpExcepOrderModelTempDataForExport(paramMap);
	}

	/**
	 * 根据车间取仓库代码
	 */
	@Override
	public String selectStorageByWorkCenter(MpExcepOrderModel mpExcepOrderModel) {
		return mpExcepOrderDao.selectStorageByWorkCenter(mpExcepOrderModel);
	}

	/**
	 * 校验是否包含已订购数据
	 */
	@Override
	public Integer queryMpExcepOrderCheck(List<String> listIds) {
		return mpExcepOrderDao.queryMpExcepOrderCheck(listIds);
	}

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月4日 上午11:01:08
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception{
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("例外订单删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_MP_EXCEP_ORDER_DEMAND");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		mpExcepOrderDao.deleteByIds(aryIds);
	}

	/**
	 * 获取到货仓库填充下拉框
	 */
	@Override
	public List<DictVO> getInvWareHouse() {
		return mpExcepOrderDao.getInvWareHouse();
	}
	
}

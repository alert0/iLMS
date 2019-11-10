package com.hanthink.sw.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwVentureForecastMonDao;
import com.hanthink.sw.manager.SwVentureForecastMonManager;
import com.hanthink.sw.model.SwVentureForecastModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("SwVentureForecastMonManager")
public class SwVentureForecastMonManagerImpl extends AbstractManagerImpl<String, SwVentureForecastModel>
implements SwVentureForecastMonManager{

	@Resource
	private SwVentureForecastMonDao dao;

	@Override
	protected Dao<String, SwVentureForecastModel> getDao() {
		
		return dao;
	}

	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		dao.deleteImportTempDataByUUID(uuid);
	}

	@Override
	public Map<String, Object> importModel(MultipartFile file, String uuid, String ipAddr, IUser user) {
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
        String[] columns = {"jvPlace","jvVersion","partNo",
        				"orderQty","planDelivery","advanceTime","phase",
        				"supplierNo","modelCode"};
        List<SwVentureForecastModel> importList = null;
        try {
            if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
                importList = (List<SwVentureForecastModel>) ExcelUtil.importExcelXLS(new SwVentureForecastModel(), 
                		file.getInputStream(), columns, 1, 0);
            }else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
                importList = (List<SwVentureForecastModel>) ExcelUtil.importExcelXLSX(new SwVentureForecastModel(), 
                		file.getInputStream(), columns, 1, 0);
            }else{
                result = false;
                console = "上传文件不是excel类型！";
                throw new RuntimeException(console);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            console = e.getMessage();
            throw new RuntimeException(console);
        }
        
        //数据导入初始化，格式初步检查
        for(SwVentureForecastModel m : importList){
//        	m.setPlanDelivery( m.getPlanDelivery().replace("-", "").substring(0, 8));
            m.setUuid(uuid);
            m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setCheckInfo("");
			m.setCheckResult("1");
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
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
	public PageList<SwVentureForecastModel> queryImportTempData(Map<String, String> paramMap, Page page) {
		
		return dao.queryImportTempData(paramMap,page);
	}

	@Override
	public List<SwVentureForecastModel> queryTempDataForExport(Map<String, String> paramMap) {
		
		return dao.queryTempDataForExport(paramMap);
	}

	@Override
	public void insertImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		try {
			List<SwVentureForecastModel> list = dao
					.queryForInsertList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
			/**
			 * 导入新增的方法
			 */
			dao.insertImportData(paramMap);
			
			/**
			 * 版本号写入版本管理表中MM_SW_FORECAST_VERSION_JV
			 */
			dao.insertVersion(paramMap);

			// 更新临时数据导入状态
			dao.updateVentureForecastImportDataImpStatus(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误，请联系管理员");

		}
	}

	@Override
	public PageList<SwVentureForecastModel> queryVentureForePage(SwVentureForecastModel model, Page page) {
		
		return dao.queryVentureForePage(model,page);
	}

	@Override
	public int deleteVentureVersion(SwVentureForecastModel model) {
		List<SwVentureForecastModel> list = dao.selectVentureByVersion(model); //判断要删除的版本是否已发布
		list.remove(null);
		if (null != list && list.size() > 0) {
			return 1;
		}
		if (list.size() == 0) {
			dao.deleteVentureVersion(model); //删除MM_SW_FORECAST_JV版本数据
			dao.deleteVentureVersionJv(model); //删除MM_SW_FORECAST_VERSION_JV版本数据
			return 0;
		}
		return -1;
		
	}

	@Override
	public List<SwVentureForecastModel> exportForExcelModel(SwVentureForecastModel model) {
		
		return dao.exportForExcelModel(model);
	}

	@Override
	public int insertReleaseVersion(SwVentureForecastModel model) {
		Integer count = dao.selectIsReleaseVersion(model); //判断要发布的版本是否已发布至目标版本
		if (count > 0) {
			return 1;
		}else if (count == 0) {
			//查询要发布版本的对象周（月）、发布周（月）、开始日期、结束日期
//			SwVentureForecastModel swVentureForecastModel = dao.selectDemandForecasetByTagVersion(model); 
//			model.setObjMonth(swVentureForecastModel.getObjMonth());
//			model.setObjWeek(swVentureForecastModel.getObjWeek());
//			model.setStartDate(swVentureForecastModel.getStartDate());
//			model.setEndDate(swVentureForecastModel.getEndDate());
			
			dao.insertReleaseVersion(model);  //发不数据写入到发布数据表
			String[] jvVersionList = model.getJvVersion().split(",");
			for (String jvVersion : jvVersionList) {
				model.setJvVersion(jvVersion);
				dao.insertForecastRecord(model);  //发布数据版本号记录到发布记录表
				dao.updateJvReleaseStatus(model); //发布数据后修改合资车预测表发布状态
				dao.updateErpReleaseStatus(model); //修改erp预测表发布状态
			}
			return 0;
		}
		return -1;
		
	}

	@Override
	public List<SwVentureForecastModel> getJvVersion(Map<String, String> map) {
		
		return dao.getJvVersion(map);
	}

	@Override
	public List<SwVentureForecastModel> getErpVersion(Map<String, String> map) {
		
		return dao.getErpVersion(map);
	}

	@Override
	public void checkDataRsult(SwVentureForecastModel model, Page page) {
		//查询要发布版本的对象周（月）、发布周（月）、开始日期、结束日期
		SwVentureForecastModel swVentureForecastModel = dao.selectDemandForecasetByTagVersion(model); 
		model.setObjMonth(swVentureForecastModel.getObjMonth());
		model.setStartDate(swVentureForecastModel.getStartDate().substring(0,10));
		model.setEndDate(swVentureForecastModel.getEndDate().substring(0,10));
		
		dao.deleteReleaseImp(model); //删除临时表数据
		dao.insertReleaseImpJv(model); //订购方数据写入合并临时表
		dao.insertReleaseImpErp(model); //erp数据写入合并临时表
//		return dao.checkDataRsult(model,page);
	}

	@Override
	public PageList<SwVentureForecastModel> queryTotalQty(SwVentureForecastModel model, Page page) {
		
		return dao.queryTotalQty(model,page);
	}

	@Override
	public PageList<Map<String, Object>> queryVentureForecastMonExportDataByPage(String startMonthStr,
			String endMonthStr, SwVentureForecastModel model, DefaultPage page) {
		
		return dao.queryVentureForecastMonExportDataByPage(startMonthStr,endMonthStr,model,page);
	}


	@Override
	public SwVentureForecastModel getExportModeMsg(Map<String, Object> map) {
		
		return dao.getExportModeMsg(map);
	}
}

package com.hanthink.jit.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jit.dao.JitInvenCompDao;
import com.hanthink.jit.dao.JitReckonDao;
import com.hanthink.jit.manager.JitInvenCompManager;
import com.hanthink.jit.model.JitInvenCompModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: JitInvenCompManagerImpl
 * @Description: 拉动库存对比
 * @author dtp
 * @date 2018年11月3日
 */
@Service("jitInvenCompManager")
public class JitInvenCompManagerImpl extends AbstractManagerImpl<String, JitInvenCompModel> implements JitInvenCompManager{

	@Resource
	private JitInvenCompDao jitInvenCompDao;
	@Resource 
	private JitReckonDao jitReckonDao;
	
	@Override
	protected Dao<String, JitInvenCompModel> getDao() {
		return jitInvenCompDao;
	}

	/**
	 * @Description: 拉动库存对比excel批量导入 
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importJitInvenComp(MultipartFile file, String uuid, String opeIp) {
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
		String[] columns = {"planCode", "partNo", "vin", "arrBatchNo", "arrProcessNo", "currInventory","safetyInventory"};
		List<JitInvenCompModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<JitInvenCompModel>) ExcelUtil.importExcelXLS(new JitInvenCompModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<JitInvenCompModel>) ExcelUtil.importExcelXLSX(new JitInvenCompModel(), file.getInputStream(), columns, 1, 0);
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
		//信息点key-value互换
		PubPlanCodeModel pubPlanmodel = new PubPlanCodeModel();
		pubPlanmodel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<PubPlanCodeModel> pubPlanList = jitReckonDao.loadPlanCodeComboData(pubPlanmodel);
		Map<String, String> dictMap = new HashMap<String, String>();
		for (PubPlanCodeModel pubPlan : pubPlanList) {
			dictMap.put(pubPlan.getPlanCodeDesc(), pubPlan.getPlanCode());
		}
		
		//数据导入初始化，格式初步检查
		for(JitInvenCompModel m : importList){
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			JitInvenCompModel.checkImportData(m, dictMap);
			//m.setId(UniqueIdUtil.getSuid());
			m.setImpUuid(uuid);
		}
		
		//导入数据写入到临时表
		jitInvenCompDao.insertImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", opeIp);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		//推算时校验数据完整性
		//jitInvenCompDao.checkImportData(ckParamMap);
		//result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		result = true;
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		return rtnMap;
	}

	/**
	 * @Description: 查询导入excel数据
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@Override
	public PageList<JitInvenCompModel> queryImportTempPage(JitInvenCompModel model, DefaultPage page) {
		return jitInvenCompDao.queryImportTempPage(model, page);
	}

	/**
	 * @Description: 执行拉动库存对比计算 
	 * @param: @param reckonParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@Override
	public void isReckon(Map<String, String> paramMap) {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		jitInvenCompDao.isReckon(paramMap);
		//更新推算状态
		jitInvenCompDao.updateImportDataImpStatus(paramMap);
	}

	/**
	 * @Description: 拉动库存对比计算excel导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@Override
	public List<JitInvenCompModel> queryImportTempList(JitInvenCompModel model) {
		return jitInvenCompDao.queryImportTempList(model);
	}

	/**
	 * @Description: 拉动计划差异查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	@Override
	public PageList<JitInvenCompModel> queryJitPlanDiff(JitInvenCompModel model, DefaultPage page) {
		return jitInvenCompDao.queryJitPlanDiff(model, page);
	}

	/**
	 * @Description:  根据uuid删除导入临时数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月31日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		jitInvenCompDao.deleteImportTempDataByUUID(uuid);
	}

	/**
	 * @Description: 查询校验结果是否包含不通过  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月31日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return jitInvenCompDao.queryIsExistsCheckResultFalse(uuid);
	}

	/**
	 * @Description: 根据车间获取最小最大批次
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2019年4月18日
	 */
	@Override
	public List<JitInvenCompModel> queryJitCurBatchByWorkcenter(JitInvenCompModel model) {
		return jitInvenCompDao.queryJitCurBatchByWorkcenter(model);
	}

	/**
	 * @Description: 获取计划差异  
	 * @param: @param param
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<Map<String,Object>>   
	 * @author: dtp
	 * @date: 2019年4月18日
	 */
	@Override
	public PageList<Map<String, Object>> queryJitPlanDiffPage(Map<String, Object> param, DefaultPage page) {
		return jitInvenCompDao.queryJitPlanDiffPage(param, page);
	}

	/**
	 * @param: @return 查询拉动计划差异导出excel信息 
	 * @return: List<Map<String,Object>>   
	 * @author: dtp
	 * @date: 2019年4月19日
	 */
	@Override
	public List<Map<String, Object>> queryJitPlanDiffList(Map<String, Object> param) {
		return jitInvenCompDao.queryJitPlanDiffList(param);
	}
	
}

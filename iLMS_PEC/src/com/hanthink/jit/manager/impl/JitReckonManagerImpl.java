package com.hanthink.jit.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jit.dao.JitReckonDao;
import com.hanthink.jit.manager.JitReckonManager;
import com.hanthink.jit.model.JitPartRemainLogModel;
import com.hanthink.jit.model.JitPartRemainModel;
import com.hanthink.jit.model.JitPartRemainProdModel;
import com.hanthink.jit.model.JitVehQueueModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: JitReckonManagerImpl
 * @Description: 拉动推算控制台
 * @author dtp
 * @date 2018年9月21日
 */
@Service("jitReckonManager")
public class JitReckonManagerImpl extends AbstractManagerImpl<String, JitPartRemainModel> implements JitReckonManager{

	@Resource 
	private JitReckonDao jitReckonDao;
	
	@Override
	protected Dao<String, JitPartRemainModel> getDao() {
		return jitReckonDao;
	}

	/**
	 * @Description: 过点车序查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@Override
	public PageList<JitVehQueueModel> queryJitVehQueuePage(JitVehQueueModel model, DefaultPage page) {
		return jitReckonDao.queryJitVehQueuePage(model, page);
	}

	/**
	 * @Description: 过点车序导出excel 
	 * @param: @return    
	 * @return: List<JitVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@Override
	public List<JitVehQueueModel> queryJitVehQueueList(JitVehQueueModel model) {
		return jitReckonDao.queryJitVehQueueList(model);
	}

	/**
	 * @Description: 当前零件余量查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@Override
	public PageList<JitPartRemainModel> queryJitPartRemainPage(JitPartRemainModel model, DefaultPage page) {
		return jitReckonDao.queryJitPartRemainPage(model, page);
	}

	/**
	 * @Description: 当前零件余量导出excel 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@Override
	public List<JitPartRemainModel> queryJitPartRemainList(JitPartRemainModel model) {
		return jitReckonDao.queryJitPartRemainList(model);
	}

	/**
	 * @Description: 截止产品编号零件余量查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@Override
	public PageList<JitPartRemainProdModel> queryJitPartRemainProdPage(JitPartRemainProdModel model, DefaultPage page) {
		return jitReckonDao.queryJitPartRemainProdPage(model, page);
	}

	/**
	 * @Description: 截止产品编号零件余量导出excel  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@Override
	public List<JitPartRemainProdModel> queryJitPartRemainProdList(JitPartRemainProdModel model) {
		return jitReckonDao.queryJitPartRemainProdList(model);
	}

	/**
	 * @Description: 零件余量修改日志查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainLogModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@Override
	public PageList<JitPartRemainLogModel> queryJitPartRemainLogPage(JitPartRemainLogModel model, DefaultPage page) {
		return jitReckonDao.queryJitPartRemainLogPage(model, page);
	}

	/**
	 * @Description: 新增零件余量
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@Override
	public void savePartRemain(JitPartRemainModel model) {
		jitReckonDao.savePartRemain(model);
	}

	/**
	 * @Description: 修改零件余量  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@Override
	public void updatePartRemain(JitPartRemainModel model) {
		JitPartRemainModel oldModel = jitReckonDao.queryOldPartRemain(model);
		jitReckonDao.updatePartRemain(model);
		//零件余量修改保存日志表MM_JIT_PART_REMAIN_LOG
		JitPartRemainLogModel logModel = new JitPartRemainLogModel();
		logModel.setPlanCode(model.getPlanCode());
		logModel.setPartNo(model.getPartNo());
		logModel.setLocation(model.getLocation());
		logModel.setPartRemainOld(oldModel.getPartRemain());
		logModel.setPartRemainNew(model.getPartRemain());
		logModel.setSafetyInventory(model.getSafetyInventory());
		logModel.setEndRequireDate(model.getEndRequireDate());
		logModel.setEndRequireNum(model.getEndRequireNum());
		logModel.setKbProductSeqno(oldModel.getProductSeqno());
		logModel.setOpeUser(ContextUtil.getCurrentUser().getAccount());
		logModel.setOpeIp(model.getLastModifiedIp());
		jitReckonDao.insertJitPartRemainLog(logModel);
		
	}

	/**
	 * @Description: 校验业务主键唯一性，信息点、配送地址、零件号 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@Override
	public List<JitPartRemainModel> queryJitPartRemainUnique(JitPartRemainModel model) {
		return jitReckonDao.queryJitPartRemainUnique(model);
	}

	/**
	 * @Description: 查询当前零件余量 
	 * @param: @param model
	 * @param: @return    
	 * @return: JitPartRemainModel   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@Override
	public JitPartRemainModel queryPartRemain(JitPartRemainModel model) {
		return jitReckonDao.queryPartRemain(model);
	}

	/**
	 * @Description: 零件余量excel批量导入
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年9月26日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importJitPartRemain(MultipartFile file, String uuid, String opeIp) {
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
		String[] columns = {"planCode", "location", "partNo", "partRemain","safetyInventory"};
		List<JitPartRemainModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<JitPartRemainModel>) ExcelUtil.importExcelXLS(new JitPartRemainModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<JitPartRemainModel>) ExcelUtil.importExcelXLSX(new JitPartRemainModel(), file.getInputStream(), columns, 1, 0);
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
		pubPlanmodel.setUserId(ContextUtil.getCurrentUser().getUserId());
		List<PubPlanCodeModel> pubPlanList = jitReckonDao.loadJitReckonPlanCodeComboData(pubPlanmodel);
		Map<String, String> dictMap = new HashMap<String, String>();
		for (PubPlanCodeModel pubPlan : pubPlanList) {
			dictMap.put(pubPlan.getPlanCodeDesc(), pubPlan.getPlanCode());
		}
		
		//数据导入初始化，格式初步检查
		for(JitPartRemainModel m : importList){
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			JitPartRemainModel.checkImportData(m, dictMap);
			//m.setId(UniqueIdUtil.getUId());
			m.setUuid(uuid);
			m.setCreationUser(ContextUtil.getCurrentUser().getAccount());
		}
		//导入数据写入到临时表
		jitReckonDao.insertJitPartRemainTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", opeIp);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		jitReckonDao.checkImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		return rtnMap;
		
	}

	/**
	 * @Description: 确定导入，将临时表数据写入到正式业务表(零件余量)
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		//零件余量修改保存日志表MM_JIT_PART_REMAIN_LOG
		List<JitPartRemainLogModel> logList = jitReckonDao.queryPartRemainLog(paramMap);
		for (JitPartRemainLogModel logModel : logList) {
			logModel.setOpeIp(paramMap.get("ipAddr"));
			logModel.setOpeUser(ContextUtil.getCurrentUser().getAccount());
			jitReckonDao.insertJitPartRemainLog(logModel);
		}
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		jitReckonDao.insertImportData(paramMap);
		//更新临时数据导入状态
		jitReckonDao.updateImportDataImpStatus(paramMap);
	}

	/**
	 * @Description: 查询临时表数据-零件余量 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public PageList<JitPartRemainModel> queryImportTempPage(JitPartRemainModel model, DefaultPage page) {
		return jitReckonDao.queryImportTempPage(model, page);
	}

	/**
	 * @Description: 导出临时表数据-零件余量 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public List<JitPartRemainModel> queryImportTempList(JitPartRemainModel model) {
		return jitReckonDao.queryImportTempList(model);
	}

	/**
	 * @Description: 拉动推算控制台推算服务状态查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public PageList<PubPlanCodeModel> queryJitReckonStatePage(PubPlanCodeModel model, DefaultPage page) {
		return jitReckonDao.queryJitReckonStatePage(model, page);
	}

	/**
	 * @Description: 加载信息点下拉框
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public List<PubPlanCodeModel> loadPlanCodeComboData(PubPlanCodeModel model) {
		return jitReckonDao.loadPlanCodeComboData(model);
	}

	/**
	 * @Description: 更新推算状态
	 * @param: @param pubPlanCodeModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public void updateReckonExecState(PubPlanCodeModel pubPlanCodeModel) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(pubPlanCodeModel.getLastModifiedIp()); 
		logVO.setFromName("更新拉动推算状态");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_PLAN_CODE");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PLAN_CODE");
		pkColumnVO.setColumnVal(pubPlanCodeModel.getPlanCode());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		jitReckonDao.updateReckonExecState(pubPlanCodeModel);
	}

	/**
	 * @Description: 根据uuid删除导入临时数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月4日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		jitReckonDao.deleteImportTempDataByUUID(uuid);
	}

	/**
	 * @Description: 根据planCode查询推算状态  
	 * @param: @param model
	 * @param: @return    
	 * @return: PubPlanCodeModel   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@Override
	public PubPlanCodeModel queryReckonState(PubPlanCodeModel model) {
		return jitReckonDao.queryReckonState(model);
	}

	/**
	 * @Description: 根据planCode查询推算状态  
	 * @param: @param model
	 * @param: @return    
	 * @return: PubPlanCodeModel   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@Override
	public PubPlanCodeModel queryReckonExecState(PubPlanCodeModel pubPlanCodeModel) {
		return jitReckonDao.queryReckonExecState(pubPlanCodeModel);
	}

	/**
	 * @Description: 查询零件与配送地址关系是否存在
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@Override
	public List<JitPartRemainModel> queryPartAndLocationIsExist(JitPartRemainModel model) {
		return jitReckonDao.queryPartAndLocationIsExist(model);
	}

	/**
	 * @Description: 零件余量修改日志导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年12月31日
	 */
	@Override
	public List<JitPartRemainProdModel> queryJitPartRemainLogList(JitPartRemainLogModel model) {
		return jitReckonDao.queryJitPartRemainLogList(model);
	}

	/**
	 * @Description:  拉动推算控制台信息点下拉框(添加数据权限) 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2019年1月17日
	 */
	@Override
	public List<PubPlanCodeModel> loadJitReckonPlanCodeComboData(PubPlanCodeModel model) {
		return jitReckonDao.loadJitReckonPlanCodeComboData(model);
	}

	/**
	 * @Description: 查询导入校验结果  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月23日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return jitReckonDao.queryIsExistsCheckResultFalse(uuid);
	}

}

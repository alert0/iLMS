package com.hanthink.pup.manager.impl;

import java.util.ArrayList;
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
import com.hanthink.inv.dao.InvEmptyOutDao;
import com.hanthink.pup.dao.PupMakePlanDao;
import com.hanthink.pup.dao.PupPlanDao;
import com.hanthink.pup.manager.PupPlanManager;
import com.hanthink.pup.model.PupPlanModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.identity.IdentityService;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:取货计划查询业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("planQuery")
public class PupPlanManagerImpl extends AbstractManagerImpl<String, PupPlanModel>
				implements PupPlanManager{
	@Resource
	private PupPlanDao planDao;
	
	@Resource
	private InvEmptyOutDao emptyOutDao;
	
	@Resource
	private PupMakePlanDao makePlanDao;
	
	@Resource
	private IdentityService service;
	
	@Override
	protected Dao<String, PupPlanModel> getDao() {
		return planDao;
	}
	/**
	 * 取货计划查询业务实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public PageList<PupPlanModel> queryPlanForPage(PupPlanModel model, Page page)throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<PupPlanModel> list = planDao.queryPlanForPage(model,page);

		return new PageList<PupPlanModel>(list);
	}
	/**
	 * 数据字项下载状态加载业务层接口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<DictVO> getDownloadStatus() throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return planDao.getDownloadStatus(paramMap);
	}
	/**
	 * 单条/批量删除数据业务接口实现
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void deletePlans(String[] orderNos,String[] purchaseNos,String ipAddr) throws Exception {
		if(orderNos.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		//记录修改数据日志
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("删除数据");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_PICKUP_PLAN");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ORDER_NO");
		
		List<PupPlanModel> list = new ArrayList<PupPlanModel>();
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		for (int i = 0; i < orderNos.length; i++) {
			pkColumnVO.setColumnVal(orderNos[i]);
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			PupPlanModel model = new PupPlanModel();
			model.setFactoryCode(factoryCode);
			model.setOrderNo(orderNos[i]);
			model.setPurchaseNo(purchaseNos[i]);
			list.add(model);
		}
		planDao.deletePlans(list);
	}
	/**
	 * 根据导入UUID删除临时表已存在数据业务实现方法
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void deletePlanByUUID(String uuid) throws Exception {
		planDao.deletePlanByUUID(uuid);
	}
	/**
	 * 将Excel数据写入临时表业务实现方法
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importPlanToTempTable(MultipartFile file,
			String uuid, String ipAddr) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"area","carType","routeCode","totalBatchs","mergeBatchs","supFactory","supplierNo","orderNo",
							"purchaseNo","workDate","todayCarBatch","planPickupDate","planPickupTime","planArrDate","planArrTime","planAssembleDate",
							"planAssembleTime","orderUse","outerLogisManager","interLogisManager","confirmDays","pickupType", "unloadPort"};
		
		List<PupPlanModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<PupPlanModel>) ExcelUtil.importExcelXLS(new PupPlanModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<PupPlanModel>) ExcelUtil.importExcelXLSX(new PupPlanModel(), file.getInputStream(), columns, 1, 0);
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

		IUser user = ContextUtil.getCurrentUser();
		for (PupPlanModel pupPlanModel : importList) {
			pupPlanModel.setId(UniqueIdUtil.getSuid());
			pupPlanModel.setImpUUID(uuid);
			pupPlanModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			pupPlanModel.setFactoryCode(user.getCurFactoryCode());
			pupPlanModel.setCreationUser(user.getAccount());
			pupPlanModel.checkImportMessage(pupPlanModel);
			if (StringUtil.isEmpty(pupPlanModel.getPlanPickupDate()) || StringUtil.isEmpty(pupPlanModel.getPlanPickupTime())) {
				pupPlanModel.setPlanPickupTime(null);
			}else {
				pupPlanModel.setPlanPickupTime(pupPlanModel.getPlanPickupDate()+" "+pupPlanModel.getPlanPickupTime());
			}
			
			if (StringUtil.isEmpty(pupPlanModel.getPlanArrDate()) || StringUtil.isEmpty(pupPlanModel.getPlanArrTime())) {
				pupPlanModel.setPlanArrTime(null);
			}else {
				pupPlanModel.setPlanArrTime(pupPlanModel.getPlanArrDate()+" "+pupPlanModel.getPlanArrTime());
			}
			
			if (StringUtil.isEmpty(pupPlanModel.getPlanAssembleDate()) || StringUtil.isEmpty(pupPlanModel.getPlanAssembleTime())) {
				pupPlanModel.setPlanAssembleTime(null);
			}else {
				pupPlanModel.setPlanAssembleTime(pupPlanModel.getPlanAssembleDate()+" "+pupPlanModel.getPlanAssembleTime());
			}
		}
		try {
			planDao.insertToTempTable(importList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = "请按模版导入数据";
			throw new RuntimeException(console);
		}
		
		//调用存储过程进行验证
		Map<String, String> checkMap = new HashMap<String,String>();
		checkMap.put("uuid", uuid);
		checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		checkMap.put("opeIp", ipAddr);
		checkMap.put("errorFlag", "");
		checkMap.put("errorMsg", "");
		planDao.checkImportMessage(checkMap);
		result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(checkMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = planDao.queryImportFlag(uuid);
		//临时导入情况返回
		resultMap.put("result", result);
		resultMap.put("console", console);
		resultMap.put("flag", flag);
		return resultMap;
	}
	/**
	 * 查询导入数据详情业务实现方法
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public PageList<PupPlanModel> queryImportForPage(Map<String, String> paramMap, Page page) throws Exception {
		List<PupPlanModel> list = new ArrayList<PupPlanModel>();
		if(!paramMap.isEmpty()) {
			paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			list = planDao.queryImportPlanForPage(paramMap,page);
		}
		return new PageList<PupPlanModel>(list);
	}
	/**
	 * 确认导入功能接口实现方法
	 * @param paramMap
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void insertTempToFormal(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		Integer countImport = planDao.getCountForImport(paramMap);
		Integer countAllImport = planDao.getCountAllImport(paramMap);
		if(countImport.intValue() < 1) {
			throw new Exception("订单已全部发布");
		}
		if (countImport.intValue() != countAllImport.intValue()) {
			throw new Exception("请检查导入数据正确性");
		}
		//调用存储将数据写入正式表
		try {
			paramMap.put("userName", ContextUtil.getCurrentUser().getAccount());
			paramMap.put("errorFlag", "");
			paramMap.put("errorMsg", "");
			paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());

			planDao.insertAndUpdatePlan(paramMap);	
			//记录发布版本号
			String verSionNo = service.genNextNo("versionNumber");
			paramMap.put("versionNo", verSionNo);
			makePlanDao.recordVersionMessage(paramMap);
			//生成空容器
			Map<String, String> emptyMap = new HashMap<String, String>();
			emptyMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			emptyMap.put("resultCode", "0");
			Integer resultCode = emptyOutDao.makeEmptyContainer(emptyMap);
			if (resultCode != 0) {
				throw new Exception("生成空容器失败");
			}
			//修改订单的发布状态为已发布
			makePlanDao.updateCurrPublished();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}	
	}
	/**
	 * 临时数据Excel导出业务实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<PupPlanModel> queryImportForExport(Map<String, String> paramMap) throws Exception {
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		return planDao.queryImportForExport(paramMap);
	}
	@Override
	public List<PupPlanModel> queryPlanForExport(PupPlanModel model) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return planDao.queryPlanForExport(model);
	}
	/**
	 * 已发车查询
	 */
	@Override
	public PageList<PupPlanModel> queryForDepEnq(PupPlanModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<PupPlanModel> list = planDao.queryForDepEnq(model,page);
		return new PageList<PupPlanModel>(list);
	}
	@Override
	public List<PupPlanModel> queryForExportDepEnq(PupPlanModel model) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return planDao.queryForExportDepEnq(model);
	}

	
}

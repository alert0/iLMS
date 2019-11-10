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
import com.hanthink.pup.dao.PupRouteMessageDao;
import com.hanthink.pup.manager.PupRouteMessageManager;
import com.hanthink.pup.model.PupRouteMessageModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:路线信息维护业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月21日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("routeMessage")
public class PupRouteMessageManagerImpl extends AbstractManagerImpl<String, PupRouteMessageModel>
						implements PupRouteMessageManager{
	@Resource
	PupRouteMessageDao routeDao;
	
	@Override
	protected Dao<String, PupRouteMessageModel> getDao() {
		return routeDao;
	}

	@Override
	public PageList<PupRouteMessageModel> queryRouteMessageForPage(PupRouteMessageModel model, Page page)
			throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<PupRouteMessageModel> list;
		if(!PupUtil.isAllFieldNull(model)) {
			if (StringUtil.isNotEmpty(model.getCarType())) {
				model.setCarType(model.getCarType().replace("，", ","));
			}
			list = routeDao.queryRouteMessageForPage(model,page);
		}else {
			list = new ArrayList<>();
		}
		return new PageList<PupRouteMessageModel>(list);
	}

	@Override
	public List<DictVO> getBatches() throws Exception {
		return routeDao.getBatches();
	}

	@Override
	public List<PupRouteMessageModel> queryRouteMessageForExport(PupRouteMessageModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return routeDao.queryRouteMessageForExport(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}

	@Override
	public void removeRouteMessagesByIds(String[] ids,String ipAddr) throws Exception {
		if(ids.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		try {
			//设置日志记录条件
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("数据删除");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_PUP_ROUTE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			
			routeDao.removeRouteMessagesByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		
	}

	@Override
	public void deleteTempRouteMessageByUUID(String uuid) throws Exception {
		routeDao.deleteTempRouteMessageByUUID(uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importRouteMessageToTempTable(MultipartFile file, String uuid, String ipAddr)
			throws Exception {
		PupRouteMessageModel importModel = new PupRouteMessageModel();
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
		String[] columns = {"area","routeDist","unloadPlace","supplierNo","supFactory","retEmptyPlatform",
							"unloadPort","carType","wareCode","pickupType","routeCode","routeName","pickupCar","supCalNum","locDepth",
							"advanceArrNum","firstArriveTime","departTimePoint","speArriveTime","transTime","recShiftA","recShiftB",
							"wwlManager","nwlManager","mergeNum","pickCycle","supOutTime","batch","startSortId"};
		List<PupRouteMessageModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<PupRouteMessageModel>) ExcelUtil.importExcelXLS(importModel,file.getInputStream(),columns,1,0);
			}else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<PupRouteMessageModel>) ExcelUtil.importExcelXLSX(importModel,file.getInputStream(),columns,1,0);
			}else {
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
		for (PupRouteMessageModel routeModel : importList) {
			if (StringUtil.isNotEmpty(routeModel.getBatch())) {
				if("否".equals(routeModel.getBatch())) {
					routeModel.setBatch("0");
				}
				if("是".equals(routeModel.getBatch())) {
					routeModel.setBatch("1");
				}
			}else {
				routeModel.setBatch(null);
			}
			
			routeModel.setId(UniqueIdUtil.getSuid());
			routeModel.setImpUUID(uuid);
			routeModel.setCreationUser(user.getAccount());
			routeModel.setCreationTime(PupUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
			routeModel.setLastModifiedUser(user.getAccount());
			routeModel.setLastModifiedTime(PupUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
			routeModel.setFactoryCode(user.getCurFactoryCode());
			routeModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			PupRouteMessageModel.checkImportRouteMessage(routeModel);
		}
		try {
			routeDao.insertRouteMessageToTempTable(importList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = "请按模版导入数据";
			throw new RuntimeException(console);
		}
		try {
			//调用存储过程进行验证
			Map<String, String> checkMap = new HashMap<String,String>();
			checkMap.put("uuid", uuid);
			checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
			checkMap.put("opeIp", ipAddr);
			checkMap.put("errorFlag", "");
			checkMap.put("errorMsg", "");
			routeDao.checkImportRouteMessage(checkMap);
			result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
			if(!result && StringUtil.isEmpty(console)){
				console = String.valueOf(checkMap.get("errorMsg"));
			}
			/**
			 * 检查是否可以批量导入
			 */
			String flag = routeDao.queryImportFlag(uuid);
			//临时导入情况返回
			resultMap.put("result", result);
			resultMap.put("console", console);
			resultMap.put("flag", flag);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		
	}

	@Override
	public void insertImportDataToFormalTable(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		
		Integer countImport = routeDao.getCountForImport(paramMap);
		if (countImport > 0) {
			try {
				//设置日志记录条件
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("导入数据删除");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
				logVO.setTableName("MM_PUP_ROUTE");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("FACTORY_CODE");
				pkColumnVO.setColumnVal(ContextUtil.getCurrentUser().getCurFactoryCode());
				this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
				
				routeDao.deleteRouteMessageFromRegula(paramMap);
				/**
				 * 将数据写入正式表
				 */
				routeDao.insertTempDataToRegula(paramMap);
				
				/**
				 * 更新临时数据的导入状态
				 */
				routeDao.updateRouteMessageImpStatus(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理员");
			}
		}else {
			throw new Exception("没有可导入的正确数据");
		}
	}

	@Override
	public PageList<PupRouteMessageModel> queryImportModelForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		try {
			List<PupRouteMessageModel> list = new ArrayList<>();
			if(!paramMap.isEmpty()) {
				paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
				list = routeDao.queryImportModelForPage(paramMap,page);
			}
			return new PageList<>(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	@Override
	public List<PupRouteMessageModel> queryImportModelForExport(Map<String, String> paramMap) throws Exception {
		try {
			paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			return routeDao.queryImportModelForExport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		
	}
	@Override
	public PupRouteMessageModel queryRouteMessageById(String id) throws Exception {
		if(StringUtil.isEmpty(id)) {
			throw new Exception("系统错误,请联系管理员");
		}
		return routeDao.queryRouteMessageById(id);
	}
	
	@Override
	public void updateRouteMessageById(String id,PupRouteMessageModel model) throws Exception {
		if(StringUtil.isEmpty(id) && PupUtil.isAllFieldNull(model)) {
			throw new Exception("系统错误，请联系管理员");
		}
		if (StringUtil.isNotEmpty(model.getFirstArriveTime())) {
			model.setFirstArriveTime(PupUtil.getCurrentDate()+" "+model.getFirstArriveTime());
		}
		if (StringUtil.isNotEmpty(model.getSpeArriveTime())) {
			model.setSpeArriveTime(PupUtil.getCurrentDate()+" "+model.getSpeArriveTime());
		}
		if(StringUtil.isNotEmpty(model.getSupOutTime())) {
			model.setSupOutTime(PupUtil.getCurrentDate()+" "+model.getSupOutTime());
		}
		try {
			//设置日志记录条件
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(model.getOpeIpAddr()); 
			logVO.setFromName("数据删除");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_PUP_ROUTE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			//记录日志
			pkColumnVO.setColumnVal(id);
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			IUser user = ContextUtil.getCurrentUser();
			model.setLastModifiedUser(user.getAccount());
			routeDao.updateRouteMessageById(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
}

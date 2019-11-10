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
import com.hanthink.pup.model.PupRouteMessagePageModel;
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
	public PageList<PupRouteMessageModel> queryRouteMessageForPage(PupRouteMessagePageModel model, Page page)
			throws Exception {
		List<PupRouteMessageModel> list;
		if(!PupUtil.isAllFieldNull(model)) {
			if (StringUtil.isNotEmpty(model.getCarType())) {
				model.setCarType(model.getCarType().replace(",", " "));
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
	public List<PupRouteMessageModel> queryRouteMessageForExport(PupRouteMessagePageModel model) throws Exception {
		return routeDao.queryRouteMessageForExport(model);
	}

	@Override
	public void removeRouteMessagesByIds(String[] ids) throws Exception {
		if(ids.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		routeDao.removeRouteMessagesByIds(ids);
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
		String[] columns = {"area","routeDist","unloadPlace","supplierNo","supFactory","supplierName","retEmptyPlatform",
							"carType","wareCode","pickupType","routeCode","routeName","pickupCar","supCalNum","locDepth",
							"advanceArrNum","firstArriveTime","departTimePoint","speArriveTime","recShiftA","recShiftB",
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
			if(routeModel.getBatch().equals("否")) {
				routeModel.setBatch("0");
			}
			if(routeModel.getBatch().equals("是")) {
				routeModel.setBatch("1");
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
		routeDao.insertRouteMessageToTempTable(importList);
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
	}

	@Override
	public void insertImportDataToFormalTable(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		/**
		 * 拿出Id查询哪些数据要修改
		 */
		List<String> ids = routeDao.queryUpdateDataFromTemp(paramMap);
		if(ids.size()>0) {
			/**
			 * 声明一个String数组，用于存放List
			 */
			String[] idp = new String[ids.size()];
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("导入更新");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_PUP_ROUTE_UPDATE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids.toArray(idp));
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			/**
			 * 导入修改的数据
			 */
			routeDao.updateRouteMessageImportData(paramMap);
		}
		/**
		 * 将数据写入正式表
		 */
		routeDao.insertTempDataToRegula(paramMap);
		
		/**
		 * 更新临时数据的导入状态
		 */
		routeDao.updateRouteMessageImpStatus(paramMap);
	}

	@Override
	public PageList<PupRouteMessageModel> queryImportModelForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		List<PupRouteMessageModel> list = new ArrayList<>();
		if(!paramMap.isEmpty()) {
			list = routeDao.queryImportModelForPage(paramMap,page);
		}
		return new PageList<>(list);
	}

	@Override
	public PupRouteMessageModel queryRouteMessageById(String id) throws Exception {
		if(StringUtil.isEmpty(id)) {
			throw new Exception("你大爷，出错了");
		}
		return routeDao.queryRouteMessageById(id);
	}
	
	@Override
	public void updateRouteMessageById(String id,PupRouteMessageModel model) throws Exception {
		if(StringUtil.isEmpty(id) && PupUtil.isAllFieldNull(model)) {
			throw new Exception("系统错误，请联系工作人员");
		}
		model.setId(id);
		String currentDate = PupUtil.getCurrentDate();
		IUser user = ContextUtil.getCurrentUser();
		model.setLastModifiedUser(user.getAccount());
		if(StringUtil.isNotEmpty(model.getSupOutTime())) {
			model.setSupOutTime(currentDate + model.getSupOutTime());
		}
		if(StringUtil.isNotEmpty(model.getDepartTimePoint())) {
			model.setDepartTimePoint(currentDate + model.getDepartTimePoint());
		}
		if(StringUtil.isNotEmpty(model.getSpeArriveTime())) {
			model.setSpeArriveTime(currentDate + model.getSpeArriveTime());
		}
		routeDao.updateRouteMessageById(model);
	}
}

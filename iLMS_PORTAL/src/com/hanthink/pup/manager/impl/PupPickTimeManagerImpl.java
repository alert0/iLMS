package com.hanthink.pup.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pup.dao.PupPickTimeDao;
import com.hanthink.pup.manager.PupPickTimeManager;
import com.hanthink.pup.model.PupPickTimeModel;
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
* 描述：固定取货时间维护服务层实现类
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-17
* 版权：汉思信息技术有限公司
* </pre>
*/
@Service("pickTimeService")
public class PupPickTimeManagerImpl extends AbstractManagerImpl<String, PupPickTimeModel>
				implements PupPickTimeManager{
	
	@Resource
	PupPickTimeDao pickTimeDao;
	
	@Override
	protected Dao<String, PupPickTimeModel> getDao() {
		return this.pickTimeDao;
	}
	
	@Override
	public PageList<PupPickTimeModel> queryPickupTimeForPage(PupPickTimeModel model,Page page) throws Exception {
		return (PageList<PupPickTimeModel>) pickTimeDao.queryPickupTimeForPage(model,page);
	}

	@Override
	public PupPickTimeModel getPickTimeByRouteCode(String routeCode) throws Exception {
		PupPickTimeModel pickTimeModel = null;
		if(StringUtil.isEmpty(routeCode)) {
			return null;
		}else {
			pickTimeModel = pickTimeDao.getPickTimeByRouteCode(routeCode);
		}
		return pickTimeModel;
	}

	@Override
	public void savePickTime(String flag,PupPickTimeModel model) throws Exception {
		if(model != null) {
			//获取页面参数并进行验证
			String routeCode = model.getRouteCode();
			String todayNo = model.getTodayNo();
			String pickTime = model.getPickTime();
			String arriveTime = model.getArriveTime();
			
			String currentDate = PupUtil.getCurrentDate();
			if(StringUtil.isEmpty(routeCode)) {
				throw new Exception("线路代码不能为空");
			}else {
				model.setRouteCode(routeCode);
			}
			if(StringUtil.isEmpty(todayNo)) {
				throw new Exception("当日班次不能为空");
			}else {
				model.setTodayNo(todayNo);
			}
			//判断取货、到货时间是否合理
			boolean compareResult = PupUtil.compareTwoTimes(pickTime, arriveTime);
			if(compareResult == false) {
				throw new Exception("取货/到货时间录入有误，请重新录入");
			}else {
				model.setPickTime(currentDate+" "+pickTime);
				model.setArriveTime(currentDate+" "+arriveTime);
			}
			
			//获取用户数据
			IUser user = ContextUtil.getCurrentUser();
			String creationUser = user.getAccount();
			String factoryCode = user.getCurFactoryCode();
			String currentTime = PupUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss");
			
			model.setFactoryCode(factoryCode);
			model.setCreationUser(creationUser);
			model.setCreationTime(currentTime);
			model.setLastModifiedUser(creationUser);
			model.setLastModifiedTime(currentTime);
			
			if (StringUtil.isEmpty(flag)) {
				PupPickTimeModel pickModel = pickTimeDao.getPickTimeByRouteCode(routeCode);
				if(pickModel != null) {
					throw new Exception("集货线路:"+routeCode+"已存在");
				}
				pickModel = pickTimeDao.getPickTimeByTodayNo(todayNo);
				if(pickModel != null) {
					throw new Exception("当日班次:"+todayNo+"已存在");
				}
				pickTimeDao.createPickTime(model);
			}else {
				pickTimeDao.updatePickTime(model);
			}
		}else {
			throw new Exception("系统繁忙，请稍后再试");
		}
	}

	@Override
	public void removeByRouteCodes(String[] routeCodes) throws Exception {
		if (routeCodes.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		pickTimeDao.removeByRouteCodes(routeCodes);
	}

	@Override
	public List<PupPickTimeModel> queryPickupTimeForExport(PupPickTimeModel model) throws Exception {
		return pickTimeDao.queryPickTimeForExport(model);
	}
	
	@Override
	public PageList<PupPickTimeModel> queryPickTimeForImport(Map<String, String> param,Page page)throws Exception {
		return (PageList<PupPickTimeModel>) pickTimeDao.queryPickTimeForImport(param,page);
	}

	@Override
	public void deletePickTimeTempDataByUUID(String uuid) throws Exception {
		pickTimeDao.deletePickTimeTempDataByUUID(uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importDataToTempTable(MultipartFile file, String uuid, 
									String ipAddr,IUser user) throws Exception {
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
		String[] columns = {"routeCode","todayNo","pickTime","arriveTime"};
		List<PupPickTimeModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<PupPickTimeModel>) ExcelUtil.importExcelXLS(new PupPickTimeModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<PupPickTimeModel>) ExcelUtil.importExcelXLSX(new PupPickTimeModel(), file.getInputStream(), columns, 1, 0);
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
		for(PupPickTimeModel pickTimeModel : importList){
			pickTimeModel.setId(UniqueIdUtil.getSuid());
			pickTimeModel.setImpUUID(uuid);
			pickTimeModel.setFactoryCode(user.getCurFactoryCode());
			pickTimeModel.setCreationUser(user.getAccount());
            pickTimeModel.setCreationTime(PupUtil.date2String(new Date(), "yyyy-MM-dd hh:mm:ss"));
			pickTimeModel.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			pickTimeModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			PupPickTimeModel.checkImportData(pickTimeModel);
		}
		//导入数据写入到临时表
		pickTimeDao.inserDataToTempDataTable(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> checkMap = new HashMap<String, String>();
		checkMap.put("uuid", uuid);
		checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		checkMap.put("opeIp", ipAddr);
		checkMap.put("errorFlag", "");
		checkMap.put("errorMsg", "");
		pickTimeDao.checkImportData(checkMap);
		result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(checkMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = pickTimeDao.queryImportFlag(uuid);
		//临时导入情况返回
		resultMap.put("result", result);
		resultMap.put("console", console);
		resultMap.put("flag", flag);
		return resultMap;
	}

	@Override
	public List<PupPickTimeModel> queryPickupTimeTempDataForExport(Map<String, String> param) throws Exception {
		return pickTimeDao.queryPickupTimeTempDataForExport(param);
	}

	@Override
	public void insertImportDataToFormalTable(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		/**
		 * 拿出Id查询哪些数据要修改
		 */
		List<String> ids = pickTimeDao.queryUpdateDataFromTemp(paramMap);
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
			logVO.setTableName("MM_PUP_FIX_TIME_UPDATE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids.toArray(idp));
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			/**
			 * 导入修改的数据
			 */
			pickTimeDao.updatePickTimeImportData(paramMap);
		}
		/**
		 * 将数据写入正式表
		 */
		pickTimeDao.insertTempDataToRegula(paramMap);
		
		/**
		 * 更新临时数据的导入状态
		 */
		pickTimeDao.updatePickTimeImportDataImpStatus(paramMap);
	}
}

package com.hanthink.pup.manager.impl;

import java.util.ArrayList;
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
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return (PageList<PupPickTimeModel>) pickTimeDao.queryPickupTimeForPage(model,page);
	}

	@Override
	public void savePickTime(PupPickTimeModel model) throws Exception {
		if(model != null) {
			//获取页面参数并进行验证
			String routeCode = model.getRouteCode();
			String todayNo = model.getTodayNo();
			String pickTime = model.getPickTime();
			String arriveTime = model.getArriveTime();

			if(StringUtil.isEmpty(routeCode)) {
				throw new Exception("线路代码不能为空");
			}
			if(StringUtil.isEmpty(todayNo)) {
				throw new Exception("当日车次不能为空");
			}else {
				try {
					if (Integer.parseInt(todayNo) < 1) {
						throw new Exception("当日车次只能是正整数");
					}
				} catch (Exception e) {
					throw new Exception("当日车次只能是正整数");
				}
			}
			if (StringUtil.isEmpty(pickTime)) {
				throw new Exception("取货时间为空");
			}
			if (StringUtil.isEmpty(arriveTime)) {
				throw new Exception("到货时间为空");
			}
			model.setPickTime(PupUtil.getCurrentDate()+" "+pickTime);
			model.setArriveTime(PupUtil.getCurrentDate()+" "+arriveTime);
			//获取用户数据
			IUser user = ContextUtil.getCurrentUser();
			String creationUser = user.getAccount();
			String factoryCode = user.getCurFactoryCode();
			
			model.setFactoryCode(factoryCode);
			model.setCreationUser(creationUser);
			model.setLastModifiedUser(creationUser);
			
			PupPickTimeModel pickModel = pickTimeDao.getPickTime(model);
			if(pickModel != null) {
				throw new Exception("集货线路:"+routeCode+";今日车次:"+todayNo+"已存在");
			}
			
			pickTimeDao.createPickTime(model);
		}else {
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	@Override
	public void updatePickTime(PupPickTimeModel model) throws Exception {
		if (StringUtil.isEmpty(model.getId())) {
			throw new Exception("系统错误,请联系管理员");
		}
		try {
			model.setArriveTime(PupUtil.getCurrentDate()+" "+model.getArriveTime());
			model.setPickTime(PupUtil.getCurrentDate()+" "+model.getPickTime());
			//记录修改数据日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(model.getIpAddr()); 
			logVO.setFromName("数据修改");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_PUP_FIX_PICKUP_TIME");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(model.getId());
			
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
			model.setLastModifiedTime(PupUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			
			pickTimeDao.updatePickTime(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	@Override
	public void removeByRouteCodes(String[] ids,String ipAddr) throws Exception {
		if (ids.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		try {
			//设置日志记录条件
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("数据删除");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_PUP_FIX_PICKUP_TIME");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
			List<PupPickTimeModel> list = new ArrayList<PupPickTimeModel>();
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			for(int i = 0;i < ids.length; i++) {			
				PupPickTimeModel model = new PupPickTimeModel();
				model.setFactoryCode(factoryCode);
				model.setId(ids[i]);
				list.add(model);
			}
			
			pickTimeDao.removeByRouteCodes(list);
		} catch (Exception e) {
			throw new Exception("系统错误,请联系管理员");
		}
		
	}

	@Override
	public List<PupPickTimeModel> queryPickupTimeForExport(PupPickTimeModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return pickTimeDao.queryPickTimeForExport(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	@Override
	public PageList<PupPickTimeModel> queryPickTimeForImport(Map<String, String> param,Page page)throws Exception {
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
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
		try {
			//导入数据写入到临时表
			pickTimeDao.inserDataToTempDataTable(importList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = "请按模版导入数据";
			throw new RuntimeException(console);
		}
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}

	@Override
	public List<PupPickTimeModel> queryPickupTimeTempDataForExport(Map<String, String> param) throws Exception {
		try {
			return pickTimeDao.queryPickupTimeTempDataForExport(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		
	}

	@Override
	public void insertImportDataToFormalTable(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		//获取正确数据的条数
		Integer countImport = pickTimeDao.getCountForImport(paramMap);
		if (countImport > 0) {
			try {
				//查询需要更新导入的数据条数
				List<String> updateList = pickTimeDao.getUpdateList(paramMap);
				if (updateList.size() > 0) {
					//记录修改日志
					TableOpeLogVO logVO = new TableOpeLogVO();
					logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
					logVO.setOpeIp(ipAddr); 
					logVO.setFromName("导入更新");
					logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
					logVO.setTableName("MM_PUP_FIX_PICKUP_TIME");
					TableColumnVO pkColumnVO = new TableColumnVO();
					pkColumnVO.setColumnName("ID");
					String[] ids = updateList.toArray(new String[updateList.size()]);
					pkColumnVO.setColumnValArr(ids);
					this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
					
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
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理员");
			}
		}else {
			throw new Exception("没有可导入的正确数据");
		}
	}
}

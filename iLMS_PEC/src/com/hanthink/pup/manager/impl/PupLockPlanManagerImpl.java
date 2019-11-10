package com.hanthink.pup.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pup.dao.PupLockPlanDao;
import com.hanthink.pup.manager.PupLockPlanManager;
import com.hanthink.pup.model.PupLockPlanModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("lockPlan")
public class PupLockPlanManagerImpl extends AbstractManagerImpl<String, PupLockPlanModel>
					implements PupLockPlanManager{
	
	@Resource
	PupLockPlanDao lockPlanDao;
	
	@Override
	protected Dao<String, PupLockPlanModel> getDao() {
		return lockPlanDao;
	}
	/**
	 * 锁定取货计划维护查询业务实现方法
	 * @param model 查询参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的锁定计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public PageList<PupLockPlanModel> queryLockPlanForPage(PupLockPlanModel model,Page page) throws Exception {
		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<PupLockPlanModel> list = lockPlanDao.queryLockPlanModelsForPage(model, page);
		
		return new PageList<PupLockPlanModel>(list);
	}
	/**
	 * 锁定取货计划维护数据删除业务实现方法
	 * @param ids 一个/多个数据id
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public void deleteLockPlanByIds(String[] ids,String ipAddr) throws Exception {
		if(ids.length < 1) {
			throw new Exception("请选择要删除的数据");
		}
		try {
			//记录删除日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("删除数据");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_PUP_LOCK_PLAN");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			lockPlanDao.deleteLockPlanByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 锁定取货计划维护数据导出查询业务实现方法
	 * @param model 请求参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public List<PupLockPlanModel> queryLockPlanForExport(PupLockPlanModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return lockPlanDao.queryLockPlanForExport(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 锁定取货计划维护删除业务实现方法
	 * @param id 需要删除的数据的ID
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	@Override
	public void deleteLockPlanById(String id) throws Exception {
		String[] ids = new String[1];
		if (StringUtil.isNotEmpty(id)) {
			ids[0] = id;
			lockPlanDao.deleteLockPlanByIds(ids);
		}else {
			throw new Exception("请选择需要删除的数据");
		}
	}
	/**
	 * 锁定取货计划维护数据导入业务实现方法
	 * @param file Excel文件
	 * @param ipAddr 用户IP地址
	 * @return 操作结果集
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertExcelDataToTale(MultipartFile file, String ipAddr) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excle数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"area","unloadPlace","pickupType","carType","routeCode","totalNo","mergeNo","todayNo",
							"unloadPort","workday","pickDate","pickTime","arriveDate","arriveTime","assembleDate","assembleTime"};
		List<PupLockPlanModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<PupLockPlanModel>) ExcelUtil.importExcelXLS(new PupLockPlanModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<PupLockPlanModel>) ExcelUtil.importExcelXLSX(new PupLockPlanModel(), file.getInputStream(), columns, 1, 0);
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
		try {
			IUser user = ContextUtil.getCurrentUser();
			for (PupLockPlanModel pupLockPlanModel : importList) {
				if (StringUtil.isNotEmpty(pupLockPlanModel.getWorkday())) {
					try {
						if (pupLockPlanModel.getWorkday().length() < 11) {
							pupLockPlanModel.setWorkday(pupLockPlanModel.getWorkday()+" 00:00:00");
						}
						PupUtil.String2Date(pupLockPlanModel.getWorkday(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						pupLockPlanModel.setWorkday(null);
					}
				}
				//设置工作日
				if (StringUtil.isNotEmpty(pupLockPlanModel.getWorkday())) {
					pupLockPlanModel.setWorkday(pupLockPlanModel.getWorkday().substring(0,10));
				}
				
				if (StringUtil.isNotEmpty(pupLockPlanModel.getPickDate())) {
					try {
						if (pupLockPlanModel.getPickDate().length() < 11) {
							pupLockPlanModel.setPickDate(pupLockPlanModel.getPickDate()+" 00:00:00");
						}
						PupUtil.String2Date(pupLockPlanModel.getPickDate(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						pupLockPlanModel.setPickDate(null);
					}	
				}else {
					pupLockPlanModel.setPickDate(null);
				}
				if (StringUtil.isNotEmpty(pupLockPlanModel.getPickTime())) {
					try {
						if (pupLockPlanModel.getPickTime().length() < 11) {
							PupUtil.String2Date(pupLockPlanModel.getPickTime(), "HH:mm:ss");
							pupLockPlanModel.setPickTime(PupUtil.getCurrentDate()+" "+pupLockPlanModel.getPickTime());
						}
						PupUtil.String2Date(pupLockPlanModel.getPickTime(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						pupLockPlanModel.setPickTime(PupUtil.getCurrentDate()+" 00:00:00");
					}
				}else {
					pupLockPlanModel.setPickTime(PupUtil.getCurrentDate()+" 00:00:00");
				}
				//设置取货时间
				if (StringUtil.isEmpty(pupLockPlanModel.getPickDate())) {
					pupLockPlanModel.setPickDate(null);
				}else {
					pupLockPlanModel.setPickDate(pupLockPlanModel.getPickDate().substring(0, 10)+" "
							+pupLockPlanModel.getPickTime().substring(11));				
				}
				
				if (StringUtil.isNotEmpty(pupLockPlanModel.getArriveDate())) {
					try {
						if (pupLockPlanModel.getArriveDate().length() < 11) {
							pupLockPlanModel.setArriveDate(pupLockPlanModel.getArriveDate()+" 00:00:00");
						}
						PupUtil.String2Date(pupLockPlanModel.getArriveDate(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						pupLockPlanModel.setArriveDate(null);
					}	
				}else {
					pupLockPlanModel.setArriveDate(null);
				}
				if (StringUtil.isNotEmpty(pupLockPlanModel.getArriveTime())) {
					try {
						if (pupLockPlanModel.getArriveTime().length() < 11) {
							pupLockPlanModel.setArriveTime(PupUtil.getCurrentDate()+" "+pupLockPlanModel.getArriveTime());
						}
						PupUtil.String2Date(pupLockPlanModel.getArriveTime(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						pupLockPlanModel.setArriveTime(PupUtil.getCurrentDate()+" 00:00:00");
					}
				}else {
					pupLockPlanModel.setArriveTime(PupUtil.getCurrentDate()+" 00:00:00");
				}
				
				//设置到货时间
				if (StringUtil.isEmpty(pupLockPlanModel.getArriveDate()) || StringUtil.isEmpty(pupLockPlanModel.getArriveTime())) {
					pupLockPlanModel.setArriveDate(null);
				}else {
					pupLockPlanModel.setArriveDate(pupLockPlanModel.getArriveDate().substring(0, 10) +" " 
							+ pupLockPlanModel.getArriveTime().substring(11));
				}
				
				if (StringUtil.isNotEmpty(pupLockPlanModel.getArriveDate())) {
					try {
						if (pupLockPlanModel.getArriveDate().length() < 11) {
							pupLockPlanModel.setArriveDate(pupLockPlanModel.getArriveDate()+" 00:00:00");
						}
						PupUtil.String2Date(pupLockPlanModel.getArriveDate(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						pupLockPlanModel.setArriveDate(null);
					}	
				}
				if (StringUtil.isNotEmpty(pupLockPlanModel.getAssembleTime())) {
					try {
						if (pupLockPlanModel.getAssembleTime().length() < 11) {
							pupLockPlanModel.setAssembleTime(PupUtil.getCurrentDate()+" "+pupLockPlanModel.getAssembleTime());
						}
						PupUtil.String2Date(pupLockPlanModel.getAssembleTime(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						pupLockPlanModel.setAssembleTime(PupUtil.getCurrentDate()+" 00:00:00");
					}
				}
				//设置装配时间
				if (StringUtil.isEmpty(pupLockPlanModel.getAssembleDate())) {
					pupLockPlanModel.setAssembleDate(null);
				}else {
					pupLockPlanModel.setAssembleDate(pupLockPlanModel.getAssembleDate().substring(0, 10) + " " 
							+ pupLockPlanModel.getAssembleTime().substring(11));
				}
				
				pupLockPlanModel.setCreationUser(user.getAccount());
				pupLockPlanModel.setCreationTime(PupUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				pupLockPlanModel.setFactoryCode(user.getCurFactoryCode());
				pupLockPlanModel.setLastModifiedUser(user.getAccount());
				pupLockPlanModel.setLastModifiedTime(PupUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			}
			try {
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("导入数据删除");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
				logVO.setTableName("MM_PUP_LOCK_PLAN");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("FACTORY_CODE");
				pkColumnVO.setColumnVal(ContextUtil.getCurrentUser().getCurFactoryCode());
				this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
				/** 删除上一版本的数据 */
				lockPlanDao.deleteLockPlan(ContextUtil.getCurrentUser().getCurFactoryCode());
				/** 执行导入操作 */
				lockPlanDao.insertExcelDataToTale(importList);
				result = true;
				console = null;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
				console = "请按模版导入数据";
				throw new Exception(console);
			}
			resultMap.put("result", result);
			resultMap.put("console", console);
			return resultMap;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(console);
		}
	}
}

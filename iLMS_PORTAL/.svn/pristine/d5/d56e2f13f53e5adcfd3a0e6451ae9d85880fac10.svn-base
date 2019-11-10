package com.hanthink.pup.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pup.dao.PupLockPlanDao;
import com.hanthink.pup.manager.PupLockPlanManager;
import com.hanthink.pup.model.PupLockPlanModel;
import com.hanthink.pup.model.PupLockPlanPageModel;
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
	public PageList<PupLockPlanModel> queryLockPlanForPage(PupLockPlanPageModel model,Page page) throws Exception {
		
		List<PupLockPlanModel> list = new ArrayList<PupLockPlanModel>();;
		
		if(!PupUtil.isAllFieldNull(model)) {
			list = lockPlanDao.queryLockPlanModelsForPage(model, page);
		}
		
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
	public void deleteLockPlanByIds(String[] ids) throws Exception {
		if(ids.length < 1) {
			throw new Exception("请选择数据");
		}
		lockPlanDao.deleteLockPlanByIds(ids);
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
	public List<PupLockPlanModel> queryLockPlanForExport(PupLockPlanPageModel model) throws Exception {
		return lockPlanDao.queryLockPlanForExport(model);
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
		String[] columns = {"area","unloadPlace","pickupType","carType","routeCode","totalNo","mergeNo",
							"todayNo","workday","pickDate","pickTime","arriveDate","arriveTime","assembleDate","assembleTime"};
		List<PupLockPlanModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<PupLockPlanModel>) ExcelUtil.importExcelXLS(new PupLockPlanModel(), file.getInputStream(), columns, 2, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<PupLockPlanModel>) ExcelUtil.importExcelXLSX(new PupLockPlanModel(), file.getInputStream(), columns, 2, 0);
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
		for (PupLockPlanModel pupLockPlanModel : importList) {
			//设置取货时间
			pupLockPlanModel.setPickDate(pupLockPlanModel.getPickDate().substring(0, 10)+" "
													+pupLockPlanModel.getPickTime().substring(11));

			//设置到货时间
			pupLockPlanModel.setArriveDate(pupLockPlanModel.getArriveDate().substring(0, 10) +" " 
										+ pupLockPlanModel.getArriveTime().substring(11));
			//设置装配时间
			pupLockPlanModel.setAssembleDate(pupLockPlanModel.getAssembleDate().substring(0, 10) + " " 
												+ pupLockPlanModel.getAssembleTime().substring(11));
			
			pupLockPlanModel.setCreationUser(user.getAccount());
			pupLockPlanModel.setCreationTime(PupUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			pupLockPlanModel.setFactoryCode(user.getCurFactoryCode());
			pupLockPlanModel.setLastModifiedUser(user.getAccount());
			pupLockPlanModel.setLastModifiedTime(PupUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
		}
		try {
			lockPlanDao.insertExcelDataToTale(importList);
			result = true;
			console = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = "数据已存在或有误,请检查修改后再导入";
		}
		resultMap.put("result", result);
		resultMap.put("console", console);
		return resultMap;
	}
}

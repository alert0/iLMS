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
import com.hanthink.pup.dao.PupVersionGapDao;
import com.hanthink.pup.manager.PupVersionGapManager;
import com.hanthink.pup.model.PupLockPlanModel;
import com.hanthink.pup.model.PupVersionModel;
import com.hanthink.pup.model.PupVersionPageModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("versionGap")
public class PupVersionGapManagerImpl extends AbstractManagerImpl<String, PupVersionModel>
				implements PupVersionGapManager{
	
	@Resource
	private PupVersionGapDao versionDao;
	@Override
	protected Dao<String, PupVersionModel> getDao() {
		return versionDao;
	}
	/**
	 * 订单数据差异分页查询业务层实现方法
	 * @param pageModel
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public PageList<PupVersionModel> queryVersionGapForPage(PupVersionPageModel pageModel, Page page) throws Exception {
		List<PupVersionModel> list = new ArrayList<>();
		if(PupUtil.isAllFieldNull(pageModel)) {
			list = versionDao.queryVersionGapForPage(pageModel,page);
		}
		return new PageList<>(list);
	}
	/**
	 * 加载数据字典差异标识实现方法
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public List<DictVO> getDiffFlag() throws Exception {
		Map<String, Object> map = new HashMap<>();
		return versionDao.getDiffFlag(map);
	}
	/**
	 * 版本差异数据导出业务层实现方法
	 * @param pageModel
	 * @return
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public List<PupVersionModel> exportVersionGapForQuery(PupVersionPageModel pageModel) throws Exception {
		return versionDao.exportVersionGapForQuery(pageModel);
	}
	/**
	 * 导入版本数据业务层实现方法
	 * @param file
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importVersion(MultipartFile file) throws Exception {
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
			versionDao.importVersion(importList);
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

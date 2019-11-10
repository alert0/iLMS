package com.hanthink.pup.manager.impl;

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
import com.hanthink.pup.dao.PupVersionGapDao;
import com.hanthink.pup.manager.PupVersionGapManager;
import com.hanthink.pup.model.PupVersionModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.api.identity.IdentityService;
import com.hotent.sys.util.ContextUtil;

@Service("versionGap")
public class PupVersionGapManagerImpl extends AbstractManagerImpl<String, PupVersionModel>
				implements PupVersionGapManager{
	
	@Resource
	private PupVersionGapDao versionDao;
	
	@Resource
	private IdentityService service;
	
	@Override
	protected Dao<String, PupVersionModel> getDao() {
		return versionDao;
	}
	/**
	 *  检查是否生成物流计划业务实现
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public void checkIsPlan() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		
		Integer num = versionDao.checkIsPlan(factoryCode);
		
		if (num == null || num <= 0) {
			throw new Exception("请先生成物流计划");
		}
	}
	/**
	 * 检查是否导入上一版本的数据业务实现
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public void checkIsPrePlan() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		
		Integer num = versionDao.checkIsPrePlan(factoryCode);
		
		if (num == null || num <= 0) {
			throw new Exception("请导入上一版本物流计划数据");
		}
	}
	/**
	 * 订单数据差异分页查询业务层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public PageList<PupVersionModel> queryVersionGapForPage(PupVersionModel model, Page page) throws Exception {
		if (StringUtil.isNotEmpty(model.getDiffFlag())) {
			model.setFlag(Integer.parseInt(model.getDiffFlag()));
		}
		List<PupVersionModel> list = versionDao.queryVersionGapForPage(model,page);

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
	 * @param model
	 * @return
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@Override
	public List<PupVersionModel> exportVersionGapForQuery(PupVersionModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return versionDao.exportVersionGapForQuery(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importVersion(MultipartFile file, String ipAddr) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excle数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"area","unloadPlace","pickupType","carType","routeCode","totalNo","mergeNo","oldWorkday","todayNo",
							"unloadPort","pickDate","pickTime","arriveDate","arriveTime","oldAssembleDate","oldAssembleTime"};
		List<PupVersionModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<PupVersionModel>) ExcelUtil.importExcelXLS(new PupVersionModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<PupVersionModel>) ExcelUtil.importExcelXLSX(new PupVersionModel(), file.getInputStream(), columns, 1, 0);
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
		String versionNo = service.genNextNo("versionNumber");
		for (PupVersionModel pupVersionModel : importList) {
			if (StringUtil.isNotEmpty(pupVersionModel.getPickDate()) && StringUtil.isNotEmpty(pupVersionModel.getPickTime())) {
				try {
					PupUtil.String2Date(pupVersionModel.getPickDate(), "yyyy-MM-dd");
					PupUtil.String2Date(pupVersionModel.getPickTime(), "HH:mm:ss");
					pupVersionModel.setPickTime(pupVersionModel.getPickDate()+" "+pupVersionModel.getPickTime());
				} catch (Exception e) {
					pupVersionModel.setPickTime(null);
				}
			}else {
				pupVersionModel.setPickTime(null);
			}
			
			if (StringUtil.isNotEmpty(pupVersionModel.getArriveDate()) && StringUtil.isNotEmpty(pupVersionModel.getArriveTime())) {
				try {
					PupUtil.String2Date(pupVersionModel.getArriveDate(), "yyyy-MM-dd");
					PupUtil.String2Date(pupVersionModel.getArriveTime(), "HH:mm:ss");
					pupVersionModel.setArriveTime(pupVersionModel.getArriveDate()+" "+pupVersionModel.getArriveTime());
				} catch (Exception e) {
					pupVersionModel.setArriveTime(null);
				}
			}else {
				pupVersionModel.setArriveTime(null);
			}
			
			if (StringUtil.isNotEmpty(pupVersionModel.getOldAssembleDate()) && StringUtil.isNotEmpty(pupVersionModel.getOldAssembleTime())) {
				try {
					PupUtil.String2Date(pupVersionModel.getOldAssembleDate(), "yyyy-MM-dd");
					PupUtil.String2Date(pupVersionModel.getOldAssembleTime(), "HH:mm:ss");
					pupVersionModel.setOldAssembleTime(pupVersionModel.getOldAssembleDate()+" "+pupVersionModel.getOldAssembleTime());
				} catch (Exception e) {
					pupVersionModel.setOldAssembleTime(null);
				}
			}else {
				pupVersionModel.setOldAssembleTime(null);
			}
			
			if (StringUtil.isNotEmpty(pupVersionModel.getOldWorkday())) {
				try {
					PupUtil.String2Date(pupVersionModel.getOldWorkday(), "yyyy-MM-dd");
					pupVersionModel.setOldWorkday(pupVersionModel.getOldWorkday()+" 00:00:00");
				} catch (Exception e) {
					pupVersionModel.setOldWorkday(null);
				}
			}else {
				pupVersionModel.setOldWorkday(null);
			}
			//设置版本号
			pupVersionModel.setVersionNo(versionNo);
			pupVersionModel.setId(UniqueIdUtil.getSuid());
			pupVersionModel.setFactoryCode(factoryCode);
		}
		try {
			//记录导入删除数据日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("导入数据删除");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_PUP_VERSION");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("FACTORY_CODE");
			pkColumnVO.setColumnVal(factoryCode);
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			//删除上一版本的数据
			versionDao.deleteOldVersion(factoryCode);
			//将数据写入数据表中
			versionDao.insertVersionToTable(importList);
			//记录版本
			versionDao.logVersionGap(importList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = "系统错误,请联系管理员";
		}
		resultMap.put("result", result);
		resultMap.put("consile", console);
		return resultMap;
	}
	@Override
	public List<PupVersionModel> queryforVesion() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return versionDao.queryforVesion(factoryCode);
	}
	
	@Override
	public PageList<PupVersionModel> queryOneOrTwoVersion(PupVersionModel model, Page page) throws Exception {
		List<PupVersionModel> list = null;
		if (StringUtil.isNotEmpty(model.getFirstVersion()) && StringUtil.isNotEmpty(model.getLastVersion())) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("factoryCode", model.getFactoryCode());
			paramMap.put("firstVersion", model.getFirstVersion());
			paramMap.put("lastVersion", model.getLastVersion());
			list = versionDao.queryTwoVersion(paramMap,page);
		}else {
			list = versionDao.queryOneVersion(model,page);
		}
		return new PageList<PupVersionModel>(list);
	}
}

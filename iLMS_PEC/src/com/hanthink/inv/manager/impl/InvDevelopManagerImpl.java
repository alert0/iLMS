package com.hanthink.inv.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.inv.dao.InvDevelopDao;
import com.hanthink.inv.manager.InvDevelopManager;
import com.hanthink.inv.model.InvDevelopModel;
import com.hanthink.inv.model.InvLockModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:库存推移查询业务实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月16日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("develop")
public class InvDevelopManagerImpl extends AbstractManagerImpl<String, InvDevelopModel>
					implements InvDevelopManager{
	@Resource
	private InvDevelopDao developDao;
	@Override
	protected Dao<String, InvDevelopModel> getDao() {
		return developDao;
	}
	/**
	 * 库存推移分页查询业务实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public PageList<InvDevelopModel> queryDevelopForPage(InvDevelopModel model, Page page) throws Exception {
		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvDevelopModel> list = developDao.queryDevelopForPage(model,page);
		
		return new PageList<InvDevelopModel>(list);
	}
	/**
	 * 库存推移查询数据导出业务实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public List<InvDevelopModel> queryForExcelExport(InvDevelopModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<InvDevelopModel> list = developDao.queryForExcelExport(model);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 零件消耗量查询业务实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public PageList<InvDevelopModel> queryConsumptionForPage(InvDevelopModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvDevelopModel> list = developDao.queryConsumptionForPage(model,page);
		
		return new PageList<InvDevelopModel>(list);
	}
	/**
	 * 零件消耗量Excel导出业务实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public List<InvDevelopModel> queryConsumptionForExcelExport(InvDevelopModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return developDao.queryConsumptionForExcelExport(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 库存推移数据分页查询业务实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public PageList<InvDevelopModel> queryDevelopManagerForPage(InvDevelopModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return (PageList<InvDevelopModel>) developDao.queryDevelopManagerForPage(model,page);
	}
	/**
	 * 库存推移数据查询导出业务接口
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public List<InvDevelopModel> exportExcelForDevelopManager(InvDevelopModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return developDao.exportExcelForDevelopManager(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 修改库存信息数据业务实现方法
	 * @param model
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public void updateStock(InvDevelopModel model,String ipAddr) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		if(StringUtil.isEmpty(model.getId())) {
			throw new Exception("系统错误,请联系管理员");
		}
		try {
			//记录修改日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("修改库存数据");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_INV_DEV_BASE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(model.getId());
			developDao.updateStock(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 获取库存数据业务实现方法
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月16日
	 */
	@Override
	public void getStock() throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		paramMap.put("opeUser", ContextUtil.getCurrentUser().getAccount());
		paramMap.put("resultMsg", "");
		paramMap.put("resultFlag", "0");
		try {
			developDao.getStock(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public void deleteImportByUUID(String uuid) throws Exception {
		developDao.deleteImportByUUID(uuid);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importStockToTempTable(MultipartFile file, String uuid, String ipAddr) throws Exception {
		InvDevelopModel importModel = new InvDevelopModel();
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
		String[] columns = {"workCenter","wareCode","partNo","stock","adjStock"};
		List<InvDevelopModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<InvDevelopModel>) ExcelUtil.importExcelXLS(importModel,file.getInputStream(),columns,1,0);
			}else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<InvDevelopModel>) ExcelUtil.importExcelXLSX(importModel,file.getInputStream(),columns,1,0);
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
		for (InvDevelopModel invDevelopModel : importList) {
			invDevelopModel.setUuid(uuid);
			invDevelopModel.setCreationUser(user.getAccount());
			invDevelopModel.setFactoryCode(user.getCurFactoryCode());
			invDevelopModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			InvDevelopModel.checkImportModel(invDevelopModel);
		}
		try {
			//将数据写入正式表
			developDao.insertExcelToTemp(importList);
			//调用存储过程进行验证
			Map<String, String> checkMap = new HashMap<String,String>();
			checkMap.put("uuid", uuid);
			checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
			checkMap.put("opeIp", ipAddr);
			checkMap.put("errorFlag", "");
			checkMap.put("errorMsg", "");
			developDao.checkImportStock(checkMap);
			result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
			if(!result && StringUtil.isEmpty(console)){
				console = String.valueOf(checkMap.get("errorMsg"));
			}
			//临时导入情况返回
			resultMap.put("result", result);
			resultMap.put("console", console);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	@Override
	public PageList<InvDevelopModel> queryImportForPage(Page page,String uuid) throws Exception {
		if (StringUtil.isEmpty(uuid)) {
			List<InvDevelopModel> list = new ArrayList<InvDevelopModel>();
			return new PageList<InvDevelopModel>(list);
		}
		return new PageList<InvDevelopModel>(developDao.queryImportForPage(page,uuid));
	}
	@Override
	public void isImport(Map<String, Object> paramMap) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		//查看是否有数据可以批量写入
		Integer countJust =  developDao.queryImportFlag(paramMap);
		if (null == countJust || countJust <= 0) {
			throw new Exception("没有正确的数据可导入");
		}
		try {
			//将数据写入正式数据表
			developDao.insertTempToFormal(paramMap);
			//修改已导入数据的导入状态
			developDao.updateImportStatus(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	@Override
	public List<InvDevelopModel> exportExcelForImport(String uuid) throws Exception {
		try {
			return developDao.exportExcelForImport(uuid);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	@Override
	public InvLockModel queryRunStatus() throws Exception {
		try {
			return developDao.queryRunStatus(ContextUtil.getCurrentUser().getCurFactoryCode());			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	@Override
	public void elapseUpdate(Integer state) throws Exception {
		InvLockModel model = new InvLockModel();
		model.setFactory(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setIsAuto(state.toString());
		model.setIsFirst("0");
		try {
			developDao.elapseUpdate(model);
			if (state == 1) {
				developDao.elapseUpdateFirst(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
		
	}
	@Override
	public PageList<InvDevelopModel> queryDailyConsumption(InvDevelopModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<InvDevelopModel> list = developDao.queryDailyConsumption(model,page);
		return new PageList<InvDevelopModel>(list);
	}
	@Override
	public PageList<InvDevelopModel> queryDailyConsumptionDetail(InvDevelopModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		//获取当前车间对应的仓库信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("factoryCode", model.getFactoryCode());
		//获取当前车间首字母
		paramMap.put("wareCodeStart", model.getWorkCenter().substring(0,1));
		List<String> wareList = developDao.queryWareForWorkcenter(paramMap);
		//获取当前仓库下零件的最小、最大库存
		paramMap.put("partNo", model.getPartNo());
		paramMap.put("wareList", wareList);
		InvDevelopModel safeStock = developDao.queryWareListForStock(paramMap);
		//库存汇总
		List<InvDevelopModel> list = developDao.queryDailyConsumptionDetail(model,page);
		for (InvDevelopModel invDevelopModel : list) {
			invDevelopModel.setMinStock(safeStock.getMinStock());
			invDevelopModel.setMaxStock(safeStock.getMaxStock());
		}
		return new PageList<InvDevelopModel>(list);
	}
}

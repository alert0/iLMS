package com.hanthink.jisi.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jisi.dao.JisiPartDao;
import com.hanthink.jisi.manager.JisiPartManager;
import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.jisi.model.JisiPartModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.sun.org.apache.xpath.internal.operations.And;

/**
 * 
 * ClassName: JisiPartManagerImpl
 * 
 * @Description: 厂内同步零件信息维护
 * @author yokoboy
 * @date 2018年11月9日
 */
@Service("JisiPartManager")
public class JisiPartManagerImpl extends AbstractManagerImpl<String, JisiPartModel> implements JisiPartManager {

	@Resource
	private JisiPartDao dao;

	@Override
	protected Dao<String, JisiPartModel> getDao() {

		return dao;
	}

	/**
	 * 
	 * @Description: 零件信息维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return
	 * @return PageList<JisiPartModel>
	 * @throws @author luoxq
	 * @date 2018年11月9日 上午10:47:40
	 */
	@Override
	public PageList<JisiPartModel> queryJisiPartForPage(JisiPartModel model, DefaultPage p) {

		return dao.queryJisiPartForPage(model, p);
	}

	/**
	 * 
	 * @Description: 判断零件组代码和零件号是否已经存在
	 * @param @param partGroupNo
	 * @param @param partNo
	 * @param @return
	 * @return boolean
	 * @throws @author luoxq
	 * @date 2018年11月9日 上午10:51:49
	 */
	@Override
	public boolean isExistByCode(String partGroupId, String partNo) {
		List<JisiPartModel> list = dao.getJisiPartListByCode(partGroupId, partNo);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Description: 零件信息新增界面，零件组下拉框
	 * @param @param model
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月9日 上午11:11:20
	 */
	@Override
	public List<JisiPartModel> getJisiPartGroupIdUnload(JisiPartModel model) {
		return dao.getJisiPartGroupIdUnload(model);

	}

	/**
	 * 
	 * @Description: 修改零件信息维护
	 * @param @param model
	 * @param @param ipAddr
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月9日 上午11:19:06
	 */
	@Override
	public void updateAndLog(JisiPartModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_JISI_PART");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		dao.update(model);
	}

	/**
	 * 
	 * @Description: 删除零件信息维护
	 * @param @param idArr
	 * @param @param ipAddr
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月9日 上午11:29:09
	 */
	@Override
	public void removeAndLogByIds(String[] idArr, String ipAddr) {
		// 日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_JISI_PART");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(idArr);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

		super.removeByIds(idArr);
	}

	/**
	 * 
	 * @Description: 查询需要导出的数据
	 * @param @param model
	 * @param @return
	 * @return List<JisiPartModel>
	 * @throws @author luoxq
	 * @date 2018年11月9日 下午2:04:13
	 */
	@Override
	public List<JisiPartModel> queryJisiPartByKey(JisiPartModel model) {

		return dao.queryJisiPartByKey(model);
	}

	/**
	 * 
	 * @Description: 导入之前从临时表删除上次导入的数据
	 * @param @param uuid
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午2:04:41
	 */
	@Override
	public void deleteJisiPartImportTempDataByUUID(String uuid) {
		dao.deleteJisiPartImportTempDataByUUID(uuid);
	}

	/**
	 * 
	 * @Description: 文件导入临时表,并返回校验信息
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return
	 * @return Map<String,Object>
	 * @throws Exception
	 * @throws           @author luoxq
	 * @date 2018年11月10日 下午2:11:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importJisiPartModel(MultipartFile file, String uuid, String ipAddr, IUser user,
			String planCode) {
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = true;
		String console = null;

		if (file == null || file.isEmpty()) {
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		// 读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = { "partGroupNo", "partNo", "partMark", "effStart", "effEnd" };
		List<JisiPartModel> importList = null;
		try {
			if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<JisiPartModel>) ExcelUtil.importExcelXLS(new JisiPartModel(), file.getInputStream(),
						columns, 1, 0);
			} else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<JisiPartModel>) ExcelUtil.importExcelXLSX(new JisiPartModel(), file.getInputStream(),
						columns, 1, 0);
			} else {
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}

			// 数据导入初始化，格式初步检查
			for (JisiPartModel model : importList) {
				model.setId(UniqueIdUtil.getSuid());
				model.setPlanCode(planCode);
				model.setUuid(uuid);
				model.setFactoryCode(user.getCurFactoryCode());
				model.setCreationUser(user.getAccount());
				model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
				model.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
				JisiPartModel.checkImportData(model);

			}
			// 导入数据写入到临时表
			dao.insertJisiPartIntoTempData(importList);

			// 调用存储过程等检查数据准确性
			Map<String, String> checkMap = new HashMap<String, String>();
			checkMap.put("uuid", uuid);
			checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
			checkMap.put("opeIp", ipAddr);
			checkMap.put("errorFlag", "");
			checkMap.put("errorMsg", "");
			dao.checkJisiPartImportDataInformation(checkMap);
			result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
			if (!result && StringUtil.isEmpty(console)) {
				console = String.valueOf(checkMap.get("errorMsg"));
			}
			/**
			 * 检查是否可以批量导入
			 */
			String flag = dao.queryJisiPartImportFlag(uuid);
			// 临时导入情况返回
			resultMap.put("result", result);
			resultMap.put("console", console);
			resultMap.put("flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = e.getMessage();
			throw new RuntimeException(console);
		}
		return resultMap;
	}

	/**
	 * 
	 * @Description: 分页查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return
	 * @return PageList<JisiPartGroupModel>
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午4:39:36
	 */
	@Override
	public PageList<JisiPartModel> queryImportInformationForPage(Map<String, String> paramMap, Page page) {

		return dao.queryImportInformationForPage(paramMap, page);
	}

	/**
	 * 
	 * @Description: 将临时表数据写入正式表
	 * @param @param paramMap
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午5:02:27
	 */
	@Override
	public void insertTempDataToPartTable(Map<String, String> paramMap) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
		// 查看是否有数据可以导入正式表
		Integer countImport = dao.getCountForImport(paramMap);
		if (countImport == 0 ) {
			/**
			 * 将数据写入正式表
			 */
			dao.insertTempDataToRegula(paramMap);
			/**
			 * 更新导入数据的导入状态
			 */
			dao.updateImportStatus(paramMap);
		} else {
			throw new Exception("存在校验结果不通过的数据");
		}
	}

	/**
	 * 
	 * @Description: 导出Excel在导入时所有数据
	 * @param @param uuid
	 * @param @return
	 * @return List<PupProPlanModel>
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午5:45:41
	 */
	@Override
	public List<JisiPartModel> queryImportInformationForExport(String uuid) {

		return dao.queryImportInformationForExport(uuid);
	}

	/**
	 * 
	 * @Description: 根据工厂和信息点类型获取信息点
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws @author luoxq
	 * @date 2018年11月11日 下午9:41:52
	 */
	@Override
	public String getPlanCode(JisiPartModel model) {

		return dao.getPlanCode(model);
	}

	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月27日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return dao.queryIsExistsCheckResultFalse(uuid);
	}

	/**
	 * @Description: 判断零件号是否存在  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisiPartModel>   
	 * @author: dtp
	 * @date: 2019年3月28日
	 */
	@Override
	public List<JisiPartModel> queryPartNoIsExists(JisiPartModel model) {
		return dao.queryPartNoIsExists(model);
	}

}

package com.hanthink.inv.manager.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.hanthink.inv.dao.InvEmptyDao;
import com.hanthink.inv.manager.InvEmptyManager;
import com.hanthink.inv.model.InvEmptyModel;
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
 *  
 * 功能描述:空容器查询业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("empty")
public class InvEmptyManagerImpl extends AbstractManagerImpl<String, InvEmptyModel> implements InvEmptyManager {
	@Resource
	private InvEmptyDao emptyDao;

	@Override
	protected Dao<String, InvEmptyModel> getDao() {
		return emptyDao;
	}

	/**
	 * 空容器分页查询业务实现方法
	 * 
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@Override
	public PageList<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<InvEmptyModel> list = emptyDao.queryEmptyForPage(model, page);
		return new PageList<InvEmptyModel>(list);
	}

	/**
	 * 修改空容器数据信息业务实现方法
	 * 
	 * @param model
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@Override
	public void updateForEmpty(InvEmptyModel model, String ipAddr) throws Exception {
		model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
		// 记录修改日志
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("修改空容器数据");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_INV_EC");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		// 记录日志
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		emptyDao.updateForEmpty(model);
	}

	/**
	 * 空容器库存查询导出
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@Override
	public List<InvEmptyModel> exportForExcel(InvEmptyModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return emptyDao.exportForExcel(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理人员");
		}
	}

	/**
	 * 根据UUID删除导入的信息
	 */
	@Override
	public void deleteInvEmptyImportTempDataByUUID(String uuid) {
		emptyDao.deleteInvEmptyImportTempDataByUUID(uuid);
	}

	/**
	 * 导入数据到临时表
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	@Override
	public Map<String, Object> importInvEmptyModel(MultipartFile file, String uuid, String ipAddr, IUser user)
			throws IOException, Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";

		if (file == null || file.isEmpty()) {
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}

		// 读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = { "workCenter", "supplierNo", "boxType", "boxQty" };
		List<InvEmptyModel> importList = null;

		if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
			importList = (List<InvEmptyModel>) ExcelUtil.importExcelXLS(new InvEmptyModel(), file.getInputStream(),
					columns, 1, 0);
		} else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
			importList = (List<InvEmptyModel>) ExcelUtil.importExcelXLSX(new InvEmptyModel(), file.getInputStream(),
					columns, 1, 0);
		} else {
			result = false;
			console = "上传文件不是excel类型！";
			throw new RuntimeException(console);
		}

		// 数据导入初始化，格式初步检查
		for (InvEmptyModel m : importList) {
			String workCenter = emptyDao.selectWorkCenter(m.getWorkCenter());
			String boxType = emptyDao.selectBoxType(m.getBoxType());
			m.setBoxType(boxType);
			m.setWorkCenter(workCenter);
			m.setId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setOpeType("I");
			;
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			InvEmptyModel.checkImportData(m);
		}

		// 导入数据写入到临时表
		emptyDao.insertInvEmptyImportTempData(importList);

		// 调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		emptyDao.checkImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if (!result && StringUtil.isEmpty(console)) {
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = emptyDao.queryInvEmptyIsImportFlag(uuid);
		// 临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}

	/**
	 * 查询导入的临时数据信息
	 */
	@Override
	public PageList<InvEmptyModel> queryInvEmptyImportTempData(Map<String, String> paramMap, Page page) {
		return emptyDao.queryInvEmptyImportTempData(paramMap, page);
	}

	/**
	 * 将临时数据写入正式表
	 * 
	 * @throws Exception
	 */
	@Override
	public void insertInvEmptyImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {

		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		try {
			List<InvEmptyModel> list = emptyDao.queryForInsertList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
			/**
			 * 拿出Id查询哪些数据要修改
			 */
			List<InvEmptyModel> idList = emptyDao.queryUpdateDataFromInvEmptyImp(paramMap);
			if (idList.size() > 0) {
				/**
				 * 声明一个String数组，用于存放List
				 */
				for (int k = 0; k < idList.size(); k++) {
					InvEmptyModel model = idList.get(k);
					TableOpeLogVO logVO = new TableOpeLogVO();
					logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
					logVO.setOpeIp(ipAddr);
					logVO.setFromName("导入更新");
					logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
					logVO.setTableName("MM_INV_EC");
					TableColumnVO pkColumnVO = new TableColumnVO();
					pkColumnVO.setColumnName("ID");
					pkColumnVO.setColumnVal(model.getBusiId());
					this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

					/**
					 * 导入修改的方法
					 */
					paramMap.put("id", model.getBusiId());
					Integer boxQty = Integer.parseInt(model.getBoxQty());
					paramMap.put("boxQty", boxQty);
					emptyDao.updateInvEmptyImportData(paramMap);
				}

			}

			/**
			 * 导入新增的方法
			 */
			emptyDao.insertInvEmptyImportData(paramMap);

			// 更新临时数据导入状态
			emptyDao.updateInvEmptyImportDataImpStatus(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误，请联系管理员");
		}
	}

	/**
	 * 导出临时数据信息
	 */
	@Override
	public List<InvEmptyModel> queryInvEmptyImportTempDataForExport(Map<String, String> paramMap) {
		return emptyDao.queryInvEmptyImportTempDataForExport(paramMap);
	}
}

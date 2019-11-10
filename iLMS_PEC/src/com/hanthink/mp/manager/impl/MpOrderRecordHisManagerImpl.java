package com.hanthink.mp.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ctc.wstx.io.ISOLatinReader;
import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpOrderRecordHisDao;
import com.hanthink.mp.manager.MpOrderRecordHisManager;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：零件分组 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpOrderRecordHisManager")
public class MpOrderRecordHisManagerImpl extends AbstractManagerImpl<String, MpOrderRecordHisModel> implements MpOrderRecordHisManager{
	@Resource
	MpOrderRecordHisDao mpOrderRecordHisDao;
	@Override
	protected Dao<String, MpOrderRecordHisModel> getDao() {
		return mpOrderRecordHisDao;
	}
	
	/**
	 * 分页查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	 @Override
	    public PageList<MpOrderRecordHisModel> queryMpOrderRecordHisForPage(MpOrderRecordHisModel model, DefaultPage p) {
	        return (PageList<MpOrderRecordHisModel>) mpOrderRecordHisDao.queryMpOrderRecordHisForPage(model, p);
	    }

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpOrderRecordHisModel> queryMpOrderRecordHisByKey(MpOrderRecordHisModel model) {
		return mpOrderRecordHisDao.queryMpOrderRecordHisByKey(model);
	}

	/**
	 * 获取计算队列
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<DictVO> getUnloadPort() {
		
		return mpOrderRecordHisDao.getUnloadPort();
	}

	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月14日 上午10:54:52
	 */
	@Override
	public void updateAndLog(MpOrderRecordHisModel mpOrderRecordHisModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_MP_ORDER_RECORD_HIS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PLAN_ORDER_ID");
		pkColumnVO.setColumnVal(mpOrderRecordHisModel.getPlanOrderId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		mpOrderRecordHisDao.update(mpOrderRecordHisModel);
		
	}
	
	/**
	 * 导入Excel数据MpOrderRecordHisModel
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 上午10:27:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importMpOrderRecordHisModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception{
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"purchaseNo", "supplierNo", "supFactory", "supplierName",
				"unloadPort","modelCode","partNo","partShortNo","partNameCn",
				"groupIdStr","logisticsOrder","drSortIdStartStr","drSortIdEndStr",
				"necessaryOrderResidualStr","adjDiffNumStr","defectNumStr","safeNumStr",
				"necessaryNetNumStr","necessaryPlanNumStr","orderNumStr","orderPackageStr","necessaryRealResidualNumStr",
				"adjBoxStr","totalOrderBoxStr","totalOrderNumStr","arriveTimeStr",
				"codeValueName"};
		List<MpOrderRecordHisModel> importList = null;
		
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<MpOrderRecordHisModel>) ExcelUtil.importExcelXLS(new MpOrderRecordHisModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<MpOrderRecordHisModel>) ExcelUtil.importExcelXLSX(new MpOrderRecordHisModel(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		
		
		//数据导入初始化，格式初步检查
		for(MpOrderRecordHisModel m : importList){
			String orderStatus =  mpOrderRecordHisDao.queryOrderStatus(m.getCodeValueName());
			m.setOrderStatus(orderStatus);
			m.setId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setFactoryCode(user.getCurFactoryCode());
            m.setCreationUser(user.getAccount());
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			MpOrderRecordHisModel.checkImportData(m);
		}
		
		//导入数据写入到临时表
		mpOrderRecordHisDao.insertMpOrderRecordHisImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		mpOrderRecordHisDao.checkMpOrderRecordHisImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = mpOrderRecordHisDao.queryMpOrderRecordHisIsImportFlag(uuid);
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}
	
	/**
	 * 查询导入临时表数据
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public PageList<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempData(Map<String, String> paramMap, Page page) {
		return mpOrderRecordHisDao.queryMpOrderRecordHisImportTempData(paramMap, page);
	}
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public List<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempDataForExport(Map<String, String> paramMap) {
		return mpOrderRecordHisDao.queryMpOrderRecordHisImportTempDataForExport(paramMap);
	}

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月2日 下午12:22:28
	 */
	@Override
	public void insertMpOrderRecordHisImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		 try {			
				List<MpOrderRecordHisModel> list = mpOrderRecordHisDao.queryForInsertList(paramMap);
				if (list.size() < 1) {
					throw new Exception("没有正确数据可导入或已全部导入");
				}
		/**
		 * 拿出Id查询哪些数据要修改
		 */
		List<String> ids = mpOrderRecordHisDao.queryUpdateDataFromMpOrderRecordHisImp(paramMap);
		if(ids.size()>0) {
			/**
			 * 声明一个String数组，用于存放List
			 */
			String[] idp = ids.toArray(new String[ids.size()]);
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("导入更新");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_MP_ORDER_RECORD_HIS");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("PLAN_ORDER_ID");
			pkColumnVO.setColumnValArr(idp);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			
			/**
			 * 导入修改的方法
			 */
			mpOrderRecordHisDao.updateMpOrderRecordHisImportData(paramMap);
		    
		    }
		
		//更新临时数据导入状态
		mpOrderRecordHisDao.updateMpOrderRecordHisImportDataImpStatus(paramMap);
		
		 }catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误，请联系管理员");
				
			}
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public void deleteMpOrderRecordHisImportTempDataByUUID(String uuid) {
		mpOrderRecordHisDao.deleteMpOrderRecordHisImportTempDataByUUID(uuid);
	}
	
}

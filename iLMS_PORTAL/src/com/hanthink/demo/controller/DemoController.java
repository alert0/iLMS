package com.hanthink.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.demo.manager.DemoManager;
import com.hanthink.demo.model.DemoModel;
import com.hanthink.demo.model.DemoModelImport;
import com.hanthink.demo.model.JisiInsBean;
import com.hanthink.demo.model.ParameterName;
import com.hanthink.demo.model.ParameterValue;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.model.DataDict;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;

/**
 * 
 * <pre>
 * Description: 测试DEMO
 * Company: HanThink
 * Date: 2018年8月15日 上午11:26:57
 * </pre>
 * @author ZUOSL
 */
@Controller
@RequestMapping("/demo/demo")
public class DemoController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(DemoController.class);
	
	@Resource
	private DemoManager demoManager;
	
	@RequestMapping("genPdfTest")
	public void genPdfTest (HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println("-------开始---------");
		long start = System.currentTimeMillis();
		String pageStr = RequestUtil.getString(request, "page");
		if(null == pageStr){
			pageStr = "5";
		}
		int page = Integer.valueOf(pageStr);
		try {
			JRDataSource jRDataSource = new JRBeanCollectionDataSource(initData(page*30));
//			String filenurl = "E:\\test\\ireport\\JISI_INS1.jasper";
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "demo" + File.separator + "JISI_INS1.jasper";
            InputStream file = new FileInputStream(filenurl);

            JasperPrint jasperPrint = JasperFillManager.fillReport(file, initMap(),
            		jRDataSource);
//            OutputStream out = new FileOutputStream("E:\\test\\ireport\\JISI_INS1.pdf");
            
            String exportFileName  = "abced";
//    		response.setContentType("multipart/form-data");
            response.setContentType("application/pdf");
//    		response.setHeader("Content-disposition", 
//					"attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1") + ".pdf");
//            response.setHeader("Content-disposition", 
//					"inline; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1") + ".pdf");
            response.setHeader("Content-disposition", 
					"inline;");
    		OutputStream out = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            
            long end = System.currentTimeMillis();
            System.out.println("-----------结束-------------:"+(end-start));
            
            
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		
	}
	

	/**
	 * 查询Demo信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年8月31日 下午3:44:01
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter = getQueryFilter(request);
		
		//获取用户信息
//		IUser user = ContextUtil.getCurrentUser();
//		System.out.println("工厂代码：" + user.getCurFactoryCode());
		
		//保存Filter
		HttpSession session = request.getSession();
		session.setAttribute(SessionKey.DEMO_DEMOMODEL_QUERYFILTER, queryFilter);
		
		PageList<DemoModel> pageList = (PageList<DemoModel>) demoManager.query(queryFilter);
		return new PageJson(pageList);
	}
	
	/**
	 * 根据主键ID查询Demo信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年8月31日 下午3:46:23
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody DemoModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new DemoModel();
		}
		return demoManager.get(id);
	}
	
	/**
	 * 保存更新Demo数据信息
	 * @param request
	 * @param response
	 * @param demoModel
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年8月31日 下午3:51:44
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody DemoModel demoModel) throws Exception{
		String resultMsg = null;
		String id = demoModel.getId();
		try {
			if(StringUtil.isEmpty(id)){
				demoModel.setId(UniqueIdUtil.getSuid());
				
				demoManager.create(demoModel);
				resultMsg="添加成功";
			}else{
				demoManager.updateAndLog(demoModel, RequestUtil.getIpAddr(request));
				resultMsg="更新成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 删除Demo数据信息
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年8月31日 下午3:54:01
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "pkId");
			demoManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 下载导出Demo数据信息
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 上午11:25:20
	 */
	@RequestMapping("downloadDemoModel")
	public void downloadDemoModel(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		QueryFilter queryFilter = (QueryFilter)session.getAttribute(SessionKey.DEMO_DEMOMODEL_QUERYFILTER);
		if(null == queryFilter){
			queryFilter = getQueryFilter(request);
		}
		PageList<DemoModel> pageList = (PageList<DemoModel>) demoManager.query(queryFilter);
		
		//判断记录是否超过系统允许数量
		queryFilter.setPage(new DefaultPage(1, 100));
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		//清空page对象重新查询
		if(pageList.getPageResult().getTotalPages() > 1){
			queryFilter.clearPage();
			pageList = (PageList<DemoModel>) demoManager.query(queryFilter);
		}
		String[] headers = {"字段一", "字段二", "字段三", "字段四"};
		String[] columns = {"col1", "col2", "col3", "col4"};
		int[] widths = {40, 40, 40, 40};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "DEMO导入数据校验结果", pageList, headers, widths, columns);
	}
	
	/**
	 * 大数据量导出
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午6:15:49
	 */
	@RequestMapping("downloadDemoModel2")
	public void downloadDemoModel2(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		QueryFilter queryFilter = (QueryFilter)session.getAttribute(SessionKey.DEMO_DEMOMODEL_QUERYFILTER);
		if(null == queryFilter){
			queryFilter = getQueryFilter(request);
		}
		PageList<DemoModel> pageList = (PageList<DemoModel>) demoManager.query(queryFilter);
		
		//判断记录是否超过系统允许数量
		queryFilter.setPage(new DefaultPage(1, 10));
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000); //获取系统所允许的最大导出数量
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		DemoExportUtil exportUtil = new DemoExportUtil(demoManager, queryFilter);
		String[] headers = {"字段一", "字段二", "字段三", "字段四"};
		String[] columns = {"col1", "col2", "col3", "col4"};
		int[] widths = {40, 40, 40, 40};
		try {
			exportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "DEMO导入数据校验结果", pageList, headers, widths, columns);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	
	/**
	 * 导入Excel数据信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年9月1日 下午11:03:18
	 */
	@RequestMapping("importDemoModel")
	public void importDemoModel(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.DEMO_DEMOMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				demoManager.deleteImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.DEMO_DEMOMODEL_IMPORT_UUID, uuid);
			
			Map<String,Object> rtn = demoManager.importDemoModel(file, uuid, RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), "失败", "", JSONObject.fromObject(rtn), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 大数据量导入
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午6:16:29
	 */
	@RequestMapping("importDemoModel2")
	public void importDemoModel2(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.DEMO_DEMOMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				demoManager.deleteImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.DEMO_DEMOMODEL_IMPORT_UUID, uuid);
			
			Map<String,Object> rtn = demoManager.importDemoModel2(file, uuid, RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), "失败", "", JSONObject.fromObject(rtn), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), e.getMessage(), e.toString(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 查询导入的临时数据信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年9月1日 下午6:59:07
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.DEMO_DEMOMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<DemoModelImport> pageList = (PageList<DemoModelImport>) demoManager.queryImportTempData(paramMap, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 确定导入，将临时表数据写入到正式业务表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:08:51
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.DEMO_DEMOMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			demoManager.insertImportData(paramMap);
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 导出临时数据信息
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.DEMO_DEMOMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		Page page = new DefaultPage(1, 50000);
		PageList<DemoModelImport> pageList = demoManager.queryImportTempData(paramMap, page);
		
		String[] headers = {"字段一", "字段二", "字段三", "字段四",
				"校验结果", "校验信息"};
		String[] columns = {"col1", "col2", "col3", "col4", "checkResult", "checkInfo"};
		int[] widths = {40, 40, 40, 40, 50, 360};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "DEMO导入数据校验结果", pageList, headers, widths, columns);
	}
	
	
	@RequestMapping("loadComboData")
	public @ResponseBody PageJson loadComboData(HttpServletRequest request,HttpServletResponse response){
		
		//获取参数
		String param = RequestUtil.getString(request, "test");
		System.out.println("参数：" + param);
		
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> typeDataDictList = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < 5; i ++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeKey", "YES_NO");
			map.put("typeName", "测试类型");
			map.put("valueKey1", "value" + i);
			map.put("valueName1", "显示名称" + i);
			map.put("sortNum", i);
			typeDataDictList.add(map);
		}
		
		for(int i = 0; i < 5; i ++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeKey", "YES_NO2");
			map.put("typeName", "类型");
			map.put("valueKey1", "vaue" + i);
			map.put("valueName1", "显名称" + i);
			map.put("sortNum", i);
			typeDataDictList.add(map);
		}
		dataDictList.addAll(typeDataDictList);
		
		return new PageJson(dataDictList);
	}
	
	
	//------------------------------------------------------------------------------------

 	public static void main(String[] args) {
		
		try {
//			System.out.println("-------开始---------");
//			long start = System.currentTimeMillis();
//			JRDataSource jRDataSource = new JRBeanCollectionDataSource(initData());
//			String filenurl = "E:\\test\\ireport\\JISI_INS1.jasper";
//            InputStream file = new FileInputStream(filenurl);
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(file, initMap(),
//            		jRDataSource);
//            OutputStream out = new FileOutputStream("E:\\test\\ireport\\JISI_INS1.pdf");
//            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
//            long end = System.currentTimeMillis();
//            System.out.println("-----------结束-------------:"+(end-start));
			
			
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Map initMap(){
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		//头信息
		parameters.put(ParameterName.TITLE_LABEL, ParameterValue.JISI_INS);
		parameters.put(ParameterName.CODE_LABEL, ParameterValue.CODE_VALUE);
		parameters.put(ParameterName.DISTR_BATCH,ParameterValue.DISTR_BATCH_VALUE);
		parameters.put(ParameterName.PARTGROUP_LABEL,ParameterValue.PARTGROUP_VALUE);
		parameters.put(ParameterName.PART_NAME_LABEL,ParameterValue.PART_NAME_VALUE);
		parameters.put(ParameterName.LOCATION_LABEL,ParameterValue.LOCATION_VALUE);
		parameters.put(ParameterName.RECEIVE_LABEL,ParameterValue.RECEIVE_VALUE);
		parameters.put(ParameterName.SPARE_LABEL,ParameterValue.SPARE_VALUE);
		parameters.put(ParameterName.SHIP_LABEL,ParameterValue.SHIP_VALUE);
		parameters.put(ParameterName.LINE_LABEL,ParameterValue.LINE_VALUE);
		
		//列表
		parameters.put(ParameterName.NO_LABEL,ParameterValue.NO_VALUE);
		parameters.put(ParameterName.PA_OFF_DATE_LABEL,ParameterValue.PA_OFF_DATE_VALUE);
		parameters.put(ParameterName.PA_OFF_TIME_LABEL,ParameterValue.PA_OFF_TIME_VALUE);
		parameters.put(ParameterName.SEQ_NO_LABEL,ParameterValue.SEQ_NO_VALUE);
		parameters.put(ParameterName.MTOC_LABEL,ParameterValue.MTOC_VALUE);
		//车身序号
		parameters.put(ParameterName.VEHICLE_NO_LABEL,ParameterValue.VEHICLE_NO);
		parameters.put(ParameterName.PART_SHORT_NO_LABEL,ParameterValue.PART_SHORT_NO_VALUE);
		parameters.put(ParameterName.PART_NO_LABEL,ParameterValue.PART_NO_VALUE);
		parameters.put(ParameterName.QUANTITY_LABEL,ParameterValue.QUANTITY_VALUE);
		parameters.put(ParameterName.REMARK_LABEL,ParameterValue.REMARK_VALUE);
		
		//页脚
		parameters.put(ParameterName.DI_DES_LABEL,ParameterValue.CHECK_VALUE);
		parameters.put(ParameterName.TOTAL_PAGE_LABEL,ParameterValue.TOTAL_PAGE_VALUE);
		parameters.put(ParameterName.PAGE_DES_LABEL,ParameterValue.PAGE_VALUE);
		
		parameters.put(ParameterName.CHECK_LABEL,ParameterValue.CHECK_VALUE);
		parameters.put(ParameterName.PAGE_TOTAL_LABEL,ParameterValue.PAGE_TOTAL_VALUE);
		parameters.put(ParameterName.PAGE_LABEL,ParameterValue.PAGE_VALUE);
		parameters.put(ParameterName.JIT_PRINT_TIMELABEL,ParameterValue.PRINT_TIME_VALUE);
		
		parameters.put("codeValue", "code");
		parameters.put("distrBatchV", "distrBatch");
		parameters.put("partGroup","partGroup");
		parameters.put("partName","partName");
		parameters.put("locationV","locationv");
		parameters.put("insNo","insNo");
		parameters.put("printTime","printTime");
		
		return parameters;
	}
	
	private static List<JisiInsBean> initData() {
		return initData(5);
	}
	
	public static List<JisiInsBean> initData(int total){
		
		ArrayList<JisiInsBean> list = new ArrayList<JisiInsBean>();
		for(int i = 0; i < total; i ++){
			JisiInsBean meterBean = new JisiInsBean();
//			meterBean.setCodeValue(resultSet.getString("code"));
//			meterBean.setDistrBatch(resultSet.getString("distrBatch"));
//			meterBean.setPartGroup(resultSet.getString("partGroup"));
//			meterBean.setPartName(resultSet.getString("partName"));
//			meterBean.setLocation(resultSet.getString("location"));
//			meterBean.setInsNo(resultSet.getString("insNo"));
//			
//			meterBean.setPaOffDate(resultSet.getString("paOffTime").substring(0,10));
//			meterBean.setPaOffTime(resultSet.getString("paOffTime").substring(10,resultSet.getString("paOffTime").length()));
//			meterBean.setSeqNo(resultSet.getString("seqNo"));
//			meterBean.setMtoc(resultSet.getString("mtoc"));
//			meterBean.setVin(resultSet.getString("vin"));
//			meterBean.setPartShortNo(resultSet.getString("partShortNo"));
//			meterBean.setPartNo(resultSet.getString("partNo"));
//			meterBean.setQuantity(resultSet.getString("quantity"));
//			meterBean.setRemark(resultSet.getString("remark"));
			if (meterBean != null) {
				list.add(meterBean);
			}
		}
		
		ArrayList<JisiInsBean> listTable = new ArrayList<JisiInsBean>();
		for(int i=0;i<list.size(); i++){
			JisiInsBean meterBean1 = list.get(i);
			JisiInsBean meterBean = new JisiInsBean();
			meterBean.setNo(""+(i+1));
			meterBean.setPaOffDate(meterBean1.getPaOffDate());
			meterBean.setPaOffTime(meterBean1.getPaOffTime());
			meterBean.setSeqNo(meterBean1.getSeqNo());
			meterBean.setMtoc(meterBean1.getMtoc());
			meterBean.setVin(meterBean1.getVin());
			meterBean.setPartShortNo(meterBean1.getPartShortNo());
			meterBean.setPartNo(meterBean1.getPartNo());
			meterBean.setQuantity(meterBean1.getQuantity());
			meterBean.setRemark(meterBean1.getRemark());
			listTable.add(meterBean);
		}
		return listTable;
	}
	
}

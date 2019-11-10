package com.hanthink.mp.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpOrderRecordHisManager;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：订单履历 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpOrderRecordHis")
public class MpOrderRecordHisController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpOrderRecordHisController.class);
	
	@Resource
	MpOrderRecordHisManager mpOrderRecordHisManager;
	  	
	/**
     * 分页查询订单履历
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			MpOrderRecordHisModel model) {
		String resultMsg = null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpOrderRecordHisModel> pageList = (PageList<MpOrderRecordHisModel>) mpOrderRecordHisManager
					.queryMpOrderRecordHisForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
	 * 获取计算队列填充下拉框
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("getUnloadPort")
	public @ResponseBody Object getUnloadPort(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = mpOrderRecordHisManager.getUnloadPort();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 获取明细
	 * <p>return: PkgBoxModel</p>  
	 * <p>Description: MpOrderRecordHisController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月26日
	 * @version 1.0
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody MpOrderRecordHisModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String planOrderId = RequestUtil.getString(request, "planOrderId");
		if(StringUtil.isEmpty(planOrderId)){
			return new MpOrderRecordHisModel();
		}
		mpOrderRecordHisManager.get(planOrderId);
		/*System.out.println(mpOrderRecordHisManager.get(planOrderId).getOrderStatus());*/
		return mpOrderRecordHisManager.get(planOrderId);	
	}
	
	/**
	 * 保存订单履历信息
	 * @param request
	 * @param response
	 * @param MpOrderRecordHis
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody MpOrderRecordHisModel mpOrderRecordHisModel) throws Exception{
		String resultMsg=null;
		String planOrderId = mpOrderRecordHisModel.getPlanOrderId();
		String orderStatus = mpOrderRecordHisModel.getOrderStatus();
		try {
			if(!StringUtil.isEmpty(planOrderId) && orderStatus.equals("0")){
				mpOrderRecordHisManager.updateAndLog(mpOrderRecordHisModel, RequestUtil.getIpAddr(request));
				resultMsg="更新订单履历成功";
			}
			else {
				resultMsg="订单状态不为未计划";
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
				return;
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg="对订单履历操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 下载导出MpOrderRecordHis数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpOrderRecordHisModel")
	public void downloadMpOrderRecordHisModel(HttpServletRequest request,HttpServletResponse response
			,MpOrderRecordHisModel model){
		try {
		/*HttpSession session = request.getSession();
		MpOrderRecordHisModel model = (MpOrderRecordHisModel)session.getAttribute(SessionKey.MP_RESIDUALMODEL_QUERYFILTER);*/
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpOrderRecordHisModel> list =  mpOrderRecordHisManager.queryMpOrderRecordHisByKey(model);
		/**
		 * 如果查询记录超过10000条则报错
		 */
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"订单号", "供应商代码", "出货地", "供应商名称",
				"计算队列","车型","零件号","简号","零件名称", "采购层级",
				"分组号","临时单号","D_R起始号","D_R结束号",
				"上回零件剩余量","计划对比调整","不良数量","安全库存",
				"净需求","订单净需求","订单量（个数）","订单量（箱数）","本回零件剩余量",
				"手动调整（箱数）","最终订单量（箱数）","最终订单量（个数）","到货时间",
				"订单状态", "创建时间"};
		String[] columns = {"purchaseNo", "supplierNo", "supFactory", "supplierName",
				"unloadPort","modelCode","partNo","partShortNo","partNameCn", "purchaseType",
				"groupId","logisticsOrder","drSortIdStart","drSortIdEnd",
				"necessaryOrderResidual","adjDiffNum","defectNum","safeNum",
				"necessaryNetNum","necessaryPlanNum","orderNum","orderPackage","necessaryRealResidualNum",
				"adjBox","totalOrderBox","totalOrderNum","arriveTimeStr",
				"codeValueName", "creationTimeStr"};
		int[] widths = {80, 80, 80, 80, 80,
				80, 80, 80, 80, 80, 80, 80,
				80, 80, 80, 80, 80,
				80, 80, 80, 80, 80,
				80, 80, 80, 80, 80,
				80, 80, 80, 80, 
				80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "订单履历"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	/**
	 * 大数据量导出
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午6:15:49
	 */
	/*@RequestMapping("downloadMpOrderRecordHisModel2")
	public void downloadMpOrderRecordHisModel2(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		MpOrderRecordHisModel model = (MpOrderRecordHisModel)session.getAttribute(SessionKey.MP_RESIDUALMODEL_QUERYFILTER);
		List<MpOrderRecordHisModel> list =  mpOrderRecordHisManager.queryMpOrderRecordHisByKey(model);
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		MpOrderRecordHisExportUtil exportUtil = new MpOrderRecordHisExportUtil(mpOrderRecordHisManager, model);
		String[] headers = {"订单号", "供应商代码", "出货地", "供应商名称",
				"计算队列","车型","零件号","简号","零件名称",
				"分组号","临时单号","D_R起始号","D_R结束号",
				"上回零件剩余量","计划对比调整","不良数量","安全库存",
				"净需求","订单净需求","订单量（个数）","订单量（箱数）","本回零件剩余量",
				"手动调整（箱数）","最终订单量（箱数）","最终订单量（个数）","到货时间",
				"订单状态"};
		String[] columns = {"purchaseNo", "supplierNo", "supFactory", "supplierName",
				"unloadPort","modelCode","partNo","partShortNo","partNameCn",
				"groupId","logisticsOrder","drSortIdStart","drSortIdEnd",
				"necessaryOrderResidual","adjDiffNum","defectNum","safeNum",
				"necessaryNetNum","necessaryPlanNum","orderNum","orderPackage","necessaryRealResidualNum",
				"adjBox","totalOrderBox","totalOrderNum","arriveTimeStr",
				"codeValueName"};
		int[] widths = {80, 80, 80, 80, 
				80, 80, 80, 80, 80, 80,
				80, 80, 80, 80, 
				80, 80, 80, 80, 
				80, 80, 80, 80, 80,
				80, 80, 80, 80, 
				80};
		try {
			exportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "MM_MP_ORDER_RECORD_HIS_LOAD", list, headers, widths, columns);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}*/
	
	/**
	 * 导入Excel数据信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月1日 下午11:03:18
	 */
	@RequestMapping("importMpOrderRecordHisModel")
	public void importMpOrderRecordHisModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				mpOrderRecordHisManager.deleteMpOrderRecordHisImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID, uuid);
			
			Map<String,Object> resultMap = mpOrderRecordHisManager.importMpOrderRecordHisModel(file, uuid, RequestUtil.getIpAddr(request),user);
			/**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", 
				JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(),"失败", (String) resultMap.get("console"),
				JSONObject.fromObject(resultMap), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 查询导入的临时数据信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月1日 下午6:59:07
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<MpOrderRecordHisModel> pageList = (PageList<MpOrderRecordHisModel>) mpOrderRecordHisManager.queryMpOrderRecordHisImportTempData(paramMap, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 确定导入，将临时表数据写入到正式业务表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:08:51
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			mpOrderRecordHisManager.insertMpOrderRecordHisImportData(paramMap,RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
			
		} catch (Exception e) {
    		e.printStackTrace();
			log.error(e.toString());
			
			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 导出临时数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.MM_MP_ORDER_RECORD_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<MpOrderRecordHisModel> list = mpOrderRecordHisManager.queryMpOrderRecordHisImportTempDataForExport(paramMap);
		
		String[] headers = {"订单号", "供应商代码", "出货地", "供应商名称",
				"计算队列","车型","零件号","简号","零件名称",
				"分组号","临时单号","D_R起始号","D_R结束号",
				"上回零件剩余量","计划对比调整","不良数量","安全库存",
				"净需求","订单净需求","订单量（个数）","订单量（箱数）","本回零件剩余量",
				"手动调整（箱数）","最终订单量（箱数）","最终订单量（个数）","到货时间",
				"订单状态",
				"校验结果","导入状态", "校验信息"};
		String[] columns = {"purchaseNo", "supplierNo", "supFactory", "supplierName",
				"unloadPort","modelCode","partNo","partShortNo","partNameCn",
				"groupId","logisticsOrder","drSortIdStart","drSortIdEnd",
				"necessaryOrderResidual","adjDiffNum","defectNum","safeNum",
				"necessaryNetNum","necessaryPlanNum","orderNum","orderPackage","necessaryRealResidualNum",
				"adjBox","totalOrderBox","totalOrderNum","arriveTimeStr",
				"codeValueName",
				"codeValueNameF","codeValueNameG", "checkInfo"};
		int[] widths = {80, 80, 80, 80, 
				80, 80, 80, 80, 80, 80,
				80, 80, 80, 80, 
				80, 80, 80, 80, 
				80, 80, 80, 80, 80,
				80, 80, 80, 80, 
				80,
				50,50, 360};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "订单履历"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
	
	
}

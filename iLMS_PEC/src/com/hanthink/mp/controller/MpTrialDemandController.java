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

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpTrialDemandManager;
import com.hanthink.mp.model.MpTrialDemandModel;
import com.hanthink.mp.model.MpTrialDemandModelImport;
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

import net.sf.json.JSONObject;


/**
 * 
 * <pre> 
 * 描述：新车型需求计算 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpTrialDemand")
public class MpTrialDemandController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpTrialDemandController.class);
	
	@Resource
	MpTrialDemandManager mpTrialDemandManager;
	  		
	/**
     * 分页查询新车型需求计算
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
			MpTrialDemandModel model) {
		String resultMsg = null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpTrialDemandModel> pageList = (PageList<MpTrialDemandModel>) mpTrialDemandManager
					.queryMpTrialDemandForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 新车型需求计算明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody MpTrialDemandModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		try {
		if(StringUtil.isEmpty(id)){
			return new MpTrialDemandModel();
		}
		return mpTrialDemandManager.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存新车型需求计算信息
	 * @param request
	 * @param response
	 * @param MpTrialDemand
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MpTrialDemandModel mpTrialDemandModel) throws Exception{
		String resultMsg=null;
		String id =mpTrialDemandModel.getId();
		try {
			if(StringUtil.isEmpty(id)){
				mpTrialDemandModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				mpTrialDemandManager.updateAndLog(mpTrialDemandModel, RequestUtil.getIpAddr(request));
				resultMsg="更新新车型需求计算成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg="对新车型需求计算操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 需求计算生成
	 * <p>return: void</p>  
	 * <p>Description: MpTrialDemandController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	@RequestMapping("generateMpTrialDemand")
	public void generateMpTrialDemand(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = mpTrialDemandManager.generateMpTrialDemand(ContextUtil.getCurrentUser().getCurFactoryCode());
			if (0 == count) {
				message = new ResultMessage(ResultMessage.SUCCESS, "需求计算生成成功");
			} else {
			    message=new ResultMessage(ResultMessage.FAIL, "需求计算生成失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "需求计算生成失败");
		}
 		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 需求计算发布
	 * <p>return: void</p>  
	 * <p>Description: MpTrialDemandController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	@RequestMapping("releaseMpTrialDemand")
	public void releaseMpTrialDemand(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = mpTrialDemandManager.releaseMpTrialDemand(ContextUtil.getCurrentUser().getCurFactoryCode());
			if (0 == count) {
			    message=new ResultMessage(ResultMessage.SUCCESS, "需求计算发布成功");
			}else {
				message=new ResultMessage(ResultMessage.FAIL, "需求计算发布失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "需求计算发布失败");
		}
 		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 下载导出MpTrialDemand数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpTrialDemandModel")
	public void downloadMpTrialDemandModel(HttpServletRequest request,HttpServletResponse response
			,MpTrialDemandModel model){
		try {
		//String exportFileName = "新车型需求计算"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		/*HttpSession session = request.getSession();
		MpTrialDemandModel model = (MpTrialDemandModel)session.getAttribute(SessionKey.MP_TRIAL_DEMANDMODEL_QUERYFILTER);*/
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpTrialDemandModel> list =  mpTrialDemandManager.queryMpTrialDemandByKey(model);
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
		
		String[] headers = {
				"物流单号","订单号", "行号",
				"供应商代码","出货地代码","供应商名称","零件号","简号", 
				"到货日期","采购类型","净需求","例外需求","总需求","订单状态"};
		String[] columns = {
				"orderNo","purchaseNo","rowNo",
				"supplierNo","supFactory","supplierName","partNo","partShortNo",
				"arriveTimeStr","purchaseType","orderNum","excOrderNum","totalOrderNum","codeValueNameD"};
		int[] widths = {
				80, 80, 80,
				80, 80, 80, 80, 80,
				80, 80, 80, 80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "新车型需求计算"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
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
	 * @author linzhuo	
	 * @DATE	2018年9月1日 下午11:03:18
	 */
	@RequestMapping("importMpTrialDemandModel")
	public void importMpTrialDemandModel(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_DEMANDMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				mpTrialDemandManager.deleteMpTrialDemandImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MP_TRIAL_DEMANDMODEL_IMPORT_UUID, uuid);
			
			Map<String,Object> resultMap = mpTrialDemandManager.importMpTrialDemandModel(file, uuid, RequestUtil.getIpAddr(request),user);
			 /**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
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
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_DEMANDMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<MpTrialDemandModelImport> pageList = (PageList<MpTrialDemandModelImport>) mpTrialDemandManager.queryMpTrialDemandImportTempData(paramMap, page);
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
			String uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_DEMANDMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			mpTrialDemandManager.insertMpTrialDemandImportData(paramMap,RequestUtil.getIpAddr(request));
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
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_DEMANDMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<MpTrialDemandModelImport> list = mpTrialDemandManager.queryMpTrialDemandImportTempDataForExport(paramMap);
		
		String[] headers = {"零件号", "出货地代码","供应商代码", "到货日期","例外需求",
				"校验结果","导入状态", "校验信息"};
		String[] columns = {"partNo", "supFactory","supplierNo", "arriveTimeStr","excOrderNum", 
				"codeValueNameB","codeValueNameC", "checkInfo"};
		int[] widths = {80,80,80,80,80,
				50,50, 360};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "需求计算"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
	
	
}

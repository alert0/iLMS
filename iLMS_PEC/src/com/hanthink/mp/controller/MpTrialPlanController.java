package com.hanthink.mp.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ls.LSException;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpTrialPlanManager;
import com.hanthink.mp.model.MpTrialPlanModel;
import com.hanthink.mp.model.MpTrialPlanModelImport;
import com.hanthink.pup.util.PupUtil;
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
 * 描述：新车型计划维护 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpTrialPlan")
public class MpTrialPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpTrialPlanController.class);
	
	@Resource
	MpTrialPlanManager mpTrialPlanManager;
	  		
	/**
     * 分页查询零件新车型计划维护
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse, MpTrialPlanModel model) {
    	String resultMsg=null;
    	try {
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        /**
         * 判断pageList是否为空
         */
        List<MpTrialPlanModel> pageList;
        if (!PupUtil.isAllFieldNull(model)) {
        	pageList  = (PageList<MpTrialPlanModel>) mpTrialPlanManager.queryMpTrialPlanForPage(model, p);
		}
        else {
        	/**
        	 * 没有数据返回空行
        	 */
        	pageList = new ArrayList<>();
        }   
        return new PageJson(pageList);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
    /**
	 * 删除未订购数据
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("removeByCalStatus")
	public void removeByCalStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			List<String> listIds =   mpTrialPlanManager.querySortIdAndLogByCalStatus();
			/**
			 * 此处id仅仅只是用于记录日志，依旧根据未订购状态来删除
			 */
			if (listIds.size() == 0) {
				message = new ResultMessage(ResultMessage.FAIL, "没有未订购数据");
			} else {
				mpTrialPlanManager.removeAndLogByCalStatus(listIds, RequestUtil.getIpAddr(request));
				message = new ResultMessage(ResultMessage.SUCCESS, "删除未订购数据成功");
			}
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除未订购数据失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 获取新车型计划
	 * <p>return: void</p>  
	 * <p>Description: MpTrialPlanController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	@RequestMapping("getMpTrialPlan")
	public void getMpTrialPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = mpTrialPlanManager.getMpTrialPlan(ContextUtil.getCurrentUser().getCurFactoryCode());
			if (0 == count) {
				message = new ResultMessage(ResultMessage.SUCCESS, "获取新车型计划成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "获取新车型计划失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "获取新车型计划失败");
		}
 		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 下载导出MpTrialPlan数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpTrialPlanModel")
	public void downloadMpTrialPlanModel(HttpServletRequest request,HttpServletResponse response
			,MpTrialPlanModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpTrialPlanModel> list =  mpTrialPlanManager.queryMpTrialPlanByKey(model);
		/**
		 * 如果查询记录超过100000条则报错
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
		
		String[] headers = {"生产排序号","总装下线日期","焊装上线时间", "车型", "生产阶段", "订单号","计算状态"};
		String[] columns = {"sortId","afoffDateStr","weonTimeStr", "carType", "proPhase", "orderNo","codeValueName"};
		int[] widths = {80, 80, 80, 80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "新车型计划"+df.format(new Date()), list, headers, widths, columns);
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
	@RequestMapping("importMpTrialPlanModel")
	public void importMpTrialPlanModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_PLANMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				mpTrialPlanManager.deleteMpTrialPlanImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MP_TRIAL_PLANMODEL_IMPORT_UUID, uuid);
			
			Map<String,Object> resultMap = mpTrialPlanManager.importMpTrialPlanModel(file, uuid, RequestUtil.getIpAddr(request),user);
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
		String uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_PLANMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<MpTrialPlanModelImport> pageList = (PageList<MpTrialPlanModelImport>) mpTrialPlanManager.queryMpTrialPlanImportTempData(paramMap, page);
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
			String uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_PLANMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			mpTrialPlanManager.insertMpTrialPlanImportData(paramMap,RequestUtil.getIpAddr(request));
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
		String uuid = (String)session.getAttribute(SessionKey.MP_TRIAL_PLANMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<MpTrialPlanModelImport> list = mpTrialPlanManager.queryMpTrialPlanImportTempDataForExport(paramMap);
		
		String[] headers = {"生产排序号", "总装下线日期", "车型", "生产阶段","订单号",
				"校验结果","导入状态", "校验信息"};
		String[] columns = {"sortId", "afoffDate", "carType", "proPhase","orderNo",
				"codeValueNameB","codeValueNameC", "checkInfo"};
		int[] widths = {80,80,80,80,80,
				50,50, 360};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "新车型计划"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
	
}

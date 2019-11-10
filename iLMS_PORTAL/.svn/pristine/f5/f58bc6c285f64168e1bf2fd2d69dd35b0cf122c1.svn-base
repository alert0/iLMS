package com.hanthink.pup.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.pup.manager.PupProPlanManager;
import com.hanthink.pup.model.PupProPlanPageModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hanthink.pup.model.PupProPlanModel;
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
 * <pre> 
 * 描述：生产计划维护控制器类
 * 构建组：x5-bpmx-platform
 * 作者:zmj
 * 日期:2018-09-13 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pup/proplan")
public class PupProPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupProPlanController.class);
	
	@Resource
	PupProPlanManager planManager;
	
	/**
	 * 按条件查询数据
	 * @param req 请求
	 * @param plan 页面参数VO实体
	 * @return 页面数据
	 * @throws Exception
	 */
	@RequestMapping("/listJson")
	public @ResponseBody PageJson queryProPlanForPage(HttpServletRequest req,PupProPlanPageModel plan) throws Exception{
		QueryFilter qFilter = getQueryFilter(req);
		Page page = qFilter.getPage();
		HttpSession session = req.getSession();
		session.setAttribute(SessionKey.PUP_PROPLAN_QUERYFILTER, plan);
		
		PageList<PupProPlanModel> list = planManager.queryProPlanForPage(plan,page);
		
		return new PageJson(list);
	}
	
	/**
	 * 导出查询结果
	 * @param request
	 * @param response
	 * @author zmj	
	 * @throws Exception 
	 * @DATE	2018年9月3日 下午6:15:49
	 */
	@RequestMapping("/exportPlan")
	public void downloadPlanModel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			PupProPlanPageModel plan = (PupProPlanPageModel) session.getAttribute(SessionKey.PUP_PROPLAN_QUERYFILTER);
			List<PupProPlanModel> list =  planManager.queryPupPlanBykey(plan);
			/**
			 * 如果查询记录超过100000条则报错
			 */
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000);
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			
			String[] headers = {"订单顺序号","生产订单号","车型", "标识", "混合车型排序","分车型排序","计划下线日期","计划下线时间"};
			String[] columns = {"sortId","orderNo","carType", "mark", "mixSortId", "singleSortId","afoffDate","afoffTime"};
			int[] widths = {80,120, 80, 120, 80, 80, 120, 120};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "生产计划维护数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				ExcelUtil.exportException(e, request, response);
			}
		}
	
	/**
	 * 
	 *@Description 计划订单导入控制器
	 *@author zmj
	 *@param request
	 *@param response
	 *@param file
	 *@throws IOException
	 *@date 2018年9月14日 下午12:14:54
	 */
	@RequestMapping("/importProPlanModel")
	public void importProPlan(HttpServletRequest request,HttpServletResponse response,@RequestParam("file")MultipartFile file) throws IOException {
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.PUP_PROPLAN_IMPORT_UUID);
			
			if(StringUtil.isNotEmpty(uuid)){
				planManager.deleteProPlanImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			
			session.setAttribute(SessionKey.PUP_PROPLAN_IMPORT_UUID, uuid);
			
			Map<String,Object> rtn = planManager.importProPlanModel(file, uuid, RequestUtil.getIpAddr(request),user);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
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
	 * 功能描述:查询Excel导入数据相关信息
	 * 作者:zmj
	 * 日期:2018年9月15日下午10:52:43
	 * 版权:汉思信息技术有限公司
	 */
	@RequestMapping("/queryImportInformation")
	public @ResponseBody PageJson queryImportInformationForPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, String> paramMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		paramMap.put("uuid", (String) session.getAttribute(SessionKey.PUP_PROPLAN_IMPORT_UUID));
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupProPlanModel> pageList = planManager.queryImportInformationForPage(paramMap,page);
		return new PageJson(pageList);
	}
	/**
	 * 确定导入，将临时表数据写入到正式表
	 *@param request
	 *@param response
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月17日 上午10:05:43
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResultMessage rMessage = null;
		try {
			Map<String, String> paramMap = new HashMap<String,String>();
			HttpSession session = request.getSession();
			paramMap.put("uuid", (String) session.getAttribute(SessionKey.PUP_PROPLAN_IMPORT_UUID));
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			planManager.insertTempDataToPlanTable(paramMap);
			rMessage = new ResultMessage(ResultMessage.SUCCESS,"执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			rMessage = new ResultMessage(ResultMessage.ERROR,"执行失败");
		}
		writeResultMessage(response.getWriter(), rMessage);
	}
	/**
	 * 导出Excel导入的数据(所有数据/有错误的数据)
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月15日
	 */
	@RequestMapping("/exportNeedToModifiedData")
	public void exportNeedToModifiedData(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, String> paramMap = new HashMap<>();
		HttpSession session = request.getSession();
		paramMap.put("uuid", (String) session.getAttribute(SessionKey.PUP_PROPLAN_IMPORT_UUID));
		Page page = new DefaultPage(1, 100000);
		PageList<PupProPlanModel> list = planManager.queryImportInformationForPage(paramMap, page);
		String[] headers = {"生产订单排序号","订单号","车型", "标识", "混合车型排序","分车型排序","计划下线日期", "计划下线时间","校验信息"};
		String[] columns = {"sortId","orderNo","carType", "mark", "mixSortId", "singleSortId","afoffDate","afoffTime","checkInfo"};
		int[] widths = {80, 120, 80, 120, 80, 80, 80, 80, 300};
		
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "生产计划导入需修改数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
	}
	
	@RequestMapping("/getProPlan")
	public void getProPlan(HttpServletRequest request,HttpServletResponse response,PupProPlanPageModel model)throws Exception {
		String resultMsg = null;
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setOpeId(ContextUtil.getCurrentUser().getAccount());
		try {
			planManager.getProPlan(model);
			resultMsg = "1";
		}catch (Exception e) {
			e.printStackTrace();
			resultMsg = "2";
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
	}
}

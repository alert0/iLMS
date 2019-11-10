package com.hanthink.pup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hanthink.pup.manager.PupLockPlanManager;
import com.hanthink.pup.model.PupLockPlanModel;
import com.hanthink.pup.model.PupLockPlanPageModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
 * <pre> 
 * 功能描述:锁定取货计划控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月25日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/pup/lockPlan")
@Controller
public class PupLockPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupLockPlanController.class);
	
	@Resource
	PupLockPlanManager lockPlanManager;
	/**
	 * 条件查询生产计划控制器
	 * @param request 请求
	 * @param response	响应
	 * @param model 请求参数实体
	 * @return 查询的页面数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@RequestMapping("/queryPlanForPage")
	public @ResponseBody PageJson queryLockPlanForPage(HttpServletRequest request,HttpServletResponse response,
									PupLockPlanPageModel model)throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute(SessionKey.PUP_LOCKPLAN_QYERFILTER, model);
		Page page = getQueryFilter(request).getPage();
		PageList<PupLockPlanModel> pageList = lockPlanManager.queryLockPlanForPage(model,page);
		return new PageJson(pageList);
	}
	/**
	 * 批量/单个删除选中数据控制器
	 * @param request 请求
	 * @param response 响应
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@RequestMapping("/clearAll")
	public void removePageData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg = null;
		String[] ids = RequestUtil.getStringAryByStr(request, "id");
		try {
			lockPlanManager.deleteLockPlanByIds(ids);
			resultMsg = "删除成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "删除失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	/**
	 * 根据查询条件导出页面数据控制器
	 * @param request 请求
	 * @param response 响应
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	@RequestMapping("/exportQueryData")
	public void exportForExcel(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			HttpSession session = request.getSession();
			PupLockPlanPageModel model = (PupLockPlanPageModel) session.getAttribute(SessionKey.PUP_LOCKPLAN_QYERFILTER);
			List<PupLockPlanModel> list = lockPlanManager.queryLockPlanForExport(model);
			/**
			 * 如果查询记录超过10000条则报错
			 */
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"工厂代码","区域","卸货地点","订单物流模式","车型","集货线路","累计车次","合并车次","工作日",
								"当日车次","计划取货日期","计划取货时间","计划到货日期","计划到货时间","计划装配日期","计划装配时间"};
			String[] columns = {"factoryCode","area","unloadPlace","pickupType","carType","routeCode","totalNo","mergeNo","workday",
								"todayNo","pickDate","pickTime","arriveDate","arriveTime","assembleDate","assembleTime"};
			int[] widths = {80,80,80,80,80,  80,80,80,80,80,  80,80,80,80,80,  80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "锁定计划"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * Excel数据导入控制器
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月26日
	 */
	@RequestMapping("/importLockPlan")
	public void importLockPlan(HttpServletRequest request,HttpServletResponse response,
									@RequestParam("file")MultipartFile file)throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap = lockPlanManager.insertExcelDataToTale(file,RequestUtil.getIpAddr(request));
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMap.put("result", false);
			resultMap.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.FAIL);
		}
	}
	
}

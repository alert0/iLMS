package com.hanthink.pup.controller;

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
import com.hanthink.mp.controller.MpResidualController;
import com.hanthink.pup.manager.PupRouteMessageManager;
import com.hanthink.pup.model.PupRouteMessageModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;
/**
 * <pre> 
 * 功能描述:路线信息维护控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月21日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/pup/route")
@Controller
public class PupRouteMessageController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(MpResidualController.class);

	@Resource
	PupRouteMessageManager routeManager;
	/**
	 * 条件分页查询数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/listRouteMessage")
	public @ResponseBody PageJson queryRouteMessageForPage(HttpServletRequest request, HttpServletResponse response,
			PupRouteMessageModel model) throws Exception {
		
		Page page = getQueryFilter(request).getPage();

		PageList<PupRouteMessageModel> pageList = (PageList<PupRouteMessageModel>) routeManager.queryRouteMessageForPage(model, page);
		
		return new PageJson(pageList);
	}
	/**
	 * 加载数据字典选项
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 *//*
	@RequestMapping("/getBatch")
	public @ResponseBody Object getBacth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<DictVO> models = routeManager.getBatches();
			return new PageJson(models);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			return null;
		}
	}*/
	/**
	 * 导出数据查询控制器类
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/exportQueryData")
	public void exportQueryData(HttpServletRequest request, HttpServletResponse response,
									PupRouteMessageModel model) throws Exception {
		try {
			
			List<PupRouteMessageModel> list = routeManager.queryRouteMessageForExport(model);
			/**
			 * 如果查询记录超过10000条则报错
			 */
			if (0 == list.size()) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); // 获取系统所允许的最大导出数量
			if (list.size() > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = { "区域", "路线区分", "卸货地点", "供应商代码", "出货地代码", 
					  			 "供应商名称", "计算队列", "车型", "组单台套数", "仓库代码", 
					  			 "订单物理模式", "集货线路", "线路名称", "取货车辆", "最大工深", 
					  			 "返空站台", "提前台套数", "最早到货时间", "特殊到货时间", "运输时长(H)", 
					  			 "A班收货", "B班收货", "用户ID", "内物流管理员", "合并基准", 
					  			 "每周固定取货日", "供应商出货日期", "是否批次取货", "备注", "起始SortId", 
					  			 "发车时长" };
			String[] columns = { "area", "routeDist", "unloadPlace", "supplierNo", "supFactory", 
						 		 "supplierName", "unloadPort", "carType", "supCalNum", "wareCode", 
						 		 "pickupType", "routeCode", "routeName", "pickupCar", "locDepth", 
						 		 "retEmptyPlatform", "advanceArrNum", "firstArriveTime", "speArriveTime", "transTime",
						 		 "recShiftA", "recShiftB", "wwlManager", "nwlManager", "mergeNum", 
						 		 "pickCycle", "supOutTime", "excelBatch", "remark","firstSortId", 
						 		 "departTimePoint" };
			int[] widths = { 80, 80, 80, 80, 80, 
							 80, 80, 80, 80, 80, 
							 80, 80, 80, 80, 80, 
							 80, 80, 100, 100, 80, 
							 80, 80, 80, 80, 80, 
							 80, 100, 80, 80, 80,
							 80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "线路信息维护查询数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers,
					widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 根据数据ID查询数据详情控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/getJson")
	public @ResponseBody PupRouteMessageModel getJsonByRouteId(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String id = RequestUtil.getString(request, "id");
		try {
			return routeManager.queryRouteMessageById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 单个/多条记录删除控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/removeRouteMessages")
	public void removeRouteMessagesByIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String[] ids = RequestUtil.getStringAryByStr(request, "id");
		try {
			routeManager.removeRouteMessagesByIds(ids,RequestUtil.getIpAddr(request));
			resultMsg = "删除数据成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 * Excel数据导入控制器
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/importRouteMessage")
	public void importRouteMessage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) throws Exception {
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				routeManager.deleteTempRouteMessageByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID, uuid);

			rtn = routeManager.importRouteMessageToTempTable(file, uuid,
					RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if ((boolean) rtn.get("result")) {
				writeResultMessage(response.getWriter(), "文件导入成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			rtn.put("result", false);
			rtn.put("console", e.getMessage());
			writeResultMessage(response.getWriter(), (String) rtn.get("console"), ResultMessage.FAIL);
		}
	}
	/**
	 * 查询导入数据详情控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/queryImportRouteMessage")
	public @ResponseBody PageJson queryImportModelForPage(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();

		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupRouteMessageModel> pageList = routeManager.queryImportModelForPage(paramMap,page);
		return new PageJson(pageList);
	}
	/**
	 * 导出导入数据详情控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月21日
	 */
	@RequestMapping("/exportTempForExcel")
	public void exportTempForExcel(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		
		paramMap.put("uuid", uuid);
		try {
			List<PupRouteMessageModel> list = routeManager.queryImportModelForExport(paramMap);
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
			String[] headers = { "区域", "路线区分", "卸货地点", "供应商代码", "出货地代码", "返空站台", "计算队列", "车型区分", "对应仓库", "取货区分",
					"路线代码", "线路名称", "取货车辆", "组单台套数", "最大工深", "厂内提前台套数", "最早到货时间", "发车时长", "特殊到货时间","运输时间",
					"A班收货", "B班收货", "物流管理员","内物流管理员", "合并基准", "取货周期", "供应商出货日期", "是否批次取货", "起始SortId", 
					"是否通过校验","校验信息"};
			String[] columns = {"area","routeDist","unloadPlace","supplierNo","supFactory","retEmptyPlatform",
					"unloadPort","carType","wareCode","pickupType","routeCode","routeName","pickupCar","supCalNum","locDepth",
					"advanceArrNum","firstArriveTime","departTimePoint","speArriveTime","transTime","recShiftA","recShiftB",
					"wwlManager","nwlManager","mergeNum","pickCycle","supOutTime","excelBatch","startSortId","checkResult","checkInfo"};
			int[] widths = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
							100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
							100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "路线信息维护数据详情"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 确认导入，将数据写入正式数据表控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ResultMessage resultMsg = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			
			routeManager.insertImportDataToFormalTable(paramMap,RequestUtil.getIpAddr(request));
			resultMsg = new ResultMessage(ResultMessage.SUCCESS, "执行数据写入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), resultMsg);
	}
	/**
	 * 修改线路信息控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/saveEdit")
	public void saveEdit(HttpServletRequest request,HttpServletResponse response,
								@RequestBody PupRouteMessageModel model)throws Exception {
		String resultMsg = null;
		String id = model.getId();
		
		try {
			model.setOpeIpAddr(RequestUtil.getIpAddr(request));
			routeManager.updateRouteMessageById(id,model);
			resultMsg = "修改数据成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "修改数据失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
}

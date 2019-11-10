package com.hanthink.pup.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.pup.manager.PupRouteMessageManager;
import com.hanthink.pup.model.PupRouteMessageModel;
import com.hanthink.pup.model.PupRouteMessagePageModel;
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

	private static Logger log = LoggerFactory.getLogger(PupRouteMessageController.class);

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
			PupRouteMessagePageModel model) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute(SessionKey.PUP_ROUTEMESSAGE_QYERFILTER, model);
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
	 */
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
	}
	/**
	 * 导出数据查询控制器类
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/exportQueryData")
	public void exportQueryData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			PupRouteMessagePageModel model = (PupRouteMessagePageModel) session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_QYERFILTER);
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
			String[] headers = { "区域", "路线区分", "卸货地点", "供应商代码", "出货地", "供应商名称", "计算队列", "车型", "台套数", "仓库代码", "订单物理模式",
					"集货线路", "线路名称", "最早配送地址", "返空站台", "提前台套数", "最早到货时间", "特殊到货时间", "运输时间", "A班收货", "B班收货", "用户ID",
					"内物流管理员", "合并基准", "取货周期", "供应商出货日期", "是否批次取货", "起始SortId", "发车时间基准" };
			String[] columns = { "area", "routeDist", "unloadPlace", "supplierNo", "supFactory", "supplierName",
					"unloadPort", "carType", "supCalNum", "wareCode", "pickupType", "routeCode", "routeName",
					"locDepth", "retEmptyPlatform", "advanceArrNum", "firstArriveTime", "speArriveTime", "transTime",
					"recShiftA", "recShiftB", "wwlManager", "nwlManager", "mergeNum", "pickCycle", "supOutTime",
					"batch", "firstSortId", "departTimePoint" };
			int[] widths = { 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
					80, 80, 80, 80, 80, 80 };
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
			routeManager.removeRouteMessagesByIds(ids);
			resultMsg = "删除成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "删除失败";
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
		String uuid = null;
		try {
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID);
			if (StringUtil.isNotEmpty(uuid)) {
				routeManager.deleteTempRouteMessageByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID, uuid);

			Map<String, Object> resultMap = routeManager.importRouteMessageToTempTable(file, uuid,
					RequestUtil.getIpAddr(request));
			if ((boolean) resultMap.get("result")) {
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(),
						ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(),
						ResultMessage.FAIL);
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
		paramMap.put("uuid", (String) session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID));
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupRouteMessageModel> pageList = routeManager.queryImportModelForPage(paramMap,page);
		return new PageJson(pageList);
	}
	/**
	 * 生成等间距时刻表控制器
	 * @return
	 * @author zmj
	 * @date 2018年9月21日
	 */
	@RequestMapping("/schedule")
	public @ResponseBody PageJson getTimeList(){
		String[] comboVal = new String[96];
		List<DictVO> list = new ArrayList<>();
		int hh = 0;
		String valH = "";
		int id = 0;
		String inputV = null;
		for (int h = 0; h < 24; h++) {
			if (hh < 10) {
				valH = "0" + hh;
			} else if (hh >= 24) {
				valH = "00";
			} else {
				valH = ""+hh;
			}
			hh++;
			String valM = "";
			int mm = 0;
			for (int m = 0; m < 4; m++) {
				if (mm >= 60) {
					valM = "00";
				}else if(mm < 10){
					valM = "0"+mm;
				}else {
					valM = ""+mm;
				}
				mm = mm + 15;
				inputV = valH + ":" + valM + ":00";
				comboVal[id] = inputV;
				id++;
			}
		}
		
		for(int i = 0;i < comboVal.length;i++) {
			DictVO timeList = new DictVO();
			timeList.setValueKey(comboVal[i]);
			timeList.setValueName(comboVal[i]);
			list.add(timeList);
		}

		return new PageJson(list);
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
			paramMap.put("uuid", (String)session.getAttribute(SessionKey.PUP_ROUTEMESSAGE_IMPORT_UUID));
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			
			routeManager.insertImportDataToFormalTable(paramMap,RequestUtil.getIpAddr(request));
			resultMsg = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), resultMsg);
	}
	
	@RequestMapping("/saveEdit")
	public void saveEdit(HttpServletRequest request,HttpServletResponse response,
			PupRouteMessageModel model)throws Exception {
		String resultMsg = null;
		String id = RequestUtil.getString(request, "id");
		
		try {
			routeManager.updateRouteMessageById(id,model);
			resultMsg = "修改成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "修改失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
}

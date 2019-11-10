package com.hanthink.pup.controller;

import java.io.IOException;
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
import com.hanthink.pup.manager.PupPickTimeManager;
import com.hanthink.pup.model.PupPickTimeModel;
import com.hanthink.pup.model.PupTimeListModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
* <pre> 
* 描述：固定取货时间维护控制器类
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-17
* 版权：汉思信息技术有限公司
* </pre>
*/
@RequestMapping("/pup/pickupTime")
@Controller
public class PupPickTimeController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupPickTimeController.class);
	
	@Resource
	PupPickTimeManager pickTimeManager;
	/**
	 * 查询固定取货时间维护详细信息
	 *@param request
	 *@param response
	 *@param model
	 *@return
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月17日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryPickupTimeForPage(HttpServletRequest request,HttpServletResponse response,PupPickTimeModel model) throws Exception{
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupPickTimeModel> pageList = pickTimeManager.queryPickupTimeForPage(model,page);
		
		return new PageJson(pageList);
	}

	/**
	 * 保存新增、修改取货信息的控制器类
	 *@param request
	 *@param response
	 *@param model
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 上午11:58:13
	 */
	@RequestMapping("/editPickTime")
	public void savePickTime(HttpServletRequest request,HttpServletResponse response,
							@RequestBody PupPickTimeModel model) throws Exception{		
		String resultMsg = null;
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				pickTimeManager.savePickTime(model);
				resultMsg = "添加数据成功";
			}else {
				pickTimeManager.updatePickTime(model);
				resultMsg = "修改数据成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 删除选中的数据数据
	 *@param request
	 *@param response
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月22日 上午8:21:55
	 */
	@RequestMapping("/remove")
	public void removeByRouteCodes(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String resultMsg = null;
		try {
			String[] ids = RequestUtil.getStringAryByStr(request, "id");
			
			pickTimeManager.removeByRouteCodes(ids,RequestUtil.getIpAddr(request));
			resultMsg = "删除固定取货时间信息成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "删除固定取货时间信息失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	/**
	 * 页面数据导出控制器类
	 *@param request
	 *@param response
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 上午10:54:03
	 */
	@RequestMapping("/downloadPickTimeModel")
	public void downloadPickTimeModel(HttpServletRequest request,HttpServletResponse response,
									PupPickTimeModel model)throws Exception {
		try {
			List<PupPickTimeModel> list = pickTimeManager.queryPickupTimeForExport(model);
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
			String[] headers = {"集货线路","当日车次","取货时间","到货时间"};
			String[] columns = {"routeCode","todayNo","pickTime","arriveTime"};
			int[] widths = {120,120,120,120};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "固定取货时间数据信息"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 查询导入的临时数据
	 *@param request
	 *@param response
	 *@return
	 *@author zmj
	 * @throws Exception 
	 *@date 2018年9月20日 上午11:20:13
	 */
	@RequestMapping("/ListTempPickTime")
	public @ResponseBody PageJson queryTempPickTimeData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, String> param = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.PUP_PICKTTIME_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		param.put("uuid", uuid);
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<PupPickTimeModel> pageList = (PageList<PupPickTimeModel>) pickTimeManager.queryPickTimeForImport(param,page);
		return new PageJson(pageList);
	}
	/**
	 * 导入数据到临时数据表
	 *@param file
	 *@param request
	 *@param response
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月20日 下午5:08:30
	 */
	@RequestMapping("/pickTimeExcelImport")
	public void importPickTimeModel(HttpServletRequest request,HttpServletResponse response,@RequestParam("file")MultipartFile file) throws IOException {
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.PUP_PICKTTIME_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				pickTimeManager.deletePickTimeTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.PUP_PICKTTIME_IMPORT_UUID, uuid);
			
			rtn = pickTimeManager.importDataToTempTable(file, uuid, RequestUtil.getIpAddr(request),user);
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "文件导入成功", "文件导入成功", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			rtn.put("result", false);
			rtn.put("console", e.getMessage());
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 生成固定差值的时间
	 *@return hh:mm:ss
	 *@author zmj
	 *@date 2018年9月23日 下午5:32:00
	 */
	@RequestMapping("/schedule")
	public @ResponseBody List<PupTimeListModel> getTimeList(){
		String[] comboVal = new String[96];
		List<PupTimeListModel> list = new ArrayList<>();
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
			PupTimeListModel timeList = new PupTimeListModel(comboVal[i], comboVal[i]);
			list.add(timeList);
		}

		return list;
	}
	
	/**
	 * 导出有误数据以便修改
	 *@param request
	 *@param response
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午8:02:12
	 */
	@RequestMapping("/exportTempData")
	public void downloadPickTimeTempData(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.PUP_PICKTTIME_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			List<PupPickTimeModel> list = pickTimeManager.queryPickupTimeTempDataForExport(paramMap);
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
			String[] headers = {"集货线路","当日车次","取货时间","到货时间","导入状态","校验结果","校验信息"};
			String[] columns = {"routeCode","todayNo","pickTime","arriveTime","excelImportStatus","excelCheckResult","checkInfo"};
			int[] widths = {120,120,120,120,80,80,180};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "固定取货时间维护数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 将临时表数据写入正式表
	 *@param request
	 *@param response
	 *@throws Exception
	 *@author zmj
	 *@date 2018年9月23日 下午10:08:17
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.PUP_PICKTTIME_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);

			pickTimeManager.insertImportDataToFormalTable(paramMap,RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行数据写入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}
}

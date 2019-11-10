package com.hanthink.sw.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.util.PupUtil;
import com.hanthink.sw.manager.SwVentureOrderMananger;
import com.hanthink.sw.model.SwVentureOrderModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
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
 * 功能描述: 合资车订单导入控制器
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2019年8月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sw/swVentureOrder")
public class SwVentureOrderContoller extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(SwVentureOrderContoller.class);
	
	@Resource
	private SwVentureOrderMananger orderManager;
	
	/**
	 * 订单界面数据查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author zmj
	 * @date 2019年8月17日
	 */
	@RequestMapping("/queryVentureOrder")
	public @ResponseBody PageJson queryVentureOrderForPage(HttpServletRequest request, HttpServletResponse response,
								SwVentureOrderModel model) {
		Page page = getQueryFilter(request).getPage();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setUserType(user.getUserType());
		model.setUserId(user.getUserId());
		PageList<SwVentureOrderModel> pageList = orderManager.queryVentureOrderForPage(model,page);
		return new PageJson(pageList);
	}
	/**
	 * 订单界面数据导出
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月24日
	 */
	@RequestMapping("/queryVentureOrderForExport")
	public void queryVentureOrderForExport(HttpServletRequest request, HttpServletResponse response,
			SwVentureOrderModel model)throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		model.setUserType(user.getUserType());
		model.setUserId(user.getUserId());
		model.setFactoryCode(user.getCurFactoryCode());
		List<SwVentureOrderModel> list = orderManager.queryVentureOrderForExport(model);
		
		if (0 == list.size()) {
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000);
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"工厂","采购订单","采购订单行号","订单类型","卸货口","供应商代码","出货地代码","供应商名称","订单号",
							"零件号","简号","零件名称","收容数","订单箱数","订单个数","拣货地址","配送地址","到货批次","订单日期",
							"供应商出货时间","到达时间","供应商线路名","供应商便次","GBL仓库出货时间","GBL到达时间","GBL路线名",
							"GBL趟次","纳入日","创建时间"};
		String[] columns = {"jvPlace","purchseNo","rowNo","excelOrderType","unloadPort","supplierNo","supFactory","supplierName","orderNo",
							"partNo","partShortNo","partName","standardPackage","orderBox","orderQty","storage","location","arriveBatch","orderDate",
							"supShipTime","supArriveTime","supRouteName","supCarBatch","gblShipTime","gblArriveTime","gblRouteName",
							"gblCarBatch","arriveDate","creationTime"};
		int[] widths = {80, 100, 80, 80, 80,   80, 80, 120, 100, 100,
						80, 120, 80, 80, 80,   80, 80, 80, 100, 100,
						100, 100, 80, 100, 100, 100, 100, 100, 100};
		
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "合资车订单数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
	}
	
	/**
	 * 
	 * @Description: 合资车周预测导入临时表
	 * @param @param file
	 * @param @param request
	 * @param @param response
	 * @return void
	 * @throws Exception
	 * @throws           @author luoxq
	 * @date 2019年8月2日 下午3:11:48
	 */
	@RequestMapping("importModel")
	public void importModel(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		String uuid = null;
		try {
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				orderManager.deleteImportTempDataByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replace("-", ""); // 生成随机UUID
			}
			Map<String, Object> rtn = orderManager.importModel(file, uuid, RequestUtil.getIpAddr(request), user);
			rtn.put("uuid", uuid);
			if ((Boolean) rtn.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败", (String) rtn.get("console"), JSONObject.fromObject(rtn),
						ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception(e.toString());
		}
	}

	/**
	 * 
	 * @Description: 分页查询导入临时表数据
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception
	 * @return PageJson
	 * @throws @author luoxq
	 * @date 2019年8月14日 上午10:29:30
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));

		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<SwVentureOrderModel> pageList = (PageList<SwVentureOrderModel>) orderManager
				.queryImportTempData(paramMap, page);
		return new PageJson(pageList);
	}

	/**
	 * 导出导入校验结果数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			String uuid = RequestUtil.getString(request, "uuid");
			paramMap.put("uuid", uuid);
			List<SwVentureOrderModel> list = orderManager.queryTempDataForExport(paramMap);

			String[] headers = { "工厂", "版本号", "零件号", "需求量", "下线日期", "提前取货时间(天)", "生产阶段", "供应商代码", "车型",
					 "校验结果", "导入状态", "校验信息" };
			String[] columns = { "jvPlace", "jvVersion", "partNo", "orderQty", "planDelivery", "advanceTime",
					"phase", "supplierNo", "modelCode",  "checkResultStr", "importStatusStr", "checkInfo" };
			int[] widths = { 80, 100, 150, 80, 180, 150, 100, 100,  100, 100, 100, 100 };
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "合资车预测导入校验结果数据" + df.format(new Date()),
					list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}

	/**
	 * 确认导入
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String uuid = RequestUtil.getString(request, "uuid");
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
			/**
			 * 临时表数据写入正式表
			 */
			orderManager.insertImportData(paramMap, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 删除所有未订购数据
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月24日
	 */
	@RequestMapping("/deleteAllPuchaeIsNull")
	public void deleteAllPuchaeIsNull(HttpServletRequest request, HttpServletResponse response)throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userType", user.getUserType());
		paramMap.put("userId", user.getUserId());
		paramMap.put("factoryCode", user.getCurFactoryCode());
		try {
			orderManager.deleteAllPuchaeIsNull(paramMap);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, "删除未订购数据成功"));
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.FAIL, "删除未订购数据失败"));
		}
	}
	/**
	 * 订单审核及发布
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author zmj
	 * @date 2019年8月24日
	 */
	@RequestMapping("/orederRelease")
	public void orederRelease(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IUser user = ContextUtil.getCurrentUser();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("factoryCode", user.getCurFactoryCode());
		paramMap.put("checkUser", user.getAccount());
		paramMap.put("flag", '0');
		try {
			orderManager.orederRelease(paramMap);
			if (String.valueOf(paramMap.get("flag")).equals("1")) {
				writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, "订单发布成功"));
			}else {
				writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.FAIL, "系统错误,请联系管理员"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.FAIL, "系统错误,请联系管理员"));
		}
	}
}

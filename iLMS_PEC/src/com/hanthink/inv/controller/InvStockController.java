package com.hanthink.inv.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.inv.manager.InvStockManager;
import com.hanthink.inv.model.InvStockModel;
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

@RequestMapping("/inv/stock")
@Controller
public class InvStockController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvStockController.class);
	
	@Resource
	private InvStockManager stockManager;
	/**
	 * 分页数据查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("/queryStockForPage")
	public @ResponseBody PageJson queryStockForPage(HttpServletRequest request,HttpServletResponse response,
									InvStockModel model)throws Exception{
		Page page = getQueryFilter(request).getPage();

		PageList<InvStockModel> pageList = stockManager.queryStockForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 加载单条数据详情控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("/getJson")
	public @ResponseBody InvStockModel queryStockById(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String id = RequestUtil.getString(request, "id");
		InvStockModel model = stockManager.queryStockById(id);
		return model;
	}
	/**
	 * 修改安全库存数控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("/saveStock")
	public void updateForSafeStockNum(HttpServletRequest request,HttpServletResponse response,
									@RequestBody InvStockModel model) throws Exception {
		String resultMsg = null;
		try {
			stockManager.updateForSafeStockNum(model,RequestUtil.getIpAddr(request));
			resultMsg = "修改安全库存数据成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	/**
	 * 查询数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@RequestMapping("/exportForExcel")
	public void queryExportDataForExcel(HttpServletRequest request,HttpServletResponse response,
										InvStockModel model)throws Exception {
		
		List<InvStockModel> list = stockManager.queryExportDataForExcel(model);
		
		try {
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"仓库名称","零件号","简号","零件名称","库位","收容数","最小库存(数量)","最小库存(箱)","最大库存(数量)","最大库存(箱)","在库数量","在库数量(箱)","调整库存备注"};
			String[] columns = {"wareName","partNo","partShortNo","partName","baseLocation","standardPac","minStock","safeStock","maxStockNom","maxStock","realStock","stock","adjRemark"};
			int[] widths = {100,100,100, 100,100,100, 100,100,100, 100,100,100, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "库存数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 批量修改库存数据
	 * @param request
	 * @param response
	 * @param list
	 * @author zmj
	 * @date 2019年2月18日
	 */
	@RequestMapping("/batchUpdate")
	public void batchUpdateStock(HttpServletRequest request,HttpServletResponse response,
							@RequestBody List<InvStockModel> list) throws Exception{
		String maxStock = RequestUtil.getString(request, "maxStock");
		String minStock = RequestUtil.getString(request, "minStock");
		String ipAddr = RequestUtil.getIpAddr(request);
		String resultMsg = null;
		try {
			stockManager.batchUpdateStock(list,maxStock,minStock,ipAddr);
			resultMsg = "数据修改成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage().toString();
			writeResultMessage(response.getWriter(),resultMsg,resultMsg,ResultMessage.FAIL);
		}
	}
	
	@RequestMapping("/importStock")
	public void importStock(HttpServletRequest request,HttpServletResponse response,@RequestParam("file")MultipartFile file)throws Exception {
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			String uuid = RequestUtil.getString(request, "uuid");
			if (StringUtil.isNotEmpty(uuid)) {
				stockManager.delTempDataByUUID(uuid);
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			rtn = stockManager.importStock(file, uuid, RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "数据读取成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			rtn.put("console", e.getMessage());
			writeResultMessage(response.getWriter(), (String) rtn.get("console"), ResultMessage.FAIL);
		}
	}
	
	@RequestMapping("/queryImportTemp")
	public @ResponseBody PageJson queryImportTemp(HttpServletRequest request,HttpServletResponse response)throws Exception {
		
		Map<String, String> paramMap = new HashMap<>();
		
		String	uuid = RequestUtil.getString(request, "uuid");
		
		paramMap.put("uuid", uuid);
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvStockModel> pageList = stockManager.queryImportTemp(paramMap, page);
		
		return new PageJson(pageList);
	}
	
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ResultMessage rMessage = null;
		Map<String, String> paramMap = new HashMap<String,String>();
		try {
			String uuid = RequestUtil.getString(request, "uuid");
			paramMap.put("uuid", uuid) ;
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			paramMap.put("ipAddr", RequestUtil.getIpAddr(request));
			
			stockManager.insertTempDataTpFromal(paramMap);
			rMessage = new ResultMessage(ResultMessage.SUCCESS,"执行数据写入成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			rMessage = new ResultMessage(ResultMessage.ERROR,"请检查数据是否正确");
		}
		
		writeResultMessage(response.getWriter(), rMessage);
	}
	
	@RequestMapping("/exportImportData")
	public void exportImportData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		String uuid = RequestUtil.getString(request, "uuid");
		paramMap.put("uuid", uuid);
		try {
			List<InvStockModel> list = stockManager.exportImportData(paramMap);
			
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"出库仓库代码","入库仓库代码","零件号","最小库存","最大库存","库存差异","备注信息","导入状态","校验结果","校验信息"};
			String[] columns = {"fromDepotNo","toDepotNo","partNo","minStock","maxStock","stock","adjRemark","excelImportStatus","excelCheckResult","checkInfo"};
			int[] widths = {80,80,120,80,80, 80,150,80,80, 150};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "库存导入管理导入数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
}

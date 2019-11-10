package com.hanthink.jit.controller;

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
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.jit.manager.JitInvenCompManager;
import com.hanthink.jit.model.JitInvenCompModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
 * @ClassName: JitInvenCompController
 * @Description: 拉动库存对比
 * @author dtp
 * @date 2018年11月3日
 */
@Controller
@RequestMapping("/jit/jitInvenComp")
public class JitInvenCompController extends GenericController {
	
	private static Logger log = LoggerFactory.getLogger(JitInvenCompController.class);
	
	@Resource
	private JitInvenCompManager jitInvenCompManager;
	
	/**
	 * @Description: 拉动库存对比excel批量导入
	 * @param: @param file
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws IOException    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@RequestMapping("importJitInvenComp")
	public void importJitInvenComp(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.JIT_INVENCOMP_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)) {
				//jitInvenCompManager.deleteImportTempDataByUUID(uuid);
				//jitReckonManager.deleteImportTempDataByUUID(uuid); 
			}else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.JIT_INVENCOMP_IMPORT_UUID, uuid);
			Map<String,Object> rtn = jitInvenCompManager.importJitInvenComp(file, uuid, RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), "失败", "", JSONObject.fromObject(rtn), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * @Description: 查询导入excel数据
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@RequestMapping("queryImportTempPage")
	public @ResponseBody PageJson queryImportTempPage(HttpServletRequest request,HttpServletResponse reponse,
			@ModelAttribute("model") JitInvenCompModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInvenCompModel> pageList = jitInvenCompManager.queryImportTempPage(model, page);
		return new PageJson(pageList);	
	}
	
	/**
	 * @Description: 执行拉动库存对比计算
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月5日
	 */
	@RequestMapping("isReckon")
	public void isReckon(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInvenCompModel model) throws IOException {
		if(StringUtil.isEmpty(model.getImpUuid())) {
			writeResultMessage(response.getWriter(), "执行推算失败", ResultMessage.FAIL);
			return;
		}
		try {
			//调用存储过程等检查数据准确性
			Map<String, String> reckonParamMap = new HashMap<String, String>();
			reckonParamMap.put("impUuid", model.getImpUuid());
			reckonParamMap.put("result", "");
			reckonParamMap.put("errMsg", "");
			//2018-11-06存储未完成
			//jitInvenCompManager.isReckon(reckonParamMap);
			writeResultMessage(response.getWriter(), "执行推算成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "执行推算失败", ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * @Description: 导出拉动库存对比计算excel   
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@RequestMapping("downloadJitInvenComp")
	public void downloadJitInvenComp(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInvenCompModel model) {
		String exportFileName = "拉动库存对比推算结果" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInvenCompModel> pageList = jitInvenCompManager.queryImportTempPage(model, page);
		//判断记录是否超过系统允许数量
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<JitInvenCompModel> list = jitInvenCompManager.queryImportTempList(model);
		if(null != list) {
			String[] headers = {"推算状态", "校验信息", "车间", "信息点", "零件号",
					"简号", "零件名称", "盘点截止VIN", "下一个到货批次进度", 
					"当前已推算截止VIN", "当前已推算最新过点批次进度", "盘点数量(a)",
					"安全库存(b)", "计划到货数量(c)", "装配需求(d)", "库存差异(e=a+c-b-d)"};
			String[] columns = {"importStatus", "checkInfo", "workcenter", "planCodeDesc", "partNo",
					"partShortNo", "partName", "vin", "checkArrProSeqno", 
					"checkCalVin", "checkCalKbProSeqno", "currInventory", 
					"safetyInventory", "checkPlanQty", "checkAssemblyQty", "diff"};
			int[] widths = {100, 100, 100, 100, 150,
					100, 100, 100, 100,
					100, 100, 100,
					100, 100, 100, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			
		}
		
	}
	
	/**
	 * @Description: 拉动计划差异查询  
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	@RequestMapping("queryJitPlanDiff")
	public @ResponseBody PageJson queryJitPlanDiff(HttpServletRequest request,HttpServletResponse reponse,
			@ModelAttribute("model") JitInvenCompModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInvenCompModel> pageList = jitInvenCompManager.queryJitPlanDiff(model, page);
		return new PageJson(pageList);	
	}

	
}

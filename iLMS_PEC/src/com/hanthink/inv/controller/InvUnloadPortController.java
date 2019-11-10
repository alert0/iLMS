package com.hanthink.inv.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.inv.manager.InvUnloadPortManager;
import com.hanthink.inv.model.InvUnloadPortModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
/**
 * <pre> 
 * 功能描述:卸货口管理控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日上午
 * 版权:汉思信息技术有限公司
 * </pre>
 */
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;

@RequestMapping("/inv/unloadPort")
@Controller
public class InvUnloadPortController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(InvUnloadPortController.class);
	
	@Resource
	private InvUnloadPortManager unloadPortManager;
	/**
	 * 查询卸货口数据控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@RequestMapping("/queryUnloadPort")
	public @ResponseBody PageJson queryUnloadPortForPage(HttpServletRequest request,HttpServletResponse response,
													InvUnloadPortModel model)throws Exception{
		QueryFilter qFilter = getQueryFilter(request);
		Page page = qFilter.getPage();
		
		PageList<InvUnloadPortModel> pageList = unloadPortManager.queryUnloadPortForPage(model,page);
		
		return new PageJson(pageList);
	}
	
	/**
	 * 查询数据Excel导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@RequestMapping("/exportForExcel")
	public void exportQueryDataForExcel(HttpServletRequest request,HttpServletResponse response,
								InvUnloadPortModel model)throws Exception {
		
		List<InvUnloadPortModel> list = unloadPortManager.queryUnloadPortForExport(model);
		
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
			String[] headers = {"卸货代码","仓库名称","车间","是否有P链","物流模式","类别","物理卸货口","备注"};
			String[] columns = {"unloadPort","wareName","excelWorkCenter","excelPlFlag","excelLogisticsModel","unloadType","logicUnload","note"};
			int[] widths = {100,100,100, 100,100,120, 200,100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "卸货口数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	@RequestMapping("/removeUnloadPort")
	public void removeUnloadPort(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage resultMessage;
		try {
			String[] ids = RequestUtil.getStringAryByStr(request, "ids");
			unloadPortManager.removeUnloadPort(ids,RequestUtil.getIpAddr(request));
			resultMessage = new ResultMessage(ResultMessage.SUCCESS, "删除数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), resultMessage);
	}
	/**
	 * 卸货口新增/修改控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/saveEdit")
	public void saveUnloadPortEdit(HttpServletRequest request,HttpServletResponse response,
										@RequestBody List<Map<String,String>> list)throws Exception{
		try {
			String resultMsg = unloadPortManager.saveUnloadPortEdit(list,RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	@RequestMapping("/loadWareCode")
	public @ResponseBody List<InvUnloadPortModel> queryWareCodeLsit(HttpServletRequest request,HttpServletResponse response)throws Exception{
		return unloadPortManager.queryWareCodeLsit();
	}
	
	@RequestMapping("/queryLogicUnload")
	public @ResponseBody List<InvUnloadPortModel> queryLogicUnload(HttpServletRequest request,HttpServletResponse response)throws Exception{
		return unloadPortManager.queryLogicUnload();
	}
}

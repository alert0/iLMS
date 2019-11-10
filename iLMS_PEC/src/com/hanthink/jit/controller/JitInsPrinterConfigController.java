package com.hanthink.jit.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.jit.manager.JitInsPrinterConfigManager;
import com.hanthink.jit.model.JitInsPrinterConfigModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * @ClassName: JitInsPrinterConfigController
 * @Description: 拣货工厂与打印机配置
 * @author dtp
 * @date 2018年10月25日
 */
@Controller
@RequestMapping("/jit/jitInsPrinter")
public class JitInsPrinterConfigController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(JitInsPrinterConfigController.class);
	
	@Resource
	private JitInsPrinterConfigManager jitInsPrinterConfigManager;
	
	/**
	 * @Description: 拣货工厂与打印机关系查询 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@RequestMapping("queryJitInsPrinterConfigPage")
	public @ResponseBody PageJson queryJitInsPrinterConfigPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JitInsPrinterConfigModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInsPrinterConfigModel> pageList = jitInsPrinterConfigManager.queryJitInsPrinterConfigPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 保存拣货工厂与打印机关系
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年10月25日
	 */
	@RequestMapping("saveJitInsPrintConfig")
	public void saveJitInsPrintConfig(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInsPrinterConfigModel model) throws IOException {
		String resultMsg=null;
		String id = model.getId();
		IUser user = ContextUtil.getCurrentUser();
		try {
			if(StringUtil.isEmpty(id)) {
				DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
						getQueryFilter(request).getPage().getPageSize()));
				model.setFactoryCode(user.getCurFactoryCode());
				//判断MM_JIT_INS_PRINTER_CONFIG业务主键
				List<JitInsPrinterConfigModel> list = jitInsPrinterConfigManager.queryPkIsConflict(model);
				if(list.size() > 0) {
					writeResultMessage(response.getWriter(), "数据已存在", ResultMessage.FAIL);
					return;
				}
				model.setCreationUser(user.getAccount());
				model.setLastModifiedIp(RequestUtil.getIpAddr(request));
				jitInsPrinterConfigManager.insertJitInsPrintConfig(model);
				writeResultMessage(response.getWriter(), "保存成功", ResultMessage.SUCCESS);
			}else {
				model.setLastModifiedUser(user.getAccount());
				model.setLastModifiedIp(RequestUtil.getIpAddr(request));
				jitInsPrinterConfigManager.updateJitInsPrintConfig(model);
				writeResultMessage(response.getWriter(), "修改成功", ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * @Description:   
	 * @param:     
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月25日
	 */
	@RequestMapping("downloadJitInsPrinterConfig")
	public void downloadJitInsPrinterConfig(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitInsPrinterConfigModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "拣货工程与打印机关系" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitInsPrinterConfigModel> pageList = jitInsPrinterConfigManager.queryJitInsPrinterConfigPage(model, page);
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<JitInsPrinterConfigModel> list = jitInsPrinterConfigManager.queryJitInsPrinterConfigList(model);
		if(null != list) {
			String[] headers = {"车间", "信息点", "拣货工程",
					"打印机", "是否自动打印", "创建时间", "最后更新人",
					"最后更新时间"};
			String[] columns = {"workcenter", "planCodeDesc", "preparePerson",
					"printerIdDesc", "isAutoPrintDesc", "creationTime", "lastModifiedUser",
					"lastModifiedTime"};
			int[] widths = {80, 80, 100, 
					80, 100, 100, 100,
					100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
	}
	
	/**
	 * @Description: 删除
	 * @param: @param request
	 * @param: @param response
	 * @param: @param models    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年10月25日
	 */
	@RequestMapping("deleteJitInsPrintConfig")
	public void deleteJitInsPrintConfig(HttpServletRequest request,HttpServletResponse response,
			@RequestBody JitInsPrinterConfigModel[] models) throws IOException {
		try {
			jitInsPrinterConfigManager.deleteJitInsPrintConfig(models, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "删除成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"删除失败",ResultMessage.FAIL);
		}
		
	}
	
}

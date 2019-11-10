package com.hanthink.mp.controller;

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

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.ExcepOrderManager;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hanthink.util.excel.ExcelUtil;
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

/**
 * 
 * @Desc    : 例外订单
 * @CreateOn: 2018年9月7日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Controller
@RequestMapping("/mp/excepOrder")
public class ExcepOrderController extends GenericController{

    private static Logger log = LoggerFactory.getLogger(ExcepOrderController.class);
    
    @Resource
    private ExcepOrderManager manager;    

    /**
     * 分页查询例外需求数据
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            ExcepOrderModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        List<ExcepOrderModel> pageList = (PageList<ExcepOrderModel>) manager.queryExcepOrderForPage(model, p);
        return new PageJson(pageList);
    }
    
    /**
	 * 下载导出ExcepOrder数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadExcepOrderModel")
	public void downloadExcepOrderModel(HttpServletRequest request,HttpServletResponse response
			,ExcepOrderModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<ExcepOrderModel> list =  manager.queryExcepOrderByKey(model);
		/**
		 * 如果查询记录超过10000条则报错
		 */
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"仓库代码","零件号", "供应商代码", "出货地代码", "零件用量",
				"到货时间"};
		String[] columns = {"storage","partNo", "supplierNo", "supFactory", "orderNum",
				"arriveDateStr"};
		int[] widths = {80, 80, 80, 80, 80, 80,
				80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "例外需求"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
    
	/**
	 * 导入EXCEL数据信息
	 * <p>return: void</p>  
	 * <p>Description: ExcepOrderController.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
    @RequestMapping("importModel")
    public void importModel(@RequestParam("file") MultipartFile file,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
        try {
            IUser user = ContextUtil.getCurrentUser();
            String uuid = null;
            HttpSession session = request.getSession();
            uuid = (String)session.getAttribute(SessionKey.MP_EXCEP_ORDER_DEMAND_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
            if(StringUtil.isNotEmpty(uuid)){
                manager.deleteImportTempDataByUUID(uuid);
            }else{
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            session.setAttribute(SessionKey.MP_EXCEP_ORDER_DEMAND_IMPORT_UUID, uuid);
            
            Map<String,Object> resultMap = manager.importDemoModel(file, uuid, RequestUtil.getIpAddr(request), user);
            /**
			 * 这里要传递uuid***
			 */
            resultMap.put("uuid", uuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(),"失败", (String) resultMap.get("console"),
				JSONObject.fromObject(resultMap), ResultMessage.FAIL);
			}
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
        }
    }
    
    /**
     * 查询导入的临时例外订单用量数据
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistImportTempJson")
    public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
        Map<String, String> paramMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
        paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
        QueryFilter queryFilter = getQueryFilter(request);
        Page page = queryFilter.getPage();
        PageList<ExcepOrderModelImport> pageList = (PageList<ExcepOrderModelImport>) manager.queryImportTempData(paramMap, page);
        return new PageJson(pageList);
    }
    
    /**
     * 将临时例外订单需求数据导入正式表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("isImport")
    public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ResultMessage message = null;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            HttpSession session = request.getSession();
            String uuid = (String)session.getAttribute(SessionKey.MP_EXCEP_ORDER_DEMAND_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
            paramMap.put("uuid", uuid);
            paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
            paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
            manager.insertImportData(paramMap);
            message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            message = new ResultMessage(ResultMessage.FAIL, "执行失败");
        }
        writeResultMessage(response.getWriter(), message);
    }
    
    /**
	 * 导出临时数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.MP_EXCEP_ORDER_DEMAND_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<ExcepOrderModelImport> list = manager.queryExcepOrderModelImportTempDataForExport(paramMap);
		
		String[] headers = {"零件号", "供应商代码", "出货地代码", "零件用量",
		"到货时间","校验结果","导入状态", "校验信息"};
		String[] columns = {"partNo", "supplierNo", "supFactory", "orderNum",
				"arriveDateStr","codeValueNameB","codeValueNameC", "checkInfo"};
		int[] widths = {80, 80, 80, 80,
		                80, 50, 50, 360};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "例外需求"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
}

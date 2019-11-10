package com.hanthink.sw.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sw.manager.SwRecManager;
import com.hanthink.sw.model.SwRecModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 
* <p>Title: SwRecController</p>  
* <p>Description: 收货查询界面controller</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月21日 下午9:32:18
 */

@RequestMapping("/sw/swRec")
@Controller
public class SwRecController extends GenericController{
	
	@Resource
	private SwRecManager manager;
	
	private static Logger log = LoggerFactory.getLogger(SwRecController.class);

	/**
	 * 
	 * <p>Title: queryJisoPickupPage</p>  
	 * <p>Description: 收货查询界面，分页查询功能</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:25:46
	 */
	@RequestMapping("queryJisoRecPage")
	public @ResponseBody PageJson queryJisoPickupPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwRecModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<SwRecModel> pageList = (PageList<SwRecModel>) manager.queryJisoRecPage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * <p>Title: queryJisoDeliveryDetailPage</p>  
	 * <p>Description: 收货查询界面，查询明细功能</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午10:06:18
	 */
	@RequestMapping("queryJisoRecDetailPage")
	public @ResponseBody PageJson queryJisoDeliveryDetailPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwRecModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<SwRecModel> pageList = (PageList<SwRecModel>) manager.queryJisoRecDetailPage(model, p);
        return new PageJson(pageList);
	}

	/**
	 * 
	 * <p>Title: downloadSwDemandForecastModel</p>  
	 * <p>Description: 收货查询界面，导出功能，查询需要导出的数据</p>  
	 * @param request
	 * @param response
	 * @param model  
	 * @throws Exception 
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:54:04
	 */
	@RequestMapping("exportForExcelModel")
	public void downloadSwDemandForecastModel(HttpServletRequest request,HttpServletResponse response, 
								SwRecModel model) throws Exception{
		try {
		String exportFileName = "收货信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
	    model.setFactoryCode(user.getCurFactoryCode());
		List<SwRecModel> list =  manager.querySwRecByKey(model);
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
		
		String[] headers = {"订单号","物流单号","供应商代码","供应商名称","仓库代码",
				"订单行号","零件号","零件名称","简号","订购数量",
				"收货数量","收货状态","最后收货时间"};
		String[] columns = {"orderNo","purchaseNo","supplierNo","supplierName","depotNo",
				"purchaseRowno","partNo","partNameCn","partShortNo","orderQty",
				"totalRecQty","codeValueName","receiveDate"};
		int[] widths = {100, 100, 100, 100, 100,
				100, 100, 100, 100, 100,
				100, 100, 100};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
}

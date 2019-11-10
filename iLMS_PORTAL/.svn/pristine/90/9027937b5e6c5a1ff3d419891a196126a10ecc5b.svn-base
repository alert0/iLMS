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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sw.manager.SwAccountBillManager;
import com.hanthink.sw.model.SwAccountBillModel;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.sw.util.ExcelBillUtil;
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
 * 
* <p>Title: SwAccountBillController</p>  
* <p>Description: 发票对账管理</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月22日 下午4:29:47
 */
@Controller
@RequestMapping("/sw/swAccountBill")
public class SwAccountBillController extends GenericController{

	@Resource
	private SwAccountBillManager manager;
	
	private static Logger log = LoggerFactory.getLogger(SwAccountBillController.class);

	/**
	 * 
	 * <p>Title: queryJisoAccountBillPage</p>  
	 * <p>Description: 发票对账管理分页查询</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午5:04:31
	 */
	@RequestMapping("queryJisoAccountBillPage")
	public @ResponseBody PageJson queryJisoAccountBillPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwAccountBillModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        //供应商用户只能查看与与自己相关的数据
        if(IUser.USER_TYPE_SUPP.equals(user.getUserType())){
        	model.setSupplierNo(user.getSupplierNo());
        }
        PageList<SwAccountBillModel> pageList = (PageList<SwAccountBillModel>) manager.queryJisoAccountBillPage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * <p>Title: queryJisoAccountBillPage</p>  
	 * <p>Description: 发票对账(物流)</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer Zengfanzhuo
	 * @time 2019年6月18日 下午5:04:31
	 */
	@RequestMapping("queryJisoAccountBillSearchPage")
	public @ResponseBody PageJson queryJisoAccountBillSearchPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwAccountBillModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        //供应商用户只能查看与与自己相关的数据
        if(IUser.USER_TYPE_SUPP.equals(user.getUserType())){
        	model.setSupplierNo(user.getSupplierNo());
        }
        PageList<SwAccountBillModel> pageList = (PageList<SwAccountBillModel>) manager.queryJisoAccountBillSearchPage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * <p>Title: queryJisoAccountBillDetailPage</p>  
	 * <p>Description: 发票对账管理，明细查看</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午5:04:10
	 */
	@RequestMapping("queryJisoAccountBillDetailPage")
	public @ResponseBody PageJson queryJisoAccountBillDetailPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwAccountBillModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<SwAccountBillModel> pageList = (PageList<SwAccountBillModel>) manager.queryJisoAccountBillDetailPage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * <p>Title: feedback</p>  
	 * <p>Description: 发票对账管理，反馈功能</p>  
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午5:38:43
	 */
	@RequestMapping("feedback")
	public void feedback(HttpServletRequest request,HttpServletResponse response, 
			@RequestBody SwAccountBillModel model) 
			throws Exception{
			ResultMessage message=null;
			IUser user = ContextUtil.getCurrentUser();
			
			try {
				if (null != model) {
					model.setFactoryCode(user.getCurFactoryCode());
					
					if (StringUtil.isEmpty(model.getBillNo())) {
						message = new ResultMessage(ResultMessage.FAIL, "未选中数据");
						writeResultMessage(response.getWriter(), message);
						return;
					}
					String[] billNos = model.getBillNo().split(",");
					model.setBillNo(billNos[0]);
					Boolean b = manager.isExists(model); //根据对账单号、金税发票号、发票代码判断是否已存在该发票
					if (b) {
						message = new ResultMessage(ResultMessage.FAIL, "该发票已存在");
						writeResultMessage(response.getWriter(), message);
						return;
					}
					manager.billFeedback(model);
					message = new ResultMessage(ResultMessage.SUCCESS, "反馈成功");
					writeResultMessage(response.getWriter(), message);
					return;
				}else {
					message = new ResultMessage(ResultMessage.FAIL, "未选中数据");
					writeResultMessage(response.getWriter(), message);
					return;
				}
				//message = new ResultMessage(ResultMessage.SUCCESS, "反馈成功");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				throw new Exception("系统错误,请联系管理员");
//				message = new ResultMessage(ResultMessage.FAIL, "反馈失败");
//				writeResultMessage(response.getWriter(), message);
			}
			
	}
	
	/**
	 * 
	 * <p>Title: downloadSwDemandForecastModel</p>  
	 * <p>Description: 发票对账管理界面，导出功能</p>  
	 * @param request
	 * @param response
	 * @param model  
	 * @throws Exception 
	 * @authoer luoxq
	 * @time 2018年10月22日 下午5:55:38
	 */
	@RequestMapping("exportForExcelModel")
	public void downloadSwAccountBillModel(HttpServletRequest request,HttpServletResponse response, 
								SwAccountBillModel model) throws Exception{
		try {
		
		String exportFileName = "发票对账信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
		if (IUser.USER_TYPE_SUPP.equals(user.getUserType())) {  //供应商导出自己的数据
			model.setSupplierNo(user.getSupplierNo());
		}
        model.setFactoryCode(user.getCurFactoryCode());
		List<SwAccountBillModel> list =  manager.querySwAccountBillByKey(model);
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
		
		String[] headers = {"对账单号","供应商代码", "对账单不含税合计","对账单含税合计","对账单税额合计","货币类型","供应商返利值",
							"供应商返利描述","特殊扣款值","特殊扣款描述","年底调差值","年底调差描述","模具分摊值","模具分摊描述","付款条件","过账日期"};
		String[] columns = {"billNo","supplierNo", "taxExcluded","taxInclusive","totalTax","currencyType",
				   			"rebate","rebateDesc","deductMoney","deductMoneyDesc","yearAdjust","yearAdjustDesc","mouldAmount","mouldAmountDesc",
				   			"payTerm","makeDate"};
		int[] widths = {100,100, 100,100,100,100,100,100,100,100,100,100,100,100,100,100,100};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	 * <p>Title: downloadSwDemandForecastModel</p>  
	 * <p>Description: 发票对账(物流)界面，导出功能</p>  
	 * @param request
	 * @param response
	 * @param model  
	 * @throws Exception 
	 * @authoer zengfanzhuo
	 * @time 2019年6月19日 下午5:55:38
	 */
	@RequestMapping("exportAccountBillSearchForExcelModel")
	public void downloadSwAccountBillSearchModel(HttpServletRequest request,HttpServletResponse response, 
								SwAccountBillModel model) throws Exception{
		try {
		String exportFileName = "发票对账明细导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
		List<SwAccountBillModel> list =  manager.querySwAccountBillSearchByKey(model);
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
		
		String[] headers = {"对账单号","对账单行号", "订单号","订单行号","零件号","简号","零件名称","收货数量","物料基本单位"};
		String[] columns = {"billNo","billRowNo", "purchaseNo","purchaseRowNo","partNo","partShortNo",
				   			"partNameCn","recNum","partUnit",};
		int[] widths = {105,90,100,80,100,80,130,80,100};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	 * @Description: 发票对账明细导出
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月24日 下午6:24:21
	 */
	@RequestMapping("exportForExcelModelDetail")
	public void downloadSwAccountBillDetailModel(HttpServletRequest request,HttpServletResponse response, 
			SwAccountBillModel model) throws Exception{
		try {
		String exportFileName = "发票对账明细信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
		List<SwAccountBillModel> list =  manager.querySwAccountDetailBillByKey(model);
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
		/**
		 * udpate by luoxianqin 
		 * 导出字段调整
		 */
//		String[] headers = {"对账单号","对账单行号","订单号", "收货凭证年度","物料凭证","收货凭证日期",
//							"暂估价","正式价","调差价格（不含税）","定价（暂估价）百分比","暂估结算单价","零件号","已收货数量",
//							"物料基本单位","应付（不含税）金额","税率","税额","货币类型"};
//		String[] columns = {"billNo","billRowNo","purchaseNo", "recVoucherYear","recVoucherBillno","recDate",
//				   			"evaPrice","officialPrice","adjustDiffPrice","evaPricePercent","evaSettlePrice","partNo","recNum",
//				   			"partUnit","payAmount","taxRate","taxAmount","currencyType"};
		String[] headers = {"对账单号","订单号", "物料凭证","收货凭证年度","收货凭证日期",
		"暂估结算单价（折前）","定价（暂估价）百分比","暂估结算单价（折后）","零件号","已收货数量",
		"物料基本单位","应付（不含税）金额","税率","税额"};
		
		String[] columns = {"billNo","purchaseNo", "recVoucherBillno","recVoucherYear","recDate",
			"evaSettlePriceBf","evaPricePercent","evaSettlePrice","partNo","recNum",
			"partUnit","payAmount","taxRate","taxAmount"};
		int[] widths = {120,90, 90,100,100,60,60,60,100,50,50,70,50,50};
		ExcelBillUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	 * @Description: 查看发票反馈情况
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月3日 下午1:59:34
	 */
	@RequestMapping("queryJisoAccountInvoicePage")
	public @ResponseBody PageJson queryJisoAccountInvoicePage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwAccountBillModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());

        PageList<SwAccountBillModel> pageList = (PageList<SwAccountBillModel>) manager.queryJisoAccountInvoicePage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * @Description: 发票提交
	 * @param @param request
	 * @param @param reponse   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月3日 下午4:01:43
	 */
	@RequestMapping("submitInvoice")
	public void submitInvoice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message=null;
		try {
			String billNos = RequestUtil.getString(request, "billNos");
			
			String [] billNoArr = billNos.split(",");
			manager.updateSubmitStatus(billNoArr,SwAccountBillModel.SUBMIT_STATUS_YES);
			message = new ResultMessage(ResultMessage.SUCCESS, "提交成功");
			writeResultMessage(response.getWriter(), message);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
		
	}
	
	/**
	 * 
	 * @Description: 反馈发票导出
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年6月20日 下午5:45:13
	 */
	@RequestMapping("downloadSwAccountBillInvoiceModel")
	public void downloadSwAccountBillInvoiceModel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
	    SwAccountBillModel model = new SwAccountBillModel();
	    String billNos = RequestUtil.getString(request, "billNos");
	    if (StringUtil.isEmpty(billNos)) {
	    	message = new ResultMessage(ResultMessage.FAIL, "请选择要导出的数据");
			writeResultMessage(response.getWriter(), message);
			return;
		}
	    model.setBillNoArr(billNos.split(","));
		String exportFileName = "发票信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
		List<SwAccountBillModel> list =  manager.downloadSwAccountBillInvoiceModel(model);
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
		
		String[] headers = {"对账单号","金税发票号", "发票代码","发票含税总金额","发票税额","发票开票日期"};
		
		String[] columns = {"billNo","invoiceNo", "invoiceCode","invoiceAmount","taxAmount","invoiceDate"};
		
		int[] widths = {130, 100, 100, 100, 100, 100};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
}

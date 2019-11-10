package com.hanthink.pub.controller;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.manager.PubSupplierManager;
import com.hanthink.pub.model.PubSupplierModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：供应商主数据表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubSupplier")
public class PubSupplierController extends GenericController{
	private static Logger log = LoggerFactory.getLogger(PubSupplierController.class);
	
	@Resource
	PubSupplierManager pubSupplierManager;
	
	/**
     * 供应商主数据表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            PubSupplierModel model) {
    	String resultMsg=null;
    	try {
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        List<PubSupplierModel> pageList = (PageList<PubSupplierModel>) pubSupplierManager.queryPubSupplierForPage(model, p);
        return new PageJson(pageList);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
    /**
	 * 下载导出供应商主数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadPubSupplierModel")
	public void downloadPubSupplierModel(HttpServletRequest request,HttpServletResponse response,
			 PubSupplierModel model){
		try {
		List<PubSupplierModel> list =  pubSupplierManager.queryPubSupplierByKey(model);
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
		
		String[] headers = {
				"出货地代码","供应商代码", "供应商名称", "供应商地址","供应商状态","联系邮箱","联系人","联系手机","联系电话",
				
				"出货地地址",
				"重要联络人部门", "重要联络人职位", "重要联络人姓名","重要联络人移动电话","重要联络人固定电话","重要联络人邮箱地址",
				"PT订单联络人部门","PT订单联络人职位", "PT订单联络人姓名", "PT订单联络人移动电话","PT订单联络人固定电话","PT订单联络人邮箱地址",
				"量产订单联络人部门","量产订单联络人职位", "量产订单联络人姓名", "量产订单联络人移动电话","量产订单联络人固定电话","量产订单联络人邮箱地址",
				
				"异常联络人部门A","异常联络人部门B", "异常联络人职位A", "异常联络人职位B","异常联络人姓名A","异常联络人姓名B",
				"异常联络人移动电话A","异常联络人移动电话B","异常联络人固定电话A","异常联络人固定电话B","异常联络人邮箱地址A","异常联络人邮箱地址B",
				
				"设变联络人部门","设变联络人职位","设变联络人姓名","设变联络人移动电话","设变联络人固定电话","设变联络人邮箱地址",
				
				"重要联络人部门1","重要联络人职位1", "重要联络人姓名1", "重要联络人移动电话1","重要联络人固定电话1","重要联络人邮箱地址1",
				"PT订单联络人部门1","PT订单联络人职位1", "PT订单联络人姓名1", "PT订单联络人移动电话1","PT订单联络人固定电话1","PT订单联络人邮箱地址1",
				"量产订单联络人部门1","量产订单联络人职位1", "量产订单联络人姓名1", "量产订单联络人移动电话1","量产订单联络人固定电话1","量产订单联络人邮箱地址1",
				"设变联络人部门1","设变联络人职位1", "设变联络人姓名1", "设变联络人移动电话1","设变联络人固定电话1","设变联络人邮箱地址1",
				
				"包装联络人部门1","包装联络人职位1", "包装联络人姓名1", "包装联络人移动电话1","包装联系人固定电话1","包装联络人邮箱地址1",
				"包装联络人部门2","包装联络人职位2", "包装联络人姓名2", "包装联络人移动电话2","包装联系人固定电话2","包装联络人邮箱地址2",
				
				"PT物流对应联络人部门","PT物流对应联络人姓名", "PT物流对应联络人职位", "PT物流对应联络人移动电话","PT物流对应联络人固定电话","PT物流对应联络人邮箱地址",
				"PT物流对应联络人部门1","PT物流对应联络人姓名1", "PT物流对应联络人职位1", "PT物流对应联络人移动电话1","PT物流对应联络人固定电话1","PT物流对应联络人邮箱地址1",
				"量产物流对应联络人部门","量产物流对应联络人职位", "量产物流对应联络人姓名", "量产物流对应联络人移动电话","量产物流对应联络人固定电话","量产物流对应联络人邮箱地址",
				"量产物流对应联络人部门1","量产物流对应联络人职位1", "量产物流对应联络人姓名1", "量产物流对应联络人移动电话1","量产物流对应联络人固定电话1","量产物流对应联络人邮箱地址1"
				
				};
		String[] columns = {
				"supFactory","supplierNo", "supplierName", "detailAddr","codeValueName","email", "contact", "mobileNo","telNo",
				
				"supFactoryAddr",
				"importDep","importPosition", "importName", "importMobile","importTel","importMail",
				"ptDep","ptPosition", "ptName", "ptMobile","ptTel","ptMail",
				"massDep","massPosition", "massName", "massMobile","massTel","massMail",
				
				"excepDepA","excepDepB","excepPositionA","excepPositionB","excepNameA","excepNameB",
				"excepMobileA","excepMobileB", "excepTelA", "excepTelB","excepMailA","excepMailB",
				
				"deviceDep","devicePosition", "deviceName", "deviceMobile","deviceTel","deviceMail",
				"importDepA","importPositionA", "importNameA", "importMobileA","importTelA","importMailA",
				"ptDepA","ptPositionA", "ptName", "ptMobileA","ptTelA","ptMailA",
				"massDepA","massPositionA", "massNameA", "massMobileA","massTelA","massMailA",
				
				"deviceDepA","devicePositionA", "deviceNameA", "deviceMobileA","deviceTelA","deviceMailA",
				"packDepA","packPositionA", "packNameA", "packMobileA","packTelA","packMailA",
				"packDepB","packPositionB", "packNameB", "packMobileB","packTelB","packMailB",
				
				"ptLogisticsDep","ptLogisticsName", "ptLogisticsPosition", "ptLogisticsMobile","ptLogisticsTel","ptLogisticsMail",
				"ptLogisticsDepA","ptLogisticsNameA", "ptLogisticsPositionA", "ptLogisticsMobileA","ptLogisticsTelA","ptLogisticsMailA",
				"massLogisticsDep","massLogisticsPosition", "massLogisticsName", "massLogisticsMobile","massLogisticsTel","massLogisticsMail",
				"massLogisticsDepA","massLogisticsPositionA", "massLogisticsNameA", "massLogisticsMobileA","massLogisticsTelA","massLogisticsMailA"
				
				};
		int[] widths = {180, 180, 180, 180, 180, 180, 180, 180, 180,
				180,
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180,
				180, 180, 180, 180, 180, 180
				};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "供应商主数据"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	/**
	 * 行修改
	 * @param request
	 * @param response
	 * @param PubPrPrinter
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("update")
	public void update(HttpServletRequest request,HttpServletResponse response,
			@RequestBody PubSupplierModel  pubSupplierModel) throws Exception{
		String resultMsg = null;
		try {
			pubSupplierManager.updateAndLog(pubSupplierModel, RequestUtil.getIpAddr(request));
			resultMsg="修改成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg = "修改失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
}

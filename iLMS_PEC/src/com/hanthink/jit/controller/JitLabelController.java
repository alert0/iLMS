package com.hanthink.jit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.jit.manager.JitLabelManager;
import com.hanthink.jit.model.JitLabelModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @ClassName: JitLabelController
 * @Description: 零件标签查询
 * @author dtp
 * @date 2018年9月29日
 */
@Controller
@RequestMapping("/jit/jitLabel")
public class JitLabelController extends GenericController {
	
	@Resource
	private JitLabelManager jitLabelManager;
	
	private static Logger log = LoggerFactory.getLogger(JitLabelController.class);
	
	/**
	 * @Description: 零件标签查询 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年9月29日
	 */
	@RequestMapping("queryJitLabelPage")
	public @ResponseBody PageJson queryJitLabelPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JitLabelModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitLabelModel> pageList = jitLabelManager.queryJitLabelPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 零件标签导出
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月29日
	 */
	@RequestMapping("downloadJitLabel")
	public void downloadJitLabel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitLabelModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName = "零件标签" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitLabelModel> pageList = jitLabelManager.queryJitLabelPage(model, page);
		int curNum = pageList.getPageResult().getTotalCount();
		if(0 == curNum){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000);
		//int sysMaxNum_T = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000);
		if(curNum > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		List<JitLabelModel> list = jitLabelManager.queryJitLabelList(model);
		if(null != list) {
			String[] headers = {"车间", "信息点", "出货仓库", "物流单号", "零件号",
					"简号", "零件名称", "配送地址","备件批次进度", "发货批次进度",
					"到货批次进度", "配送批次进度","下线批次", "收容数", 
					"配送工程", "卸货口","供应商代码","看板名称","打印状态",
					"打印时间","创建时间"};
			String[] columns = {"workcenter", "planCodeDesc", "shipDepot", "orderNo", "partNo",
					"partShortNo", "partName","location", "prepareProductSeqno", "deliveryProductSeqno", 
					"arriveProductSeqno" ,"distriProductSeqno", "kbProductSeqno", "standardPackage",
					"distriPerson", "unloadPort","supplierNo", "kbName", "printStatus",
					"printTime", "creationTime"};
			int[] widths = {60, 80, 80, 100, 100,
					80, 100, 80, 80, 80,
					80, 80, 100, 80,
					80, 70, 80, 80, 80,
					100, 100};
			if(curNum <= sysMaxNum) {
				ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}else {
				//ExcelExportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
			}
		}
		
	}
	
	/**
	 * @Description: 零件标签打印 
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws Exception    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月29日
	 */
	@RequestMapping("jitLabelPrint")
	public void jitLabelPrint(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String orderNoStr = RequestUtil.getString(request, "orderNoStr");
		String[] orderNoArr = orderNoStr.split(",");
		String labelRownoStr = RequestUtil.getString(request, "labelRownoStr");
		String[] labelRownoArr = labelRownoStr.split(",");
		if(null != orderNoArr && null != labelRownoArr && orderNoArr.length == labelRownoArr.length) {
			//打印N张
			List<JitLabelModel> list = new ArrayList<JitLabelModel>();
			//更新打印状态list
			List<JitLabelModel> list_printInfo = new ArrayList<JitLabelModel>();
			Map<String, Object> parameters = new HashMap<String, Object>();
			for (int j = 0; j < labelRownoArr.length; j++) {
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				JitLabelModel model = new JitLabelModel();
				model.setOrderNo(orderNoArr[j]);
				model.setLabelRowno(labelRownoArr[j]);
				model.setPrintUser(ContextUtil.getCurrentUser().getAccount());
				model.setPrintUserIp(RequestUtil.getIpAddr(request));
				JitLabelModel bean = jitLabelManager.queryJitLabel(model);
				//更新打印状态
				list_printInfo.add(model);
				//二维码
				StringBuffer qrCode = new StringBuffer();
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getPartNo());//零件号
				qrCode.append("#");
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getStandardPackage());//包装数
				qrCode.append("#");
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getPurchaseOrderno());//订单号
				qrCode.append("#");
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getOrderRowNo());//采购订单行号
				qrCode.append("#");
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getOrderNo());//物流单号
				qrCode.append("#");
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getPartShortNo());//简号
				qrCode.append("#");
				qrCode = JitLabelController.addEmptyStr(qrCode, "03");//订单类型
				qrCode.append("#");
				qrCode.append("");//追溯重要度等级
				qrCode.append("#");  
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getSupplierNo());//供应商代码
				qrCode.append("#");
				qrCode.append("");//供应商生产批次
				qrCode.append("#");
				qrCode = JitLabelController.addEmptyStr(qrCode, bean.getRequireNum());//同批次数量
				qrCode.append("#");
				if(StringUtils.isNullOrEmpty(bean.getUuid())) {
					qrCode.append(uuid);//UUID
					bean.setUuid(uuid);
				}else {
					qrCode.append(bean.getUuid());
				}
				bean.setQRCode(MakeQrcodeImages.getQrCodeImage(qrCode.toString(), "150", "150"));
				//看板张数 kbzs
				bean.setKbzs((list.size()+1) + "/" + orderNoArr.length );
				bean.setLabelRowno(labelRownoArr[j]);
				list.add(bean);
			}
			JRDataSource jRDataSource;
			if (list.size() > 0) {
				jRDataSource = new JRBeanCollectionDataSource(list);
			} else {
				jRDataSource = new JREmptyDataSource();
			}
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "jit" + File.separator + "JIT_LABEL.jasper";
			InputStream file = new FileInputStream(filenurl);
			JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;"); 
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			//更新打印状态
			jitLabelManager.updateJitLabelPrintInfo(list_printInfo, list);
		}
		
	}
	
	/**
	 * 标签打印null换成""
	 * @param qrCode
	 * @param str
	 * @author dtp
	 * @return
	 */
	private static StringBuffer addEmptyStr(StringBuffer qrCode, String str) {
		if(StringUtils.isNullOrEmpty(str)) {
			qrCode.append("");
		}else {
			qrCode.append(str);
		}
		return qrCode;
	}

}

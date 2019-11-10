package com.hanthink.sps.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.sps.manager.SpsShelfLabelTmpManager;
import com.hanthink.sps.model.SpsShelfLabelTmpModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
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
import net.sf.json.JSONObject;

/**
 * @ClassName: SpsShelfLabelTmpController
 * @Description: 货架标签打印
 * @author dtp
 * @date 2018年10月31日
 */
@Controller
@RequestMapping("/sps/spsShelfLabel")
public class SpsShelfLabelTmpController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(SpsShelfLabelTmpController.class);
	
	@Resource
	private SpsShelfLabelTmpManager spsShelfLabelTmpManager;
	
	/**
	 * @Description: 导入货架标签excel  
	 * @param: @param file
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	@RequestMapping("importSpsShelfLabel")
	public void importSpsShelfLabel(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) {
		try {
			String uuid = null;
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				spsShelfLabelTmpManager.deleteImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			Map<String,Object> rtn = spsShelfLabelTmpManager.importSpsShelfLabelTmpModel(file, uuid, RequestUtil.getIpAddr(request));
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), "失败", "", JSONObject.fromObject(rtn), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}
	
	/**
	 * @Description: 查询导入数据 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	@RequestMapping("querySpsShelfLabelPage")
	public @ResponseBody PageJson querySpsShelfLabelPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") SpsShelfLabelTmpModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsShelfLabelTmpModel> pageList = spsShelfLabelTmpManager.querySpsShelfLabelPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 货架标签打印  
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @throws Exception 
	 * @date: 2018年11月8日
	 */
	@RequestMapping("spsShelfLabelPrint")
	public void spsShelfLabelPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idArrStr = RequestUtil.getString(request, "idArrStr");
		String[] idArr = idArrStr.split(",");
		if(null != idArr && idArr.length > 0) {
			//打印N张
			Map<String, Object> parameters = new HashMap<String, Object>();
			List<SpsShelfLabelTmpModel> printList = spsShelfLabelTmpManager.querySpsShelfLabelList(idArr);
			//拣取图片
			String pagePath = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator + "page" + File.separator + "sps.jpg";
			BufferedImage spsImg = ImageIO.read(new FileInputStream(pagePath));
			for (SpsShelfLabelTmpModel sps : printList) {
				//零件号+货架号
				StringBuffer qrCode = new StringBuffer();
				qrCode = SpsShelfLabelTmpController.addEmptyStr(qrCode, sps.getPartNo());
				qrCode.append("#");
				qrCode = SpsShelfLabelTmpController.addEmptyStr(qrCode, sps.getPartShortNo());
				qrCode.append("#");
				qrCode.append("");//供应商代码
				qrCode.append("#");
				qrCode = SpsShelfLabelTmpController.addEmptyStr(qrCode, sps.getShelfNo());//货架号
				qrCode.append("#");
				qrCode.append("10");//收容数
				qrCode.append("#");
				qrCode.append("100");//最小在库量
				qrCode.append("#");
				qrCode.append("1000");//最大在库量
				qrCode.append("#");
				qrCode.append("4");//叠放层次
				sps.setQrCode(MakeQrcodeImages.getQrCodeImage(qrCode.toString(), "120", "120"));
				sps.setSpsImg(spsImg); 
			}
			JRDataSource jRDataSource;
			if (printList.size() > 0) {
				jRDataSource = new JRBeanCollectionDataSource(printList);
			} else {
				jRDataSource = new JREmptyDataSource();
			}
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "sps" + File.separator + "SPS_LABEL.jasper";
			InputStream file = new FileInputStream(filenurl);
			JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;"); 
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		}
		
	}
	
	/**
	 * @Description: SPS货架标签打印导出
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	@RequestMapping("downloadSpsShelfLabel")
	public void downloadSpsShelfLabel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") SpsShelfLabelTmpModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String exportFileName =  "SPS货架标签打印导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<SpsShelfLabelTmpModel> pageList = spsShelfLabelTmpManager.querySpsShelfLabelPage(model, page);
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
		List<SpsShelfLabelTmpModel> list = spsShelfLabelTmpManager.querySpsShelfLabelList(model);
		if(null != list) {
			String[] headers = {"拣货号", "记号", "简号", "零件名称",
					"零件号","车型", "货架号", "导入状态", "检查结果", 
					"校验信息"};
			String[] columns = {"shelfNoView", "mark", "partShortNo", "partName", 
					"partNo", "modelCode", "shelfNo", "importStatus", "checkResult", 
					"checkInfo"};
			int[] widths = {60, 60, 80, 150, 
					100, 60, 80, 80, 60, 
					150};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
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

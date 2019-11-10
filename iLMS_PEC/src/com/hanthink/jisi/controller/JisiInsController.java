
package com.hanthink.jisi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hanthink.jisi.manager.JisiInsManager;
import com.hanthink.jisi.model.JisiInsModel;
import com.hanthink.jit.model.JitLabelModel;
import com.hanthink.pub.model.PubPrintInsModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * 
 * ClassName: JisiInsController 
 * @Description: 厂内同步票据查询
 * @author yokoboy
 * @date 2018年11月10日
 */
@Controller
@RequestMapping("/jisi/ins")
public class JisiInsController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(JisiInsController.class);
	
	@Resource
	private JisiInsManager manager;
	
	/**
	 * 
	 * @Description: 厂内同步票据查询，分页查询
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:07:24
	 */
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") JisiInsModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        try {
        	 PageList<JisiInsModel> pageList = (PageList<JisiInsModel>) manager.queryJisiInsForPage(model, p);
             return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
       
    }
	
	/**
	 * 
	 * @Description: 厂内同步票据查询，明细查看
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:09:08
	 */
	@RequestMapping("curdlistJsonDetail")
    public @ResponseBody PageJson curdlistJsonDetail(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") JisiInsModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<JisiInsModel> pageList = (PageList<JisiInsModel>) manager.queryJisiInsDetailForPage(model, p);
        return new PageJson(pageList);
    }
	
	/**
	 * 
	 * @Description: 厂内同步票据查询，导出
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:11:21
	 */
	@RequestMapping("downloadJisiInsModel")
	public void downloadJisiInsModel(HttpServletRequest request,HttpServletResponse response, JisiInsModel model) throws Exception{
		try {
		String exportFileName = "厂内同步票据信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
		List<JisiInsModel> list =  manager.queryJisiInsByKey(model);
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
		
		String[] headers = {"指示票号","VIN", "车型", "阶段","车身序号","PA OFF时间", "创建时间","打印机","打印状态","打印时间"};
		String[] columns = {"insNo","vin", "modelCode", "phase","wcSeqno","passTime", "creationTime","printerName","printStatus","printTime"};
		int[] widths = {120,80, 80,80,80,80,80,80,80,100};
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
	 * @Description: 厂内同步指示票打印
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws IOException 
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午5:33:00
	 */
	@RequestMapping("jisiInsPrint")
	public void jisiInsPrint(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String insNoStr = RequestUtil.getString(request, "insNo");
		String[] insNoArr = insNoStr.split(",");
		if(null != insNoArr && insNoArr.length > 0) {
			List<JisiInsModel> list_pr = new ArrayList<JisiInsModel>();
			//更新打印状态list
			List<JisiInsModel> list_printInfo = new ArrayList<JisiInsModel>();
			Map<String, Object> parameters = new HashMap<String, Object>();
			for (int j = 0; j < insNoArr.length; j++) {
				JisiInsModel model_q = new JisiInsModel();
				model_q.setInsNo(insNoArr[j]); 
				model_q.setPrintUser(ContextUtil.getCurrentUser().getAccount());
				model_q.setPrintUserIp(RequestUtil.getIpAddr(request));
				list_printInfo.add(model_q);
				//指示票明细
				List<JisiInsModel> detailList = manager.queryJisiInsDetailList(model_q);
				JisiInsModel pr = new JisiInsModel();
				for (int i = 0; i < detailList.size(); i++) {
					pr = detailList.get(i);
					if("1".equals(pr.getPrintLocation())) {
						pr.setNo1(pr.getWcSeqno());
						pr.setPartMark(pr.getPartMark());
						pr.setLocation(pr.getLocation());
					}
					if("2".equals(pr.getPrintLocation())) {
						pr.setNo2(pr.getWcSeqno());
						pr.setPartMark2(pr.getPartMark());
						pr.setLocation2(pr.getLocation());
					}
					if("3".equals(pr.getPrintLocation())) {
						pr.setNo3(pr.getWcSeqno());
						pr.setPartMark3(pr.getPartMark());
						pr.setLocation3(pr.getLocation());
					}
					if("4".equals(pr.getPrintLocation())) {
						pr.setNo4(pr.getWcSeqno());
						pr.setPartMark4(pr.getPartMark());
						pr.setLocation4(pr.getLocation());
					}
				}
				list_pr.add(pr);
			}
			JRDataSource jRDataSource;
			if (list_pr.size() > 0) {
				jRDataSource = new JRBeanCollectionDataSource(list_pr);
			} else {
				jRDataSource = new JREmptyDataSource();
			}
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
					+ File.separator +"ireport" + File.separator + "jis" + File.separator + "JISI_INS.jasper";
			InputStream file;
			try {
				file = new FileInputStream(filenurl);
				JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;"); 
				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
				manager.updatePrintStatus(list_printInfo, RequestUtil.getIpAddr(request), insNoArr);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString()); 
				String resultMsg="系统错误,请联系管理员";
				writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
			}
		}
	
	}

	
}

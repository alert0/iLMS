package com.hanthink.business.dpm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.dpm.manager.DpmInsManager;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hanthink.business.util.SessionKey;
import com.hanthink.util.constant.Constant;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * 
 * <pre>
 * Description:不良品申请
 * Company: HanThink
 * Date: 2018年9月17日 下午6:36:14
 * </pre>
 * @author luoxq
 */
@Controller
@RequestMapping("/dpm/ins")
public class DpmInsController extends GenericController{

	@Resource
	private DpmInsManager dpmInsManager;
	
	private static Logger log = LoggerFactory.getLogger(DpmInsController.class);
	
	/**
	 * 
	 * @Title: 分页查询不良品申请列表数据 
	 * @Description:  
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception    
	 * @return PageJson     
	 * @time   2018年9月18日 上午11:17:21
	 * @throws
	 */
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") DpmInsModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        HttpSession session = request.getSession();
        session.setAttribute(SessionKey.DPM_INS_QUERYFILTER, model);

        PageList<DpmInsModel> pageList;
        pageList = (PageList<DpmInsModel>) dpmInsManager.queryDpmInsForPage(model, p);
        return new PageJson(pageList);
    }
	
	/**
	 * 
	 * @Title: 修改和新增申请的不良品信息 
	 * @Description:  
	 * @param @param request
	 * @param @param response
	 * @param @param dpmInsModel
	 * @param @throws Exception    
	 * @return void     
	 * @time   2018年9月18日 下午2:20:56
	 * @throws
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody DpmInsModel dpmInsModel) throws Exception{
		String resultMsg = null;
		String insDate = null;
		String discoArea = null;
		String supplierNo = null;
		HttpSession session = request.getSession();
		if (null != session) {
			insDate = (String) session.getAttribute("insDate");
			discoArea = (String) session.getAttribute("discoArea");
			supplierNo = (String) session.getAttribute("supplierNo");
		}
		String applyNo = dpmInsModel.getApplyNo();

		IUser user = ContextUtil.getCurrentUser();
		String dpmType = RequestUtil.getString(request, "dpmType");
		if (Constant.SUBIMT_TYPE_STATUS.equals(dpmType) && null != discoArea) {
			dpmInsModel.setDiscoArea(discoArea);
		}
		dpmInsModel.setInsDate(insDate);
		dpmInsModel.setSupplierNo(supplierNo);
		try {
			if(StringUtil.isEmpty(applyNo)){
				    //从系统参数中获取最大流水号
				    Integer seriaNum = dpmInsManager.getSerialNum();
				    //如果流水号增长到最大值99999，则从1开始计数
				    if (null == seriaNum ||seriaNum==99999) {
						seriaNum = 1;
					}
				    
				    //生成不良品申请单号（年月日+五位流水号）
				    applyNo = new SimpleDateFormat("yyyyMMdd").format(new Date())+String.format("%05d", seriaNum);
				    dpmInsModel.setApplyNo(applyNo);
				    
				    //记录创建人
					dpmInsModel.setCreationUser(user.getAccount()); 
					dpmInsModel.setFactoryCode(user.getCurFactoryCode());
					dpmInsModel.setDpmType(dpmType);
					dpmInsManager.create(dpmInsModel);
					seriaNum += 1;
					dpmInsManager.updateSerialNum(seriaNum);
					resultMsg="添加成功";
				
			}else{
				DpmInsModel d = dpmInsManager.get(dpmInsModel.getApplyNo());
				//已提交信息不能进行修改
				if (!Constant.UNSUBIMT.equals(d.getInsStatus()) ) {
					resultMsg = "只能修改未提交信息，请确认";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return ;
				}
				dpmInsModel.setLastModifiedUser(user.getAccount());//记录修改人
				dpmInsManager.updateAndLog(dpmInsModel, RequestUtil.getIpAddr(request));
				resultMsg="修改成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * @Title: 通过applyNo获取明细 
	 * @Description:  
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception    
	 * @return DpmAreaModel     
	 * @time   2018年9月18日 上午11:16:30
	 * @throws
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody DpmInsModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String applyNo = RequestUtil.getString(request, "applyNo");
		if(StringUtil.isEmpty(applyNo)){
			return new DpmInsModel();
		}
		return dpmInsManager.get(applyNo);
	}
	
	/**
	 * 
	 * @Title: 删除 
	 * @Description:  
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception    
	 * @return void     
	 * @time   2018年9月20日 上午10:02:29
	 * @throws
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "applyNo");
			
			for (String applyId : aryIds) {
				DpmInsModel d = dpmInsManager.get(applyId);
				//已提交信息不能进行删除
				if (!Constant.UNSUBIMT.equals(d.getInsStatus()) ) {
					message = new ResultMessage(ResultMessage.FAIL, "只能修改未提交信息，请确认");
					writeResultMessage(response.getWriter(), message);
					return ;
				}
			}
			dpmInsManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Title: 新增界面输入零件号带出相关信息 
	 * @Description:  
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception    
	 * @return DpmInsModel     
	 * @time   2018年9月19日 上午10:15:10
	 * @throws
	 */
	@RequestMapping("getMsgByPartNo")
	public @ResponseBody DpmInsModel getMsgByPartNo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String partNo = RequestUtil.getString(request, "partNo");
		HttpSession session = request.getSession();
		if (null != session) {
			session.removeAttribute("supplierNo");
		}
		if(StringUtil.isEmpty(partNo)){
			return new DpmInsModel();
		}
		DpmInsModel dpmInsModel = dpmInsManager.getMsgByPartNo(partNo);
		session.setAttribute("supplierNo", dpmInsModel.getSupplierNo());
		return dpmInsManager.getMsgByPartNo(partNo);
	}
	
	/**
	 * 
	 * @Title: 获取处理结果下拉框 
	 * @Description:  
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception    
	 * @return Object     
	 * @time   2018年9月19日 下午3:14:45
	 * @throws
	 */
	@RequestMapping("getUnloadDealResult")
	public @ResponseBody Object getUnloadDealResult(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = dpmInsManager.getUnloadDealResult();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	 * @Title: 获取不良品项目代码下拉框 
	 * @Description:  
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception    
	 * @return Object     
	 * @time   2018年9月19日 下午3:14:59
	 * @throws
	 */
	@RequestMapping("getUnloadDpmCode")
	public @ResponseBody Object getUnloadDpmCode(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = dpmInsManager.getUnloadDpmCode();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	 * @Title: 获取新增界面责任组下拉框 
	 * @Description:  
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception    
	 * @return Object     
	 * @time   2018年9月19日 下午3:36:25
	 * @throws
	 */
	@RequestMapping("getUnloadRespDep")
	public @ResponseBody Object getUnloadRespDep(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = dpmInsManager.getUnloadRespDep();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	 * @Title: 打开新增界面时，根据用户ID显示默认信息 
	 * @Description:  
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception    
	 * @return Object     
	 * @time   2018年9月19日 下午3:28:23
	 * @throws
	 */
	@RequestMapping("getDefaultMsg")
	public @ResponseBody DpmInsModel getDefaultMsg(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		DpmInsModel dpmInsModel;
		HttpSession session = request.getSession();
		if (null != session) {
			session.removeAttribute("insDate");
			session.removeAttribute("discoArea");
		}
		try {
			IUser user = ContextUtil.getCurrentUser();
		    dpmInsModel = dpmInsManager.getDefaultMsg(user.getUserId());
			String insDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			session.setAttribute("insDate", insDate);
			if (dpmInsModel != null) {
				session.setAttribute("discoArea", dpmInsModel.getDiscoArea());
				dpmInsModel.setInsDate(insDate);
			}else {
				dpmInsModel = new DpmInsModel();
				dpmInsModel.setInsDate(insDate);
			}
            return dpmInsModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	 * @Title: 修改不良品申请信息状态 
	 * @Description:  
	 * @param @param request
	 * @param @param response
	 * @param @param dpmInsModel
	 * @param @throws Exception    
	 * @return void     
	 * @time   2018年9月20日 下午3:58:29
	 * @throws
	 */
	@RequestMapping("submit")
	public void updateInsStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String submit = RequestUtil.getString(request, "submit");
		ResultMessage message=null;
		IUser user = ContextUtil.getCurrentUser();
		try {
			String[] applyNos = request.getParameterValues("applyNoArr");
			
			//AUTO  需添加参数审核人和审核日期
			message = dpmInsManager.updateInsStatus(applyNos,submit,user.getAccount());
					
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "操作失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	* @Title: downloadDpmInsModel 
	* @Description: 导出不良品信息 
	* @param @param request
	* @param @param response    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 上午10:55:08
	 */
	@RequestMapping("downloadDpmInsModel")
	public void downloadDpmInsModel(HttpServletRequest request,HttpServletResponse response){
		try {
		HttpSession session = request.getSession();
		String exportFileName = "不良品信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DpmInsModel model = (DpmInsModel)session.getAttribute(SessionKey.DPM_INS_QUERYFILTER);
		List<DpmInsModel> list =  dpmInsManager.queryDpmInsByKey(model);
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
		
		String[] headers = {"不良品类型","申请单号", "状态", "填单时间", "申请人",
				"申请科室","零件号","简号","零件名称","不良品数量","发现区域","不良品项目"
				,"不良品描述","责任组","车型","处理结果","打印状态","供应商代码","供应商名称","供应商担当"
				,"供应商联系方式","是否紧急","订单生成状态","例外订单生成状态","备注"};
		String[] columns = {"dpmType","applyNo", "insStatus", "insDate", "creator",
				"applyDep","partNo","partShortNo","partNameCn","dpmNum","discoArea","dpmCode"
				,"dpmDesc","respDep","modelCode","dealResult","printStatus","supplierNo","supplierName"
				,"contact","telNo","isUrgent","orderStatus","excepStatus","remark"};
		int[] widths = {80,100, 80, 80, 80,80,80,80,80,80,80,80,80,80,
				        80,80, 80, 80,80,80, 80, 80,80,80,80};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	* @Title: excepOrder 
	* @Description: 手工生成例外订单 
	* @param @param request
	* @param @param response
	* @param @throws Exception    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年9月28日 上午11:12:57
	 */
	@RequestMapping("excepOrder")
	public void excepOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ResultMessage message=null;
		IUser user = ContextUtil.getCurrentUser();
		try {
			String[] applyNos = request.getParameterValues("applyNoArr");
			
			//生成例外订单
			message = dpmInsManager.toExcepOrder(applyNos,user.getAccount(),user.getCurFactoryCode());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "操作失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("print")
	public void dpmInsPrint(HttpServletRequest request,HttpServletResponse response) 
			throws Exception {
		String applyNos = RequestUtil.getString(request, "applyNoArr");
		String[] applyNoArr = applyNos.split(",");
//		List<DpmInsModel> list = new ArrayList<DpmInsModel>();
		if(null != applyNoArr && applyNoArr.length > 0) {
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			DpmInsModel model_q = new DpmInsModel();
			for (int j = 0; j < applyNoArr.length; j++) {
				//生成多个InputStream file,防止抛异常
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
						+ File.separator +"ireport" + File.separator + "demo" + File.separator + "DPM_INS.jasper";
				InputStream file = new FileInputStream(filenurl);
				model_q.setApplyNo(applyNoArr[j]);
				//不良品明细
				List<DpmInsModel> detailList = dpmInsManager.queryDpmInsDetailList(model_q);
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				if(null != detailList || detailList.size() > 1) {
					//null 处理
					DpmInsModel model = detailList.get(0);
//					for (DpmInsModel model : detailList) {
					
					if ("1".equals(model.getDpmType())) {
						parameters.put("dpmTypeCb", "1");
						parameters.put("dpmTypeJb", "1");
					}else {
						parameters.put("dpmTypeJb", "2");
						parameters.put("dpmTypeCb", "2");
					}
					if ("1".equals(model.getDealResult())) {
						parameters.put("dealResultFp", "1");
						parameters.put("dealResultBf", "1");
					}else {
						parameters.put("dealResultBf", "2");
						parameters.put("dealResultFp", "2");
					}
					parameters.put("applyNo", model.getApplyNo());
					parameters.put("partNo", model.getPartNo());
					parameters.put("partNameCn", model.getPartNameCn());
					parameters.put("supplierNo", model.getSupplierNo());
					parameters.put("supplierName", model.getSupplierName());
					parameters.put("dpmNum", model.getDpmNum());
					parameters.put("discoArea", model.getDiscoArea());
					parameters.put("insDate", model.getInsDate());
					parameters.put("dpmDesc", model.getDpmDesc());
					JRDataSource jRDataSource  = new JRBeanCollectionDataSource(detailList);
					JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters,
							jRDataSource);
					JasperPrintList.add(jasperPrint);
				}
			}
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;");
			JRPdfExporter exporter = new JRPdfExporter();
			OutputStream out = response.getOutputStream();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			exporter.exportReport();
		}
	}
	
}

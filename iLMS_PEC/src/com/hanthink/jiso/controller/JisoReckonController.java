
package com.hanthink.jiso.controller;

import java.io.IOException;
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
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.jiso.manager.JisoReckonManager;
import com.hanthink.jiso.model.JisoInsManuDealModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hanthink.jiso.model.JisoNetReqModel;
import com.hanthink.jiso.model.JisoOrderManuDealModel;
import com.hanthink.jiso.model.JisoPartgroupModel;
import com.hanthink.jiso.model.JisoVehQueueModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
/**
 * @ClassName: JisoReckon
 * @Description: 厂外同步推算控制台
 * @author dtp
 * @date 2018年9月11日
 */
@Controller
@RequestMapping("/jiso/jisoReckon")
public class JisoReckonController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(JisoReckonController.class);
	
	@Resource
	private JisoReckonManager jisoReckonManager;
	
	/**
	 * @Description: 根据信息点获取推算状态  
	 * @param:     
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年9月13日
	 */
	@RequestMapping("queryReckonState")
	public @ResponseBody PubPlanCodeModel queryReckonState(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String planCodeType = RequestUtil.getString(request, "planCodeType");
		String planCode = RequestUtil.getString(request, "planCode");
		PubPlanCodeModel model = new PubPlanCodeModel();
		model.setPlanCodeType(planCodeType);
		model.setPlanCode(planCode);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PubPlanCodeModel res = jisoReckonManager.queryReckonState(model);
		return res;
	}
	
	/**
	 * 查询待组票净需求-零件组列表
	 * @param request
	 * @param reponse
	 * @return
	 * @author dtp
	 * @throws IOException 
	 * @DATE   2018年9月12日 
	 */
	@RequestMapping("updateJisoExecState")
	public void updateJisoExecState(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获取用户信息
		IUser user = ContextUtil.getCurrentUser(); 
		PubPlanCodeModel pubPlanCodeModel = new PubPlanCodeModel();
		String execState = RequestUtil.getString(request, "execState");
		pubPlanCodeModel.setLastModifiedUser(user.getAccount());
		pubPlanCodeModel.setLastModifiedIp(RequestUtil.getIpAddr(request));
		try {
			pubPlanCodeModel.setExecState(execState);
			jisoReckonManager.updateJisoExecState(pubPlanCodeModel, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(),"操作成功",ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"操作推算服务失败,请联系管理员",e.getMessage(),ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * 查询过点车序
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author dtp
	 * @DATE	2018年9月11日 
	 */
	@RequestMapping("queryJisoVehQueuePage")
	public @ResponseBody PageJson queryJisoVehQueuePage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JisoVehQueueModel model) throws Exception{
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JisoVehQueueModel> pageList = jisoReckonManager.queryJisoVehQueuePage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * 查询过点车序
	 * @param request
	 * @param reponse
	 * @return
	 * @author dtp
	 * @DATE   2018年9月12日 
	 */
	@RequestMapping("downloadJisoVehQueuePage")
	public void downloadJisoVehQueuePage(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JisoVehQueueModel model) {
		String exportFileName = "厂外同步过点车序导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JisoVehQueueModel> pageList = jisoReckonManager.queryJisoVehQueuePage(model, page);
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
		List<JisoVehQueueModel> list = jisoReckonManager.queryJisoVehQueueList(model);
		if(null != list) {
			String[] headers = {"PA OFF时间", "生产单号","VIN", "车型", "阶段", "车身序号", "组票状态", "组单状态", "计划下线批次进度"};
			String[] columns = {"passTime", "erpOrderNo","vin", "modelCode", "phase", "wcSeqno", "groupInsState", "groupOrderState", "kbProductSeqno"};
			int[] widths = {100, 100, 100, 60, 60, 60, 60, 60, 100};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		}
		
	}
	
	/**
	 * 查询待组票净需求-零件组列表
	 * @param request
	 * @param reponse
	 * @return
	 * @author dtp
	 * @DATE   2018年9月12日 
	 */
	@RequestMapping("queryJisoPartGroupPage")
	public @ResponseBody PageJson queryJisoPartGroupPage(HttpServletRequest request,
            HttpServletResponse reponse, @ModelAttribute("model") JisoPartgroupModel model) throws Exception{
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PageList<JisoPartgroupModel> pageList = (PageList<JisoPartgroupModel>) jisoReckonManager.queryJisoPartGroupPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 手工组票2
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年10月21日
	 */
	@RequestMapping("manuDealBillFn")
	public void manuDealBillFn(HttpServletRequest request,HttpServletResponse response, 
			@RequestBody JisoInsManuDealModel[] models) throws IOException {
		List<JisoInsManuDealModel> list = new ArrayList<JisoInsManuDealModel>();
		IUser user = ContextUtil.getCurrentUser();
		for (JisoInsManuDealModel jisoInsManuDealModel : models) {
			jisoInsManuDealModel.setManuReqUser(user.getAccount());
			jisoInsManuDealModel.setManuReqIp(RequestUtil.getIpAddr(request));
			list.add(jisoInsManuDealModel);
		}
		try {
			jisoReckonManager.manuDealBillFn(list, RequestUtil.getIpAddr(request));
			//jisoReckonManager.insertBatchManuDealBill(list, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(),"手工组票操作成功",ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"手工组票操作失败",e.getMessage(),ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * @Description: 查询未组票零件净需求
	 * @param:     
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月13日
	 */
	@RequestMapping("queryRemainByPartgroupNo")
	public @ResponseBody PageJson queryRemainByPartgroupNo(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") JisoNetReqModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JisoNetReqModel> pageList = jisoReckonManager.queryRemainByPartgroupNo(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 待组单指示票-查询组单信息  
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年9月14日
	 */
	@RequestMapping("queryJisoGroupOrderPage")
	public @ResponseBody PageJson queryJisoGroupOrderPage(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") JisoInsModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PageList<JisoInsModel> pageList = jisoReckonManager.queryJisoGroupOrderPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 待组单指示票-查询未组单指示票列表   
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年9月15日
	 */
	@RequestMapping("queryNotGroupBillPage")
	public @ResponseBody PageJson queryNotGroupBillPage(HttpServletRequest request, HttpServletResponse reponse,
			 @ModelAttribute("model") JisoInsModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JisoInsModel> pageList = jisoReckonManager.queryNotGroupBillPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 获取厂外同步零件组下拉框
	 * @param: @param request
	 * @param: @param response
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年10月2日
	 */
	@RequestMapping("queryPartgroupComboData")
	public @ResponseBody PageJson queryPartgroupComboData(HttpServletRequest request,HttpServletResponse response) {
		List<JisoInsModel> list = jisoReckonManager.queryPartgroupComboData();
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		if(null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeKey", "JISO_PARTGROP");
				map.put("typeName", "厂外同步零件组");
				map.put("valueKey", list.get(i).getPartgroupNo());
				map.put("valueName", list.get(i).getPartgroupName());
				dataDictList.add(map);
			}
		}
		return new PageJson(dataDictList);
		
	}
	
	/**
	 * @Description: 获取厂外同步零件组下拉框(vue)
	 * @param: @param request
	 * @param: @param response
	 * @param: @return    
	 * @return: List<T>   
	 * @author: dtp
	 * @date: 2018年10月21日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("loadPartGroupNoComboData")
	public @ResponseBody List<T> loadPartGroupNoComboData(HttpServletRequest request,HttpServletResponse response){
		List<JisoInsModel> list = jisoReckonManager.queryPartgroupComboData();
		List<Map<String, Object>> dataDictList = new ArrayList<Map<String, Object>>();
		if(null != list && list.size() > 0) {
			for (JisoInsModel jisoInsModel : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("label", jisoInsModel.getPartgroupName());
				map.put("value", jisoInsModel.getPartgroupNo());
				dataDictList.add(map);
			}
		}
		return new PageJson(dataDictList).getRows();
	}
	
	/**
	 * @Description: 手工组单(vue)
	 * @param: @param request
	 * @param: @param response
	 * @param: @param models    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年10月24日
	 */
	@RequestMapping("manuDealOrderFn")
	public void manuDealOrderFn(HttpServletRequest request,HttpServletResponse response, 
			@RequestBody JisoOrderManuDealModel[] models) throws IOException {
		List<JisoOrderManuDealModel> list = new ArrayList<JisoOrderManuDealModel>();
		IUser user = ContextUtil.getCurrentUser();
		for (JisoOrderManuDealModel jisoOrderManuDealModel : models) {
			jisoOrderManuDealModel.setManuReqUser(user.getAccount());
			jisoOrderManuDealModel.setManuReqIp(RequestUtil.getIpAddr(request));
			list.add(jisoOrderManuDealModel);
		}
		try {
			jisoReckonManager.updateManuDealOrder(list, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(),"手工组单操作成功",ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"手工组单操作失败",e.getMessage(),ResultMessage.FAIL);
		}
		
		
	}

}

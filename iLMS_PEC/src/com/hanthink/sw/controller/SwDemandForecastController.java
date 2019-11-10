package com.hanthink.sw.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sw.manager.SwDemandForecastManager;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.util.constant.Constant;
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

/**
 * 
* <p>Title: SwAnnViewController</p>  
* <p>Description:公告查看 </p>  
* @author luoxq  
* @date 2018年10月16日 下午4:17:00
 */
@Controller
@RequestMapping("/sw/swDemandForecast")
public class SwDemandForecastController extends GenericController{

	@Resource
	private SwDemandForecastManager manager;
	
	private static Logger log = LoggerFactory.getLogger(SwAnnounceController.class);
	
	/**
	 * 
	 * <p>Title: queryJisoDemandPage</p>  
	 * <p>Description: 预测数据界面，分页查询列表</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午2:40:15
	 */
	@RequestMapping("queryJisoDemandPage")
	public @ResponseBody PageJson queryJisoDemandPage(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("model") SwDemandForecastModel model) throws Exception{
		ResultMessage message =null ;
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        model.setAccount(user.getAccount());
        if (IUser.USER_TYPE_SUPP.equals(user.getUserType())) { // 供应商用户只能查看与自己相关的信息
			model.setSupplierNo(user.getSupplierNo());
		}
        model.setUserId(user.getUserId());
        model.setUserType(user.getUserType());
        PageList<SwDemandForecastModel> pageList = (PageList<SwDemandForecastModel>) manager.queryJisoDemandPage(model, p);
        return new PageJson(pageList);
	}
	
	/**
	 * 
	 * <p>Title: publish</p>  
	 * <p>Description: 预测数据界面，发布功能</p>
	 * @param request
	 * @param response
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午2:44:19
	 */
	@RequestMapping("publish")
	public void publish(HttpServletRequest request,HttpServletResponse response,  SwDemandForecastModel model) 
			throws Exception{
			ResultMessage message=null;
			try {
//				String idStr = RequestUtil.getString(request,"id");
//				String [] ids = idStr.split(",");
				manager.publishDemandForecast(model);		 //通过版本号发布
				message = new ResultMessage(ResultMessage.SUCCESS, "发布成功");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				throw new Exception("系统错误,请联系管理员");
//				message = new ResultMessage(ResultMessage.FAIL, "发布失败");
			}
			writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * <p>Title: feedback</p>  
	 * <p>Description:预测数据界面，反馈功能 </p>  
	 * @param request
	 * @param response
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午6:18:58
	 */
	@RequestMapping("feedback")
	public void feedback(HttpServletRequest request,HttpServletResponse response, 
			@RequestBody SwDemandForecastModel model) 
			throws Exception{
			ResultMessage message=null;
			String idArr = null;
			String feedbackStatus = null;
			try {
				if (null != model) {
					idArr = model.getId();
					feedbackStatus = model.getFeedbackStatus();
				}
				if (null != idArr && null != feedbackStatus) {
					String []ids = idArr.split(",");
					manager.feedbackDemandForecast(ids,model);
				}
				message = new ResultMessage(ResultMessage.SUCCESS, "反馈成功");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				throw new Exception("系统错误,请联系管理员");
//				message = new ResultMessage(ResultMessage.FAIL, "反馈失败");
			}
			writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 预测数据导出
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年11月12日 下午5:20:21
	 */
	@RequestMapping("exportDemandForecastModel")
	public void exportDemandForecastModel(HttpServletRequest request,HttpServletResponse response, SwDemandForecastModel model) throws Exception{
		ResultMessage message=null;
		IUser user = ContextUtil.getCurrentUser();
		try {
//			String searchDate = model.getArriveStarTime().substring(0,7);
			if (null != model.getVersion()) {
				if (IUser.USER_TYPE_SUPP.equals(user.getUserType())) {
					model.setSupplierNo(user.getSupplierNo());
				}
			model.setUserType(user.getUserType());
			model.setUserId(user.getUserId());
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdfym = new SimpleDateFormat("yyyy-MM");
			
			String dateYear = model.getVersion().substring(0, 4);
			String dateMonth = model.getVersion().substring(4,6);
			
//			String dateDay = model.getVersion().substring(6,8);
			String  smonthdate1 = dateYear + "-" + dateMonth ;
			Date searchDate1 = sdfym.parse(smonthdate1);
			cal.setTime(searchDate1);
			cal.add(Calendar.MONTH, 1);
			String searchDate = sdfym.format(cal.getTime()).toString();
//			String supplierNo = model.getSupplierNo();
			String endMonthStr = model.getEndDate();
//			String releaseStatus = model.getReleaseStatus();
			String exportFileName = "月预测数据信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			DemandForecastExportUtil exportUtil = new DemandForecastExportUtil(manager, searchDate, endMonthStr, model);
			exportUtil.exportDemandForecastExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName);
			} else {
				message = new ResultMessage(ResultMessage.SUCCESS, "请输入要导出的版本");
				writeResultMessage(response.getWriter(), message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
	
	
	/**
	 * 周预测数据导出
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年11月12日 下午5:20:21
	 */
	@RequestMapping("exportDemandForecastWeekModel")
	public void exportDemandForecastWeekModel(HttpServletRequest request,HttpServletResponse response, SwDemandForecastModel model) throws Exception{
		ResultMessage message=null;
		IUser user = ContextUtil.getCurrentUser();
		try {
//			String searchDate = model.getArriveStarTime().substring(0,7);
			if (null != model.getVersion()) {
				if (IUser.USER_TYPE_SUPP.equals(user.getUserType())) {
					model.setSupplierNo(user.getSupplierNo());
				}
			model.setUserType(user.getUserType());
			model.setUserId(user.getUserId());
			String objWeek = manager.getObjWeekByVersion(model.getVersion());  //通过版本获取到对象周
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("version", model.getVersion());
 			SwDemandForecastModel swDemandForecastModel = manager.getMinAndMaxDate(map);//获取到erp下发的每个版本零件的最大日期和最小日期
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdfym = new SimpleDateFormat("yyyy-MM-dd");
//			String objWeek = objWeek1.substring(0, 11);
//			String dateDay = model.getVersion().substring(6,8);
//			String  smonthdate1 = dateYear + "-" + dateMonth ;
			Date startMonthStr1 = sdfym.parse(swDemandForecastModel.getMinDate());//导出中的每天最小日期
			cal.setTime(startMonthStr1);
			
			String startMonthStr = sdfym.format(cal.getTime()).toString();
//			String supplierNo = model.getSupplierNo();
//			cal.add(Calendar.DATE, 27);
			Date endMonthStr1 = sdfym.parse(swDemandForecastModel.getMaxDate());
			cal.setTime(endMonthStr1);
			String endMonthStr = sdfym.format(endMonthStr1).toString();//导出中的每天最大日期
//			String releaseStatus = model.getReleaseStatus();
			String exportFileName = "周预测数据信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			DemandForecastWeekExportUtil exportUtil = new DemandForecastWeekExportUtil(manager, startMonthStr, endMonthStr, model);
			exportUtil.exportDemandForecastExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName);
			} else {
				message = new ResultMessage(ResultMessage.SUCCESS, "请输入要导出的版本");
				writeResultMessage(response.getWriter(), message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	 * @Description: 获取发布版本号
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月25日 上午10:14:56
	 */
	@RequestMapping("getVersion")
	public @ResponseBody List<SwDemandForecastModel> getVersion(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			IUser user = ContextUtil.getCurrentUser();
			String foreType = RequestUtil.getString(request, "foreType");
			map.put("foreType", foreType);
			map.put("factoryCode", user.getCurFactoryCode());
			List<SwDemandForecastModel> list = manager.getVersion(map);
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");		
		}
	}
	
	/**
	 * 
	 * @Description: 获取发布版本号默认下拉框值
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return List<SwDemandForecastModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月25日 上午11:58:31
	 */
	@RequestMapping("getDefaultVersion")
	public @ResponseBody List<SwDemandForecastModel> getDefaultVersion(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			IUser user = ContextUtil.getCurrentUser();
			String foreType = RequestUtil.getString(request, "foreType");
			map.put("foreType", foreType);
			map.put("factoryCode", user.getCurFactoryCode());
			List<SwDemandForecastModel> list = manager.getDefaultVersion(map);
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");		
		}
	}
	
	/**
	 * 
	 * @Description: 分页查询周预测数据
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月20日 下午5:07:58
	 */
	@RequestMapping("queryDemandWeekPage")
	public @ResponseBody PageJson queryDemandWeekPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwDemandForecastModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        //供应商用户只能查看与与自己相关的数据
        if(IUser.USER_TYPE_SUPP.equals(user.getUserType())){
        	model.setSupplierNo(user.getSupplierNo());
        	//供应商用户只能查看已发布的数据
			model.setReleaseStatus(Constant.SW_RELEASE_STATUS_YES);
        }
        model.setUserId(user.getUserId());
        model.setUserType(user.getUserType());
        PageList<SwDemandForecastModel> pageList = (PageList<SwDemandForecastModel>) manager.queryDemandWeekPage(model, p);
        return new PageJson(pageList);
	}
}

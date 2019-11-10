package com.hanthink.mp.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpAdjPlanManager;
import com.hanthink.mp.model.MpAdjPlanModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：调整计划 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpAdjPlan")
public class MpAdjPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpAdjPlanController.class);
	
	@Resource
	MpAdjPlanManager mpAdjPlanManager;
	  	
	/**
     * 分页查询调整计划
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            MpAdjPlanModel model) {
    	String resultMsg=null;
    	try {
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        HttpSession session = request.getSession();
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        session.setAttribute(SessionKey.MP_ADJ_PLANMODEL_QUERYFILTER, model);
        
        /**
         * 判断pageList是否为空
         */
        List<MpAdjPlanModel> pageList;
        if (!PupUtil.isAllFieldNull(model)) {
        	pageList = (PageList<MpAdjPlanModel>) mpAdjPlanManager.queryMpAdjPlanForPage(model, p);
		}
        else {
        	/**
        	 * 没有数据返回空行
        	 */
        	pageList = new ArrayList<>();
        }
        return new PageJson(pageList);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
    /**
	 * 获取调整计划
	 * <p>return: void</p>  
	 * <p>Description: MpVehPlanController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月20日
	 * @version 1.0
	 */
	@RequestMapping("getAdjPlan")
	public void getAdjPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			String adjDateStrStart = RequestUtil.getString(request, "adjDateStrStart");
			String adjDateStrEnd = RequestUtil.getString(request, "adjDateStrEnd");
			Integer count = mpAdjPlanManager.getAdjPlan(ContextUtil.getCurrentUser().getCurFactoryCode(),adjDateStrStart,adjDateStrEnd);
			if (0 == count) {
			    message=new ResultMessage(ResultMessage.SUCCESS, "获取调整计划成功");
			}else {
				message=new ResultMessage(ResultMessage.FAIL, "获取调整计划失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "获取调整计划失败");
		}
 		writeResultMessage(response.getWriter(), message);
	}
    
	/**
	 * 下载导出MpAdjPlan数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpAdjPlanModel")
	public void downloadMpAdjPlanModel(HttpServletRequest request,HttpServletResponse response
			,MpAdjPlanModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpAdjPlanModel> list =  mpAdjPlanManager.queryMpAdjPlanByKey(model);
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
		
		String[] headers = {"计划ID","车型", "调整数量", "调整日期"};
		String[] columns = {"pkid","carType", "diffNum", "adjDateStr"};
		int[] widths = {80, 80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "调整计划"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	/**
	 * 大数据量导出
	 */
	/*@RequestMapping("downloadMpAdjPlanModel2")
	public void downloadMpAdjPlanModel2(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		MpAdjPlanModel model = (MpAdjPlanModel)session.getAttribute(SessionKey.MP_ADJ_PLANMODEL_QUERYFILTER);
		List<MpAdjPlanModel> list =  mpAdjPlanManager.queryMpAdjPlanByKey(model);
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		MpAdjPlanExportUtil exportUtil = new MpAdjPlanExportUtil(mpAdjPlanManager, model);
		String[] headers = {"计划ID","车型", "调整数量", "调整日期"};
		String[] columns = {"pkid","carType", "diffNum", "adjDateStr"};
		int[] widths = {80, 80, 80, 80};
		try {
			exportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "MM_MP_ADJ_PLAN_LOAD", list, headers, widths, columns);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}*/
	
	
}

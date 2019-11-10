package com.hanthink.mp.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mp.manager.MpDiffNumTempManager;
import com.hanthink.mp.model.MpDiffNumTempModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * <pre> 
 * 描述：w+1,w+2生产计划 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpDiffNumTemp")
public class MpDiffNumTempController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpDiffNumTempController.class);
	
	@Resource
	MpDiffNumTempManager mpDiffNumTempManager;
	  	
	/**
     * 分页查询w+1,w+2生产计划
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            MpDiffNumTempModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpDiffNumTempModel> pageList = (PageList<MpDiffNumTempModel>) mpDiffNumTempManager.queryMpDiffNumTempForPage(model,
					p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 下载导出数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpDiffNumTempModel")
	public void downloadMpDiffNumTempModel(HttpServletRequest request,HttpServletResponse response
			, MpDiffNumTempModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpDiffNumTempModel> list =  mpDiffNumTempManager.queryMpDiffNumTempByKey(model);
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
		
		String[] headers = {"订单号","数量", "类型", "车型", 
				"标识"};
		String[] columns = {"orderNo","num", "type", "modelCode", 
				"flag"};
		int[] widths = {80, 80, 80, 80,
				80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "生产计划"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
        /**
		 * 获取调整计划USP_MP_ZSB_DIFF_PLAN
		 * <p>return: void</p>  
		 * <p>Description: MpVehPlanController.java</p>  
		 * @author linzhuo  
		 * @date 2018年9月20日
		 * @version 1.0
		 */
		@RequestMapping("getZsbDiffPlan")
		public void getZsbDiffPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
			ResultMessage message=null;
			try {
				/**
				 * 根据当前登录人获取到工厂信息
				 */
				Integer count = mpDiffNumTempManager.getZsbDiffPlan(ContextUtil.getCurrentUser().getCurFactoryCode());
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
	
}

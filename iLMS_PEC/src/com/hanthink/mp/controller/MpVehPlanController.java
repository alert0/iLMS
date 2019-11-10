package com.hanthink.mp.controller;


import java.text.SimpleDateFormat;
import java.util.Arrays;
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

import com.hanthink.mp.manager.MpVehPlanManager;
import com.hanthink.mp.model.MpVehPlanModel;
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
 * 描述：车辆计划  控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpVehPlan")
public class MpVehPlanController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpVehPlanController.class);
	
	@Resource
	MpVehPlanManager mpVehPlanManager;
	  	
	/**
     * 分页查询车辆计划
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, 
			HttpServletResponse reponse,MpVehPlanModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpVehPlanModel> pageList = (PageList<MpVehPlanModel>) mpVehPlanManager.queryMpVehPlanForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 批量删除车辆计划数据
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "sortId");
			/**
			 *将数组转成list集合
			 */
			List<String> listIds = Arrays.asList(aryIds);
			/**
			 *查看选中的记录里面是否存在已订购数据
			 */
			Integer checknum = mpVehPlanManager.queryMpVehPlanCheck(listIds);
			 if(checknum>0) {
				 
				 message=new ResultMessage(ResultMessage.FAIL, "记录中包含已订购数据,删除失败");
				 writeResultMessage(response.getWriter(), message);
				 return ;
			 }
			mpVehPlanManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message=new ResultMessage(ResultMessage.SUCCESS, "删除车辆计划成功");
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "删除车辆计划失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 删除未订购数据
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("removeByCalStatus")
	public void removeByCalStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			List<String> listIds =   mpVehPlanManager.querySortIdAndLogByCalStatus();
			/**
			 * 此处id仅仅只是用于记录日志，依旧根据未订购状态来删除
			 */
			if (listIds.size() == 0) {
				message = new ResultMessage(ResultMessage.FAIL, "没有未订购数据");
			} else {
			mpVehPlanManager.removeAndLogByCalStatus(ContextUtil.getCurrentUser().getCurFactoryCode(), listIds,RequestUtil.getIpAddr(request));
			message=new ResultMessage(ResultMessage.SUCCESS, "删除未订购数据成功");
			}
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除未订购数据失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 获取生产计划
	 * <p>return: void</p>  
	 * <p>Description: MpVehPlanController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月20日
	 * @version 1.0
	 */
	@RequestMapping("getVehPlan")
	public void getVehPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = mpVehPlanManager.getVehPlan(ContextUtil.getCurrentUser().getCurFactoryCode());
			if(0 == count) {
			    message=new ResultMessage(ResultMessage.SUCCESS, "获取生产计划成功");
			}else {
				message=new ResultMessage(ResultMessage.FAIL, "获取生产计划失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "获取生产计划失败");
		}
 		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 下载导出MpVehPlan数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpVehPlanModel")
	public void downloadMpVehPlanModel(HttpServletRequest request,HttpServletResponse response
			,MpVehPlanModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpVehPlanModel> list =  mpVehPlanManager.queryMpVehPlanByKey(model);
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
		
		String[] headers = { "生产排序号", "车型排序", "车型物理排序号", "车型",
		        "生产单号", "总装下线时间", "焊装上线时间",
				"生产阶段","计算状态"};
		String[] columns = { "mesSortId", "carTypeSortId", "sortIdStr","carType", 
		        "orderNo", "afoffTimeStr", "weOnTimeStr",
		        "codeValueNameD", "codeValueName"};
		int[] widths = {120, 100, 150, 80, 
		        150,  150,  150,
				150,150};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "车辆计划"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
}

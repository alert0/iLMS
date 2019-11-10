package com.hanthink.mon.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager.MonPickPlanManager;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/mon/pickPlan")
public class MonPickPlanController  extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MonPickPlanController.class);
	
	@Resource
	private MonPickPlanManager monPickPlanManager;
	
	/**
	 * 查询分割数
	 * @param request
	 * @param response
	 * Lxh
	 * @throws IOException 
	 */
	@RequestMapping("/queryPickPlanSplitNumber")
	public void queryPickPlanSplitNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			int batchNum = RequestUtil.getInt(request, "splitNum", 12); ; 
			while(24*60 % batchNum != 0){
				batchNum += 1;
			}
			int minTime  = 24*60/batchNum;
			
			SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfhm = new SimpleDateFormat("HH:mm");
			
			String curDateStr = RequestUtil.getString(request, "curDateStr", sdfymd.format(new Date()));
			
			//动态拼接查询列表头
			List<Map<String, String>> tableColumnDataList = new ArrayList<Map<String, String>>();
			Date curdate = sdfymd.parse(curDateStr);
			for(int i = 0; i < batchNum; i++){
				Date tmpDate = new Date(curdate.getTime() + ((i+1)*minTime)*60000);
				Map<String, String> tmpMap = new HashMap<String, String>();
				tmpMap.put("label", sdfhm.format(tmpDate));
				tmpMap.put("name", String.valueOf((i+1)));
				tableColumnDataList.add(tmpMap);
			};
			
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("tableColumnData", tableColumnDataList);
			rtnMap.put("curDateStr", sdfymd.format(curdate));
			rtnMap.put("minTimeNum", minTime);
			writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtnMap), ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "失败", e.getMessage(), null, ResultMessage.FAIL);
			e.printStackTrace();
			log.error(e.toString());
		}
	}
	
	/**
	 *  查询取货单备货监控信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月5日 下午1:17:01
	 */
	@RequestMapping("queryPickPreparePage")
	public @ResponseBody PageJson queryPickPreparePage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int batchNum = RequestUtil.getInt(request, "splitNum", 12); //分段数量
		int minTimeNum = RequestUtil.getInt(request, "minTimeNum", 120); //间隔分钟数
		String supplierNo = RequestUtil.getString(request, "supplierNo");
		String supplierName = RequestUtil.getString(request, "supplierName");
		String depotNo = RequestUtil.getString(request, "depotNo");
		
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDateStr = RequestUtil.getString(request, "curDateStr", sdfymd.format(new Date()));//查询的当前日期
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("batchNum", batchNum);
		param.put("curDateStr", curDateStr);
		param.put("minTimeNum", minTimeNum);
		param.put("supplierNo", supplierNo);
		param.put("supplierName", supplierName);
		param.put("depotNo", depotNo);
		
		//拼接统计列SQL
		//SUM(DECODE(GROUP_NO, 1, VIEW_PREPARE_STATUS)) VIEW_PREPARE_STATUS1,
		//SUM(DECODE(GROUP_NO, 1, VIEW_ONTIME_STATUS)) VIEW_ONTIME_STATUS1,
		StringBuffer sbf = new StringBuffer();
		for(int i = 0; i < batchNum; i ++){
			String tmpBatchSeqNo = String.valueOf((i+1)); //批次流水
			
			sbf.append( " SUM(DECODE(GROUP_NO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_PREPARE_STATUS)) ");
			sbf.append("VIEW_PREPARE_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
			
			sbf.append( " SUM(DECODE(GROUP_NO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_ONTIME_STATUS)) ");
			sbf.append("VIEW_ONTIME_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
		};
		param.put("monSql", sbf.toString());
		
        PageList<Map<String, Object>> pageList = monPickPlanManager.queryBySimpleQuery("queryPickPreparePage", param, page);
        return new PageJson(pageList);
	}

	
	 /**
	  * 查询取货单备货监控明细
	  * @param request
	  * @param response
	  * @return
	  * @author ZUOSL	
	  * @DATE	2018年12月5日 下午1:18:07
	  */
	@RequestMapping("queryPickPrepareDetailPage")
	public @ResponseBody PageJson queryPickPrepareDetailPage(HttpServletRequest request, HttpServletResponse response){
		
		try {
			int minTimeNum = RequestUtil.getInt(request, "minTimeNum"); //间隔分钟数
			int colIndexNum = RequestUtil.getInt(request, "colIndexNum"); //列序号
			String supplierNo = RequestUtil.getString(request, "supplierNo");
			String depotNo = RequestUtil.getString(request, "depotNo");
			String preStatus = RequestUtil.getString(request, "prepareStatus"); //备货状态
			String onTimeStatus = RequestUtil.getString(request, "onTimeStatus"); //准时状态
			
			SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String curDateStr = RequestUtil.getString(request, "curDateStr", sdfymd.format(new Date()));//查询的当前日期
			
			//根据列序号和间隔分钟数和日期计算起止时间
			Date startDate = new Date(sdfymd.parse(curDateStr).getTime() + (colIndexNum-1)*minTimeNum*60*1000);
			Date endDate = new Date(sdfymd.parse(curDateStr).getTime() + (colIndexNum)*minTimeNum*60*1000);
			
			Page page = getQueryFilter(request).getPage();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			param.put("supplierNo", supplierNo);
			param.put("depotNo", depotNo);
			param.put("preStatus", preStatus);
			param.put("onTimeStatus", onTimeStatus);
			param.put("startDateTimeStr", sdfymdhms.format(startDate));
			param.put("endDateTimeStr", sdfymdhms.format(endDate));
			
			PageList<Map<String, Object>> pageList = monPickPlanManager.queryBySimpleQuery("queryPickPrepareDetailPage", param, page);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return null;
	}

	/**
	 * 查询收货监控信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月6日 下午2:27:27
	 */
	@RequestMapping("queryPickReceivePage")
	public @ResponseBody PageJson queryPickReceivePage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int batchNum = RequestUtil.getInt(request, "splitNum", 12); //分段数量
		int minTimeNum = RequestUtil.getInt(request, "minTimeNum", 120); //间隔分钟数
		String supplierNo = RequestUtil.getString(request, "supplierNo");
		String supplierName = RequestUtil.getString(request, "supplierName");
		String depotNo = RequestUtil.getString(request, "depotNo");
		
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDateStr = RequestUtil.getString(request, "curDateStr", sdfymd.format(new Date()));//查询的当前日期
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("batchNum", batchNum);
		param.put("curDateStr", curDateStr);
		param.put("minTimeNum", minTimeNum);
		param.put("supplierNo", supplierNo);
		param.put("supplierName", supplierName);
		param.put("depotNo", depotNo);
		
		//拼接统计列SQL
		//SUM(DECODE(GROUP_NO, 1, VIEW_REC_STATUS)) VIEW_REC_STATUS1,
		//SUM(DECODE(GROUP_NO, 1, VIEW_ONTIME_STATUS)) VIEW_ONTIME_STATUS1,
		StringBuffer sbf = new StringBuffer();
		for(int i = 0; i < batchNum; i ++){
			String tmpBatchSeqNo = String.valueOf((i+1)); //批次流水
			
			sbf.append( " SUM(DECODE(GROUP_NO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_REC_STATUS)) ");
			sbf.append("VIEW_REC_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
			
			sbf.append( " SUM(DECODE(GROUP_NO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_ONTIME_STATUS)) ");
			sbf.append("VIEW_ONTIME_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
		};
		param.put("monSql", sbf.toString());
		
        PageList<Map<String, Object>> pageList = monPickPlanManager.queryBySimpleQuery("queryPickReceivePage", param, page);
        return new PageJson(pageList);
	}

	/**
	 * 查询收货监控明细
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月6日 下午2:28:18
	 */
	@RequestMapping("queryPickReceiveDetailPage")
	public @ResponseBody PageJson queryPickReceiveDetailPage(HttpServletRequest request, HttpServletResponse response){
		
		try {
			int minTimeNum = RequestUtil.getInt(request, "minTimeNum"); //间隔分钟数
			int colIndexNum = RequestUtil.getInt(request, "colIndexNum"); //列序号
			String supplierNo = RequestUtil.getString(request, "supplierNo");
			String depotNo = RequestUtil.getString(request, "depotNo");
			String receiveStatus = RequestUtil.getString(request, "receiveStatus"); //收货状态
			String onTimeStatus = RequestUtil.getString(request, "onTimeStatus"); //准时状态
			
			SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String curDateStr = RequestUtil.getString(request, "curDateStr", sdfymd.format(new Date()));//查询的当前日期
			
			//根据列序号和间隔分钟数和日期计算起止时间
			Date startDate = new Date(sdfymd.parse(curDateStr).getTime() + (colIndexNum-1)*minTimeNum*60*1000);
			Date endDate = new Date(sdfymd.parse(curDateStr).getTime() + (colIndexNum)*minTimeNum*60*1000);
			
			Page page = getQueryFilter(request).getPage();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			param.put("supplierNo", supplierNo);
			param.put("depotNo", depotNo);
			param.put("receiveStatus", receiveStatus);
			param.put("onTimeStatus", onTimeStatus);
			param.put("startDateTimeStr", sdfymdhms.format(startDate));
			param.put("endDateTimeStr", sdfymdhms.format(endDate));
			
			PageList<Map<String, Object>> pageList = monPickPlanManager.queryBySimpleQuery("queryPickReceiveDetailPage", param, page);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return null;
	}


	/**
	 * 查询出货监控信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年12月6日 下午2:31:04
	 */
	@RequestMapping("queryPickDeliveryPage")
	public @ResponseBody PageJson queryPickDeliveryPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int batchNum = RequestUtil.getInt(request, "splitNum", 12); //分段数量
		int minTimeNum = RequestUtil.getInt(request, "minTimeNum", 120); //间隔分钟数
		String supplierNo = RequestUtil.getString(request, "supplierNo");
		String supplierName = RequestUtil.getString(request, "supplierName");
		String depotNo = RequestUtil.getString(request, "depotNo");
		
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDateStr = RequestUtil.getString(request, "curDateStr", sdfymd.format(new Date()));//查询的当前日期
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("batchNum", batchNum);
		param.put("curDateStr", curDateStr);
		param.put("minTimeNum", minTimeNum);
		param.put("supplierNo", supplierNo);
		param.put("supplierName", supplierName);
		param.put("depotNo", depotNo);
		
		//拼接统计列SQL
		StringBuffer sbf = new StringBuffer();
		for(int i = 0; i < batchNum; i ++){
			String tmpBatchSeqNo = String.valueOf((i+1)); //批次流水
			
			sbf.append( " SUM(DECODE(GROUP_NO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_DELIVERY_STATUS)) ");
			sbf.append("VIEW_DELIVERY_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
			
			sbf.append( " SUM(DECODE(GROUP_NO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_ONTIME_STATUS)) ");
			sbf.append("VIEW_ONTIME_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
		};
		param.put("monSql", sbf.toString());
		
        PageList<Map<String, Object>> pageList = monPickPlanManager.queryBySimpleQuery("queryPickDeliveryPage", param, page);
        return new PageJson(pageList);
	}

	/**
	 * 查询出货监控明细数据信息
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月6日 下午2:31:30
	 */
	@RequestMapping("queryPickDeliveryDetailPage")
	public @ResponseBody PageJson queryPickDeliveryDetailPage(HttpServletRequest request, HttpServletResponse response){
		
		try {
			int minTimeNum = RequestUtil.getInt(request, "minTimeNum"); //间隔分钟数
			int colIndexNum = RequestUtil.getInt(request, "colIndexNum"); //列序号
			String supplierNo = RequestUtil.getString(request, "supplierNo");
			String depotNo = RequestUtil.getString(request, "depotNo");
			String deliveryStatus = RequestUtil.getString(request, "deliveryStatus"); //出货状态
			String onTimeStatus = RequestUtil.getString(request, "onTimeStatus"); //准时状态
			
			SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String curDateStr = RequestUtil.getString(request, "curDateStr", sdfymd.format(new Date()));//查询的当前日期
			
			//根据列序号和间隔分钟数和日期计算起止时间
			Date startDate = new Date(sdfymd.parse(curDateStr).getTime() + (colIndexNum-1)*minTimeNum*60*1000);
			Date endDate = new Date(sdfymd.parse(curDateStr).getTime() + (colIndexNum)*minTimeNum*60*1000);
			
			Page page = getQueryFilter(request).getPage();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			param.put("supplierNo", supplierNo);
			param.put("depotNo", depotNo);
			param.put("deliveryStatus", deliveryStatus);
			param.put("onTimeStatus", onTimeStatus);
			param.put("startDateTimeStr", sdfymdhms.format(startDate));
			param.put("endDateTimeStr", sdfymdhms.format(endDate));
			
			PageList<Map<String, Object>> pageList = monPickPlanManager.queryBySimpleQuery("queryPickDeliveryDetailPage", param, page);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return null;
	}

}

package com.hanthink.mon.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager. MonJITManager;
import com.hanthink.mon.model. MonJITModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.EmailUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/mon/jit")
public class MonJITController extends GenericController{

	@Resource
	private  MonJITManager manager;
	
	private static Logger log = LoggerFactory.getLogger(MonJITController.class);


	@RequestMapping("queryjitPage")
	public @ResponseBody PageJson queryReceiptPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model")  MonJITModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        PageList< MonJITModel> pageList = (PageList< MonJITModel>) manager.queryJITPage(model, p);
        for (MonJITModel monJITModel : pageList) {
        	String result = monJITModel.getResult();
        	String[] split = result.split(",");
        	for (int i = 0; i < split.length; i++) {
				String string = split[i];
				if (!"**".equals(string)) {
					String[] splitResult = string.split("\\*");
					String trim = splitResult[0].trim();
					String trim2 = splitResult[1].trim();
					String trim3 = splitResult[2].trim();
					if ("3".equals(trim3)) {
						monJITModel.setAfterThreeBatch(trim + ":" + trim2);
					} else if ("2".equals(trim3)) {
						monJITModel.setAfterTwoBatch(trim + ":" + trim2);
					} else if ("1".equals(trim3)) {
						monJITModel.setAfterOneBatch(trim + ":" + trim2);
					} else if ("0".equals(trim3)) {
						monJITModel.setCurrentBatch(trim + ":" + trim2);
					}else if ("-1".equals(trim3)) {
						monJITModel.setBeforeOneBatch(trim + ":" + trim2);
					}else if ("-2".equals(trim3)) {
						monJITModel.setBeforeTwoBatch(trim + ":" + trim2);
					}else if ("-3".equals(trim3)) {
						monJITModel.setBeforeThreeBatch(trim + ":" + trim2);
					}
				}
			}
        	
		}
        return new PageJson(pageList);
	}
	
	
	@RequestMapping("queryjitDetailPage")
	public @ResponseBody PageJson queryReceiptDetailPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model")  MonJITModel model) throws Exception{
		DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
		String parameter = request.getParameter("supplierNo");
		String queryString = request.getQueryString();
		System.out.println(queryString + "****************************************************");
		String decode = java.net.URLDecoder.decode(queryString);
		System.out.println(decode + "****************************************************");
		String[] split = decode.split("=");
		model.setSupplierNo(split[1].trim());
		System.out.println(model.getSupplierNo());
        PageList< MonJITModel> pageList = (PageList< MonJITModel>) manager.queryJITPage(model, p);
        return new PageJson(pageList);
	}
	
	
	/**
	 * 查询当前批次信息
	 * @param request
	 * @param reponse
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月14日 下午4:22:44
	 */
	@RequestMapping("queryJitCurBatch")
	public void queryJitCurBatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String workcenter = RequestUtil.getString(request, "workcenter", "AF"); //车间
		int batchNum = Integer.valueOf(RequestUtil.getString(request, "batchNum", "7")); //批次范围数量
//		String supFactory = RequestUtil.getString(request, "supFactory");
//		String supplierName = RequestUtil.getString(request, "supplierName");
		
		//查询看板批次信息
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workCenter", workcenter);
		List<Map<String, Object>> kbBatchList = manager.queryKbBatch(param);
		Map<String, Object> kbBatch = null;
		if(0 < kbBatchList.size()){
			kbBatch = kbBatchList.get(0);
		}else{
			writeResultMessage(response.getWriter(), "看板信息获取失败", null,ResultMessage.FAIL);
			return;
		}
		
		int curBatchSeqNo = Integer.valueOf(String.valueOf(kbBatch.get("CURR_BATCH_SEQNO"))); //当前批次流水
		int batchCycleNum = Integer.valueOf(String.valueOf(kbBatch.get("BATCH_CYCLE_NUM"))); //批次循环基数
		int processCycleNum = Integer.valueOf(String.valueOf(kbBatch.get("PROCESS_CYCLE_NUM"))); //进度循环基数
		int curProductNo = Integer.valueOf(String.valueOf(kbBatch.get("CURR_PROCESS_NO"))); //当前进度号
//		int curBatchNo = Integer.valueOf(String.valueOf(kbBatch.get("CURR_BATCH_NO"))); //当前批次号
		int curBatchNo = curBatchSeqNo % batchCycleNum == 0 ? curBatchSeqNo / batchCycleNum : curBatchSeqNo / batchCycleNum + 1; //当前批次号
				
		//动态拼接查询列表头
		List<Map<String, String>> tableColumnDataList = new ArrayList<Map<String, String>>();
		int startBatchSeqNo = curBatchSeqNo - batchNum > 0 ? curBatchSeqNo - batchNum : 1; //起始批次流水
		int endBatchSeqNo = curBatchSeqNo + batchNum; //截止批次流水
		int startProductSeqNo = (startBatchSeqNo - 1) * processCycleNum; //开始进度流水
		int endProductSeqNo = endBatchSeqNo * processCycleNum; //截止进度流水
		for(int i = 0; i <= (endBatchSeqNo - startBatchSeqNo); i ++){
			Map<String, String> tmpMap = new HashMap<String, String>();
			tmpMap.put("name", "BATCHNO" + String.valueOf(startBatchSeqNo + i));
			tmpMap.put("statusSeqNo", String.valueOf(startBatchSeqNo + i));
			int tmpBatchNo = (startBatchSeqNo + i) % batchCycleNum == 0 ? batchCycleNum : (startBatchSeqNo + i) % batchCycleNum;
			tmpMap.put("label", String.valueOf(tmpBatchNo));
			tableColumnDataList.add(tmpMap);
		};
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("curBatchNo", curBatchNo);
		rtnMap.put("curProductNo", curProductNo);
		rtnMap.put("curBatchSeqNo", curBatchSeqNo);
		rtnMap.put("startProductSeqNo", startProductSeqNo);
		rtnMap.put("endProductSeqNo", endProductSeqNo);
		rtnMap.put("tableColumnData", tableColumnDataList);
		writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtnMap), ResultMessage.SUCCESS);
	}
	
	
	/**
	 * 查询拉动收货监控信息
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月13日 下午3:18:51
	 */
	@RequestMapping("queryJitArrivePage")
	public @ResponseBody PageJson queryJitArrivePage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int batchNum = RequestUtil.getInt(request, "batchNum", 7); //批次范围数量
		int startProductSeqNo = RequestUtil.getInt(request, "startProductSeqNo"); //起始进度流水
		int endProductSeqNo = RequestUtil.getInt(request, "endProductSeqNo"); //截止进度流水
		int curBatchSeqNo = RequestUtil.getInt(request, "curBatchSeqNo"); //当前批次流水
		String workcenter = RequestUtil.getString(request, "workcenter", "AF"); //车间
		String supFactory = RequestUtil.getString(request, "supFactory");
		String supplierName = RequestUtil.getString(request, "supplierName");
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workCenter", workcenter);
		param.put("supFactory", supFactory);
		param.put("supplierName", supplierName);
		param.put("curBatchSeqNo", curBatchSeqNo);
		param.put("startProductSeqNo", startProductSeqNo);
		param.put("endProductSeqNo", endProductSeqNo);
		
		//拼接统计列SQL
		int startBatchSeqNo = curBatchSeqNo - batchNum > 0 ? curBatchSeqNo - batchNum : 1; //起始批次流水
		int endBatchSeqNo = curBatchSeqNo + batchNum; //截止批次流水
		StringBuffer sbf = new StringBuffer();
		for(int i = 0; i <= (endBatchSeqNo - startBatchSeqNo); i ++){
			String tmpBatchSeqNo = String.valueOf(startBatchSeqNo + i); //批次流水
			
			sbf.append( " SUM(DECODE(PLAN_ARR_BATCHSEQNO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_ARR_STATUS)) ");
			sbf.append("VIEW_ARR_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
			
			sbf.append( " SUM(DECODE(PLAN_ARR_BATCHSEQNO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_ONTIME_STATUS)) ");
			sbf.append("VIEW_ONTIME_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
		};
		param.put("monArrSql", sbf.toString());
		
        PageList<Map<String, Object>> pageList = manager.queryJitArrivePage(param, page);
        return new PageJson(pageList);
	}
	
	/**
	 * 查询拉动收货监控明细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月15日 下午2:07:48
	 */
	@RequestMapping("queryJitArriveDetailPage")
	public @ResponseBody PageJson queryJitArriveDetailPage(HttpServletRequest request, HttpServletResponse response){
		String supFactory = RequestUtil.getString(request, "supFactory");
		int batchSeqNo = RequestUtil.getInt(request, "batchSeqNo"); //批次流水
		String workcenter = RequestUtil.getString(request, "workcenter"); //车间
		String arrStatus = RequestUtil.getString(request, "arrStatus"); //到货状态
		String onTimeStatus = RequestUtil.getString(request, "onTimeStatus"); //准时状态
		
		//查询看板批次信息
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param1.put("workCenter", workcenter);
		List<Map<String, Object>> kbBatchList = manager.queryKbBatch(param1);
		Map<String, Object> kbBatch = null;
		if(0 < kbBatchList.size()){
			kbBatch = kbBatchList.get(0);
		}else{
			try {
				writeResultMessage(response.getWriter(), "看板信息获取失败", null,ResultMessage.FAIL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		int curBatchSeqNo = Integer.valueOf(String.valueOf(kbBatch.get("CURR_BATCH_SEQNO"))); //当前批次流水
		int processCycleNum = Integer.valueOf(String.valueOf(kbBatch.get("PROCESS_CYCLE_NUM"))); //进度循环基数
		int startProductSeqNo = (batchSeqNo - 1) * processCycleNum; //开始进度流水
		int endProductSeqNo = batchSeqNo * processCycleNum; //截止进度流水
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workCenter", workcenter);
		param.put("supFactory", supFactory);
		param.put("curBatchSeqNo", curBatchSeqNo); //当前批次流水
		param.put("batchSeqNo", batchSeqNo);
		param.put("startProductSeqNo", startProductSeqNo);
		param.put("endProductSeqNo", endProductSeqNo);
		param.put("arrStatus", arrStatus);
		param.put("onTimeStatus", onTimeStatus);
		
        PageList<Map<String, Object>> pageList = manager.queryJitArriveDetailPage(param, page);
        return new PageJson(pageList);
	}
	
	/**
	 * 查询拉动出货监控信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午9:58:38
	 */
	@RequestMapping("queryJitDeliveryPage")
	public @ResponseBody PageJson queryJitDeliveryPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int batchNum = RequestUtil.getInt(request, "batchNum", 7); //批次范围数量
		int startProductSeqNo = RequestUtil.getInt(request, "startProductSeqNo"); //起始进度流水
		int endProductSeqNo = RequestUtil.getInt(request, "endProductSeqNo"); //截止进度流水
		int curBatchSeqNo = RequestUtil.getInt(request, "curBatchSeqNo"); //当前批次流水
		String workcenter = RequestUtil.getString(request, "workcenter", "AF"); //车间
		String supFactory = RequestUtil.getString(request, "supFactory");
		String supplierName = RequestUtil.getString(request, "supplierName");
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workCenter", workcenter);
		param.put("supFactory", supFactory);
		param.put("supplierName", supplierName);
		param.put("curBatchSeqNo", curBatchSeqNo);
		param.put("startProductSeqNo", startProductSeqNo);
		param.put("endProductSeqNo", endProductSeqNo);
		
		//拼接统计列SQL
		int startBatchSeqNo = curBatchSeqNo - batchNum > 0 ? curBatchSeqNo - batchNum : 1; //起始批次流水
		int endBatchSeqNo = curBatchSeqNo + batchNum; //截止批次流水
		StringBuffer sbf = new StringBuffer();
		for(int i = 0; i <= (endBatchSeqNo - startBatchSeqNo); i ++){
			String tmpBatchSeqNo = String.valueOf(startBatchSeqNo + i); //批次流水
			
			sbf.append( " SUM(DECODE(PLAN_DELIVERY_BATCHSEQNO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_DELIVERY_STATUS)) ");
			sbf.append("VIEW_DELIVERY_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
			
			sbf.append( " SUM(DECODE(PLAN_DELIVERY_BATCHSEQNO, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_ONTIME_STATUS)) ");
			sbf.append("VIEW_ONTIME_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
		};
		param.put("monDeliverySql", sbf.toString());
		
        PageList<Map<String, Object>> pageList = manager.queryJitDeliveryPage(param, page);
        return new PageJson(pageList);
	}
	
	/**
	 * 查询拉动出货监控明细
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午9:58:22
	 */
	@RequestMapping("queryJitDeliveryDetailPage")
	public @ResponseBody PageJson queryJitDeliveryDetailPage(HttpServletRequest request, HttpServletResponse response){
		String supFactory = RequestUtil.getString(request, "supFactory");
		int batchSeqNo = RequestUtil.getInt(request, "batchSeqNo"); //批次流水
		String workcenter = RequestUtil.getString(request, "workcenter"); //车间
		String deliveryStatus = RequestUtil.getString(request, "deliveryStatus"); //到货状态
		String onTimeStatus = RequestUtil.getString(request, "onTimeStatus"); //准时状态
		
		//查询看板批次信息
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param1.put("workCenter", workcenter);
		List<Map<String, Object>> kbBatchList = manager.queryKbBatch(param1);
		Map<String, Object> kbBatch = null;
		if(0 < kbBatchList.size()){
			kbBatch = kbBatchList.get(0);
		}else{
			try {
				writeResultMessage(response.getWriter(), "看板信息获取失败", null,ResultMessage.FAIL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		int curBatchSeqNo = Integer.valueOf(String.valueOf(kbBatch.get("CURR_BATCH_SEQNO"))); //当前批次流水
		int processCycleNum = Integer.valueOf(String.valueOf(kbBatch.get("PROCESS_CYCLE_NUM"))); //进度循环基数
		int startProductSeqNo = (batchSeqNo - 1) * processCycleNum; //开始进度流水
		int endProductSeqNo = batchSeqNo * processCycleNum; //截止进度流水
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workCenter", workcenter);
		param.put("supFactory", supFactory);
		param.put("curBatchSeqNo", curBatchSeqNo); //当前批次流水
		param.put("batchSeqNo", batchSeqNo);
		param.put("startProductSeqNo", startProductSeqNo);
		param.put("endProductSeqNo", endProductSeqNo);
		param.put("deliveryStatus", deliveryStatus);
		param.put("onTimeStatus", onTimeStatus);
		
        PageList<Map<String, Object>> pageList = manager.queryJitDeliveryDetailPage(param, page);
        return new PageJson(pageList);
	}
	
	/**
	 * 查询拉动备货监控信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:06:03
	 */
	@RequestMapping("queryJitPreparePage")
	public @ResponseBody PageJson queryJitPreparePage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int batchNum = RequestUtil.getInt(request, "batchNum", 7); //批次范围数量
		int startProductSeqNo = RequestUtil.getInt(request, "startProductSeqNo"); //起始进度流水
		int endProductSeqNo = RequestUtil.getInt(request, "endProductSeqNo"); //截止进度流水
		int curBatchSeqNo = RequestUtil.getInt(request, "curBatchSeqNo"); //当前批次流水
		String workcenter = RequestUtil.getString(request, "workcenter", "AF"); //车间
		String supFactory = RequestUtil.getString(request, "supFactory");
		String supplierName = RequestUtil.getString(request, "supplierName");
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workCenter", workcenter);
		param.put("supFactory", supFactory);
		param.put("supplierName", supplierName);
		param.put("curBatchSeqNo", curBatchSeqNo);
		param.put("startProductSeqNo", startProductSeqNo);
		param.put("endProductSeqNo", endProductSeqNo);
		
		//拼接统计列SQL
		int startBatchSeqNo = curBatchSeqNo - batchNum > 0 ? curBatchSeqNo - batchNum : 1; //起始批次流水
		int endBatchSeqNo = curBatchSeqNo + batchNum; //截止批次流水
		StringBuffer sbf = new StringBuffer();
		for(int i = 0; i <= (endBatchSeqNo - startBatchSeqNo); i ++){
			String tmpBatchSeqNo = String.valueOf(startBatchSeqNo + i); //批次流水
			
			sbf.append( " SUM(DECODE(PLAN_PRE_BATCHSEQNO_S, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_PRE_STATUS)) ");
			sbf.append("VIEW_PRE_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
			
			sbf.append( " SUM(DECODE(PLAN_PRE_BATCHSEQNO_S, ");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", VIEW_ONTIME_STATUS)) ");
			sbf.append("VIEW_ONTIME_STATUS");
			sbf.append(tmpBatchSeqNo);
			sbf.append(", ");
		};
		param.put("monPrepareSql", sbf.toString());
		
        PageList<Map<String, Object>> pageList = manager.queryJitPreparePage(param, page);
        return new PageJson(pageList);
	}
	
	/**
	 * 查询拉动备货监控明细
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:06:23
	 */
	@RequestMapping("queryJitPrepareDetailPage")
	public @ResponseBody PageJson queryJitPrepareDetailPage(HttpServletRequest request, HttpServletResponse response){
		String supFactory = RequestUtil.getString(request, "supFactory");
		int batchSeqNo = RequestUtil.getInt(request, "batchSeqNo"); //批次流水
		String workcenter = RequestUtil.getString(request, "workcenter"); //车间
		String prepareStatus = RequestUtil.getString(request, "prepareStatus"); //备货状态
		String onTimeStatus = RequestUtil.getString(request, "onTimeStatus"); //准时状态
		
		//查询看板批次信息
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param1.put("workCenter", workcenter);
		List<Map<String, Object>> kbBatchList = manager.queryKbBatch(param1);
		Map<String, Object> kbBatch = null;
		if(0 < kbBatchList.size()){
			kbBatch = kbBatchList.get(0);
		}else{
			try {
				writeResultMessage(response.getWriter(), "看板信息获取失败", null,ResultMessage.FAIL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		int curBatchSeqNo = Integer.valueOf(String.valueOf(kbBatch.get("CURR_BATCH_SEQNO"))); //当前批次流水
		int processCycleNum = Integer.valueOf(String.valueOf(kbBatch.get("PROCESS_CYCLE_NUM"))); //进度循环基数
		int startProductSeqNo = (batchSeqNo - 1) * processCycleNum; //开始进度流水
		int endProductSeqNo = batchSeqNo * processCycleNum; //截止进度流水
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("workCenter", workcenter);
		param.put("supFactory", supFactory);
		param.put("curBatchSeqNo", curBatchSeqNo); //当前批次流水
		param.put("batchSeqNo", batchSeqNo);
		param.put("startProductSeqNo", startProductSeqNo);
		param.put("endProductSeqNo", endProductSeqNo);
		param.put("prepareStatus", prepareStatus);
		param.put("onTimeStatus", onTimeStatus);
		
        PageList<Map<String, Object>> pageList = manager.queryJitPrepareDetailPage(param, page);
        return new PageJson(pageList);
	}
	
}

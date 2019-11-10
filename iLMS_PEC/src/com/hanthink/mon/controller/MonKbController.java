package com.hanthink.mon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanthink.base.model.DictVO;
import com.hanthink.inv.model.InvScanGoodsModel;
import com.hanthink.mon.manager.MonKbManager;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.MonKbModel;
import com.hanthink.mp.model.MpResidualModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hanthink.pup.util.PupUtil;
import com.hotent.base.api.Page;
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
 * 
 * @author luocc
 */
@Controller
@RequestMapping("/mon/monKb")
public class MonKbController extends GenericController {
	@Resource
	private MonKbManager monKbManager;

	private static Logger log = LoggerFactory.getLogger(MonKbController.class);

	/**
	 * @Description:基础看板信息分页查询 (只查厂外同步)
	 * @param: @param
	 *             model
	 * @param: @return
	 *             PageJson
	 * @return: PageList<MonKbModel>
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("queryMonBaseKbPage")
	public @ResponseBody PageJson queryMonBaseKbPage(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") MonKbModel model) throws Exception {
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		model.setKbType("JISO");
		PageList<MonKbModel> pageList = monKbManager.queryMonBaseKbPage(model, page);
		return new PageJson(pageList);
	}

	/**
	 * @Description:基础看板信息单条新增或修改
	 * @param: @param
	 *             model
	 * @param: @return
	 * @return:
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("saveMonBaseKb")
	public void saveMonBaseKb(HttpServletRequest request, HttpServletResponse response, @RequestBody MonKbModel model)
			throws Exception {
		String resultMsg = null;
		String kbCode = model.getKbCode();
		String id = model.getId();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		String workCenter = monKbManager.queryWorkCenterByPlanCode(model);
		model.setWorkcenter(workCenter);
		try {
			if (StringUtil.isNotEmpty(model.getBatchCycleNum()) && StringUtil.isNotEmpty(model.getCurrbatchNo())) {
				if (StringUtil.isNotEmpty(model.getProcessCycleNum())
						&& StringUtil.isNotEmpty(model.getCurrprocessNo())) {
					if (Integer.parseInt(model.getBatchCycleNum()) < Integer.parseInt(model.getCurrbatchNo()) || Integer
							.parseInt(model.getProcessCycleNum()) < Integer.parseInt(model.getCurrprocessNo())) {
						resultMsg = "批数或进度输入有误,当前批数或进度不得大于基数";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
				}
			}
			// 根据ID判断是新增或修改
			if (StringUtil.isEmpty(id)) {
				model.setCreateBy(user.getAccount());
				model.setProductionLine(user.getCurProductLine());
				
				String result = monKbManager.addMonBaseKbOne(model);
				if (result.equals("0")) {
					resultMsg = "已存在看板代码【" + kbCode + "】看板信息";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				} else if (result.equals("1")) {
					resultMsg = "保存成功";
				}
			} else {
				model.setUpdateBy(user.getAccount());
				monKbManager.modifyMonBaseKbOne(model, RequestUtil.getIpAddr(request));
				resultMsg = "保存成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = "系统错误,请联系管理员";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * @Description:基础看板信息批量删除
	 * @param: @param
	 * @param: @return
	 * @return:
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("removeBaseKb")
	public void removeBaseKb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String idsStr = request.getParameter("idsStr");
			String[] ids = idsStr.split(",");
			monKbManager.removeByIds(ids, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * @Description:看板明细查询 (厂外同步、出发链看板)
	 * @param: @param
	 *             model
	 * @param: @return
	 *             PageJson
	 * @return: MonKbModel
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("/queryKbDetail")
	public @ResponseBody MonKbModel queryKbDetail(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") MonKbModel model) throws Exception {
		model = monKbManager.queryKbDetailOne(model);
		if (model == null) {
			return null;
		}
		if (StringUtil.isNotEmpty(model.getCurrbatchNo())) {
			if (Math.abs(Integer.parseInt(model.getCurrbatchNo())) < 10) {
				model.setCurrbatchNo(PupUtil.autoGenericCode(model.getCurrbatchNo(), 2));
			}
		}
		if (StringUtil.isNotEmpty(model.getCurrprocessNo())) {
			if (Math.abs(Integer.parseInt(model.getProcessCycleNum())) < 10) {
				model.setCurrprocessNo(PupUtil.autoGenericCode(model.getProcessCycleNum(), 2));
			}
		}
		if (StringUtil.isNotEmpty(model.getBatchCycleNum())) {
			if (Math.abs(Integer.parseInt(model.getBatchCycleNum())) < 10) {
				model.setBatchCycleNum(PupUtil.autoGenericCode(model.getBatchCycleNum(), 2));
			}
		}
		if (StringUtil.isNotEmpty(model.getProcessCycleNum())) {
			if (Math.abs(Integer.parseInt(model.getProcessCycleNum())) < 10) {
				model.setProcessCycleNum(PupUtil.autoGenericCode(model.getProcessCycleNum(), 2));
			}
		}
		if (StringUtil.isNotEmpty(model.getRunProcessNo())) {
			if (Math.abs(Integer.parseInt(model.getRunProcessNo())) < 10) {
				model.setRunProcessNo(PupUtil.autoGenericCode(model.getRunProcessNo(), 2));
			}
		}
		return model;
	}

	/**
	 * @Description:看板明细设置
	 * @param: @param
	 *             model
	 * @param: @return
	 * @return:
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@RequestMapping("saveKbDetail")
	public void saveKbDetail(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonKbModel model) throws Exception {
		String resultMsg = null;
		String kbId = model.getId();
		IUser user = ContextUtil.getCurrentUser();
		try {
			model.setCreateBy(user.getAccount());
			model.setUpdateBy(user.getAccount());
			model.setModifyIp(RequestUtil.getIpAddr(request));
			String result = monKbManager.saveKbDetail(model);
			if (result.equals("1")) {
				resultMsg = "设置成功";
			} else if (result.equals("2")) {
				resultMsg = "设置成功";
			} else {
				resultMsg = "操作失败";
				writeResultMessage(response.getWriter(), resultMsg, "", ResultMessage.FAIL);
				return;
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = "操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 获取当前登录用户的拣货工程权限
	 * 
	 * @param request
	 * @param response
	 * @date 2018年11月8日
	 */
	@RequestMapping("/queryLoguserJurisdiction")
	public void queryLoguserJurisdiction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		String account = user.getAccount();
		String factoryCode = user.getCurFactoryCode();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("account", account);
		paraMap.put("factoryCode", factoryCode);
		paraMap.put("jurisType", "JIT_PREPARE_PERSON");
		monKbManager.queryLoguserJurisdiction(paraMap);
	}

	// ********************************************************************大物备货看板**********************************************************************//

	/**
	 * 获取产品流水号
	 * <p>
	 * return: void
	 * </p>
	 * <p>
	 * Description: MonKbController.java
	 * </p>
	 * 
	 * @author linzhuo
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getBatchNo")
	public @ResponseBody BigStockKbModel getBatchNo(HttpServletRequest request, HttpServletResponse response,BigStockKbModel eModel)
			throws Exception {
		ResultMessage message = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		if( RequestUtil.getIpAddr(request) != null && !RequestUtil.getIpAddr(request).isEmpty()){
		   paramMap.put("ip", RequestUtil.getIpAddr(request));
		}else{
		   message = new ResultMessage(ResultMessage.FAIL, "IP地址无法获取，请检查现场网络状况！");
		   writeResultMessage(response.getWriter(), message);
		   return null;
		}
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		String kbCode = "0";
		if(null != eModel.getKbCode() || ("").equals(eModel.getKbCode()) ) {
		  kbCode = eModel.getKbCode();
		}
		String checkComp = "(^[\\-0-9][0-9]*(.[0-9]+)?)$";
		boolean isMatch = Pattern.matches(checkComp, kbCode);
		if(isMatch == false) {
			 kbCode = monKbManager.selectKbCodeByIp(paramMap);
		}
		try {
			/**
			 * 根据看板代码获取批次和台数
			 */
			List<BigStockKbModel> list = (List<BigStockKbModel>) monKbManager.getBatchNo(kbCode);
			if(null == list || list.size() == 0){
				message = new ResultMessage(ResultMessage.FAIL, "无法根据此看板代码获取批次和台数！");
				writeResultMessage(response.getWriter(), message);
				return null;
			}
			BigStockKbModel model = list.get(0);
			if (StringUtil.isEmpty(model.getCurrBatchNo())) {
				model.setCurrBatchNo("00");
			}else {
				if (Integer.parseInt(model.getCurrBatchNo()) < 10) {
					model.setCurrBatchNo("0" + model.getCurrBatchNo());
				}
			}
			if (StringUtil.isEmpty(model.getCurrProcessNo())) {
				model.setCurrProcessNo("00");
			}else {
				if (Integer.parseInt(model.getCurrProcessNo()) < 10) {
					model.setCurrProcessNo("0" + model.getCurrProcessNo());
				}
			}		
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
		return null;
	}

	/**
	 * 根据产品流水号，看板代码查询单头（工程）
	 * 
	 * @param 林卓
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBigStockKb")
	public @ResponseBody List<BigStockKbModel> getBigStockKb(HttpServletRequest request, HttpServletResponse response,
			BigStockKbModel model) throws Exception {
		ResultMessage message = null;
		String productSeqNo = model.getProductSeqNo();
		/**
		 * 用户维护数值
		 */
		String paramVal = monKbManager.selectProductSeqNoByParamCode(productSeqNo);
		Integer finalVal = Integer.parseInt(productSeqNo) + Integer.parseInt(paramVal);
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		if(("").equals(factoryCode)||null == factoryCode) {
			factoryCode = "2000";
		}
		/**
		 * IP地址
		 */
		String ip = RequestUtil.getIpAddr(request);
		String workCenter = model.getWorkCenter();
		if(("{}").equals(workCenter)||("").equals(workCenter) || null == workCenter) {
			workCenter = monKbManager.selectWorkCenterByIp(ip);
		}
		try {
			List<BigStockKbModel> list = new ArrayList<>();
			if(("A1").equals(workCenter)) {
				/**
				 * 说明是总装
				 */
				list = monKbManager.getBigStockKbAC(finalVal.toString(),factoryCode,workCenter);
			}else if(("W1").equals(workCenter)){
				/**
				 * 说明是焊装
				 */
				list = monKbManager.getBigStockKbWC(finalVal.toString(),factoryCode,workCenter);
			}
			if(list.size()==0) {
				list = monKbManager.selectBigStockKbByNull();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
		return null;
	}

	/**
	 * 查询明细（零件）
	 * 
	 * @param 林卓
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBigStockKbDetail")
	public @ResponseBody JSONArray getBigStockKbDetail(HttpServletRequest request,
			HttpServletResponse response, @RequestBody List<String> modelList) throws Exception {
		ResultMessage message = null;
		try {
			JSONArray jsonArray = new JSONArray();
			/**
			 * 保留明细信息
			 */
			for(int i=0;i<modelList.size();i++) {
				String distriPerson = modelList.get(i);
				List<String> detailList = monKbManager.getBigStockKbDetail(distriPerson);
				jsonArray.add(detailList);
			}
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "执行失败");
		}
		writeResultMessage(response.getWriter(), message);
		return null;
	}

	// ********************************************************************大物备货看板**********************************************************************//

	/**
	 * @Description:看板批次查询 (供应商、厂内拉动看板)
	 * @param: @param
	 *             model
	 * @param: @return
	 *             PageJson
	 * @return: MonKbModel
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("queryBatchKb")
	public @ResponseBody MonKbModel queryBatchKb(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") MonKbModel model) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		if (StringUtil.isNotEmpty(model.getIp())) {
			model = monKbManager.queryForKbConfig(model);
		}else {
			if(StringUtil.isEmpty(model.getSkweProcessNo())) {
				model.setSkweProcessNo("0");
			}
			model = monKbManager.selectBatchBySeqAndSkew(model);
		}
		
		if (StringUtil.isEmpty(model.getCurrbatchNo())) {
			model.setCurrbatchNo("00");
		}else {
			if (Integer.parseInt(model.getCurrbatchNo()) < 10) {
				model.setCurrbatchNo("0" + model.getCurrbatchNo());
			}
		}
		if (StringUtil.isEmpty(model.getCurrprocessNo())) {
			model.setCurrprocessNo("00");
		}else {
			if (Integer.parseInt(model.getCurrprocessNo()) < 10) {
				model.setCurrprocessNo("0" + model.getCurrprocessNo());
			}
		}		
		return model;
		
//		if (null != model.getSkweProcessNo()) {// 厂内拉动
//			model = monKbManager.selectBatchBySeqAndSkew(model);
//			if (Integer.parseInt(model.getCurrbatchNo()) < 10) {
//				model.setCurrbatchNo("0" + model.getCurrbatchNo());
//			}
//			if (Integer.parseInt(model.getCurrprocessNo()) < 10) {
//				model.setCurrprocessNo("0" + model.getCurrbatchseqNo());
//			}
//			return model;
//		} else {// 供应商
//			model = monKbManager.selectBatchBySeq(model);
//			if (Integer.parseInt(model.getCurrbatchNo()) < 10) {
//				model.setCurrbatchNo("0" + model.getCurrbatchNo());
//			}
//			if (Integer.parseInt(model.getCurrprocessNo()) < 10) {
//				model.setCurrprocessNo("0" + model.getCurrprocessNo());
//			}
//			return model;
//		}
	}

	/**
	 * @Description:看板偏移进度设置
	 * @param: @param
	 *             model
	 * @param: @return
	 * @return:
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@RequestMapping("setSkwe")
	public void setSkwe(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("model") MonKbModel model) throws Exception {
		String resultMsg = null;
		try {
			model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
			model.setLastModifiedIp(RequestUtil.getIpAddr(request));
			monKbManager.updateSkewProcessNo(model, RequestUtil.getIpAddr(request));
			resultMsg = "保存偏移进度成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg = "操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 看板信息下拉框封装
	 * 
	 * @param kbKey(字段)
	 *            kbType 看板类型（JISO JIT）
	 * @author luocc
	 * @date 2018年11月8日
	 */
	@RequestMapping("queryKbOptions")
	public @ResponseBody List queryKbOptions(HttpServletRequest request, HttpServletResponse response) {
		String kbKey = RequestUtil.getString(request, "kbKey", null);
		String kbType = RequestUtil.getString(request, "kbType", null);
		String remark = RequestUtil.getString(request, "remark");
		if (StringUtil.isEmpty(kbKey) || StringUtil.isEmpty(kbType)) {
			return null;
		}
		MonKbModel mo = new MonKbModel();
		mo.setKbType(kbType);
		if (StringUtil.isNotEmpty(remark) && !"undefined".equals(remark)) {
			mo.setRemark(remark);			
		}

		List<DictVO> codeType = null;
		switch (kbKey) {
		case "kbName":
			codeType = monKbManager.queryForMonKbName(mo);
			break;
		case "lampId":
			codeType = monKbManager.queryForLampId();
			break;
		case "distriPerson":
			codeType = monKbManager.queryForDistriPerson(mo);
			break;
		default:
			break;
		}
		List<Map<String, Object>> kbDictList = new ArrayList<Map<String, Object>>();
		for (DictVO dict : codeType) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", dict.getValueKey());
			map.put("label", dict.getValueName());
			kbDictList.add(map);
		}
		return kbDictList;
	}

	@RequestMapping("/queryKbConfig")
	public @ResponseBody PageJson queryForKbConfig(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Page page = getQueryFilter(request).getPage();
		PageList<MonKbModel> pageList = monKbManager.queryForKbConfig(page);
		return new PageJson(pageList);
	}

	@RequestMapping("/getLoactionIP")
	public @ResponseBody MonKbModel getLoactionShowMessage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ip = RequestUtil.getIpAddr(request);
		return monKbManager.getLoactionShowMessage(ip);
	}

	@RequestMapping("/updateCurrentKbStatus")
	public void updateCurrentKbStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestBody MonKbModel model) throws Exception {
		model.setIp(RequestUtil.getIpAddr(request));
		try {
			monKbManager.updateCurrentKbStatus(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/queryDistriPersonForSelect")
	public @ResponseBody List queryDistriPersonForSelect(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String kbCode = RequestUtil.getString(request, "kbCode");
		
		MonKbModel model = new MonKbModel();
		model.setKbCode(kbCode);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<DictVO> list = monKbManager.queryDistriPersonForSelect(model);
		List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
		for (DictVO dictVO : list) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("value", dictVO.getValueKey());
			resMap.put("label", dictVO.getValueName());
			resList.add(resMap);
		}
		return resList;
	}
	
	/**
	 * @Description: 获取冲压配送指示 
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param model
	 * @param: @return    
	 * @return: MonKbModel   
	 * @author: dtp
	 * @date: 2019年4月22日
	 */
	@RequestMapping("queryStampKb")
	public @ResponseBody MonKbModel queryStampKb(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") MonKbModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MonKbModel> list = monKbManager.queryStampKb(model);
		MonKbModel model_res = new MonKbModel();
		if(null != list && list.size() == 1) {
			if (StringUtil.isEmpty(model_res.getCurrbatchNo())) {
				model_res.setCurrbatchNo("00");
			}else {
				if (Integer.parseInt(model_res.getCurrbatchNo()) < 10) {
					model_res.setCurrbatchNo("0" + model_res.getCurrbatchNo());
				}
			}
			if (StringUtil.isEmpty(model_res.getCurrprocessNo())) {
				model_res.setCurrprocessNo("00");
			}else {
				if (Integer.parseInt(model_res.getCurrprocessNo()) < 10) {
					model_res.setCurrprocessNo("0" + model_res.getCurrbatchseqNo());
				}
			}		
		}
		return model_res;
	}
	
	/**
	 * @Description: 获取电池车间指示  
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param model
	 * @param: @return    
	 * @return: MonKbModel   
	 * @author: dtp
	 * @date: 2019年4月22日
	 */
	@RequestMapping("queryBatteryKb")
	public @ResponseBody List<MonKbModel> queryBatteryKb(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") MonKbModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MonKbModel> list = monKbManager.queryBatteryKb(model);
		return list;
	}
	
	
}

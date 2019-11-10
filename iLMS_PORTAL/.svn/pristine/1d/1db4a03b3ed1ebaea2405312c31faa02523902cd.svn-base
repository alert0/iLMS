package com.hanthink.mon.controller;

import java.util.ArrayList;
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

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.manager.MonKbManager;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.MonKbModel;
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
public class MonKbController extends GenericController{
	@Resource
	private MonKbManager monKbManager;
	
	private static Logger log = LoggerFactory.getLogger(MonKbController.class);
	
	/**
	 * @Description:基础看板信息分页查询 (只查厂外同步)
	 * @param: @param model
	 * @param: @return  PageJson
	 * @return: PageList<MonKbModel>   
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("queryMonBaseKbPage")
	public @ResponseBody PageJson queryMonBaseKbPage(HttpServletRequest request,HttpServletResponse reponse,
            @ModelAttribute("model") MonKbModel model) throws Exception{
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		model.setKbType("JISO");
		PageList<MonKbModel> pageList = monKbManager.queryMonBaseKbPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description:基础看板信息单条新增或修改
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("saveMonBaseKb")
	public void saveMonBaseKb(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MonKbModel model) throws Exception {
		String resultMsg = null;
		String kbCode=model.getKbCode();
		String id=model.getId();
		IUser user = ContextUtil.getCurrentUser();
		try {	
			if (StringUtil.isNotEmpty(model.getBatchCycleNum()) && StringUtil.isNotEmpty(model.getCurrbatchNo())) {
				if (StringUtil.isNotEmpty(model.getProcessCycleNum()) && StringUtil.isNotEmpty(model.getCurrprocessNo())) {
					if(Integer.parseInt(model.getBatchCycleNum()) < Integer.parseInt(model.getCurrbatchNo()) 
							|| Integer.parseInt(model.getProcessCycleNum()) < Integer.parseInt(model.getCurrprocessNo())) {
						resultMsg="批数或进度输入有误,当前批数或进度不得大于基数";
						writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
						return;
					}
				}
			}
			//根据ID判断是新增或修改
			if(StringUtil.isEmpty(id)) {
				model.setFactoryCode(user.getCurFactoryCode());
				model.setCreateBy(user.getAccount());
				model.setProductionLine(user.getCurProductLine());
				String result=monKbManager.addMonBaseKbOne(model);
				if(result.equals("0")) {
					resultMsg="已存在看板代码【"+kbCode+"】看板信息";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return;
				}else if(result.equals("1")){
					resultMsg="添加成功";
				}
			}else {
				model.setUpdateBy(user.getAccount());	
				monKbManager.modifyMonBaseKbOne(model, RequestUtil.getIpAddr(request));
				resultMsg="更新成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
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
	public void removeBaseKb(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
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
	 * @param: @param model
	 * @param: @return  PageJson
	 * @return: MonKbModel  
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("queryKbDetail")
	public @ResponseBody MonKbModel queryKbDetail(HttpServletRequest request,HttpServletResponse reponse,
            @ModelAttribute("model") MonKbModel model) throws Exception{
		return monKbManager.queryKbDetailOne(model);
	}
	
	/**
	 * @Description:看板明细设置
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@RequestMapping("saveKbDetail")
	public void saveKbDetail(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") MonKbModel model) throws Exception {
		String resultMsg = null;
		String kbId=model.getId();
		IUser user = ContextUtil.getCurrentUser();
		try {
			model.setCreateBy(user.getAccount());
			model.setUpdateBy(user.getAccount());
			model.setModifyIp(RequestUtil.getIpAddr(request));
			String result=monKbManager.saveKbDetail(model);
				 if(result.equals("1")){
					resultMsg="设置成功";
				}else if(result.equals("2")) {
					resultMsg="设置成功";
				}else{
					resultMsg="操作失败";
					writeResultMessage(response.getWriter(),resultMsg,"",ResultMessage.FAIL);
					return;
				}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	/**
	 * 获取当前登录用户的拣货工程权限
	 * @param request
	 * @param response
	 * @date 2018年11月8日
	 */
	@RequestMapping("/queryLoguserJurisdiction")
	public void queryLoguserJurisdiction(HttpServletRequest request, HttpServletResponse response)throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		String account = user.getAccount();
		String factoryCode = user.getCurFactoryCode();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("account", account);
		paraMap.put("factoryCode", factoryCode);
		paraMap.put("jurisType", "JIT_PREPARE_PERSON");
		monKbManager.queryLoguserJurisdiction(paraMap);
	}
	/**
	 * 大物备货监控看板数据详情查看
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryStockKbDetails")
	public @ResponseBody PageJson queryStockKbDetails(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		List<BigStockKbModel> list = monKbManager.queryStockKbDetails(paramMap);
		return new PageJson(list);
	}
	
	/**
	 * @Description:看板批次查询 (供应商、厂内拉动看板)
	 * @param: @param model
	 * @param: @return  PageJson
	 * @return: MonKbModel  
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@RequestMapping("queryBatchKb")
	public @ResponseBody MonKbModel queryBatchKb(HttpServletRequest request,HttpServletResponse reponse,
            @ModelAttribute("model") MonKbModel model) throws Exception{
		if(null != model.getSkweProcessNo()) {//厂内拉动
			return monKbManager.selectBatchBySeqAndSkew(model);
		}else {//供应商
			return monKbManager.selectBatchBySeq(model);
		}
	}
	/**
	 * @Description:看板偏移进度设置
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@RequestMapping("setSkwe")
	public void setSkwe(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") MonKbModel model) throws Exception {
		String resultMsg = null;
		try {
			model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
			model.setLastModifiedIp(RequestUtil.getIpAddr(request));
			monKbManager.updateSkewProcessNo(model, RequestUtil.getIpAddr(request));
			resultMsg="保存偏移进度成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	/**
	 * 看板信息下拉框封装
	 * @param kbKey(字段)  kbType 看板类型（JISO JIT）
	 * @author luocc  
	 * @date 2018年11月8日
	 */
	@RequestMapping("queryKbOptions")
	public @ResponseBody List queryKbOptions(HttpServletRequest request,HttpServletResponse response){
		String kbKey = RequestUtil.getString(request, "kbKey", null);
		String kbType = RequestUtil.getString(request, "kbType", null);
		if(StringUtil.isEmpty(kbKey) || StringUtil.isEmpty(kbType)) {
			return null;
		}
		MonKbModel mo=new MonKbModel();
		mo.setKbType(kbType);
		
		List<DictVO> codeType =null;
		switch (kbKey) {
			case "kbName":
				codeType=monKbManager.queryForMonKbName(mo);
				break;
			case "lampId":
				codeType=monKbManager.queryForLampId();
				break;
			case "distriPerson":
				codeType=monKbManager.queryForDistriPerson(mo);
				break;
			default:break;
		}
		List<Map<String, Object>> kbDictList = new ArrayList<Map<String, Object>>();
		for(DictVO dict : codeType){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", dict.getValueKey());
			map.put("label", dict.getValueName());
			kbDictList.add(map);
		}
		return kbDictList;
	}
}

package com.hanthink.jiso.controller;

import java.io.IOException;
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

import com.hanthink.jiso.manager.JisoSupKBManager;
import com.hanthink.jiso.model.JisoOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * @Desc		: 厂外同步出货地切换看板
 * @FileName	: JisoSupKBController.java
 * @CreateOn	: 2018年11月21日 上午9:43:11
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
@Controller
@RequestMapping("/jiso/jisoSupKB")
public class JisoSupKBController extends GenericController {
	
	private static Logger log = LoggerFactory.getLogger(JisoSupKBController.class);
	
	@Resource
	private JisoSupKBManager manager;
	
	@RequestMapping("queryJisoSupKB")
	public @ResponseBody PageJson queryJisoSupKB(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int batchNum = RequestUtil.getInt(request, "queryBatchNum", 15); //从界面获取查询批次数量 默认15
		
		//拼接动态列
		StringBuffer sbf = new StringBuffer();
		for(int i = 1; i <= batchNum; i ++){
			if(i < batchNum){
				sbf.append(i);
				sbf.append(" PO");
				sbf.append(i);
				sbf.append(", ");
			}else{
				sbf.append(i);
				sbf.append(" PO");
				sbf.append(i);
				sbf.append(" ");
			}
		}
		
		Page page = getQueryFilter(request).getPage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		param.put("batchNum", batchNum);
		param.put("columnSql", sbf.toString());
		
        PageList<Map<String, Object>> pageList = manager.queryJisoSupKB(param, page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        for (int i = 0; i < pageList.size(); i++) {
        	Map<String, Object> map = pageList.get(i);
			Map<String, Object> newmap = new HashMap<String, Object>();
			newmap.put("PARTGROUP_NO", String.valueOf(map.get("PARTGROUP_NO")));
			newmap.put("PARTGROUP_NAME", String.valueOf(map.get("PARTGROUP_NAME")));
			newmap.put("PARTGROUP_NO_2", String.valueOf(map.get("PARTGROUP_NO_2")));
			newmap.put("PARTGROUP_NAME_2", String.valueOf(map.get("PARTGROUP_NAME_2")));
			newmap.put("PLAN_CODE", String.valueOf(map.get("PLAN_CODE")));
			map.put("PARTGROUP_NO", "");
			map.put("PARTGROUP_NAME", "");
			for(int j = 1; j <= batchNum; j ++){
				dealMap(map, newmap, "PO"+j);
			}
			list.add(newmap);
			list.add(map);
		}
        
        return new PageJson(new PageList<>(list));
	}
	
	/**
	 * @Description:   处理时间
	 * @param:     
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月19日
	 */
	private static void dealMap(Map<String, Object> map, Map<String, Object> newmap, String key) {
		String[] poinfoArr = String.valueOf(map.get(key)).split(",");
		if(null != poinfoArr && poinfoArr.length == 4) {
			String factoryCode = poinfoArr[0];
			String orderNo = poinfoArr[1];
			String delFlg = poinfoArr[2];
			String timeStr = poinfoArr[3];
			newmap.put(key,timeStr+","+orderNo+","+delFlg);
			map.put(key,factoryCode+","+orderNo+","+delFlg);
		}else {
			newmap.put(key, "");
			map.put(key, "");
		}
		
	}

	
	/**
	 * 更新派车处理信息
	 * @param request
	 * @param response
	 * @param param
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:18:09
	 */
	@RequestMapping("updateDealData")
	public void updateDealData(HttpServletRequest request,HttpServletResponse response,
			@RequestBody Map<String, Object> param) throws Exception{
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		param.put("DEAL_USER", user.getAccount());
		param.put("DEAL_IP", RequestUtil.getIpAddr(request));
		
		try {
			manager.updateDealData(param);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 查询出货地切换派车汇总
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("queryJisoSupCarPage")
	public @ResponseBody PageJson queryJisoSupCarPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JisoOrderModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JisoOrderModel> pageList = manager.queryJisoSupCarPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 更新车牌 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2019年2月20日
	 */
	@RequestMapping("updateJisoSupCar")
	public void updateJisoSupCar(HttpServletRequest request,HttpServletResponse response,
			@RequestBody JisoOrderModel[] modelArr) throws IOException {
		try {
			IUser user = ContextUtil.getCurrentUser();
			List<JisoOrderModel> list = new ArrayList<JisoOrderModel>();
			for (JisoOrderModel model : modelArr) {
				model.setManuReqUser(user.getAccount());
				model.setManuReqIp(RequestUtil.getIpAddr(request));
				list.add(model);
			}
			manager.updateJisoSupCar(list, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "保存成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * @Description: 派车处理
	 * @param: @param request
	 * @param: @param response
	 * @param: @param modelArr
	 * @param: @throws IOException    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月21日
	 */
	@RequestMapping("updateCarNo")
	public void updateCarNo(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JisoOrderModel model) throws IOException {
		try {
			IUser user = ContextUtil.getCurrentUser();
			model.setManuReqUser(user.getAccount());
			model.setManuReqIp(RequestUtil.getIpAddr(request));
			manager.updateCarNo(model, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "保存成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "系统错误,请联系管理员",e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	

}

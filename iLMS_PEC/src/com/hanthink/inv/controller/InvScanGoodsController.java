package com.hanthink.inv.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.inv.manager.InvScanGoodsManager;
import com.hanthink.inv.model.InvScanGoodsModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * <pre> 扫苗圃收货控制器类
 * 功能描述:
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/inv/scanGoods")
@Controller
public class InvScanGoodsController extends GenericController{
	
	@Resource
	private InvScanGoodsManager scanGoodsManager;
	/**
	 * 扫描订单详情控制器
	 * @param request
	 * @param response
	 * @return
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@RequestMapping("/scanGoods")
	public @ResponseBody PageJson scanGoods(HttpServletRequest request,HttpServletResponse response,
											InvScanGoodsModel model) {
		
		Page page = getQueryFilter(request).getPage();
		
		List<InvScanGoodsModel> list = null;
		InvScanGoodsModel resultModel = new InvScanGoodsModel();
		try {
			list = scanGoodsManager.scanGoods(model,page);
			return new PageJson(new PageList<InvScanGoodsModel>(list));
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<InvScanGoodsModel>();
			resultModel.setResultMsg(e.getMessage());
			list.add(resultModel);
			return new PageJson(list);
		}
	}
	/**
	 * 确认收货提交控制器
	 * @param request
	 * @param response
	 * @author zmj
	 * @throws Exception 
	 * @date 2018年10月11日
	 */
	@RequestMapping("/confirmReceipt")
	public void confirmReceipt(HttpServletRequest request,HttpServletResponse response,@RequestBody List<InvScanGoodsModel> list) throws Exception {
		String ipAddr = RequestUtil.getIpAddr(request);
		String resultMsg = null;
		
		try {
			//收货处理
			scanGoodsManager.dealOrderSh(list,ipAddr);
			resultMsg = "订单"+list.get(0).getOrderNo()+"收货成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg,resultMsg,ResultMessage.FAIL);
		}
	}
	
}

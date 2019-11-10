package com.hanthink.inv.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.inv.manager.InvWareHouseManager;
import com.hanthink.inv.model.InvWareHouseModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * <pre> 
 * 功能描述:仓库管理控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月8日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@RequestMapping("/inv/wareHouse")
@Controller
public class InvWareHouseController extends GenericController{
	
	@Resource
	private InvWareHouseManager wareHouseManager;
	
	/**
	 * 仓库信息分页查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@RequestMapping("/queryWareHouse")
	public @ResponseBody PageJson queryWareHouseForPage(HttpServletRequest request,HttpServletResponse response,
											InvWareHouseModel model)throws Exception{
		
		Page page = getQueryFilter(request).getPage();
		
		PageList<InvWareHouseModel> pageList = wareHouseManager.queryWareHouseForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 修改/新增仓库数据控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月8日
	 */
	@RequestMapping("/saveWareHouse")
	public void saveEditForWareHouse(HttpServletRequest request,HttpServletResponse response,
								@RequestBody InvWareHouseModel model)throws Exception {
		String resultMsg = null;
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				wareHouseManager.createWareHouse(model);
				resultMsg = "添加仓库信息成功";
			}else {
				wareHouseManager.updateWareHouse(model,RequestUtil.getIpAddr(request));
				resultMsg = "修改仓库信息成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			if(StringUtil.isEmpty(id)) {
				resultMsg = e.getMessage();
			}else {
				resultMsg = e.getMessage();
			}
			writeResultMessage(response.getWriter(),resultMsg,resultMsg,ResultMessage.FAIL);
		}
	}
	/**
	 * 单条/批量删除业务控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@RequestMapping("/removeWareHouse")
	public void deleteWareHouseByIds(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ResultMessage resultMsg = null;
		String[] ids = RequestUtil.getStringAryByStr(request, "id");
		String ipAddr = RequestUtil.getIpAddr(request);
		try {
			
			wareHouseManager.deleteWareHouseByIds(ids,ipAddr);
			resultMsg = new ResultMessage(ResultMessage.SUCCESS, "删除数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = new ResultMessage(ResultMessage.FAIL, "删除数据失败");
		}
		writeResultMessage(response.getWriter(), resultMsg);
	}
	/**
	 * 获取ERP仓库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月16日
	 */
	@RequestMapping("/queryERPWareCode")
	public @ResponseBody List<InvWareHouseModel> queryERPWareCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return wareHouseManager.queryERPWareCode();
	}
	@RequestMapping("/queryWareType")
	public @ResponseBody List<InvWareHouseModel> queryWareType(HttpServletRequest request,HttpServletResponse response)throws Exception{
		return wareHouseManager.queryWareType();
	}
}

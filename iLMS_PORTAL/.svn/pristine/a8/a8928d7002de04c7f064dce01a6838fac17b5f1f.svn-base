package com.hanthink.inv.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.inv.manager.InvEmptyManager;
import com.hanthink.inv.model.InvEmptyModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
/**
 * <pre> 
 * 功能描述:空容器库存查询业务控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
@RequestMapping("/inv/empty")
@Controller
public class InvEmptyController extends GenericController{
	@Resource
	private InvEmptyManager emptyManager;
	/**
	 * 空容器分页查询控制器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@RequestMapping("/queryForPage")
	public @ResponseBody PageJson queryEmptyForPage(HttpServletRequest request,HttpServletResponse response,
											InvEmptyModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		PageList<InvEmptyModel> pageList = emptyManager.queryEmptyForPage(model,page);
		
		return new PageJson(pageList);
	}
	/**
	 * 修改空容器数据控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@RequestMapping("/updateForEmpty")
	public void updateForEmpty(HttpServletRequest request,HttpServletResponse response,
						@RequestBody InvEmptyModel model)throws Exception {
		try {
			emptyManager.updateForEmpty(model);
			writeResultMessage(response.getWriter(), "更新成功", model.getId(), ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "操作失败", e.getMessage(), ResultMessage.FAIL);
		}
	}
}

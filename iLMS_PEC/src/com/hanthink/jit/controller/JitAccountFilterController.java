package com.hanthink.jit.controller;

import java.io.IOException;
import java.util.List;

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

import com.hanthink.jit.manager.JitAccountFilterManager;
import com.hanthink.jit.model.JitAccountFilterModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: JitAccountFilterController
 * @Description: 拉动订单屏蔽结算维护
 * @author dtp
 * @date 2018年11月3日
 */
@Controller
@RequestMapping("/jit/jitAccountFilter")
public class JitAccountFilterController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(JitAccountFilterController.class);
	
	@Resource
	private JitAccountFilterManager jitAccountFilterManager;
	
	/**
	 * @Description: 拉动订单结算屏蔽查询  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@RequestMapping("queryJitAccountFilterPage")
	public @ResponseBody PageJson queryJitAccountFilterPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") JitAccountFilterModel model) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<JitAccountFilterModel> pageList = jitAccountFilterManager.queryJitAccountFilterPage(model, page);
		return new PageJson(pageList);
		
	}
	
	/**
	 * @Description: 新增拉动订单结算屏蔽 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月3日
	 */
	@RequestMapping("saveJitAccountFilter")
	public void saveJitAccountFilter(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitAccountFilterModel model) throws IOException {
		IUser user = ContextUtil.getCurrentUser();
		List<JitAccountFilterModel> list = jitAccountFilterManager.queryIsExists(model);
		if(null != list && list.size() > 0) {
			writeResultMessage(response.getWriter(),"数据已存在",ResultMessage.FAIL);
			return;
		}
		try {
			model.setCreationUser(user.getAccount());
			model.setLastModifiedIp(RequestUtil.getIpAddr(request));
			jitAccountFilterManager.insertJitAccountFilter(model);
			writeResultMessage(response.getWriter(), "保存成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"保存失败",e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * @Description: 删除拉动订单结算屏蔽   
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月3日
	 */
	@RequestMapping("deleteJitAccountFilter")
	public void deleteJitAccountFilter(HttpServletRequest request,HttpServletResponse response,
			@RequestBody JitAccountFilterModel[] models) throws IOException {
		try {
			jitAccountFilterManager.deleteJitAccountFilter(models, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(), "删除成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"删除失败",ResultMessage.FAIL);
		}
	}
	
	
}

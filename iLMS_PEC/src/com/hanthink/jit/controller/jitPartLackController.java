package com.hanthink.jit.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.jit.manager.jitPartLackManager;
import com.hanthink.jit.model.JitPartLackModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


@Controller
@RequestMapping("/jit/partLack")
public class jitPartLackController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(jitPartLackController.class);
	
	@Resource
	private jitPartLackManager manager;
	
	/**
	 * 
	 * @Description: 分也查询拉动零件缺件记录信息
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月15日 下午9:55:23
	 */
	@RequestMapping("getJitPartLackList")
	public @ResponseBody PageJson  getJitPartLackList(HttpServletRequest request , HttpServletResponse response, 
			@ModelAttribute("model") JitPartLackModel model) {
		DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		PageList<JitPartLackModel> pageList = (PageList<JitPartLackModel>) manager.getJitPartLackList(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 
	 * @Description: 查询明细
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return   
	 * @return List<JitPartLackModel>  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月16日 下午3:34:46
	 */
	@RequestMapping("getJitPartLackDetialList")
	public @ResponseBody PageJson getJitPartLackDetialList(HttpServletRequest request, HttpServletResponse response,
			JitPartLackModel model) throws Exception {
		try {
			DefaultPage page = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
					getQueryFilter(request).getPage().getPageSize()));
			
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());
			PageList<JitPartLackModel> list = (PageList<JitPartLackModel>) manager.getJitPartLackDetialList(model,page);
			return new PageJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 处理
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月16日 下午3:53:13
	 */
	@RequestMapping("jitPartLackCheck")
	public void jitPartLackCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message=null;
		try {
			String ids = RequestUtil.getString(request, "ids");
			JitPartLackModel model = new JitPartLackModel();
			String dealIp = RequestUtil.getIpAddr(request);
			String dealUser = ContextUtil.getCurrentUser().getAccount();
			
			model.setDealIp(dealIp);
			model.setDealUser(dealUser);
			String []idArr = ids.split(",");
			manager.jitPartLackCheck(idArr,model);
			message = new ResultMessage(ResultMessage.SUCCESS, "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
}

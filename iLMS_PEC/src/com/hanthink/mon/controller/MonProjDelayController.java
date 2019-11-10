package com.hanthink.mon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mon.manager.MonProjDelayManager;
import com.hanthink.mon.model.MonProjDelayModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

@Controller
@RequestMapping("/mon/projDelay")
public class MonProjDelayController extends GenericController{
	
	@Resource
	private MonProjDelayManager projManager;
	
	@RequestMapping("/queryProjDealyForPage")
	public @ResponseBody PageJson queryProjDealyForPage(HttpServletRequest request,HttpServletResponse response,
													MonProjDelayModel model)throws Exception {
		DefaultPage page = new DefaultPage(new RowBounds(1, 2000));
		String workCenter = RequestUtil.getString(request, "workCenter");
		model.setWorkCenter(workCenter);
		PageList<MonProjDelayModel> pageList = projManager.queryProjDealyForPage(model, page);
		return new PageJson(pageList);
	}
}

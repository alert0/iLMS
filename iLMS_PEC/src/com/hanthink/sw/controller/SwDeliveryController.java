package com.hanthink.sw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sw.manager.SwDeliveryManager;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwDeliveryModel;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;

@Controller
@RequestMapping("/sw/swDelivery")
public class SwDeliveryController extends GenericController{

	@Resource
	private SwDeliveryManager manager;
	
	/**
	 * 
	 * <p>Title: queryJisoGroupPage</p>  
	 * <p>Description: 发货数据管理，分页查询</p>  
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:11:45
	 */
	@RequestMapping("queryJisoDeliveryPage")
	public @ResponseBody PageJson queryJisoDeliveryPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwDeliveryModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionKey.SW_SUPPLIER_GROUP_QUERYFILTER, model);
        
        PageList<SwDeliveryModel> pageList = (PageList<SwDeliveryModel>) manager.queryJisoDeliveryPage(model, p);
        return new PageJson(pageList);
	}
	
	@RequestMapping("queryJisoDeliveryDetailPage")
	public @ResponseBody PageJson queryJisoDeliveryDetailPage(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") SwDeliveryModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionKey.SW_SUPPLIER_GROUP_QUERYFILTER, model);
        
        PageList<SwDeliveryModel> pageList = (PageList<SwDeliveryModel>) manager.queryJisoDeliveryDetailPage(model, p);
        return new PageJson(pageList);
	}
}

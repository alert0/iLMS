package com.hanthink.sys.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sys.manager.SysPdaMenuManager;
import com.hanthink.sys.model.SysPdaMenuModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：PDA菜单权限管理
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sys/sysPdaMenu")
public class SysPdaMenuController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(SysPdaMenuController.class);
	@Resource
	SysPdaMenuManager sysPdaMenuManager;
	
	/**
	 * 分页查询PDA菜单权限
	 * @param request
	 * @param reponse
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
	        HttpServletResponse reponse, SysPdaMenuModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			/**
			 * 判断当前登录人是否为超级用户
			 */
			if(ContextUtil.getCurrentUser().isAdmin()) {
				model.setOpeUser("admin");
			}else {
				model.setOpeUser(ContextUtil.getCurrentUser().getUserId());
			}
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<SysPdaMenuModel> pageList = (PageList<SysPdaMenuModel>) sysPdaMenuManager.querySysPdaMenuForPage(model,
					p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 右边栏位显示查询结果
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("getRowClick")
	public @ResponseBody PageJson getRowClick(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		/**
		 * 获取用户名
		 */
		String userName = RequestUtil.getString(request, "userName");
		DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
		PageList<SysPdaMenuModel> pageList = (PageList<SysPdaMenuModel>)sysPdaMenuManager.getRowClick(userName,p);
		
		return new PageJson(pageList);
	}
	
	 /**
     * 根据用户ID查询该用户待添加的菜单权限信息
     * @param request
     * @param response
     * @param model
     * @return
     * @author linzhuo	
     * @DATE	2018年12月26日 下午10:32:06
     */
    @RequestMapping("queryAddPdaMenuByUserName")
    public @ResponseBody PageJson queryAddPdaMenuByUserName(HttpServletRequest request,
    		HttpServletResponse response) {
		try {
			String userName = RequestUtil.getString(request, "userName");
			String menuDesc = RequestUtil.getString(request, "menuDesc");
			DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
			PageList<SysPdaMenuModel> pageList = sysPdaMenuManager.queryAddPdaMenuByUserName(userName,menuDesc, p);
			
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
	
    /**
     * 添加用户的菜单权限信息，多个菜单权限ID用英文逗号分隔
     * @param request
     * @param response
     * @param model
     * @return
     * @author linzhuo	
     * @DATE	2018年12月26日 下午10:35:45
     */
    @RequestMapping("addPdaMenu")
    public void addPdaMenu(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody SysPdaMenuModel model) {
    	String resultMsg = "操作失败";
		String menuIdStr = model.getMenuId();
		String userId = model.getUserId();
		try {
			if (StringUtil.isEmpty(menuIdStr)) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			List<SysPdaMenuModel> pdaMenuList = new ArrayList<SysPdaMenuModel>();
			String[] pdaMenuIdArr = menuIdStr.split(",");
			for(String menuId : pdaMenuIdArr){
				SysPdaMenuModel t = new SysPdaMenuModel();
				t.setUserId(userId);
				t.setMenuId(menuId);
				pdaMenuList.add(t);
			}
			sysPdaMenuManager.addPdaMenu(pdaMenuList);
			resultMsg = "添加成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    /**
     * 删除用户的菜单权限信息
     * @param request
     * @param response
     * @param modelList
     * @author linzhuo	
     * @DATE	2018年12月26日 下午10:42:14
     */
    @RequestMapping("deletePdaMenu")
    public void deletePdaMenu(HttpServletRequest request, HttpServletResponse response,
            @RequestBody List<SysPdaMenuModel> modelList) {
    	String resultMsg = "操作失败";
		try {
			if (null == modelList || 0 >= modelList.size()) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			sysPdaMenuManager.deletePdaMenu(modelList);
			resultMsg = "删除成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
	
}

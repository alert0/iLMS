package com.hanthink.pub.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.pub.manager.PubFactoryManager;
import com.hanthink.pub.model.PubFactoryModel;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: PubFactoryController
 * @Description: 工厂controller
 * @author dtp
 * @date 2019年5月29日
 */
@Controller
@RequestMapping("/pub/factory")
public class PubFactoryController extends GenericController{

	@Resource
	private PubFactoryManager pubFactoryManager;
	
	/**
	 * @Description: 查询工厂   
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@RequestMapping("queryFactoryPage")
	public @ResponseBody PageJson queryFactoryPage(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("model") PubFactoryModel model) {
		DefaultPage page=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
				getQueryFilter(request).getPage().getPageSize()));
		PageList<PubFactoryModel> pageList = pubFactoryManager.queryFactoryPage(model, page);
		return new PageJson(pageList);
	}
	
	/**
	 * @Description: 新增/修改工厂
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@RequestMapping("saveFactory")
    public void saveFactory(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody PubFactoryModel model) {
    	String resultMsg = "操作失败";
		String id = model.getId();
		String curUserId = ContextUtil.getCurrentUserId();
		try {
			if (StringUtil.isEmpty(id)) {
				//新增前判断工厂代码是否唯一
				List<PubFactoryModel> list = pubFactoryManager.queryIsExistsFactoryCode(model);
				if(null == list || list.size() > 0) {
					resultMsg = "工厂代码已存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}
				model.setCreationUser(curUserId);
				model.setId(UniqueIdUtil.getSuid());
				pubFactoryManager.insertFactory(model, RequestUtil.getIpAddr(request));
				resultMsg = "添加成功";
			} else {
				model.setLastModifiedUser(curUserId);
				pubFactoryManager.updateFactory(model, RequestUtil.getIpAddr(request));
				resultMsg = "更新成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), "系统错误,请联系管理员", e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
	
	/**
	 * @Description: 删除工厂
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@RequestMapping("deleteFactory")
    public void deleteFactory(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody PubFactoryModel model) {
    	String resultMsg = "操作失败";
		String id = model.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				resultMsg = "系统错误";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			pubFactoryManager.deleteFactory(model, RequestUtil.getIpAddr(request));
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
	
	/**
	 * @Description: 分页查询工厂对应账号
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@RequestMapping("queryFactoryUserList")
    public @ResponseBody PageJson queryFactoryUserList(HttpServletRequest request, HttpServletResponse response,
    	@ModelAttribute("model") PubFactoryModel model) {
		try {
			Page p = getQueryFilter(request).getPage();
			List<PubFactoryModel> pageList = pubFactoryManager.queryFactoryUserList(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
	
	/**
	 * @Description: 查询非当前工厂的系统用户账号 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@RequestMapping("queryNotThisFactorySystemUserPage")
    public @ResponseBody PageJson queryNotThisFactorySystemUserPage(HttpServletRequest request, HttpServletResponse response,
    		@ModelAttribute("model") PubFactoryModel model) {
		try {
			Page p = getQueryFilter(request).getPage();
			List<PubFactoryModel> pageList = pubFactoryManager.queryNotThisFactorySystemUserPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
	
	
	
	/**
	 * @Description: 工厂新增账号 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@RequestMapping("addFactoryUser")
    public void addFactoryUser(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody PubFactoryModel model) {
    	String resultMsg = "操作失败";
		//String userIdStr = RequestUtil.getString(request, "userId");
		//String factoryCode = RequestUtil.getString(request, "factoryCode");
    	String userIdStr = model.getUserId();
		String factoryCode = model.getFactoryCode();
		String curUserId = ContextUtil.getCurrentUserId();
		try {
			if (StringUtil.isEmpty(factoryCode) || StringUtil.isEmpty(factoryCode)) {
				resultMsg = "缺失参数工厂";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			List<PubFactoryModel> list = new ArrayList<PubFactoryModel>();
			String[] userIdArr = userIdStr.split(",");
			for(String userId : userIdArr){
				PubFactoryModel t = new PubFactoryModel();
				t.setFactoryCode(factoryCode);
				t.setUserId(userId);
				t.setCreationUser(curUserId);
				list.add(t);
			}
			pubFactoryManager.addFactoryUser(list);
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
	 * @Description: 删除工厂下账号 
	 * @param: @param request
	 * @param: @param response
	 * @param: @param modelList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@RequestMapping("deleteFactoryUser")
    public void deleteFactoryUser(HttpServletRequest request, HttpServletResponse response,
            @RequestBody List<PubFactoryModel> modelList) {
    	String resultMsg = "操作失败";
		try {
			if (null == modelList || 0 >= modelList.size()) {
				resultMsg = "缺失参数";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
				return;
			} 
			pubFactoryManager.deleteFactoryUser(modelList);
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

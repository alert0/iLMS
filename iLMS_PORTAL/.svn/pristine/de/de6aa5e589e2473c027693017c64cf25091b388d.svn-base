package com.hotent.portal.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.model.DefaultQueryFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
import com.hotent.portal.persistence.manager.SysIndexToolsManager;
import com.hotent.portal.persistence.model.SysIndexTools;

import java.util.List;


/**
 * 
 * <pre> 
 * 描述：首页工具 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 12:45:45
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/portal/sysIndexTools/sysIndexTools")
public class SysIndexToolsController extends GenericController{
	@Resource
	SysIndexToolsManager sysIndexToolsManager;
//	@Resource
//	BpmDefUserManager bpmDefUserManager;
	
	/**
	 * 首页工具列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<SysIndexTools> sysIndexToolsList=(PageList<SysIndexTools>)sysIndexToolsManager.query(queryFilter);
		return new PageJson(sysIndexToolsList);
	}
	
	
	
	/**
	 * 首页工具明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysIndexTools getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new SysIndexTools();
		}
		SysIndexTools sysIndexTools=sysIndexToolsManager.get(id);
		return sysIndexTools;
	}
	
	/**
	 * 保存首页工具信息
	 * @param request
	 * @param response
	 * @param sysIndexTools
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysIndexTools sysIndexTools) throws Exception{
		String resultMsg=null;
		String id=sysIndexTools.getId();
		try {
			if(StringUtil.isEmpty(id)){
				sysIndexTools.setId(UniqueIdUtil.getSuid());
				sysIndexToolsManager.create(sysIndexTools);
				resultMsg="添加首页工具成功";
			}else{
				sysIndexToolsManager.update(sysIndexTools);
				resultMsg="更新首页工具成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对首页工具操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}

	/**
	 * 判断首页工具名称是否已经存在
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("queryName")
	public void queryName(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=new ResultMessage(ResultMessage.SUCCESS, "");
		String name=RequestUtil.getString(request, "name");
		DefaultQueryFilter filter=new DefaultQueryFilter();
		filter.addFilter("NAME_",name,QueryOP.EQUAL);
		List list=sysIndexToolsManager.query(filter);
		if(list.size()>0){
			message=new ResultMessage(ResultMessage.ERROR, "该名称已存在！请换个名称在提交");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 批量删除首页工具记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			sysIndexToolsManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除首页工具成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除首页工具失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 获取授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
//	@RequestMapping("getRights")
//	public @ResponseBody JSONArray getColumnRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String id = RequestUtil.getString(request, "id");
//		if (StringUtil.isEmpty(id)) {
//			return null;
//		}
//		return bpmDefUserManager.getRights(id, SysIndexTools.INDEX_TOOLS);
//	}
	
	
	/**
	 * 保存授权信息
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @exception
	 */
//	@RequestMapping("saveRights")
//	public void saveRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ResultMessage message = null;
//		try { 
//			String id = RequestUtil.getString(request, "id");
//			String objType = SysIndexTools.INDEX_TOOLS;
//			String ownerNameJson = RequestUtil.getString(request, "rightsData");
//			bpmDefUserManager.saveRights(id, objType, ownerNameJson);
//			message = new ResultMessage(ResultMessage.SUCCESS,"权限保存成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//			message = new ResultMessage(ResultMessage.FAIL, "权限保存失败");
//		}
//		writeResultMessage(response.getWriter(), message);
//	}
}

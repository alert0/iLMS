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

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.portal.persistence.manager.SysIndexLayoutManager;
import com.hotent.portal.persistence.model.SysIndexLayout;

import java.io.IOException;
import java.util.List;


/**
 * 
 * <pre> 
 * 描述：首页布局 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:15
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/portal/sysIndexLayout/sysIndexLayout")
public class SysIndexLayoutController extends GenericController{
	@Resource
	SysIndexLayoutManager sysIndexLayoutManager;
	
	
	/**
	 * sys_index_layout列表(分页条件查询)数据
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
		PageList<SysIndexLayout> sysIndexLayoutList=(PageList<SysIndexLayout>)sysIndexLayoutManager.query(queryFilter);
		return new PageJson(sysIndexLayoutList);
	}
	
	
	
	/**
	 * 布局明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysIndexLayout getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Long id=RequestUtil.getLong(request, "id");
		if(id==null||id==0){
			return null;
		}
		SysIndexLayout sysIndexLayout=sysIndexLayoutManager.get(id);
		return sysIndexLayout;
	}

	/**
	 * 判断首页布局名称是否已经存在
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("queryName")
	public void  queryName(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ResultMessage message=new ResultMessage(ResultMessage.SUCCESS, "");
		String name=RequestUtil.getString(request, "name");
		DefaultQueryFilter filter=new DefaultQueryFilter();
		filter.addFilter("NAME",name,QueryOP.EQUAL);
		List list=sysIndexLayoutManager.query(filter);
		if(list.size()>0){
			message=new ResultMessage(ResultMessage.ERROR, "该名称已存在！请换个名称在提交");
		}
		writeResultMessage(response.getWriter(), message);

	}
	
	/**
	 * 保存布局信息
	 * @param request
	 * @param response
	 * @param sysIndexLayout
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysIndexLayout sysIndexLayout) throws Exception{
		String resultMsg=null;
		Long id=sysIndexLayout.getId();
		try {
			if(id==null||id==0){
				sysIndexLayout.setId(UniqueIdUtil.getUId());
				sysIndexLayoutManager.create(sysIndexLayout);
				resultMsg="添加布局成功";
			}else{
				sysIndexLayoutManager.update(sysIndexLayout);
				resultMsg="更新布局成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对布局操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除布局记录
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
			Long[] aryIds=RequestUtil.getLongAryByStr(request, "id");
			sysIndexLayoutManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除布局成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除布局失败");
		}
		writeResultMessage(response.getWriter(), message);
	} 	
	
}

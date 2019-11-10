package com.hotent.mini.controller.system;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.SysOrgParamsManager;
import com.hotent.org.persistence.model.SysOrgParams;


/**
 * 
 * <pre> 
 * 描述：组织参数 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-04 11:44:06
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/sysOrgParams/")
public class SysOrgParamsController extends GenericController{
	@Resource
	SysOrgParamsManager sysOrgParamsManager;
	
	/**
	 * 组织参数列表(分页条件查询)数据
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
		PageList<SysOrgParams> sysOrgParamsList=(PageList<SysOrgParams>)sysOrgParamsManager.query(queryFilter);
		return new PageJson(sysOrgParamsList);
	}
	
	
	
	/**
	 * 组织参数明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysOrgParams getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		SysOrgParams sysOrgParams=sysOrgParamsManager.get(id);
		return sysOrgParams;
	}
	
	/**
	 * 保存组织参数信息
	 * @param request
	 * @param response
	 * @param sysOrgParams
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysOrgParams sysOrgParams) throws Exception{
		String resultMsg=null;
		String id=sysOrgParams.getId();
		try {
			if(StringUtil.isEmpty(id)){
				sysOrgParams.setId(UniqueIdUtil.getSuid());
				sysOrgParamsManager.create(sysOrgParams);
				resultMsg="添加组织参数成功";
			}else{
				sysOrgParamsManager.update(sysOrgParams);
				resultMsg="更新组织参数成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对组织参数操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除组织参数记录
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
			sysOrgParamsManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除组织参数成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除组织参数失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 根据组织id获取组织属性数据
	 * @param id
	 * @return
	 */
	@RequestMapping("getByOrgId")
	public @ResponseBody List<SysOrgParams> getByOrgId(HttpServletRequest request,HttpServletResponse response){
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		return sysOrgParamsManager.getByOrgId(id);
	}
	
	/**
	 * 保存用户参数信息
	 * @param request
	 * @param response
	 * @param sysUserParams
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("saveParams")
	public void saveParams(HttpServletRequest request,HttpServletResponse response, @RequestBody List<JSONObject> lists) throws Exception{
		String orgId = RequestUtil.getString(request, "id");
		try{
			sysOrgParamsManager.saveParams(orgId, lists);
			writeResultMessage(response.getWriter(),"保存组织属性成功",ResultMessage.SUCCESS);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),"保存组织属性失败",ResultMessage.FAIL);
		}
	}
}

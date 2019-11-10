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
import com.hotent.org.persistence.manager.SysUserParamsManager;
import com.hotent.org.persistence.model.SysUserParams;


/**
 * 
 * <pre> 
 * 描述：用户参数 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-01 17:19:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/sysUserParams/")
public class SysUserParamsController extends GenericController{
	@Resource
	SysUserParamsManager sysUserParamsManager;
	
	/**
	 * 用户参数列表(分页条件查询)数据
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
		PageList<SysUserParams> sysUserParamsList=(PageList<SysUserParams>)sysUserParamsManager.query(queryFilter);
		return new PageJson(sysUserParamsList);
	}
	
	
	
	/**
	 * 用户参数明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysUserParams getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		SysUserParams sysUserParams=sysUserParamsManager.get(id);
		return sysUserParams;
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
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysUserParams sysUserParams) throws Exception{
		String resultMsg=null;
		String id=sysUserParams.getId();
		try {
			if(StringUtil.isEmpty(id)){
				sysUserParams.setId(UniqueIdUtil.getSuid());
				sysUserParamsManager.create(sysUserParams);
				resultMsg="添加用户参数成功";
			}else{
				sysUserParamsManager.update(sysUserParams);
				resultMsg="更新用户参数成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对用户参数操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除用户参数记录
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
			sysUserParamsManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除用户参数成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除用户参数失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 根据用户id获取用户属性数据
	 * @param id
	 * @return
	 */
	@RequestMapping("getByUserId")
	public @ResponseBody List<SysUserParams> getByUserId(HttpServletRequest request,HttpServletResponse response){
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		return sysUserParamsManager.getByUserId(id);
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
		String userId = RequestUtil.getString(request, "id");
		try{
			sysUserParamsManager.saveParams(userId, lists);
			writeResultMessage(response.getWriter(),"保存用户属性成功",ResultMessage.SUCCESS);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),"保存用户属性失败",ResultMessage.FAIL);
		}
	}
}

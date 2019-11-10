package com.hotent.mini.controller.base;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.persistence.manager.SubsystemManager;
import com.hotent.base.persistence.model.Subsystem;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：子系统定义 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:30:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/base/base/subsystem")
public class SubsystemController extends GenericController{
	@Resource
	SubsystemManager subsystemManager;
	
	/**
	 * 子系统定义列表(分页条件查询)数据
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
		PageList<Subsystem> subsystemList=(PageList<Subsystem>)subsystemManager.query(queryFilter);
		return new PageJson(subsystemList);
	}
	
	
	
	/**
	 * 子系统定义明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody Subsystem getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		Subsystem subsystem=subsystemManager.get(id);
		return subsystem;
	}
	
	/**
	 * 保存子系统定义信息
	 * @param request
	 * @param response
	 * @param subsystem
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody Subsystem subsystem) throws Exception{
		String resultMsg=null;

		boolean isExist = subsystemManager.isExist(subsystem);
		if(isExist) {
			resultMsg="别名子系统中已存在!";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
			return;
		}
		
		String id=subsystem.getId();
		try {
			if(StringUtil.isEmpty(id)){
				subsystem.setId(UniqueIdUtil.getSuid());
				IUser user=ContextUtil.getCurrentUser();
				subsystem.setCreator(user.getFullname());
				subsystem.setCreatorId(user.getUserId());
				subsystemManager.create(subsystem);
				resultMsg="添加子系统定义成功";
			}else{
				subsystemManager.update(subsystem);
				resultMsg="更新子系统定义成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对子系统定义操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除子系统定义记录
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
			subsystemManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除子系统定义成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除子系统定义失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	@RequestMapping("setDefault")
	public void setDefault(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String systemId=RequestUtil.getString(request, "systemId", "0");
			subsystemManager.setDefaultSystem(systemId);
			message=new ResultMessage(ResultMessage.SUCCESS, "设置成功!");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}

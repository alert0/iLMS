package com.hotent.portal.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
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
import com.hotent.portal.persistence.manager.SysLayoutToolsManager;
import com.hotent.portal.persistence.model.SysIndexTools;
import com.hotent.portal.persistence.model.SysLayoutTools;


/**
 * 
 * <pre> 
 * 描述：布局工具设置 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 20:25:54
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/portal/sysLayoutTools/sysLayoutTools")
public class SysLayoutToolsController extends GenericController{
	@Resource
	SysLayoutToolsManager sysLayoutToolsManager;
	
	/**
	 * 布局工具设置列表(分页条件查询)数据
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
		PageList<SysLayoutTools> sysLayoutToolsList=(PageList<SysLayoutTools>)sysLayoutToolsManager.query(queryFilter);
		return new PageJson(sysLayoutToolsList);
	}
	
	
	
	/**
	 * 布局工具设置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysLayoutTools getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new SysLayoutTools();
		}
		SysLayoutTools sysLayoutTools=sysLayoutToolsManager.get(id);
		return sysLayoutTools;
	}
	
	/**
	 * 保存布局工具设置信息
	 * @param request
	 * @param response
	 * @param sysLayoutTools
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysLayoutTools sysLayoutTools) throws Exception{
		String resultMsg=null;
		try {
			SysLayoutTools layoutTools = sysLayoutToolsManager.getByLayoutID(sysLayoutTools.getLayoutId(), sysLayoutTools.getToolsType());
			if(layoutTools == null){
				sysLayoutTools.setId(UniqueIdUtil.getSuid());
				sysLayoutToolsManager.create(sysLayoutTools);
				resultMsg="添加布局工具设置成功";
			}else{
				layoutTools.setToolsIds(sysLayoutTools.getToolsIds());
				sysLayoutToolsManager.update(layoutTools);
				resultMsg="更新布局工具设置成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对布局工具设置操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除布局工具设置记录
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
			sysLayoutToolsManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除布局工具设置成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除布局工具设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("toolsJson")
	public @ResponseBody PageJson toolsJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String layoutId = RequestUtil.getString(request, "layoutId");
		String toolsType = RequestUtil.getString(request, "toolsType","");
		PageList<SysIndexTools> sysIndexToolsList=new PageList<SysIndexTools>(sysLayoutToolsManager.queryTools(layoutId,toolsType));
		return new PageJson(sysIndexToolsList);
	}
	@RequestMapping("moveTool")
	public void moveTool(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String layoutId = RequestUtil.getString(request, "layoutId");
		String toolsType = RequestUtil.getString(request, "toolsType","");
		String toolId = RequestUtil.getString(request, "toolId","");
		boolean isMove = RequestUtil.getBoolean(request, "isMove");
		try{
			SysLayoutTools sysLayoutTools = sysLayoutToolsManager.getByLayoutID(layoutId, toolsType);
			String[] toolsArray = sysLayoutTools.getToolsIds().split(",");
			for (int i=0; i < toolsArray.length; i++){
				if(toolsArray[i].equals(toolId)){
					if(isMove){
						// 向前移动
						String pre = toolsArray[i-1];
						toolsArray[i-1] = toolsArray[i];
						toolsArray[i] = pre;
					}else{
						// 向后移动
						String later = toolsArray[i+1];
						toolsArray[i+1] = toolsArray[i];
						toolsArray[i] = later;
					}
					sysLayoutTools.setToolsIds(StringUtils.join(toolsArray, ","));
					sysLayoutToolsManager.update(sysLayoutTools);
					break;
				}
			}
			writeResultMessage(response.getWriter(),"移动成功",ResultMessage.SUCCESS);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),"移动失败",e.getMessage(),ResultMessage.FAIL);
		}
	}
	@RequestMapping("deleteTool")
	public void deleteTool(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String layoutId = RequestUtil.getString(request, "layoutId");
		String toolsType = RequestUtil.getString(request, "toolsType","");
		String toolId = RequestUtil.getString(request, "toolId","");
		try{
			SysLayoutTools sysLayoutTools = sysLayoutToolsManager.getByLayoutID(layoutId, toolsType);
			String[] toolsArray = sysLayoutTools.getToolsIds().split(",");
			toolsArray = (String[]) ArrayUtils.removeElement(toolsArray, toolId);
			sysLayoutTools.setToolsIds(StringUtils.join(toolsArray, ","));
			sysLayoutToolsManager.update(sysLayoutTools);
			writeResultMessage(response.getWriter(),"删除成功",ResultMessage.SUCCESS);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),"删除失败",e.getMessage(),ResultMessage.FAIL);
		}
	}
}

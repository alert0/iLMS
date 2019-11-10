package com.hotent.mini.controller.base;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.graphics.predictor.Sub;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.persistence.manager.CacheUtil;
import com.hotent.base.persistence.manager.ResRoleManager;
import com.hotent.base.persistence.manager.SubsystemManager;
import com.hotent.base.persistence.manager.SysResourceManager;
import com.hotent.base.persistence.model.ResRole;
import com.hotent.base.persistence.model.Subsystem;
import com.hotent.base.persistence.model.SysResource;


/**
 * 
 * <pre> 
 * 描述：角色资源分配 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:30:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/base/base/resRole")
public class ResRoleController extends GenericController{
	@Resource
	ResRoleManager resRoleManager;
	
	@Resource
	SysResourceManager sysResourceManager;
	
	@Resource
	SubsystemManager subsystemManager;
	
	/**
	 * 角色资源分配列表(分页条件查询)数据
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
		PageList<ResRole> resRoleList=(PageList<ResRole>)resRoleManager.query(queryFilter);
		return new PageJson(resRoleList);
	}
	
	
	
	/**
	 * 角色资源分配明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody ResRole getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		ResRole resRole=resRoleManager.get(id);
		return resRole;
	}
	
	/**
	 * 保存角色资源分配信息
	 * @param request
	 * @param response
	 * @param resRole
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg=null;
		String roleId = RequestUtil.getString(request, "roleId");
		String systemId = RequestUtil.getString(request, "systemId");
		String resIds = RequestUtil.getString(request, "resIds");
		try {
			resRoleManager.assignResByRoleSys(resIds,systemId,roleId);
			//清除角色和系统资源的缓存。
			CacheUtil.cleanResCache(systemId);
			
			resultMsg="添加角色资源分配成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对角色资源分配操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除角色资源分配记录
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
			resRoleManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除角色资源分配成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除角色资源分配失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	
	/**
	 * 给角色分配资源。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sysResourceAssign")
	public ModelAndView sysResourceAssign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv= getAutoView() ;
		
		String roleId=RequestUtil.getString(request, "roleId");
		
		
		List<Subsystem> list= subsystemManager.getList();
		
		String systemId=list.get(0).getId();
		 
		mv.addObject("systemList",list);
		mv.addObject("roleId",roleId);
		mv.addObject("systemId",systemId);
		
		return mv;
	}
	
	@RequestMapping("getTreeData")
	@ResponseBody
	public List<SysResource> getTreeData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String roleId=RequestUtil.getString(request, "roleId");
		String systemId=RequestUtil.getString(request, "systemId");
		List<SysResource> roleResourceList = sysResourceManager.getBySystemAndRole(systemId,roleId);
		List<SysResource> resourceList = sysResourceManager.getBySystemId(systemId);
		for (SysResource sysResource : resourceList) {
			if (roleResourceList.contains(sysResource)) {
				sysResource.setChecked(true);
			}
		}
		if (BeanUtils.isEmpty(resourceList))
			resourceList = new ArrayList<SysResource>();
		
		SysResource rootRes = new SysResource();
		String rootName = subsystemManager.get(systemId).getName();
		rootRes.setName( rootName);
		rootRes.setId("0");
		rootRes.setSystemId(systemId); // 根节点
		resourceList.add(rootRes);
		return resourceList;
	}
	
	@RequestMapping("getChildrenTreeData")
	@ResponseBody
	public List<SysResource> getChildrenTreeData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return BeanUtils.listToTree(getTreeData(request, response));
	}
}

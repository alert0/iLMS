package com.hotent.mini.controller.base;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultFieldSort;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.persistence.manager.CacheUtil;
import com.hotent.base.persistence.manager.RelResourceManager;
import com.hotent.base.persistence.manager.SubsystemManager;
import com.hotent.base.persistence.manager.SysResourceManager;
import com.hotent.base.persistence.model.Subsystem;
import com.hotent.base.persistence.model.SysResource;
import com.hotent.mini.web.context.SubSystemUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：子系统资源 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:30:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/base/base/sysResource")
public class SysResourceController extends GenericController{
	@Resource
	SysResourceManager sysResourceManager;
	
	@Resource
	RelResourceManager relResourceManager;
	
	@Resource
	SubsystemManager subsystemManager;
	
	/**
	 * 子系统资源列表(分页条件查询)数据
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
		PageList<SysResource> sysResourceList=(PageList<SysResource>)sysResourceManager.query(queryFilter);
		return new PageJson(sysResourceList);
	}
	
	
	
	/**
	 * 子系统资源明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysResource getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			String parentId = RequestUtil.getString(request, "parentId");
			String sysytemId = RequestUtil.getString(request, "systemId");
			SysResource sysResource = new SysResource();
			sysResource.setSystemId(sysytemId);
			sysResource.setParentId(parentId);
			sysResource.setNewWindow(0);
			sysResource.setHasChildren(1);
			sysResource.setOpened(0);
			sysResource.setEnableMenu(0);
			return sysResource;
		}
		
		SysResource sysResource = sysResourceManager.getByResId(id);
		return sysResource;
	}
	
	/**
	 * 保存子系统资源信息
	 * @param request
	 * @param response
	 * @param sysResource
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysResource sysResource) throws Exception{
		String resultMsg=null;
		String id=sysResource.getId();
		boolean isExist= sysResourceManager.isExist(sysResource);
		if(isExist){
			writeResultMessage(response.getWriter(),"资源已经存在,请修改重新添加!",ResultMessage.FAIL);
			return;
		}
		try {
			String cause="";
			if(StringUtil.isEmpty(id)){
				if(null == sysResource.getSn()){
					sysResource.setSn(System.currentTimeMillis());
				}
				sysResourceManager.create(sysResource);
				resultMsg="添加子系统资源成功";
				cause=buildResult(sysResource.getId(), true);
			}else{
				sysResourceManager.update(sysResource);
				cause=buildResult(sysResource.getId(), false);
				resultMsg="更新子系统资源成功";
			}
			//清除缓存。
			CacheUtil.cleanResCache(sysResource.getSystemId());
			writeResultMessage(response.getWriter(),resultMsg,cause,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对子系统资源操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除子系统资源记录
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
			String id=RequestUtil.getString(request, "id");
			sysResourceManager.removeByResId(id);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除子系统资源成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除子系统资源失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("sysResourceEdit")
	public ModelAndView sysResourceEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		String id = request.getParameter("id");
		String parentsysResourceName = "子系统资源管理";
		if (id == null && parentId != null && !parentId.equals("0")) {
			// 新增时
			parentsysResourceName = sysResourceManager.get(parentId).getName();
		}
		return getAutoView().addObject("id", id)
				.addObject("parentId", parentId).addObject("parentsysResourceName", parentsysResourceName);
	}
	
	
	
	@RequestMapping("sysResourceGet")
	public ModelAndView sysResourceGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		SysResource sysResource = sysResourceManager.get(id);

		return getAutoView().addObject("sysResource",sysResource);
	}
	
	@RequestMapping("getTreeData")
	@ResponseBody
	public List<SysResource> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemId=RequestUtil.getString(request, "systemId");
		Subsystem subsystem=subsystemManager.get(systemId);
		List<SysResource> groupList = getGroupTree(systemId);
		if (BeanUtils.isEmpty(groupList))
			groupList = new ArrayList<SysResource>();
		SysResource rootResource = new SysResource();
		rootResource.setName(subsystem.getName());
		rootResource.setId("0");
		rootResource.setSystemId(systemId); // 根节点
		rootResource.setHasChildren(1);
		groupList.add(rootResource);
		return groupList;
	}
	
	@RequestMapping("getAllTreeData")
	@ResponseBody
	public List<SysResource> getAllTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemId=RequestUtil.getString(request, "systemId");
		Subsystem subsystem=subsystemManager.get(systemId);
		List<SysResource> groupList = getGroupTree(systemId);
		if (BeanUtils.isEmpty(groupList))
			groupList = new ArrayList<SysResource>();
		SysResource rootResource = new SysResource();
		rootResource.setName(subsystem.getName());
		rootResource.setId("0");
		rootResource.setSystemId(systemId); // 根节点
		rootResource.setHasChildren(1);
		groupList.add(rootResource);
		return BeanUtils.listToTree(groupList);
	}
	
	private List<SysResource> getGroupTree(String systemId) {
		DefaultQueryFilter filter=new DefaultQueryFilter();
		filter.addFilter("SYSTEM_ID_", systemId, QueryOP.EQUAL);
		filter.addFieldSort("SN_", Direction.ASC.name());
		filter.addFieldSort("ID_", Direction.ASC.name());
		filter.setPage(null);
		List<SysResource> groupList = sysResourceManager.query(filter);
		return groupList;
	}
	
	@RequestMapping("moveResource")
	public void moveResource(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ResultMessage message=null;
		try {
			String id=RequestUtil.getString(request, "id");
			SysResource sysResource = sysResourceManager.get(id);
			String parentId = RequestUtil.getString(request, "parentId");
			
			SysResource parentResource = sysResourceManager.get(parentId);
			if(parentResource!=null){
				parentResource.setHasChildren(1);
				sysResourceManager.update(parentResource);
			}
			sysResource.setParentId(parentId);
			sysResourceManager.update(sysResource);
			message=new ResultMessage(ResultMessage.SUCCESS, "移动资源成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "移动资源失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	/**
	 * 加载菜单资源树
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author ZUOSL	
	 * @DATE	2018年10月31日 下午12:34:27
	 */
	@RequestMapping("getResTree")
	@ResponseBody
	public List<SysResource> getSysResource(HttpServletRequest request,HttpServletResponse response) throws IOException {
		IUser  user=ContextUtil.getCurrentUser();
		String systemId=SubSystemUtil.getSystemId(request);
		boolean isAdmin=user.isAdmin();
		List<SysResource> queryList = null;
		if(isAdmin){
			queryList = sysResourceManager.getBySystemId(systemId);
		}
		else{
			queryList = sysResourceManager.getBySystemAndUser(systemId, user.getUserId());
		}
		
		List<SysResource> list = new ArrayList<SysResource>();
		for(SysResource r : queryList){
			if(null != r.getEnableMenu() && r.getEnableMenu().intValue() == 1){
				list.add(r);
			}
		}
		
		return BeanUtils.listToTree(list);
	}


	/**
	 * 查询用户请求资源权限
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年10月31日 下午12:37:54
	 */
	@RequestMapping("queryUserReqResPermission")
	@ResponseBody
	public List<SysResource> queryUserReqResPermission(HttpServletRequest request,HttpServletResponse response){
		IUser  user=ContextUtil.getCurrentUser();
		String systemId=SubSystemUtil.getSystemId(request);
		boolean isAdmin=user.isAdmin();
		List<SysResource> queryList = null;
		if(isAdmin){
			queryList = sysResourceManager.getBySystemId(systemId);
		}
		else{
			queryList = sysResourceManager.getBySystemAndUser(systemId, user.getUserId());
		}
		
		List<SysResource> list = new ArrayList<SysResource>();
		for(SysResource r : queryList){
			if(SysResource.SYS_RES_TYPE_REQAJAX.equals(r.getSysResType()) 
					|| SysResource.SYS_RES_TYPE_REQFILE.equals(r.getSysResType())){
				list.add(r);
			}
		}
		return list;
	}
	
	
}

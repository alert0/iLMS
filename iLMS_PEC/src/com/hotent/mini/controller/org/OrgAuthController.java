package com.hotent.mini.controller.org;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.OrgAuthManager;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgAuth;
import com.hotent.org.persistence.model.OrgAuthTree;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：分级组织管理 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-20 14:30:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/orgAuth")
public class OrgAuthController extends GenericController{
	@Resource
	OrgAuthManager orgAuthManager;
	@Resource
	OrgManager orgManager;
	
	/**
	 * 分级组织管理列表(分页条件查询)数据
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
		String userId = ContextUtil.getCurrentUserId();
		String orgId = RequestUtil.getString(request, "orgId");
		queryFilter.addParamsFilter("orgId", orgId);
//		queryFilter.addParamsFilter("userId", userId);
		PageList<OrgAuth> orgAuthList=(PageList<OrgAuth>)orgAuthManager.query(queryFilter);
		return new PageJson(orgAuthList);
	}
	
	
	
	/**
	 * 分级组织管理明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody OrgAuth getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new OrgAuth();
		}
		OrgAuth orgAuth=orgAuthManager.get(id);
		return orgAuth;
	}
	
	/**
	 * 保存分级组织管理信息
	 * @param request
	 * @param response
	 * @param orgAuth
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody OrgAuth orgAuth) throws Exception{
		String resultMsg=null;
		String id=orgAuth.getId();
		//先判断orgId  与  userId 是否已经设置过了
		Map<String,String> params = new HashMap<String,String>();
		params.put("orgId", orgAuth.getOrgId());
		params.put("userId", orgAuth.getUserId());
		OrgAuth OrgAuth = orgAuthManager.getByOrgIdAndUserId(params);
		
		try {
			if(BeanUtils.isNotEmpty(OrgAuth) && StringUtil.isEmpty(id) ){
				resultMsg="请勿重复添加分级管理员";
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
			}else{
				if(StringUtil.isEmpty(id)){
					orgAuth.setId(UniqueIdUtil.getSuid());
					orgAuthManager.create(orgAuth);
					resultMsg="添加分级组织管理成功";
				}else{
					orgAuthManager.update(orgAuth);
					resultMsg="更新分级组织管理成功";
				}
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
			}
			
		} catch (Exception e) {
			resultMsg="对分级组织管理操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除分级组织管理记录
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
			Boolean flag = true;
			for(String id : aryIds){
				if(id.equals("1")){
					flag = false;
				}
			}
			if(flag){
				orgAuthManager.removeByIds(aryIds);
				message=new ResultMessage(ResultMessage.SUCCESS, "删除分级组织管理成功");
			}else{
				message=new ResultMessage(ResultMessage.FAIL, "【行政维度】为系统数据，不能删除！");
			}
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除分级组织管理失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 获取当前用户的分级管理组织
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCurrentUserAuthJson")
	public @ResponseBody PageJson getCurrentUserAuthJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter=getQueryFilter(request);
		queryFilter.addParamsFilter("userId", userId);
		PageList<OrgAuth> orgAuthList=(PageList<OrgAuth>)orgAuthManager.getAllOrgAuth(queryFilter);
		return new PageJson(orgAuthList);
	}
	
	/**
	 * 获取组织树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAuthOrgTree")
	@ResponseBody
	public List<OrgAuthTree> getAuthOrgTree(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String parentId = RequestUtil.getString(request, "orgId");//orgAuth对象对应的组织id
		String authId = RequestUtil.getString(request, "authId");
		String pId = RequestUtil.getString(request, "id",null);//延迟加载时传入的父组织id
		
		OrgAuth orgAuth = orgAuthManager.get(authId);
		
		List<OrgAuthTree> groupTreeList = new ArrayList<OrgAuthTree>();
//		List<Org> groupList = orgManager.getByParentId(parentId);
//		Org parentOrg = orgManager.get(parentId);
		
		List<Org> groupList = new ArrayList<Org>();
		if(pId != null){
			DefaultQueryFilter filter = new DefaultQueryFilter();
			DefaultPage page = new DefaultPage();
			page.setLimit(Integer.MAX_VALUE);
			filter.setPage(page);
			filter.addParamsFilter("demId", orgAuth.getDemId());
			filter.addParamsFilter("parentId", pId);
			groupList = orgManager.getByParentAndDem(filter);
		}else{
			Org parentOrg = orgManager.get(parentId);
			groupList.add(parentOrg);
		}
		
//		groupList.add(parentOrg);
		for (Org group : groupList) {
			OrgAuthTree groupTree = new OrgAuthTree(group);
			groupTree.setPosPerms(orgAuth.getPosPerms());
			groupTree.setUserPerms(orgAuth.getUserPerms());
			groupTree.setOrgPerms(orgAuth.getOrgPerms());
			groupTree.setOrgauthPerms(orgAuth.getOrgauthPerms());
			groupTree.setLayoutPerms(orgAuth.getLayoutPerms());
			groupTree.setUserId(orgAuth.getUserId());
			groupTreeList.add(groupTree);
		}
		return groupTreeList;
	}
	
	/**
	 * 获取当前用户的组织布局管理权限
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCurrentUserAuthOrgLayout")
	@ResponseBody
	public List<OrgAuth> getCurrentUserAuthOrgLayout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId = ContextUtil.getCurrentUserId();
		List<OrgAuth> orgAuthList= orgAuthManager.getLayoutOrgAuth(userId);
		return orgAuthList;
	}
}

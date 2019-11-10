package com.hotent.mini.controller.org;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgRelManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.SysDemensionManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgTree;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.SysDemension;
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONObject;

/**
 * <pre>
 * 描述：组织架构 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-28 15:13:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/org")
public class OrgController extends GenericController {
	@Resource
	OrgManager orgManager;
	@Resource
	UserManager userManager;
	@Resource
	OrgRelManager orgRelManager;
	@Resource
	OrgUserManager orgUserManager;
	@Resource
	SysDemensionManager demensionManager;

	/**
	 * 组织架构列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String parentId = request.getParameter("parentId");
		if (StringUtil.isNotEmpty(parentId)) {
			queryFilter.addFilter("parent_id_", parentId, QueryOP.EQUAL);
		}
		PageList<Org> orgList = (PageList<Org>) orgManager.query(queryFilter);
		return new PageJson(orgList);
	}
	
	@RequestMapping("orgEdit")
	public ModelAndView orgEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		String id = request.getParameter("id");
		
		String demId = request.getParameter("demId");
		
		String parentOrgName = demensionManager.get(demId).getDemName();
		
		String code = "";
		if (id == null && parentId != null && !parentId.equals("0")) {
			// 新增时
			parentOrgName = orgManager.get(parentId).getName();
		}
		if (id != null) {
			code = orgManager.get(id).getCode();
		}
		return getAutoView().addObject("code", code).addObject("id", id)
				.addObject("parentId", parentId).addObject("parentOrgName", parentOrgName).addObject("demId", demId);
	}
	
	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String oldCode = RequestUtil.getString(request, "oldCode");
		String code = RequestUtil.getString(request, "key");
		if (oldCode.equals(code) && StringUtil.isNotEmpty(code)) {
			return false;
		}
		if (StringUtil.isNotEmpty(code)) {
			Org temp = orgManager.getByCode(code);
			return temp != null;
		}
		return false;
	}
	
	@RequestMapping("orgGet")
	public ModelAndView orgGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String demId = request.getParameter("demId");
		Org org = orgManager.get(id);
		String parentOrgName = demensionManager.get(demId).getDemName();
		if (!org.getParentId().equals("0")) {
			parentOrgName = orgManager.get(org.getParentId()).getName();
		}
		org.setParentOrgName(parentOrgName);
		return getAutoView().addObject("org", org);
	}
	/**
	 * 组织架构明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody Org getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		Org org = orgManager.get(id);
		String demId = RequestUtil.getString(request, "demId");
		String parentOrgName = demensionManager.get(demId).getDemName();
		if (org != null && !org.getParentId().equals("0")) {
			parentOrgName = orgManager.get(org.getParentId()).getName();
		}
		if (org != null)
			org.setParentOrgName(parentOrgName);
		return org;
	}
	/**
	 * 保存组织架构信息
	 * @param request
	 * @param response
	 * @param org
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody Org org) throws Exception {
		String resultMsg = null;
		String id = org.getId();
		String parentId = org.getParentId();
		String demId = org.getDemId();
		try {
			if (StringUtil.isEmpty(id)) {
				org.setId(UniqueIdUtil.getSuid());
				if("0".equals(parentId)){
					org.setPath(demId+"."+org.getId()+".");
					org.setPathName("/"+org.getName());
				}else{
					Org parentOrg = orgManager.get(parentId);
					org.setPath(parentOrg.getPath()+org.getId()+".");
					org.setPathName(parentOrg.getPathName()+"/"+org.getName());
				}
				orgManager.create(org);
				resultMsg = "添加组织成功";
			} else {
				orgManager.update(org);
				resultMsg = "更新组织成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "对组织操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 获取当前用户当前组织
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getCurrentUserDeptName")
	public void getCurrentUserDeptName(HttpServletRequest request, HttpServletResponse response) throws IOException{
		IGroup group = ContextUtil.getCurrentGroup();
		writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, group==null?"":group.getName()));
	}
	
	/**
	 * 获取当前用户当前组织岗位名称
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getCurrentUserPostName")
	public void getCurrentUserPostName(HttpServletRequest request, HttpServletResponse response) throws IOException{
		OrgRel currentOrgRel = null;
		IGroup group = ContextUtil.getCurrentGroup();
		// 获取当前用户在当前组织下的拥有的岗位列表
		List<OrgUser> orgUsers = orgUserManager.getListByOrgIdUserId(group.getGroupId(), ContextUtil.getCurrentUserId());
		for (OrgUser orgUser : orgUsers) {
			// 主岗位优先返回
			if(1==orgUser.getIsMaster()){
				currentOrgRel = orgRelManager.get(orgUser.getRelId());
				break;
			}
		}
		// 没获取到主岗位，则返回第一个
		if( orgUsers.size()>0 && BeanUtils.isEmpty(currentOrgRel) ){
			currentOrgRel = orgRelManager.get(orgUsers.get(0).getRelId());
		}
		writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, currentOrgRel==null?"":currentOrgRel.getName()));
	}
	
	/**
	 * 获取当前用户当前组织
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getCurrentUserDept")
	public void getCurrentUserDept(HttpServletRequest request, HttpServletResponse response) throws IOException{
		IGroup group = ContextUtil.getCurrentGroup();
		writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, group==null?"":JSONObject.fromObject(group).toString()));
	}
	
	/**
	 * 获取当前用户主组织岗位
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getCurrentUserPost")
	public void getCurrentUserPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		OrgRel currentOrgRel = null;
		IGroup group = ContextUtil.getCurrentGroup();
		// 获取当前用户在当前组织下的拥有的岗位列表
		List<OrgUser> orgUsers = orgUserManager.getListByOrgIdUserId(group.getGroupId(), ContextUtil.getCurrentUserId());
		for (OrgUser orgUser : orgUsers) {
			// 主岗位优先返回
			if(1==orgUser.getIsMaster()){
				currentOrgRel = orgRelManager.get(orgUser.getRelId());
				break;
			}
		}
		// 没获取到主岗位，则返回第一个
		if( orgUsers.size()>0 && BeanUtils.isEmpty(currentOrgRel) ){
			currentOrgRel = orgRelManager.get(orgUsers.get(0).getRelId());
		}
		writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, currentOrgRel==null?"":JSONObject.fromObject(currentOrgRel).toString()));
	}
	
	/**
	 * 批量删除组织架构记录
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			orgManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除组织架构成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除组织架构失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("getTreeData")
	@ResponseBody
	public List<OrgTree> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = RequestUtil.getString(request, "id", "0");
		List<OrgTree> groupTreeList = getGroupTree(pid);
		if (BeanUtils.isEmpty(groupTreeList))
			groupTreeList = new ArrayList<OrgTree>();
		if(!StringUtil.isNotZeroEmpty(pid)){
			OrgTree rootGroup = new OrgTree();
			rootGroup.setName("行政组织");
			rootGroup.setId("0"); //根节点
			rootGroup.setIsParent(1);
			groupTreeList.add(rootGroup);
		}
		return groupTreeList;
	}
	
	@RequestMapping("getTreeDataByDemid")
	@ResponseBody
	public List<OrgTree> getTreeDataByDemid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String demId = request.getParameter("demId");
		String pId = request.getParameter("id");//父组织id
		if(pId == null){
			pId = "0";
		}
		String scope = request.getParameter("scope");
		List<OrgTree> groupTreeList = new ArrayList<OrgTree>();
		if(demId != null && !demId.equals("0") ){
			groupTreeList = getByDemid(demId,request,pId);
			if (BeanUtils.isEmpty(groupTreeList))
				groupTreeList = new ArrayList<OrgTree>();
			if(BeanUtils.isEmpty(scope) && "0".equals(pId)){
				OrgTree rootGroup = new OrgTree();
				SysDemension demension = demensionManager.get(demId);
				if(BeanUtils.isNotEmpty(demension)){
					rootGroup.setName(demension.getDemName());
					rootGroup.setId("0"); // 根节点
					if(BeanUtils.isEmpty(groupTreeList)){
						rootGroup.setIsParent(0);
					}else{
						rootGroup.setIsParent(1);
					}
					rootGroup.setDemId(demension.getId());
					groupTreeList.add(rootGroup);
				}
			}
		}else{
			List<SysDemension> demensionList = demensionManager.getAll();
			for(int i=0;i<demensionList.size();i++){
				List<OrgTree> sub = getByDemid(demensionList.get(i).getId(),request,pId);
				if (BeanUtils.isEmpty(sub))
					sub = new ArrayList<OrgTree>();
				if(BeanUtils.isEmpty(scope)){
				OrgTree rootGroup = new OrgTree();
				rootGroup.setName(demensionList.get(i).getDemName());
				rootGroup.setId("0"); // 根节点
				if(BeanUtils.isEmpty(groupTreeList)){
					rootGroup.setIsParent(0);
				}else{
					rootGroup.setIsParent(1);
				}
				rootGroup.setDemId(demensionList.get(i).getId());
				sub.add(rootGroup);
				}
				groupTreeList.addAll(sub);
			}
		}
		return groupTreeList;
	}
	
	/**
	 * 根据维度id   获取对应维度下的组织
	 * @param demId 维度id
	 * @param pId 父组织id
	 * @return
	 */
	private List<OrgTree> getByDemid(String demId,HttpServletRequest request,String pId){
		List<OrgTree> groupTreeList = new ArrayList<OrgTree>();
		DefaultQueryFilter filter = new DefaultQueryFilter();
		DefaultPage page = new DefaultPage();
		page.setLimit(Integer.MAX_VALUE);
		filter.setPage(page);
		filter.addParamsFilter("demId", demId);
		filter.addParamsFilter("parentId", pId);
		List<Org> groupList = orgManager.getByParentAndDem(filter);
		for (Org group : groupList) {
			OrgTree groupTree = new OrgTree(group);
			groupTreeList.add(groupTree);
		}
		return groupTreeList;
	}
	@RequestMapping("getMobileTreeDataByDemid")
	@ResponseBody
	public List<OrgTree> getMobileTreeDataByDemid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String demId = request.getParameter("demId");
		String pId = request.getParameter("id");//父组织id
		if(pId == null){
			pId = "0";
		}
		String scope = request.getParameter("scope");
		List<OrgTree> groupTreeList = new ArrayList<OrgTree>();
		if(demId != null && !demId.equals("0") ){
			groupTreeList = getMobileByDemid(demId,request,pId);
			if (BeanUtils.isEmpty(groupTreeList))
				groupTreeList = new ArrayList<OrgTree>();
			if(BeanUtils.isEmpty(scope) && "0".equals(pId)){
				OrgTree rootGroup = new OrgTree();
				SysDemension demension = demensionManager.get(demId);
				if(BeanUtils.isNotEmpty(demension)){
					rootGroup.setName(demension.getDemName());
					rootGroup.setId("0"); // 根节点
					if(BeanUtils.isEmpty(groupTreeList)){
						rootGroup.setIsParent(0);
					}else{
						rootGroup.setIsParent(1);
					}
					rootGroup.setDemId(demension.getId());
					groupTreeList.add(rootGroup);
				}
			}
		}else{
			List<SysDemension> demensionList = demensionManager.getAll();
			for(int i=0;i<demensionList.size();i++){
				List<OrgTree> sub = getMobileByDemid(demensionList.get(i).getId(),request,pId);
				if (BeanUtils.isEmpty(sub))
					sub = new ArrayList<OrgTree>();
				if(BeanUtils.isEmpty(scope)){
				OrgTree rootGroup = new OrgTree();
				rootGroup.setName(demensionList.get(i).getDemName());
				rootGroup.setId("0"); // 根节点
				if(BeanUtils.isEmpty(groupTreeList)){
					rootGroup.setIsParent(0);
				}else{
					rootGroup.setIsParent(1);
				}
				rootGroup.setDemId(demensionList.get(i).getId());
				sub.add(rootGroup);
				}
				groupTreeList.addAll(sub);
			}
		}
		return groupTreeList;
	}
	/**
	 * 根据维度id   获取对应维度下的组织
	 * @param demId 维度id
	 * @param pId 父组织id
	 * @return
	 */
	private List<OrgTree> getMobileByDemid(String demId,HttpServletRequest request,String pId){
		List<OrgTree> groupTreeList = new ArrayList<OrgTree>();
		DefaultQueryFilter filter = new DefaultQueryFilter();
		DefaultPage page = new DefaultPage();
		page.setLimit(Integer.MAX_VALUE);
		filter.setPage(page);
		filter.addParamsFilter("demId", demId);
		filter.addParamsFilter("parentId", pId);
		List<Org> groupList = orgManager.getByParentAndDem(filter);
		for (Org group : groupList) {
			OrgTree groupTree = new OrgTree(group);
			Integer population = caclPopulation(group);
			groupTree.setPopulation(population);
			groupTreeList.add(groupTree);
			if("0".equals(pId)){
				break;
			}
		}
		return groupTreeList;
	}
	private Integer caclPopulation(Org group) {
		List<Org> populationList = new ArrayList<Org>();
		// 先查询该部门下的人员id
		Org orgParent = orgManager.getOrgIdUserId(group.getId());
		populationList.add(orgParent);
		// 查询出子组织所有用户
		queryOrgUser(orgParent,populationList);
		// 合并userList
		Set<String> totalUser = new HashSet<String>();  
		for (Org org : populationList) {
			totalUser.addAll(org.getUserList());
		}
		return totalUser.size();
	}

	private void queryOrgUser(Org orgParent, List<Org> populationList) {
		List<Org> byParentId = orgManager.getByParentId(orgParent.getId());
		for (Org org : byParentId) {
			Org orgIdUserId = orgManager.getOrgIdUserId(org.getId());
			populationList.add(orgIdUserId);
			queryOrgUser(org,populationList);
		}
	}

	private List<OrgTree> getGroupTree(String pid) {
		List<OrgTree> groupTreeList = new ArrayList<OrgTree>();
		List<Org> groupList = orgManager.getByParentId(pid);
		for (Org group : groupList) {
			OrgTree groupTree = new OrgTree(group);
			groupTreeList.add(groupTree);
		}
		return groupTreeList;
	}
	
	/**
	 * 根据父组织id获取子组织
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByParentId")
	@ResponseBody
	public List<Org> getByParentId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = RequestUtil.getString(request, "parentId");
		List<Org> orgList = orgManager.getByParentId(parentId);
		return orgList;
	}
	
	@RequestMapping("getParentOrgList")
	@ResponseBody
	public List<OrgTree> getParentOrgList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String demId = request.getParameter("demId");
		String sonId = request.getParameter("id");//子组织id
		List<OrgTree> parentgroupTreeList = new ArrayList<OrgTree>();
		Org parentOrg = getParentOrg(demId,sonId);
		if(parentOrg != null){
			OrgTree groupTree = new OrgTree(parentOrg);
			parentgroupTreeList.add(groupTree);
			while(!parentOrg.getParentId().equals("0")){
				parentOrg = getParentOrg(demId,parentOrg.getId());
				parentgroupTreeList.add(new OrgTree(parentOrg));
			}
		}
		Collections.reverse(parentgroupTreeList);
		return parentgroupTreeList;
	}
	private Org getParentOrg(String demId,String sonId) {
		Org org = orgManager.getByDemIdAndSonId(demId,sonId);
		return org;
	}
}

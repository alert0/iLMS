package com.hotent.mini.controller.org;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.SysUserRelManager;
import com.hotent.org.persistence.model.SysUserRel;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.persistence.manager.SysCategoryManager;
import com.hotent.sys.persistence.manager.SysTypeManager;


/**
 * 
 * <pre> 
 * 描述：用户关系 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-06-12 09:21:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/sysUserRel")
public class SysUserRelController extends GenericController{
	@Resource
	SysUserRelManager sysUserRelManager;
	@Resource
	SysTypeManager sysTypeManager;
	@Resource
	SysCategoryManager sysCategoryManager;
	/**
	 * 用户关系列表(分页条件查询)数据
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
		PageList<SysUserRel> sysUserRelList=(PageList<SysUserRel>)sysUserRelManager.query(queryFilter);
		return new PageJson(sysUserRelList);
	}
	
	
	
	/**
	 * 用户关系明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysUserRel getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		SysUserRel sysUserRel=sysUserRelManager.get(id);
		return sysUserRel;
	}
	
	/**
	 * 保存用户关系信息
	 * @param request
	 * @param response
	 * @param sysUserRel
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysUserRel sysUserRel) throws Exception{
		String resultMsg=null;
		String id=sysUserRel.getId();
		try {
			if(StringUtil.isEmpty(id)){
				sysUserRel.setId(UniqueIdUtil.getSuid());
				sysUserRelManager.create(sysUserRel);
				resultMsg="添加用户关系成功";
			}else{
				sysUserRelManager.update(sysUserRel);
				resultMsg="更新用户关系成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对用户关系操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除用户关系记录
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
			sysUserRelManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除用户关系成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除用户关系失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 获取typeId （汇报线） 人员关系
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public Object getTreeData(HttpServletRequest request, HttpServletResponse response){

		String typeId = RequestUtil.getString(request, "typeId");
		SysType sysType = sysTypeManager.get(typeId);
		// 根节点parentId = -1； 标记
		SysUserRel sysUserRel = new SysUserRel();
		sysUserRel.setId(sysType.getId());
		sysUserRel.setUserId(sysType.getId());
		sysUserRel.setParentId("-1");
		sysUserRel.setUserName(sysType.getName());
		sysUserRel.setTypeId(typeId);
		
		List<SysUserRel> list =  sysUserRelManager.getByTypeId(typeId);
		
		list.add(sysUserRel);
		
		List<SysUserRel> rtnList= BeanUtils.listToTree(list);

		return rtnList;

	}
	
	/**
	 * 获取当前汇报线下的用户的下属用户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("setLevel")
	public void setLevel(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ResultMessage message = null;
		String id = RequestUtil.getString(request, "id", "");
		String userId = RequestUtil.getString(request, "userId","");
		String level = RequestUtil.getString(request, "level", "");
		if(StringUtil.isEmpty(id) || StringUtil.isEmpty(level) ){
			message=new ResultMessage(ResultMessage.FAIL, "值为空,不保存");
		}
		SysUserRel userRel =  sysUserRelManager.get(id);

		if(containsUserId(userRel,userId)){
			message=new ResultMessage(ResultMessage.FAIL, ThreadMsgUtil.getMessage());
			writeResultMessage(response.getWriter(), message);
			return;
		}
		
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("type_id_", userRel.getTypeId(), QueryOP.EQUAL);
		queryFilter.addFilter("user_id_", userId, QueryOP.EQUAL);
		if(StringUtil.isNotEmpty(level)){
			queryFilter.addFilter("level_", level, QueryOP.EQUAL);
			PageList<SysUserRel> sysUserRelList=(PageList<SysUserRel>)sysUserRelManager.query(queryFilter);
			if(BeanUtils.isNotEmpty(sysUserRelList)){
				message=new ResultMessage(ResultMessage.FAIL, "在同一分类下，同一个人员的级别不能重复！");
				return;
			}
		}
		userRel.setLevel(level);
		userRel.setUserId(userId);
		sysUserRelManager.update(userRel);
		message=new ResultMessage(ResultMessage.SUCCESS, "保存成功");
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * userId 是否已在当前关系中的上级和下级   
	 * @param userRel
	 * @param userId
	 * @return ture 是  false 否
	 */
	private Boolean containsUserId(SysUserRel userRel, String userId) {
		SysUserRel rel =  sysUserRelManager.get(userRel.getParentId());
		if(BeanUtils.isNotEmpty(rel)  && userId.equals(sysUserRelManager.get(userRel.getParentId()).getUserId())  ){
			ThreadMsgUtil.addMsg("不能跟直接上级相同。");
			return true;
		}
		
		rel = sysUserRelManager.getByUserIdAndParentId(userRel.getTypeId(), userId, userRel.getParentId());
		if(BeanUtils.isNotEmpty(rel) && !userId.equals(userRel.getUserId()) ){
			ThreadMsgUtil.addMsg("用户已经存在该位置。");
			return true;
		}
		
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("type_id_", userRel.getTypeId(), QueryOP.EQUAL);
		queryFilter.addFilter("parent_id_", userRel.getId(), QueryOP.EQUAL);
		queryFilter.setPage(null);
		List<SysUserRel> list = sysUserRelManager.query(queryFilter);
		for (SysUserRel sysUserRel : list) {
			if(userId.equals(sysUserRel.getUserId())){
				ThreadMsgUtil.addMsg("不能跟直接下级相同");
				return true;
			}
		}
		return false;
		
	}



	/**
	 * 获取当前汇报线下的用户的下属用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getUnderUser")
	@ResponseBody
	public PageJson getUnderUser(HttpServletRequest request, HttpServletResponse response){
		String typeId = RequestUtil.getString(request, "typeId", "");
		String pid = RequestUtil.getString(request, "parentId", "");
		
		QueryFilter queryFilter = getQueryFilter(request);
		if(StringUtil.isNotEmpty(typeId)){
			queryFilter.addParamsFilter("typeId", typeId);
		}
		if(StringUtil.isNotEmpty(pid)){
			queryFilter.addParamsFilter("parentId", pid);
		}
		
		PageList<SysUserRel> list = (PageList<SysUserRel>) sysUserRelManager.query(queryFilter);
		
		
		return new PageJson(list);
	}
	
	
	
	/**
	 * 保存用户关系
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveUserRel")
	public void saveUserRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String parentId = RequestUtil.getString(request, "parentId");
		String[] aryIds = RequestUtil.getStringAryByStr(request, "userIds");
		String typeId = RequestUtil.getString(request, "typeId");
		try {
			if (StringUtil.isNotEmpty(parentId)&&StringUtil.isNotEmpty(typeId)) {
				SysUserRel userRel = sysUserRelManager.get(parentId);
				for (String userId : aryIds) {
					if(userId.equals(BeanUtils.isEmpty(userRel)?"-1":userRel.getUserId())) continue;
					addUserRel(typeId,userId,parentId);
				}
				resultMsg = "保存成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "保存失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 1. 已经存在的， 不允许在添加
	 * 2. 不允许添加自己为下级
	 * @param typeId
	 * @param userId
	 * @param parentId
	 */
	private void addUserRel(String typeId,String userId, String parentId) {
		SysUserRel userRel = sysUserRelManager.getByUserIdAndParentId(typeId,userId,parentId);
		if(BeanUtils.isNotEmpty(userRel)) return;
		SysUserRel entity = new SysUserRel();
		entity.setId(UniqueIdUtil.getSuid());
		entity.setUserId(userId);
		entity.setParentId(parentId);
		entity.setTypeId(typeId);
		sysUserRelManager.create(entity);
	}
}

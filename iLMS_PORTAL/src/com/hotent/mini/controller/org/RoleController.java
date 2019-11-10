package com.hotent.mini.controller.org;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.persistence.manager.CacheUtil;
import com.hotent.mini.web.context.SubSystemUtil;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.model.Role;

/**
 * <pre>
 * 描述：角色管理 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:04
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/role")
public class RoleController extends GenericController {
	@Resource
	RoleManager roleManager;
	
	@Resource
	ICache iCache;
	

	/**
	 * 角色管理列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<Role> roleList = (PageList<Role>) roleManager.query(queryFilter);
		return new PageJson(roleList);
	}
	/**
	 * 角色管理明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody Role getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		Role role = roleManager.get(id);
		return role;
	}
	/**
	 * 保存角色管理信息
	 * @param request
	 * @param response
	 * @param role
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody Role role) throws Exception {
		String resultMsg = null;
		String id = role.getId();
		boolean isExist=roleManager.isRoleExist(role);
		if(isExist && StringUtil.isEmpty(id)){
			writeResultMessage(response.getWriter(), "角色在系统中已存在!", ResultMessage.FAIL);
			return ;
		}
		try {
			if (StringUtil.isEmpty(id)) {
				role.setId(UniqueIdUtil.getSuid());
				roleManager.create(role);
				resultMsg = "添加角色成功";
			} else {
				//清除角色和系统资源的缓存。
				String systemId=SubSystemUtil.getSystemId(request);
				CacheUtil.cleanResCache(systemId);
				
				roleManager.update(role);
				resultMsg = "更新角色成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "对角色操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 * 批量删除角色管理记录
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
			
			// 清除角色和系统资源的缓存。
			String systemId=SubSystemUtil.getSystemId(request);
			CacheUtil.cleanResCache(systemId);
			
			roleManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除角色成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除角色失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
}

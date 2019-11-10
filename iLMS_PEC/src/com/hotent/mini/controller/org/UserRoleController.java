package com.hotent.mini.controller.org;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.manager.UserRoleManager;
import com.hotent.org.persistence.model.User;
import com.hotent.org.persistence.model.UserRole;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * 描述：UserRoleController.java
 * 构建组：x5
 * 作者:Administrator
 * 邮箱:luoxw@jee-soft.cn
 * 日期:Jul 14, 2016 10:43:18 AM
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/userRole")
public class UserRoleController extends GenericController {
	private static Logger log = LoggerFactory.getLogger(UserRoleController.class);
	
	@Resource
	UserRoleManager userRoleManager;
	@Resource
	UserManager userManager;

	/**
	 * 用户角色管理列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String roleId = RequestUtil.getString(request, "roleId");
		String userId = RequestUtil.getString(request, "userId");
		QueryFilter queryFilter = getQueryFilter(request);
		if (StringUtil.isNotEmpty(roleId)) {
			queryFilter.addParamsFilter("roleId", roleId);
		}
		if (StringUtil.isNotEmpty(userId)) {
			queryFilter.addParamsFilter("userId", userId);
		}
		PageList<UserRole> userRoleList = (PageList<UserRole>) userRoleManager.query(queryFilter);
		return new PageJson(userRoleList);
	}
	/**
	 * 用户角色管理明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody UserRole getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		UserRole userRole = userRoleManager.get(id);
		return userRole;
	}
	/**
	 * 保存角色用户
	 * @param request
	 * @param response
	 * @param userRole
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String roleId = RequestUtil.getString(request, "roleId");
		String[] aryIds = RequestUtil.getStringAryByStr(request, "userId");
		String type = RequestUtil.getString(request, "type");
		try {
			int addCounts=0;
			if (StringUtil.isNotEmpty(roleId)) {
				if("user".equals(type)){
					for (String userId : aryIds) {
						addCounts+= addUserRole(userId,roleId);
					}
				}else if("org".equals(type)){
					for (String orgId : aryIds) {
						List<User> users = userManager.getUserListByOrgId(orgId);
						for (User user : users) {
							addCounts+=	addUserRole(user.getId(),roleId);
						}
					}
				}
				resultMsg = "已成功分配："+addCounts+"个用户";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "对角色分配用户操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
			e.printStackTrace();
		}
	}
	
	private int addUserRole(String userId,String roleId){
		if (userRoleManager.getByRoleIdUserId(roleId, userId) != null) return 0;
		
		UserRole userRole = new UserRole();
		userRole.setId(UniqueIdUtil.getSuid());
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		userRoleManager.create(userRole);
		return 1;
	}
	
	/**
	 * 保存用户角色
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveUserRole")
	public void saveUserRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String userId = RequestUtil.getString(request, "userId");
		String[] aryIds = RequestUtil.getStringAryByStr(request, "roleId");
		try {
			if (StringUtil.isNotEmpty(userId)) {
				for (String roleId : aryIds) {
					addUserRole(userId,roleId);
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
	 * 批量删除用户角色管理记录
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
			userRoleManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 查询用户信息与用户角色信息
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月2日 下午2:58:34
	 */
	@RequestMapping("queryUserRoleInfo")
	@ResponseBody
	public Map<String, Object> queryUserRoleInfo(HttpServletRequest request,HttpServletResponse response){
		IUser user = ContextUtil.getCurrentUser();
		List<UserRole> userRoleList = userRoleManager.getListByUserId(user.getUserId());
		List<String> roles = new ArrayList<String>();
		for(UserRole r : userRoleList){
			roles.add(r.getAlias());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userInfo", user);
		result.put("roles", roles);
		return result;
	}
	
	@RequestMapping("queryAddUserMenuRoleByUserId")
    public @ResponseBody PageJson queryAddUserMenuRoleByUserId(HttpServletRequest request, HttpServletResponse response) {
		try {
			Page p = getQueryFilter(request).getPage();
			UserRole model = new UserRole();
			model.setUserId(RequestUtil.getDecodeString(request, "userId"));
			model.setRoleName(RequestUtil.getDecodeString(request, "roleName"));
			IUser curUser = ContextUtil.getCurrentUser();
			PageList<UserRole> pageList = null;
			if(curUser.isAdmin()){
				pageList = userRoleManager.queryAddUserMenuRoleByUserId(model, p);
			}else{
				model.setCurLoginUserId(curUser.getUserId());
				pageList = userRoleManager.queryCurUserAddUserMenuRoleByUserId(model, p);
			}
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
    }
}

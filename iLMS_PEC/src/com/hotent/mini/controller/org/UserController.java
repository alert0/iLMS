
package com.hotent.mini.controller.org;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
//import com.hotent.mobile.weixin.api.IWXUserService;
//import com.hotent.mobile.weixin.impl.WxUserService;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.SysUserParamsManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.SysUserParams;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
 * <pre>
 * 描述：用户表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/user")
public class UserController extends GenericController {
	@Resource
	UserManager userManager;
	@Resource
	OrgUserManager orgUserManager;
	@Resource
	OrgManager orgManager;
	@Resource 
	SysUserParamsManager sysUserParamsManager;

//	@Resource
//	IWXUserService iwxUserService;
	/**
	 * 用户表列表(分页条件查询)
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<User> userList = (PageList<User>) userManager.query(queryFilter);
		return new PageJson(userList);
	}
	
	/**
	 * 通讯录列表(分页条件查询)
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("addressList")
	public @ResponseBody PageJson addressList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<User> userList = (PageList<User>) userManager.query(queryFilter);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (User user : userList) {
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp =  com.hotent.base.core.util.BeanUtils.convertObjToMap(user);
			Org org = orgManager.getMainGroup(user.getUserId());
			if(org!=null){
				tmp.put("orgName", org.getName());
			}
			List<SysUserParams> userParams =  sysUserParamsManager.getByUserId(user.getUserId());
			for (SysUserParams sysUserParams : userParams) {
				tmp.put(sysUserParams.getAlias(), sysUserParams.getValue());
			}
			list.add(tmp);
		}
		
		return new PageJson(list);
	}



//	@RequestMapping("syncUserToWx")
//	public void syncUserToWx(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ResultMessage message = null;
//		try {
//			String[] lAryId = RequestUtil.getStringAryByStr(request, "userId");
//			iwxUserService.syncUserToWx(lAryId);
//			message = new ResultMessage(ResultMessage.SUCCESS, "同步用户成功");
//		} catch (Exception e) {
//			message = new ResultMessage(ResultMessage.ERROR, "同步用户失败",e.getMessage());
//			e.printStackTrace();
//		}
//		writeResultMessage(response.getWriter(), message);
//	}

	/**
	 * 获取用户下的组织列表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listUserOrgJson")
	public @ResponseBody PageJson listUserOrgJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = RequestUtil.getString(request, "userId");
		queryFilter.addFilter("u.id_", userId, QueryOP.EQUAL);
		PageList<User> userList = (PageList<User>) userManager.queryOrgUser(queryFilter);
		return new PageJson(userList);
	}
	/**
	 * 获取用户下的岗位列表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listUserPostJson")
	public @ResponseBody PageJson listUserPostJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = RequestUtil.getString(request, "userId");
		queryFilter.addFilter("orguser.user_id_", userId, QueryOP.EQUAL);
		PageList orgUserList = (PageList) userManager.queryOrgUserRel(queryFilter);
		return new PageJson(orgUserList);
	}
	/**
	 * 用户表明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody User getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		/*String id = "10000017060007";
		User userA = userManager.get(id);
		WxUserService bean = AppUtil.getBean(WxUserService.class);
		bean.create(userA);*/
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		User user = userManager.get(id);
		return user;
	}
	/**
	 * 保存用户表信息
	 * @param request
	 * @param response
	 * @param user
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) throws Exception {
		String resultMsg = null;
		boolean isExist=userManager.isUserExist(user);
		if(isExist){
			writeResultMessage(response.getWriter(), "用户在系统中已存在!", ResultMessage.FAIL);
			return ;
		}
		try {
			String id = user.getId();
			if (StringUtil.isEmpty(id)) {
				user.setId(UniqueIdUtil.getSuid());
				String password = EncryptUtil.encryptSha256(user.getPassword());
				user.setPassword(password);
				//添加用户和组织的关系，默认为主关系。
				if(StringUtil.isNotEmpty( user.getGroupId())){
					OrgUser orgUser=new OrgUser();
					orgUser.setId(UniqueIdUtil.getSuid());
					orgUser.setIsMaster(OrgUser.MASTER_YES);
					orgUser.setOrgId(user.getGroupId());
					orgUser.setUserId(user.getUserId());
					
					/*TableOpeLogVO logVO = new TableOpeLogVO();
					logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
					logVO.setOpeIp(RequestUtil.getIpAddr(request));
					logVO.setFromName("新增用户");
					logVO.setOpeType(TableOpeLogVO.OPE_TYPE_INSERT);
					logVO.setTableName("SYS_USER");
					TableColumnVO pkColumnVO = new TableColumnVO();
					pkColumnVO.setColumnName("ID_");
					pkColumnVO.setColumnVal(orgUser.getId());
					userManager.logOpeTableData(logVO, pkColumnVO);*/
					orgUserManager.create(orgUser);
				}
				userManager.create(user);
				resultMsg = "添加用户成功!";
			} else {
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(RequestUtil.getIpAddr(request));
				logVO.setFromName("修改用户");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
				logVO.setTableName("SYS_USER");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("ID_");
				pkColumnVO.setColumnVal(user.getId());
				userManager.logOpeTableData(logVO, pkColumnVO);
				
				userManager.update(user);
				resultMsg = "更新用户成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, user.getId(), ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "对用户操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	
	@RequestMapping("saveUserInfo")
	public void saveUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) throws Exception {
		try {
			//TODO 可以不更新敏感信息
			userManager.update(user);
			writeResultMessage(response.getWriter(), "更新用户成功", user.getId(), ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "对用户操作失败", e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	@RequestMapping("updateUserPsw")
	public void updateUserPsw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oldPassWorld = RequestUtil.getString(request, "oldPassWorld");
		String newPassword = RequestUtil.getString(request, "newPassword");
		
		try {
			User user = userManager.get(ContextUtil.getCurrentUserId());
			if(user.getPassword().equals(EncryptUtil.encryptSha256(oldPassWorld))){
				user.setPassword(EncryptUtil.encryptSha256(newPassword));
				userManager.update(user);
				writeResultMessage(response.getWriter(), "更新密码成功", user.getId(), ResultMessage.SUCCESS);
				return;
			}
			else{
				writeResultMessage(response.getWriter(), "旧密码输入错误，更新密码失败！", ResultMessage.FAIL);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "更新密码失败", e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("changUserPsd")
	public void changUserPsd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = RequestUtil.getString(request, "userId");
		String newPassword = RequestUtil.getString(request, "newPassword");
		
		try {
			User user = userManager.get(userId);
			if(BeanUtils.isNotEmpty(user)){
				user.setPassword(EncryptUtil.encryptSha256(newPassword));
				userManager.update(user);
				writeResultMessage(response.getWriter(), "更新密码成功", user.getId(), ResultMessage.SUCCESS);
				return;
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "更新密码失败", e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 重置用户密码
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月18日 下午2:42:44
	 */
	@RequestMapping("resetUserPwd")
	public void resetUserPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = RequestUtil.getString(request, "userId");
		String newPassword = SysPropertyUtil.getByAlias("system.resetPwd", "123456");
		
		try {
			User user = userManager.get(userId);
			if(BeanUtils.isNotEmpty(user)){
				user.setPassword(EncryptUtil.encryptSha256(newPassword));
				userManager.update(user);
				writeResultMessage(response.getWriter(), "更新密码成功", user.getId(), ResultMessage.SUCCESS);
				return;
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "更新密码失败", e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除用户表记录
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
			userManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS,ThreadMsgUtil.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "删除用户失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	
	@RequestMapping("getUserByGroupJson")
	public @ResponseBody PageJson getUserByGroupJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String orgId = RequestUtil.getString(request, "orgId");
		String relId = RequestUtil.getString(request, "relId");
		queryFilter.addFilter("orguser.org_id_", orgId, QueryOP.EQUAL);
		if(StringUtil.isNotEmpty(relId)){
			queryFilter.addFilter("orguser.rel_id_", relId, QueryOP.EQUAL);
		}
		PageList orgUserList = (PageList) orgUserManager.getUserByGroup(queryFilter);
		return new PageJson(orgUserList);
	}
	
	/**
	 * 获取当前用户
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getCurrentUserName")
	public void getCurrentUserName(HttpServletRequest request, HttpServletResponse response) throws IOException{
		IUser user = ContextUtil.getCurrentUser();
		writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, user==null?"":user.getFullname()));
	}
	
	/**
	 * 获取当前用户
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getCurrentUser")
	public void getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		IUser user = ContextUtil.getCurrentUser();
		writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, user==null?"":JSONObject.fromObject(user).toString()));
	}
	
	/**
	 * 从excel导入用户
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importUser")
	public void importUser(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String demId = RequestUtil.getString(request, "demId");
			Map<String,Object> rtn = userManager.importUser(file,demId);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 查找该维度对应编码是否已存在
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("isCodeExist")
	public @ResponseBody boolean isCodeExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String demId = RequestUtil.getString(request, "demId");
		String code = RequestUtil.getString(request, "code");
		if (StringUtil.isNotEmpty(demId)&&StringUtil.isNotEmpty(code)) {
			QueryFilter queryFilter = new DefaultQueryFilter();
			queryFilter.addFilter("DEM_ID_", demId, QueryOP.NOT_EQUAL);
			queryFilter.addFilter("CODE_", code+"\\_", QueryOP.RIGHT_LIKE);
			List<Org> list = orgManager.query(queryFilter);
			if (BeanUtils.isNotEmpty(list)) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("getUserAndGroup")
	public @ResponseBody PageJson getUserAndGroup(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		DefaultQueryFilter queryFilter = (DefaultQueryFilter) getQueryFilter(request);
		String userName = RequestUtil.getString(request, "userName");
		String orgId = RequestUtil.getString(request, "orgId");
		String userId = RequestUtil.getString(request, "userId");
		if(StringUtil.isNotEmpty(userName)){
			queryFilter.addFilter("u.fullname_", userName, QueryOP.LIKE);
		}
		if(StringUtil.isNotEmpty(orgId)&& !orgId.equals("undefined")){
			queryFilter.addFilter("orguser.org_id_", orgId, QueryOP.EQUAL);
		}
		if(StringUtil.isNotEmpty(userId)){
			queryFilter.addFilter("u.id_", userId, QueryOP.EQUAL);
		}
		PageList orgUserList = (PageList) orgUserManager.getUserAndGroup(queryFilter);
		return new PageJson(orgUserList);
	}
	
	@RequestMapping("getSerachUser")
	public @ResponseBody PageJson getSerachUser(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		DefaultQueryFilter queryFilter = (DefaultQueryFilter) getQueryFilter(request);
		String userName = RequestUtil.getString(request, "userName");
		if(StringUtil.isNotEmpty(userName)){
			queryFilter.addFilter("u.fullname_", userName, QueryOP.LIKE);
		}
		PageList orgUserList = (PageList) orgUserManager.getSerachUser(queryFilter);
		return new PageJson(orgUserList);
	}
}

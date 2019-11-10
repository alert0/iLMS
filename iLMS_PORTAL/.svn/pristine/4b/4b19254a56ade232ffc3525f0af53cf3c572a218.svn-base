package com.hotent.mini.controller.org;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;
import com.hotent.org.api.context.ICurrentContext;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.User;

/**
 * <pre>
 * 描述：用户组织关系 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:27:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/orgUser")
public class OrgUserController extends GenericController {
	@Resource
	OrgUserManager orgUserManager;
	@Resource
	UserManager userManager;

	/**
	 * 用户组织关系列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listOrgUserJson")
	public @ResponseBody PageJson listOrgUserJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String orgId = RequestUtil.getString(request, "orgId");
		if (StringUtil.isNotEmpty(orgId)) {
			queryFilter.addFilter("org.id_", orgId, QueryOP.EQUAL);
		}
		PageList<User> userList = (PageList<User>) userManager.queryOrgUserRel(queryFilter);
		return new PageJson(userList);
	}
	
	/**
	 * 获取岗位的人员
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listPostUserJson")
	public @ResponseBody PageJson listPostUserJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String relId = RequestUtil.getString(request, "relId");
		String orgId = RequestUtil.getString(request, "orgId");
		queryFilter.addFilter("orguser.rel_id_", relId, QueryOP.EQUAL);
		queryFilter.addFilter("org.id_", orgId, QueryOP.EQUAL);
		PageList<User> orgUserList = (PageList<User>) userManager.queryOrgUserRel(queryFilter);
		return new PageJson(orgUserList);
	}
	
	@RequestMapping("getUserByOrg")
	public @ResponseBody PageJson getUserByOrg(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
//		QueryFilter queryFilter = getQueryFilter(request);
//		String orgId = RequestUtil.getString(request, "orgId");
//		queryFilter.addFilter("org.id_", orgId, QueryOP.EQUAL);
//		PageList<User> orgUserList = (PageList<User>) userManager.queryOrgUserRel(queryFilter);
//		return new PageJson(orgUserList);
		return null;
	}
	
	
	/**
	 * 用户组织关系明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody OrgUser getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		OrgUser orgUser = orgUserManager.get(id);
		return orgUser;
	}
	/**
	 * 保存用户组织关系信息
	 * @param request
	 * @param response
	 * @param orgUser
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("saveOrgUser")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String[] aryIds = RequestUtil.getStringAryByStr(request, "userId");
		String orgId = RequestUtil.getString(request, "orgId");
		try {
			for (String userId : aryIds) {
				List<OrgUser> orgUserList = orgUserManager.getListByOrgIdUserId(orgId, userId);
				if (BeanUtils.isEmpty(orgUserList)) {
					OrgUser orgUser = new OrgUser();
					orgUser.setId(UniqueIdUtil.getSuid());
					orgUser.setOrgId(orgId);
					orgUser.setUserId(userId);
					orgUser.setIsMaster(0);
					orgUserManager.create(orgUser);
				}
			}
			resultMsg = "加入组织用户成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "对用户组织关系操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 * 分配用户岗位
	 * @param request
	 * @param response
	 * @param orgUser
	 * @throws Exception
	 */
	@RequestMapping("saveUserOrg")
	public void saveUserPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String[] relIds = RequestUtil.getStringAryByStr(request, "relId");
		String[] orgIds = RequestUtil.getStringAryByStr(request, "orgId");
		String userId = RequestUtil.getString(request, "userId");
		try {
			String relId = null;
			for (int i = 0; i < orgIds.length; i++) {
				String orgId = orgIds[i];
				
				if(BeanUtils.isNotEmpty(relIds)){
					relId = relIds[i];
				}
				OrgUser orgUser = orgUserManager.getOrgUser(orgId, userId, relId);
				if(orgUser==null){
					List<OrgUser> orgUsers = orgUserManager.getListByOrgIdUserId(orgId, userId);
					if(!orgUsers.isEmpty()&&StringUtil.isEmpty(relId)){
						continue;
					}
					if( !orgUsers.isEmpty() && StringUtil.isEmpty(orgUsers.get(0).getRelId()) ){
						orgUser = orgUsers.get(0);
						orgUser.setRelId(relId);
						orgUserManager.update(orgUser);
					}else{
						orgUser = new OrgUser();
						orgUser.setId(UniqueIdUtil.getSuid());
						orgUser.setOrgId(orgId);
						orgUser.setUserId(userId);
						orgUser.setRelId(relId);
						orgUser.setIsMaster(0);
						//当在组织岗位中添加人员时，先判断该用户在该组织下是否已经设置过组织负责人或是组织主负责人，若有，则应该与之前的保持一致
						List<OrgUser> orgUserList = orgUserManager.getListByOrgIdUserId(orgId,userId);
						if(BeanUtils.isNotEmpty(orgUserList)){
							orgUser.setIsCharge(orgUserList.get(0).getIsCharge());
						}
						orgUserManager.create(orgUser);
					}
				}
			}
			resultMsg = "成功！";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "失败！";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 * 保存组织用户岗位关系
	 * @param request
	 * @param response
	 * @param orgUser
	 * @throws Exception
	 */
	@RequestMapping("saveOrgUserRel")
	public void saveOrgUserRel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String[] aryIds = RequestUtil.getStringAryByStr(request, "userId");
		String orgId = RequestUtil.getString(request, "orgId");
		String relId = RequestUtil.getString(request, "relId");
		try {
			for (String userId : aryIds) {
				OrgUser orgUser = orgUserManager.getOrgUser(orgId, userId, relId);
				if (orgUser == null) {
					orgUser = orgUserManager.getOrgUser(orgId, userId, "");
					if (orgUser == null) {
						orgUser = new OrgUser();
						orgUser.setId(UniqueIdUtil.getSuid());
						orgUser.setOrgId(orgId);
						orgUser.setUserId(userId);
						orgUser.setRelId(relId);
						orgUser.setIsMaster(0);
						//当在组织岗位中添加人员时，先判断该用户在该组织下是否已经设置过组织负责人或是组织主负责人，若有，则应该与之前的保持一致
						List<OrgUser> orgUserList = orgUserManager.getListByOrgIdUserId(orgId,userId);
						if(BeanUtils.isNotEmpty(orgUserList)){
							orgUser.setIsCharge(orgUserList.get(0).getIsCharge());
						}
						orgUserManager.create(orgUser);
					} else {
						orgUser.setRelId(relId);
						orgUserManager.update(orgUser);
					}
				}
			}
			resultMsg = "分配岗位用户成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg = "分配用户岗位操作失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
	/**
	 * 批量删除用户组织关系记录
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
			//删除缓存中的用户组织
			for (int i = 0; i <aryIds.length ; i++) {
				OrgUser u= orgUserManager.get(aryIds[i]);
				String userId=u.getUserId();
				ICache iCache=(ICache)AppUtil.getBean(ICache.class);
				String userKey=ICurrentContext.CURRENT_ORG + userId;
				System.out.println(userKey);
				iCache.delByKey(userKey);
			}
			orgUserManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "成功删除用户组织岗位关系");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除用户组织岗位失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("removeByOrgIdUserId")
	public void removeByOrgIdUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String userId = RequestUtil.getString(request, "userId");
			String orgId = RequestUtil.getString(request, "orgId");
			orgUserManager.removeByOrgIdUserId(orgId, userId);
			message = new ResultMessage(ResultMessage.SUCCESS, "成功删除用户组织关系");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除用户组织关系失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 删除指定岗位的分配用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("removePostUser")
	public void removePostUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			orgUserManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除岗位用户关系成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除岗位用户关系失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("setMaster")
	public void setMaster(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			String id = RequestUtil.getString(request, "id");
			orgUserManager.setMaster(id);
			writeResultMessage(response.getWriter(), "设置用户主组织成功!", ResultMessage.SUCCESS);
		}
		catch(Exception ex){
			writeResultMessage(response.getWriter(), "设置用户主组织失败!", ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * 设置部门负责人
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("setCharge")
	public void setCharge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			String userId = RequestUtil.getString(request, "userId");
			Boolean isCharge = RequestUtil.getBoolean(request, "isCharge");
			String orgId = RequestUtil.getString(request,"orgId","");
			orgUserManager.setCharge(userId,isCharge,orgId);
			writeResultMessage(response.getWriter(), "设置组织负责人成功!", ResultMessage.SUCCESS);
		}
		catch(Exception ex){
			writeResultMessage(response.getWriter(), "设置组织负责人失败!", ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * 获取用户是否有主组织
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getOrgUserIsMaster")
	public void getOrgUserIsMaster(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		int isMaster = ResultMessage.FAIL;
		if (StringUtil.isNotEmpty(id)) {
			OrgUser orgUser = orgUserManager.getOrgUserMaster(id);
			if(BeanUtils.isNotEmpty(orgUser)){
				isMaster = ResultMessage.SUCCESS;
			}
		}
		writeResultMessage(response.getWriter(),"", isMaster);
	}
	
	/**
	 * 获取该组织是否已有非己主负责人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getOrgIsCharge")
	public void getOrgIsCharge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgId = RequestUtil.getString(request, "orgId");
		String userId = RequestUtil.getString(request, "userId");
		int isCharge = ResultMessage.FAIL;
		if (StringUtil.isNotEmpty(orgId)) {
			QueryFilter queryFilter = new DefaultQueryFilter();
			queryFilter.addFilter("org.id_", orgId, QueryOP.EQUAL);
			queryFilter.addFilter("orguser.user_id_", userId, QueryOP.NOT_EQUAL);
			queryFilter.addFilter("orguser.is_charge_", 2, QueryOP.EQUAL);
			PageList<User> orgUserList = (PageList<User>) userManager.queryOrgUserRel(queryFilter);
			if(BeanUtils.isNotEmpty(orgUserList)){
				isCharge = ResultMessage.SUCCESS;
			}
		}
		writeResultMessage(response.getWriter(),"", isCharge);
	}
}

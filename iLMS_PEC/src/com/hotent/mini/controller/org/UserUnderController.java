package com.hotent.mini.controller.org;


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
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.manager.UserUnderManager;
import com.hotent.org.persistence.model.User;
import com.hotent.org.persistence.model.UserUnder;


/**
 * 
 * <pre> 
 * 描述：下属管理 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-25 09:24:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/org/userUnder")
public class UserUnderController extends GenericController{
	@Resource
	UserUnderManager userUnderManager;
	@Resource
	UserManager userManager;
	
	/**
	 * 下属管理列表(分页条件查询)数据
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
		String orgId = RequestUtil.getString(request, "orgId");
		String userId = RequestUtil.getString(request, "userId");
		queryFilter.addParamsFilter("userId", userId);
		queryFilter.addParamsFilter("orgId", orgId);
		PageList<UserUnder> userUnderList=(PageList<UserUnder>)userUnderManager.getUserUnder(queryFilter);
		return new PageJson(userUnderList);
	}
	
	
	
	/**
	 * 下属管理明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody UserUnder getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new UserUnder();
		}
		UserUnder userUnder=userUnderManager.get(id);
		return userUnder;
	}
	
	/**
	 * 保存下属管理信息
	 * @param request
	 * @param response
	 * @param userUnder
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody UserUnder userUnder) throws Exception{
		String resultMsg=null;
		String id=userUnder.getId();
		try {
			if(StringUtil.isEmpty(id)){
				userUnder.setId(UniqueIdUtil.getSuid());
				userUnderManager.create(userUnder);
				resultMsg="添加下属管理成功";
			}else{
				userUnderManager.update(userUnder);
				resultMsg="更新下属管理成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对下属管理操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除下属管理记录
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
			userUnderManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除下属成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除下属失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 保存下属
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveUserUnders")
	public void saveUserUnders(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String[] aryIds = RequestUtil.getStringAryByStr(request, "userUnderId");//下级用户id
		String[] aryNames = RequestUtil.getStringAryByStr(request, "userNames");
		String orgId = RequestUtil.getString(request, "orgId");
		String userId = RequestUtil.getString(request, "userId");//上级用户id
		Boolean flag = true;
		try {
			for(int i=0;i<aryIds.length;i++){
				QueryFilter filter1 = getQueryFilter(request);
				filter1.addParamsFilter("noUserId", userId);
				filter1.addParamsFilter("orgId", orgId);
				filter1.addParamsFilter("underUserId", aryIds[i]);
				List<UserUnder> list1 = userUnderManager.getUserUnder(filter1);//获取该组织下，不是当前上级用户的数据
				if(BeanUtils.isNotEmpty(list1)){//同一组织下，用户只能有一个上级
					User supperUser = userManager.get(list1.get(0).getUserId());
					resultMsg = "用户【"+aryNames[i]+"】在当前组织中已有上级"+"【"+supperUser.getFullname()+"】，在当前组织中不能再设置其他上级。";
					flag = false;
					break;
				}else{
					QueryFilter filter2 = getQueryFilter(request);
					filter2.addParamsFilter("userId", userId);
					filter2.addParamsFilter("orgId", orgId);
					filter2.addParamsFilter("underUserId", aryIds[i]);
					List<UserUnder> list2 = userUnderManager.getUserUnder(filter2);//获取该组织下，是当前上级用户的数据
					if(BeanUtils.isNotEmpty(list2)){//
						continue;
					}else{
						UserUnder userUnder = new UserUnder();
						userUnder.setId(UniqueIdUtil.getSuid());
						userUnder.setUserId(userId);
						userUnder.setUnderUserId(aryIds[i]);
						userUnder.setUnderUserName(aryNames[i]);
						userUnder.setOrgId(orgId);
						userUnderManager.create(userUnder);
					}
				}
			}
			if(!flag){
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
			}else{
				resultMsg = "下属分配成功";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "下属分配失败";
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}
}

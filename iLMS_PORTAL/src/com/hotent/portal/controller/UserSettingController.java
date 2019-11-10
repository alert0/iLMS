package com.hotent.portal.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.portal.persistence.manager.UserSettingManager;
import com.hotent.portal.persistence.model.UserSetting;


/**
 * 
 * <pre> 
 * 描述：用户配置 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-01 17:18:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/portal/userSetting/userSetting")
public class UserSettingController extends GenericController{
	@Resource
	UserSettingManager userSettingManager;
	
	/**
	 * 用户配置列表(分页条件查询)数据
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
		PageList<UserSetting> userSettingList=(PageList<UserSetting>)userSettingManager.query(queryFilter);
		return new PageJson(userSettingList);
	}
	
	
	
	/**
	 * 用户配置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody UserSetting getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Long id=RequestUtil.getLong(request, "id");
		if(StringUtil.isEmpty(id)){
			return new UserSetting();
		}
		UserSetting userSetting=userSettingManager.get(id);
		return userSetting;
	}
	
	/**
	 * 保存用户配置信息
	 * @param request
	 * @param response
	 * @param userSetting
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody UserSetting userSetting) throws Exception{
		String resultMsg=null;
		Long id=userSetting.getId();
		try {
			if(StringUtil.isEmpty(id)){
				userSetting.setId(UniqueIdUtil.getUId());
				userSettingManager.create(userSetting);
				resultMsg="添加用户配置成功";
			}else{
				userSettingManager.update(userSetting);
				resultMsg="更新用户配置成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对用户配置操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除用户配置记录
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
			Long[] aryIds=RequestUtil.getLongAryByStr(request, "id");
			userSettingManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除用户配置成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除用户配置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}

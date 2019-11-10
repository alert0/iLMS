package com.hotent.mobile.weixin.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hotent.mobile.weixin.api.IWXOrgService;
import com.hotent.org.persistence.manager.OrgManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.HttpUtil;
import com.hotent.mobile.weixin.api.IWXUserService;
import com.hotent.mobile.weixin.api.WeixinConsts;
import com.hotent.mobile.weixin.model.WxUser;
import com.hotent.mobile.weixin.util.OrgConvertUtil;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.SysUser;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.util.SysPropertyUtil;

@Component
public class WxUserService implements IWXUserService{
	private final Log logger = LogFactory.getLog(WxUserService.class);
	@Resource 
	IUserService sysUserService;
	@Resource
	UserManager userManager;
	@Resource
	OrgManager orgManager;
	@Resource
	IWXOrgService iwxOrgService;

	@Override
	public void create(User user){
		if(StringUtils.isEmpty(user.getMobile()))
			throw new RuntimeException(user.getFullname()+"添加微信通讯录失败 ：没有填写手机信息 ");
		
		if( this.queryUser(user)==true) {
			this.update(user);
			return;
		}
		
		WxUser wxUser = OrgConvertUtil.userToWxUser(user);
		if(BeanUtils.isEmpty(wxUser.getDepartment())) return;
		
		String resultJson = HttpUtil.sendHttpsRequest(WeixinConsts.getCreateUserUrl(), wxUser.toString(), "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		
		String errcode = result.getString("errcode");
		if("0".equals(errcode)){
			user.setHasSyncToWx(1);
			userManager.update(user);
			return;
		}
		// 表示已经存在
		if("60102".equals(errcode)){
			this.update(user);
			return;
		}
		throw new RuntimeException(user.getFullname()+"添加微信通讯录失败 ： "+result.getString("errmsg"));
		
	}

	@Override
	public void update(User user){
		WxUser wxUser = OrgConvertUtil.userToWxUser(user);

		if(this.queryUser(user)==false) 
			throw new RuntimeException(wxUser.getName()+"更新微信通讯录失败 ： 该用户不存在企业微信通讯录");
		//验证存在，即可更新
		String url=WeixinConsts.getUpdateUserUrl();
		String resultJson = HttpUtil.sendHttpsRequest(url, wxUser.toString(), "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		
		if(!"0".equals(result.getString("errcode"))){
			throw new RuntimeException(wxUser.getName()+"更新微信通讯录失败 ： "+result.getString("errmsg"));
		}
		
	}

	@Override
	public void delete(String userId) {
		String url=WeixinConsts.getDeleteUserUrl()+userId;
		String resultJson = HttpUtil.sendHttpsRequest(url, "", "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		if("0".equals(result.getString("errcode"))) return;
		
		throw new RuntimeException(userId+"删除微信通讯录失败 ： "+result.getString("errmsg"));
	}
	
	@Override
	public void deleteAll(String userIds) {
		JSONObject users= new JSONObject();
		users.put("useridlist", userIds.split(","));
		String resultJson = HttpUtil.sendHttpsRequest(WeixinConsts.getDeleteAllUserUrl(),users.toJSONString(), "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		if("0".equals(result.getString("errcode"))) return;
		
		throw new RuntimeException("批量删除微信通讯录用户失败 ： "+result.getString("errmsg"));
	}
	
	@Override
	public void addAll(List<User> users) {
		for (User user : users){
			//SysUser u =(SysUser) user;
			// u.getHasSyncToWx() ==1
			if(StringUtils.isEmpty(user.getMobile()))
				continue;
			
			this.create(user);
		}
	}
	
	public void invite(String wxUserId){
		JSONObject inviteData= new JSONObject();
		inviteData.put("userid", wxUserId);
		inviteData.put("invite_tips", SysPropertyUtil.getByAlias("wx.invite_tips","宏天流程业务平台邀请您关注！"));
		String resultJson = HttpUtil.sendHttpsRequest(WeixinConsts.getInviteUserUrl(),inviteData.toJSONString(), "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		if("0".equals(result.getString("errcode"))) return;
		logger.error("微信邀请失败！"+result.getString("errmsg"));
	}
	
	
	public boolean queryUser(User user){
		//获取企业微信用户，判断是否存在
		String getUserUrl=WeixinConsts.getUserUrl() + user.getAccount();
		String resultJsonUser = HttpUtil.sendHttpsRequest(getUserUrl, "", "POST");
		JSONObject userJson = JSONObject.parseObject(resultJsonUser);
		if("0".equals(userJson.getString("errcode"))){
			return true;
		}
		return false;
	}


	@Override
	public void syncUserToWx(String[] lAryId) {
		List<User> userList = new ArrayList<User>();
		iwxOrgService.syncAllOrg();
		if(BeanUtils.isNotEmpty(lAryId)){
			for (int i = 0; i < lAryId.length; i++) {
				User user= userManager.get(lAryId[i]);
				if(user != null) userList.add(user);
			}
		}else{
			userList = userManager.getAll();
		}
		addAll(userList);
	}
}

package com.hotent.mobile.weixin.impl;

import java.util.List;

import com.hotent.org.persistence.manager.OrgManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.HttpUtil;
import com.hotent.mobile.weixin.api.IWXOrgService;
import com.hotent.mobile.weixin.api.WeixinConsts;
import com.hotent.mobile.weixin.model.WxOrg;
import com.hotent.mobile.weixin.util.OrgConvertUtil;
import com.hotent.org.persistence.model.Org;

import javax.annotation.Resource;

@Component
public class WxOrgService implements IWXOrgService{
	


	protected Logger logger = LoggerFactory.getLogger(WxOrgService.class);

	@Resource
	OrgManager orgManager;




	@Override
	public void create(Org org) {
		WxOrg wxorg = OrgConvertUtil.sysOrgToWxOrg(org);
		String strurl=WeixinConsts.getCreateOrgUrl();
		String json=wxorg.toString();
		String resultJson = HttpUtil.sendHttpsRequest(strurl, wxorg.toString(), "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		String orgId=result.getString("id");
		org.setWxOrgId(result.getString("id"));
		String errcode = result.getString("errcode");
		if("0".equals(errcode)){
			orgManager.update(org);
			return;
		}
		logger.debug(wxorg.toString());
		logger.debug(resultJson);
		// 表示已经存在 跳过
		if("60008".equals(errcode)){
			return;
		}
		throw new RuntimeException(org.getName()+" 添加微信通讯录组织失败 ： "+result.getString("errmsg"));
	}

	@Override
	public void update(Org org) {
		WxOrg wxorg = OrgConvertUtil.sysOrgToWxOrg(org);
		String resultJson = HttpUtil.sendHttpsRequest(WeixinConsts.getUpdateOrgUrl(), wxorg.toString(), "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		String errcode = result.getString("errcode");
		if("0".equals(errcode))return;
		
		throw new RuntimeException(org.getName()+"添加微信通讯录组织失败 ： "+result.getString("errmsg"));
	}

	@Override
	public void delete(String orgId) {
		String url=WeixinConsts.getDeleteOrgUrl()+orgId;
		String resultJson = HttpUtil.sendHttpsRequest(url, "", "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		
		if("0".equals(result.getString("errcode"))) return;
		//尚未同步的组织
		if("60003".equals(result.getString("errcode"))){
			logger.error(orgId+"删除微信通讯录失败 ： "+result.getString("errmsg"));
			return;
		}
		
		throw new RuntimeException(orgId+"删除微信通讯录失败 ： "+result.getString("errmsg"));
	}

	@Override
	public void deleteAll(String orgIds) {
		
		String delUrl=WeixinConsts.getDeleteAllUserUrl();
		JSONObject users= new JSONObject();
		users.put("useridlist", orgIds.split(","));
		String resultJson = HttpUtil.sendHttpsRequest(delUrl,users.toJSONString(), "POST");
		JSONObject result = JSONObject.parseObject(resultJson);
		if("0".equals(result.getString("errcode"))) return;
		throw new RuntimeException("批量删除微信通讯录用户失败 ： "+result.getString("errmsg"));
		
	}
	
	/**
	 * 根据微信组织代码获取该组织下所有成员
	 * @param orgCode 微信组织代码
	 * @return
	 */
	public String getDepartmentUser(String orgCode){
		String departmentUrl = WeixinConsts.getDepartmentUrl(orgCode);
		String departmentJson = HttpUtil.sendHttpsRequest(departmentUrl, "", WeixinConsts.METHOD_GET);
		JSONObject departmentResult = JSONObject.parseObject(departmentJson);
		if("0".equals(departmentResult.getString("errcode"))){
			JSONArray users = departmentResult.getJSONArray("userlist");
			String[] userAccounts= new String[users.size()]; 
			for(int i = 0 ; i<users.size() ; i++){
				JSONObject user = users.getJSONObject(i);
				userAccounts[i] = user.getString("userid");
			}
			return StringUtils.join(userAccounts,",");
		}
		throw new RuntimeException("批量删除微信通讯录用户失败 ： "+departmentResult.getString("errmsg"));
	}
	
	@Override
	public void addAll(List<Org> orgList){
		for (Org org : orgList){
			this.create(org);
		}
	}

	@Override
	public void syncAllOrg() {
		List<Org> orgs = orgManager.getByParentId("0");
		if(orgs.size()>=0){
			for (Org rootOrg : orgs) {
				syncOrgsByParentId(rootOrg.getId());
			}
		}
	}

	public void syncOrgsByParentId(String parentId){
		List<Org> orgs = orgManager.getByParentId(parentId);
		addAll(orgs);
		for (Org org : orgs) {
			syncOrgsByParentId(org.getId());
		}
	}
}

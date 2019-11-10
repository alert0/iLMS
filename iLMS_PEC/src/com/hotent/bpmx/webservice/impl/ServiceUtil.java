package com.hotent.bpmx.webservice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.util.ContextUtil;

public class ServiceUtil {
	
	
	/**
	 * 构建pagelist的返回json数据。
	 * @param result
	 * @param list
	 * @param queryFilter
	 */
	public static void buildListReuslt(JSONObject result,PageList list , DefaultQueryFilter queryFilter){
		result.put("totalCount", list.getPageResult().getTotalCount());
		result.put("pageSize", queryFilter.getPage().getPageSize());
		result.put("currentPage", queryFilter.getPage().getPageNo());
		result.put("state", "0");
		result.put("msg", "操作成功");
		String json=com.alibaba.fastjson.JSONObject.toJSONString(list);
		
		result.put("list", json);
	}
	
	/**
	 * 构建list 返回 json数据。
	 * @param result
	 * @param list
	 */
	public static void bulidListResult(JSONObject result,List list){
		result.put("totalCount", list.size());
		result.put("state", "0");
		result.put("msg", "操作成功");
		result.put("list", GsonUtil.toJsonTree(list).toString());
	}
	
	/**
	 * 构建消息。
	 * @param success
	 * @param message
	 * @param rtnObj
	 * @return
	 */
	public static String bulidMessage(boolean success,String message,Object rtnObj){
		JSONObject result = new JSONObject();
		result.put("state", success ? "0" : "-1");
		if (success){
			result.put("msg", "操作成功： " + message);
			if(BeanUtils.isNotEmpty(rtnObj)){
				result.accumulate("rtn", rtnObj);
			}
		}else{
			throw new RuntimeException(message);
		}
		return result.toString();
	}
	
	/**
	 * 构建消息。
	 * @param success
	 * @param message
	 * @param rtnObj
	 * @return
	 */
	public static Map<String,Object> bulidMapMessage(boolean success,String message,Object rtnObj){
		Map<String,Object> map = new  HashMap<String, Object>();
		map.put("state", success ? "0" : "-1");
		if (success){
			map.put("msg", "操作成功： " + message);
			if(BeanUtils.isNotEmpty(rtnObj)){
				map.put("rtn", rtnObj);
			}
		}else{
			throw new RuntimeException(message);
		}
		return map;
	}
	
	/**
	 * 根据用户账户获取用户
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static IUser getUserByAccount(String account) throws Exception
	{
		if (StringUtil.isEmpty(account))
		{// 没传用户就报错
			throw new RuntimeException("必须传入用户账号(account)!");
		}
		
		IUserService userServiceImpl=AppUtil.getBean(IUserService.class);
		IUser user = userServiceImpl.getUserByAccount(account);
		if (BeanUtils.isEmpty(user))
		{
			throw new RuntimeException("该账号的用户不存在,请确认传入的账户（account）信息是否匹配!");
		}
		return user;
	}
	
	/**
	 * 通过userId获取用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static IUser getUserById(String userId) throws Exception{
		if (StringUtil.isEmpty(userId))
		{
			throw new RuntimeException("必须传入用户ID(userId)!");
		}
		IUserService userServiceImpl=AppUtil.getBean(IUserService.class);
		IUser user = userServiceImpl.getUserById(userId);
		if (BeanUtils.isEmpty(user))
		{
			throw new Exception("该用户不存在,请确认传入的userId是否存在");
		}
		return user;
	}
	
	/**
	 * 根据流程处理人抽取用户列表
	 * @param bpmIdentities
	 * @return
	 * @throws Exception
	 */
	public static List<IUser> extractUser(List<BpmIdentity> bpmIdentities) throws Exception{
		if (BeanUtils.isEmpty(bpmIdentities))
		{
			throw new RuntimeException("必须传入流程处理人(bpmIdentities)!");
		}
		BpmIdentityExtractService bpmIdentityExtractService = AppUtil.getBean(BpmIdentityExtractService.class);
		List<IUser> extractUser = bpmIdentityExtractService.extractUser(bpmIdentities);
		// 去重复
		BeanUtils.removeDuplicate(extractUser);
		return extractUser;
	}
	
	public static void setCurrentUser(String account) throws Exception{
		if(StringUtil.isEmpty(account)){
			throw new RuntimeException("必须传入用户账号(account)");
		}
		IUser user = getUserByAccount(account);
		if (user == null) {
			throw new Exception(String.format("用户账号(account):%s不存在", account));
		}
		ContextUtil.setCurrentUser(user);
	}
}

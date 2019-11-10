package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmDefUser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;


/**
 * 对象功能:流程定义权限明细 Manager类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 14:10:50
 */
public interface BpmDefUserManager extends Manager<String, BpmDefUser>{
	/**
	 * 获取首页栏目权限
	 * @param id
	 * @param objType
	 * @return
	 */
	public JSONArray getRights(String id,String objType);
	/**
	 * 保存首页栏目权限
	 * @param id
	 * @param objType
	 * @param ownerNameJson
	 */
	public void saveRights(String id,String objType,String ownerNameJson);
	/**
	 * 通过objType获取当前用户权限
	 * @param objType
	 * @return
	 */
	public List<String> getAuthorizeIdsByUserMap(String objType);
	/**
	 * 判断用户对某个模块数据是否有权限
	 * @param userID
	 * @param authorizeId
	 */
	public boolean hasRights(String authorizeId);
}

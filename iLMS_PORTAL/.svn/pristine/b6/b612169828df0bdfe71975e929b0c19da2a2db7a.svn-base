/**
 * 对象功能:流程分管授权限用户中间表明细 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 10:10:53
 */
package com.hotent.bpmx.persistence.dao;


import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmDefAct;


public interface BpmDefActDao extends Dao<String, BpmDefAct>{
	
	
	/**
	 * 获取所有的授权的流程
	 * @param params
	 * @return
	 */
	public  List<BpmDefAct> getActByMap(Map<String,Object> params);
	
	/**
	 * 根据授权ID删除流程授权的权限信息
	 * @param typeId
	 * @return
	 */
	public void delByAuthorizeId(String authorizeId);
	
	/**
	 * 根据流程相关信息删除流程授权的权限信息
	 * @param params
	 * @return
	 */
	public void delByMap(Map<String,Object> params);
	
	/**
	 * 获取与用户相关的授权的流程
	 * @param params
	 * @return
	 */
	public  List<BpmDefAct>  getActRightByUserMap(Map<String,String> map,String authorizeType,String defKey);
}
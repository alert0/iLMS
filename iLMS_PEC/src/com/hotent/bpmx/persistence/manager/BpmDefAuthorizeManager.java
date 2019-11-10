package com.hotent.bpmx.persistence.manager;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmDefAuthorize;

/**
 * 对象功能:流程定义权限明细 Manager类
 *  开发公司:广州宏天软件有限公司 
 *  开发人员:xucx 
 *  创建时间:2014-03-05 10:10:50
 */
public interface BpmDefAuthorizeManager extends Manager<String, BpmDefAuthorize>
{

	/**
	 * 获取流程分管授权列表信息
	 * 
	 * @param queryFilter
	 * @return List<BpmDefAuthorize>
	 */
	List<BpmDefAuthorize> getAuthorizeListByFilter(QueryFilter queryFilter);

	/**
	 * 获取流程分管授权所有信息
	 * 
	 * @param id
	 * @return BpmDefAuthorize
	 */
	BpmDefAuthorize getAuthorizeById(String id);

	/**
	 * 根据参数内容获取流程分管授权所有信息
	 * 
	 * @param id
	 * @param isNeedjson
	 * @return BpmDefAuthorize
	 */
	BpmDefAuthorize getAuthorizeById(String id,  boolean isNeedjson);

	/**
	 * 按ID数据删除流程分管授权所有信息
	 * 
	 * @param bpmDefAuthorize
	 * @return 
	 */
	void deleteAuthorizeByIds(String[] lAryId);

	/**
	 * 保存或修改流程分管授权所有信息
	 * 
	 * @param bpmDefAuthorize
	 * @return Long
	 */
	String saveOrUpdateAuthorize(BpmDefAuthorize bpmDefAuthorize);

	

	

	/**
	 * 查询自己相关的分管授权的流程权限
	 * 
	 * @param userId  指定用户ID
	 * @param authorizeType 授权类型
	 * @param isRight （是否包括流程操作细化的权限）
	 * @param isMyDef （是否包括自己创建的流程的所有权限，即自己创建的流程就拥有所有权限）
	 * @param authorizeType start,management,task,instance
	 * @return Map<String,Object> :包括： authorizeIds对象和authorizeRightMap对象
	 *         authorizeIds:授权定义的ID，以逗号隔开
	 *         authorizeRightMap：流程授权的对象，即Map<String,AuthorizeRight>
	 */
	Map<String, Object> getActRightByUserId(String userId, String authorizeType, boolean isRight, boolean isMyDef);
	
	
	/**
	 * 根据流程定义KEY和用户ID获取流程相关的权限。
	 * @param defKey	
	 * @param authorizeType
	 * @return	返回权限格式如下：
	 * 	key 为权限类型
	 * 	value 为权限值
	 */
	JSONObject getRight(String defKey,String authorizeType);

}

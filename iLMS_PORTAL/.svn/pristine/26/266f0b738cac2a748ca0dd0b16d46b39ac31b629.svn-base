package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.dao.BpmTaskDao;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.org.api.model.IGroup;

@Repository
public class BpmTaskDaoImpl extends MyBatisDaoImpl<String, DefaultBpmTask> implements BpmTaskDao
{

	@Override
	public String getNamespace()
	{
		return DefaultBpmTask.class.getName();
	}

	@Override
	public DefaultBpmTask getByRelateTaskId(String relateTaskId)
	{
		DefaultBpmTask task = this.getUnique("getByRelateTaskId", relateTaskId);
		return task;
	}

	/**
	 * 删除关联任务。
	 */
	@Override
	public void removeByTaskId(String taskId)
	{
		this.deleteByKey("removeByTaskId", taskId);
	}

	@Override
	public List<DefaultBpmTask> getByUserId(String userId, Map<String, String> groupMap)
	{
		Map<String, Object> params = getParamByGroupMap(userId, groupMap, null);
		return this.getByKey("getByUserId", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmTask> getByUserId(String userId, Map<String, String> groupMap, Page page)
	{
		Map<String, Object> params = getParamByGroupMap(userId, groupMap, null);
		return this.getByKey("getByUserId", params, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmTask> getByUserId(String userId, Map<String, String> groupMap, QueryFilter queryFilter)
	{
		Map<String, Object> params = getParamByGroupMap(userId, groupMap, queryFilter);
		return this.getByKey("getByUserId", params, queryFilter.getPage());
	}

	private Map<String, Object> getParamByGroupMap(String userId, Map<String, String> groupMap, QueryFilter queryFilter)
	{
		Map<String, Object> params = queryFilter.getParams();
		if (params == null)
			params = new HashMap<String, Object>();

		params.put("groupMap", groupMap);
		params.put("assignee", userId);

		if (queryFilter != null&&!queryFilter.getFieldLogic().getSql().equals(""))
			params.put("whereSql", queryFilter.getFieldLogic().getSql());
		if (queryFilter.getFieldSortList() != null&&queryFilter.getFieldSortList().size()>0)
		{
			String sort = queryFilter.getFieldSortList().get(0).getDirection().name();
			String order = queryFilter.getFieldSortList().get(0).getField();
			params.put("orderBySql",order+" "+sort);
		}
		return params;
	}

	@Override
	public List<DefaultBpmTask> getByInstId(String instId)
	{
		Map<String, Object> params = buildMapBuilder().addParam("instId", instId).getParams();
		return this.getByKey("getByInstId", params);
	}

	@Override
	public List<DefaultBpmTask> getByExeIdAndNodeId(String instId, String nodeId)
	{
		Map<String, Object> params = buildMapBuilder().addParam("instId", instId).addParam("nodeId", nodeId).getParams();
		return this.getByKey("getByExeIdAndNodeId", params);
	}

	@Override
	public List<DefaultBpmTask> getByInstUser(String instId, String userId)
	{
		Map<String, Object> params = buildMapBuilder().addParam("instId", instId).addParam("assigneeId", userId).getParams();
		return this.getByKey("getByInstUser", params);
	}

	@Override
	public List<DefaultBpmTask> getByBpmInstIdUserIdGroupList(String bpmnInstId, String userId, List<IGroup> groupList)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bpmnInstId", bpmnInstId);
		params.put("userId", userId);
		params.put("groupList", groupList);
		return this.getByKey("getByBpmInstIdUserIdGroupList", params);
	}

	@Override
	public void updateAssigneeById(String taskId, String userId)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", taskId);
		params.put("assigneeId", userId);
		this.updateByKey("updateAssigneeById", params);
	}

	@Override
	public void updateAssigneeOwnerId(String taskId, String ownerId, String assigneeId)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", taskId);
		params.put("ownerId", ownerId);
		params.put("assigneeId", assigneeId);
		this.updateByKey("updateAssigneeOwnerId", params);
	}

	@Override
	public void delByInstList(List<String> instList)
	{
		this.deleteByKey("delByInstList", instList);
	}

	@Override
	public void delByParentId(String parentId)
	{
		this.deleteByKey("delByParentId", parentId);
	}

	@Override
	public List<DefaultBpmTask> getByParentId(String parentTaskId)
	{
		return this.getByKey("getByParentId", parentTaskId);
	}

	@Override
	public List<DefaultBpmTask> getReminderTask()
	{
		return this.getByKey("getReminderTask", new HashMap<String, Object>());
	}

	@Override
	public List<DefaultBpmTask> getByInstList(List<String> instIds)
	{
		return this.getByKey("getByInstList", instIds);

	}

	@Override
	public PageList<DefaultBpmTask> getMyTransTask(String userId, QueryFilter queryFilter)
	{
		queryFilter.addParamsFilter("userId", userId);
		PageList<DefaultBpmTask> pageList = (PageList<DefaultBpmTask>) this.getByQueryFilter("getMyTransTask", queryFilter);
		return pageList;
	}

	@Override
	public void updateTaskPriority(String taskId, Long priority) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		map.put("priority", priority);
		this.updateByKey("updateTaskPriority", map);
	}

	@Override
	public List<DefaultBpmTask> getByExecuteAndNodeId(String executeId, String nodeId) {
		Map<String, Object> params= buildMapBuilder()
				.addParam("executeId", executeId)
				.addParam("nodeId", nodeId).getParams();
		return this.getByKey("getByExecuteAndNodeId", params);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmTask> getByNeedPendMeetingUserId(String userId, Map<String, String> groupMap,
			QueryFilter queryFilter) {
		Map<String, Object> params = getParamByGroupMap(userId, groupMap, queryFilter);
		return this.getByKey("getByNeedPendMeetingUserId", params, queryFilter.getPage());
	}
	

}

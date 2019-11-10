package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmExeStackDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackExecutorDao;
import com.hotent.bpmx.persistence.model.BpmExeStack;


@Repository
public class BpmExeStackDaoImpl extends MyBatisDaoImpl<String, BpmExeStack> implements BpmExeStackDao{
	@Resource
	BpmExeStackExecutorDao exeStackExecutorDao;

    @Override
    public String getNamespace() {
        return BpmExeStack.class.getName();
    }


	@Override
	public List<BpmExeStack> getByInstNodeToken(String procInstId, String nodeId,
			String token) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",procInstId)
				.addParam("nodeId", nodeId)
				.addParam("taskToken", token)
				.getParams();
		return this.getByKey("getByInstNodeToken", params);
	}

	@Override
	public BpmExeStack getInitStack(String instId) {
		BpmExeStack stack=this.getUnique("getInitStack", instId);
		return stack;
	}

	@Override
	public void removeByPath(String instId, String path) {
		exeStackExecutorDao.deleteByStackPath(path);
		
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeByPath", params);
	}
	/**
	 * 删除堆栈，除了初始的第一个不删除，即parentid为0的不删除
	 * @param instId
	 * @param path
	 */
	@Override
	public void removeExeStackExceptParentId(String instId,String parentId) {	
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("parentId",parentId)
				.getParams();

		this.deleteByKey("removeExeStackExceptParentId", params);
	}
	@Override
	public void removeBpmTaskByPath(String instId, String path) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeBpmTaskByPath", params);
	}
	@Override
	public void removeBpmExeStackRelation(String instId, String path) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeBpmExeStackRelationInToStackId", params);
		this.deleteByKey("removeBpmExeStackRelationInFromStackId", params);
	}
	
	@Override
	public void removeBpmTaskCandidateByPath(String instId, String path) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeBpmTaskCandidateByPath", params);
	}
	
	@Override
	public void removeBpmTaskTurnByPath(String instId, String path) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeBpmTaskTurnByPath", params);
	}
	
	
	@Override
	public void removeActRuTaskByPath(String instId, String path,String notIncludeExecuteIds) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("notIncludeExecuteIds", notIncludeExecuteIds)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeActRuTaskByPath", params);
	}
	
	@Override
	public void removeActRuVariableByPath(String instId, String path,String notIncludeExecuteIds) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("notIncludeExecuteIds", notIncludeExecuteIds)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeActRuVariableByPath", params);
	}
	
	
	
	@Override
	public void removeActRuExeCutionByPath(String instId, String path,String notIncludeExecuteIds) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("notIncludeExecuteIds", notIncludeExecuteIds)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removeActRuExeCutionByPath", params);
	}

	@Override
	public void removebpmProStatusPath(String instId, String path) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("procInstId",instId)
				.addParam("nodePath", path +"%")
				.getParams();

		this.deleteByKey("removebpmProStatusPath", params);
	}
	
	/**
	 * 按流程图执行多实例退回时调整Bpm任务表
	 * @param rejectAfterExecutionId
	 */
	@Override
	public void multipleInstancesRejectAdjustOnBpmTask(String rejectAfterExecutionId) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("rejectAfterExecutionId",rejectAfterExecutionId)
				.getParams();

		this.updateByKey("multipleInstancesRejectAdjustOnBpmTask", params);
	}
	/**
	 * 按流程图执行多实例退回时调整Act任务表
	 * @param rejectAfterExecutionId
	 */
	@Override
	public void multipleInstancesRejectAdjustOnActTask(String rejectAfterExecutionId) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("rejectAfterExecutionId",rejectAfterExecutionId)
				.getParams();


		this.updateByKey("multipleInstancesRejectAdjustOnActTask", params);
	}
	/**
	 *  按流程图执行多实例退回时调整Act执行表
	 * @param actProcInstanceId
	 */
	@Override
	public void multipleInstancesRejectAdjustOnActExecution(String actProcInstanceId) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("actProcInstanceId",actProcInstanceId)
				.getParams();

		this.deleteByKey("multipleInstancesRejectAdjustOnActExecution", params);
	}
	
	@Override
	public void remove(String entityId) {
		exeStackExecutorDao.deleteByStackId(entityId);
		super.remove(entityId);
	}
	
	@Override
	public List<BpmExeStack> getByIds(String[] ids) {
		Map<String, Object> params = buildMap("ids", ids);
		return this.getByKey("getByIds", params); 
	}
}


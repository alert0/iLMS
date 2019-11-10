package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskCandidate;


public interface BpmTaskCandidateDao  extends Dao<String, DefaultBpmTaskCandidate> {
	public void removeByTaskId(String taskId);
	public List<DefaultBpmTaskCandidate> queryByTaskId(String taskId);
	
	/**
	 * 通过任务ID、执行人ID、类型ID取得候选人记录
	 * @param taskId
	 * @param executorId
	 * @param type 值为'user',或DefaultGroup中的dimKey值
	 * @return 
	 * DefaultBpmTaskCandidate
	 */
	DefaultBpmTaskCandidate getByTaskIdExeIdType(String taskId,String executorId,String type);
	
	
	/**
	 * 根据流程实例列表删除候选人数据。 
	 * @param instList 
	 * void
	 */
	void delByInstList(List<String> instList);
	
	
	/**
	 * 根据流程实例列表获取任务候选人。
	 * @param instList
	 * @return 
	 * List&lt;DefaultBpmTaskCandidate>
	 */
	List<DefaultBpmTaskCandidate> getByInstList(List<String> instList);
}



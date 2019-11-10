package com.hotent.bpmx.api.service;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.process.task.TaskTrans;
import com.hotent.org.api.model.IUser;

/**
 * 流转任务接口。
 * <pre> 
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-31-下午12:00:18
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface TaskTransService {
	/**
	 * 结束流转任务。
	 * <pre>
	 * 	1.删除本任务。
	 *  2.发送通知给发送人。
	 * 	2.根据父任务ID修改票数，同意和反对的票数。
	 * 	3.判断流程是否完成。
	 * 		如果完成执行是否返回或或提交。
	 * 		如果是再派生的。
	 * 			1.如果为返回修改这个父任务状态为trans。
	 * 			2.如果提交则根据提交的结果，对父任务进行投票。
	 *  4.如果未完成那么判断流程是否是并行，如果是串行，那么取得下一个执行人，并产生任务。
	 *  
	 * </pre>
	 * @param taskId		任务ID
	 * @param actionName	审批的意见同意或反对
	 * @param opinion 		
	 * void
	 */
	void completeTask(String taskId,String actionName,String notifyType, String opinion);
	
	
	/**
	 * 获取我流转出去的流转任务。
	 * 根据规则判断是否完成。
	 * @param taskId 
	 * void
	 */
	PageList getMyTransTask(String userId,QueryFilter queryFilter);
	
	
	/**
	 * 撤销我的流转出去任务。
	 * <pre>
	 * 	1.根据当前任务查找下面的所有子任务。
	 * 	2.删除这些子任务并给子任务人员发送给通知。
	 * 	3.将当前任务修改状态。
	 * 		普通任务修改为normal。
	 * 		流转任务修改为transformed.
	 * </pre>
	 * @param taskId
	 * @param opinion 
	 * void
	 */
	void withDraw(String taskId,String notifyType,String opinion);

	/**
	 * 添加流转任务。
	 * <pre>
	 * 	1.选择多个执行人，那么会产生多个任务。
	 * 		在BPM_TASK表中添加多条任务数据，任务类型为trans，这些任务的exec_id_为空，表示为派生的任务。
	 * 		这些任务父ID为parentTaskId。
	 * 	2.更新父任务类型为startTrans，这类任务在查询是不可见，并且检查不能执行。
	 * 	3.在bpm_task_trans表中添加一条记录，添加会签规则。
	 * 	4.根据通知类型发送通知。
	 * </pre>
	 * @param list
	 * @param opinion
	 * @param notifyType 
	 * void
	 */
	void addTransTask(TaskTrans taskTrans, List<IUser> listUsers, String notifyType, String opinion);
	
	/**
	 * 获取流转信息
	 * @param taskId
	 * @return
	 */
	TaskTrans getTransTaskByTaskId(String taskId);
}

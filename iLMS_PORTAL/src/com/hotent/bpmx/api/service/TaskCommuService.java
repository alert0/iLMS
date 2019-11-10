package com.hotent.bpmx.api.service;

import java.util.List;

import com.hotent.org.api.model.IUser;

/**
 * 沟通任务。
 * <pre> 
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-8-5-下午5:59:05
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface TaskCommuService {
	
	/**
	 * 添加沟通任务。 
	 * @param taskId
	 * @param notifyType
	 * @param opinion
	 * @param users 
	 * void
	 */
	void addCommuTask(String taskId,String notifyType,String opinion,List<IUser> users);
	
	/**
	 * 完成沟通任务。
	 * <pre>
	 * 1.删除任务。
	 * 2.发送通知。
	 * </pre>
	 * @param taskId
	 * @param notifyType
	 * @param opinion 
	 * void
	 */
	void completeTask(String taskId,String notifyType,String opinion);
	
	
	/**
	 * 在任务完成的时候调用，用来删除沟通任务。
	 * @param parentId 
	 * void
	 */
	void finishTask(String parentId);

}

package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmTaskReminder;

/**
 * 
 * <pre> 
 * 描述：任务催办 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:52:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmTaskReminderManager extends Manager<String, BpmTaskReminder>{
	void deleteByTaskId(String id);
	/**
	 * 获取需要触发的催办项
	 * @return
	 */
	List<BpmTaskReminder> getTriggerReminders();
	
}

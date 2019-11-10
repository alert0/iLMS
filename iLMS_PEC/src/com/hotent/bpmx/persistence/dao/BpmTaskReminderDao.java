package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmTaskReminder;

/**
 * 
 * <pre> 
 * 描述：任务催办 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:52:35
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmTaskReminderDao extends Dao<String, BpmTaskReminder> {

	/**
	 * 根据任务删除催办。
	 * @param taskId
	 */
	void deleteByTaskId(String taskId);

	/**
	 * 获取可以触发的催办信息。
	 * @return
	 */
	List<BpmTaskReminder> getTriggerReminders();
}

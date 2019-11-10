package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskReminderDao;
import com.hotent.bpmx.persistence.model.BpmTaskReminder;
import com.hotent.bpmx.persistence.manager.BpmTaskReminderManager;

/**
 * 
 * <pre> 
 * 描述：任务催办 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:52:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmTaskReminderManager")
public class BpmTaskReminderManagerImpl extends AbstractManagerImpl<String, BpmTaskReminder> implements BpmTaskReminderManager{
	@Resource
	BpmTaskReminderDao bpmTaskReminderDao;
	@Override
	protected Dao<String, BpmTaskReminder> getDao() {
		return bpmTaskReminderDao;
	}
	@Override
	public void deleteByTaskId(String taskId) {
		bpmTaskReminderDao.deleteByTaskId(taskId);		
	}
	
	@Override
	public List<BpmTaskReminder> getTriggerReminders() {
		return bpmTaskReminderDao.getTriggerReminders();
	}
}

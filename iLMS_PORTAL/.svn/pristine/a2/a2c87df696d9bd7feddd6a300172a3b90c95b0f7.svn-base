package com.hotent.bpmx.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskReminderDao;
import com.hotent.bpmx.persistence.model.BpmTaskReminder;

/**
 * 
 * <pre> 
 * 描述：任务催办 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf	
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:52:35
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmTaskReminderDaoImpl extends MyBatisDaoImpl<String, BpmTaskReminder> implements BpmTaskReminderDao{

    @Override
    public String getNamespace() {
        return BpmTaskReminder.class.getName();
    }

	@Override
	public void deleteByTaskId(String taskId) {
		this.deleteByKey("deleteByTaskId", taskId);
	}

	@Override
	public List<BpmTaskReminder> getTriggerReminders() {
		Date thisDate  = new Date();
		return this.getByKey("getTriggerReminders",thisDate);
	}
	
}


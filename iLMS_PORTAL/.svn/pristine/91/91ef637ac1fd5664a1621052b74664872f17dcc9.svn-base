package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmReminderHistoryDao;
import com.hotent.bpmx.persistence.model.BpmReminderHistory;
import com.hotent.bpmx.persistence.manager.BpmReminderHistoryManager;

/**
 * 
 * <pre> 
 * 描述：催办历史 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf	
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:46:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmReminderHistoryManager")
public class BpmReminderHistoryManagerImpl extends AbstractManagerImpl<String, BpmReminderHistory> implements BpmReminderHistoryManager{
	@Resource
	BpmReminderHistoryDao bpmReminderHistoryDao;
	@Override
	protected Dao<String, BpmReminderHistory> getDao() {
		return bpmReminderHistoryDao;
	}
}

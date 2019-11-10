package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskDueTimeDao;
import com.hotent.bpmx.persistence.manager.BpmTaskDueTimeManager;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;

/**
 * 
 * <pre> 
 * 描述：任务期限统计 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-05-16 16:25:22
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmTaskDueTimeManager")
public class BpmTaskDueTimeManagerImpl extends AbstractManagerImpl<String, BpmTaskDueTime> implements BpmTaskDueTimeManager{
	@Resource
	BpmTaskDueTimeDao bpmTaskDueTimeDao;
	@Override
	protected Dao<String, BpmTaskDueTime> getDao() {
		return bpmTaskDueTimeDao;
	}
	
	/**
	 * 根据任务id, 获取最新的任务期限
	 */
	@Override
	public BpmTaskDueTime getByTaskId(String taskId) {
		return bpmTaskDueTimeDao.getByTaskId(taskId);
	}

	@Override
	public void updateAndSave(BpmTaskDueTime bpmTaskDueTime) {
		BpmTaskDueTime dueTime =  bpmTaskDueTimeDao.get(bpmTaskDueTime.getId());
		dueTime.setIsNew((short)0);
		bpmTaskDueTimeDao.update(dueTime);
		bpmTaskDueTime.setId(UniqueIdUtil.getSuid());
		bpmTaskDueTimeDao.create(bpmTaskDueTime);
	}
}

package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskTransRecordDao;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.bpmx.persistence.manager.BpmTaskTransRecordManager;

/**
 * 
 * <pre> 
 * 描述：bpm_task_trans_record 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-04 16:12:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmTaskTransRecordManager")
public class BpmTaskTransRecordManagerImpl extends AbstractManagerImpl<String, BpmTaskTransRecord> implements BpmTaskTransRecordManager{
	@Resource
	BpmTaskTransRecordDao bpmTaskTransRecordDao;
	@Override
	protected Dao<String, BpmTaskTransRecord> getDao() {
		return bpmTaskTransRecordDao;
	}
	@Override
	public BpmTaskTransRecord getByTaskId(String taskId) {
		return bpmTaskTransRecordDao.getByTaskId(taskId);
	}
	@Override
	public List<BpmTaskTransRecord> getMyTransRecord(QueryFilter queryFilter) {
		IUser user = ContextUtil.getCurrentUser();
		queryFilter.addParamsFilter("userId", user.getUserId());
		return bpmTaskTransRecordDao.getTransRecord(queryFilter);
	}
	@Override
	public List<BpmTaskTransRecord> getTransRecordList(QueryFilter queryFilter) {
		return bpmTaskTransRecordDao.getTransRecord(queryFilter);
	}
}

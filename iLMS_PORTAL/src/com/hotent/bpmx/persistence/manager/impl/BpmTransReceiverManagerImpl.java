package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmTransReceiverDao;
import com.hotent.bpmx.persistence.model.BpmTransReceiver;
import com.hotent.bpmx.persistence.manager.BpmTransReceiverManager;

/**
 * 
 * <pre> 
 * 描述：流转任务接收人 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-06 10:51:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmTransReceiverManager")
public class BpmTransReceiverManagerImpl extends AbstractManagerImpl<String, BpmTransReceiver> implements BpmTransReceiverManager{
	@Resource
	BpmTransReceiverDao bpmTransReceiverDao;
	@Override
	protected Dao<String, BpmTransReceiver> getDao() {
		return bpmTransReceiverDao;
	}
	@Override
	public List<BpmTransReceiver> getByTransRecordid(String transRecordid) {
		return bpmTransReceiverDao.getByTransRecordid(transRecordid);
	}
	@Override
	public BpmTransReceiver getByTransRecordAndUserId(Map<String, String> params) {
		return bpmTransReceiverDao.getByTransRecordAndUserId(params);
	}
}

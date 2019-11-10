package com.hanthink.gps.mail.dao.impl;

import java.util.List;

import com.hanthink.gps.mail.dao.QueueDao;
import com.hanthink.gps.mail.vo.QueueVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

public class QueueDaoImpl extends BaseDaoSupport implements QueueDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<QueueVO> queryQueueList(QueueVO queueVO) {
		
		return (List<QueueVO>) this.executeQueryForList("sw.select_queryQueueList", queueVO);
	}

	@Override
	public int updateSendQueue(List<QueueVO> updateList) {
		this.executeBatchUpdate("sw.update_updateSendQueue", updateList);
		return 1;
	}

	
	
}

package com.hanthink.gps.mail.service.impl;

import java.util.List;

import com.hanthink.gps.mail.dao.QueueDao;
import com.hanthink.gps.mail.service.QueueService;
import com.hanthink.gps.mail.vo.QueueVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

public class QueueServiceImpl extends BaseServiceImpl implements QueueService {
	
	private QueueDao queueDao;

	public QueueDao getQueueDao() {
		return queueDao;
	}
	public void setQueueDao(QueueDao queueDao) {
		this.queueDao = queueDao;
	}
	
	
	@Override
	public List<QueueVO> queryQueueList(QueueVO queueVO) {
		
		return queueDao.queryQueueList(queueVO);
	}
	@Override
	public int updateSendQueue(List<QueueVO> updateList) {
		return queueDao.updateSendQueue(updateList);
	}

	
	
	
	
}

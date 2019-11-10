package com.hanthink.gps.mail.dao;

import java.util.List;

import com.hanthink.gps.mail.vo.QueueVO;

public interface QueueDao {

	/**
	 * 查询队列信息
	 * @param queueVO
	 * @return
	 * @author zuosl 2016-6-30
	 */
	List<QueueVO> queryQueueList(QueueVO queueVO);

	/**
	 * 更新消息队列信息
	 * @param updateList
	 * @return
	 * @author zuosl 2016-7-1
	 */
	int updateSendQueue(List<QueueVO> updateList);

}

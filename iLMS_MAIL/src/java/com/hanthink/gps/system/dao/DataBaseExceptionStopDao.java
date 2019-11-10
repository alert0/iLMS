package com.hanthink.gps.system.dao;

import java.util.List;

import com.hanthink.gps.system.vo.ProErrorVO;

/**
 * @Title: DataBaseExceptionStopDao.java
 * @Package: com.hanthink.gps.system.dao
 * @Description: 数据库job异常停止,触发器异常提醒
 * @author dtp
 * @date 2019-5-27
 */
public interface DataBaseExceptionStopDao {

	/**
	 * 查询数据库JOB异常停止
	 * @param vo
	 * @return
	 */
	List<ProErrorVO> queryDBJobExceptionStop(ProErrorVO vo);

	/**
	 * 查询触发器异常
	 * @param vo
	 * @return
	 */
	List<ProErrorVO> queryDBTriggerExceptionStop(ProErrorVO vo);

	/**
	 * 查询接口异常
	 * @param vo
	 * @return
	 */
	List<ProErrorVO> queryIFExceptionList(ProErrorVO vo);


}

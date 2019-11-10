package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.system.dao.DataBaseExceptionStopDao;
import com.hanthink.gps.system.vo.ProErrorVO;

/**
 * @Title: DataBaseExceptionStopDaoImpl.java
 * @Package: com.hanthink.gps.system.dao.impl
 * @Description: 数据库job异常停止,触发器异常提醒
 * @author dtp
 * @date 2019-5-27
 */
public class DataBaseExceptionStopDaoImpl extends BaseDaoSupport 
		implements DataBaseExceptionStopDao{

	/**
	 * 查询数据库JOB异常停止
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProErrorVO> queryDBJobExceptionStop(ProErrorVO vo) {
		return (List<ProErrorVO>) this.executeQueryForList("gacne_db.queryDBJobExceptionStop", vo);
	}

	/**
	 * 查询触发器异常
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProErrorVO> queryDBTriggerExceptionStop(ProErrorVO vo) {
		return (List<ProErrorVO>) this.executeQueryForList("gacne_db.queryDBTriggerExceptionStop", vo);
	}

	/**
	 * 查询接口异常
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProErrorVO> queryIFExceptionList(ProErrorVO vo) {
		return (List<ProErrorVO>) this.executeQueryForList("gacne_db.queryIFExceptionList", vo);
	}

}

package com.hanthink.gps.system.dao;

import java.util.List;

import com.hanthink.gps.system.vo.ProErrorVO;

public interface SysErrorDao {

	/**
	 * 查询PMC存储过程执行错误的日志信息
	 * @return
	 * @author zuosl 2016-7-10
	 */
	List<ProErrorVO> queryProErrorInfoList();

}

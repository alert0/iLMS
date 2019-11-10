package com.hanthink.gps.system.service;

import java.util.List;

import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.system.vo.ProErrorVO;

public interface SysErrorService {

	/**
	 * 查询PMC存储过程执行错误的日志信息
	 * @return
	 * @author zuosl 2016-7-10
	 */
	List<ProErrorVO> queryProErrorInfoList();

	/**
	 * 查询系统参数
	 * @param vo
	 * @return
	 */
	SystemParamVO queryParamByParamCode(SystemParamVO vo);

}

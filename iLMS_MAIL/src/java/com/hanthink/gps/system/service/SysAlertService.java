/**
 * 
 */
package com.hanthink.gps.system.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.system.vo.SysAlertVO;

/**
 * 描述：系统警讯信息提醒Service
 * @author chenyong
 * @date   2016-10-18
 */
public interface SysAlertService extends BaseService{

	/**
	 * 获得A级异常信息
	 * return PECGpsMesIfErrorVO  A级异常信息
	 */
	public List<SysAlertVO> queryPECGpsMesIfErrorA();
	
	/**
	 * 获得非A级异常信息
	 * return PECGpsMesIfErrorVO  非A级异常信息
	 */
	public List<SysAlertVO> queryPECGpsMesIfErrorNotA();
	
	/**
	 * 修改非A级别的数据为已处理
	 */
	public void updateNotAIsHandle();
}

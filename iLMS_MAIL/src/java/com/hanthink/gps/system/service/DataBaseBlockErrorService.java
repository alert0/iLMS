/**
 * 
 */
package com.hanthink.gps.system.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.system.vo.DataBaseBlockErrorVO;

/**
 * 描述：数据库阻塞异常邮件提醒Servcie
 * @author chenyong
 * @date   2016-10-09
 */
public interface DataBaseBlockErrorService extends BaseService{
	
	//获取数据库阻塞异常的队列表
	public List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo();

	//获取数据库阻塞异常的队列表
	public List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo_PORTAL();
}

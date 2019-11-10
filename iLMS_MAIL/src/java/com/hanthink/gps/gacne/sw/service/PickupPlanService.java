package com.hanthink.gps.gacne.sw.service;

import java.util.List;

import com.hanthink.gps.gacne.sw.vo.PickupPlanVo;
import com.hanthink.gps.pub.service.BaseService;

/**
 * 
 * @author Administrator
 *
 */
public interface PickupPlanService  extends BaseService{

	/**
	 * 查询未反馈或下载取货计划的信息
	 * @param 
	 * @return
	 * @author zhengwuchao 2018-11-14
	 */

	List<PickupPlanVo> queryPickupPlanInfo();
	
	/**
	 * 查询未反馈或下载取货计划的信息
	 * @param 
	 * @return
	 * @author zhengwuchao 2018-11-14
	 */

	List<PickupPlanVo> queryPickupPlanNum(String user);
	
	
	
}

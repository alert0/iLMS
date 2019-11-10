package com.hanthink.gps.gacne.sw.dao;

import java.util.List;

import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
import com.hanthink.gps.gacne.sw.vo.PickupPlanVo;

public interface PickupPlanDao {

	/**
	 * 查询未查看公告的供应商数量
	 * @param infoId
	 * @return
	 * @author zhengwuchao 2018-11-14
	 */

	List<PickupPlanVo> queryPickupPlanInfo();

	List<PickupPlanVo> queryPickupPlanNum(String user);
	
}

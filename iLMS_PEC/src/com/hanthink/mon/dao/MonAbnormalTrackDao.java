package com.hanthink.mon.dao;


import java.util.List;
import com.hanthink.mon.model.MonAbnormalTrackModel;
import com.hotent.base.db.api.Dao;

/**
 * @ClassName: MonAbnormalTrackDao
 * @Description:异常监控看板
 * @author luocc
 * @date 2018年11月3日
 */
public interface MonAbnormalTrackDao extends Dao<String, MonAbnormalTrackModel>{
	
	/**
	 * 异常查询汇总
	 * <p>return: List<MonAbnormalTrackModel></p>  
	 * <p>Description: MonAbnormalTrackDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月10日
	 * @version 1.0
	 */
	List<MonAbnormalTrackModel> queryAbnormalForPage(MonAbnormalTrackModel model);

	
}

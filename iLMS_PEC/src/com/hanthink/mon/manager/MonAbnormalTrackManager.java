package com.hanthink.mon.manager;


import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.MonAbnormalTrackModel;
import com.hotent.base.manager.api.Manager;
/**
 * 异常监控
 * <p>Title: MonAbnormalTrackManager</p>  
 * <p>Description: MonAbnormalTrackManager.java</p>  
 * @author linzhuo  
 * @date 2019年4月10日
 * @version 1.0
 */
public interface MonAbnormalTrackManager extends Manager<String,MonAbnormalTrackModel> {
	/**
	 * 查询异常
	 * <p>return: List<MonAbnormalTrackModel></p>  
	 * <p>Description: MonAbnormalTrackManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月10日
	 * @version 1.0
	 */
	List<MonAbnormalTrackModel> queryAbnormalForPage(MonAbnormalTrackModel model);

}

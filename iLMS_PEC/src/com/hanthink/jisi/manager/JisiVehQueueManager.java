package com.hanthink.jisi.manager;

import java.util.List;

import com.hanthink.jisi.model.JisiPartModel;
import com.hanthink.jisi.model.JisiVehQueueModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface JisiVehQueueManager extends Manager<String, JisiVehQueueModel>{

	/**
	 * 
	 * @Description: PA OFF队列查询分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiVehQueueModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午6:13:37
	 */
	PageList<JisiVehQueueModel> queryJisiVehQueueForPage(JisiVehQueueModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 查询导出数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiVehQueueModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午6:20:52
	 */
	List<JisiVehQueueModel> queryJisiVehQueueByKey(JisiVehQueueModel model);

}

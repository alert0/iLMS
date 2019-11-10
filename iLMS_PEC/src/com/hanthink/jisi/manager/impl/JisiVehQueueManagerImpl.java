package com.hanthink.jisi.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jisi.dao.JisiVehQueueDao;
import com.hanthink.jisi.manager.JisiVehQueueManager;
import com.hanthink.jisi.model.JisiPartModel;
import com.hanthink.jisi.model.JisiVehQueueModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
@Service("JisiVehQueueManager")
public class JisiVehQueueManagerImpl extends AbstractManagerImpl<String, JisiVehQueueModel> implements JisiVehQueueManager{

	@Resource
	private JisiVehQueueDao dao;
	@Override
	protected Dao<String, JisiVehQueueModel> getDao() {
		
		return dao;
	}
	
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
	@Override
	public PageList<JisiVehQueueModel> queryJisiVehQueueForPage(JisiVehQueueModel model, DefaultPage p) {
		
		return dao.queryJisiVehQueueForPage(model,p);
	}

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
	@Override
	public List<JisiVehQueueModel> queryJisiVehQueueByKey(JisiVehQueueModel model) {
		
		return dao.queryJisiVehQueueByKey(model);
	}

}

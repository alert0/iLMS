package com.hanthink.jisi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jisi.dao.JisiVehQueueDao;
import com.hanthink.jisi.model.JisiPartModel;
import com.hanthink.jisi.model.JisiVehQueueModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
@Repository
public class JisiVehQueueDaoImpl extends MyBatisDaoImpl<String, JisiVehQueueModel> implements JisiVehQueueDao{

	@Override
	public String getNamespace() {
		
		return JisiVehQueueModel.class.getName();
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
	 * @date 2018年11月9日 下午6:14:33
	 */
	@Override
	public PageList<JisiVehQueueModel> queryJisiVehQueueForPage(JisiVehQueueModel model, DefaultPage p) {
		
		return (PageList<JisiVehQueueModel>) this.getByKey("queryJisiVehQueueForPage", model, p);
	}

	/**
	 * 
	 * @Description: 导出数据查询
	 * @param @param model
	 * @param @return   
	 * @return List<JisiVehQueueModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午6:21:33
	 */
	@Override
	public List<JisiVehQueueModel> queryJisiVehQueueByKey(JisiVehQueueModel model) {
		
		return this.getByKey("queryJisiVehQueueForPage", model);
	}

}

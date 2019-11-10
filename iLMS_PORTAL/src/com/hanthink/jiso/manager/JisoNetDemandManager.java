package com.hanthink.jiso.manager;

import java.util.List;

import com.hanthink.jiso.model.JisoNetDemandModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface JisoNetDemandManager extends Manager<String, JisoNetDemandModel>{

	/**
	 * 
	 * @Description: 分页查询厂外同步净需求
	 * @param @param model
	 * @param @param page
	 * @param @return   
	 * @return PageList<JisoInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月4日 上午10:17:28
	 */
	PageList<JisoNetDemandModel> queryJisoNetDemandPage(JisoNetDemandModel model, DefaultPage page);

	/**
	 * 
	 * @Description: 查询厂外同步导出数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisoNetDemandModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月4日 上午10:30:59
	 */
	List<JisoNetDemandModel> queryJisoNetDemandByKey(JisoNetDemandModel model);

}

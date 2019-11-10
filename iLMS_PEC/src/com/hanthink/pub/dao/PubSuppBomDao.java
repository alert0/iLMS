package com.hanthink.pub.dao;

import java.util.List;

import com.hanthink.pub.model.PubSuppBomModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface PubSuppBomDao extends Dao<String, PubSuppBomModel> {

	/**
	 * 
	 * @Description: 分页查询支给bom数据
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<PubSuppBomModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月16日 上午9:34:44
	 */
	PageList<PubSuppBomModel> queryPubSuppBomForPage(PubSuppBomModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 导出支给bom数据
	 * @param @param model
	 * @param @return   
	 * @return List<PubSuppBomModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月16日 上午9:35:08
	 */
	List<PubSuppBomModel> downloadPubSuppBomModel(PubSuppBomModel model);

}

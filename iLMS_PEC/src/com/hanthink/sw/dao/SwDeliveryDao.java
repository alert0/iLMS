package com.hanthink.sw.dao;

import com.hanthink.sw.model.SwDeliveryModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwDeliveryDao extends Dao<String, SwDeliveryModel>{

	/**
	 * 
	 * <p>Title: queryJisoDeliveryPage</p>  
	 * <p>Description: 发布数据管理界面，分页查询功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:09:49
	 */
	PageList<SwDeliveryModel> queryJisoDeliveryPage(SwDeliveryModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: queryJisoDeliveryDetailPage</p>  
	 * <p>Description: 发货数据管理界面，明细查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:14:02
	 */
	PageList<SwDeliveryModel> queryJisoDeliveryDetailPage(SwDeliveryModel model, DefaultPage p);

}

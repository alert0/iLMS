package com.hanthink.sw.dao.impl;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwDeliveryDao;
import com.hanthink.sw.model.SwDeliveryModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
@Repository
public class SwDeliveryDaoImpl extends MyBatisDaoImpl<String, SwDeliveryModel>
implements SwDeliveryDao{

	@Override
	public String getNamespace() {
		
		return SwDeliveryModel.class.getName();
	}

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
	@Override
	public PageList<SwDeliveryModel> queryJisoDeliveryPage(SwDeliveryModel model, DefaultPage p) {
		
		return (PageList<SwDeliveryModel>) this.getList("queryJisoDeliveryPage", model, p);
	}

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
	@Override
	public PageList<SwDeliveryModel> queryJisoDeliveryDetailPage(SwDeliveryModel model, DefaultPage p) {
		
		return (PageList<SwDeliveryModel>) this.getList("queryJisoDeliveryDetailPage", model, p);
	}

}

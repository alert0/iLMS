package com.hanthink.sw.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwDeliveryDao;
import com.hanthink.sw.manager.SwDeliveryManager;
import com.hanthink.sw.model.SwDeliveryModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Service("SwDeliveryManager")
public class SwDeliveryManagerImpl extends AbstractManagerImpl<String, SwDeliveryModel>
implements SwDeliveryManager{

	@Resource
	private SwDeliveryDao dao;
	@Override
	protected Dao<String, SwDeliveryModel> getDao() {
		
		return dao;
	}
	
	/**
	 * 
	 * <p>Title: queryJisoDeliveryPage</p>  
	 * <p>Description: 发货数据管理界面，分页查询功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:08:46
	 */
	@Override
	public PageList<SwDeliveryModel> queryJisoDeliveryPage(SwDeliveryModel model, DefaultPage p) {
		
		return dao.queryJisoDeliveryPage(model,p);
	}

	/**
	 * 
	 * <p>Title: queryJisoDeliveryDetailPage</p>  
	 * <p>Description: 发货数据管理界面，明细查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月18日 上午10:13:10
	 */
	@Override
	public PageList<SwDeliveryModel> queryJisoDeliveryDetailPage(SwDeliveryModel model, DefaultPage p) {
		
		return dao.queryJisoDeliveryDetailPage(model,p);
	}

}

package com.hanthink.sw.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwRecDao;
import com.hanthink.sw.manager.SwRecManager;
import com.hanthink.sw.model.SwRecModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
* <p>Title: SwRecManagerImpl</p>  
* <p>Description: 收货查询ManagerImpl</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月21日 下午9:37:45
 */
@Service("SwRecManager")
public class SwRecManagerImpl extends AbstractManagerImpl<String, SwRecModel>
implements SwRecManager{
	
	@Resource
	private SwRecDao dao;

	@Override
	protected Dao<String, SwRecModel> getDao() {
		
		return dao;
	}

	/**
	 * 
	 * <p>Title: queryJisoRecPage</p>  
	 * <p>Description: 收货查询界面，分页查询</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:27:55
	 */
	@Override
	public PageList<SwRecModel> queryJisoRecPage(SwRecModel model, DefaultPage p) {
		
		return dao.queryJisoRecPage(model,p);
	}

	/**
	 * 
	 * <p>Title: querySwRecByKey</p>  
	 * <p>Description: 收货查询界面，导出功能,查询需要导出的数据</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:34:42
	 */
	@Override
	public List<SwRecModel> querySwRecByKey(SwRecModel model) {
		
		return dao.querySwRecByKey(model);
	}

	/**
	 * 
	 * <p>Title: queryJisoRecDetailPage</p>  
	 * <p>Description:收货查询界面，查询明细功能 </p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午10:08:43
	 */
	@Override
	public PageList<SwRecModel> queryJisoRecDetailPage(SwRecModel model, DefaultPage p) {
		
		return dao.queryJisoRecDetailPage(model,p);
	}

}

package com.hanthink.sw.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwRecDao;
import com.hanthink.sw.model.SwRecModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * 
* <p>Title: SwRecDaoImpl</p>  
* <p>Description: 收货查询界面DaoImpl</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月21日 下午9:36:45
 */
@Repository
public class SwRecDaoImpl extends MyBatisDaoImpl<String, SwRecModel>
implements SwRecDao{

	@Override
	public String getNamespace() {
		
		return SwRecModel.class.getName();
	}

	/**
	 * 
	 * <p>Title: queryJisoRecPage</p>  
	 * <p>Description: 收货查询界面，分页查询功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:28:47
	 */
	@Override
	public PageList<SwRecModel> queryJisoRecPage(SwRecModel model, DefaultPage p) {
		
		return (PageList<SwRecModel>) this.getList("queryJisoRecPage", model, p);
	}

	/**
	 * 
	 * <p>Title: querySwRecByKey</p>  
	 * <p>Description: 收货查询界面，导出功能，查询需要导出的数据</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:35:40
	 */
	@Override
	public List<SwRecModel> querySwRecByKey(SwRecModel model) {
		
		return (List<SwRecModel>) this.getList("queryJisoRecPage", model);
	}

	/**
	 * 
	 * <p>Title: queryJisoRecDetailPage</p>  
	 * <p>Description: 收货查询界面，获取明细功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午10:09:51
	 */
	@Override
	public PageList<SwRecModel> queryJisoRecDetailPage(SwRecModel model, DefaultPage p) {
		return (PageList<SwRecModel>) this.getList("queryJisoRecDetailPage", model, p);
	}

}

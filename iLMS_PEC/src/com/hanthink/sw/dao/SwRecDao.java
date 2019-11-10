package com.hanthink.sw.dao;

import java.util.List;

import com.hanthink.sw.model.SwRecModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * 
* <p>Title: SwRecDao</p>  
* <p>Description: 收货查询Dao</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月21日 下午9:37:08
 */
public interface SwRecDao extends Dao<String, SwRecModel>{

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
	PageList<SwRecModel> queryJisoRecPage(SwRecModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: querySwRecByKey</p>  
	 * <p>Description: 收货查询界面，导出功能，查询需要导出的数据</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:35:40
	 */
	List<SwRecModel> querySwRecByKey(SwRecModel model);

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
	PageList<SwRecModel> queryJisoRecDetailPage(SwRecModel model, DefaultPage p);

}

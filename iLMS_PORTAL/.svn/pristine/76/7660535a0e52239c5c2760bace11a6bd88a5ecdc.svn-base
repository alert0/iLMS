package com.hanthink.sw.manager;

import java.util.List;

import com.hanthink.sw.model.SwRecModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
* <p>Title: SwRecManager</p>  
* <p>Description: 收货查询Manager</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月21日 下午9:38:34
 */
public interface SwRecManager extends Manager<String, SwRecModel>{

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
	PageList<SwRecModel> queryJisoRecPage(SwRecModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: querySwRecByKey</p>  
	 * <p>Description: 收货查询界面，导出功能,查询需要导出的数据</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月21日 下午9:34:42
	 */
	List<SwRecModel> querySwRecByKey(SwRecModel model);

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
	PageList<SwRecModel> queryJisoRecDetailPage(SwRecModel model, DefaultPage p);

}

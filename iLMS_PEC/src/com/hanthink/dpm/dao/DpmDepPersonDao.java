package com.hanthink.dpm.dao;

import com.hanthink.dpm.model.DpmDepPersonModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月14日 上午11:12:34
 * </pre>
 * @author luoxq
 */
public interface DpmDepPersonDao extends Dao<String, DpmDepPersonModel>{

	/**
	 * 
	 * @Title: 分页查询列表数据 
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmDepPersonModel>     
	 * @time   2018年9月14日 下午2:36:24
	 * @throws
	 */
	public PageList<DpmDepPersonModel> queryDpmDepPersonForPage(DpmDepPersonModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 部门人员维护界面，判断人员是否已经存在
	 * @param @param account
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 下午4:24:15
	 */
	public boolean isUserIdExist(DpmDepPersonModel dpmDepPersonModel);

	/**
	 * 
	 * @Description: 部门人员维护界面，判断责任组是否已经存在
	 * @param @param dpmDepPersonModel
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月26日 上午11:37:22
	 */
	public Integer isDepExixt(DpmDepPersonModel dpmDepPersonModel);

}

package com.hanthink.dpm.manager;

import com.hanthink.dpm.model.DpmDepPersonModel;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月14日 上午11:08:29
 * </pre>
 * @author luoxq
 */
public interface DpmDepPersonManager extends Manager<String, DpmDepPersonModel> {

	/**
	 * 
	 * @Title: 分页查询列表数据
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmDepPersonModel>     
	 * @time   2018年9月14日 下午2:19:07
	 * @throws
	 */
	public PageList<DpmDepPersonModel> queryDpmDepPersonForPage(DpmDepPersonModel model, DefaultPage p);

	/**
	 * 
	 * @Title: 判断部门人员是否已经存在 
	 * @Description:  
	 * @param @param userId
	 * @param @return    
	 * @return DpmDepartmentModel     
	 * @time   2018年9月14日 下午2:19:49
	 * @throws
	 */
//	public DpmDepartmentModel getByDepUserId(String userId);

	/**
	 * 
	 * @Title: 更新时记录日志 
	 * @Description:  
	 * @param @param dpmDepPersonModel
	 * @param @param ipAddr    
	 * @return void     
	 * @time   2018年9月14日 下午2:20:41
	 * @throws
	 */
	public void updateAndLog(DpmDepPersonModel dpmDepPersonModel, String ipAddr);

	/**
	 * 
	 * @Title: 删除时记录日志
	 * @Description:  
	 * @param @param aryIds
	 * @param @param ipAddr    
	 * @return void     
	 * @time   2018年9月14日 下午2:21:10
	 * @throws
	 */
	public void removeAndLogByIds(String[] aryIds, String ipAddr);

	/**
	 * 
	 * @Description: 判断部门人员维护界面，人员是否已经存在
	 * @param @param account
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 下午4:23:06
	 */
	public boolean isUserIdExist(DpmDepPersonModel dpmDepPersonModel);

	/**
	 * 
	 * @Description: 判断部门人员维护界面，责任组是否已经存在
	 * @param @param dpmDepPersonModel
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月26日 上午11:35:36
	 */
	public boolean isDepExixt(DpmDepPersonModel dpmDepPersonModel);

}

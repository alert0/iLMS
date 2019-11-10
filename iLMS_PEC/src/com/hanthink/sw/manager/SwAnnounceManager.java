package com.hanthink.sw.manager;

import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.UserModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
* <p>Title: SwAnnounceManager.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月15日
*/

public interface SwAnnounceManager extends Manager<String, SwAnnounceModel>{

	/**
	 * 
	* @Title: queryJisoAnnouncePage 
	* @Description: 分页查询公告发布管理 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwAnnounceModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月15日 下午4:08:43
	 */
	PageList<SwAnnounceModel> queryJisoAnnouncePage(SwAnnounceModel model, DefaultPage p);

	/**
	 * 
	* @Title: removeAndLogByIds 
	* @Description: 删除公告 并记录日志
	* @param @param groupIds
	* @param @param ipAddr    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月15日 下午5:15:52
	 */
	void removeAndLogByIds(String[] noticeIds, String ipAddr);

	/**
	 * 
	 * <p>Title: insertAnnounce</p>  
	 * <p>Description: 保存新增</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午9:51:35
	 */
	void insertAnnounce(SwAnnounceModel model) ;

	/**
	 * 
	 * <p>Title: updateAndLog</p>  
	 * <p>Description: 修改公告并记录日志 </p>  
	 * @param model
	 * @param ipAddr  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午10:42:34
	 */
	void updateAndLog(SwAnnounceModel model, String ipAddr);

	/**
	 * 
	 * <p>Title: publishNotice</p>  
	 * <p>Description: 发布公告</p>  
	 * @param noticeIds  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午11:00:00
	 */
	void publishNotice(String[] noticeIds);

	/**
	 * 
	 * <p>Title: viewAnnounceJisoPage</p>  
	 * <p>Description: 公告查看界面分页查询</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午6:31:24
	 */
	PageList<SwAnnounceModel> viewAnnounceJisoPage(SwAnnounceModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: updateViewAndLog</p>  
	 * <p>Description: 修改供应商查看表并记录</p>  
	 * @param model
	 * @param ipAddr  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午10:23:06
	 */
	void updateViewAndLog(SwAnnounceModel model, String ipAddr);

	/**
	 * 
	 * <p>Title: queryJisoFeedbackPage</p>  
	 * <p>Description: 公告发布管理界面，供应商反馈查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午11:37:03
	 */
	PageList<SwAnnounceModel> queryJisoFeedbackPage(SwAnnounceModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 通过登录用户账号获取当前登录用户对象
	 * @param @param account
	 * @param @return   
	 * @return UserModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月21日 下午11:11:08
	 */
	UserModel getUserModel(String account);


}

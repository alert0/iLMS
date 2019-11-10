package com.hanthink.sw.dao;

import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.UserModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: SwAnnounceDao.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月15日
*/

public interface SwAnnounceDao extends Dao<String, SwAnnounceModel>{

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
	* @date   2018年10月15日 下午4:20:23
	 */
	PageList<SwAnnounceModel> queryJisoAnnouncePage(SwAnnounceModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: insertAnnounce</p>  
	 * <p>Description:保存新增或者修改 </p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午9:52:34
	 */
	void insertAnnounce(SwAnnounceModel model);

	/**
	 * 
	 * <p>Title: updateNotice</p>  
	 * <p>Description:修改公告并记录日志 </p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午10:45:19
	 */
	void updateNotice(SwAnnounceModel model);

	/**
	 * 
	 * <p>Title: inserNoticeView</p>  
	 * <p>Description: 点击发布，将数据写入公告供应商查看记录表中</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午11:05:48
	 */
	void inserNoticeView(SwAnnounceModel model);

	/**
	 * 
	 * <p>Title: updateNoticeView</p>  
	 * <p>Description: 修改公告时同时修改公告信息查看表中数据</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午11:50:30
	 */
	void updateNoticeSupGroup(SwAnnounceModel model);

	/**
	 * 
	 * <p>Title: publishNotice</p>  
	 * <p>Description:点击发布，修改公告状态为发布 </p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午12:21:56
	 */
	void publishNotice(SwAnnounceModel model);

	/**
	 * 
	 * <p>Title: removeView</p>  
	 * <p>Description: 删除公告</p>  
	 * @param noticeIds  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午4:06:19
	 */
	void removeView(String[] noticeIds);

	/**
	 * 
	 * <p>Title: viewAnnounceJisoPage</p>  
	 * <p>Description: 公告查看界面分页查询</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午6:32:32
	 */
	PageList<SwAnnounceModel> viewAnnounceJisoPage(SwAnnounceModel model, DefaultPage p);

	/**
	 * 
	 * <p>Title: updateNoticeView</p>  
	 * <p>Description: 公告查看反馈功能，修改MM_SW_NOTICE_VIEW表中数据</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午10:31:31
	 */
	void updateNoticeView(SwAnnounceModel model);

	/**
	 * 
	 * <p>Title: queryJisoFeedbackPage</p>  
	 * <p>Description: 公告发布管理界面，供应商反馈查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午11:38:26
	 */
	PageList<SwAnnounceModel> queryJisoFeedbackPage(SwAnnounceModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 通过当前登录用户获取用户对象
	 * @param @param account
	 * @param @return   
	 * @return UserModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月21日 下午11:12:20
	 */
	UserModel getUserModel(String account);

}

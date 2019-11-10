package com.hanthink.sw.manager;

import java.util.List;

import com.hanthink.sw.model.FileInfoModel;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.SwUserModel;
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
	 * @param string 
	 * @param feedbackStatus 
	 * @authoer luoxq
	 * @time 2018年10月16日 上午11:00:00
	 */
	void updatePublishNotice(List<SwAnnounceModel> list);
	
//	void updatePublishNotice(List<SwAnnounceModel> list, String account);

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
	 * @Description: 判断供应商标题是否已经存在
	 * @param @param model
	 * @param @return   
	 * @return List<SwAnnounceModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:05:19
	 */
	List<SwAnnounceModel> isTitleExists(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 上传的附件写入到文件信息表
	 * @param @param newFileName   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:46:19
	 */
	void insertFile(FileInfoModel model);

	/**
	 * 
	 * @Description: 新增公告，插入到公告供应商组表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:57:57
	 */
	void insertAnnounceGroup(SwAnnounceModel model);
	
	/**
	 * 
	 * @Description: 获取文件信息表自增id
	 * @param @return   
	 * @return SwAnnounceModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月7日 下午3:12:24
	 */
	FileInfoModel getFileId();

	/**
	 * 
	 * @Description: 查询当前登录供应商有几条未查看公告
	 * @param @param model
	 * @param @return   
	 * @return List<SwAnnounceModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月12日 下午1:55:57
	 */
	List<SwAnnounceModel> getListCount(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 查看公告修改查看状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月12日 下午7:09:05
	 */
	void updateDetail(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 通过账号获取当前登录用户对象
	 * @param @param account
	 * @param @return   
	 * @return UserModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月21日 下午11:54:52
	 */
	SwUserModel getUserModel(String account);

	/**
	 * 
	 * @Description: 公告查看界面，供应商反馈
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月27日 上午11:27:58
	 */
	void updateNoticeView(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 下载附件后更新下载状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月21日 下午4:29:25
	 */
	void updateDownloadStatus(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 公告置顶
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月25日 下午6:46:57
	 */
	void upIndex(SwAnnounceModel model);

	/**
	 * @Description: 根据供应商代码查询该供应商是否有新的公告   
	 * @param: @param model
	 * @param: @return    
	 * @return: PageList<SwAnnounceModel>   
	 * @author: dtp
	 * @date: 2019年1月3日
	 */
	PageList<SwAnnounceModel> queryExistsNewAnnounce(SwAnnounceModel model);

}

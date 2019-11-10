package com.hanthink.sw.dao;

import java.util.List;

import com.hanthink.sw.model.FileInfoModel;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.SwUserModel;
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
	void insertNoticeView(SwAnnounceModel model);

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
	void updatePublishNotice(SwAnnounceModel model);

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
	 * @Description: 公告查看反馈之后判断公告是否全部都已经查看，如果是修改主表反馈状态为已反馈
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月14日 下午4:19:15
	 */
	void updateFeedbackStatus(SwAnnounceModel model);

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
	 * @Description:判断公告标题是否已经存在
	 * @param @param model
	 * @param @return   
	 * @return List<SwAnnounceModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:06:43
	 */
	List<SwAnnounceModel> isTitleExists(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 上传的文件写入到文件信息表
	 * @param @param newFileName   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:48:46
	 */
	void insertFile(FileInfoModel model);

	/**
	 * 
	 * @Description: 新增公告，写入公告供应商分组表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:59:23
	 */
	void insertAnnounceGroup(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 获取自增公告id
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午8:56:47
	 */
	SwAnnounceModel getNoticId();

	/**
	 * 
	 * @Description: 获取文件信息表自增id
	 * @param @return   
	 * @return SwAnnounceModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月7日 下午3:13:33
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
	 * @date 2018年11月12日 下午1:56:52
	 */
	List<SwAnnounceModel> getListCount(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 查看公告信息修改公告查看状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月12日 下午7:10:14
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
	 * @date 2018年11月21日 下午11:55:50
	 */
	SwUserModel getUserModel(String account);

	/**
	 * 
	 * @Description: 下载附件后更新下载状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月21日 下午4:30:26
	 */
	void updateDownloadStatus(SwAnnounceModel model);

	/**
	 * 
	 * @Description: 公告置顶
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月25日 下午7:00:25
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

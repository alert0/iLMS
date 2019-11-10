package com.hanthink.sw.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwAnnounceDao;
import com.hanthink.sw.model.FileInfoModel;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwUserModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: SwAnnounceDaoImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月15日
*/
@Repository
public class SwAnnounceDaoImpl extends MyBatisDaoImpl<String, SwAnnounceModel>
implements SwAnnounceDao{

	@Override
	public String getNamespace() {
		return SwAnnounceModel.class.getName();
	}

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
	@Override
	public PageList<SwAnnounceModel> queryJisoAnnouncePage(SwAnnounceModel model, DefaultPage p) {
		return (PageList<SwAnnounceModel>) this.getList("queryJisoAnnouncePage", model, p);
	}

	/**
	 * 
	 * <p>Title: insertAnnounce</p>  
	 * <p>Description:保存新增或者修改 </p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午9:52:34
	 */
	@Override
	public void insertAnnounce(SwAnnounceModel model) {
		this.insertByKey("insertAnnounce", model);
		
		
	}

	/**
	 * 
	 * <p>Title: updateNotice</p>  
	 * <p>Description:修改公告并记录日志 </p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午10:45:19
	 */
	@Override
	public void updateNotice(SwAnnounceModel model) {
 		this.updateByKey("updateNotice", model);
	}

	/**
	 * 
	 * <p>Title: inserNoticeView</p>  
	 * <p>Description: 点击发布，将数据写入公告供应商查看记录表中</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午11:05:48
	 */
	@Override
	public void insertNoticeView(SwAnnounceModel model) {
		this.insertByKey("insertNoticeView", model);
	}

	@Override
	public void updateNoticeSupGroup(SwAnnounceModel model) {
		this.deleteByKey("deleteNoticeSupGroup", model);
		String[] groupIds = model.getGroupIds();
		for (String groupId : groupIds) {
			model.setGroupId(groupId);
			
			this.insertByKey("insertNoticeSupGroup", model);
		}
		
	}

	/**
	 * 
	 * <p>Title: publishNotice</p>  
	 * <p>Description:点击发布，修改公告状态为发布 </p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午12:21:56
	 */
	@Override
	public void updatePublishNotice(SwAnnounceModel model) {
		this.updateByKey("publishNotice", model);
		
	}

	@Override
	public void removeView(String[] noticeIds) {
		for (String noticeId : noticeIds) {
			this.deleteByKey("removeView", noticeId);	
		}
         	
	}

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
	@Override
	public PageList<SwAnnounceModel> viewAnnounceJisoPage(SwAnnounceModel model, DefaultPage p) {
		return (PageList<SwAnnounceModel>) this.getList("viewAnnounceJisoPage", model, p);
	}

	/**
	 * 
	 * <p>Title: updateNoticeView</p>  
	 * <p>Description: 公告查看反馈功能，修改MM_SW_NOTICE_VIEW表中数据</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午10:31:31
	 */
	@Override
	public void updateNoticeView(SwAnnounceModel model) {
		this.updateByKey("updateNoticeView", model);
	}
	
	/**
	 * 
	 * @Description: 公告查看反馈之后判断公告是否全部都已经查看，如果是修改主表反馈状态为已反馈
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月14日 下午4:19:15
	 */
	@Override
	public void updateFeedbackStatus(SwAnnounceModel model) {
		this.updateByKey("updateFeedbackStatus", model);
	}

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
	@Override
	public PageList<SwAnnounceModel> queryJisoFeedbackPage(SwAnnounceModel model, DefaultPage p) {
		return (PageList<SwAnnounceModel>) this.getList("queryJisoFeedbackPage", model, p);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<SwAnnounceModel> isTitleExists(SwAnnounceModel model) {
		return (List<SwAnnounceModel>) this.getList("isTitleExists", model);
	}

	@Override
	public void insertFile(FileInfoModel model) {
		this.insertByKey("insertFile", model);
		
	}

	/**
	 * 
	 * @Description: 新增公告，写入公告供应商分组表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:59:23
	 */
	@Override
	public void insertAnnounceGroup(SwAnnounceModel model) {
		this.insertByKey("insertAnnGroup", model);
		
	}

	/**
	 * 
	 * @Description: 获取自增公告id
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午8:56:47
	 */
	@Override
	public SwAnnounceModel getNoticId() {
		Map<String, Object> map = new HashMap<String, Object>();
		return (SwAnnounceModel) this.getOne("getNoticId", map);
	}
	
	
	@Override
	public FileInfoModel getFileId() {
		Map<String, Object> map = new HashMap<String, Object>();
		return (FileInfoModel) this.getOne("getFileId", map);
	}

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
	@Override
	public List<SwAnnounceModel> getListCount(SwAnnounceModel model) {
		return this.getByKey("getListCount", model);
	}

	/**
	 * 
	 * @Description: 查看公告信息修改公告查看状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月12日 下午7:10:14
	 */
	@Override
	public void updateDetail(SwAnnounceModel model) {
		this.updateByKey("updateDetail", model);
	}

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
	@Override
	public SwUserModel getUserModel(String account) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getUserModel", account);
	}

	/**
	 * 
	 * @Description: 下载附件后更新下载状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月21日 下午4:30:26
	 */
	@Override
	public void updateDownloadStatus(SwAnnounceModel model) {
		this.updateByKey("updateDownloadStatus", model);
	}

	/**
	 * 
	 * @Description: 公告置顶
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月25日 下午7:00:25
	 */
	@Override
	public void upIndex(SwAnnounceModel model) {
		this.updateByKey("upIndex", model);
	}

	/**
	 * @Description: 根据供应商代码查询该供应商是否有新的公告   
	 * @param: @param model
	 * @param: @return    
	 * @return: PageList<SwAnnounceModel>  
	 * @author: dtp
	 * @date: 2019年1月3日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwAnnounceModel> queryExistsNewAnnounce(SwAnnounceModel model) {
		return (PageList<SwAnnounceModel>) this.getList("queryExistsNewAnnounce", model, new DefaultPage(new RowBounds(1, 100)));
	}

}

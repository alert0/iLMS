package com.hanthink.sw.dao.impl;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwAnnounceDao;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.UserModel;
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
		this.insertByKey("insertAnnGroup", model);
		
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
	public void inserNoticeView(SwAnnounceModel model) {
		this.insertByKey("inserNoticeView", model);
	}

	@Override
	public void updateNoticeSupGroup(SwAnnounceModel model) {
		String[] groupIds = model.getGroupIds().split(";");
		for (String groupId : groupIds) {
			model.setGroupId(groupId);
			this.deleteByKey("deleteNoticeSupGroup", groupId);
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
	public void publishNotice(SwAnnounceModel model) {
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

	@Override
	public PageList<SwAnnounceModel> queryJisoFeedbackPage(SwAnnounceModel model, DefaultPage p) {
		return (PageList<SwAnnounceModel>) this.getList("queryJisoFeedbackPage", model, p);
	}

	@Override
	public UserModel getUserModel(String account) {
		return null;
	}

}

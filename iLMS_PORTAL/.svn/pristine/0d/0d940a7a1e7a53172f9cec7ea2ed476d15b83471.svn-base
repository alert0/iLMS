package com.hanthink.sw.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sw.dao.SwAnnounceDao;
import com.hanthink.sw.manager.SwAnnounceManager;
import com.hanthink.sw.model.FileInfoModel;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.SwUserModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.constants.CategoryConstants;
import com.hotent.sys.util.ContextUtil;

/**
* <p>Title: SwAnnounceManagerImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月15日
*/
@Service("SwAnnounceManager")
public class SwAnnounceManagerImpl extends AbstractManagerImpl<String, SwAnnounceModel>
implements SwAnnounceManager{
	
	@Resource
	private SwAnnounceDao dao;

	@Override
	protected Dao<String, SwAnnounceModel> getDao() {
		
		return dao;
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
	* @date   2018年10月15日 下午4:08:43
	 */
	@Override
	public PageList<SwAnnounceModel> queryJisoAnnouncePage(SwAnnounceModel model, DefaultPage p) {
		return dao.queryJisoAnnouncePage(model,p);
	}

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
	@Override
	public void removeAndLogByIds(String[] noticeIds, String ipAddr) {
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_SW_NOTICE");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("NOTICE_ID");
		pkColumnVO.setColumnValArr(noticeIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
						
		super.removeByIds(noticeIds);
		dao.removeView(noticeIds);
	}

	/**
	 * 
	 * <p>Title: insertAnnounce</p>  
	 * <p>Description: 保存新增或修改</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午9:51:35
	 */
	@Override
	public void insertAnnounce(SwAnnounceModel model) {
		String []groupIdArr = model.getGroupIds();
		String noticeId = dao.getNoticId().getNoticeId();
		model.setNoticeId(noticeId);
		dao.insertAnnounce(model);
		for (String groupId : groupIdArr) {
			model.setGroupId(groupId);
			dao.insertAnnounceGroup(model);
		}
	}

	/**
	 * 
	 * <p>Title: updateAndLog</p>  
	 * <p>Description: 修改公告并记录日志 </p>  
	 * @param model
	 * @param ipAddr  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午10:42:34
	 */
	@Override
	public void updateAndLog(SwAnnounceModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_NOTICE");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("NOTICE_ID");
		pkColumnVO.setColumnVal(model.getNoticeId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dao.updateNotice(model);//修改MM_SW_NOTICE表中数据
//		String []groupIdArr = model.getGroupIds();
//		for (String groupId : groupIdArr) {
//			model.setGroupId(groupId);
			dao.updateNoticeSupGroup(model);//修改MM_SW_NOTICE_SUPGROUP表中数据
//		}
//		dao.updateNoticeSupGroup(model);
	}

	/**
	 * 
	 * <p>Title: publishNotice</p>  
	 * <p>Description: 发布公告</p>  
	 * @param noticeIds  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午11:00:00
	 */
	@Override
	public void updatePublishNotice(List<SwAnnounceModel> list) {
		IUser user = ContextUtil.getCurrentUser();
		for (SwAnnounceModel model : list) {
			model.setPublishUser(user.getAccount());
			model.setNoticeStatus(Constant.SW_NOTICE_STATUS);//修改公告状态为发布
			if (Constant.SW_IS_FEEDBACK_STATUS.equals(model.getIsFeedback())) {
				model.setViewStatus(Constant.SW_NOTICE_VIEW_STATUS_NO);
			}else {
				//若公告需要反馈,初始化查看状态为未反馈
				model.setViewStatus(Constant.SW_NOTICE_VIEW_STATUS_FEEDBACK_NO);
			}
			
			dao.updatePublishNotice(model);
			dao.insertNoticeView(model);
		}
	}
	
	/**
	 * 
	 * <p>Title: publishNotice</p>  
	 * <p>Description: 发布公告</p>  
	 * @param noticeIds  
	 * @authoer luoxq
	 * @time 2018年10月16日 上午11:00:00
	 */
//	@Override
//	public void updatePublishNotice(List<SwAnnounceModel> list, String account) {
//		for (SwAnnounceModel model : list) {
//			model.setCreationUser(account);
//			model.setNoticeStatus(Constant.SW_NOTICE_STATUS);
//			dao.updatePublishNotice(model);
//			dao.insertNoticeView(model);
//		}
//	}

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
	@Override
	public PageList<SwAnnounceModel> viewAnnounceJisoPage(SwAnnounceModel model, DefaultPage p) {
		
		return dao.viewAnnounceJisoPage(model,p);
	}

	/**
	 * 
	 * <p>Title: updateViewAndLog</p>  
	 * <p>Description: 修改供应商查看表并记录</p>  
	 * @param model
	 * @param ipAddr  
	 * @authoer luoxq
	 * @time 2018年10月17日 上午10:23:06
	 */
	@Override
	public void updateViewAndLog(SwAnnounceModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_NOTICE_VIEW");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("NOTICE_ID");
		pkColumnVO.setColumnVal(model.getNoticeId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dao.updateNoticeView(model);//修改MM_SW_NOTICE_VIEW表中数据
		dao.updateFeedbackStatus(model);//判断公告是否全部被查看，如是则修改主表反馈状态为已反馈
	}

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
	@Override
	public PageList<SwAnnounceModel> queryJisoFeedbackPage(SwAnnounceModel model, DefaultPage p) {
		
		return dao.queryJisoFeedbackPage(model,p);
	}

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
	@Override
	public List<SwAnnounceModel> isTitleExists(SwAnnounceModel model) {
		
		return dao.isTitleExists(model);
	}

	/**
	 * 
	 * @Description: 上传的附件写入到文件信息表
	 * @param @param newFileName   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:46:19
	 */
	@Override
	public void insertFile(FileInfoModel model) {
		dao.insertFile(model);
	}

	/**
	 * 
	 * @Description: 新增公告，插入到公告供应商组表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午6:57:57
	 */
	@Override
	public void insertAnnounceGroup(SwAnnounceModel model) {
		dao.insertAnnounceGroup(model);
	}

	/**
	 * 
	 * @Description: 获取文件信息表自增id
	 * @param @return   
	 * @return SwAnnounceModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月7日 下午3:12:24
	 */
	@Override
	public FileInfoModel getFileId() {
		
		return dao.getFileId();
	}

	@Override
	public List<SwAnnounceModel> getListCount(SwAnnounceModel model) {
		return dao.getListCount(model);
	}

	/**
	 * 
	 * @Description: 查看公告修改查看状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月12日 下午7:09:05
	 */
	@Override
	public void updateDetail(SwAnnounceModel model) {
		dao.updateDetail(model);
	}

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
	@Override
	public SwUserModel getUserModel(String account) {
		
		return dao.getUserModel(account);
	}

	/**
	 * 
	 * @Description: 公告查看界面，供应商反馈
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月27日 上午11:27:58
	 */
	@Override
	public void updateNoticeView(SwAnnounceModel model) {
		dao.updateNoticeView(model);//修改MM_SW_NOTICE_VIEW表中数据
		dao.updateFeedbackStatus(model);//判断公告是否全部被查看，如是则修改主表反馈状态为已反馈
	}

	/**
	 * 
	 * @Description: 下载附件后更新下载状态
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月21日 下午4:29:25
	 */
	@Override
	public void updateDownloadStatus(SwAnnounceModel model) {
		dao.updateDownloadStatus(model);
	}

	/**
	 * 
	 * @Description: 公告置顶
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月25日 下午6:46:57
	 */
	@Override
	public void upIndex(SwAnnounceModel model) {
		dao.upIndex(model);
	}

	/**
	 * @Description: 根据供应商代码查询该供应商是否有新的公告   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwAnnounceModel>   
	 * @author: dtp
	 * @date: 2019年1月3日
	 */
	@Override
	public PageList<SwAnnounceModel> queryExistsNewAnnounce(SwAnnounceModel model) {
		return dao.queryExistsNewAnnounce(model);
	}

}

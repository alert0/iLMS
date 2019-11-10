package com.hanthink.sw.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sw.dao.SwAnnounceDao;
import com.hanthink.sw.manager.SwAnnounceManager;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.sw.model.UserModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
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
		dao.insertAnnounce(model);
		
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
		dao.updateNoticeSupGroup(model);//修改MM_SW_NOTICE_SUPGROUP表中数据
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
	public void publishNotice(String[] noticeIds) {
		SwAnnounceModel model = new SwAnnounceModel();
		for (String noticeId : noticeIds) {
			model = dao.get(noticeId);
			dao.inserNoticeView(model);
			model.setNoticeStatus("1");//修改公告状态为发布
			dao.publishNotice(model);
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
	 * @Description: 通过登录用户账号获取当前登录用户对象
	 * @param @param account
	 * @param @return   
	 * @return UserModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月21日 下午11:11:08
	 */
	@Override
	public UserModel getUserModel(String account) {
		
		return dao.getUserModel(account);
	}
	
}

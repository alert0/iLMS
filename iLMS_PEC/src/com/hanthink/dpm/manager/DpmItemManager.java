package com.hanthink.dpm.manager;


import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface DpmItemManager extends Manager<String, DpmItemModel>{
	
	/**
	 * 更新数据并记录日志
	 * @param dpmItemModel
	 * @param opeIp
	 * @author luoxq	
	 * @DATE	2018年9月10日  
	 */
	void updateAndLog(DpmItemModel dpmItemModel, String opeIp);
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author luoxq	
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr);

	/**
	 * 根据不良品项目代码查询数据
	 * @param dpmItemModel
	 * @author luoxq	
	 * @DATE	2018年9月11日 上午11:00:04
	 */
	public DpmItemModel getByCode(DpmItemModel dpmItemModel);

	/**
	 * 
	 * @Title: 分页查询 
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmItemModel>     
	 * @time   2018年9月13日 下午12:28:54
	 * @throws
	 */
	public PageList<DpmItemModel> queryDpmItemForPage(DpmItemModel model, DefaultPage p);
}

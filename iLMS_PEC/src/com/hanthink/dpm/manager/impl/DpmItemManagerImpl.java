package com.hanthink.dpm.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.dpm.dao.DpmItemDao;
import com.hanthink.dpm.manager.DpmItemManager;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("DpmItemManager")
public class DpmItemManagerImpl extends AbstractManagerImpl<String, DpmItemModel> implements DpmItemManager {

	@Resource
	private DpmItemDao dpmItemDao;

	@Override
	protected Dao<String, DpmItemModel> getDao() {
		return dpmItemDao;
	}

	/**
	 * 更新数据并记录日志
	 * 
	 * @param dpmItemModel
	 * @param ipAddr
	 * @author luoxq
	 * @DATE 2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(DpmItemModel dpmItemModel, String ipAddr) {

		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_DPM_ITEM");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(dpmItemModel.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		dpmItemDao.update(dpmItemModel);

	}
	
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author luoxq	
	 * @DATE	2018年9月10日 
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr){
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_DPM_ITEM");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(aryIds);
	}

	/**
	 * 根据不良品项目代码查询数据
	 * @param dpmItemModel
	 * @author luoxq	
	 * @DATE	2018年9月11日 上午11:00:04
	 */
	@Override
	public DpmItemModel getByCode(DpmItemModel dpmItemModel) {
		dpmItemModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return dpmItemDao.getByCode(dpmItemModel);
	}

	@Override
	public PageList<DpmItemModel> queryDpmItemForPage(DpmItemModel model, DefaultPage p) {
		
		return dpmItemDao.queryDpmItemForPage(model,p);
	}

}

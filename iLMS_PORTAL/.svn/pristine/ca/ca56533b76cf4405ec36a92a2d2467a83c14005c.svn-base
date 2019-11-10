package com.hanthink.dpm.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.dpm.dao.DpmDepPersonDao;
import com.hanthink.dpm.manager.DpmDepPersonManager;
import com.hanthink.dpm.model.DpmDepPersonModel;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月14日 上午11:08:52
 * </pre>
 * @author luoxq
 */
@Service("DpmDepPersonManager")
public class DpmDepPersonManagerImpl extends AbstractManagerImpl<String, DpmDepPersonModel> implements DpmDepPersonManager{

	@Resource
	private DpmDepPersonDao dpmDepPersonDao;

	@Override
	protected Dao<String, DpmDepPersonModel> getDao() {
		
		return dpmDepPersonDao;
	}

	@Override
	public PageList<DpmDepPersonModel> queryDpmDepPersonForPage(DpmDepPersonModel model, DefaultPage p) {
		
		return dpmDepPersonDao.queryDpmDepPersonForPage(model,p);
	}

//	@Override
//	public DpmDepartmentModel getByDepUserId(String userId) {
//		
//		return dpmDepPersonDao.getByDepUserId(userId);
//	}

	/**
	 * 更新数据并记录日志
	 * 
	 * @param dpmItemModel
	 * @param ipAddr
	 * @author luoxq
	 * @DATE 2018年9月14日 上午10:54:52
	 */
	@Override
	public void updateAndLog(DpmDepPersonModel dpmDepPersonModel, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_DPM_DEP_PERSON");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(dpmDepPersonModel.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		dpmDepPersonDao.update(dpmDepPersonModel);
	
	}

	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) {
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_DPM_DEP_PERSON");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
				
		super.removeByIds(aryIds);
	}
	
}

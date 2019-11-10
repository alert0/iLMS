package com.hanthink.dpm.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.dpm.dao.DpmAreaDao;
import com.hanthink.dpm.manager.DpmAreaManager;
import com.hanthink.dpm.model.DpmAreaModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月11日 下午4:03:46
 * </pre>
 * @author luoxq
 */
@Service("DpmAreaManager")
public class DpmAreaManagerImpl extends AbstractManagerImpl<String, DpmAreaModel> implements DpmAreaManager{

	@Resource
	private DpmAreaDao dpmAreaDao;

	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author luoxq	
	 * @DATE	2018年9月11日 上午10:54:52
	 */
	@Override
	public void updateAndLog(DpmAreaModel dpmAreaModel, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_DPM_AREA");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(dpmAreaModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dpmAreaDao.update(dpmAreaModel);
	}

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author luoxq	
	 * @DATE	2018年9月11日 
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr){
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_DPM_AREA");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(aryIds);
	}

	@Override
	protected Dao<String, DpmAreaModel> getDao() {
		
		return dpmAreaDao;
	}

	@Override
	public DpmAreaModel getByCode(String areaCode) {
		
		return dpmAreaDao.getByCode(areaCode);
	}


	@Override
	public List getUnloadCode() {
		
		return dpmAreaDao.getUnloadCode();
	}

	@Override
	public PageList<DpmAreaModel> queryDpmAreaForPage(DpmAreaModel model, DefaultPage p) {
		
		return dpmAreaDao.queryDpmAreaForPage(model,p);
	}

	@Override
	public DpmAreaModel getWareNameByCode(String wareCode) {
		
		return dpmAreaDao.getWareNameByCode(wareCode);
	}

	@Override
	public List<DictVO> getUnloadWareCode() {
		
		return dpmAreaDao.getUnloadWareCode();
	}

	@Override
	public List<DictVO> getUnloadWorkcenter(String factoryCode) {
		
		return dpmAreaDao.getUnloadWorkcenter(factoryCode);
	}


}

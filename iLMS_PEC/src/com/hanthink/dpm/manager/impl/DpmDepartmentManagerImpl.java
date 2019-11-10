package com.hanthink.dpm.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.dpm.dao.DpmDepartmentDao;
import com.hanthink.dpm.manager.DpmDepartmentManager;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月13日 下午12:03:14
 * </pre>
 * @author luoxq
 */
@Service("DpmDepartmentManager")
public class DpmDepartmentManagerImpl extends AbstractManagerImpl<String, DpmDepartmentModel> implements DpmDepartmentManager{

	@Resource
	private DpmDepartmentDao dpmDepartmentDao;
	
	@Override
	protected Dao<String, DpmDepartmentModel> getDao() {
		
		return dpmDepartmentDao;
	}
	
	/**
	 * 更新数据并记录日志
	 * 
	 * @param dpmDepartmentModel
	 * @param ipAddr
	 * @author luoxq
	 * @DATE 2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(DpmDepartmentModel dpmDepartmentModel, String ipAddr) {

		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_DPM_DEPARTMENT");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(dpmDepartmentModel.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		dpmDepartmentDao.update(dpmDepartmentModel);

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
		logVO.setTableName("MM_DPM_DEPARTMENT");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(aryIds);
	}

	/**
	 * 根据不良品项目代码查询数据
	 * @param itemCode
	 * @author luoxq	
	 * @DATE	2018年9月11日 上午11:00:04
	 */
	@Override
	public DpmDepartmentModel getByCode(String depCode) {
		
		return dpmDepartmentDao.getByCode(depCode);
	}

	@Override
	public PageList<DpmDepartmentModel> queryDpmDepartmentForPage(DpmDepartmentModel model, DefaultPage p) {
		
		return dpmDepartmentDao.queryDpmDepartmentForPage(model,p);
	}

	/**
	 * 
	 * @Title: 分页查询 获取部门审核人
	 * @Description:  
	 * @param @param model
	 * @param @param p
	 * @param @return    
	 * @return PageList<DpmItemModel>     
	 * @time   2018年9月13日 下午12:21:37
	 * @throws
	 */
	@Override
	public List<DpmDepartmentModel> getDepChecker(DpmDepartmentModel model) {
		
		return dpmDepartmentDao.getDepChecker(model);
	}

	@Override
	public List<DictVO> getDepUnloadCode(String factoryCode) {
		
		return dpmDepartmentDao.getDepUnloadCode(factoryCode);
	}

}

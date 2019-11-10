package com.hanthink.pkg.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pkg.dao.PkgPartDao;
import com.hanthink.pkg.manager.PkgPartManager;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hanthink.pkg.model.PkgPartModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
* <p>Title: PkgPartManagerImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月26日
*/
@Service("PkgPartManager")
public class PkgPartManagerImpl extends AbstractManagerImpl<String, PkgPartModel> implements PkgPartManager{

	@Resource
	private PkgPartDao pkgPartDao;
	@Override
	protected Dao<String, PkgPartModel> getDao() {
		
		return pkgPartDao;
	}
	
	/**
	 * 
	* @Title: queryPkgPartForPage 
	* @Description: 分页查询零件担当维护界面数据 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<PkgPartModel> 
	* @throws
	 */
	@Override
	public PageList<PkgPartModel> queryPkgPartForPage(PkgPartModel model, DefaultPage p) {
		
		return pkgPartDao.queryPkgPartForPage(model,p);
	}
	
	/**
	 * 
	* @Title: updateAndLog 
	* @Description: 修改零件担当信息并记录日志  
	* @param @param PkgProposalModel
	* @param @param ipAddr    
	* @return void 
	* @throws
	 */
	@Override
	public void updateAndLog(PkgPartModel pkgPartModel, String ipAddr) {
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PKG_PART");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(pkgPartModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		pkgPartDao.update(pkgPartModel);
	}

	/**
	 * 
	* @Title: getTelNoByUser 
	* @Description: 通过零件担当带出联系方式  
	* @param @param partRespUser
	* @param @return    
	* @return PkgProposalModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月7日 下午3:57:16
	 */
	@Override
	public PkgProposalModel getTelNoByUser(String partRespUser) {
		
		return pkgPartDao.getTelNoByUser(partRespUser);
	}

	@Override
	public void getPartFromIf() {
		pkgPartDao.getPartFromIf();
	}

}

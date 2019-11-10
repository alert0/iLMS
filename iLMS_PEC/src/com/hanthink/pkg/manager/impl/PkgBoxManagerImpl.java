package com.hanthink.pkg.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pkg.dao.PkgBoxDao;
import com.hanthink.pkg.manager.PkgBoxManager;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
* <p>Title: PkgBoxManagerImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月25日
*/

@Service("PkgBoxManager")
public class PkgBoxManagerImpl extends AbstractManagerImpl<String, PkgBoxModel> implements PkgBoxManager{

	@Resource
	private PkgBoxDao pkgBoxDao;
	@Override
	protected Dao<String, PkgBoxModel> getDao() {
		
		return pkgBoxDao;
	}
	
	/**
	 * 
	* @Title: queryPkgBoxForPage 
	* @Description: 分页查询出包装管理箱种信息
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<DpmItemModel> 
	* @throws
	 */
	@Override
	public PageList<PkgBoxModel> queryPkgBoxForPage(PkgBoxModel model, DefaultPage p) {
		
		return pkgBoxDao.queryPkgBoxForPage(model,p);
	}
	
	/***
	 * 
	* @Title: getByCode 
	* @Description: 新增箱种信息是判断箱code是否重复 
	* @param @param boxCode
	* @param @return    
	* @return PkgBoxModel 
	* @throws
	 */
	@Override
	public PkgBoxModel getByCode(String boxCode) {
		
		return pkgBoxDao.getByCode(boxCode);
	}
	
	
	/**
	 * 
	* @Title: updateAndLog 
	* @Description: 修改箱种管理信息并记录日志 
	* @param @param pkgBoxModel
	* @param @param ipAddr    
	* @return void 
	* @throws
	 */
	@Override
	public void updateAndLog(PkgBoxModel pkgBoxModel, String ipAddr) {
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PKG_BOX");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(pkgBoxModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		pkgBoxDao.update(pkgBoxModel);
		
	}

	/**
	 * 
	* @Title: removeAndLogByIds 
	* @Description: 删除箱种管理信息并记录日志 
	* @param @param aryIds
	* @param @param ipAddr    
	* @return void 
	* @throws
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) {
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_PKG_BOX");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
				
		super.removeByIds(aryIds);
	}

	/**
	 * 加载可使用的箱CODE信息
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月27日 下午12:40:53
	 */
	@Override
	public List<PkgBoxModel> loadBoxCodeData(PkgBoxModel pkgBox) {
		return pkgBoxDao.loadBoxCodeData(pkgBox);
	}

	/**
	 * 
	 * @Description: 获取需要维护的箱种下拉框
	 * @param @param map
	 * @param @return   
	 * @return List<Map<String,Object>>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月7日 下午5:39:02
	 */
	@Override
	public List<PkgBoxModel> loadBoxTypeData(Map<String, Object> map) {
		
		return pkgBoxDao.loadBoxTypeData(map);
	}

	/**
	 * 
	 * @Description: 箱种信息导出
	 * @param @param model
	 * @param @return   
	 * @return List<PkgBoxModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月9日 上午9:53:11
	 */
	@Override
	public List<PkgBoxModel> queryPkgBoxByKey(PkgBoxModel model) {
		
		return pkgBoxDao.queryPkgBoxByKey(model);
	}

}

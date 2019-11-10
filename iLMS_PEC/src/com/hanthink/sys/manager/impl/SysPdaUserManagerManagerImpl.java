package com.hanthink.sys.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sys.dao.SysPdaUserManagerDao;
import com.hanthink.sys.manager.SysPdaUserManagerManager;
import com.hanthink.sys.model.SysPdaUserManagerModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：PDA用户信息管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("SysPdaUserManagerManager")
public class SysPdaUserManagerManagerImpl extends AbstractManagerImpl<String, SysPdaUserManagerModel> implements SysPdaUserManagerManager{
	@Resource
	SysPdaUserManagerDao sysPdaUserManagerDao;
	@Override
	protected Dao<String, SysPdaUserManagerModel> getDao() {
		return sysPdaUserManagerDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public PageList<SysPdaUserManagerModel> querySysPdaUserManagerForPage(SysPdaUserManagerModel model, DefaultPage p) {
		return sysPdaUserManagerDao.querySysPdaUserManagerForPage(model, p);
	}

	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(SysPdaUserManagerModel sysPdaUserManagerModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PDA_USER");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("USER_NAME");
		pkColumnVO.setColumnVal(sysPdaUserManagerModel.getUserId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		sysPdaUserManagerDao.update(sysPdaUserManagerModel);
		/**
		 * 由于PDA用户没有数据权限，所以需要在PEC用户表中也更新相应的用户
		 */
		sysPdaUserManagerDao.updatePEC(sysPdaUserManagerModel);
	}
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月4日 上午11:01:08
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception{
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("PDA用户信息删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_PDA_USER");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("USER_NAME");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		/**
		 * 先删除用户
		 */
		sysPdaUserManagerDao.deleteByIds(aryIds);
		/**
		 * 再删除用户菜单关系
		 */
		sysPdaUserManagerDao.deletePdaMenuByIds(aryIds);
		/**
		 * 由于PDA用户没有数据权限，所以需要在PEC用户表中也删除相应的用户
		 */
		sysPdaUserManagerDao.deleteByIdsPEC(aryIds);
	}

	/**
	 * 避免主键冲突
	 */
	@Override
	public Integer selectPrimaryKey(SysPdaUserManagerModel sysPdaUserManagerModel) {
		return sysPdaUserManagerDao.selectPrimaryKey(sysPdaUserManagerModel);
	}

	/**
	 * 新增数据
	 */
	@Override
	public void insert(SysPdaUserManagerModel sysPdaUserManagerModel) {
		sysPdaUserManagerDao.insert(sysPdaUserManagerModel);
		
	}

	/**
	 * 更新打印状态
	 */
	@Override
	public void updatePrintStatus(String ipAddr, SysPdaUserManagerModel model) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("修改打印状态");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PDA_USER");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("USER_NAME");
		pkColumnVO.setColumnVal(model.getUserName());
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		sysPdaUserManagerDao.updatePrintStatus(model);
	}
     	
	 /**
	  *  打印用查询
	  */
	@Override
	public SysPdaUserManagerModel querySysPdaUserManagerLabel(SysPdaUserManagerModel sysPdaUserManagerModel) {
		return sysPdaUserManagerDao.querySysPdaUserManagerLabel(sysPdaUserManagerModel);
	}

	/**
	 * 查询系统参数表是否有图片信息
	 */
	@Override
	public Integer queryPicture(SysPdaUserManagerModel model) {
		return sysPdaUserManagerDao.queryPicture(model);
	}

	/**
	 * 新增图片
	 */
	@Override
	public void insertImageId(SysPdaUserManagerModel model) {
		sysPdaUserManagerDao.insertImageId(model);
	}

	/**
	 * 更新图片
	 */
	@Override
	public void updateImageIdAndLog(SysPdaUserManagerModel model, String ipAddr) {
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_SYS_PARAM");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PARAM_CODE");
		pkColumnVO.setColumnVal(model.getImageId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		sysPdaUserManagerDao.updateImageId(model);
	}

	/**
	 * 查询之前的图片ID
	 */
	@Override
	public List<SysPdaUserManagerModel> queryOlderPicture(SysPdaUserManagerModel model) {
		return sysPdaUserManagerDao.queryOlderPicture(model);
	}

	/**
	 * 新增PEC表用户
	 */
	@Override
	public void insertPEC(SysPdaUserManagerModel sysPdaUserManagerModel) {
		sysPdaUserManagerDao.insert(sysPdaUserManagerModel);
		/**
		 * 由于PDA用户没有数据权限，所以需要在PEC用户表中也建立相应的用户
		 */
		sysPdaUserManagerDao.insertPEC(sysPdaUserManagerModel);
	}

	/**
	 * 校验PEC用户冲突
	 */
	@Override
	public Integer selectPrimaryKeyPEC(SysPdaUserManagerModel sysPdaUserManagerModel) {
		return sysPdaUserManagerDao.selectPrimaryKeyPEC(sysPdaUserManagerModel);
	}
	
}

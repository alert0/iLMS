package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.dao.PubFactoryDao;
import com.hanthink.pub.manager.PubFactoryManager;
import com.hanthink.pub.model.PubFactoryModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: PubFactoryManagerImpl
 * @Description: 工厂ManagerImpl
 * @author dtp
 * @date 2019年5月29日
 */
@Service("PubFactoryManager")
public class PubFactoryManagerImpl extends AbstractManagerImpl<String, PubFactoryModel> implements PubFactoryManager{

	@Resource
	private PubFactoryDao pubFactoryDao;
	
	@Override
	protected Dao<String, PubFactoryModel> getDao() {
		return pubFactoryDao;
	}

	/**
	 * @Description: 查询工厂
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@Override
	public PageList<PubFactoryModel> queryFactoryPage(PubFactoryModel model, DefaultPage page) {
		return pubFactoryDao.queryFactoryPage(model, page);
	}

	/**
	 * @Description: 新增工厂   
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@Override
	public void insertFactory(PubFactoryModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("工厂维护");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_INSERT);
		logVO.setTableName("MM_PUB_FACTORY");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubFactoryDao.insertFactory(model);
	}

	/**
	 * @Description: 更新工厂 
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@Override
	public void updateFactory(PubFactoryModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("工厂维护");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_FACTORY");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubFactoryDao.updateFactory(model);
	}

	/**
	 * @Description:  删除工厂   
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月29日
	 */
	@Override
	public void deleteFactory(PubFactoryModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("工厂维护");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_PUB_FACTORY");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubFactoryDao.deleteFactory(model);
	}

	/**
	 * @Description: 分页查询工厂对应账号 
	 * @param: @param model
	 * @param: @param p
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@Override
	public List<PubFactoryModel> queryFactoryUserList(PubFactoryModel model, Page p) {
		return pubFactoryDao.queryFactoryUserList(model, p);
	}

	/**
	 * @Description: 查询非当前工厂的系统用户账号   
	 * @param: @param model
	 * @param: @param p
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@Override
	public List<PubFactoryModel> queryNotThisFactorySystemUserPage(PubFactoryModel model, Page p) {
		return pubFactoryDao.queryNotThisFactorySystemUserPage(model, p);
	}

	/**
	 * @Description: 工厂新增账号 
	 * @param: @param list    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@Override
	public void addFactoryUser(List<PubFactoryModel> list) {
		pubFactoryDao.addFactoryUser(list);
	}

	/**
	 * @Description: 删除工厂下账号 
	 * @param: @param modelList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月30日
	 */
	@Override
	public void deleteFactoryUser(List<PubFactoryModel> list) {
		pubFactoryDao.deleteFactoryUser(list);
	}

	/**
	 * @Description: 新增前判断工厂代码是否唯一  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubFactoryModel>   
	 * @author: dtp
	 * @date: 2019年5月31日
	 */
	@Override
	public List<PubFactoryModel> queryIsExistsFactoryCode(PubFactoryModel model) {
		return pubFactoryDao.queryIsExistsFactoryCode(model);
	}

}

package com.hanthink.mon.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonVehPassDao;
import com.hanthink.mon.manager.MonVehPassManager;
import com.hanthink.mon.model.MonVehPassModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: VehPassManagerImpl
 * @Description: 实时工程查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Service("vehPassManager")
public class MonVehPassManagerImpl extends AbstractManagerImpl<String, MonVehPassModel>
		implements MonVehPassManager {

	@Resource
	private MonVehPassDao vehPassDao;

	@Override
	protected Dao<String, MonVehPassModel> getDao() {
		return vehPassDao;
	}

	/**
	 * @Description: 实时工程查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<VehPassModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	@Override
	public PageList<MonVehPassModel> queryVehPassPage(MonVehPassModel model, DefaultPage page) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return vehPassDao.queryVehPassPage(model, page);
	}


}

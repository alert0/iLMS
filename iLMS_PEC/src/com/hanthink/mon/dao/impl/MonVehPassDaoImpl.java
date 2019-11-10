package com.hanthink.mon.dao.impl;

import org.springframework.stereotype.Repository;
import com.hanthink.mon.dao.MonVehPassDao;
import com.hanthink.mon.model.MonVehPassModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: VehPassDaoImpl
 * @Description: 实时工程查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Repository
public class MonVehPassDaoImpl extends MyBatisDaoImpl<String, MonVehPassModel> implements MonVehPassDao {

	@Override
	public String getNamespace() {
		return MonVehPassModel.class.getName();
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
	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonVehPassModel> queryVehPassPage(MonVehPassModel model, DefaultPage page) {
		return (PageList<MonVehPassModel>) this.getList("queryVehPassPage", model, page);
	}



}

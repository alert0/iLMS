package com.hanthink.mon.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonColScreenDao;
import com.hanthink.mon.model.MonColScreenModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class MonColScreenDaoImpl extends MyBatisDaoImpl<String, MonColScreenModel>
					implements MonColScreenDao{

	@Override
	public String getNamespace() {
		return MonColScreenModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonColScreenModel> queryForWEStartupRate(Map<String, String> paramMap) throws Exception {
		return (List<MonColScreenModel>) this.getList("queryForWEStartupRate", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonColScreenModel> queryForAFStartupRate(Map<String, String> paramMap) throws Exception {
		return (List<MonColScreenModel>) this.getList("queryForAFStartupRate", paramMap);
	}

}

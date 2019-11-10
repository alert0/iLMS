package com.hanthink.mon.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.MonColScreenModel;
import com.hotent.base.db.api.Dao;

public interface MonColScreenDao extends Dao<String, MonColScreenModel>{

	List<MonColScreenModel> queryForWEStartupRate(Map<String, String> paramMap)throws Exception;

	List<MonColScreenModel> queryForAFStartupRate(Map<String, String> paramMap)throws Exception;

}

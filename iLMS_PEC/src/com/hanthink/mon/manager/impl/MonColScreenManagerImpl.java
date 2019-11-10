package com.hanthink.mon.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonColScreenDao;
import com.hanthink.mon.manager.MonColScreenManager;
import com.hanthink.mon.model.MonColScreenModel;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.util.ContextUtil;

@Service("monColScreenService")
public class MonColScreenManagerImpl extends AbstractManagerImpl<String, MonColScreenModel>
								implements MonColScreenManager{
	private final String WE_WORK_CENTER = "W1";
	private final String AF_WORK_CENTER = "A1";
	
	@Resource
	private MonColScreenDao colScreenDao;
	
	@Override
	protected Dao<String, MonColScreenModel> getDao() {
		return colScreenDao;
	}

	@Override
	public Map<String, List<MonColScreenModel>> getShowMessageMap() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		Map<String, List<MonColScreenModel>> resMap = new HashMap<String, List<MonColScreenModel>>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("factoryCode", factoryCode);
		paramMap.put("weWorkCenter", WE_WORK_CENTER);
		paramMap.put("afWorkCenter", AF_WORK_CENTER);
		List<MonColScreenModel> startupWERate = colScreenDao.queryForWEStartupRate(paramMap);
		List<MonColScreenModel> startupAFRate = colScreenDao.queryForAFStartupRate(paramMap);
		resMap.put("WERATE", startupWERate);
		resMap.put("AFRATE", startupAFRate);
		return resMap;
	}

}

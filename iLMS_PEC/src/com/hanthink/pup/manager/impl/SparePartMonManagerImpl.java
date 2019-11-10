package com.hanthink.pup.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pup.dao.SparePartMonDao;
import com.hanthink.pup.manager.SparePartMonManager;
import com.hanthink.pup.model.SparePartMonModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("sparePart")
public class SparePartMonManagerImpl extends AbstractManagerImpl<String,SparePartMonModel>
					implements SparePartMonManager{
	@Resource
	private SparePartMonDao sparePartDao;
	
	@Override
	protected Dao<String, SparePartMonModel> getDao() {
		return sparePartDao;
	}

	@Override
	public PageList<SparePartMonModel> querySparePartForPage(SparePartMonModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<SparePartMonModel> list = sparePartDao.querySparePartForPage(model,page);
		
		return new PageList<SparePartMonModel>(list);
	}

	@Override
	public List<SparePartMonModel> queryPartCheck(Map<String, String> paramMap, Page page) throws Exception {
		String ip = paramMap.get("ip");
		if (StringUtil.isNotEmpty(ip)) {
			String[] ips = ip.split("\\.");
			if ("102".equals(ips[1])) {
				paramMap.put("workCenter", "W1");
			}else if ("104".equals(ips[1])) {
				paramMap.put("workCenter", "A1");
			}else {
				paramMap.put("workCenter", "");
			}
		}else {
			paramMap.put("workCenter", "");
		}
		List<SparePartMonModel> list = sparePartDao.queryPartCheck(paramMap, page);
		int limitCount = 8;
		if (limitCount == list.size()) {
			Map<String, Object> delayStationMap = new HashMap<String, Object>();
			for (SparePartMonModel sparePartMonModel : list) {
				if (sparePartMonModel.getUnloadStatus().equals("1") || sparePartMonModel.getCheckStatus().equals("1")) {
					delayStationMap.put("delayStation", sparePartMonModel.getStation());
					sparePartMonModel.setDelayStationMap(delayStationMap);
				}
				sparePartMonModel.setDelayStationMap(delayStationMap);
			}
		}else {
			int errRange = limitCount - list.size();
			for (SparePartMonModel sparePartMonModel : list) {
				Map<String, Object> delayStationMap = new HashMap<String, Object>();
				if (sparePartMonModel.getUnloadStatus().equals("1") || sparePartMonModel.getCheckStatus().equals("1")) {
					delayStationMap.put("delayStation", sparePartMonModel.getStation());
					sparePartMonModel.setDelayStationMap(delayStationMap);
				}
			}
			for(int i = 0;i < errRange; i++) {
				Map<String, Object> delayStationMap = new HashMap<String, Object>();
				delayStationMap.put("delayStation", "");
				SparePartMonModel nullModel = new SparePartMonModel();
				nullModel.setStation("");
				nullModel.setUnloadStart("");
				nullModel.setUnloadEnd("");
				nullModel.setCheckStart("");
				nullModel.setCheckEnd("");
				nullModel.setCarCard("");
				nullModel.setDelayStationMap(delayStationMap);
				list.add(nullModel);
				nullModel.setDelayStationMap(delayStationMap);
			}
		}
		return list;
	}

}

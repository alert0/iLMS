package com.hanthink.mon.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonAbnormalTrackDao;
import com.hanthink.mon.manager.MonAbnormalTrackManager;
import com.hanthink.mon.model.MonAbnormalTrackModel;
import com.hanthink.mon.model.MonProjDelayModel;
import com.hotent.base.db.api.Dao;


/**
 * @Description: 异常监控信息
 * @author luocc
 * @date 2018年11月3日
 */
@Service("MonAbnormalTrackManager")
public class MonAbnormalTrackManagerImpl extends AbstractManagerImpl<String,MonAbnormalTrackModel> implements MonAbnormalTrackManager{
	@Resource
	private MonAbnormalTrackDao monAbnormalTrackDao;
	
	@Override
	protected Dao<String, MonAbnormalTrackModel> getDao() {
		return monAbnormalTrackDao;
	}

	@Override
	public List<MonAbnormalTrackModel> queryAbnormalForPage(MonAbnormalTrackModel model) {
		List<MonAbnormalTrackModel> list = monAbnormalTrackDao.queryAbnormalForPage(model);
		Integer checkCount = 0;
		Integer prePareCount = 0;
		Integer distCount = 0;
		if(list.size() > 0) {
			for (MonAbnormalTrackModel monAbnormalTrackModel : list) {
				if ("验收".equals(monAbnormalTrackModel.getOptType())) {
					checkCount += Integer.parseInt(monAbnormalTrackModel.getExcepBox());
				}else if ("备件".equals(monAbnormalTrackModel.getOptType())) {
					prePareCount += Integer.parseInt(monAbnormalTrackModel.getExcepBox());
				}else if ("配送".equals(monAbnormalTrackModel.getOptType())) {
					distCount += Integer.parseInt(monAbnormalTrackModel.getExcepBox());
				}
			}
			MonAbnormalTrackModel countModel = new MonAbnormalTrackModel();
			countModel.setCheckCount(checkCount.toString());
			countModel.setPrePareCount(prePareCount.toString());
			countModel.setDistCount(distCount.toString());
			list.add(countModel);
		}
		return monAbnormalTrackDao.queryAbnormalForPage(model);
	}
	
}

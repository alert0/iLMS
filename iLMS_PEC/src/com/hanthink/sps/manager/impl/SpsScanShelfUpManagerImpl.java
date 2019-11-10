package com.hanthink.sps.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.sps.dao.SpsScanShelfUpDao;
import com.hanthink.sps.manager.SpsScanShelfUpManager;
import com.hanthink.sps.model.SpsScanShelfUpModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsScanShelfUpManagerImpl
 * @Description: SPS扫描上架
 * @author dtp
 * @date 2018年10月16日
 */
@Service("SpsScanShelfUpManager")
public class SpsScanShelfUpManagerImpl extends AbstractManagerImpl<String, SpsScanShelfUpModel> implements SpsScanShelfUpManager{

	@Resource
	private SpsScanShelfUpDao SpsScanShelfUpDao;
	
	@Override
	protected Dao<String, SpsScanShelfUpModel> getDao() {
		return SpsScanShelfUpDao;
	}

	/**
	 * @Description: SPS扫描上架
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsScanShelfUpModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@Override
	public PageList<SpsScanShelfUpModel> querySpsScanShelfUpPage(SpsScanShelfUpModel model, DefaultPage page) {
		return SpsScanShelfUpDao.querySpsScanShelfUpPage(model, page);
	}

	@Override
	public List<SpsScanShelfUpModel> querySpsScanShelfUpByKey(SpsScanShelfUpModel model) {
		return SpsScanShelfUpDao.querySpsScanShelfUpByKey(model);
	}

	/**
	 * 查询默认值
	 */
	@Override
	public SpsScanShelfUpModel selectDefaultValue(SpsScanShelfUpModel model) {
		return SpsScanShelfUpDao.selectDefaultValue(model);
	}

	

}

package com.hanthink.sps.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.sps.dao.SpsPadCheckDao;
import com.hanthink.sps.manager.SpsPadCheckManager;
import com.hanthink.sps.model.SpsPadCheckModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsPadCheckManagerImpl
 * @Description: pad无纸化分拣查询
 * @author dtp
 * @date 2018年10月16日
 */
@Service("SpsPadCheckManager")
public class SpsPadCheckManagerImpl extends AbstractManagerImpl<String, SpsPadCheckModel> implements SpsPadCheckManager{

	@Resource
	private SpsPadCheckDao spsPadCheckDao;
	
	@Override
	protected Dao<String, SpsPadCheckModel> getDao() {
		return spsPadCheckDao;
	}

	/**
	 * @Description: pad无纸化分拣查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsPadCheckModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@Override
	public PageList<SpsPadCheckModel> querySpsPadCheckPage(SpsPadCheckModel model, DefaultPage page) {
		return spsPadCheckDao.querySpsPadCheckPage(model, page);
	}

	/**
	 * 获取票据模板名称填充下拉框
	 */
	@Override
	public List<DictVO> getMouldName() {
		return spsPadCheckDao.getMouldName();
	}

}

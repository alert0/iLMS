package com.hanthink.sps.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.sps.dao.SpsPadCheckDao;
import com.hanthink.sps.model.SpsPadCheckModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsPadCheckDaoImpl
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
@Repository
public class SpsPadCheckDaoImpl extends MyBatisDaoImpl<String, SpsPadCheckModel> implements SpsPadCheckDao{

	@Override
	public String getNamespace() {
		return SpsPadCheckModel.class.getName();
	}

	/**
	 * @Description: SPS过点车序查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsPadCheckModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsPadCheckModel> querySpsPadCheckPage(SpsPadCheckModel model, DefaultPage page) {
		return (PageList<SpsPadCheckModel>) this.getList("querySpsPadCheckPage", model, page);
	}

	/**
	 * 获取票据模板名称
	 */
	@Override
	public List<DictVO> getMouldName() {
		Map<String,Object> map=new HashMap();
		return this.getList("getMouldName", map);
	}

}

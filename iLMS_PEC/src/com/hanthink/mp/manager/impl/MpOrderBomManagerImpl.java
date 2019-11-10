package com.hanthink.mp.manager.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mp.dao.MpOrderBomDao;
import com.hanthink.mp.manager.MpOrderBomManager;
import com.hanthink.mp.model.MpOrderBomModel;
/**
 * 根据表名实现的类
 */
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 订购使用单车BOM
 * @author WY
 *
 */
@Service("MpOrderBomManager")
public class MpOrderBomManagerImpl extends AbstractManagerImpl<String, MpOrderBomModel> implements MpOrderBomManager{
	
	@Resource
    private MpOrderBomDao mpOrderBomDao;

	@Override
	protected Dao<String, MpOrderBomModel> getDao() {
		return mpOrderBomDao;
	}

	@Override
	public PageList<MpOrderBomModel> queryMpOrderBomForPage(
			MpOrderBomModel model, DefaultPage p) {
		return (PageList<MpOrderBomModel>) mpOrderBomDao.queryMpOrderBomForPage(model, p);
	}

	@Override
	public List<MpOrderBomModel> queryMpOrderBomByKey(MpOrderBomModel model) {
		return mpOrderBomDao.queryMpOrderBomByKey(model);
	}

	@Override
	public Integer genOrderBom(String curFactoryCode, String account) {
		HashMap<String,String> paramMap = new HashMap<String,String>();   
		paramMap.put("factoryCode", curFactoryCode);
		paramMap.put("account", account);
		paramMap.put("outCode", "0");
		return mpOrderBomDao.genOrderBom(paramMap);
	}
	
	
}

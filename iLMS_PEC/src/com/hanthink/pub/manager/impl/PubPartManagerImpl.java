package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubPartDao;
import com.hanthink.pub.manager.PubPartManager;
import com.hanthink.pub.model.PubPartModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * 
 * <pre> 
 * 描述：生产节拍查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubPartManager")
public class PubPartManagerImpl extends AbstractManagerImpl<String, PubPartModel> implements PubPartManager{
	@Resource
	PubPartDao pubPartDao;
	@Override
	protected Dao<String, PubPartModel> getDao() {
		return pubPartDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubPartModel> queryPubPartForPage(PubPartModel model, DefaultPage p) {
		return pubPartDao.queryPubPartForPage(model, p);
	}

	/**
	 * 到处查询
	 */
	@Override
	public List<PubPartModel> queryPubPartByKey(PubPartModel model) {
		return pubPartDao.queryPubPartByKey(model);
	}
	
}

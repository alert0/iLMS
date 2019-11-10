package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubTactTimeDao;
import com.hanthink.pub.manager.PubTactTimeManager;
import com.hanthink.pub.model.PubTactTimeModel;

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
@Service("PubTactTimeManager")
public class PubTactTimeManagerImpl extends AbstractManagerImpl<String, PubTactTimeModel> implements PubTactTimeManager{
	@Resource
	PubTactTimeDao pubTactTimeDao;
	@Override
	protected Dao<String, PubTactTimeModel> getDao() {
		return pubTactTimeDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubTactTimeModel> queryPubTactTimeForPage(PubTactTimeModel model, DefaultPage p) {
		return pubTactTimeDao.queryPubTactTimeForPage(model, p);
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<PubTactTimeModel> queryPubTactTimeByKey(PubTactTimeModel model) {
		return pubTactTimeDao.queryPubTactTimeByKey(model);
	}
	
}

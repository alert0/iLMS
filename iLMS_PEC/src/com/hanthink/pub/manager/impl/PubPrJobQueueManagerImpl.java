package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubPrJobQueueDao;
import com.hanthink.pub.manager.PubPrJobQueueManager;
import com.hanthink.pub.model.PubPrJobQueueModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * 
 * <pre> 
 * 描述：打印日志传输表查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubPrJobQueueManager")
public class PubPrJobQueueManagerImpl extends AbstractManagerImpl<String, PubPrJobQueueModel> implements PubPrJobQueueManager{
	@Resource
	PubPrJobQueueDao pubPrJobQueueDao;
	@Override
	protected Dao<String, PubPrJobQueueModel> getDao() {
		return pubPrJobQueueDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubPrJobQueueModel> queryPubPrJobQueueForPage(PubPrJobQueueModel model, DefaultPage p) {
		return pubPrJobQueueDao.queryPubPrJobQueueForPage(model, p);
	}
	
}

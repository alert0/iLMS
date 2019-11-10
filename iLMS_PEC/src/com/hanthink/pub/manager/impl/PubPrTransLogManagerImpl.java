package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubPrTransLogDao;
import com.hanthink.pub.manager.PubPrTransLogManager;
import com.hanthink.pub.model.PubPrTransLogModel;

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
@Service("PubPrTransLogManager")
public class PubPrTransLogManagerImpl extends AbstractManagerImpl<String, PubPrTransLogModel> implements PubPrTransLogManager{
	@Resource
	PubPrTransLogDao pubPrTransLogDao;
	@Override
	protected Dao<String, PubPrTransLogModel> getDao() {
		return pubPrTransLogDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubPrTransLogModel> queryPubPrTransLogForPage(PubPrTransLogModel model, DefaultPage p) {
		return pubPrTransLogDao.queryPubPrTransLogForPage(model, p);
	}
	
}

package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubOpeLogDao;
import com.hanthink.pub.manager.PubOpeLogManager;
import com.hanthink.pub.model.PubOpeLogModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * 
 * <pre> 
 * 描述：操作日志表查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubOpeLogManager")
public class PubOpeLogManagerImpl extends AbstractManagerImpl<String, PubOpeLogModel> implements PubOpeLogManager{
	@Resource
	PubOpeLogDao pubOpeLogDao;
	@Override
	protected Dao<String, PubOpeLogModel> getDao() {
		return pubOpeLogDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubOpeLogModel> queryPubOpeLogForPage(PubOpeLogModel model, DefaultPage p) {
		return pubOpeLogDao.queryPubOpeLogForPage(model, p);
	}
	
}

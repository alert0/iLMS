package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jit.dao.JitRealKbBatchDao;
import com.hanthink.jit.manager.JitRealKbBatchManager;
import com.hanthink.jit.model.JitRealKbBatchModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * 
 * <pre> 
 * 描述：实际过点批次查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("jitRealKbBatchManager")
public class JitRealKbBatchManagerImpl extends AbstractManagerImpl<String, JitRealKbBatchModel> implements JitRealKbBatchManager{
	@Resource
	JitRealKbBatchDao JitRealKbBatchDao;
	@Override
	protected Dao<String, JitRealKbBatchModel> getDao() {
		return JitRealKbBatchDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<JitRealKbBatchModel> queryJitRealKbBatchForPage(JitRealKbBatchModel model, DefaultPage p) {
		return JitRealKbBatchDao.queryJitRealKbBatchForPage(model, p);
	}

	/**
	 * 实际过点批次查询
	 */
	@Override
	public List<JitRealKbBatchModel> queryJitRealKbBatchByKey(JitRealKbBatchModel model) {
		return JitRealKbBatchDao.queryJitRealKbBatchByKey(model);
	}
	
}

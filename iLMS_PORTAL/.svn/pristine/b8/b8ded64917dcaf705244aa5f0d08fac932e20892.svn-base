package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jit.dao.JitPkgReqDao;
import com.hanthink.jit.manager.JitPkgReqManager;
import com.hanthink.jit.model.JitPkgReqModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * 
 * <pre> 
 * 描述：拉动包装明细查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("jitPkgReqManager")
public class JitPkgReqManagerImpl extends AbstractManagerImpl<String, JitPkgReqModel> implements JitPkgReqManager{
	@Resource
	JitPkgReqDao jitPkgReqDao;
	@Override
	protected Dao<String, JitPkgReqModel> getDao() {
		return jitPkgReqDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<JitPkgReqModel> queryJitPkgReqForPage(JitPkgReqModel model, DefaultPage p) {
		return jitPkgReqDao.queryJitPkgReqForPage(model, p);
	}

	/**
	 * 拉动包装明细查询
	 */
	@Override
	public List<JitPkgReqModel> queryJitPkgReqByKey(JitPkgReqModel model) {
		return jitPkgReqDao.queryJitPkgReqByKey(model);
	}
	
}

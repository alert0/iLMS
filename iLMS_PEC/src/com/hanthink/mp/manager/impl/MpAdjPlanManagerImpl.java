package com.hanthink.mp.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpAdjPlanDao;
import com.hanthink.mp.manager.MpAdjPlanManager;
import com.hanthink.mp.model.MpAdjPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：调整计划 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpAdjPlanManager")
public class MpAdjPlanManagerImpl extends AbstractManagerImpl<String, MpAdjPlanModel> implements MpAdjPlanManager{
	@Resource
	MpAdjPlanDao mpAdjPlanDao;
	@Override
	protected Dao<String, MpAdjPlanModel> getDao() {
		return mpAdjPlanDao;
	}
	
	/**
	 * 分页查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	 @Override
	    public PageList<MpAdjPlanModel> queryMpAdjPlanForPage(MpAdjPlanModel model, DefaultPage p) {
	        return (PageList<MpAdjPlanModel>) mpAdjPlanDao.queryMpAdjPlanForPage(model, p);
	    }

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpAdjPlanModel> queryMpAdjPlanByKey(MpAdjPlanModel model) {
		return mpAdjPlanDao.queryMpAdjPlanByKey(model);
	}

	/**
	 * 获取调整计划
	 */
	@Override
	public Integer getAdjPlan(String curFactoryCode, String adjDateStrStart, String adjDateStrEnd) {
	    return mpAdjPlanDao.getAdjPlan( curFactoryCode,  adjDateStrStart,  adjDateStrEnd);
	}
	
}

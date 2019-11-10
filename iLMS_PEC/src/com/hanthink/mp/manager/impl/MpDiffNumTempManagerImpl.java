package com.hanthink.mp.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpDiffNumTempDao;
import com.hanthink.mp.manager.MpDiffNumTempManager;
import com.hanthink.mp.model.MpDiffNumTempModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：W+1,W+2生产计划 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpDiffNumTempManager")
public class MpDiffNumTempManagerImpl extends AbstractManagerImpl<String, MpDiffNumTempModel> implements MpDiffNumTempManager{
	@Resource
	MpDiffNumTempDao mpDiffNumTempDao;
	@Override
	protected Dao<String, MpDiffNumTempModel> getDao() {
		return mpDiffNumTempDao;
	}
	
	/**
	 * 分页查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	 @Override
	    public PageList<MpDiffNumTempModel> queryMpDiffNumTempForPage(MpDiffNumTempModel model, DefaultPage p) {
	        return (PageList<MpDiffNumTempModel>) mpDiffNumTempDao.queryMpDiffNumTempForPage(model, p);
	    }

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpDiffNumTempModel> queryMpDiffNumTempByKey(MpDiffNumTempModel model) {
		return mpDiffNumTempDao.queryMpDiffNumTempByKey(model);
	}

	/**
	 * 获取调整计划
	 */
	@Override
	public Integer getZsbDiffPlan(String curFactoryCode) {
		return mpDiffNumTempDao.getZsbDiffPlan( curFactoryCode);
	}
	
}

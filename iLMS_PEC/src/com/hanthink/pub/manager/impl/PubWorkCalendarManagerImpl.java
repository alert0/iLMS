package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubWorkCalendarDao;
import com.hanthink.pub.manager.PubWorkCalendarManager;
import com.hanthink.pub.model.PubWorkCalendarModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
/**
 * 根据表名实现的类
 */
import com.hotent.base.db.api.Dao;

/**
 * 
 * <pre> 
 * 描述：供应商分组 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubWorkCalendarManager")
public class PubWorkCalendarManagerImpl extends AbstractManagerImpl<String, PubWorkCalendarModel> implements PubWorkCalendarManager{
	@Resource
	PubWorkCalendarDao pubWorkCalendarDao;
	@Override
	protected Dao<String, PubWorkCalendarModel> getDao() {
		return pubWorkCalendarDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubWorkCalendarModel> queryPubWorkCalendarForPage(PubWorkCalendarModel model, DefaultPage p) {
		return pubWorkCalendarDao.queryPubWorkCalendarForPage(model, p);
	}
	
	/**
	 * 右侧栏位显示查询结果
	 */
	@Override
	public List<PubWorkCalendarModel> getRowClick(String idWorkCalendar,DefaultPage p) {
		return pubWorkCalendarDao.getRowClick(idWorkCalendar,p);
	}

    @Override
    public List<PubWorkCalendarModel> queryWorkTime(PubWorkCalendarModel model) {
        return pubWorkCalendarDao.queryWorkTime(model);
    }

    @Override
    public List<PubWorkCalendarModel> queryRestTime(PubWorkCalendarModel model) {
        return pubWorkCalendarDao.queryRestTime(model);
    }

    @Override
    public Integer queryIsWorkDay(PubWorkCalendarModel pubWorkCalendarModel) {
        return pubWorkCalendarDao.queryIsWorkDay(pubWorkCalendarModel);
    }

    @Override
    public List<PubWorkCalendarModel> queryIsWorkDayForList(List<PubWorkCalendarModel> newPubWorkCalendarModels) {
        return pubWorkCalendarDao.queryIsWorkDayForList(newPubWorkCalendarModels);
    }
	
}

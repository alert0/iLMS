package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubProPlanDao;
import com.hanthink.pub.manager.PubProPlanManager;
import com.hanthink.pub.model.PubProPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc    : W+3周计划业务处理实现类
 * @CreateOn: 2019年1月3日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Service("PubProPlanManager")
public class PubProPlanManagerImpl extends AbstractManagerImpl<String, PubProPlanModel> implements PubProPlanManager{
	@Resource
	PubProPlanDao pubProPlanDao;
	
	@Override
	protected Dao<String, PubProPlanModel> getDao() {
		return pubProPlanDao;
	}

    @Override
    public PageList<PubProPlanModel> queryPubProPlanForPage(PubProPlanModel model, DefaultPage p) {
        return pubProPlanDao.queryPubProPlanForPage(model, p);
    }

    @Override
    public List<PubProPlanModel> queryPubProPlanByKey(PubProPlanModel model) {
        return pubProPlanDao.queryPubProPlanByKey(model);
    }
}

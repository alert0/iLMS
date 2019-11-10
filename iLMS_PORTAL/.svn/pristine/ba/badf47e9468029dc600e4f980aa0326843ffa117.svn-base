package com.hanthink.pub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubProPlanDao;
import com.hanthink.pub.model.PubProPlanModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * @Desc    : W+3周计划DAO实现
 * @CreateOn: 2019年1月3日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Repository
public class PubPlanPlanDaoImpl extends MyBatisDaoImpl<String, PubProPlanModel> implements PubProPlanDao{

    @Override
    public String getNamespace() {
        return PubProPlanModel.class.getName();
    }

    @Override
    public PageList<PubProPlanModel> queryPubProPlanForPage(PubProPlanModel model, DefaultPage p) {
        return (PageList<PubProPlanModel>) this.getByKey("queryPubProPlanForPage", model, p);
    }

    @Override
    public List<PubProPlanModel> queryPubProPlanByKey(PubProPlanModel model) {
        return this.getByKey("queryPubProPlanForPage", model);
    }
}


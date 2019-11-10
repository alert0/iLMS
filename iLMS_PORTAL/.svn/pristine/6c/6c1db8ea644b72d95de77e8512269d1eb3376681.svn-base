package com.hanthink.pub.manager;

import java.util.List;

import com.hanthink.pub.model.PubProPlanModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * @Desc    : W+3周计划业务处理
 * @CreateOn: 2019年1月3日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface PubProPlanManager extends Manager<String, PubProPlanModel>{

    /**
     * 分页查询W+3周计划数据
     * @param model
     * @param p
     * @return
     */
    PageList<PubProPlanModel> queryPubProPlanForPage(PubProPlanModel model, DefaultPage p);

    /**
     * 查询FOR导出
     * @param model
     * @return
     */
    List<PubProPlanModel> queryPubProPlanByKey(PubProPlanModel model);

}

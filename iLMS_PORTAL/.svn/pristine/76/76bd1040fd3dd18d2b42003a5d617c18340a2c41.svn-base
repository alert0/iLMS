package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.PubProPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * @Desc    : W+3周计划DAO
 * @CreateOn: 2019年1月3日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface PubProPlanDao extends Dao<String, PubProPlanModel> {

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

package com.hanthink.pub.manager;

import java.util.List;

import com.hanthink.pub.model.WelModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * @Desc    : 首页业务处理
 * @CreateOn: 2019年1月5日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface WelManager extends Manager<String, WelModel>{

    /**
     * 查询包装提案数
     * @param model
     * @param p
     * @return
     */
    PageList<WelModel> queryPackageForPage(WelModel model, DefaultPage p);

    /**
     * 查询订单
     * @param model
     * @param p
     * @return
     */
    PageList<WelModel> queryOrderForPage(WelModel model, DefaultPage p);

    /**
     * 加载首页Panel
     * @param curFactoryCode
     */
    List<WelModel> loadRolePanel(String account);
    
}

package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.WelModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


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
public interface WelDao extends Dao<String, WelModel> {

    /**
     * 查询包装提案数
     * @param model
     * @param p
     * @return
     */
    PageList<WelModel> queryPackageForPage(WelModel model, DefaultPage p);

    /**
     * 查询订单信息
     * @param model
     * @param p
     * @return
     */
    PageList<WelModel> queryOrderForPage(WelModel model, DefaultPage p);

    /**
     * 查询首页面板权限
     * @param curFactoryCode
     * @return
     */
    List<WelModel> loadRolePanel(String account);
	   
}

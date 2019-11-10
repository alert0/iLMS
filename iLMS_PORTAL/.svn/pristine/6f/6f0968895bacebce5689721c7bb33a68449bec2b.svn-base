package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.WelDao;
import com.hanthink.pub.manager.WelManager;

/**
 * 根据表名实现的类
 */

import com.hanthink.pub.model.WelModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc    : 首页业务处理实现类
 * @CreateOn: 2019年1月5日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Service("PubWelManager")
public class WelManagerImpl extends AbstractManagerImpl<String, WelModel> implements WelManager{
	@Resource
	WelDao welDao;
	@Override
	protected Dao<String, WelModel> getDao() {
		return welDao;
	}
	
    @Override
    public PageList<WelModel> queryPackageForPage(WelModel model, DefaultPage p) {
        return welDao.queryPackageForPage(model, p);
    }

    @Override
    public PageList<WelModel> queryOrderForPage(WelModel model, DefaultPage p) {
        return welDao.queryOrderForPage(model, p);
    }
    
    @Override
    public List<WelModel> loadRolePanel(String account) {
        return welDao.loadRolePanel(account);
    }
}

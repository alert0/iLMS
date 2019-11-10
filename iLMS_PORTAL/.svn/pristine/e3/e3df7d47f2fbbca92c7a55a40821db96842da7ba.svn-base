package com.hanthink.pub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.WelDao;
import com.hanthink.pub.model.WelModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * @Desc    : 首页查询Dao实现
 * @CreateOn: 2019年1月5日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Repository
public class WelDaoImpl extends MyBatisDaoImpl<String, WelModel> implements WelDao{

    @Override
    public String getNamespace() {
        return WelModel.class.getName();
    }

    @Override
    public PageList<WelModel> queryPackageForPage(WelModel model, DefaultPage p) {
        return (PageList<WelModel>) this.getByKey("queryPackageForPage", model, p);
    }

    @Override
    public PageList<WelModel> queryOrderForPage(WelModel model, DefaultPage p) {
    	//判断当前登录人是admin还是供应商
        if("admin".equals(model.getAccount())){
            //如果是管理员
            return (PageList<WelModel>) this.getByKey("queryAllOrderForPage", model, p);
        }if("1".equals(model.getUserType())){
        	//如果是管理员
            return (PageList<WelModel>) this.getByKey("queryAllOrderForPage", model, p);
        }else{
            return (PageList<WelModel>) this.getByKey("queryOrderForPage", model, p);
        }
    }
    
    @Override
    public List<WelModel> loadRolePanel(String account) {
        return this.getByKey("loadRolePanel", account);
    }
	
}


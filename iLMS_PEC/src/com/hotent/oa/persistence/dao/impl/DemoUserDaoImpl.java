package com.hotent.oa.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.oa.persistence.dao.DemoUserDao;
import com.hotent.oa.persistence.model.DemoUser;

/**
 * 
 * <pre> 
 * 描述：DemoUser DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-02 11:05:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class DemoUserDaoImpl extends MyBatisDaoImpl<String, DemoUser> implements DemoUserDao{

    @Override
    public String getNamespace() {
        return DemoUser.class.getName();
    }
	
}


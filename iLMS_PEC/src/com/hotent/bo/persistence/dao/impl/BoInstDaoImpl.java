package com.hotent.bo.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bo.persistence.dao.BoInstDao;
import com.hotent.bo.persistence.model.BoInst;

/**
 * 
 * <pre> 
 * 描述：业务对象实例 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-11 09:52:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BoInstDaoImpl extends MyBatisDaoImpl<String, BoInst> implements BoInstDao{

    @Override
    public String getNamespace() {
        return BoInst.class.getName();
    }
	
}


package com.hotent.bo.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bo.persistence.dao.BODataRelDao;
import com.hotent.bo.persistence.model.BODataRel;

/**
 * 
 * <pre> 
 * 描述：BO数据关联表，用于多对多的情况。 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BODataRelDaoImpl extends MyBatisDaoImpl<String, BODataRel> implements BODataRelDao{

    @Override
    public String getNamespace() {
        return BODataRel.class.getName();
    }
	
}


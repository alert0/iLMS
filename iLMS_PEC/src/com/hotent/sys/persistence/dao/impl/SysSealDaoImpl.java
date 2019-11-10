package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysSealDao;
import com.hotent.sys.persistence.model.SysSeal;


/**
 * 
 * <pre> 
 * 描述：电子印章 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-11-12 10:14:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysSealDaoImpl extends MyBatisDaoImpl<String, SysSeal> implements SysSealDao{

    @Override
    public String getNamespace() {
        return SysSeal.class.getName();
    }
	
}


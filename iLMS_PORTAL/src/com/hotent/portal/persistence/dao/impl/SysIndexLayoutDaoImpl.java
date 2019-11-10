package com.hotent.portal.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.portal.persistence.dao.SysIndexLayoutDao;
import com.hotent.portal.persistence.model.SysIndexLayout;

/**
 * 
 * <pre> 
 * 描述：sys_index_layout DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:15
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysIndexLayoutDaoImpl extends MyBatisDaoImpl<Long, SysIndexLayout> implements SysIndexLayoutDao{

    @Override
    public String getNamespace() {
        return SysIndexLayout.class.getName();
    }
	
}


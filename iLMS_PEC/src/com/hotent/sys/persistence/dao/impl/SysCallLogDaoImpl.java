package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.api.model.SysCallLog;
import com.hotent.sys.persistence.dao.SysCallLogDao;

/**
 * 
 * <pre> 
 * 描述：sys_call_log DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:zhangxw
 * 邮箱:zhangxw@jee-soft.cn
 * 日期:2017-10-26 11:40:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysCallLogDaoImpl extends MyBatisDaoImpl<String, SysCallLog> implements SysCallLogDao{

    @Override
    public String getNamespace() {
        return SysCallLog.class.getName();
    }
	
}


package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysOfficeTemplateDao;
import com.hotent.sys.persistence.model.SysOfficeTemplate;
;

/**
 * 
 * <pre> 
 * 描述：OFFICE模版表 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-10-31 16:08:45
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysOfficeTemplateDaoImpl extends MyBatisDaoImpl<String, SysOfficeTemplate> implements SysOfficeTemplateDao{

    @Override
    public String getNamespace() {
        return SysOfficeTemplate.class.getName();
    }
	
}


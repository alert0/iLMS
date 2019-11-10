package com.hotent.bpmx.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTestCaseDao;
import com.hotent.bpmx.persistence.model.BpmTestCase;

/**
 * 
 * <pre> 
 * 描述：流程的测试用例设置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2018-01-15 16:39:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmTestCaseDaoImpl extends MyBatisDaoImpl<String, BpmTestCase> implements BpmTestCaseDao{

    @Override
    public String getNamespace() {
        return BpmTestCase.class.getName();
    }
	
}


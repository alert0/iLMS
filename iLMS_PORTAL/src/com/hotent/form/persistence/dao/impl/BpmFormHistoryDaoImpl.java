package com.hotent.form.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.BpmFormHistoryDao;
import com.hotent.form.persistence.model.BpmFormHistory;

/**
 * 
 * <pre> 
 * 描述：流程表单HTML设计历史记录 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:xianggang
 * 邮箱:xianggang.qq.com
 * 日期:2014-10-23 15:31:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmFormHistoryDaoImpl extends MyBatisDaoImpl<String, BpmFormHistory> implements BpmFormHistoryDao{

    @Override
    public String getNamespace() {
        return BpmFormHistory.class.getName();
    }
	
}


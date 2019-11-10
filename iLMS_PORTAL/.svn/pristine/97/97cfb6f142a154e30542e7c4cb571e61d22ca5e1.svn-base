package com.hotent.form.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.form.persistence.dao.BpmFormHistoryDao;
import com.hotent.form.persistence.manager.BpmFormHistoryManager;
import com.hotent.form.persistence.model.BpmFormHistory;

/**
 * 
 * <pre> 
 * 描述：流程表单HTML设计历史记录 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:xianggang
 * 邮箱:xianggang.qq.com
 * 日期:2014-10-23 15:31:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmFormHistoryManager")
public class BpmFormHistoryManagerImpl extends AbstractManagerImpl<String, BpmFormHistory> implements BpmFormHistoryManager{
	@Resource
	BpmFormHistoryDao bpmFormHistoryDao;
	@Override
	protected Dao<String, BpmFormHistory> getDao() {
		return bpmFormHistoryDao;
	}
}

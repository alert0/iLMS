package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmExeStackRelationDao;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;
import com.hotent.bpmx.persistence.manager.BpmExeStackRelationManager;

/**
 * 
 * <pre> 
 * 描述：堆栈关系表 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-06-17 13:56:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmExeStackRelationManager")
public class BpmExeStackRelationManagerImpl extends AbstractManagerImpl<String, BpmExeStackRelation> implements BpmExeStackRelationManager{
	@Resource
	BpmExeStackRelationDao bpmExeStackRelationDao;
	@Override
	protected Dao<String, BpmExeStackRelation> getDao() {
		return bpmExeStackRelationDao;
	}
}

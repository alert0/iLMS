package com.hotent.bpmx.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskDueTimeDao;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;

/**
 * 
 * <pre> 
 * 描述：任务期限统计 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-05-16 16:25:22
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmTaskDueTimeDaoImpl extends MyBatisDaoImpl<String, BpmTaskDueTime> implements BpmTaskDueTimeDao{

    @Override
    public String getNamespace() {
        return BpmTaskDueTime.class.getName();
    }

	@Override
	public BpmTaskDueTime getByTaskId(String taskId) {
		return this.getUnique("getByTaskId", taskId);
	}
	
}


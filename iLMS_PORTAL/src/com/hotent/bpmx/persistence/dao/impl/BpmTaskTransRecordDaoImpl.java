package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskTransRecordDao;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;

/**
 * 
 * <pre> 
 * 描述：bpm_task_trans_record DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-04 16:12:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmTaskTransRecordDaoImpl extends MyBatisDaoImpl<String, BpmTaskTransRecord> implements BpmTaskTransRecordDao{

    @Override
    public String getNamespace() {
        return BpmTaskTransRecord.class.getName();
    }

	@Override
	public BpmTaskTransRecord getByTaskId(String taskId) {
		return this.getUnique("getByTaskId", taskId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BpmTaskTransRecord> getTransRecord(QueryFilter queryFilter) {
		return this.getByQueryFilter("getTransRecord", queryFilter);
	}
	
}


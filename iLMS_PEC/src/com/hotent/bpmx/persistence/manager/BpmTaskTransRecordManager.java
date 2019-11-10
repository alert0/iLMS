package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;

/**
 * 
 * <pre> 
 * 描述：bpm_task_trans_record 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-04 16:12:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmTaskTransRecordManager extends Manager<String, BpmTaskTransRecord>{
	
	/**
	 * 根据任务id获取流转任务记录
	 * @param taskId
	 * @return
	 */
	BpmTaskTransRecord getByTaskId(String taskId);
	
	/**
	 * 获取用户的流转记录列表
	 * @param queryFilter
	 * @return
	 */
	List<BpmTaskTransRecord> getMyTransRecord(QueryFilter queryFilter);
	
	/**
	 * 获取流转记录列表
	 * @param queryFilter
	 * @return
	 */
	List<BpmTaskTransRecord> getTransRecordList(QueryFilter queryFilter);
}

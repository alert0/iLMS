package com.hotent.bpmx.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;

/**
 * 
 * <pre> 
 * 描述：任务期限统计 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-05-16 16:25:22
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmTaskDueTimeDao extends Dao<String, BpmTaskDueTime> {

	BpmTaskDueTime getByTaskId(String taskId);
}

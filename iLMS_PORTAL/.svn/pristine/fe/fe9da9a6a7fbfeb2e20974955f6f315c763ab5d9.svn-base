package com.hotent.bpmx.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmTransReceiver;

/**
 * 
 * <pre> 
 * 描述：流转任务接收人 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-06 10:51:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmTransReceiverManager extends Manager<String, BpmTransReceiver>{
	
	/**
	 * 根据transRecordid获取接收人列表
	 * @param transRecordid
	 * @return
	 */
	List<BpmTransReceiver> getByTransRecordid(String transRecordid);
	
	/**
	 * 根据transRecordid和人员id获取数据
	 * @param params
	 * @return
	 */
	BpmTransReceiver getByTransRecordAndUserId(Map<String,String> params);
}

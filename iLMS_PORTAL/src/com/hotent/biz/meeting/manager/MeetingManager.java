package com.hotent.biz.meeting.manager;



import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.biz.meeting.model.Meeting;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-10 16:53:39
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MeetingManager extends Manager<String, Meeting>{
	
	List<DefaultBpmTask> getByNeedPendMeetingUserId(String userId,QueryFilter queryFilter);
	
	List<Map<String,Object>> getMyAllRequestByUserId(String userId, QueryFilter queryFilter);
	
	List<Map<String, Object>> getHandledMeetingByUserId(String userId, QueryFilter queryFilter);
	
}

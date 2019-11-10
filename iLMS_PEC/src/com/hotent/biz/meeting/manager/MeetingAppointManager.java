package com.hotent.biz.meeting.manager;



import com.hotent.base.manager.api.Manager;
import com.hotent.biz.meeting.model.MeetingAppoint;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-16 15:18:41
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MeetingAppointManager extends Manager<String, MeetingAppoint>{
	MeetingAppoint getByMeetingId(String id);
}

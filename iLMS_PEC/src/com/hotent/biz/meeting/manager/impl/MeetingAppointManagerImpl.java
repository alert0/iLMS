package com.hotent.biz.meeting.manager.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.biz.meeting.dao.MeetingAppointDao;
import com.hotent.biz.meeting.manager.MeetingAppointManager;
import com.hotent.biz.meeting.model.MeetingAppoint;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-16 15:18:41
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("meetingAppointManager")
public class MeetingAppointManagerImpl extends AbstractManagerImpl<String, MeetingAppoint> implements MeetingAppointManager{
	@Resource
	MeetingAppointDao meetingAppointDao;
	@Override
	protected Dao<String, MeetingAppoint> getDao() {
		return meetingAppointDao;
	}
	@Override
	public MeetingAppoint getByMeetingId(String id) {
		// TODO Auto-generated method stub
	  return meetingAppointDao.getByMeetingId(id);
	}
}

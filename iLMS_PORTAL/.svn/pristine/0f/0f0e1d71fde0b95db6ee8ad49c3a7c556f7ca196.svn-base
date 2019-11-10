package com.hotent.biz.meeting.dao.impl;



import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.biz.meeting.dao.MeetingAppointDao;
import com.hotent.biz.meeting.model.MeetingAppoint;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-16 15:18:41
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MeetingAppointDaoImpl extends MyBatisDaoImpl<String, MeetingAppoint> implements MeetingAppointDao{

    @Override
    public String getNamespace() {
        return MeetingAppoint.class.getName();
    }


	@Override
	public MeetingAppoint getByMeetingId(String id) {
		// TODO Auto-generated method stub
		 return  this.getUnique("getByMeetingId", id);
	}
	
}


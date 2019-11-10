package com.hotent.biz.meeting.manager.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.biz.meeting.dao.MeetingroomDao;
import com.hotent.biz.meeting.manager.MeetingroomManager;
import com.hotent.biz.meeting.model.Meetingroom;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 16:15:07
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("meetingroomManager")
public class MeetingroomManagerImpl extends AbstractManagerImpl<String, Meetingroom> implements MeetingroomManager{
	@Resource
	MeetingroomDao meetingroomDao;
	@Override
	protected Dao<String, Meetingroom> getDao() {
		return meetingroomDao;
	}
}

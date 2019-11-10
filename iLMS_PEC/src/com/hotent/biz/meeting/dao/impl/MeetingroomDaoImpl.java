package com.hotent.biz.meeting.dao.impl;



import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.biz.meeting.dao.MeetingroomDao;
import com.hotent.biz.meeting.model.Meetingroom;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 16:15:07
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MeetingroomDaoImpl extends MyBatisDaoImpl<String, Meetingroom> implements MeetingroomDao{

    @Override
    public String getNamespace() {
        return Meetingroom.class.getName();
    }
	
}


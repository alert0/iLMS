package com.hotent.biz.meeting.dao;


import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.biz.meeting.model.Meeting;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-10 16:53:39
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MeetingDao extends Dao<String, Meeting> {
	List<DefaultBpmTask> getByNeedPendMeetingUserId(String userId, Map<String, String> groupMap,
			QueryFilter queryFilter);
	
    /**
     * 获取用户发起的所有请求。草稿除外
     * @param userId
     * @param queryFilter
     * @return
     */
	List<Map<String,Object>> getMyAllRequestByUserId(String userId, QueryFilter queryFilter);
	
	List<Map<String, Object>> getHandledMeetingByUserId(String userId, QueryFilter queryFilter);
	
	
}

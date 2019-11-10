package com.hotent.biz.meeting.manager.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.biz.meeting.dao.MeetingDao;
import com.hotent.biz.meeting.manager.MeetingManager;
import com.hotent.biz.meeting.model.Meeting;
import com.hotent.bpmx.persistence.dao.BpmTaskDao;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.org.api.impl.service.DefaultUserGroupService;
import com.hotent.org.api.model.IGroup;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-10 16:53:39
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("meetingManager")
public class MeetingManagerImpl extends AbstractManagerImpl<String, Meeting> implements MeetingManager{
	@Resource
	MeetingDao meetingDao;
	@Resource
	DefaultUserGroupService defaultUserGroupService;
	@Resource
	BpmTaskDao bpmTaskDao;
	@Override
	protected Dao<String, Meeting> getDao() {
		return meetingDao;
	}
	
	@Override
	public List<DefaultBpmTask> getByNeedPendMeetingUserId(String userId, QueryFilter queryFilter) {
		List<IGroup> list= defaultUserGroupService.getGroupsByUserId(userId);
		Map<String,String> groupMap=convertGroupList(list);
		return meetingDao.getByNeedPendMeetingUserId(userId, groupMap,queryFilter);
	}
	
	@Override
	public List<Map<String,Object>> getMyAllRequestByUserId(String userId, QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return meetingDao.getMyAllRequestByUserId(userId,queryFilter);
	}
	private Map<String,String> convertGroupList(List<IGroup> list){
		Map<String,String> map=new HashMap<String, String>();
		if(BeanUtils.isEmpty(list)) return map;
		for(IGroup group:list){
			String type=group.getGroupType();
			if(map.containsKey(type)){
				String groupId=map.get(type);
				groupId+=",'" + group.getGroupId() +"'";
				map.put(type,groupId);
			}
			else{
				map.put(type,"'"+ group.getGroupId()  +"'");
			}
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> getHandledMeetingByUserId(String userId, QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return meetingDao.getHandledMeetingByUserId(userId,queryFilter);
	}
}

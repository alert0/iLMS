package com.hotent.biz.meeting.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.biz.meeting.dao.MeetingDao;
import com.hotent.biz.meeting.model.Meeting;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-10 16:53:39
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MeetingDaoImpl extends MyBatisDaoImpl<String, Meeting> implements MeetingDao{

    @Override
    public String getNamespace() {
        return Meeting.class.getName();
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmTask> getByNeedPendMeetingUserId(String userId, Map<String, String> groupMap,
			QueryFilter queryFilter) {
		Map<String, Object> params = getParamByGroupMap(userId, groupMap, queryFilter);
		return this.getByKey("getByNeedPendMeetingUserId", params, queryFilter.getPage());
	}
	private Map<String, Object> getParamByGroupMap(String userId, Map<String, String> groupMap, QueryFilter queryFilter)
	{
		Map<String, Object> params = queryFilter.getParams();
		if (params == null)
			params = new HashMap<String, Object>();

		params.put("groupMap", groupMap);
		params.put("assignee", userId);

		if (queryFilter != null&&!queryFilter.getFieldLogic().getSql().equals(""))
			params.put("whereSql", queryFilter.getFieldLogic().getSql());
		if (queryFilter.getFieldSortList() != null&&queryFilter.getFieldSortList().size()>0)
		{
			String sort = queryFilter.getFieldSortList().get(0).getDirection().name();
			String order = queryFilter.getFieldSortList().get(0).getField();
			params.put("orderBySql",order+" "+sort);
		}
		return params;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getMyAllRequestByUserId(String userId,
			QueryFilter queryFilter) {
		queryFilter.addParamsFilter("userId", userId);
		return  this.getByQueryFilter("getMyAllRequestByUserId", queryFilter);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getHandledMeetingByUserId(String userId,
			QueryFilter queryFilter) {
		queryFilter.addParamsFilter("userId", userId);
		return this.getByQueryFilter("getHandledMeetingByUserId", queryFilter);
	}
}


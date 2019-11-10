package com.hotent.biz.meeting.controller;




import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.biz.meeting.manager.MeetingManager;
import com.hotent.biz.meeting.manager.impl.MeetingroomManagerImpl;
import com.hotent.biz.meeting.model.Meeting;
import com.hotent.biz.meeting.model.Meetingroom;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.api.service.BpmOpinionService;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;

import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-10 16:53:39
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/business/meeting/meeting")
public class MeetingController extends GenericController{
	private static final PageList<JSONObject> JSONObject = null;
	@Resource
	MeetingManager meetingManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmInstService bpmInstService;
	@Resource
	BoDataService boDataService;
	@Resource
	BpmOpinionService bpmOpinionService;       
	@Resource
	MeetingroomManagerImpl meetingroomManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	/**
	 * xq_meetingroom_appointment列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<Meeting> meetingList=(PageList<Meeting>)meetingManager.query(queryFilter);
		return new PageJson(meetingList);
	}
	
	
	
	/**
	 * xq_meetingroom_appointment明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody Meeting getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new Meeting();
		}
		Meeting meeting=meetingManager.get(id);
		return meeting;
	}
	
	/**
	 * 保存xq_meetingroom_appointment信息
	 * @param request
	 * @param response
	 * @param meeting
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody Meeting meeting) throws Exception{
		String resultMsg=null;
		String id=meeting.getId();
		try {
			if(StringUtil.isEmpty(id)){
				meeting.setId(UniqueIdUtil.getSuid());
				meetingManager.create(meeting);
				resultMsg="添加xq_meetingroom_appointment成功";
			}else{
				meetingManager.update(meeting);
				resultMsg="更新xq_meetingroom_appointment成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对xq_meetingroom_appointment操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除xq_meetingroom_appointment记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			meetingManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除xq_meetingroom_appointment成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除xq_meetingroom_appointment失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 待参加会议
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pendingJson")
	public @ResponseBody PageJson pendingJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addFilter("PROC_DEF_KEY_", "hylc",QueryOP.EQUAL);
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) meetingManager.getByNeedPendMeetingUserId(userId, queryFilter);
		PageList<JSONObject> objList= new  PageList<JSONObject> ();
		for(int i=0;i<list.size();i++){
			DefaultBpmTask task=  list.get(i);
			if( "task2".equals(task.getNodeId())|| "task3".equals(task.getNodeId())){
				BpmProcessInstance bpmProcessInstance = bpmInstService.getProcessInstance(task.getProcInstId());
				List<BoData> boDatas = boDataService.getDataByInst(bpmProcessInstance);
				// BO数据前置处理
				JSONObject data =BoDataUtil.hanlerData(bpmProcessInstance,task.getNodeId(), boDatas).getJSONObject("hybd");
				Meetingroom meetingroom=meetingroomManager.get(data.get("hys").toString());
				if(BeanUtils.isNotEmpty(meetingroom)){
					data.put("hys", meetingroom.getName());
				}
				data.put("nodeId", task.getNodeId());
				data.put("procInstId", task.getProcInstId());
				data.put("taskId", task.getId());
				objList.add(data);
			}
		}
		objList.setPageResult(list.getPageResult());
		return new PageJson(objList);
	}
	
	/**
	 * 我的发起的会议
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myRequestJson")
	public @ResponseBody PageJson myRequestJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		PageList<Map<String,Object>> list = (PageList<Map<String,Object>>)  meetingManager.getMyAllRequestByUserId(userId, queryFilter);
		// 查询列表
		PageList<Map<String,Object>> pList = new PageList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> data =list.get(i);
			List<BpmTaskOpinion> bpmTaskOpinions =  bpmOpinionService.getTaskOpinions(list.get(i).get("proInstId").toString());
			// BO数据前置处理
			Meetingroom meetingroom=meetingroomManager.get(data.get("hys").toString());
				BpmTaskOpinion bto =bpmTaskOpinions.get(bpmTaskOpinions.size()-1);
			if(BeanUtils.isNotEmpty(meetingroom)){
				data.put("hys", meetingroom.getName());
			}
			data.put("taskId", bto.getTaskKey());
			pList.add(data);
		}
		pList.setPageResult(list.getPageResult());
		return new PageJson(pList);
	}
	
	/**
	 *历史会议
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myCompletedJson")
	public @ResponseBody PageJson myCompletedJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		queryFilter.addFilter("wfInst.PROC_DEF_KEY_", "hylc",QueryOP.EQUAL);
		queryFilter.addFilter("opinion.task_key_", "task3",QueryOP.EQUAL);
		// 查询列表
		PageList<Map<String,Object>> list = (PageList<Map<String,Object>>)  meetingManager.getHandledMeetingByUserId(userId, queryFilter);
		PageList<Map<String,Object>> pList = new PageList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
				Map<String,Object> data =list.get(i);
				Meetingroom meetingroom=meetingroomManager.get(data.get("hys").toString());
				if(BeanUtils.isNotEmpty(meetingroom)){
					data.put("hys", meetingroom.getName());
				}
				data.put("taskId", list.get(i).get("taskKey").toString());
				data.put("taskId1",list.get(i).get("taskKey").toString());
				pList.add(data);
		}
		pList.setPageResult(list.getPageResult());
		return new PageJson(pList);
	}
	/**
	 *根据流程key获取最新的流程定义id
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBpmDefId")
	public @ResponseBody String getBpmDefByDefkey(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		List<DefaultBpmDefinition> defs = bpmDefinitionManager.queryByDefKey("hylc");
		String  defId = ""; 
	       if(defs.size()>0 && BeanUtils.isNotEmpty(defs.get(defs.size()-1))){
	    	   defId= defs.get(defs.size()-1).getDefId();
	       }
		return defId;
	}
}

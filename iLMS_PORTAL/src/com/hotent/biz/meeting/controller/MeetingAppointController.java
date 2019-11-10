package com.hotent.biz.meeting.controller;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.hotent.biz.meeting.manager.MeetingAppointManager;
import com.hotent.biz.meeting.manager.MeetingroomManager;
import com.hotent.biz.meeting.model.MeetingAppoint;
import com.hotent.biz.meeting.model.Meetingroom;
import com.hotent.bpmx.persistence.manager.BpmDefUserManager;


import net.sf.json.JSONObject;


/**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-16 15:18:41
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/business/meetingAppoint/meetingAppoint")
public class MeetingAppointController extends GenericController{
	@Resource
	MeetingAppointManager meetingAppointManager;
	@Resource
	MeetingroomManager meetingroomManager;
	@Resource
	BpmDefUserManager bpmDefUserManager;
	
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
		QueryFilter qfForMeetingAppoint=getQueryFilter(request);
		//获取用户申请批权限的会议室id
		List<String> idsList = bpmDefUserManager.getAuthorizeIdsByUserMap("meetingRoom");
		String ids= StringUtil.join(idsList.toArray(new String[]{}), ",");
		if(StringUtil.isNotEmpty(ids)) {
			queryFilter.addFilter("ID_", ids, QueryOP.IN);
		}
		PageList<Meetingroom> meetingroomList=(PageList<Meetingroom>)meetingroomManager.query(queryFilter);
		String weekStr=RequestUtil.getString(request,"param");
		String[] arr = weekStr.split(",");
		Date begTime = changeToDate(arr[0].split("\\|")[0]+" 00:00:00");
		Date endTime = changeToDate(arr[6].split("\\|")[0]+" 00:00:00");
		qfForMeetingAppoint.addFilter("APPOINTMENT_BEG_TIME_", begTime, QueryOP.GREAT_EQUAL);
		qfForMeetingAppoint.addFilter("APPOINTMENT_END_TIME_", endTime, QueryOP.LESS_EQUAL);
		qfForMeetingAppoint.addFilter("APPOINTMENT_STATUS_", "1", QueryOP.EQUAL);
		if(StringUtil.isNotEmpty(ids)) {
			qfForMeetingAppoint.addFilter("MEETINGROOM_ID_", ids, QueryOP.IN);
		}
		PageList<MeetingAppoint> meetingAppointList=(PageList<MeetingAppoint>)meetingAppointManager.query(qfForMeetingAppoint);
		PageList<JSONObject> objList= new  PageList<JSONObject> ();
        for(int i=0;i<meetingroomList.size();i++){
        	JSONObject data=new JSONObject();
        	Meetingroom meetingroom=meetingroomList.get(i);
        	data.put("mtRoomId",meetingroom.getId());
        	data.put("mtName",meetingroom.getName());
     		for(int z=0;z<arr.length;z++){
     			String appointMsg="";
    			int curDate=Integer.valueOf(arr[z].split("\\|")[0].replace("-", ""));
        	    String elNmae=arr[z].split("\\|")[1];
        		for(int j=0;j<meetingAppointList.size();j++){
            		MeetingAppoint meetingAppoint=meetingAppointList.get(j);
            		int endDate=changeTime(meetingAppoint.getAppointmentEndTime());
            		int begDate=changeTime(meetingAppoint.getAppointmentBegTime());
            		//如果该会议室在预约表中有记录，并且该天在会议持续时间所在的天内 。则向该会议室该天加入该条记录
            		if(meetingAppoint.getMeetingroomId().equals(meetingroom.getId()) &&	curDate<=endDate && curDate>=begDate){
                			 appointMsg+= "<span class=\"cur\" onClick=\"openDetail("+meetingAppoint.getMeetingId()+")\">";
                			 appointMsg+=meetingAppoint.getMeetingName();
                			 if(BeanUtils.isNotEmpty(meetingAppoint.getHostessName())){
                				 appointMsg+="("+meetingAppoint.getHostessName()+")";
                			 }
                    		 appointMsg+=getTime(meetingAppoint.getAppointmentBegTime())+"-";
                    		 appointMsg+=getTime(meetingAppoint.getAppointmentEndTime());
                    		 appointMsg+="</span><br>";
            	}
    			
        	 }
        		if("".equals(appointMsg)){
        			appointMsg="<div class=\"pendingDiv\" onClick=\"pendingMeeting("+meetingroom.getId()+",'"+arr[z].split("\\|")[0]+"')\"></div>";
        		}
        		 data.put(elNmae, appointMsg);
        	}
    		 objList.add(data);
        }
        objList.setPageResult(meetingroomList.getPageResult());
		return new PageJson(objList);
	}
	public String getTime(Date d){
		return d.getHours()+":"+(Integer.valueOf(d.getMinutes()) < 10? "0"+d.getMinutes():d.getMinutes());
	}	
	public int changeTime(Date d){
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		 String dateString = formatter.format(d);
		return Integer.valueOf(dateString);
	}	

	public Date changeToDate(String str){
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date =null;
		 try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
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
	public @ResponseBody MeetingAppoint getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new MeetingAppoint();
		}
		MeetingAppoint meetingAppoint=meetingAppointManager.get(id);
		return meetingAppoint;
	}
	
	/**
	 * 保存xq_meetingroom_appointment信息
	 * @param request
	 * @param response
	 * @param meetingAppoint
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody MeetingAppoint meetingAppoint) throws Exception{
		String resultMsg=null;
		String id=meetingAppoint.getId();
		try {
			if(StringUtil.isEmpty(id)){
				meetingAppoint.setId(UniqueIdUtil.getSuid());
				meetingAppointManager.create(meetingAppoint);
				resultMsg="添加xq_meetingroom_appointment成功";
			}else{
				meetingAppointManager.update(meetingAppoint);
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
			meetingAppointManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除xq_meetingroom_appointment成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除xq_meetingroom_appointment失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}

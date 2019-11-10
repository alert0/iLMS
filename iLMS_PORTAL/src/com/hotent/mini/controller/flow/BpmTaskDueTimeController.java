package com.hotent.mini.controller.flow;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctc.wstx.util.DataUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.util.time.DateUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmTaskDueTimeManager;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;
import com.hotent.sys.api.calendar.ICalendarService;



/**
 * 
 * <pre> 
 * 描述：任务期限统计 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-05-16 16:21:43
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/flow/task/bpmTaskDueTime")
public class BpmTaskDueTimeController extends GenericController{
	@Resource
	BpmTaskDueTimeManager bpmTaskDueTimeManager;
	@Resource
	ICalendarService iCalendarService;
	
	
	/**
	 * 任务期限统计列表(分页条件查询)数据
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
		PageList<BpmTaskDueTime> bpmTaskDueTimeList=(PageList<BpmTaskDueTime>)bpmTaskDueTimeManager.query(queryFilter);
		return new PageJson(bpmTaskDueTimeList);
	}
	
	
	
	/**
	 * 任务期限统计明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody BpmTaskDueTime getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		BpmTaskDueTime bpmTaskDueTime=bpmTaskDueTimeManager.get(id);
		return bpmTaskDueTime;
	}
	
	/**
	 * 根据任务id 获取最新的延期信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getByTaskId")
	public @ResponseBody BpmTaskDueTime getByTaskId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String taskId=RequestUtil.getString(request, "taskId");
		if(StringUtil.isEmpty(taskId)){
			return null;
		}
		BpmTaskDueTime bpmTaskDueTime=bpmTaskDueTimeManager.getByTaskId(taskId);
		if(BeanUtils.isEmpty(bpmTaskDueTime)){
			return new BpmTaskDueTime();
		}
		int remainingTime=getRemainingTime(bpmTaskDueTime);
		bpmTaskDueTime.setRemainingTime(remainingTime);
		return bpmTaskDueTime;
	}
	
	@RequestMapping("getExpirationDate")
	public @ResponseBody Object getExpirationDate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		int addDueTime = RequestUtil.getInt(request, "addDueTime", 0);
		if(StringUtil.isEmpty(id)){
			return null;
		}
		BpmTaskDueTime bpmTaskDueTime=bpmTaskDueTimeManager.get(id);
		Date expDate =  getExpDate(bpmTaskDueTime,addDueTime);
		if(BeanUtils.isNotEmpty(expDate)) return expDate.getTime();
		return "0000-00-00 00:00:00";
	}
	
	
	
	/**
	 * 保存任务期限统计信息
	 * @param request
	 * @param response
	 * @param bpmTaskDueTime
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody BpmTaskDueTime bpmTaskDueTime) throws Exception{
		String resultMsg=null;
		try {
			//任务是否已过期
			boolean isExpire = false;
			String id = bpmTaskDueTime.getId();
			if(StringUtil.isNotEmpty(id)){
				BpmTaskDueTime oldBpmTaskDueTime = bpmTaskDueTimeManager.get(id);
				if(BeanUtils.isNotEmpty(oldBpmTaskDueTime)){
					String expireTime = DateFormatUtil.formaDatetTime(oldBpmTaskDueTime.getExpirationDate());
					String nowtime = DateFormatUtil.formaDatetTime(new Date());
					isExpire = DateUtil.compare(expireTime,nowtime);
					if(isExpire){
						resultMsg="当前任务已过期，不能再进行延期操作！";
						writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					}
				}
			}
			if(!isExpire){
				Date expDate =  getExpDate(bpmTaskDueTime,bpmTaskDueTime.getAddDueTime());
				bpmTaskDueTime.setDueTime(bpmTaskDueTime.getDueTime()+bpmTaskDueTime.getAddDueTime());
				bpmTaskDueTime.setExpirationDate(expDate);
				bpmTaskDueTime.setCreateTime(new Date());
				bpmTaskDueTime.setStatus((short)1);
				bpmTaskDueTime.setParentId(bpmTaskDueTime.getId());
				int remainingTime = getRemainingTime(bpmTaskDueTime);
				
				bpmTaskDueTime.setRemainingTime(remainingTime);
				
				bpmTaskDueTimeManager.updateAndSave(bpmTaskDueTime);
				resultMsg="延期成功";
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			resultMsg="对任务期限统计操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	private int getRemainingTime(BpmTaskDueTime bpmTaskDueTime) {
		int remainingTime=0;
		if("caltime".equals(bpmTaskDueTime.getDateType())){
			// getSecondDiff 秒
			remainingTime = TimeUtil.getSecondDiff(new Date(), bpmTaskDueTime.getStartTime())/60;
		}else{
			// getWorkTimeByUser 毫秒
			remainingTime =(int) (iCalendarService.getWorkTimeByUser(bpmTaskDueTime.getUserId(), bpmTaskDueTime.getStartTime(), new Date())/60000);
		}
		remainingTime = bpmTaskDueTime.getDueTime() - remainingTime;
		if(remainingTime<=0){
			remainingTime = 0;
		}
		return remainingTime;
	}



	/**
	 * 批量删除任务期限统计记录
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
			bpmTaskDueTimeManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除任务期限统计成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除任务期限统计失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	private Date getExpDate(BpmTaskDueTime bpmTaskDueTime,int addDueTime){
		Date expDate = null;
		// 日历日
		if("caltime".equals(bpmTaskDueTime.getDateType())){
			expDate =  new Date(TimeUtil.getNextTime(TimeUtil.MINUTE, addDueTime,bpmTaskDueTime.getExpirationDate().getTime()));
		}else{
			expDate = iCalendarService.getEndTimeByUser(bpmTaskDueTime.getUserId(), bpmTaskDueTime.getExpirationDate(), addDueTime);
		}
		
		return expDate;
	}
}

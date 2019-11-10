package com.hotent.mini.controller.system;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.scheduler.SchedulerService;
import com.hotent.base.core.scheduler.TriggerModel;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.api.model.SysJobLog;
import com.hotent.sys.persistence.manager.SysJobLogManager;

@Controller
@RequestMapping("/system/scheduler/")
public class SchedulerController extends GenericController {

	@Resource
	SchedulerService schedulerService;
	@Resource
	SysJobLogManager sysJobLogManager;

	/**
	 * 添加任务
	 * 
	 * @param response
	 * @param request
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addJob")
	public void addJob(HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			String className = RequestUtil.getString(request, "className");
			String jobName = RequestUtil.getString(request, "name");
			String parameterJson = RequestUtil.getString(request, "parameterJson");
			String description = RequestUtil.getString(request, "description");
			boolean isExist = schedulerService.isJobExists(jobName);
			if (isExist) {
				ResultMessage obj = new ResultMessage(ResultMessage.FAIL, "任务名称已经存在，添加失败");
				writeResultMessage(response.getWriter(), obj);
			} else {
				ResultMessage obj = new ResultMessage(ResultMessage.SUCCESS, "添加任务成功");
				if(isExist("className",className)){
					obj = new ResultMessage(ResultMessage.FAIL, "任务列表中已添加该任务类记录，不能多次添加同一任务类！");
				}else{
					boolean isSuccess = schedulerService.addJob(jobName, className, parameterJson, description);
					if(!isSuccess){
						obj = new ResultMessage(ResultMessage.FAIL, "任务添加失败(可能是任务类不存在或属性类型、值不一致！)");
					}
				}
				writeResultMessage(response.getWriter(), obj);
			}
		} catch (ClassNotFoundException ex) {
			ResultMessage obj = new ResultMessage(ResultMessage.FAIL, "添加指定的任务类不存在，添加失败");
			writeResultMessage(response.getWriter(), obj);
		}
	}

	/**
	 * 任务列表
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("schedulerJobList")
	public ModelAndView jobList(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		boolean isInStandbyMode = schedulerService.isInStandbyMode();
		List<JobDetail> list = schedulerService.getJobList();
		ModelAndView mv = new ModelAndView();
		mv.addObject("jobList", list);
		mv.addObject("isStandby", isInStandbyMode);

		return mv;
	}
	
	/**
	 * 定时器任务列表。
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("schedulerJobJson")
	@ResponseBody
	public List<JobDetail> schedulerJobJson(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		List<JobDetail> list = schedulerService.getJobList();
		return list;
	}

	/**
	 * 删除任务
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping("/delJob")
	public void delJob(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException, ClassNotFoundException {
		ResultMessage message = null;
		try {
			String jobName = RequestUtil.getString(request, "jobName");
			schedulerService.delJob(jobName);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除任务成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除任务失败:" + e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 添加计划
	 * 
	 * @param response
	 * @param request
	 * @param viewName
	 * @return
	 * @throws IOException
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	@RequestMapping("/addTrigger")
	public void addTrigger(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException, ParseException {
		String trigName = RequestUtil.getString(request, "name");
		String jobName = RequestUtil.getString(request, "jobName");

		String planJson = RequestUtil.getString(request, "planJson");
		// 判断触发器是否存在
		boolean rtn = schedulerService.isTriggerExists(trigName);
		if (rtn) {
			ResultMessage obj = new ResultMessage(ResultMessage.FAIL, "指定的计划名称已经存在!");
			writeResultMessage(response.getWriter(), obj);
			return;
		}
		try {
			schedulerService.addTrigger(jobName, trigName, planJson);
			ResultMessage obj = new ResultMessage(ResultMessage.SUCCESS, "添加计划成功");
			writeResultMessage(response.getWriter(), obj);
		} catch (SchedulerException e) {
			e.printStackTrace();
			ResultMessage obj = new ResultMessage(ResultMessage.FAIL, e.getMessage());
			writeResultMessage(response.getWriter(), obj);
		}
	}

	/**
	 * 计划列表
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("getTriggersByJob")
	public ModelAndView getTriggersByJob(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		String jobName = RequestUtil.getString(request, "jobName");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/system/scheduler/schedulerTriggerList");
		mv.addObject("jobName", jobName);
		return mv;
	}
	
	@RequestMapping("getTriggersJsonByJob")
	@ResponseBody
	public List<TriggerModel>   getTriggersJsonByJob(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {

		String jobName = RequestUtil.getString(request, "jobName");

		List<Trigger> list = schedulerService.getTriggersByJob(jobName);
		HashMap<String, Trigger.TriggerState> mapState = schedulerService.getTriggerStatus(list);
		
		List<TriggerModel> modelList=new ArrayList<TriggerModel>();
		
		for(Trigger trigger:list){
			String trigName = trigger.getKey().getName();
			TriggerModel model=new TriggerModel();
			model.setJobName(trigger.getJobKey().getName());
			model.setTriggerName(trigName);
			model.setDescription(trigger.getDescription());
			Trigger.TriggerState state = (Trigger.TriggerState) mapState.get(trigName);
			model.setState(state.name());

			modelList.add(model);
		}
		
		return modelList;
	}
	

	/**
	 * 执行任务
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SchedulerException
	 */
	@RequestMapping("executeJob")
	public void executeJob(HttpServletRequest request, HttpServletResponse response) throws IOException, SchedulerException {
		try{
			String jobName = RequestUtil.getString(request, "jobName");
			schedulerService.executeJob(jobName);
			ResultMessage message = new ResultMessage(ResultMessage.SUCCESS, "执行任务成功!");
			writeResultMessage(response.getWriter(), message);
		}
		catch(Exception ex){
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "执行任务失败!");
			writeResultMessage(response.getWriter(), message);
		}
		
	
	}

	/**
	 * 验证类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("validClass")
	public void validClass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String className = RequestUtil.getString(request, "className", "");
		boolean rtn = BeanUtils.validClass(className);
		if (rtn) {
			ResultMessage message = new ResultMessage(ResultMessage.SUCCESS, "验证类成功!");
			writeResultMessage(response.getWriter(), message);
		} else {
			ResultMessage message = new ResultMessage(ResultMessage.FAIL, "验证类失败!");
			writeResultMessage(response.getWriter(), message);
		}
	}

	/**
	 * 删除触发器
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping("/delTrigger")
	public void delTrigger(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException, ClassNotFoundException {
		ResultMessage message = null;
		try {
			String name = RequestUtil.getString(request, "name");
			schedulerService.delTrigger(name);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除计划成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除计划失败:" + e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 启用或禁用
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 * @throws SchedulerException
	 */
	@RequestMapping("/toggleTriggerRun")
	public void toggleTriggerRun(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException {
		ResultMessage message = null;
		String name = RequestUtil.getString(request, "name");
		try {
			schedulerService.toggleTriggerRun(name);
			message = new ResultMessage(ResultMessage.SUCCESS,"");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	
	/**
	 * 任务执行日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {

		QueryFilter queryFilter = getQueryFilter(request);
		String trig_Name_= request.getParameter("trigName");
		String job_Name_ = request.getParameter("jobName");
		if(StringUtil.isNotEmpty(job_Name_)){
			queryFilter.addFilter("job_Name_", job_Name_, QueryOP.EQUAL);
		}
		if(StringUtil.isNotEmpty(trig_Name_)){
			queryFilter.addFilter("trig_Name_", trig_Name_, QueryOP.EQUAL);
		}
		PageList<SysJobLog> list = (PageList<SysJobLog>) sysJobLogManager.query(queryFilter);
		
		return new PageJson(list);
	}

	/**
	 * 删除任务日志
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("delJobLog")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			String[] lAryId = RequestUtil.getStringAryByStr(request, "id");
			sysJobLogManager.removeByIds(lAryId);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除任务日志成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除任务日志失败:" + e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 修改定时器的状态
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("changeStart")
	public void changeStart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String resultMsg = "";
		boolean isStandby = RequestUtil.getBoolean(request, "isStandby");
		try {
			// 如果是挂起状态就启动，否则就挂起
			if (isStandby) {
				schedulerService.start();
				resultMsg = "启动定时器成功!";
			} else {
				schedulerService.shutdown();
				resultMsg = "停止定时器成功!";
			}
			message = new ResultMessage(ResultMessage.SUCCESS, resultMsg);
		} catch (Exception e) {
			e.printStackTrace();
			if (isStandby) {
				resultMsg = "启动定时器失败:";
			} else {
				resultMsg = "停止定时器失败:";
			}
			message = new ResultMessage(ResultMessage.FAIL, resultMsg + e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String name = RequestUtil.getString(request, "name");
		String type = RequestUtil.getString(request, "type");
		return isExist(type,name);
	}
	
	private boolean isExist(String type,String name){
		boolean isExist = false;
		try {
			if("className".equals(type)){
				List<JobDetail> list =schedulerService.getJobList();
				if(BeanUtils.isNotEmpty(list)){
					for(JobDetail job:list){
						String className = job.getJobClass().getName();
						if(name.equals(className)){
							isExist = true;
							break;
						}
					}
				}
			}else if("jobName".equals(type)){
				isExist = schedulerService.isJobExists(name);
			}
		} catch (Exception e) {}
		return isExist;
	}
	
	/**
	 * 添加页面
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("schedulerTriggerAdd")
	public ModelAndView schedulerTriggerAdd(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		String jobName = RequestUtil.getString(request, "jobName");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/system/scheduler/schedulerTriggerAdd");
		mv.addObject("jobName", jobName);
		return mv;
	}
	/**
	 * 计划列表
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("schedulerLogList")
	public ModelAndView schedulerLogList(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		String jobName = RequestUtil.getString(request, "jobName");
		String trigName = RequestUtil.getString(request, "trigName");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/system/scheduler/schedulerLogList");
		if(BeanUtils.isNotEmpty(jobName)){
			mv.addObject("jobName", jobName);
		}
		if(BeanUtils.isNotEmpty(trigName)){
			mv.addObject("trigName", trigName);
		}
		return mv;
	}

}

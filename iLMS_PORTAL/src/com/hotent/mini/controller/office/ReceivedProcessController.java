package com.hotent.mini.controller.office;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.process.task.BpmTaskTurn;
import com.hotent.bpmx.api.service.BpmAgentService;
import com.hotent.bpmx.persistence.manager.BpmCptoReceiverManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTransRecordManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.manager.TaskTurnAssignManager;
import com.hotent.bpmx.persistence.model.BpmCptoReceiver;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.TaskTurnAssign;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.MessageUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;
import com.hotent.sys.util.ContextUtil;

/**
 * 我承接的流程
 * 
 * <pre>
 * 描述：流程中心-我承接的流程
 * 构建组：x5-bpmx-platform
 * 作者:hugh zhuang
 * 邮箱:zhuangxh@jee-soft.cn
 * 日期:2014-9-15-上午11:54:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/office/receivedProcess/")
public class ReceivedProcessController extends GenericController
{
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	BpmAgentService bpmAgentService;
	@Resource
	CopyToManager copyToManager;
	@Resource
	BpmCptoReceiverManager bpmCptoReceiverManager;
	@Resource
	BpmTaskTransRecordManager bpmTaskTransRecordManager;
	@Resource
	TaskTurnAssignManager taskTurnAssignManager;

	/**
	 * 待办事宜
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
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<DefaultBpmTask> list = (PageList<DefaultBpmTask>) bpmTaskManager.getByUserId(userId, queryFilter);
		bpmTaskManager.convertInfo(list);
		return new PageJson(list);
	}

	/**
	 * 已办事宜
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("handledJson")
	public @ResponseBody PageJson handledJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<Map<String,Object>> list = (PageList<Map<String,Object>>)  bpmProcessInstanceManager.getHandledByUserId(userId, queryFilter);
		
		return new PageJson(list);
	}

	/**
	 * 办结事宜
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("completedJson")
	public @ResponseBody PageJson completedJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getCompletedByUserId(userId, queryFilter);
		return new PageJson(list);
	}

	@RequestMapping("taskTurnAssigns")
	@ResponseBody
	public PageJson taskTurnAssigns(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		String taskTurnId = RequestUtil.getString(request, "taskTurnId");
		// 查询列表
		List<TaskTurnAssign> list = bpmTaskTurnManager.getTurnAssignByTaskTurnId(taskTurnId);
		return new PageJson(list);
	}

	/**
	 * 取消代理或转办。
	 * 
	 * @param request
	 * @param reponse
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("doCancelTurn")
	@ResponseBody
	public void doCancelTurn(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		String taskId = RequestUtil.getString(request, "taskId");
		String informType = RequestUtil.getString(request, "messageType");
		String cause = RequestUtil.getString(request, "cause");
		// 查询列表
		try
		{
			bpmAgentService.retrieveTask(taskId, informType, cause);
			writeResultMessage(reponse.getWriter(), "取消流转成功！", ResultMessage.SUCCESS);
		} catch (Exception e)
		{
			writeResultMessage(reponse.getWriter(), "取消任务失败！", e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 取消代理或转办。
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("cancelTurn")
	public ModelAndView cancelTurn(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		String taskId = RequestUtil.getString(request, "taskId");
		List<JmsHandler<JmsVo>> list = MessageUtil.getHanlerList();
		ModelAndView mv = this.getAutoView();

		mv.addObject("handlerList", list);
		mv.addObject("taskId", taskId);

		return mv;
	}

	/**
	 * 获取接收到的抄送数据。
	 * 
	 * @param request
	 * @param reponse
	 * @return PageJson
	 */
	@RequestMapping("receiverCopyToJson")
	public @ResponseBody PageJson receiverCopyToJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		QueryFilter queryFilter = getQueryFilter(request);
		IUser user = ContextUtil.getCurrentUser();
		PageList<CopyTo> list = copyToManager.getReceiverCopyTo(user.getUserId(), queryFilter);
		return new PageJson(list);
	}

	@RequestMapping("realDetail")
	public ModelAndView realDetail(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		String bId = RequestUtil.getString(request, "bId");
		String instId = RequestUtil.getString(request, "instId");
		BpmCptoReceiver model = bpmCptoReceiverManager.get(bId);
		if (model!=null&&model.getIsRead() == 0)
		{
			model.setIsRead((short) 1);
			bpmCptoReceiverManager.update(model);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/flow/instance/instanceGet?id=" + instId);
		return view;
	}
	
	/**
	 * 流转说明
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pendingTransDetail")
	public ModelAndView pendingTransDetail(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		String taskId = RequestUtil.getString(request, "taskId");
		BpmTaskTransRecord transRecord = bpmTaskTransRecordManager.getByTaskId(taskId);
		ModelAndView mv = new ModelAndView("/office/initiatedProcess/pendingTransDetail");
		return mv.addObject("record", transRecord);
	}
	
	/**
	 * 转办明细
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delegateDetail")
	public ModelAndView delegateDetail(HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		String taskId = RequestUtil.getString(request, "taskId");
		BpmTaskTurn taskTurn = bpmTaskTurnManager.getByTaskId(taskId);
		List<TaskTurnAssign> list = taskTurnAssignManager.getByTaskTurnId(taskTurn.getId());
		ModelAndView mv = new ModelAndView("/office/initiatedProcess/delegateDetail");
		return mv.addObject("turnAssignList", list);
	}

}

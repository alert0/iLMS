package com.hotent.mini.controller.office;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.ExceptionUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.api.service.TaskTransService;
import com.hotent.bpmx.persistence.dao.ActTaskDao;
import com.hotent.bpmx.persistence.dao.BpmProcessInstanceDao;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTransRecordManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.manager.CopyToManager;
import com.hotent.bpmx.persistence.model.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.jms.MessageUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;
import com.hotent.sys.util.ContextUtil;

/**
 * 我发起的流程
 * 
 * <pre>
 * 描述：流程中心-我发起的流程
 * 构建组：x5-bpmx-platform
 * 作者:hugh zhuang
 * 邮箱:zhuangxh@jee-soft.cn
 * 日期:2014-9-15-上午11:54:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/office/initiatedProcess/")
public class InitiatedProcessController extends GenericController {
	@Resource
	IUserService userService;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	CopyToManager copyToManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	TaskTransService transService;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	BpmTaskActionService bpmTaskActionService;
	@Resource
	BpmProcessInstanceDao bpmProcessInstanceDao;
	@Resource
	ActTaskDao actTaskDao;
	@Resource
	BpmTaskTransRecordManager taskTransRecordManager;
	/**
	 * 新建流程
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("newProcessJson")
	public @ResponseBody PageJson newProcessJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addFilter("is_main_", "Y", QueryOP.EQUAL);
		queryFilter.addFilter("status_", "deploy", QueryOP.EQUAL);
		queryFilter.addParamsFilter("bpmDefAuthorizeRightType", BPMDEFAUTHORIZE_RIGHT_TYPE.START);

		// 查询列表
		PageList<DefaultBpmDefinition> list = (PageList<DefaultBpmDefinition>) bpmDefinitionManager.queryList(queryFilter);
		return new PageJson(list);
	}

	/**
	 * 我的请求
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
		// 查询列表
		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyRequestByUserId(userId, queryFilter);

		return new PageJson(list);
	}

	/**
	 * 我的办结
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
		// 查询列表
		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getMyCompletedByUserId(userId, queryFilter);

		return new PageJson(list);
	}

	/**
	 * 我的草稿
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myDraftJson")
	public @ResponseBody PageJson myDraftJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<DefaultBpmProcessInstance> list = (PageList<DefaultBpmProcessInstance>) bpmProcessInstanceManager.getDraftsByUserId(userId, queryFilter);

		return new PageJson(list);
	}

	/**
	 * 删除草稿
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("removeDraft")
	public void removeDraft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			bpmProcessInstanceManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除草稿成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除草稿失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	// 撤消
	@RequestMapping("revoke")
	public ModelAndView revoke(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String instId = RequestUtil.getString(request, "instId");
		// 从已办中撤回的操作
		String isHandRevoke = RequestUtil.getString(request, "isHandRevoke");

		List<JmsHandler<JmsVo>> list = MessageUtil.getHanlerList();
		ModelAndView mv = this.getAutoView();
		if (StringUtil.isNotEmpty(isHandRevoke)) {
			mv.addObject("isHandRevoke", "true");
		} else {
			mv.addObject("isHandRevoke", "false");
		}
		mv.addObject("handlerList", list);
		mv.addObject("instId", instId);

		return mv;
	}

	@RequestMapping("doRevoke")
	public void doRevoke(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String instId = RequestUtil.getString(request, "instId");
		String messageType = RequestUtil.getString(request, "messageType");
		String cause = RequestUtil.getString(request, "cause");
		// 是否是从已办中撤回的操作
		String isHandRevoke = RequestUtil.getString(request, "isHandRevoke");
		if (StringUtil.isNotEmpty(isHandRevoke)&&isHandRevoke.equals("true")) {
			ResultMessage result = bpmProcessInstanceManager.revokeTask(instId, messageType, cause);
			writeResultMessage(reponse.getWriter(), result.getMessage(), result.getResult());
		} else {
			ResultMessage result = bpmProcessInstanceManager.revokeInstance(instId, messageType, cause);
			writeResultMessage(reponse.getWriter(), result.getMessage(), result.getResult());
		}

	}
	
	
	/**
	 * 由我发出的抄送。
	 * 
	 * @param request
	 * @param reponse
	 * @return PageJson
	 */
	@RequestMapping("myCopyToJson")
	@ResponseBody
	public PageJson myCopyToJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<CopyTo> list = (PageList<CopyTo>) copyToManager.getMyCopyTo(userId, queryFilter);

		return new PageJson(list);
	}

	/**
	 * 查询我流转出去的任务。
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("myTransJson")
	@ResponseBody
	public PageJson myTransJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {

		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<DefaultBpmTask> list = bpmTaskManager.getMyTransTask(userId, queryFilter);

		return new PageJson(list);
	}
	
	/**
	 * 查询我的流转记录。
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("myTransRecordJson")
	@ResponseBody
	public PageJson myTransRecordJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {

		QueryFilter queryFilter = getQueryFilter(request);
		// 查询列表
		PageList<BpmTaskTransRecord> list = (PageList<BpmTaskTransRecord>)taskTransRecordManager.getMyTransRecord(queryFilter);

		return new PageJson(list);
	}

	/**
	 * 撤销流转页面。
	 * 
	 * @param request
	 * @param reponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("revokeTrans")
	public ModelAndView revokeTrans(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		List<JmsHandler<JmsVo>> list = MessageUtil.getHanlerList();
		ModelAndView mv = this.getAutoView();

		mv.addObject("handlerList", list);
		mv.addObject("taskId", taskId);

		return mv;
	}

	/**
	 * 处理撤销流转任务。
	 * 
	 * @param request
	 * @param reponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("doRevokeTrans")
	public void doRevokeTrans(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");

		String messageType = RequestUtil.getString(request, "messageType");
		String cause = RequestUtil.getString(request, "cause");

		ResultMessage result = null;
		try {
			transService.withDraw(taskId, messageType, cause);
			result = ResultMessage.getSuccess("流转任务成功取回!");
		} catch (Exception ex) {
			String msg = ExceptionUtil.getExceptionMessage(ex);
			result = ResultMessage.getFail(msg);
		}
		reponse.getWriter().print(result);

	}

	/**
	 * 转办代理事宜
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delegateJson")
	@ResponseBody
	public PageJson delegateJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		// 查询列表
		PageList<DefaultBpmTaskTurn> list = (PageList<DefaultBpmTaskTurn>) bpmTaskTurnManager.getMyDelegate(userId, queryFilter);
		return new PageJson(list);
	}
}
package com.hotent.restful.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.bpmx.webservice.model.StartResult;
import com.hotent.restful.params.DoEndParamObject;
import com.hotent.restful.params.DoNextParamObject;
import com.hotent.restful.params.StartFlowParamObject;
import com.hotent.restful.params.CommonResult;
import com.hotent.restful.vo.BpmCheckOpinionVo;

/**
 * 流程相关操作
 * 
 * @author liangqf 2017-09-05
 *
 */
@RestController
@RequestMapping("/restful/flowoperation/")
@Api(tags = "FlowOperationRestfulController", value="流程操作接口")
public class FlowOperationRestfulController {

	@Resource
	IProcessService iProcessService;

	/**
	 * 客户端启动流程
	 * @param startFlowParamObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "start", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ApiOperation(value = "客户端启动流程", httpMethod = "POST", notes = "客户端启动流程")
	public StartResult start(@ApiParam(name = "startFlowParamObject", value = "流程启动参数", required=true) @RequestBody StartFlowParamObject startFlowParamObject)
			throws Exception {
		return iProcessService.start(startFlowParamObject);
	}

	/**
	 * 客户端提交数据,执行流程往下跳转
	 * @param doNextParamObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "doNext", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ApiOperation(value = "客户端提交数据,执行流程往下跳转", httpMethod = "POST", notes = "客户端提交数据,执行流程往下跳转")
	public BasicResult doNext(
			@ApiParam(name = "doNextParamObject", value = "流程向下执行对象", required=true) @RequestBody DoNextParamObject doNextParamObject)
			throws Exception {
		return iProcessService.doNext(doNextParamObject);
	}

	/**
	 * 人工终止流程
	 * @param doEndParamObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "doEndProcess", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "人工终止流程", httpMethod = "POST", notes = "人工终止流程")
	public CommonResult<String> doEndProcess(
			@ApiParam(name = "doEndParamObject", value = "流程终止对象") @RequestBody DoEndParamObject doEndParamObject)
			throws Exception {
		return iProcessService.doEndProcess(doEndParamObject);
	}

	/**
	 * 按流程实例ID或任务实例ID取得某个流程的审批历史
	 * @param instanId 实例id
	 * @param taskId 任务id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getHistoryOpinion", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "按流程实例ID或任务实例ID取得某个流程的审批历史", httpMethod = "GET", notes = "按流程实例ID或任务实例ID取得某个流程的审批历史")
	public List<BpmTaskOpinion> getHistoryOpinion(
			@ApiParam(name = "instanId", value = "流程实例id",required=false) @RequestParam(required=false) String instanId,
			@ApiParam(name = "taskId", value = "任务id" ,required=false) @RequestParam(required=false) String taskId)
			throws Exception {
		return iProcessService.getHistoryOpinion(instanId,taskId);
	}
	
	/**
	 * 按Activiti实例Id取得对应流程的审批历史，act_ru_task表的id值
	 * @param actTaskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getProcessOpinionByActInstId",method=RequestMethod.GET,produces={"application/json; charset=utf-8"})
	@ApiOperation(value="按Activiti实例Id取得对应流程的审批历史",httpMethod="GET",notes="按Activiti实例Id取得对应流程的审批历史")
	public List<BpmCheckOpinionVo> getProcessOpinionByActInstId(@ApiParam(name="actTaskId",value="Activiti任务Id",required=true) @RequestParam String actTaskId) throws Exception {
		return iProcessService.getProcessOpinionByActInstId(actTaskId);
	}

	/**
	 * 根据任务ID获取可驳回的节点
	 * @param taskId 任务id
	 * @param rejectType 返回方式
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getEnableRejectNode", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据任务ID获取可驳回的节点", httpMethod = "POST", notes = "根据任务ID获取可驳回的节点，rejectType驳回方式：direct直来直往，normal按照流程图执行")
	public List<JSONObject> getEnableRejectNode(@ApiParam(name = "taskId", value = "任务id") @RequestParam String taskId,
			@ApiParam(name = "rejectType", allowableValues = "direct,normal", value = "返回方式") @RequestParam String rejectType)
			throws Exception {
		return iProcessService.getEnableRejectNode(taskId, rejectType);
	}

	/**
	 * 根据任务ID或流程实例ID获取BusinessKey（流程表单为URL表单的情况）
	 * @param instanId 实例id
	 * @param taskId 任务id
	 * @return
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "getBusinessKey", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据任务ID或流程实例ID获取BusinessKey（流程表单为URL表单的情况）", httpMethod = "GET", notes = "根据任务ID或流程实例ID获取BusinessKey（流程表单为URL表单的情况）")
	public CommonResult<String> getBusinessKey(@ApiParam(name = "instanId", value = "流程实例id",required=false) @RequestParam(required=false) String instanId,
			@ApiParam(name = "taskId", value = "任务id",required=false) @RequestParam(required=false) String taskId) throws NullPointerException {
		return iProcessService.getBusinessKey(instanId,taskId);
	}

	/**
	 * 根据BussinessKey获取流程实例ID
	 * @param businessKey
	 * @return
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "getProcInstId", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据BussinessKey获取流程实例ID", httpMethod = "POST", notes = "根据BussinessKey获取流程实例ID")
	public CommonResult<String> getProcInstId(@ApiParam(name = "businessKey", value = "businessKey") @RequestParam String businessKey)
			throws NullPointerException {
		return iProcessService.getProcInstId(businessKey);
	}
	
	/**
	 * 通过表单保存草稿
	 * @param saveFlowParamObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveDraft", method = RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value="通过表单保存草稿",httpMethod="POST",notes="通过表单保存草稿")
	public CommonResult<String> saveDraft(@ApiParam(name="saveFlowParamObject",value="流程执行相关对象") @RequestBody StartFlowParamObject saveFlowParamObject) throws Exception{
		return iProcessService.saveDraft(saveFlowParamObject);
	}

}

package com.hotent.restful.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.webservice.api.IFlowService;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.restful.params.BaseQueryFilter;
import com.hotent.restful.params.DefOtherParam;
import com.hotent.restful.params.RestPageList;
import com.hotent.restful.params.TaskOrInstanQueryFilter;
import com.hotent.restful.params.CommonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/restful/bpmdefinition/")
@Api(tags = "BpmDefinitionRestfulController")
public class BpmDefinitionRestfulController extends RestfulBaseController {

	@Resource
	IFlowService iFlowService;
	@Resource
	IProcessService iProcessService;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;

	/**
	 *  获取用户可发起的流程
	 * @param response
	 * @param taskOrInstanQueryFilter
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMyFlowList", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户可发起的流程", httpMethod = "POST", notes = "获取用户可发起的流程")
	public List<DefaultBpmDefinition> getMyFlowList(HttpServletResponse response,
			@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,
			@ApiParam(name = "typeId", value = "流程分类id",required=false) @RequestParam(required=false) String typeId) throws Exception {
		return iFlowService.getMyFlowList(taskOrInstanQueryFilter,typeId);
	}

	/**
	 * 根据流程定义ID或流程定义KEY获取流程变量
	 * @param response
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getWorkflowVar", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据流程定义ID或流程定义KEY获取流程变量", httpMethod = "POST", notes = "根据流程定义ID或流程定义KEY获取流程变量，json格式：{\"defId\":\"流程定义id\",\"defKey\":\"流程定义key\"}")
	public List<BpmVariableDef> getWorkflowVar(HttpServletResponse response,
			@ApiParam(required = true, name = "json", value = "json格式字符串") @RequestParam String json) throws Exception {
		return iFlowService.getWorkflowVar(json);
	}

	/**
	 * 根据流程定义ID获取特定流程信息
	 * @param defId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getBpmDefinitionByDefId", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据流程定义ID获取流程定义实体", httpMethod = "POST", notes = "根据流程定义ID获取流程定义实体")
	public DefaultBpmDefinition getBpmDefinitionByDefId(
			@ApiParam(required = true, name = "defId", value = "流程定义ID") @RequestParam String defId)
			throws Exception {
		return iFlowService.getBpmDefinitionByDefId(defId);
	}
	
	/**
	 * 设置流程其他参数
	 * @param defOtherParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="setDefOtherParam",method=RequestMethod.POST,produces={"application/json; charset=utf-8" })
	@ApiOperation(value="设置流程其他参数",httpMethod="POST",notes="设置流程其他参数")
	public CommonResult<String> setDefOtherParam(
			@ApiParam(required = true, name = "defOtherParam", value = "流程定义其他参数") @RequestBody DefOtherParam defOtherParam)
			throws Exception {
		return iProcessService.setDefOtherParam(defOtherParam);
	}
	
	/**
	 * 查询报告状态
	 * @param taskQueryFilterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getBpmDefList", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "流程模版(流程列表)查询", httpMethod = "POST", notes = "流程模版(流程列表)查询")
	public RestPageList<DefaultBpmDefinition> getBpmDefList(HttpServletResponse response,@ApiParam(required = true, name = "baseQueryFilter", value = "查询参数对象") @RequestBody BaseQueryFilter baseQueryFilter) throws Exception{
		PageList<DefaultBpmDefinition> list = iFlowService.getBpmDefList(baseQueryFilter);
		RestPageList<DefaultBpmDefinition> restPageList = new RestPageList<DefaultBpmDefinition>(list);
		return restPageList;
	}

}

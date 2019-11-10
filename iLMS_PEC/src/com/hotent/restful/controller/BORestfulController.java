package com.hotent.restful.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hotent.bpmx.webservice.api.IBoService;
import com.hotent.restful.params.CommonResult;

/**
 * 
 * @author liyg
 *
 */
@RestController
@RequestMapping(value="/restful/bo/",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags="BORestfulController")
public class BORestfulController extends RestfulBaseController {
	
	@Resource
	private IBoService boServive;
	
	/**
	 * 获取bo 数据  
	 * @param defId
	 * @param proInstId
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="getBOData")
	@ApiOperation(value = "获取BO数据 ", httpMethod = "GET", notes = "获取BO数据<br/>参数 proInstId（流程实例id）、defId（流程定义id）必须传入一个")
	public CommonResult<JSONObject> getBOData(@ApiParam(name="proInstId",value="流程实例id") @RequestParam(required=false) String proInstId,@ApiParam(name="defId",value="流程定义id") @RequestParam(required=false) String defId
			,@ApiParam(name="nodeId",value="任务节点id") @RequestParam(required=false) String nodeId) throws Exception{
		return boServive.getBoData(proInstId,defId,nodeId);
	}
	
	@PostMapping(value="updataBoData")
	@ApiOperation(value = "更新BO数据 ", httpMethod = "POST", notes = "更新BO数据")
	public CommonResult<String> updataBoData(@ApiParam(name="proInstId",required=true) @RequestParam String proInstId,@ApiParam(name="boJson",required=true) @RequestBody String boJson){
		return boServive.updataBoData(proInstId, boJson);
	}
	

}

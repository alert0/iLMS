package com.hotent.bpmx.webservice.api;


import com.alibaba.fastjson.JSONObject;
import com.hotent.restful.params.CommonResult;

/**
 * bo 数据操作的接口
 * @author liyg
 *
 */
public interface IBoService {
	
	public CommonResult<JSONObject> getBoData(String proInstId,String defId,String nodeId) throws Exception;

	public CommonResult<String> updataBoData(String proInstId, String boJson)
			throws RuntimeException;

}

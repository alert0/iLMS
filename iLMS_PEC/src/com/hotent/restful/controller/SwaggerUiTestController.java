package com.hotent.restful.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.User;
import com.hotent.restful.params.FacadeObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;

//@RestController
//@RequestMapping("/swaggerui/test/")
//@Api(tags="SwaggerUiTestController")
public class SwaggerUiTestController{
	
	@Resource
	UserManager userManager;
	
	/**
	 * 通过帐号查询用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "getByAccount", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//	@ApiOperation(value = "通过帐号查询用户信息", httpMethod = "POST", notes = "暂无")
	public String getByAccount(@ApiParam(required = true, name = "facadeObject", value = "测试对象")@RequestBody FacadeObject facadeObject) throws Exception {
		User u = userManager.getByAccount("admin");
		JSONObject object = JSONObject.fromObject(u);
		return object.toString();
	}

}

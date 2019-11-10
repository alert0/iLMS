package com.hotent.restful.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotent.org.restful.api.IUserOrgService;
import com.hotent.restful.params.UserRestfulObject;
import com.hotent.restful.params.CommonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户组织关联数据操作
 * @author liangqf
 *
 */
@RestController
@RequestMapping("/restful/userOrgRelated/")
@Api(tags="UserOrgRelatedRestfulController")
public class UserOrgRelatedRestfulController {
	
	@Resource
	IUserOrgService iUserOrgService;
	
	/**
	 * 添加用户上下级关系
	 * @param orgCode
	 * @param upUserAccount
	 * @param underUserAccount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addUnderUser",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加用户上下级关系", httpMethod = "POST", notes = "添加用户上下级关系")
	public CommonResult<String> addUnderUser(
			@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode,
			@ApiParam(name = "upUserAccount", value = "上级用户帐号", required = true) @RequestParam String upUserAccount,
			@ApiParam(name = "underUserAccount", value = "下级用户账号", required = true) @RequestParam String underUserAccount)
			throws Exception {
		return iUserOrgService.addUnderUser(orgCode, upUserAccount, underUserAccount);
	}
	
	/**
	 * 根据上级用户帐号与下级用户帐号，删除上下级关系
	 * @param upUserAccount
	 * @param underUserAccount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="removeUnderUser",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "删除上下级关系", httpMethod = "DELETE", notes = "根据上级用户帐号与下级用户帐号，删除上下级关系")
	public CommonResult<String> removeUnderUser(
			@ApiParam(name = "upUserAccount", value = "上级用户帐号", required = true) @RequestParam String upUserAccount,
			@ApiParam(name = "underUserAccount", value = "下级用户账号", required = true) @RequestParam String underUserAccount)
			throws Exception {
		return iUserOrgService.removeUnderUser(upUserAccount, underUserAccount);
	}
	
	/**
	 * 添加用户角色关系
	 * @param account
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addUserRoleRel",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加用户角色关系", httpMethod = "POST", notes = "添加用户角色关系")
	public CommonResult<String> addUserRoleRel(
			@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,
			@ApiParam(name = "roleCode", value = "角色代码", required = true) @RequestParam String roleCode)
			throws Exception {
		return iUserOrgService.addUserRoleRel(account, roleCode);
	}
	
	/**
	 * 删除用户角色关系
	 * @param account
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="removeUserRoleRel",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "删除用户角色关系", httpMethod = "DELETE", notes = "删除用户角色关系")
	public CommonResult<String> removeUserRoleRel(
			@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,
			@ApiParam(name = "roleCode", value = "角色代码", required = true) @RequestParam String roleCode)
			throws Exception {
		return iUserOrgService.removeUserRoleRel(account, roleCode);
	}
	
	/**
	 * 添加用户岗位关系
	 * @param account
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addUserRel",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加用户岗位关系", httpMethod = "POST", notes = "添加用户岗位关系")
	public CommonResult<String> addUserRel(
			@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,
			@ApiParam(name = "relCode", value = "岗位代码", required = true) @RequestParam String relCode)
			throws Exception {
		return iUserOrgService.addUserRel(account, relCode);
	}
	
	/**
	 * 删除用户岗位关系
	 * @param account
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="removeUserRel",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "删除用户岗位关系", httpMethod = "DELETE", notes = "删除用户岗位关系")
	public CommonResult<String> removeUserRel(
			@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,
			@ApiParam(name = "relCode", value = "岗位代码", required = true) @RequestParam String relCode)
			throws Exception {
		return iUserOrgService.removeUserRel(account, relCode);
	}
	
	/**
	 * 组织加入用户
	 * @param account
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addOrgUser",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "组织加入用户", httpMethod = "POST", notes = "组织加入用户")
	public CommonResult<String> addOrgUser(
			@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,
			@ApiParam(name = "orgCode", value = "组织码", required = true) @RequestParam String orgCode)
			throws Exception {
		return iUserOrgService.addOrgUser(orgCode, account);
	}
	
	/**
	 * 组织新增用户，创建新用户，并保存用户与组织的关系
	 * @param orgCode
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addNewUserToOrg",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "组织新增用户", httpMethod = "POST", notes = "组织新增用户，创建新用户，并保存用户与组织的关系")
	public CommonResult<String> addNewUserToOrg(
			@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode,
			@ApiParam(name = "user", value = "用户对象", required = true) @RequestBody UserRestfulObject user)
			throws Exception {
		return iUserOrgService.addOrgUser(orgCode, user);
	}
	
	/**
	 * 删除用户组织关系
	 * @param account
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delOrgUser",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "删除用户组织关系", httpMethod = "DELETE", notes = "删除用户组织关系")
	public CommonResult<String> delOrgUser(
			@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,
			@ApiParam(name = "orgCode", value = "组织码", required = true) @RequestParam String orgCode)
			throws Exception {
		return iUserOrgService.delOrgUser(orgCode, account);
	}

}

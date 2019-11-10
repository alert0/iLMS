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

import com.hotent.org.api.model.IGroup;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.User;
import com.hotent.org.restful.api.IUserOrgService;
import com.hotent.restful.params.DemensionRestfulObject;
import com.hotent.restful.params.OrgAuthRestfulObject;
import com.hotent.restful.params.OrgRelRestfulObject;
import com.hotent.restful.params.OrgReldefRestfulObject;
import com.hotent.restful.params.OrgRestfulObject;
import com.hotent.restful.params.ParamsRestfulObject;
import com.hotent.restful.params.RoleRestfulObject;
import com.hotent.restful.params.UserOrgQueryFilter;
import com.hotent.restful.params.UserRestfulObject;
import com.hotent.restful.params.CommonResult;

/**
 * 用户组织模块接口
 * @author liangqf
 *
 */
@RestController
@RequestMapping("/restful/userOrg/")
@Api(tags="UserOrgRestfulController")
public class UserOrgRestfulController {
	
	@Resource
	IUserOrgService iUserOrgService;
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addUser",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加用户", httpMethod = "POST", notes = "添加用户")
	public CommonResult<String> addUser(@ApiParam(name="user",value="用户参数对象") @RequestBody UserRestfulObject user) throws Exception{
		return iUserOrgService.addUser(user);
	}
	
	/**
	 * 更新用户，不会更新id、密码、帐号信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateUser",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "更新用户", httpMethod = "POST", notes = "更新用户，不会更新id、密码、帐号信息")
	public CommonResult<String> updateUser(@ApiParam(name="user",value="用户参数对象") @RequestBody  UserRestfulObject user) throws Exception{
		return iUserOrgService.updateUser(user);
	}
	
	/**
	 * 根据用户帐号删除用户
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteUser",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据用户帐号删除用户", httpMethod = "DELETE", notes = "根据用户帐号删除用户")
	public CommonResult<String> deleteUser(@ApiParam(name="account",value="用户帐号") @RequestParam  String account) throws Exception{
		return iUserOrgService.deleteUser(account);
	}
	
	/**
	 * 添加组织
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addOrg",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加组织", httpMethod = "POST", notes = "添加组织")
	public CommonResult<String> addOrg(@ApiParam(name="org",value="组织参数对象") @RequestBody OrgRestfulObject org) throws Exception{
		return iUserOrgService.addOrg(org);
	}
	
	/**
	 * 更新组织，不会更新id、组织代码、父组织信息及维度
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateOrg",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "更新组织", httpMethod = "POST", notes = "更新组织，不会更新id、组织代码、父组织信息及维度")
	public CommonResult<String> updateOrg(@ApiParam(name="org",value="组织参数对象") @RequestBody OrgRestfulObject org) throws Exception{
		return iUserOrgService.updateOrg(org);
	}
	
	/**
	 * 根据组织代码删除组织，其子组织一并删除
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteOrg",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据组织代码删除组织", httpMethod = "DELETE", notes = "根据组织代码删除组织，其子组织一并删除")
	public CommonResult<String> deleteOrg(@ApiParam(name="orgCode",value="组织代码") @RequestParam  String orgCode) throws Exception{
		return iUserOrgService.deleteOrg(orgCode);
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addRole",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加角色", httpMethod = "POST", notes = "添加角色")
	public CommonResult<String> addRole(@ApiParam(name="role",value="角色参数对象") @RequestBody RoleRestfulObject role) throws Exception{
		return iUserOrgService.addRole(role);
	}
	
	/**
	 * 更新角色，不会更新id、角色代码
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateRole",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "更新角色", httpMethod = "POST", notes = "更新角色，不会更新id、角色代码")
	public CommonResult<String> updateRole(@ApiParam(name="role",value="角色参数对象") @RequestBody RoleRestfulObject role) throws Exception{
		return iUserOrgService.updateRole(role);
	}
	
	/**
	 * 根据角色代码删除角色
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteRole",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据角色代码删除角色", httpMethod = "DELETE", notes = "根据角色代码删除角色")
	public CommonResult<String> deleteRole(@ApiParam(name="code",value="角色代码") @RequestParam  String code) throws Exception{
		return iUserOrgService.deleteRole(code);
	}
	
	/**
	 * 获取系统所有角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getAllRole",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取系统所有角色", httpMethod = "GET", notes = "获取系统所有角色")
	public JSONObject getAllRole() throws Exception{
		return iUserOrgService.getAllRole();
	}
	
	/**
	 * 增加职务
	 * @param reldef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addOrgReldef",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "增加职务", httpMethod = "POST", notes = "增加职务")
	public CommonResult<String> addOrgReldef(@ApiParam(name="reldef",value="职务参数对象") @RequestBody OrgReldefRestfulObject reldef) throws Exception{
		return iUserOrgService.addOrgReldef(reldef);
	}
	
	/**
	 * 更新职务，不会更新id、职务代码
	 * @param reldef
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateOrgReldef",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "更新职务", httpMethod = "POST", notes = "更新职务，不会更新id、职务代码")
	public CommonResult<String> updateOrgReldef(@ApiParam(name="reldef",value="职务参数对象") @RequestBody OrgReldefRestfulObject reldef) throws Exception{
		return iUserOrgService.updateOrgReldef(reldef);
	}
	
	/**
	 * 根据职务代码删除职务
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteOrgReldef",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据职务代码删除职务", httpMethod = "DELETE", notes = "根据职务代码删除职务")
	public CommonResult<String> deleteOrgReldef(@ApiParam(name="code",value="职务代码") @RequestParam  String code) throws Exception{
		return iUserOrgService.deleteOrgReldef(code);
	}
	
	/**
	 * 获取系统所有职务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getAllReldef",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取系统所有职务", httpMethod = "GET", notes = "获取系统所有职务")
	public JSONObject getAllReldef() throws Exception{
		return iUserOrgService.getAllReldef();
	}
	
	/**
	 * 增加维度
	 * @param demension
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addDemension",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "增加维度", httpMethod = "POST", notes = "增加维度")
	public CommonResult<String> addDemension(@ApiParam(name="demension",value="维度参数对象") @RequestBody DemensionRestfulObject demension) throws Exception{
		return iUserOrgService.addDemension(demension);
	}
	
	/**
	 * 更新维度
	 * @param demension
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateDemension",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "更新维度", httpMethod = "POST", notes = "更新维度")
	public CommonResult<String> updateDemension(@ApiParam(name="demension",value="维度参数对象") @RequestBody DemensionRestfulObject demension) throws Exception{
		return iUserOrgService.updateDemension(demension);
	}
	
	/**
	 * 根据维度id删除维度
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteDemension",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据维度id删除维度", httpMethod = "DELETE", notes = "根据维度id删除维度")
	public CommonResult<String> deleteDemension(@ApiParam(name="id",value="维度id") @RequestParam  String id) throws Exception{
		return iUserOrgService.deleteDemension(id);
	}
	
	/**
	 * 获取所有维度
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getAllDemension",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取所有维度", httpMethod = "GET", notes = "获取所有维度")
	public JSONObject getAllDemension() throws Exception{
		return iUserOrgService.getAllDemension();
	}
	
	/**
	 * 添加岗位
	 * @param orgCode
	 * @param reldefCode
	 * @param rel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addOrgRel",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加岗位", httpMethod = "POST", notes = "添加岗位")
	public CommonResult<String> addOrgRel(@ApiParam(name = "orgCode", value = "组织代码",required=true) @RequestParam String orgCode,
			@ApiParam(name = "reldefCode", value = "职务代码",required=true) @RequestParam String reldefCode,
			@ApiParam(name = "rel", value = "岗位参数对象") @RequestBody OrgRelRestfulObject rel) throws Exception {
		return iUserOrgService.addOrgRel(orgCode,reldefCode,rel);
	}
	
	/**
	 * 修改岗位
	 * @param rel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateOrgRel",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "修改岗位", httpMethod = "POST", notes = "修改岗位")
	public CommonResult<String> updateOrgRel(@ApiParam(name="rel",value="岗位参数对象") @RequestBody OrgRelRestfulObject rel) throws Exception{
		return iUserOrgService.updateOrgRel(rel);
	}
	
	/**
	 * 根据岗位代码删除岗位
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteOrgRel",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据岗位代码删除岗位", httpMethod = "DELETE", notes = "根据岗位代码删除岗位")
	public CommonResult<String> deleteOrgRel(@ApiParam(name="relCode",value="岗位代码") @RequestParam String relCode) throws Exception{
		return iUserOrgService.deleteOrgRel(relCode);
	}
	
	/**
	 * 获取所有岗位信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getAllOrgRel",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取所有岗位信息", httpMethod = "GET", notes = "获取所有岗位信息")
	public JSONObject getAllOrgRel() throws Exception{
		return iUserOrgService.getAllOrgRel();
	}
	
	/**
	 * 添加分级管理
	 * @param userAccount
	 * @param orgCode
	 * @param orgAuth
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addOrgAuth",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加分级管理", httpMethod = "POST", notes = "添加分级管理")
	public CommonResult<String> addOrgAuth(@ApiParam(name = "userAccount", value = "用户帐号",required=true) @RequestParam String userAccount,
			@ApiParam(name = "orgCode", value = "组织代码",required=true) @RequestParam String orgCode,
			@ApiParam(name = "orgAuth", value = "分级管理参数对象") @RequestBody OrgAuthRestfulObject orgAuth) throws Exception {
		return iUserOrgService.addOrgAuth(userAccount,orgCode,orgAuth);
	}
	
	/**
	 * 修改分级管理
	 * @param orgAuth
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateOrgAuth",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "修改分级管理", httpMethod = "POST", notes = "修改分级管理")
	public CommonResult<String> updateOrgAuth(@ApiParam(name="orgAuth",value="分级管理参数对象") @RequestBody OrgAuthRestfulObject orgAuth) throws Exception{
		return iUserOrgService.updateOrgAuth(orgAuth);
	}
	
	/**
	 * 根据根据id删除分级管理
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteAuth",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据根据id删除分级管理", httpMethod = "DELETE", notes = "根据根据id删除分级管理")
	public CommonResult<String> deleteAuth(@ApiParam(name="id",value="id") @RequestParam String id) throws Exception{
		return iUserOrgService.deleteAuth(id);
	}
	
	/**
	 * 获取所有分级管理信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getAllAuth",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取所有分级管理信息", httpMethod = "GET", notes = "获取所有分级管理信息")
	public JSONObject getAllAuth() throws Exception{
		return iUserOrgService.getAllAuth();
	}
	
	/**
	 * 添加或者修改用户组织参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addOrUpdateParams",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "添加或者修改用户组织参数", httpMethod = "POST", notes = "添加或者修改用户组织参数")
	public CommonResult<String> addOrUpdateParams(@ApiParam(name="param",value="用户组织参数对象") @RequestBody ParamsRestfulObject param) throws Exception{
		return iUserOrgService.addOrUpdateParams(param);
	}
	
	/**
	 * 根据别名删除用户组织参数
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteParams",method=RequestMethod.DELETE, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "根据别名删除用户组织参数", httpMethod = "DELETE", notes = "根据别名删除用户组织参数")
	public CommonResult<String> deleteParams(@ApiParam(name="alias",value="参数别名",required=true) @RequestParam String alias) throws Exception{
		return iUserOrgService.deleteParams(alias);
	}
	
	/**
	 * 获取用户组织参数列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getAllParams",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户组织参数列表", httpMethod = "GET", notes = "获取用户组织参数列表")
	public JSONObject getAllParams() throws Exception{
		return iUserOrgService.getAllParams();
	}
	
	/**
	 *  编辑用户、组织属性值
	 * @param code
	 * @param type
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="editParamValue",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "编辑用户、组织属性值", httpMethod = "POST", notes = "编辑用户、组织属性值")
	public CommonResult<String> editParamValue(
			@ApiParam(name = "code", value = "用户帐号或组织代码", required = true) @RequestParam String code,
			@ApiParam(name = "type", value = "类型", allowableValues = "user,org", required = true) @RequestParam String type,
			@ApiParam(name = "list", value = "参数键、值对，[{alias:\"参数key\",value:\"具体值\"},...]", required = true) @RequestBody List<com.alibaba.fastjson.JSONObject> list)
			throws Exception {
		return iUserOrgService.editParamValue(code,type,list);
	}
	
	/**
	 * 通过id获取组织信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgById",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取组织信息", httpMethod = "GET", notes = "通过id获取组织信息")
	public Org getOrgById(@ApiParam(name = "id", value = "组织id", required = true) @RequestParam String id) throws Exception{
		return iUserOrgService.getOrgById(id);
	}
	
	/**
	 * 通过id获取岗位信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgRelById",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取岗位信息", httpMethod = "GET", notes = "通过id获取岗位信息")
	public OrgRel getOrgRelById(@ApiParam(name = "id", value = "岗位id", required = true) @RequestParam String id) throws Exception{
		return iUserOrgService.getOrgRelById(id);
	}
	
	/**
	 * 根据用户帐号获取用户所属角色
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getRoleByAccount",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户所属角色", httpMethod = "GET", notes = "根据用户帐号获取用户所属角色")
	public JSONObject getRoleByAccount(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.getRoleByAccount(account);
	}
	
	/**
	 * 根据用户帐号获取用户所属组织
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgListByAccount",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户所属组织", httpMethod = "GET", notes = "根据用户帐号获取用户所属组织")
	public JSONObject getOrgListByAccount(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.getOrgListByAccount(account);
	}
	
	/**
	 * 通过用户账号获取用户的当前组织
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="loadCurOrgByUserAccount",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户的当前组织", httpMethod = "GET", notes = "通过用户账号获取用户的当前组织")
	public IGroup loadCurOrgByUserAccount(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.loadCurOrgByUserAccount(account);
	}
	
	/**
	 * 通过用户帐号和组织代码，设置用户当前组织
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="setCurOrg",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "设置当前组织", httpMethod = "POST", notes = "通过用户帐号和组织代码，设置用户当前组织")
	public CommonResult<String> setCurOrg(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,
			@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode)
			throws Exception {
		return iUserOrgService.setCurOrg(account,orgCode);
	}
	
	/**
	 * 根据组织代码获取组织人员
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgUser",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取组织人员", httpMethod = "GET", notes = "根据组织代码获取组织人员")
	public JSONObject getOrgUser(@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode) throws Exception{
		return iUserOrgService.getOrgUser(orgCode);
	}
	
	/**
	 * 根据组织代码获取子组织
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getChildren",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取子组织", httpMethod = "GET", notes = "根据组织代码获取子组织")
	public JSONObject getChildren(@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode) throws Exception{
		return iUserOrgService.getChildren(orgCode);
	}
	
	/**
	 * 获取用户主组织
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUserMainOrg",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户主组织", httpMethod = "GET", notes = "获取用户主组织")
	public Org getUserMainOrg(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.getUserMainOrg(account);
	}
	
	/**
	 * 获取角色人员
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getRoleUser",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取角色人员", httpMethod = "GET", notes = "获取角色人员")
	public JSONObject getRoleUser(@ApiParam(name = "roleCode", value = "角色代码", required = true) @RequestParam String roleCode) throws Exception{
		return iUserOrgService.getRoleUser(roleCode);
	}
	
	/**
	 * 获取岗位人员
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getRelUser",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取岗位人员", httpMethod = "GET", notes = "获取岗位人员")
	public JSONObject getRelUser(@ApiParam(name = "relCode", value = "岗位代码", required = true) @RequestParam String relCode) throws Exception{
		return iUserOrgService.getRelUser(relCode);
	}
	
	/**
	 * 查询用户
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUserList",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "查询用户", httpMethod = "POST", notes = "查询用户")
	public JSONObject getUserList(@ApiParam(name = "filter", value = "查询参数", required = true) @RequestBody UserOrgQueryFilter filter) throws Exception{
		return iUserOrgService.getUserList(filter);
	}
	
	/**
	 * 获取用户岗位列表
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUserRel",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户岗位列表", httpMethod = "GET", notes = "获取用户岗位列表")
	public JSONObject getUserRel(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.getUserRel(account);
	}
	
	/**
	 * 获取用户主岗位
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgUserMaster",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户主岗位", httpMethod = "GET", notes = "获取用户主岗位")
	public OrgRel getOrgUserMaster(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.getOrgUserMaster(account);
	}
	
	/**
	 * 获取用户岗位列表
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgRelByOrgCode",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取组织岗位列表", httpMethod = "GET", notes = "获取用户岗位列表")
	public JSONObject getOrgRelByOrgCode(@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode) throws Exception{
		return iUserOrgService.getOrgRelByOrgCode(orgCode);
	}
	
	/**
	 * 获取用户的所有上级
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUpUsersByAccount",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户的所有上级", httpMethod = "GET", notes = "获取用户的所有上级")
	public JSONObject getUpUsersByAccount(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.getUpUsersByAccount(account);
	}
	
	/**
	 * 获取用户某组织下的上级
	 * @param account
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUpUserByUserAndOrg",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户某组织下的上级", httpMethod = "GET", notes = "获取用户某组织下的上级")
	public User getUpUserByUserAndOrg(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode) throws Exception{
		return iUserOrgService.getUpUserByUserAndOrg(account,orgCode);
	}
	
	/**
	 * 获取用户的所有下级
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUnderUsersByAccount",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户的所有下级", httpMethod = "GET", notes = "获取用户的所有下级")
	public JSONObject getUnderUsersByAccount(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account) throws Exception{
		return iUserOrgService.getUnderUsersByAccount(account);
	}
	
	/**
	 * 获取用户某组织下的下级用户
	 * @param account
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUnderUserByUserAndOrg",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户某组织下的下级用户", httpMethod = "GET", notes = "获取用户某组织下的下级用户")
	public JSONObject getUnderUserByUserAndOrg(@ApiParam(name = "account", value = "用户帐号", required = true) @RequestParam String account,@ApiParam(name = "orgCode", value = "组织代码", required = true) @RequestParam String orgCode) throws Exception{
		return iUserOrgService.getUnderUserByUserAndOrg(account,orgCode);
	}
	
	/**
	 * 获取用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUser",method=RequestMethod.GET, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户信息", httpMethod = "GET", notes = "获取用户信息")
	public User getUser(@ApiParam(name="account",value="用户帐号") @RequestParam String account) throws Exception{
		return iUserOrgService.getUser(account);
	}
	
	/**
	 * 查询组织
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgList",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "查询组织", httpMethod = "POST", notes = "查询组织")
	public JSONObject getOrgList(@ApiParam(name = "filter", value = "查询参数", required = true) @RequestBody UserOrgQueryFilter filter) throws Exception{
		return iUserOrgService.getOrgList(filter);
	}
	
	/**
	 * 查询角色
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getRoleList",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "查询角色", httpMethod = "POST", notes = "查询角色")
	public JSONObject getRoleList(@ApiParam(name = "filter", value = "查询参数", required = true) @RequestBody UserOrgQueryFilter filter) throws Exception{
		return iUserOrgService.getRoleList(filter);
	}
	
	/**
	 * 查询岗位
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgRelList",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "查询岗位", httpMethod = "POST", notes = "查询岗位")
	public JSONObject getOrgRelList(@ApiParam(name = "filter", value = "查询参数", required = true) @RequestBody UserOrgQueryFilter filter) throws Exception{
		return iUserOrgService.getOrgRelList(filter);
	}
	
	/**
	 * 查询职务
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getOrgReldefList",method=RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value = "查询职务", httpMethod = "POST", notes = "查询职务")
	public JSONObject getOrgReldefList(@ApiParam(name = "filter", value = "查询参数", required = true) @RequestBody UserOrgQueryFilter filter) throws Exception{
		return iUserOrgService.getOrgReldefList(filter);
	}

}

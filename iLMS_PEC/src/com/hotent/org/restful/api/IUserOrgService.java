package com.hotent.org.restful.api;

import java.util.List;

import com.hotent.org.api.model.IGroup;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.User;
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

import net.sf.json.JSONObject;

/**
 * 用户组织模块接口
 * @author liangqf
 *
 */
public interface IUserOrgService {
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addUser(UserRestfulObject user) throws Exception;
	
	/**
	 * 用户更新
	 * @param user
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> updateUser(UserRestfulObject user) throws Exception;
	
	/**
	 * 删除用户
	 * @param account
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteUser(String account) throws Exception;
	
	/**
	 * 添加组织
	 * @param org
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addOrg(OrgRestfulObject org) throws Exception;
	
	/**
	 * 更新组织，连同子组织一起更新
	 * @param org
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> updateOrg(OrgRestfulObject org) throws Exception;
	
	/**
	 * 删除组织，连同其子组织
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteOrg(String orgCode) throws Exception;
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addRole(RoleRestfulObject role) throws Exception;
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> updateRole(RoleRestfulObject role) throws Exception;
	
	/**
	 * 根据别名删除角色
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteRole(String alias) throws Exception;
	
	/**
	 * 获取系统所有角色
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllRole() throws Exception;
	
	/**
	 * 增加职务
	 * @param reldef
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addOrgReldef(OrgReldefRestfulObject reldef) throws Exception;
	
	/**
	 * 更新职务
	 * @param reldef
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> updateOrgReldef(OrgReldefRestfulObject reldef) throws Exception;
	
	/**
	 * 根据职务代码删除职务
	 * @param code
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteOrgReldef(String code) throws Exception;
	
	/**
	 * 获取职务列表
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllReldef() throws Exception;
	
	/**
	 * 添加维度
	 * @param demension
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addDemension(DemensionRestfulObject demension) throws Exception;
	
	/**
	 * 更新维度
	 * @param demension
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> updateDemension(DemensionRestfulObject demension) throws Exception;
	
	/**
	 * 根据id删除维度
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteDemension(String id) throws Exception;
	
	/**
	 * 获取所有维度
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllDemension() throws Exception;
	
	/**
	 * 添加岗位
	 * @param orgCode
	 * @param reldefCode
	 * @param rel
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addOrgRel(String orgCode,String reldefCode,OrgRelRestfulObject rel) throws Exception;
	
	/**
	 * 修改岗位
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> updateOrgRel(OrgRelRestfulObject rel) throws Exception;
	
	/**
	 * 根据岗位代码删除岗位
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteOrgRel(String relCode) throws Exception;
	
	/**
	 * 获取所有岗位信息
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllOrgRel() throws Exception;
	
	/**
	 * 添加分级管理
	 * @param userAccount
	 * @param orgCode
	 * @param orgAuth
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addOrgAuth(String userAccount,String orgCode,OrgAuthRestfulObject orgAuth) throws Exception;
	
	/**
	 * 修改分级管理
	 * @param orgAuth
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> updateOrgAuth(OrgAuthRestfulObject orgAuth) throws Exception;
	
	/**
	 * 根据id删除分级管理
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteAuth(String id) throws Exception;
	
	/**
	 * 获取所有分级管理
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllAuth() throws Exception;
	
	/**
	 * 添加或修改用户组织参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addOrUpdateParams(ParamsRestfulObject param) throws Exception;
	
	/**
	 * 根据参数key删除参数
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> deleteParams(String key) throws Exception;
	
	/**
	 * 获取用户组织参数列表
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllParams() throws Exception;
	
	/**
	 * 编辑用户、组织属性值
	 * @param type
	 * @param list
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> editParamValue(String code,String type,List<com.alibaba.fastjson.JSONObject> list) throws Exception;
	
	/**
	 *  添加用户上下级关系
	 * @param orgCode
	 * @param upUserAccount
	 * @param underUserAccount
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addUnderUser(String orgCode,String upUserAccount,String underUserAccount) throws Exception;
	
	/**
	 * 根据上级用户帐号与下级用户帐号，删除上下级关系
	 * @param underUserAccount
	 * @param upUserAccount
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> removeUnderUser(String underUserAccount,String upUserAccount) throws Exception;
	
	/**
	 * 添加用户角色关系
	 * @param account
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addUserRoleRel(String account,String roleCode) throws Exception;
	
	/**
	 * 删除用户角色关系
	 * @param account
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> removeUserRoleRel(String account,String roleCode) throws Exception;
	
	/**
	 * 添加用户岗位关系
	 * @param account
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addUserRel(String account,String relCode) throws Exception;
	
	/**
	 * 删除用户岗位关系
	 * @param account
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> removeUserRel(String account,String relCode) throws Exception;
	
	/**
	 * 通过id获取组织
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Org getOrgById(String id) throws Exception;
	
	/**
	 * 通过id获取岗位
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OrgRel getOrgRelById(String id) throws Exception;
	
	/**
	 * 根据用户帐号获取用户所属角色
	 * @param account
	 * @return
	 * @throws Exception
	 */
	JSONObject getRoleByAccount(String account) throws Exception;
	
	/**
	 * 根据用户帐号获取用户所属组织
	 * @param account
	 * @return
	 * @throws Exception
	 */
	JSONObject getOrgListByAccount(String account) throws Exception;
	
	/**
	 * 通过用户账号获取用户的当前组织
	 * @param account
	 * @return
	 * @throws Exception
	 */
	IGroup loadCurOrgByUserAccount(String account) throws Exception;
	
	/**
	 * 通过用户帐号和组织代码，设置当前组织
	 * @param account
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> setCurOrg(String account,String orgCode) throws Exception;
	
	/**
	 * 根据组织代码获取组织人员
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	JSONObject getOrgUser(String orgCode) throws Exception;
	
	/**
	 * 根据组织代码获取子组织
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	JSONObject getChildren(String orgCode) throws Exception;
	
	/**
	 * 获取用户主组织
	 * @param account
	 * @return
	 * @throws Exception
	 */
	Org getUserMainOrg(String account) throws Exception;
	
	/**
	 * 获取角色人员
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	JSONObject getRoleUser(String roleCode) throws Exception;
	
	/**
	 * 获取岗位人员
	 * @param relCode
	 * @return
	 * @throws Exception
	 */
	JSONObject getRelUser(String relCode) throws Exception;
	
	/**
	 * 查询用户
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	JSONObject getUserList(UserOrgQueryFilter filter) throws Exception;
	
	/**
	 * 查询组织
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	JSONObject getOrgList(UserOrgQueryFilter filter) throws Exception;
	
	/**
	 * 查询角色
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	JSONObject getRoleList(UserOrgQueryFilter filter) throws Exception;
	
	/**
	 * 查询岗位
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	JSONObject getOrgRelList(UserOrgQueryFilter filter) throws Exception;
	
	/**
	 * 查询职务
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	JSONObject getOrgReldefList(UserOrgQueryFilter filter) throws Exception;
	
	/**
	 * 获取用户岗位列表
	 * @param account
	 * @return
	 * @throws Exception
	 */
	JSONObject getUserRel(String account) throws Exception;
	
	/**
	 * 获取用户主岗位
	 * @param account
	 * @return
	 * @throws Exception
	 */
	OrgRel getOrgUserMaster(String account) throws Exception;
	
	/**
	 * 获取组织岗位列表
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	JSONObject getOrgRelByOrgCode(String orgCode) throws Exception;
	
	/**
	 * 获取用户的所有上级
	 * @param account
	 * @return
	 */
	JSONObject getUpUsersByAccount(String account) throws Exception;
	
	/**
	 * 获取用户某组织下的上级
	 * @param account
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	User getUpUserByUserAndOrg(String account,String orgCode) throws Exception;
	
	/**
	 * 获取用户的所有下级
	 * @param account
	 * @return
	 */
	JSONObject getUnderUsersByAccount(String account) throws Exception;
	
	/**
	 * 获取用户某组织下的下级用户
	 * @param account
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	JSONObject getUnderUserByUserAndOrg(String account,String orgCode) throws Exception;
	
	/**
	 * 组织加入用户
	 * @param orgCode
	 * @param account
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addOrgUser(String orgCode,String account) throws Exception;
	
	/**
	 * 组织新增用户
	 * @param orgCode
	 * @param user
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> addOrgUser(String orgCode,UserRestfulObject user) throws Exception;
	
	/**
	 * 获取用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	User getUser(String account) throws Exception;
	
	/**
	 * 删除用户组织关系
	 * @param orgCode
	 * @param account
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> delOrgUser(String orgCode,String account) throws Exception;
	
}

package com.hotent.org.restful.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.RestfulReturnUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultFieldSort;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.exception.HotentHttpStatus;
import com.hotent.base.exception.RequiredException;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.persistence.manager.OrgAuthManager;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgRelManager;
import com.hotent.org.persistence.manager.OrgReldefManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.manager.SysDemensionManager;
import com.hotent.org.persistence.manager.SysOrgParamsManager;
import com.hotent.org.persistence.manager.SysParamsManager;
import com.hotent.org.persistence.manager.SysUserParamsManager;
import com.hotent.org.persistence.manager.SysUserRelManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.manager.UserRoleManager;
import com.hotent.org.persistence.manager.UserUnderManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgAuth;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgReldef;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.Role;
import com.hotent.org.persistence.model.SysDemension;
import com.hotent.org.persistence.model.SysParams;
import com.hotent.org.persistence.model.User;
import com.hotent.org.persistence.model.UserRole;
import com.hotent.org.persistence.model.UserUnder;
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
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONObject;

@Service("IUserOrgService")
public class UserOrgServiceImpl implements IUserOrgService {
	
	@Resource
	UserManager userManager;
	@Resource
	OrgManager orgManager;
	@Resource
	SysDemensionManager demensionManager;
	@Resource
	RoleManager roleManager;
	@Resource
	OrgReldefManager reldefManager;
	@Resource
	OrgRelManager relManager;
	@Resource
	OrgAuthManager authManager;
	@Resource
	SysParamsManager paramsManager;
	@Resource
	SysUserParamsManager userParamsManager;
	@Resource
	SysOrgParamsManager orgParamsManager;
	@Resource
	SysUserRelManager userRelManager;
	@Resource
	UserRoleManager userRoleManager;
	@Resource
	UserUnderManager userUnderManager;
	@Resource
	OrgUserManager orgUserManager;

	@Override
	public CommonResult<String> addUser(UserRestfulObject user) throws Exception {
		if(StringUtil.isEmpty(user.getAccount())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：account用户帐号必填！");
		}
		if(StringUtil.isEmpty(user.getFullname())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：fullname用名必填！");
		}
		if(StringUtil.isEmpty(user.getPassword())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：password登录密码必填！");
		}
		User u = userManager.getByAccount(user.getAccount());
		if(BeanUtils.isNotEmpty(u)){
			throw new RuntimeException("帐号【"+user.getAccount()+"】已存在，请重新输入！");
		}
		JSON userOject = JSON.parseObject(user.toString());
		User newUser = JSON.toJavaObject(userOject, User.class);
		newUser.setStatus(1);
		newUser.setId(UniqueIdUtil.getSuid());
		newUser.setCreateTime(new Date());
		newUser.setPassword(EncryptUtil.encryptSha256(user.getPassword()));
		newUser.setFrom(User.FROM_RESTFUL);
		userManager.create(newUser);
		return new CommonResult<String>(true, "用户添加成功！", "");
	}

	@Override
	public CommonResult<String> updateUser(UserRestfulObject user) throws Exception {
		if(StringUtil.isEmpty(user.getAccount())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：account用户帐号必填！");
		}
		User u = userManager.getByAccount(user.getAccount());
		if(BeanUtils.isEmpty(u)){
			throw new RuntimeException("根据【"+user.getAccount()+"】没有找到对应的用户信息！");
		}
		if(StringUtil.isNotEmpty(user.getAddress())){
			u.setAddress(user.getAddress());
		}
		if(StringUtil.isNotEmpty(user.getEmail())){
			u.setEmail(user.getEmail());
		}
		if(StringUtil.isNotEmpty(user.getFullname())){
			u.setFullname(user.getFullname());
		}
		if(StringUtil.isNotEmpty(user.getSex())){
			u.setSex(user.getSex());
		}
		if(StringUtil.isNotEmpty(user.getMobile())){
			u.setMobile(user.getMobile());
		}
		if(StringUtil.isNotEmpty(user.getWeixin())){
			u.setWeixin(user.getWeixin());
		}
		userManager.update(u);
		return new CommonResult<String>(true, "用户更新成功！", "");
	}

	@Override
	public CommonResult<String> deleteUser(String account) throws Exception {
		if(StringUtil.isEmpty(account )){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：account帐号必填！");
		}
		User u = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(u)){
			throw new RuntimeException("根据【"+account+"】找不到对应的用户，无法进行删除操作！");
		}
		userManager.remove(u.getId());
		return new CommonResult<String>(true, "用户删除成功！", "");
	}

	@Override
	public CommonResult<String> addOrg(OrgRestfulObject org) throws Exception {
		if(StringUtil.isEmpty(org.getName())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：name组织名称必填！");
		}
		if(StringUtil.isEmpty(org.getCode())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：code组织代码必填！");
		}
		if(StringUtil.isEmpty(org.getDemId())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：demId对应维度必填！");
		}
		//父组织
		Org parentOrg = null;
		if(StringUtil.isNotEmpty(org.getParentId()) && !"0".equals(org.getParentId())){
			parentOrg = orgManager.get(org.getParentId());
			if(BeanUtils.isEmpty(parentOrg)){
				throw new RuntimeException("根据输入的父组织id，查询不到组织");
			}
		}
		//维度
		SysDemension dem = demensionManager.get(org.getDemId());
		if(BeanUtils.isEmpty(dem)){
			throw new RuntimeException("根据维度【"+org.getDemId()+"】找不到维度！");
		}
		Org o = orgManager.getByCode(org.getCode());
		if(BeanUtils.isNotEmpty(o)){
			throw new RuntimeException("组织代码已经重复，请输入其他数据");
		}
		JSON orgObject = JSON.parseObject(org.toString());
		o = JSON.toJavaObject(orgObject, Org.class);
		o.setId(UniqueIdUtil.getSuid());
		o.setDemId(dem.getId());
		if(BeanUtils.isNotEmpty(parentOrg)){
			o.setDemId(parentOrg.getDemId());
			o.setPath(parentOrg.getPath()+o.getId()+".");
			o.setPathName(parentOrg.getPathName()+"/"+o.getName());
		}else{
			o.setPath(dem.getId()+"."+o.getId()+".");
			o.setPathName("/"+o.getName());
		}
		orgManager.create(o);
		return new CommonResult<String>(true, "添加组织成功！", "");
	}

	@Override
	public CommonResult<String> updateOrg(OrgRestfulObject org) throws Exception {
		if(StringUtil.isEmpty(org.getCode())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填，根据代码查询组织进行更新！");
		}
		Org o = orgManager.getByCode(org.getCode());
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("根据输入的组织代码没有查询到对应组织，请检查输入！");
		}
		//更新
		if(StringUtil.isNotEmpty(org.getName())){
			o.setName(org.getName());
		}
		if (StringUtil.isNotEmpty(org.getGrade())) {
			o.setGrade(org.getGrade());
		}
		if (BeanUtils.isNotEmpty(org.getOrderNo())) {
			o.setOrderNo(org.getOrderNo());
		}
		orgManager.updateByOrg(o);
		return new CommonResult<String>(true, "组织更新成功", "");
	}

	@Override
	public CommonResult<String> deleteOrg(String orgCode) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org o = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("根据输入的组织代码，没有找到对应组织！");
		}
		String[] ids = {o.getId()};
		orgManager.removeByIds(ids);
		return new CommonResult<String>(true, "删除组织成功！", "");
	}

	@Override
	public CommonResult<String> addRole(RoleRestfulObject role) throws Exception {
		if(StringUtil.isEmpty(role.getAlias())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：alias角色别名必填！");
		}
		if(StringUtil.isEmpty(role.getName())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：name角色名称必填！");
		}
		Role r = roleManager.getByAlias(role.getAlias());
		if(BeanUtils.isNotEmpty(r)){
			throw new RuntimeException("别名已存在，请重新输入！");
		}
		JSON roleObject = JSON.parseObject(role.toString());
		r = JSON.toJavaObject(roleObject, Role.class);
		r.setId(UniqueIdUtil.getSuid());
		roleManager.create(r);
		return new CommonResult<String>(true, "角色添加成功！", "");
	}

	@Override
	public CommonResult<String> updateRole(RoleRestfulObject role) throws Exception {
		if(StringUtil.isEmpty(role.getAlias())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：alias角色别名必填！");
		}
		Role r = roleManager.getByAlias(role.getAlias());
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据别名找不到对应角色！");
		}
		//更新
		if(StringUtil.isNotEmpty(role.getDescription())){
			r.setDescription(role.getDescription());
		}
		if(StringUtil.isNotEmpty(role.getName())){
			r.setName(role.getName());
		}
		if(BeanUtils.isNotEmpty(role.getEnabled())){
			r.setEnabled(role.getEnabled());
		}
		roleManager.update(r);
		return new CommonResult<String>(true, "角色更新成功", "");
	}

	@Override
	public CommonResult<String> deleteRole(String alias) throws Exception {
		if(StringUtil.isEmpty(alias)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：alias角色别名必填！");
		}
		Role r = roleManager.getByAlias(alias);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据别名没有找到对应角色，请检查输入！");
		}
		roleManager.remove(r.getId());
		return new CommonResult<String>(true, "角色删除成功！", "");
	}

	@Override
	public JSONObject getAllRole() throws Exception {
		List<Role> list = roleManager.getAll();
		JSONObject rtn = new JSONObject();
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public CommonResult<String> addOrgReldef(OrgReldefRestfulObject reldef) throws Exception {
		if(StringUtil.isEmpty(reldef.getName())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：职务名称必填！");
		}
		if(StringUtil.isEmpty(reldef.getCode())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：职务代码必填！");
		}
		OrgReldef r = reldefManager.getByCode(reldef.getCode());
		if(BeanUtils.isNotEmpty(r)){
			throw new RuntimeException("职务代码重复，请重新填写！");
		}
		JSON reldefObject = JSON.parseObject(reldef.toString());
		r = JSON.toJavaObject(reldefObject, OrgReldef.class);
		r.setId(UniqueIdUtil.getSuid());
		reldefManager.create(r);
		return new CommonResult<String>(true, "添加职务成功！", "");
	}

	@Override
	public CommonResult<String> updateOrgReldef(OrgReldefRestfulObject reldef) throws Exception {
		if(StringUtil.isEmpty(reldef.getCode())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：职务代码必填！");
		}
		OrgReldef r = reldefManager.getByCode(reldef.getCode());
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据输入的职务代码没有找到对应的职务！");
		}
		if(StringUtil.isNotEmpty(reldef.getDescription())){
			r.setDescription(reldef.getDescription());
		}
		if(StringUtil.isNotEmpty(reldef.getName())){
			r.setName(reldef.getName());
		}
		if(StringUtil.isNotEmpty(reldef.getPostLevel())){
			r.setPostLevel(reldef.getPostLevel());
		}
		reldefManager.update(r);
		return new CommonResult<String>(true, "职务更新成功！", "");
	}

	@Override
	public CommonResult<String> deleteOrgReldef(String code) throws Exception {
		if(StringUtil.isEmpty(code)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：职务代码必填！");
		}
		OrgReldef r = reldefManager.getByCode(code);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据输入的代码没有找到职务，不能进行删除操作！");
		}
		reldefManager.remove(r.getId());
		return new CommonResult<String>(true, "职务删除成功！", null);
	}
	
	@Override
	public JSONObject getAllReldef() throws Exception{
		JSONObject rtn = new JSONObject();
		List<OrgReldef> list = reldefManager.getAll();
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public CommonResult<String> addDemension(DemensionRestfulObject demension) throws Exception {
		if(StringUtil.isEmpty(demension.getDemName())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：维度名称必填！");
		}
		JSON demObject = JSON.parseObject(demension.toString());
		SysDemension d = JSON.toJavaObject(demObject, SysDemension.class);
		d.setId(UniqueIdUtil.getSuid());
		demensionManager.create(d);
		return new CommonResult<String>(true, "添加维度成功！", null);
	}

	@Override
	public CommonResult<String> updateDemension(DemensionRestfulObject demension) throws Exception {
		if(StringUtil.isEmpty(demension.getId())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：维度id必填！");
		}
		SysDemension d = demensionManager.get(demension.getId());
		if(BeanUtils.isEmpty(d)){
			throw new RuntimeException("根据id没有找到对应的维度！");
		}
		if(StringUtil.isEmpty(demension.getDemDesc())){
			d.setDemDesc(demension.getDemDesc());
		}
		if (StringUtil.isEmpty(demension.getDemName())) {
			d.setDemName(demension.getDemName());
		}
		demensionManager.update(d);
		return new CommonResult<String>(true, "维度更新成功！", null);
	}

	@Override
	public CommonResult<String> deleteDemension(String id) throws Exception {
		if(StringUtil.isEmpty(id)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：维度id必填！");
		}
		SysDemension d = demensionManager.get(id);
		if(BeanUtils.isEmpty(d)){
			throw new RuntimeException("根据id没有找到对应的维度，不能进行删除操作！");
		}
		demensionManager.remove(id);
		return new CommonResult<String>(true, "维度删除成功！", null);
	}

	@Override
	public JSONObject getAllDemension() throws Exception {
		JSONObject rtn = new JSONObject();
		List<SysDemension> list = demensionManager.getAll();
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public CommonResult<String> addOrgRel(String orgCode, String reldefCode, OrgRelRestfulObject rel) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：orgCode组织代码必填！");
		}
		Org o = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("根据输入的组织代码，没有找到对应组织！");
		}
		if(StringUtil.isEmpty(reldefCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：reldefCode职务代码必填！");
		}
		OrgReldef reldef = reldefManager.getByCode(reldefCode);
		if(BeanUtils.isEmpty(reldef)){
			throw new RuntimeException("根据输入的职务代码，没有找到对应职务！");
		}
		if(StringUtil.isEmpty(rel.getRelCode())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：relCode岗位代码必填！");
		}
		if(StringUtil.isEmpty(rel.getRelName())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：relName岗位名称必填！");
		}
		if(BeanUtils.isNotEmpty(relManager.getByCode(rel.getRelCode()))){
			throw new RuntimeException("输入的岗位代码已存在，请重新输入！");
		}
		OrgRel r = new OrgRel();
		r.setId(UniqueIdUtil.getSuid());
		r.setRelCode(rel.getRelCode());
		r.setRelName(rel.getRelName());
		r.setOrgId(o.getId());
		r.setRelDefId(reldef.getId());
		relManager.create(r);
		return new CommonResult<String>(true, "添加岗位成功！", null);
	}

	@Override
	public CommonResult<String> updateOrgRel(OrgRelRestfulObject rel) throws Exception {
		if(StringUtil.isEmpty(rel.getRelCode())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：relCode岗位代码必填！");
		}
		OrgRel r = relManager.getByCode(rel.getRelCode());
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据输入的岗位代码没有找到对应的岗位信息");
		}
		if(StringUtil.isNotEmpty(rel.getRelName())){
			r.setRelName(rel.getRelName());
		}
		relManager.update(r);
		return new CommonResult<String>(true, "岗位更新成功！", null);
	}

	@Override
	public CommonResult<String> deleteOrgRel(String relCode) throws Exception {
		if(StringUtil.isEmpty(relCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：relCode岗位代码必填！");
		}
		OrgRel r = relManager.getByCode(relCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据输入的岗位代码没有找到岗位，不能进行删除操作！");
		}
		String[] ids = {r.getId()};
		relManager.removeByIds(ids);
		return new CommonResult<String>(true, "岗位删除成功", null);
	}

	@Override
	public JSONObject getAllOrgRel() throws Exception {
		JSONObject rtn = new JSONObject();
		List<OrgRel> list = relManager.getAll();
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public CommonResult<String> addOrgAuth(String userAccount, String orgCode, OrgAuthRestfulObject orgAuth) throws Exception {
		if(StringUtil.isEmpty(userAccount)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：userAccount用户帐号必填！");
		}
		User u = userManager.getByAccount(userAccount);
		if(BeanUtils.isEmpty(u)){
			throw new RuntimeException("没有找到对应的用户！");
		}
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：orgCode组织代码必填！");
		}
		Org o = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("没有找到对应的组织！");
		}
		if(StringUtil.isEmpty(orgAuth.getDemId())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：demId维度id必填！");
		}
		if(BeanUtils.isEmpty(demensionManager.get(orgAuth.getDemId()))){
			throw new RuntimeException("没有找到对应的维度！");
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("orgId", o.getId());
		map.put("userId", u.getId());
		OrgAuth a = authManager.getByOrgIdAndUserId(map);
		if(BeanUtils.isNotEmpty(a)){
			throw new RuntimeException("用户在该组织已经是分级管理员！");
		}
		OrgAuth auth = new OrgAuth();
		auth.setId(UniqueIdUtil.getSuid());
		auth.setOrgId(o.getId());
		auth.setUserId(u.getId());
		auth.setOrgPerms(orgAuth.getOrgPerms());
		auth.setUserPerms(orgAuth.getUserPerms());
		auth.setPosPerms(orgAuth.getPosPerms());
		auth.setOrgauthPerms(orgAuth.getOrgauthPerms());
		auth.setDemId(orgAuth.getDemId());
		authManager.create(auth);
		return new CommonResult<String>(true, "添加分级管理成功！", null);
	}

	@Override
	public CommonResult<String> updateOrgAuth(OrgAuthRestfulObject orgAuth) throws Exception {
		if (StringUtil.isEmpty(orgAuth.getId())) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description() + "：id必填！");
		}
		OrgAuth a = authManager.get(orgAuth.getId());
		if (BeanUtils.isEmpty(a)) {
			throw new RuntimeException("根据输入的id没有找到对应的数据！");
		}
		if (StringUtil.isNotEmpty(orgAuth.getPosPerms())) {
			a.setPosPerms(orgAuth.getPosPerms());
		}
		if (StringUtil.isNotEmpty(orgAuth.getUserPerms())) {
			a.setUserPerms(orgAuth.getUserPerms());
		}
		if (StringUtil.isNotEmpty(orgAuth.getOrgauthPerms())) {
			a.setOrgauthPerms(orgAuth.getOrgauthPerms());
		}
		if (StringUtil.isNotEmpty(orgAuth.getOrgPerms())) {
			a.setOrgPerms(orgAuth.getOrgPerms());
		}
		authManager.update(a);
		return new CommonResult<String>(true, "更新成功！", null);
	}

	@Override
	public CommonResult<String> deleteAuth(String id) throws Exception {
		if(StringUtil.isEmpty(id)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：id必填！");
		}
		OrgAuth auth = authManager.get(id);
		if(BeanUtils.isEmpty(auth)){
			throw new RuntimeException("根据输入的id没有找到数据！");
		}
		authManager.remove(id);
		return new CommonResult<String>(true, "删除成功！", null);
	}

	@Override
	public JSONObject getAllAuth() throws Exception {
		JSONObject rtn = new JSONObject();
		List<OrgAuth> list = authManager.getAll();
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public CommonResult<String> addOrUpdateParams(ParamsRestfulObject param) throws Exception {
		if(StringUtil.isEmpty(param.getAlias())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：alias必填！");
		}
		if(StringUtil.isEmpty(param.getType())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：type类型必填！");
		}
		if(StringUtil.isEmpty(param.getCtlType())){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：ctlType数据来源必填！");
		}
		if (("dic".equals(param.getCtlType()) || "select".equals(param.getCtlType())
				|| "checkbox".equals(param.getCtlType()) || "radio".equals(param.getCtlType())
				|| "customdialog".equals(param.getCtlType())) && StringUtil.isEmpty(param.getJson())) {
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":当数据来源为dic、select、checkbox、radio或customdialog时，json必填！");
		}
		if(("number".equals(param.getCtlType()) || "date".equals(param.getCtlType())
				|| "input".equals(param.getCtlType()))){
			param.setJson("[]");
		}
		SysParams sysParams = paramsManager.getByAlias(param.getAlias());
		JSON paramObject = JSON.parseObject(param.toString());
		SysParams p = JSON.toJavaObject(paramObject, SysParams.class);
		if(BeanUtils.isNotEmpty(sysParams)){//更新
			p.setId(sysParams.getId());
			paramsManager.update(p);
			return new CommonResult<String>(true, "更新成功！", null);
		}else{//新增
			p.setId(UniqueIdUtil.getSuid());
			paramsManager.create(p);
			return new CommonResult<String>(true, "添加用户组织参数成功！", null);
		}
	}

	@Override
	public CommonResult<String> deleteParams(String key) throws Exception {
		if(StringUtil.isEmpty(key )){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户组织参数key必填！");
		}
		SysParams p = paramsManager.getByAlias(key);
		if(BeanUtils.isEmpty(p)){
			throw new RuntimeException("根据输入的key没有找到数据，不能进行删除！");
		}
		String[] ids = {p.getId()};
		paramsManager.removeByIds(ids);
		return new CommonResult<String>(true, "删除成功！", null);
	}

	@Override
	public JSONObject getAllParams() throws Exception {
		JSONObject rtn = new JSONObject();
		List<SysParams> list = paramsManager.getAll();
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public CommonResult<String> editParamValue(String code, String type, List<com.alibaba.fastjson.JSONObject> list) throws Exception {
		if(StringUtil.isEmpty(type)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：type类型必填！");
		}
		if(StringUtil.isEmpty(code)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：code用户账号或者组织代码必填！");
		}
		
		if("user".equals(type)){
			User u = userManager.getByAccount(code);
			if(BeanUtils.isEmpty(u)){
				throw new RuntimeException("根据输入的代码没有找到用户！");
			}
			//判断输入的key是否存在
			for(com.alibaba.fastjson.JSONObject o : list){
				String key = o.get("alias").toString();
				SysParams p = paramsManager.getByAlias(key);
				if(BeanUtils.isEmpty(p)){
					throw new RuntimeException("【"+key+"】参数key不存在！");
				}
				if(!p.getType().equals("1")){
					throw new RuntimeException("【"+key+"】对应的【用户组织扩展参数】类型不是【用户参数】类型！");
				}
			}
			userParamsManager.saveParams(u.getId(), list);;
			return new CommonResult<String>(true, "用户属性值保存成功！", null);
		}else if("org".equals(type)){
			Org r = orgManager.getByCode(code);
			if(BeanUtils.isEmpty(r)){
				throw new RuntimeException("根据输入的代码没有找到组织！");
			}
			//判断输入的key是否存在
			for(com.alibaba.fastjson.JSONObject o : list){
				String key = o.get("alias").toString();
				SysParams p = paramsManager.getByAlias(key);
				if(BeanUtils.isEmpty(p)){
					throw new RuntimeException("【"+key+"】参数key不存在！");
				}
				if(!p.getType().equals("1")){
					throw new RuntimeException("【"+key+"】对应的【用户组织扩展参数】类型不是【组织参数】类型！");
				}
			}
			orgParamsManager.saveParams(r.getId(), list);
			return new CommonResult<String>(true, "组织属性值保存成功！", null);
		}else{
			throw new RuntimeException("type只能输入user或org！");
		}
	}

	@Override
	public CommonResult<String> addUnderUser(String orgCode, String upUserAccount, String underUserAccount) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":orgCode组织代码必填！");
		}
		Org o = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("根据组织代码找不到对应组织！");
		}
		if(StringUtil.isEmpty(upUserAccount)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":upUserAccount上级用户帐号必填！");
		}
		User upUser = userManager.getByAccount(upUserAccount);
		if(BeanUtils.isEmpty(upUser)){
			throw new RuntimeException("根据上级用户帐号找不到用户！");
		}
		if(StringUtil.isEmpty(underUserAccount)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":underUserAccount下级用户帐号必填！");
		}
		User underUser = userManager.getByAccount(underUserAccount);
		if(BeanUtils.isEmpty(underUser)){
			throw new RuntimeException("根据下级用户帐号找不到用户！");
		}
		DefaultQueryFilter filter = new DefaultQueryFilter();
		filter.addParamsFilter("noUserId", upUser.getId());
		filter.addParamsFilter("orgId", o.getId());
		filter.addParamsFilter("underUserId", underUser.getId());
		List<UserUnder> list1 = userUnderManager.getUserUnder(filter);//获取该组织下，不是当前上级用户的数据
		if(BeanUtils.isEmpty(list1)){
			DefaultQueryFilter filter2 = new DefaultQueryFilter();
			filter2.addParamsFilter("userId", upUser.getId());
			filter2.addParamsFilter("orgId", o.getId());
			filter2.addParamsFilter("underUserId",underUser.getId());
			List<UserUnder> list2 = userUnderManager.getUserUnder(filter2);//获取该组织下，是当前上级用户的数据
			if(BeanUtils.isEmpty(list2)){
				UserUnder userUnder = new UserUnder();
				userUnder.setId(UniqueIdUtil.getSuid());
				userUnder.setUserId(upUser.getId());
				userUnder.setUnderUserId(underUser.getId());
				userUnder.setUnderUserName(underUser.getFullname());
				userUnder.setOrgId(o.getId());
				userUnderManager.create(userUnder);
				return new CommonResult<String>(true, "设置上下级关系成功！", null);
			}else{
				throw new RuntimeException("两个用户已经是上下级关系，无需重复操作！");
			}
		}else{
			throw new RuntimeException("在组织【+"+o.getName()+"】下用户【"+underUser.getFullname()+"】已存在上级");
		}
	}

	@Override
	public CommonResult<String> removeUnderUser(String underUserAccount, String upUserAccount) throws Exception {
		if(StringUtil.isEmpty(upUserAccount)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":upUserAccount上级用户帐号必填！");
		}
		User upUser = userManager.getByAccount(upUserAccount);
		if(BeanUtils.isEmpty(upUser)){
			throw new RuntimeException("根据上级用户帐号找不到用户！");
		}
		if(StringUtil.isEmpty(underUserAccount)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":underUserAccount下级用户帐号必填！");
		}
		User underUser = userManager.getByAccount(underUserAccount);
		if(BeanUtils.isEmpty(underUser)){
			throw new RuntimeException("根据下级用户帐号找不到用户！");
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("upUserId", upUser.getId());
		map.put("underUserId", underUser.getId());
		userUnderManager.delByUpIdAndUderId(map);
		return new CommonResult<String>(true, "删除上下级关系成功！", null);
	}

	@Override
	public CommonResult<String> addUserRoleRel(String account, String roleCode) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":account上级用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据用户帐号找不到用户！");
		}
		if(StringUtil.isEmpty(roleCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":roleCode角色代码必填！");
		}
		Role r = roleManager.getByAlias(roleCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据角色代码找不到角色！");
		}
		if (userRoleManager.getByRoleIdUserId(r.getId(), user.getId()) != null){
			return new CommonResult<String>(true, "添加用户角色成功！", null);
		}else{
			UserRole userRole = new UserRole();
			userRole.setId(UniqueIdUtil.getSuid());
			userRole.setUserId(user.getId());
			userRole.setRoleId(r.getId());
			userRoleManager.create(userRole);
			return new CommonResult<String>(true, "添加用户角色成功！", null);
		}

	}

	@Override
	public CommonResult<String> removeUserRoleRel(String account, String roleCode) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":account上级用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据用户帐号找不到用户！");
		}
		if(StringUtil.isEmpty(roleCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":roleCode角色代码必填！");
		}
		Role r = roleManager.getByAlias(roleCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据角色代码找不到角色！");
		}
		UserRole ur = userRoleManager.getByRoleIdUserId(r.getId(), user.getId());
		if (ur != null){
			userRoleManager.remove(ur.getId());
			return new CommonResult<String>(true, "删除用户角色成功！", null);
		}else{
			throw new RuntimeException("两者不存在上下级关系！");
		}
	}

	@Override
	public CommonResult<String> addUserRel(String account, String relCode) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":account上级用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据用户帐号找不到用户！");
		}
		if(StringUtil.isEmpty(relCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":relCode岗位代码必填！");
		}
		OrgRel r = relManager.getByCode(relCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据岗位代码找不到对应的岗位！");
		}
		OrgUser orgUser = orgUserManager.getOrgUser(r.getOrgId(), user.getId(), r.getId());
		if (orgUser == null) {
			orgUser = orgUserManager.getOrgUser(r.getOrgId(), user.getId(), "");
			if (orgUser == null) {
				orgUser = new OrgUser();
				orgUser.setId(UniqueIdUtil.getSuid());
				orgUser.setOrgId(r.getOrgId());
				orgUser.setUserId(user.getId());
				orgUser.setRelId(r.getId());
				orgUser.setIsMaster(0);
				orgUserManager.create(orgUser);
			}else{
				orgUser.setRelId(r.getId());
				orgUserManager.update(orgUser);
			}
		}
		return new CommonResult<String>(true, "操作成功", null);
	}

	@Override
	public CommonResult<String> removeUserRel(String account, String relCode) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":account上级用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据用户帐号找不到用户！");
		}
		if(StringUtil.isEmpty(relCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":relCode岗位代码必填！");
		}
		OrgRel r = relManager.getByCode(relCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据岗位代码找不到对应的岗位！");
		}
		OrgUser orgUser = orgUserManager.getOrgUser(r.getOrgId(), user.getId(), r.getId());
		if(BeanUtils.isEmpty(orgUser)){
			throw new RuntimeException("用户与该岗位不存在关系！");
		}
		orgUserManager.remove(orgUser.getId());
		return new CommonResult<String>(true, "删除用户岗位关系成功！", null);
	}

	@Override
	public Org getOrgById(String id) throws Exception {
		if(StringUtil.isEmpty(id)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：id必填！");
		}
		Org o = orgManager.get(id);
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("通过【"+id+"】没有找到对应的数据！");
		}
		return o;
	}

	@Override
	public OrgRel getOrgRelById(String id) throws Exception {
		if(StringUtil.isEmpty(id)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：id必填！");
		}
		OrgRel o = relManager.get(id);
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("通过【"+id+"】没有找到对应的数据！");
		}
		return o;
	}

	@Override
	public JSONObject getRoleByAccount(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		JSONObject rtn = new JSONObject();
		List<Role> list = roleManager.getListByAccount(account);
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public JSONObject getOrgListByAccount(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		JSONObject rtn = new JSONObject();
		List<Org> list = orgManager.getOrgListByAccount(account);
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public IGroup loadCurOrgByUserAccount(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		ContextUtil.setCurrentUser(user);
		IGroup group = ContextUtil.getCurrentGroup();
		return group;
	}

	@Override
	public CommonResult<String> setCurOrg(String account, String orgCode) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+":orgCode组织代码必填！");
		}
		Org o = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("根据组织代码找不到对应组织！");
		}
		ContextUtil.setCurrentUser(user);
		ContextUtil.setCurrentOrg(o);
		return new CommonResult<String>(true, "设置成功！", null);
	}

	@Override
	public JSONObject getOrgUser(String orgCode) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据组织代码没有找到组织！");
		}
		JSONObject rtn = new JSONObject();
		DefaultQueryFilter filter = new DefaultQueryFilter();
		DefaultPage page = new DefaultPage(1,Integer.MAX_VALUE);
		filter.setPage(page);
		filter.addFilter("orguser.org_id_", r.getId(), QueryOP.EQUAL);
		List<User> orgUserList = (List<User>) orgUserManager.getUserByGroup(filter);
		RestfulReturnUtil.bulidListResult(rtn, orgUserList);
		return rtn;
	}

	@Override
	public JSONObject getChildren(String orgCode) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据组织代码没有找到组织！");
		}
		JSONObject rtn = new JSONObject();
		List<Org> list = orgManager.getByParentId(r.getId());
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public Org getUserMainOrg(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User u = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(u)){
			throw new RuntimeException("根据输入的帐号信息没有找到用户！");
		}
		return orgManager.getMainGroup(u.getId());
	}

	@Override
	public JSONObject getRoleUser(String roleCode) throws Exception {
		if(StringUtil.isEmpty(roleCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：角色代码必填！");
		}
		Role r = roleManager.getByAlias(roleCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据角色代码没有找到角色！");
		}
		JSONObject rtn = new JSONObject();
		List<User> list = userManager.getUserListByRoleCode(roleCode);
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public JSONObject getRelUser(String relCode) throws Exception {
		if(StringUtil.isEmpty(relCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：岗位代码必填！");
		}
		OrgRel r = relManager.getByCode(relCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据岗位代码没有找到岗位！");
		}
		JSONObject rtn = new JSONObject();
		List<User> list = userManager.getListByRelCode(relCode);
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public JSONObject getUserList(UserOrgQueryFilter filter) throws Exception {
		DefaultQueryFilter f = buildDefaultFilter(filter);
		if(StringUtil.isNotEmpty(filter.getName())){
			f.addFilter("fullname_", filter.getName(), QueryOP.LIKE);
		}
		if(StringUtil.isNotEmpty(filter.getCode())){
			f.addFilter("account_", filter.getCode(), QueryOP.LIKE);
		}
		JSONObject rtn = new JSONObject();
		PageList<User> list = (PageList<User>) userManager.query(f);
		RestfulReturnUtil.buildListReuslt(rtn, list, f);
		return rtn;
	}
	
	/**
	 * 根据UserOrgQueryFilter 构建  QueryFilter
	 * @param filter
	 * @return
	 */
	private DefaultQueryFilter buildDefaultFilter(UserOrgQueryFilter filter ){
		String orderField = filter.getOrderField();
		String orderSeq = filter.getOrderSeq();
		Integer currentPage = BeanUtils.isEmpty(filter.getCurrentPage())?1:filter.getCurrentPage();
		Integer pageSize = BeanUtils.isEmpty(filter.getPageSize())?20:filter.getPageSize();
		
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		// 设置分页
		DefaultPage page = new DefaultPage(currentPage,pageSize);
		queryFilter.setPage(page);
		// 设置排序
		if (StringUtil.isNotEmpty(orderField) && StringUtil.isNotEmpty(orderSeq))
		{
			List<FieldSort> fieldSorts = new ArrayList<FieldSort>();
			fieldSorts.add(new DefaultFieldSort(orderField, Direction.fromString(orderSeq)));
			queryFilter.setFieldSortList(fieldSorts);
		}
		
		return queryFilter;
	}

	@Override
	public JSONObject getUserRel(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		JSONObject rtn = new JSONObject();
		List<OrgRel> list = relManager.getListByUserId(user.getId());
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public OrgRel getOrgUserMaster(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		OrgUser o = orgUserManager.getOrgUserMaster(user.getId());
		if(BeanUtils.isEmpty(o)){
			throw new RuntimeException("用户还没设置主岗位");
		}
		OrgRel r = relManager.get(o.getRelId());
		return r;
	}

	@Override
	public JSONObject getOrgRelByOrgCode(String orgCode) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据输入的代码找不到组织！");
		}
		JSONObject rtn = new JSONObject();
		List<OrgRel> list = relManager.getListByOrgId(r.getId());
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public JSONObject getUpUsersByAccount(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		JSONObject rtn = new JSONObject();
		List<User> list = userManager.getUpUsersByUserId(user.getId());
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public User getUpUserByUserAndOrg(String account, String orgCode) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据组织代码没有找到组织！");
		}
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		return userManager.getUpUserByUserIdAndOrgId(account, orgCode);
	}

	@Override
	public JSONObject getUnderUsersByAccount(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		JSONObject rtn = new JSONObject();
		List<User> list = userManager.getUnderUsersByUserId(user.getId());
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public JSONObject getUnderUserByUserAndOrg(String account, String orgCode) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据组织代码没有找到组织！");
		}
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		JSONObject rtn = new JSONObject();
		List<User> list = userManager.getUnderUserByUserIdAndOrgId(account, orgCode);
		RestfulReturnUtil.bulidListResult(rtn, list);
		return rtn;
	}

	@Override
	public CommonResult<String> addOrgUser(String orgCode, String account) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据组织代码没有找到组织！");
		}
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		if(BeanUtils.isNotEmpty(orgUserManager.getListByOrgIdUserId(r.getId(), user.getId()))){
			throw new RuntimeException("用户【"+account+"】与组织【"+r.getName()+"--（"+r.getCode()+"）】已存在关系");
		}
		OrgUser ou = new OrgUser();
		ou.setId(UniqueIdUtil.getSuid());
		ou.setOrgId(r.getId());
		ou.setUserId(user.getId());
		ou.setRelId(null);
		ou.setIsCharge(0);
		ou.setIsMaster(0);
		orgUserManager.create(ou);
		return new CommonResult<String>(true, "组织加入用户成功！", null);
	}

	@Override
	public CommonResult<String> addOrgUser(String orgCode, UserRestfulObject user) throws Exception {
		
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据组织代码没有找到组织！");
		}
		//新增用户
		addUser(user);
		User u = userManager.getByAccount(user.getAccount());
		//用户组织关系
		OrgUser ou = new OrgUser();
		ou.setId(UniqueIdUtil.getSuid());
		ou.setOrgId(r.getId());
		ou.setUserId(u.getId());
		ou.setRelId(null);
		ou.setIsCharge(0);
		ou.setIsMaster(1);
		orgUserManager.create(ou);
		return new CommonResult<String>(true, "组织新增用户成功！", null);
	}

	@Override
	public User getUser(String account) throws Exception {
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		return user;
	}

	@Override
	public CommonResult<String> delOrgUser(String orgCode, String account) throws Exception {
		if(StringUtil.isEmpty(orgCode)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：组织代码必填！");
		}
		Org r = orgManager.getByCode(orgCode);
		if(BeanUtils.isEmpty(r)){
			throw new RuntimeException("根据组织代码没有找到组织！");
		}
		if(StringUtil.isEmpty(account)){
			throw new RequiredException(HotentHttpStatus.REUIRED.description()+"：用户帐号必填！");
		}
		User user = userManager.getByAccount(account);
		if(BeanUtils.isEmpty(user)){
			throw new RuntimeException("根据输入的帐号信息获取不到用户！");
		}
		orgUserManager.removeByOrgIdUserId(r.getId(), user.getId());
		return new CommonResult<String>(true, "删除用户组织关系成功！", null);
	}

	@Override
	public JSONObject getOrgList(UserOrgQueryFilter filter) throws Exception {
		DefaultQueryFilter f = buildDefaultFilter(filter);
		if(StringUtil.isNotEmpty(filter.getName())){
			f.addFilter("name_", filter.getName(), QueryOP.LIKE);
		}
		if(StringUtil.isNotEmpty(filter.getCode())){
			f.addFilter("code_", filter.getCode(), QueryOP.LIKE);
		}
		JSONObject rtn = new JSONObject();
		PageList<Org> list = (PageList<Org>) orgManager.query(f);
		RestfulReturnUtil.buildListReuslt(rtn, list, f);
		return rtn;
	}

	@Override
	public JSONObject getRoleList(UserOrgQueryFilter filter) throws Exception {
		DefaultQueryFilter f = buildDefaultFilter(filter);
		if(StringUtil.isNotEmpty(filter.getName())){
			f.addFilter("name_", filter.getName(), QueryOP.LIKE);
		}
		if(StringUtil.isNotEmpty(filter.getCode())){
			f.addFilter("alias_", filter.getCode(), QueryOP.LIKE);
		}
		JSONObject rtn = new JSONObject();
		PageList<Role> list = (PageList<Role>) roleManager.query(f);
		RestfulReturnUtil.buildListReuslt(rtn, list, f);
		return rtn;
	}

	@Override
	public JSONObject getOrgRelList(UserOrgQueryFilter filter) throws Exception {
		DefaultQueryFilter f = buildDefaultFilter(filter);
		if(StringUtil.isNotEmpty(filter.getName())){
			f.addFilter("rel_name_", filter.getName(), QueryOP.LIKE);
		}
		if(StringUtil.isNotEmpty(filter.getCode())){
			f.addFilter("rel_code_", filter.getCode(), QueryOP.LIKE);
		}
		JSONObject rtn = new JSONObject();
		PageList<OrgRel> list = (PageList<OrgRel>) relManager.query(f);
		RestfulReturnUtil.buildListReuslt(rtn, list, f);
		return rtn;
	}

	@Override
	public JSONObject getOrgReldefList(UserOrgQueryFilter filter) throws Exception {
		DefaultQueryFilter f = buildDefaultFilter(filter);
		if(StringUtil.isNotEmpty(filter.getName())){
			f.addFilter("name_", filter.getName(), QueryOP.LIKE);
		}
		if(StringUtil.isNotEmpty(filter.getCode())){
			f.addFilter("code_", filter.getCode(), QueryOP.LIKE);
		}
		JSONObject rtn = new JSONObject();
		PageList<OrgReldef> list = (PageList<OrgReldef>) reldefManager.query(f);
		RestfulReturnUtil.buildListReuslt(rtn, list, f);
		return rtn;
	}

}

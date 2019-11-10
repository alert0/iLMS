package com.hanthink.gps.pub.web;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.hanthink.gps.pub.service.RoleService;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.RoleVO;
import com.hanthink.gps.pub.vo.TreeVO;
import com.hanthink.gps.pub.vo.UserVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.sequence.CommonUtil;

public class RoleAction extends BaseActionSupport{


	/***/
	private static final long serialVersionUID = 7075960514501015340L;
	//角色名最大长度
	private final int ROLE_NAME_BYTE_LENGTH = 40;
	//角色描述最大长度
	private final int ROLE_DESC_BYTE_LENGTH = 100;
	
	// 角色
	private RoleVO roleVo;
	// 角色权限json字符串
	private String jsonStr;
	// service
	private RoleService service;
	//工厂
	private FactoryVO factoryVO;
	//用户
	private UserVO userVO;
	
	
	
	/**
	 * 检索
	 */
	public void queryForPage() {
		// 根据检索条件，取得用户一览信息
		PageObject o = service.queryForPage(roleVo, start, limit);
		writeJson(o);
	}
	
	/**
	 * 查询工厂信息
	 */
	public void queryFactoryForPage(){
		
		PageObject o = service.queryFactoryForPage(factoryVO, start, limit);
		writeJson(o);
	}
	
	/**
	 * 查询用户信息
	 */
	public void queryUserInfoForPage(){
		
		PageObject o = service.queryUserInfoForPage(userVO, start, limit);
		writeJson(o);
	}
	
	/**
	 * 查询供应商信息
	 */
	public void querySupplyForPage(){
	
		PageObject o = service.querySupplyForPage(userVO, start, limit);
		writeJson(o);
	}
	
	
	/**
	 * 检索角色的权限配置信息
	 */
	public void queryRolePrivilege() {
        LogUtil.info("检索角色的权限配置信息开始");
		// check角色Id
		validateRoleId();
		if(isInvalid())return;
		
		String treeNodes = service.queryRolePrivilege(roleVo);
		writeTreeJson(treeNodes);
        LogUtil.info("检索角色的权限配置信息结束");
	}
	
	/**
	 * 新增
	 */
	public void insert() {
		// check角色Id
		validateRoleForInsert();
		if(isInvalid())return;
		UserVO loginInfo = new UserVO();
		loginInfo =(UserVO) session.getAttribute(Constants.USER_KEY);
	    if(roleVo!=null){
	    	roleVo.setCreateUser(loginInfo.getUserName());
	    }
	   
		service.insert(roleVo);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		// check角色Id
		validateRoleId();
		if(isInvalid())return;
		UserVO loginInfo = new UserVO();
		loginInfo =(UserVO) session.getAttribute(Constants.USER_KEY);
	    if(roleVo!=null){
	    	roleVo.setCreateUser(loginInfo.getUserName());
	    }
		if(service.delete(roleVo) == 2){
			addError(Constants.MSG_ID_E_UNUSE_FAIL, "角色"+roleVo.getRoleName());
			writeError();
		} else {
			write(Constants.MSG_SUCCESS);
		}
	}
	
	/**
	 * 新增工厂
	 */
	public void addFactory(){
		service.insert(factoryVO);
	}
	
	/**
	 * 更新
	 */
	public void update() {
		// check角色
		validateRoleForInsert();
		if(isInvalid())return;
		
		service.update(roleVo);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 更新角色权限信息
	 * @throws JSONException 
	 */
	public void updatePrivilege() throws JSONException {
		// check角色
		List<TreeVO> rolePrivs = CommonUtil.jsonToBeanList(TreeVO.class, jsonStr);
		System.out.println(jsonStr);
		service.updatePrivilege(rolePrivs);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 根据角色Id检索
	 */
	public void queryByRoleId() {
		// check角色Id
		validateRoleId();
		if(isInvalid())return;
		
		RoleVO vo = service.queryByRoleId(roleVo);
		writeJson(vo,true);
	}
	
	public void ModifyUserSup(){
		String strJson =userVO.getSupplierGroup();
		String userName =userVO.getUserName();
		String delFlg =userVO.getDelFlg();
		//操作供应商组
		JSONArray js=JSONArray.fromObject(strJson);
		List<UserVO> list =new ArrayList<UserVO>();
		UserVO loginInfo = new UserVO();
		loginInfo =(UserVO) session.getAttribute(Constants.USER_KEY);
		if(js==null || js.size()<=0){
			writeJson("操作失败");
		}
		for(int i=0;i<js.size();i++){
			UserVO vo =new UserVO();
			vo.setSupplierNo(String.valueOf(js.get(i)));
			vo.setDelFlg(delFlg);
			vo.setUserName(userName);
			if(loginInfo.getUserName()!=null){
				vo.setOpeUser(loginInfo.getUserName());
			}
			list.add(vo);
		}
		int status =service.modifyUserSupplier(list);
		if(status>0){
			writeJson("操作成功",true);
		}else{
			writeJson("操作失败",true);
		}
		
	}
	
	/**
	 * 查询用户工厂关系
	 */
	public void queryUserForPage(){
	
		PageObject o = service.queryUserFactoryForPage(factoryVO, start, limit);
		writeJson(o);
	}
	
	//新增/删除用户与工厂关系
	public void ModifyUserFac(){
		String strJson =factoryVO.getUserGroup();
		String factoryCode =factoryVO.getFactoryCode();
		String type =factoryVO.getType();
		//操作用户组
		JSONArray js=JSONArray.fromObject(strJson);
		List<FactoryVO> list =new ArrayList<FactoryVO>();
		UserVO loginInfo = new UserVO();
		loginInfo =(UserVO) session.getAttribute(Constants.USER_KEY);
		if(js==null || js.size()<=0){
			writeJson("操作失败",false);
		}
		for(int i=0;i<js.size();i++){
			FactoryVO vo =new FactoryVO();
			vo.setUserName(String.valueOf(js.get(i)));
			vo.setType(type);
			vo.setFactoryCode(factoryCode);
			if(loginInfo.getUserName()!=null){
				vo.setOpeName(loginInfo.getUserName());
			}
			list.add(vo);
		}
		int status =service.modifyUserFactory(list);
		if(status>0){
			writeJson("操作成功",true);
		}else{
			writeJson("操作失败",false);
		}
	}
	
	/**
	 *  check插入的角色信息
	 */
	private void validateRoleForInsert(){
		// 非空check
		if(StringUtil.isNullOrEmpty(roleVo.getRoleName())){
			addError("", "");
		}

		if(StringUtil.isNullOrEmpty(roleVo.getRoleStatus())){
			addError("", "");
		}
		// 长度check
		if(StringUtil.getStrLength(roleVo.getRoleName()) > ROLE_NAME_BYTE_LENGTH){
			addError("", "");
		}
		if(StringUtil.getStrLength(roleVo.getRoleDesc()) > ROLE_DESC_BYTE_LENGTH){
			addError("", "");
		}
	}
	
	/**
	 *  check角色Id
	 */
	private void validateRoleId(){
		if(StringUtil.isNullOrEmpty(roleVo.getRoleId())){
			addError("", "");
		}
	}
	
	
	public void updateFactoryModule(){
		service.updateFactory(factoryVO);
		writeJson(Constants.MSG_SUCCESS,true);
	}
	

	public RoleService getService() {
		return service;
	}

	public void setService(RoleService service) {
		this.service = service;
	}

	public RoleVO getRoleVo() {
		return roleVo;
	}

	public void setRoleVo(RoleVO roleVo) {
		this.roleVo = roleVo;
	}
	
	
	
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public FactoryVO getFactoryVO() {
		return factoryVO;
	}

	public void setFactoryVO(FactoryVO factoryVO) {
		this.factoryVO = factoryVO;
	}
	
	


	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	

}

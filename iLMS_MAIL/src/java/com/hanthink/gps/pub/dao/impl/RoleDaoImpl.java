package com.hanthink.gps.pub.dao.impl;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrivilegeVO;
import com.hanthink.gps.pub.vo.RolePrivilegeVO;
import com.hanthink.gps.pub.vo.UserVO;
import com.hanthink.gps.pub.dao.RoleDao;
import com.hanthink.gps.util.annotation.VoInsert;
import com.hanthink.gps.util.annotation.VoUpdate;
import com.hanthink.gps.util.sequence.SeqManager;
import com.hanthink.gps.util.sequence.SeqType;
import com.hanthink.gps.pub.vo.RoleVO;
/**
 * 权限管理Dao
 * @author thw
 */
public class RoleDaoImpl extends BaseDaoSupport implements RoleDao {
	
	/**
	 * 根据指定的开始条数和查询条数，取得角色信息。
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject queryForPage(RoleVO queryRole, int start, int limit) {
		
		return executeQueryForPage("pub.select_role", queryRole, start, limit);
	}
	/**
	 * 根据角色ID，取得角色信息。
	 * @param queryRole 角色
	 * @return 查询结果
	 */
	public RoleVO queryByRoleId(RoleVO queryRole){
		return (RoleVO)executeQueryForObject("pub.select_role", queryRole);
	}
	
	/**
	 * 新增角色
	 * @param roleVo 角色
	 * @return 查询结果
	 */
	@VoInsert
	public RoleVO insert(RoleVO role){
		//角色ID
		role.setRoleId(SeqManager.getSeq(SeqType.SEQ_TYPE_ROLE_ID));
		return (RoleVO)executeInsert("pub.insert_role",role);
	}
	/**
	 * 更新角色
	 * @param roleVo 角色
	 * @return 查询结果
	 */
	@VoUpdate
	public int update(RoleVO role){
		return executeUpdate("pub.update_role",role);
	}
	/**
	 * 供应商角色查询
	 */
	public PageObject querySupRoleForPage(RoleVO role, int start, int limit) {
		
		return executeQueryForPage("pub.select_supRole", role, start, limit);
	}
	/**
	 * 供应商角色新增
	 */
	@VoInsert
	public RoleVO insertSupRole(RoleVO role){
		//角色ID
		role.setRoleId(SeqManager.getSeq(SeqType.SEQ_TYPE_ROLE_ID));
		
		return (RoleVO)executeInsert("pub.insert_supRole",role);
	}
	/**
	 * 判断角色名称是否已经存在
	 */
	public boolean isNameExit(RoleVO role) {
		int m=java.lang.Integer.valueOf(this.executeQueryForObject("pub.query_isNameExit", role).toString());
		boolean a=false;
		if(m>0){
			a=true;
		}
		return a;
	}
	/**
	 * 修改供应商角色
	 */
	@VoUpdate
	public int updateSupRole(RoleVO role){
		return executeUpdate("pub.update_supRole",role);
	}
	
	public RoleVO queryBySupRoleId(RoleVO roleVo){
		return (RoleVO)executeQueryForObject("pub.select_supRole", roleVo);
	}
	public int deleteOldPrivilege(String roleId) {
		
		return this.executeDelete("pub.delete_oldPrivilege", roleId);
	}
	public void addPrivilege(RolePrivilegeVO privilegeVo) {
	
		this.executeUpdate("pub.insert_newPrivilege", privilegeVo);
	}
	public PageObject queryFactoryForPage(FactoryVO factoryVO, int start,
			int limit) {
		
		return this.executeQueryForPage("pub.select_factory",factoryVO, start, limit);
	}
	public FactoryVO insertFactory(FactoryVO factoryVO) {
		
		return (FactoryVO) this.executeInsert("pub.insert_factory",factoryVO);
	}
	public PageObject queryUserFactoryForPage(FactoryVO factoryVO, int start,
			int limit) {
		if(!factoryVO.getType().equals("1")){
			return this.executeQueryForPage("pub.select_userFactory", factoryVO, start, limit);
		}else{
			return this.executeQueryForPage("pub.select_userFactory0", factoryVO, start, limit);

		}
	}
	public int deleteUserFactory(FactoryVO factoryVO) {
	
		return this.executeDelete("pub.delete_userFactory",factoryVO);
	}
	public int insertUserFactory(FactoryVO factoryVO) {
		
		return this.executeUpdate("pub.insert_userFactory",factoryVO);
	}
	public int updateFactory(FactoryVO factoryVO) {
		
		return this.executeUpdate("pub.update_factory",factoryVO);
	}
	public PageObject queryUserInfoForPage(UserVO userVO, int start, int limit) {
		
	
		return this.executeQueryForPage("pub.select_userInfo",userVO,start, limit);
	}
	public PageObject querySupplyForPage(UserVO userVO, int start, int limit) {
		System.out.println(userVO.getSupplierName()+"-"+userVO.getSupplierNo());
		if("add".equals(userVO.getDelFlg())){
			return this.executeQueryForPage("pub.select_supplyInfoAdd",userVO, start, limit);
		}else if("delete".equals(userVO.getDelFlg())){
			return this.executeQueryForPage("pub.select_supplyInfoDelete",userVO, start, limit);

		}
		return null;
	}
	public int deleteUserSupply(UserVO userVO) {
		
		return this.executeDelete("pub.deleteUserSup",userVO);
	}
	public int insertUserSupply(UserVO userVO) {
	
		return  this.executeUpdate("pub.insertUserSup", userVO);
	}
	

}

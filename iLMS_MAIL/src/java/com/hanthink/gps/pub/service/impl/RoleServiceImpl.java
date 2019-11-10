package com.hanthink.gps.pub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



import net.sf.json.JSONArray;

import com.hanthink.gps.pub.dao.RoleDao;
import com.hanthink.gps.pub.dao.RolePrivilegeDao;
import com.hanthink.gps.pub.service.RoleService;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.Node;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.RolePrivilegeVO;
import com.hanthink.gps.pub.vo.RoleVO;
import com.hanthink.gps.pub.vo.TreeVO;
import com.hanthink.gps.pub.vo.UserVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.StringUtil;

public class RoleServiceImpl extends BaseServiceImpl implements RoleService{
	// 角色管理dao
	private RoleDao roleDao;
	//角色权限do
	private RolePrivilegeDao rolePrivilegeDao;
	
	
	public PageObject queryForPage(RoleVO queryRole, int start, int limit) {
		// TODO Auto-generated method stub
		return roleDao.queryForPage(queryRole, start, limit);
	}
	
	/**
	 * 检索角色的权限配置信息
	 * 
	 * @param roleVo
	 *            角色
	 * @return 查询结果
	 * @author 谭皓文
	 */
	public String queryRolePrivilege(RoleVO role){
		// 所有权限
		
		List<RolePrivilegeVO> dataList = rolePrivilegeDao
				.queryOperPrivsByRoleId(role.getRoleId());
		//节点列表
		HashMap nodeList = new HashMap();
		//根节点
		Node root = new Node();
		//根据结果集构造节点列表（存入散列表）
		for(int i=0 ;i<dataList.size();i++){
			RolePrivilegeVO dataRecord = dataList.get(i);
			Node node = new Node();
			node.id = dataRecord.getPrivilegeCode();
			node.checked = dataRecord.getChecked().equals("0")?false:true;
			node.parentId = dataRecord.getParentCode();
			node.menuName =dataRecord.getMenuName();
			node.level = dataRecord.getLevel();
			nodeList.put(node.id, node);
		}
		root.id="0";
		nodeList.put(root.id, root);
		Set entrySet = nodeList.entrySet();
		for(Iterator it=entrySet.iterator();it.hasNext();){
			Node node =(Node)((Map.Entry)it.next()).getValue();
			if(node.parentId != null && !node.parentId.equals("")){
				((Node)nodeList.get(node.parentId)).addChild(node);
			}
			
			
		}
		String str = root.toString();
		int begin = str.indexOf("[");
		int end = str.lastIndexOf("]")+1;
	
        return str.substring(begin,end);
		}
		
		
	
	
	
	public int delete(RoleVO role) {
		if (isRoleAlreadInUse(role)) {
			return 2;
		}
		role.setRoleStatus("0");
		return roleDao.update(role);
	}
	
	public void updatePrivilege(List<TreeVO> rolePrivs) {
		String roleId="";
		roleId = rolePrivs.get(0).getRoleId();
		Map<String, String> privilegeMap = new HashMap<String, String>();
		//删除原有权限
		roleDao.deleteOldPrivilege(roleId);
		System.out.println("roleID="+roleId);
		for(int i=0;i<rolePrivs.size();i++){
			TreeVO vo =rolePrivs.get(i);
			
			//获取选中的权限
			if(vo.getCheckStatus().equals("1")){
				RolePrivilegeVO node =new RolePrivilegeVO();
				//操作权限
				if(vo.getLevel().equals("5")){
					node.setRoleId(roleId);
					node.setPrivilegeId(vo.getId());
					node.setPrivilegeType("2");
					roleDao.addPrivilege(node);	
					//页面权限
				}else if(vo.getLevel().equals("4")){
					node.setRoleId(roleId);
					node.setPrivilegeId(vo.getId());
					node.setPrivilegeType("1");
					roleDao.addPrivilege(node);	
				}
				
				
			}	
		}
    }
	
	/**
	 * 检查角色是不是已经被使用了
	 * 
	 * @param role
	 * @return
	 */
	private boolean isRoleAlreadInUse(RoleVO role) {
		if (rolePrivilegeDao.selectInUseRole(role) != null) {
			return true;
		}
		return false;
	}
	
	public int update(RoleVO role) {
		return roleDao.update(role);
	}
	
	public RoleVO queryByRoleId(RoleVO queryRole) {
		return roleDao.queryByRoleId(queryRole);
	}
	
	public RoleVO insert(RoleVO role) {
		return roleDao.insert(role);
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public RolePrivilegeDao getRolePrivilegeDao() {
		return rolePrivilegeDao;
	}

	public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
	}

	public PageObject queryFactoryForPage(FactoryVO factoryVO, int start,
			int limit) {
	
		return this.roleDao.queryFactoryForPage(factoryVO, start, limit);
	}

	public FactoryVO insert(FactoryVO factoryVO) {
	
		return this.roleDao.insertFactory(factoryVO);
	}

	public PageObject queryUserFactoryForPage(FactoryVO factoryVO, int start,
			int limit) {
	
		return this.roleDao.queryUserFactoryForPage(factoryVO, start, limit);
	}

	public int modifyUserFactory(List<FactoryVO> list) {
		int status=0;
		for(int i=0;i<list.size();i++){
			FactoryVO factoryVO =list.get(i);
			//添加用户与工厂关系
			if("add".equals(factoryVO.getType())){
				status =this.roleDao.insertUserFactory(factoryVO);	
			}else if("delete".equals(factoryVO.getType())){
				//删除用户与工厂关系
				status =this.roleDao.deleteUserFactory(factoryVO);
			}
			if(status<=0){
				return 0;
			}
		}
		
		return 1;
	}

	public int updateFactory(FactoryVO factoryVO) {
		
		return roleDao.updateFactory(factoryVO);
	}

	public PageObject queryUserInfoForPage(UserVO userVO, int start, int limit) {
	
		return roleDao.queryUserInfoForPage(userVO, start, limit);
	}

	public PageObject querySupplyForPage(UserVO userVO, int start, int limit) {
	
		return roleDao.querySupplyForPage(userVO, start, limit);
	}

	public int modifyUserSupplier(List<UserVO> list) {
		int status=0;
		for(int i=0;i<list.size();i++){
			UserVO userVO =list.get(i);
			if("add".equals(userVO.getDelFlg())){
				status =this.roleDao.insertUserSupply(userVO);	
			}else if("delete".equals(userVO.getDelFlg())){
				status =this.roleDao.deleteUserSupply(userVO);
			}
			if(status<=0){
				return 0;
			}
		}
		return 1;
		
	}

	

	
    
	

}

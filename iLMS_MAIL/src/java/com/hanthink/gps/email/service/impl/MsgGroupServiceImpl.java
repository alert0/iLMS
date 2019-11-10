package com.hanthink.gps.email.service.impl;

import com.hanthink.gps.email.dao.MsgGroupDao;
import com.hanthink.gps.email.service.MsgGroupService;
import com.hanthink.gps.email.vo.MsgGroupVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.pub.vo.PageObject;

public class MsgGroupServiceImpl extends BaseServiceImpl implements
		MsgGroupService {
    private MsgGroupDao groupDao;
    
	/**
	 * 分组信息查询
	 */
	public PageObject queryGroupForPage(MsgGroupVO groupVO, int start, int limit) {
		return groupDao.queryGroupForPage(groupVO,start,limit);
	}
	
	/**
	 * 分组人员信息查询
	 */
	public PageObject queryUserForPage(String groupCode, int start, int limit) {
		return groupDao.queryUserForPage(groupCode,start,limit);
	}
	
	/**
	 * 分组名称修改
	 */
	public void updateGroup(MsgGroupVO groupVO) {
		groupDao.updateGroup(groupVO);		
	}
	
	/**
	 * 新增分组信息
	 */
	public void insertGroup(MsgGroupVO groupVO) {
		groupDao.insertGroup(groupVO);
	}
	
	/**
	 * 删除分组信息
	 */
	public void deleteGroup(MsgGroupVO groupVO) {
		groupDao.deleteGroup(groupVO);
	}
	
	/**
	 * 删除分组下的人员分组
	 */
	public void deleteGroupUser(MsgGroupVO groupVO) {
		groupDao.deleteGroupUser(groupVO);
	}
	
	/**
	 * 新增系统人员
	 */
	public void addUser(MsgGroupVO msgGroupVO, String[] userIdArr) {
		groupDao.addUser(msgGroupVO, userIdArr);	
	}
	
	/**
	 * 查询分组是否重复
	 */
	public PageObject queryRepGroupForPage(MsgGroupVO groupVO, int start,
			int limit) {
		return groupDao.queryRepGroupForPage(groupVO,start,limit);
	}


	/**
	 * 查询未分配用户
	 */
	public PageObject queryNotUserForPage(MsgGroupVO groupVO, int start,
			int limit) {
		return groupDao.queryNotUserForPage(groupVO, start, limit);
	}

	/**
	 * 批量删除用户信息
	 */
	public Object batchDeleteUser(MsgGroupVO groupVO) {
		return groupDao.batchDeleteUser(groupVO);
	}

	/**
	 * 新增例外人员
	 */
	public void addExcUser(MsgGroupVO groupVO) {
		groupDao.addExcUser(groupVO);
	}

	/**
	 * 修改用户
	 */
	public void updateUser(MsgGroupVO groupVO) {
		groupDao.updateUser(groupVO);		
	}

	
	public MsgGroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(MsgGroupDao groupDao) {
		this.groupDao = groupDao;
	}


	
	

	
}

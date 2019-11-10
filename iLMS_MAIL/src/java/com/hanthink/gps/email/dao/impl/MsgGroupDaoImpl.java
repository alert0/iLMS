package com.hanthink.gps.email.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hanthink.gps.email.dao.MsgGroupDao;
import com.hanthink.gps.email.vo.MsgGroupVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;

public class MsgGroupDaoImpl extends BaseDaoSupport implements MsgGroupDao {

	/**
	 * 分组信息查询
	 */
	public PageObject queryGroupForPage(MsgGroupVO groupVO, int start, int limit) {
		return this.executeQueryForPage("email.select_queryGroupForPage",
				groupVO, start, limit);
	}

	/**
	 * 分组人员信息查询
	 */
	public PageObject queryUserForPage(String groupCode, int start, int limit) {
		return this.executeQueryForPage("email.select_queryUserForPage",
				groupCode, start, limit);
	}

	/**
	 * 分组名称查询
	 * 
	 * @return
	 */
	public int updateGroup(MsgGroupVO groupVO) {
		return executeUpdate("email.update_updateGroup", groupVO);
	}

	/**
	 * 新增分组信息
	 * 
	 * @return
	 */
	public Object insertGroup(MsgGroupVO groupVO) {
		return this.executeInsert("email.insert_insertGroup", groupVO);
	}

	/**
	 * 分组信息删除
	 * 
	 * @return
	 */
	public int deleteGroup(MsgGroupVO groupVO) {
		return this.executeDelete("email.delete_deleteGroup", groupVO);
	}

	/**
	 * 新增系统人员
	 */
	public void addUser(MsgGroupVO msgGroupVO, String[] userIdArr) {
		List<MsgGroupVO> msgGroupVOs = new ArrayList<MsgGroupVO>();
		for (int i = 0; i < userIdArr.length; i++) {
			MsgGroupVO vo = new MsgGroupVO();
			vo.setGroupCode(msgGroupVO.getGroupCode());
			vo.setUserId(userIdArr[i]);
			msgGroupVOs.add(vo);
			this.executeInsert("email.insert_addUser", vo);
		}
//		this.executeBatchInsert("email.insert_addUser", msgGroupVOs);
	}

	/**
	 * 查询未配置人员信息
	 */
	public PageObject queryNotUserForPage(MsgGroupVO groupVO, int start,
			int limit) {
		return this.executeQueryForPage("email.select_queryNotUserForPage",
				groupVO, start, limit);
	}

	/**
	 * 批量删除人员信息
	 */
	public Object batchDeleteUser(MsgGroupVO groupVO) {
		return this.executeDelete("email.delete_batchDeleteUser", groupVO);
	}

	/**
	 * 新增例外人员
	 */
	public Object addExcUser(MsgGroupVO groupVO) {
		return this.executeInsert("email.insert_addExcUser", groupVO);
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	public int updateUser(MsgGroupVO groupVO) {
		return executeUpdate("email.update_updateUser", groupVO);
	}

	/**
	 * 删除分组下的人员
	 */
	public int deleteGroupUser(MsgGroupVO groupVO) {
		return this.executeDelete("email.delete_deleteGroupUser", groupVO);
	}

	/**
	 * 查询分组代码是否重复
	 */
	public PageObject queryRepGroupForPage(MsgGroupVO groupVO, int start, int limit) {
		return this.executeQueryForPage("email.select_queryRepGroupForPage",
				groupVO, start, limit);
	}

}


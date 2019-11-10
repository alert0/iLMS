package com.hanthink.gps.email.dao;

import com.hanthink.gps.email.vo.MsgGroupVO;
import com.hanthink.gps.pub.vo.PageObject;

public interface MsgGroupDao {

	/**
	 * 分组信息查询
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 *@param start
	 *@param limit
	 *@return
	 */
	PageObject queryGroupForPage(MsgGroupVO groupVO, int start, int limit);

	/**
	 * 分组人员信息查询
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupCode
	 *@param start
	 *@param limit
	 *@return
	 */
	PageObject queryUserForPage(String groupCode, int start, int limit);

	/**
	 * 分组名称修改
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 */
	int updateGroup(MsgGroupVO groupVO);

	/**
	 * 新增分组信息
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 */
	Object insertGroup(MsgGroupVO groupVO);

	/**
	 * 分组信息删除
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 */
	int deleteGroup(MsgGroupVO groupVO);

	/**
	 * 新增系统人员
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param msgGroupVO
	 *@param userIdArr
	 */
	void addUser(MsgGroupVO msgGroupVO, String[] userIdArr);

	/**
	 * 查询未配置人员
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 *@param start
	 *@param limit
	 *@return
	 */
	PageObject queryNotUserForPage(MsgGroupVO groupVO, int start, int limit);

	/**
	 * 批量删除人员信息
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 *@return
	 */
	Object batchDeleteUser(MsgGroupVO groupVO);

	/**
	 * 新增例外人员
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 */
	Object addExcUser(MsgGroupVO groupVO);

	/**
	 * 修改用户信息
	 *@author smy
	 *@date 2016-8-1
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 */
	int updateUser(MsgGroupVO groupVO);

	/**
	 * 删除分组下的人员
	 *@author smy
	 *@date 2016-8-8
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 *@return
	 */
	int deleteGroupUser(MsgGroupVO groupVO);

	/**
	 * 查询分组名称是否重复
	 *@author smy
	 *@date 2016-8-8
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 *@param start
	 *@param limit
	 *@return
	 */
	PageObject queryRepGroupForPage(MsgGroupVO groupVO, int start, int limit);

}

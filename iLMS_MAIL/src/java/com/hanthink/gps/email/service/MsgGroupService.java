package com.hanthink.gps.email.service;

import com.hanthink.gps.email.vo.MsgGroupVO;
import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.PageObject;

/**
 * 分组人员维护Service
 * @author smy
 * @date 2016-7-28
 */
public interface MsgGroupService extends BaseService {

	/**
	 * 分组信息查询
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
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
	 *@param pageVO
	 */
	void updateGroup(MsgGroupVO groupVO);

	/**
	 * 新增分组信息
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 */
	void insertGroup(MsgGroupVO groupVO);

	/**
	 * 删除分组信息
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 */
	void deleteGroup(MsgGroupVO groupVO);

	/**
	 * 新增未配置用户
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param msgGroupVO
	 *@param userIdArr
	 */
	void addUser(MsgGroupVO msgGroupVO, String[] userIdArr);

	/**
	 * 查询未配置用户
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 *@param start
	 *@param limit
	 *@return
	 */
	PageObject queryNotUserForPage(MsgGroupVO groupVO, int start, int limit);

	/**
	 * 删除
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param groupVO
	 */
	Object batchDeleteUser(MsgGroupVO groupVO);
 
	/**
	 * 新增例外人员
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 */
	void addExcUser(MsgGroupVO groupVO);

	/**
	 * 修改用户
	 *@author smy
	 *@date 2016-8-1
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 */
	void updateUser(MsgGroupVO groupVO);

	/**
	 * 删除分组下的人员分组
	 *@author smy
	 *@date 2016-8-8
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@param pageVO
	 */
	void deleteGroupUser(MsgGroupVO pageVO);

	/**
	 * 查询分组是否重复
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

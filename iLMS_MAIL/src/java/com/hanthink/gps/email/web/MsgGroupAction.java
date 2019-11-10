package com.hanthink.gps.email.web;

import com.hanthink.gps.email.service.MsgGroupService;
import com.hanthink.gps.email.vo.MsgGroupVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.web.BaseActionSupport;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;

/**
 * 分组人员维护Action
 * 
 * @author smy
 * @date 2016-7-28
 */
public class MsgGroupAction extends BaseActionSupport {

	private static final long serialVersionUID = 6133796124432279123L;
	private MsgGroupVO pageVO;
	private MsgGroupService service;
	private String groupCode;

	/** 供应商代码数组 */
	private String[] userIdArr;

	public MsgGroupVO getPageVO() {
		return pageVO;
	}

	public void setPageVO(MsgGroupVO pageVO) {
		this.pageVO = pageVO;
	}

	public MsgGroupService getService() {
		return service;
	}

	public void setService(MsgGroupService service) {
		this.service = service;
	}

	/**
	 * 分组维护信息查询
	 * 
	 * @author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void queryGroupForPage() {
		PageObject o = service.queryGroupForPage(pageVO, start, limit);
		writeJson(o);
	}

	/**
	 * 分组人员信息查询
	 * 
	 * @author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void queryUserForPage() {
		PageObject o = service.queryUserForPage(groupCode, start, limit);
		writeJson(o);
	}

	/**
	 * 分组名称修改
	 * 
	 * @author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate
	 *@param pageVO
	 */
	public void updateGroup() {
		pageVO.setModifyId(userInfo.getUserName());
		service.updateGroup(pageVO);
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 分组信息新增
	 * 
	 * @author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void addGroup() {
		PageObject o = service.queryRepGroupForPage(pageVO, 0, 1);
		if(o.getTotalCount()>0){
			addError("该类型已存在");
			writeError();
			return;
		}else{
		pageVO.setEntryId(userInfo.getUserName());
		service.insertGroup(pageVO);	
		}
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 删除分组信息
	 * 
	 * @author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate
	 *@param groupCode
	 */
	public void deleteGroup() {
		service.deleteGroup(pageVO);
		service.deleteGroupUser(pageVO);
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 新增系统人员
	 *@author smy
	 *@date 2016-8-1
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void addUser() {
		if (null == userIdArr || 0 >= userIdArr.length
				|| StringUtil.isNullOrEmpty(groupCode)) {
			addError("参数获取失败");
		}
		if (isInvalid()) {
			return;
		}
		MsgGroupVO msgGroupVO = new MsgGroupVO();
		msgGroupVO.setEntryId(userInfo.getUserName());
		msgGroupVO.setGroupCode(groupCode);
		service.addUser(msgGroupVO, userIdArr);
		StringBuffer userIds = new StringBuffer();
		for (int i = 0; i < userIdArr.length; i++) {
			userIds.append(userIdArr[i]);
			userIds.append(",");
		}
		LogUtil.info(userInfo.getUserName() + "增加分组" + groupCode + ":"
				+ userIds.toString());
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 查询未配置的用户信息
	 */
	public void queryNotUserForPage() {
		PageObject o = service.queryNotUserForPage(pageVO, start, limit);
		writeJson(o);
	}

	/**
	 * 批量删除人员信息
	 *@author smy
	 *@date 2016-8-1
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void batchDeleteUser() {
		String delStr = pageVO.getDelList();
		String[] sourceStrArray = delStr.split(",");
		int maxSplit = 1;
		for (int i = 0; i < (sourceStrArray.length / maxSplit); i++) {

			MsgGroupVO groupVO = new MsgGroupVO();
			groupVO.setPkId(sourceStrArray[i * maxSplit]);
			// 删除模块
			service.batchDeleteUser(groupVO);
			write(Constants.MSG_SUCCESS);
		}
	}

	/**
	 * 新增例外人员
	 *@author smy
	 *@date 2016-7-29
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void addExcUser() {
		pageVO.setEntryId(userInfo.getUserName());
		service.addExcUser(pageVO);
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 修改用户
	 * 
	 * @author smy
	 *@date 2016-8-1
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void updateUser() {
		service.updateUser(pageVO);
		write(Constants.MSG_SUCCESS);
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public String[] getUserIdArr() {
		return userIdArr;
	}

	public void setUserIdArr(String[] userIdArr) {
		this.userIdArr = userIdArr;
	}

}

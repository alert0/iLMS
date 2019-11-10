package com.hanthink.gps.email.web;

import java.util.List;

import com.hanthink.gps.email.service.EmailManageService;
import com.hanthink.gps.email.vo.EmailManageVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.web.BaseActionSupport;
import com.hanthink.gps.util.Constants;

/**
 * 定时器、分组人员维护Action
 * 
 * @author smy
 * @date 2016-7-27
 */
public class EmailManageAction extends BaseActionSupport {

	private static final long serialVersionUID = 6058050039153072160L;

	private EmailManageVO pageVO;
	private EmailManageService service;
	private List<String> groups;
	/**
	 * 定时器管理信息查询
	 *@author smy
	 *@date 2016-7-27
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void queryEmailTimerForPage(){
		pageVO.setAppType(Constants.CUR_APP_TYPE);
		PageObject o = service.queryEmailTimerForPage(pageVO, start, limit);
		writeJson(o);
	}
	
	/**
	 * 定时器信息修改
	 *@author smy
	 *@date 2016-7-28
	 *@ModifiedUser
	 *@ModifiedDate 
	 *@return
	 */
	public void update(){
		pageVO.setModifyId(userInfo.getUserName());
		if (pageVO.getRunState().equals("停止")) {
			pageVO.setRunState("0");
		}
		
		if (pageVO.getRunState().equals("运行中")) {
			pageVO.setRunState("1");
		}
		service.updateEmail(pageVO);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 配置
	 *@author smy
	 *@date 2016-8-8
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void configGroup(){
		pageVO.setEntryId(userInfo.getUserName());
		service.configGroup(pageVO,groups);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 停止运行状态
	 *@author smy
	 *@date 2016-8-24
	 *@ModifiedUser
	 *@ModifiedDate
	 */
	public void updateRunstate(){
		pageVO.setModifyId(userInfo.getUserName());
		service. updateRunstate(pageVO);
		write(Constants.MSG_SUCCESS);
	}
	
	public EmailManageVO getPageVO() {
		return pageVO;
	}
	public void setPageVO(EmailManageVO pageVO) {
		this.pageVO = pageVO;
	}
	public EmailManageService getService() {
		return service;
	}
	public void setService(EmailManageService service) {
		this.service = service;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getGroups() {
		return groups;
	}
	
}

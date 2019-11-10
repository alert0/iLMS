package com.hanthink.gps.email.vo;

import com.hanthink.gps.pub.vo.BaseVO;


public class MsgGroupVO extends BaseVO {

	private static final long serialVersionUID = 344167649355829243L;
	
	private String sortId;//排序码
	/**分组 */ 
	private String pkId;
    private String groupCode;//分组代码
    private String groupName;//分组名称
    private String record;//备注
    private String delFlag;//删除标识
    
    /**分组 人员*/
    private String userId;//用户
    private String timerCode;//定时器代码
    private String userCname;//姓名
    private String email;//邮箱
    private String mobile;//电话
    private String department;
    private String delList;
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTimerCode() {
		return timerCode;
	}
	public void setTimerCode(String timerCode) {
		this.timerCode = timerCode;
	}
	public String getUserCname() {
		return userCname;
	}
	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDelList() {
		return delList;
	}
	public void setDelList(String delList) {
		this.delList = delList;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getPkId() {
		return pkId;
	}
	public void setDepartment(String apartment) {
		this.department = apartment;
	}
	public String getDepartment() {
		return department;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	
	
    
}	
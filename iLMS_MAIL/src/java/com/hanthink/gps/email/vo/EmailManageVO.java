package com.hanthink.gps.email.vo;


import java.util.Date;

import com.hanthink.gps.pub.vo.BaseVO;

/**
 * 定时器管理VO
 * @author smy
 * @date 2016-7-27
 */
public class EmailManageVO extends BaseVO {
	private static final long serialVersionUID = 1094069515637529436L;
	private Long pkId;
	private String timerCode;//定时器代码
	private String timerName;//定时器名称
	private String timerParam;//定时器参数
	private String runCode;//定时器调用class代码
	private Date startTime;//开始时间
	private String lastRunTime;//上次运行时间
	private String nextRunTime;//下次运行时间
	private String runState;//运行状态
	private String update;//更新标识
	private String delFlag;//删除标识
	private String timerGroup;//定时器分组
	private String timerDesc;//定时器描述
	private String ModifyDateStr;//修改时间
	private String groupCode;//分组编码
	
	/** 应用类型(用于区分多个应用指向同一个数据库) */
	private String appType;
	
	public Long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	public String getTimerCode() {
		return timerCode;
	}
	public void setTimerCode(String timerCode) {
		this.timerCode = timerCode;
	}
	public String getTimerName() {
		return timerName;
	}
	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}
	public String getTimerParam() {
		return timerParam;
	}
	public void setTimerParam(String timerParam) {
		this.timerParam = timerParam;
	}
	public String getRunCode() {
		return runCode;
	}
	public void setRunCode(String runCode) {
		this.runCode = runCode;
	}
	
	public String getLastRunTime() {
		return lastRunTime;
	}
	public void setLastRunTime(String lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
	public String getNextRunTime() {
		return nextRunTime;
	}
	public void setNextRunTime(String nextRunTime) {
		this.nextRunTime = nextRunTime;
	}
	public String getRunState() {
		return runState;
	}
	public void setRunState(String runState) {
		this.runState = runState;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getTimerGroup() {
		return timerGroup;
	}
	public void setTimerGroup(String timerGroup) {
		this.timerGroup = timerGroup;
	}
	public String getTimerDesc() {
		return timerDesc;
	}
	public void setTimerDesc(String timerDesc) {
		this.timerDesc = timerDesc;
	}
	public void setModifyDateStr(String modifyDateStr) {
		ModifyDateStr = modifyDateStr;
	}
	public String getModifyDateStr() {
		return ModifyDateStr;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}

	
  
}

package com.hotent.system.worktime.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:包括请假、休假（各类）的管理，缺勤种类通过sys_gl_type来管理 entity对象 开发公司:广州宏天软件有限公司 开发人员:zyp
 * 创建时间:2014-02-18 11:50:27
 */
public class CalendarAbsence extends AbstractModel<String> implements Cloneable {
	private static final long serialVersionUID = 1L;
	protected String id; /* 缺勤管理ID */
	protected String absRason; /* 缺勤事由 */
	protected String userId; /* 用户ID */
	protected String catKey; /* 分类Key */
	protected Date startTime; /* 缺勤开始时间 */
	protected Date endTime; /* 结束时间 */
	protected String userName; /* 用户名 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 缺勤管理ID
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setAbsRason(String absRason) {
		this.absRason = absRason;
	}

	/**
	 * 返回 缺勤事由
	 * 
	 * @return
	 */
	public String getAbsRason() {
		return this.absRason;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 返回 用户ID
	 * 
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	/**
	 * 返回 分类Key
	 * 
	 * @return
	 */
	public String getCatKey() {
		return this.catKey;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 返回 缺勤开始时间
	 * 
	 * @return
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 返回 结束时间
	 * 
	 * @return
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("absRason", this.absRason)
				.append("userId", this.userId).append("catKey", this.catKey)
				.append("startTime", this.startTime)
				.append("endTime", this.endTime).toString();
	}
}
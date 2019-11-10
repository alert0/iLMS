package com.hotent.biz.meeting.model;




import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：xq_meetingroom_appointment 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-10 16:53:39
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class Meeting extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* id
	*/
	protected String id; 
	
	/**
	* 会议室id
	*/
	protected String meetingroomId; 
	
	/**
	* 会议id
	*/
	protected String meetingId; 
	
	/**
	* 会议名称
	*/
	protected String meetingName; 
	
	/**
	* 主持人姓名
	*/
	protected String hostessName; 
	
	/**
	* 预约时间
	*/
	protected java.util.Date appointmentTime; 
	
	/**
	* 预约状态
	*/
	protected Short appointmentStatus; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 id
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setMeetingroomId(String meetingroomId) {
		this.meetingroomId = meetingroomId;
	}
	
	/**
	 * 返回 会议室id
	 * @return
	 */
	public String getMeetingroomId() {
		return this.meetingroomId;
	}
	
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	/**
	 * 返回 会议id
	 * @return
	 */
	public String getMeetingId() {
		return this.meetingId;
	}
	
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	
	/**
	 * 返回 会议名称
	 * @return
	 */
	public String getMeetingName() {
		return this.meetingName;
	}
	
	public void setHostessName(String hostessName) {
		this.hostessName = hostessName;
	}
	
	/**
	 * 返回 主持人姓名
	 * @return
	 */
	public String getHostessName() {
		return this.hostessName;
	}
	
	public void setAppointmentTime(java.util.Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	
	/**
	 * 返回 预约时间
	 * @return
	 */
	public java.util.Date getAppointmentTime() {
		return this.appointmentTime;
	}
	
	public void setAppointmentStatus(Short appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	
	/**
	 * 返回 预约状态
	 * @return
	 */
	public Short getAppointmentStatus() {
		return this.appointmentStatus;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("meetingroomId", this.meetingroomId) 
		.append("meetingId", this.meetingId) 
		.append("meetingName", this.meetingName) 
		.append("hostessName", this.hostessName) 
		.append("appointmentTime", this.appointmentTime) 
		.append("appointmentStatus", this.appointmentStatus) 
		.toString();
	}
}
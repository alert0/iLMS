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
 * 日期:2017-08-16 15:18:41
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class MeetingAppoint extends AbstractModel<String>{
	
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
	* 预约开始时间
	*/
	protected java.util.Date appointmentBegTime; 
	
	/**
	* 预约结束时间
	*/
	protected java.util.Date appointmentEndTime; 
	
	/**
	* 预约状态
	*/
	protected Integer appointmentStatus; 
	
	
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
	
	public void setAppointmentBegTime(java.util.Date appointmentBegTime) {
		this.appointmentBegTime = appointmentBegTime;
	}
	
	/**
	 * 返回 预约开始时间
	 * @return
	 */
	public java.util.Date getAppointmentBegTime() {
		return this.appointmentBegTime;
	}
	
	public void setAppointmentEndTime(java.util.Date appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
	}
	
	/**
	 * 返回 预约结束时间
	 * @return
	 */
	public java.util.Date getAppointmentEndTime() {
		return this.appointmentEndTime;
	}
	
	public void setAppointmentStatus(Integer appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	
	/**
	 * 返回 预约状态
	 * @return
	 */
	public Integer getAppointmentStatus() {
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
		.append("appointmentBegTime", this.appointmentBegTime) 
		.append("appointmentEndTime", this.appointmentEndTime) 
		.append("appointmentStatus", this.appointmentStatus) 
		.toString();
	}
}
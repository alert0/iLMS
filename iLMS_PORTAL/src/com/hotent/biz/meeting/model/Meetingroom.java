package com.hotent.biz.meeting.model;



import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：xq_meetingroom 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 16:15:07
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class Meetingroom extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* 会议室id
	*/
	protected String id; 
	
	/**
	* 会议室名称
	*/
	protected String name; 
	
	/**
	* 是否需要审批
	*/
	protected Short needPending; 
	
	/**
	* 审批人id
	*/
	protected String pendingUserId; 
	
	/**
	* 审批人姓名
	*/
	protected String pendingUserName; 
	
	/**
	* 支持的服务
	*/
	protected String supportService; 
	
	/**
	* 会议室地址
	*/
	protected String location; 
	
	/**
	* 会议室容量
	*/
	protected Integer capacity; 
	
	/**
	* 会议室备注
	*/
	protected String memo; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 会议室id
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 会议室名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setNeedPending(Short needPending) {
		this.needPending = needPending;
	}
	
	/**
	 * 返回 是否需要审批
	 * @return
	 */
	public Short getNeedPending() {
		return this.needPending;
	}
	
	public void setPendingUserId(String pendingUserId) {
		this.pendingUserId = pendingUserId;
	}
	
	/**
	 * 返回 审批人id
	 * @return
	 */
	public String getPendingUserId() {
		return this.pendingUserId;
	}
	
	public void setPendingUserName(String pendingUserName) {
		this.pendingUserName = pendingUserName;
	}
	
	/**
	 * 返回 审批人姓名
	 * @return
	 */
	public String getPendingUserName() {
		return this.pendingUserName;
	}
	
	public void setSupportService(String supportService) {
		this.supportService = supportService;
	}
	
	/**
	 * 返回 支持的服务
	 * @return
	 */
	public String getSupportService() {
		return this.supportService;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * 返回 会议室地址
	 * @return
	 */
	public String getLocation() {
		return this.location;
	}
	
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * 返回 会议室容量
	 * @return
	 */
	public Integer getCapacity() {
		return this.capacity;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * 返回 会议室备注
	 * @return
	 */
	public String getMemo() {
		return this.memo;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("needPending", this.needPending) 
		.append("pendingUserId", this.pendingUserId) 
		.append("pendingUserName", this.pendingUserName) 
		.append("supportService", this.supportService) 
		.append("location", this.location) 
		.append("capacity", this.capacity) 
		.append("memo", this.memo) 
		.toString();
	}
}
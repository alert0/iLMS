package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：流转任务接收人 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-06 10:51:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmTransReceiver extends AbstractModel<String>{
	
	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 流转任务记录ID
	*/
	protected String transRecordid; 
	
	/**
	* 流转接受人员
	*/
	protected String receiver; 
	
	/**
	* 流转接收人id
	*/
	protected String receiverId; 
	
	/**
	* 状态:0尚未处理1已处理
	*/
	protected Short status; 
	
	/**
	* 审核意见
	*/
	protected String opinion; 
	
	/**
	* 接收时间
	*/
	protected java.util.Date receiverTime; 
	
	/**
	* 审核时间
	*/
	protected java.util.Date checkTime; 
	
	/**
	* 审核状态
	*/
	protected Short checkType; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setTransRecordid(String transRecordid) {
		this.transRecordid = transRecordid;
	}
	
	/**
	 * 返回 流转任务记录ID
	 * @return
	 */
	public String getTransRecordid() {
		return this.transRecordid;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * 返回 流转接受人员
	 * @return
	 */
	public String getReceiver() {
		return this.receiver;
	}
	
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	
	/**
	 * 返回 流转接收人id
	 * @return
	 */
	public String getReceiverId() {
		return this.receiverId;
	}
	
	public void setStatus(Short status) {
		this.status = status;
	}
	
	/**
	 * 返回 状态:0尚未处理1已处理
	 * @return
	 */
	public Short getStatus() {
		return this.status;
	}
	
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	/**
	 * 返回 审核意见
	 * @return
	 */
	public String getOpinion() {
		return this.opinion;
	}
	
	public void setReceiverTime(java.util.Date receiverTime) {
		this.receiverTime = receiverTime;
	}
	
	/**
	 * 返回 接收时间
	 * @return
	 */
	public java.util.Date getReceiverTime() {
		return this.receiverTime;
	}
	
	public void setCheckTime(java.util.Date checkTime) {
		this.checkTime = checkTime;
	}
	
	/**
	 * 返回 审核时间
	 * @return
	 */
	public java.util.Date getCheckTime() {
		return this.checkTime;
	}
	
	public void setCheckType(Short checkType) {
		this.checkType = checkType;
	}
	
	/**
	 * 返回 审核状态
	 * @return
	 */
	public Short getCheckType() {
		return this.checkType;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("transRecordid", this.transRecordid) 
		.append("receiver", this.receiver) 
		.append("receiverId", this.receiverId) 
		.append("status", this.status) 
		.append("opinion", this.opinion) 
		.append("receiverTime", this.receiverTime) 
		.append("checkTime", this.checkTime) 
		.append("checkType", this.checkType) 
		.toString();
	}
}
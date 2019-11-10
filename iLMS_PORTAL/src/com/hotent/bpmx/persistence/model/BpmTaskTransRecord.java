package com.hotent.bpmx.persistence.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：bpm_task_trans_record 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-04 16:12:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmTaskTransRecord extends AbstractModel<String>{
	
	/**
	* ID_
	*/
	protected String id; 
	
	/**
	* 关联的任务ID
	*/
	protected String taskId; 
	
	/**
	* 任务名称
	*/
	protected String taskName; 
	
	/**
	* 流转任务标题
	*/
	protected String taskSubject; 
	
	/**
	* 状态：0流转中 1流转结束 2已撤销
	*/
	protected Short status; 
	
	/**
	* 流转人员
	*/
	protected String transUsers; 
	
	/**
	* 流转人员ID
	*/
	protected String transUserIds; 
	
	/**
	* 流转意见（原因）
	*/
	protected String transOpinion; 
	
	/**
	* 决策类型 同意：agree   反对：oppose
	*/
	protected String decideType; 
	
	/**
	* 完成后的操作动作
	*/
	protected String action; 
	
	/**
	* 投票类型
	*/
	protected String voteType; 
	
	/**
	* 票数
	*/
	protected Short voteAmount; 
	
	/**
	* 会签类型
	*/
	protected String signType; 
	
	/**
	* 总票数
	*/
	protected Short totalAmount; 
	
	/**
	* 通过票数
	*/
	protected Short agreeAmount; 
	
	/**
	* 反对票数
	*/
	protected Short opposeAmount; 
	
	/**
	* 流转任务所属人
	*/
	protected String transOwner; 
	
	/**
	* 创建人
	*/
	protected String creator; 
	
	/**
	* CREATOR_ID_
	*/
	protected String creatorId; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date createTime; 
	
	/**
	* 流转时间
	*/
	protected java.util.Date transTime; 
	
	/**
	* 流程名称
	*/
	protected String defName; 
	
	/**
	* 流程实例ID
	*/
	protected String procInstId; 
	
	/**
	 * 接收人员列表
	 */
	protected List<BpmTransReceiver> receiverList = new ArrayList<BpmTransReceiver>();
	
	
	public List<BpmTransReceiver> getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(List<BpmTransReceiver> receiverList) {
		this.receiverList = receiverList;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	/**
	 * 返回 关联的任务ID
	 * @return
	 */
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	/**
	 * 返回 任务名称
	 * @return
	 */
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}
	
	/**
	 * 返回 流转任务标题
	 * @return
	 */
	public String getTaskSubject() {
		return this.taskSubject;
	}
	
	public void setStatus(Short status) {
		this.status = status;
	}
	
	/**
	 * 返回 状态：0流转中 1流转结束 2已撤销
	 * @return
	 */
	public Short getStatus() {
		return this.status;
	}
	
	public void setTransUsers(String transUsers) {
		this.transUsers = transUsers;
	}
	
	/**
	 * 返回 流转人员
	 * @return
	 */
	public String getTransUsers() {
		return this.transUsers;
	}
	
	public void setTransUserIds(String transUserIds) {
		this.transUserIds = transUserIds;
	}
	
	/**
	 * 返回 流转人员ID
	 * @return
	 */
	public String getTransUserIds() {
		return this.transUserIds;
	}
	
	public void setTransOpinion(String transOpinion) {
		this.transOpinion = transOpinion;
	}
	
	/**
	 * 返回 流转意见（原因）
	 * @return
	 */
	public String getTransOpinion() {
		return this.transOpinion;
	}
	
	public void setDecideType(String decideType) {
		this.decideType = decideType;
	}
	
	/**
	 * 返回 决策类型 同意：agree   反对：oppose
	 * @return
	 */
	public String getDecideType() {
		return this.decideType;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	/**
	 * 返回 完成后的操作动作
	 * @return
	 */
	public String getAction() {
		return this.action;
	}
	
	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}
	
	/**
	 * 返回 投票类型
	 * @return
	 */
	public String getVoteType() {
		return this.voteType;
	}
	
	public void setVoteAmount(Short voteAmount) {
		this.voteAmount = voteAmount;
	}
	
	/**
	 * 返回 票数
	 * @return
	 */
	public Short getVoteAmount() {
		return this.voteAmount;
	}
	
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	/**
	 * 返回 会签类型
	 * @return
	 */
	public String getSignType() {
		return this.signType;
	}
	
	public void setTotalAmount(Short totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/**
	 * 返回 总票数
	 * @return
	 */
	public Short getTotalAmount() {
		return this.totalAmount;
	}
	
	public void setAgreeAmount(Short agreeAmount) {
		this.agreeAmount = agreeAmount;
	}
	
	/**
	 * 返回 通过票数
	 * @return
	 */
	public Short getAgreeAmount() {
		return this.agreeAmount;
	}
	
	public void setOpposeAmount(Short opposeAmount) {
		this.opposeAmount = opposeAmount;
	}
	
	/**
	 * 返回 反对票数
	 * @return
	 */
	public Short getOpposeAmount() {
		return this.opposeAmount;
	}
	
	public void setTransOwner(String transOwner) {
		this.transOwner = transOwner;
	}
	
	/**
	 * 返回 流转任务所属人
	 * @return
	 */
	public String getTransOwner() {
		return this.transOwner;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreator() {
		return this.creator;
	}
	
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	/**
	 * 返回 CREATOR_ID_
	 * @return
	 */
	public String getCreatorId() {
		return this.creatorId;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public java.util.Date getTransTime() {
		return transTime;
	}

	public void setTransTime(java.util.Date transTime) {
		this.transTime = transTime;
	}

	public String getDefName() {
		return defName;
	}

	public void setDefName(String defName) {
		this.defName = defName;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("taskId", this.taskId) 
		.append("taskName", this.taskName) 
		.append("taskSubject", this.taskSubject) 
		.append("status", this.status) 
		.append("transUsers", this.transUsers) 
		.append("transUserIds", this.transUserIds) 
		.append("transOpinion", this.transOpinion) 
		.append("decideType", this.decideType) 
		.append("action", this.action) 
		.append("voteType", this.voteType) 
		.append("voteAmount", this.voteAmount) 
		.append("signType", this.signType) 
		.append("totalAmount", this.totalAmount) 
		.append("agreeAmount", this.agreeAmount) 
		.append("opposeAmount", this.opposeAmount) 
		.append("transOwner", this.transOwner) 
		.append("creator", this.creator) 
		.append("creatorId", this.creatorId) 
		.append("createTime", this.createTime)
		.append("transTime",this.transTime)
		.append("defName",this.defName)
		.append("procInstId",this.procInstId)
		.toString();
	}
}
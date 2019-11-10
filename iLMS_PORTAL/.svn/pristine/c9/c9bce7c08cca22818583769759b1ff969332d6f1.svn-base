package com.hotent.bpmx.persistence.model;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.bpmx.api.model.process.task.TaskTrans;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.org.api.model.IUser;

/**
 * 对象功能:流程流转 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyg
 * 创建时间:2014-07-31 15:18:29
 */
public class BpmTaskTrans extends AbstractModel<String> implements TaskTrans{
	
	public static final String SIGN_TYPE_PARALLEL="parallel";
	public static final String SIGN_TYPE_SEQ="seq";
	
	public static final String SIGN_ACTION_SUBMIT="submit";
	public static final String SIGN_ACTION_BACK="back";
	
	protected String  id; /*主键*/
	protected String  instanceId; /*流程实例*/
	protected String  taskId; /*任务ID*/
	protected String  action=SIGN_ACTION_BACK; /*完成后的操作*/
	protected String  creatorId; /*创建人ID*/
	protected String  creator; /*创建人*/
	protected java.util.Date  createTime; /*创建时间*/
	
	protected String  decideType; /*决策类型*/
	protected String  voteType; /*投票类型*/
	protected Short  voteAmount=0; /*票数*/
	protected String  signType=SIGN_TYPE_PARALLEL; /*会签类型*/
	protected Short  totalAmount=0; /*总票数*/
	protected Short  agreeAmount=0; /*通过票数*/
	protected Short  opposeAmount=0; /*反对票数*/
	protected Short  seq=0; /*投票次序*/
	protected String  userJson=""; /*用户数据*/
	/**
	 * 允许表单编辑。
	 */
	protected Short allowFormEdit=0;
	
	public void setId(String id) 
	{
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getId()
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setInstanceId(String instanceId) 
	{
		this.instanceId = instanceId;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getInstanceId()
	 */
	public String getInstanceId() 
	{
		return this.instanceId;
	}
	public void setTaskId(String taskId) 
	{
		this.taskId = taskId;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getTaskId()
	 */
	public String getTaskId() 
	{
		return this.taskId;
	}
	public void setAction(String action) 
	{
		this.action = action;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getAction()
	 */
	public String getAction() 
	{
		return this.action;
	}
	public void setCreatorId(String creatorId) 
	{
		this.creatorId = creatorId;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getCreatorId()
	 */
	public String getCreatorId() 
	{
		return this.creatorId;
	}
	public void setCreator(String creator) 
	{
		this.creator = creator;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getCreator()
	 */
	public String getCreator() 
	{
		return this.creator;
	}
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getCreateTime()
	 */
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setDecideType(String decideType) 
	{
		this.decideType = decideType;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getDecideType()
	 */
	public String getDecideType() 
	{
		return this.decideType;
	}
	public void setVoteType(String voteType) 
	{
		this.voteType = voteType;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getVoteType()
	 */
	public String getVoteType() 
	{
		return this.voteType;
	}
	public void setVoteAmount(Short voteAmount) 
	{
		this.voteAmount = voteAmount;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getVoteAmount()
	 */
	public Short getVoteAmount() 
	{
		return this.voteAmount;
	}
	public void setSignType(String signType) 
	{
		this.signType = signType;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getSignType()
	 */
	public String getSignType() 
	{
		return this.signType;
	}
	public void setTotalAmount(Short totalAmount) 
	{
		this.totalAmount = totalAmount;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getTotalAmount()
	 */
	public Short getTotalAmount() 
	{
		return this.totalAmount;
	}
	public void setAgreeAmount(Short aggreeAmount) 
	{
		this.agreeAmount = aggreeAmount;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getAgreeAmount()
	 */
	public Short getAgreeAmount() 
	{
		return this.agreeAmount;
	}
	public void setOpposeAmount(Short opposeAmount) 
	{
		this.opposeAmount = opposeAmount;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getOpposeAmount()
	 */
	public Short getOpposeAmount() 
	{
		return this.opposeAmount;
	}
	public void setSeq(Short seq) 
	{
		this.seq = seq;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getSeq()
	 */
	public Short getSeq() 
	{
		return this.seq;
	}
	public void setUserJson(String userJson) 
	{
		this.userJson = userJson;
	}
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getUserJson()
	 */
	public String getUserJson() 
	{
		return this.userJson;
	}
	
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getUserByIndex(int)
	 */
	public IUser getUserByIndex(int index){
		JSONArray jsonAry=JSONArray.fromObject(this.userJson);
		JSONObject jsonObj= (JSONObject) jsonAry.get(index);
		String userId=jsonObj.getString("userId");
		String fullname=jsonObj.getString("fullname");
		IUser user=BpmUtil. getUser(userId, fullname);
		return user;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.hotent.bpmx.persistence.model.TaskTrans#getAllowFormEdit()
	 */
	public Short getAllowFormEdit() {
		return allowFormEdit;
	}
	public void setAllowFormEdit(Short allowFormEdit) {
		this.allowFormEdit = allowFormEdit;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("instanceId", this.instanceId) 
		.append("taskId", this.taskId) 
		.append("action", this.action) 
		.append("creatorId", this.creatorId) 
		.append("creator", this.creator) 
		.append("createTime", this.createTime) 
		.append("decideType", this.decideType) 
		.append("voteType", this.voteType) 
		.append("voteAmount", this.voteAmount) 
		.append("signType", this.signType) 
		.append("totalAmount", this.totalAmount) 
		.append("agreeAmount", this.agreeAmount) 
		.append("opposeAmount", this.opposeAmount) 
		.append("seq", this.seq) 
		.append("userJson", this.userJson) 
		.toString();
	}
}
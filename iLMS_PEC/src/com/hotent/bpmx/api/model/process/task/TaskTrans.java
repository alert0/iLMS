package com.hotent.bpmx.api.model.process.task;

/**
 * 流转任务
 * @author Administrator
 *
 */
public interface TaskTrans {

	/**
	 * 返回 主键
	 * @return
	 */
	public abstract String getId();

	/**
	 * 返回 流程实例
	 * @return
	 */
	public abstract String getInstanceId();

	/**
	 * 返回 任务ID
	
	 * @return
	 */
	public abstract String getTaskId();

	/**
	 * 返回 完成后的操作
	 * @return
	 */
	public abstract String getAction();

	/**
	 * 返回 创建人ID
	 * @return
	 */
	public abstract String getCreatorId();

	/**
	 * 返回 创建人
	 * @return
	 */
	public abstract String getCreator();

	/**
	 * 返回 创建时间
	 * @return
	 */
	public abstract java.util.Date getCreateTime();

	/**
	 * 返回 决策类型
	
	 * @return
	 */
	public abstract String getDecideType();

	/**
	 * 返回 投票类型
	
	 * @return
	 */
	public abstract String getVoteType();

	/**
	 * 返回 票数
	 * @return
	 */
	public abstract Short getVoteAmount();

	/**
	 * 返回 会签类型
	
	 * @return
	 */
	public abstract String getSignType();

	/**
	 * 返回 总票数
	 * @return
	 */
	public abstract Short getTotalAmount();

	/**
	 * 返回 通过票数
	 * @return
	 */
	public abstract Short getAgreeAmount();

	/**
	 * 返回 反对票数
	 * @return
	 */
	public abstract Short getOpposeAmount();

	/**
	 * 返回 投票次序
	 * @return
	 */
	public abstract Short getSeq();

	/**
	 * 返回 用户数据
	 * @return
	 */
	public abstract String getUserJson();
	
	/**
	 * 返回 是否允许编辑表单
	 * @return
	 */
	public Short getAllowFormEdit();

}

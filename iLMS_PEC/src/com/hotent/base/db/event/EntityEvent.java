package com.hotent.base.db.event;

import org.springframework.context.ApplicationEvent;

import com.hotent.base.core.model.AbstractModel;

public class EntityEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private int actionType=0;

	@SuppressWarnings("rawtypes")
	public EntityEvent(AbstractModel source) {
		super(source);
	}
	
	public void setActionType(int type){
		actionType=type;
	}
	
	/**
	 * 获取当前对实体类执行的操作类型
	 * <pre>
	 * 	0:新增
	 * 	1:更新
	 * </pre>
	 * @return
	 */
	public int getActionType(){
		return actionType;
	}
}

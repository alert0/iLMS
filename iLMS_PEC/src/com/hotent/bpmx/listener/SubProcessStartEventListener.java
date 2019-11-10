package com.hotent.bpmx.listener;

import org.activiti.engine.impl.entity.SubProcessStartOrEndEventModel;
import org.activiti.engine.impl.event.SubProcessStartEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.context.ContextThreadUtil;

/**
 * 内嵌子流程启动时事件
 */
@Service
public class SubProcessStartEventListener implements ApplicationListener<SubProcessStartEvent>, Ordered
{

	

	@Override
	public int getOrder()
	{
		return 1;
	}

	@Override
	public void onApplicationEvent(SubProcessStartEvent ev)
	{
		SubProcessStartOrEndEventModel eventModel = (SubProcessStartOrEndEventModel) ev.getSource();
		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		cmd.addTransitVars("CurrentEventType", "SubProcessStartOrEndEvent");
		cmd.addTransitVars("SubProcessStartOrEndEventModel", eventModel);
 
	}
}

package com.hotent.bpmx.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.event.AutoTestEvent;
import com.hotent.sys.api.jms.JmsProducer;
import com.hotent.sys.api.jms.model.AutoTestModel;

/**
 * 流程仿真测试
 * 
 * <pre>
 * 构建组：x5-bpmx-core
 * 作者：liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2018-01-03-上午10:02:23
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class AutoTestEventListener implements ApplicationListener<AutoTestEvent>, Ordered
{
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void onApplicationEvent(AutoTestEvent event) {
		AutoTestModel model = (AutoTestModel) event.getSource();
		doNext(model);
		
	}
	
	
	// 任务审批
	private void doNext(AutoTestModel model){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("account",model.getRandomAccount());
		jsonObject.put("actionName", "agree");
		jsonObject.put("taskId", model.getTaskId());
		JmsProducer jmsProducer=AppUtil.getBean(JmsProducer.class);
		if(jmsProducer==null) return;
		jmsProducer.sendToQueue(model);
	
	}
	
}

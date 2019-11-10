/**
 * 描述：TODO
 * 包名：com.hotent.sys.msg
 * 文件名：JmsConsumer.java
 * 作者：win-mailto:chensx@jee-soft.cn
 * 日期2014-4-24-上午11:39:46
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.sys.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.AppUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;
import com.hotent.sys.jms.model.JmsReceiverEvent;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-sys-core
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-24-上午11:39:46
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class JmsConsumer  {

	private Map<String,JmsHandler> jmsHandlerMap = new HashMap<String, JmsHandler>();
	
	private List<JmsHandler<JmsVo>> jmsHandList=new ArrayList<JmsHandler<JmsVo>>();
	
	public void setJmsHandList(List<JmsHandler<JmsVo>> jmsHandList){
		for(JmsHandler<JmsVo> handler:jmsHandList){
			jmsHandlerMap.put(handler.getType(), handler);
		}
	}

	public void execute(Object model) {
		if(model instanceof JmsVo){
			JmsVo vo=(JmsVo)model;
			JmsHandler jmsHandler = jmsHandlerMap.get(vo.getType());
			jmsHandler.send(vo);
		}
		else{
			JmsReceiverEvent ev=new JmsReceiverEvent(model);
			AppUtil.publishEvent(ev);
		}
	}

}

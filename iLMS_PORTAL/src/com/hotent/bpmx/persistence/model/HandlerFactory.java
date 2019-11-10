package com.hotent.bpmx.persistence.model;

import com.hotent.bpmx.api.model.process.nodedef.ext.BaseBpmNodeDef;
import com.hotent.bpmx.persistence.model.nodehandler.BaseNodeHandler;
import com.hotent.bpmx.persistence.model.nodehandler.SignTaskHandler;
import com.hotent.bpmx.persistence.model.nodehandler.UserTaskHandler;

/**
 * 处理器工厂。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-29-下午7:27:39
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class HandlerFactory {
	
	public static NodeHandler createHandler(BaseBpmNodeDef nodeDef){
		NodeHandler nodeHandler=null;
		switch (nodeDef.getType()) {
			case USERTASK:
				nodeHandler=new UserTaskHandler();
				break;
			case SIGNTASK:
				nodeHandler=new SignTaskHandler();
				break;
			case START:
			case END:
			case SERVICETASK:
				nodeHandler=new BaseNodeHandler();
			default:
				break;
		}
		
		
		return nodeHandler;
	}

}

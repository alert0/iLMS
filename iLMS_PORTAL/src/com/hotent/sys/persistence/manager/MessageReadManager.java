package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.model.MessageRead;

/**
 * 
 * <pre> 
 * 描述：sys_msg_read 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MessageReadManager extends Manager<String, MessageRead>{

	void addMessageRead(String msgId, IUser currentUser);

	List<MessageRead> getByMessageId(String messageId);
	
}

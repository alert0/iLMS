package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.MessageReply;

/**
 * 
 * <pre> 
 * 描述：sys_msg_reply 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 15:32:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MessageReplyManager extends Manager<String, MessageReply>{

	List<MessageReply> getByMessageId(String messageId);
	
}

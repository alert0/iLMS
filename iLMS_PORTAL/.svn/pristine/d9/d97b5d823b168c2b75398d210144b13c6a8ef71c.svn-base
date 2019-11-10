package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.MessageReplyDao;
import com.hotent.sys.persistence.manager.MessageReplyManager;
import com.hotent.sys.persistence.model.MessageReply;

/**
 * 
 * <pre> 
 * 描述：sys_msg_reply 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 15:32:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("messageReplyManager")
public class MessageReplyManagerImpl extends AbstractManagerImpl<String, MessageReply> implements MessageReplyManager{
	@Resource
	MessageReplyDao messageReplyDao;
	@Override
	protected Dao<String, MessageReply> getDao() {
		return messageReplyDao;
	}
	@Override
	public List<MessageReply> getByMessageId(String messageId) {
		return messageReplyDao.getByMessageId(messageId);
	}
}

package com.hotent.sys.persistence.manager.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.dao.MessageReadDao;
import com.hotent.sys.persistence.manager.MessageReadManager;
import com.hotent.sys.persistence.model.MessageRead;

/**
 * 
 * <pre> 
 * 描述：sys_msg_read 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("messageReadManager")
public class MessageReadManagerImpl extends AbstractManagerImpl<String, MessageRead> implements MessageReadManager{
	@Resource
	MessageReadDao messageReadDao;
	@Override
	protected Dao<String, MessageRead> getDao() {
		return messageReadDao;
	}
	@Override
	public void addMessageRead(String msgId, IUser sysUser) {
		MessageRead msgRead = messageReadDao.getReadByUser(msgId, sysUser.getUserId());
		if(msgRead==null){
			Date now = new Date();
			MessageRead messageRead = new MessageRead();
			messageRead.setMsgId(msgId);
			messageRead.setReceiverId(sysUser.getUserId());
			messageRead.setReceiver(sysUser.getFullname());
			messageRead.setReceiverTime(now);
			messageReadDao.create(messageRead);
		}
		
	}
	@Override
	public List<MessageRead> getByMessageId(String messageId) {
		return messageReadDao.getByMessageId(messageId);
	}
}

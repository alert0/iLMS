package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.dao.MessageReadDao;
import com.hotent.sys.persistence.dao.MessageReceiverDao;
import com.hotent.sys.persistence.manager.MessageReadManager;
import com.hotent.sys.persistence.manager.MessageReceiverManager;
import com.hotent.sys.persistence.model.MessageReceiver;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：sys_msg_receiver 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("messageReceiverManager")
public class MessageReceiverManagerImpl extends AbstractManagerImpl<String, MessageReceiver> implements MessageReceiverManager{
	@Resource
	MessageReceiverDao messageReceiverDao;
	@Resource
	MessageReadManager messageReadManager;
	
	@Override
	protected Dao<String, MessageReceiver> getDao() {
		return messageReceiverDao;
	}
	@Override
	public void updateReadStatus(String[] lAryId) {
		if (lAryId.length==0) return;
		IUser currentUser = ContextUtil.getCurrentUser();
		for (String id :lAryId ){
			MessageReceiver messageReceiver = messageReceiverDao.get(id);
			if (BeanUtils.isEmpty(messageReceiver)) continue;
			messageReadManager.addMessageRead(messageReceiver.getMsgId(),currentUser);
			
		}
	}
}

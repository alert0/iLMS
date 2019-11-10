package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.dao.MessageReadDao;
import com.hotent.sys.persistence.model.MessageRead;

/**
 * 
 * <pre> 
 * 描述：sys_msg_read DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MessageReadDaoImpl extends MyBatisDaoImpl<String, MessageRead> implements MessageReadDao{

    @Override
    public String getNamespace() {
        return MessageRead.class.getName();
    }

	@Override
	public MessageRead getReadByUser(String msgId, String userId) {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("msgId", msgId);
		params.put("userId", userId);
		return this.getUnique("getReadByUser", params);
	}

	@Override
	public List<MessageRead> getByMessageId(String messageId) {
		
		return this.getByKey("getByMessageId", messageId);
	}

	

	
}


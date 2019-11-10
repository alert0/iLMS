package com.hotent.sys.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.MessageReplyDao;
import com.hotent.sys.persistence.model.MessageReply;

/**
 * 
 * <pre> 
 * 描述：sys_msg_reply DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 15:32:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MessageReplyDaoImpl extends MyBatisDaoImpl<String, MessageReply> implements MessageReplyDao{

    @Override
    public String getNamespace() {
        return MessageReply.class.getName();
    }

	@Override
	public List<MessageReply> getByMessageId(String messageId) {
		return this.getByKey("getByMessageId", messageId);
	}
	
}


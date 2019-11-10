package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.model.MessageRead;

/**
 * 
 * <pre> 
 * 描述：sys_msg_read DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MessageReadDao extends Dao<String, MessageRead> {

	MessageRead getReadByUser(String msgId, String userId);

	List<MessageRead> getByMessageId(String messageId);

	
}

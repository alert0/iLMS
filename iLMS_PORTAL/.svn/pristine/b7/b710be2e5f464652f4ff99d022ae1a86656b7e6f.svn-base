package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysMessage;

/**
 * 
 * <pre> 
 * 描述：sys_msg 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 09:03:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysMessageManager extends Manager<String, SysMessage> {

	List<SysMessage> getMsgByUserId(QueryFilter queryFilter);

	//处理消息发送
	void addMessageSend(SysMessage sysMessage);

	SysMessage getNotReadMsg(String userId);

	int getNotReadMsgNum(String userId);
	/**
	 * 获取未读信息的个数
	 * @param currentUserId
	 * @return 
	 */
	int getMsgSize(String receiverId);
	
}

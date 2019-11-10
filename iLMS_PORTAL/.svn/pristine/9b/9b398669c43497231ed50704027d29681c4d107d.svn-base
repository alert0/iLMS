package com.hotent.sys.api.msg;

import java.util.List;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.msg.model.SysExecutor;

/**
 * 内部消息接口。
 * <pre> 
 * 构建组：x5-sys-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-10-30-下午4:46:23
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface InnerMsgService {
	
	/**
	 * 发送消息。
	 * @param subject		主题
	 * @param content		内容
	 * @param messageType	消息类型
	 * @param sender		发送人
	 * @param receivers 	接收人
	 * void
	 */
	ResultMessage sendMsg(String subject,String content,String messageType ,IUser sender, List<SysExecutor> receivers) ;
}

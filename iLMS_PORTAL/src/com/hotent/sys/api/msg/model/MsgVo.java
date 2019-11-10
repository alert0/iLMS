package com.hotent.sys.api.msg.model;

import java.util.List;
import java.util.Map;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.model.JmsVo;
 

public interface MsgVo extends JmsVo{	
	/**
	 * 获得模板别名
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getTemplateAlias();
	
	/**
	 * 消息标题
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getSubject();
	
	/**
	 * 消息内容
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getContent();
	
	/**
	 * 获得发送者业务键
	 * @return 
	 * User
	 * @exception 
	 * @since  1.0.0
	 */
	public IUser getSender();
	
	/**
	 * 获得接收者业务键集合
	 * @return 
	 * List<String>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<IUser> getReceivers();
	
	/**
	 * 获得扩展数据
	 * @return 
	 * Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	public Map<String, Object> getExtendVars();	
}

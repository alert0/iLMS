package com.hotent.sys.jms.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.msg.InnerMsgService;
import com.hotent.sys.api.msg.constants.MsgType;
import com.hotent.sys.api.msg.model.DefaultMsgVo;
import com.hotent.sys.api.msg.model.SysExecutor;

/**
 * 内部消息处理器。
 * <pre> 
 * 构建组：x5-sys-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-12-16-上午11:55:19
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class InnerHandler implements JmsHandler<DefaultMsgVo> {
	
	@Autowired(required=false )
	InnerMsgService innerMsgService;

	@Override
	public String getType() {
		return MsgType.INNER.key();
	}

	@Override
	public boolean send(DefaultMsgVo vo) {
		
		if(innerMsgService==null) return false;
		
		List<IUser> receivers= vo.getReceivers();
		//构造接收人
		List<SysExecutor> list=new ArrayList<SysExecutor>();
		for(IUser user:receivers){
			SysExecutor executor=new SysExecutor(user.getUserId(),user.getFullname(),SysExecutor.TYPE_USER);
			list.add(executor);
		}
		IUser sender = vo.getSender();
		innerMsgService.sendMsg(vo.getSubject(), vo.getContent(),vo.getType(),sender,list);
		
		return false;
	}



	@Override
	public String getTitle() {
		return "内部消息";
	}



	@Override
	public boolean getIsDefault() {
		return true;
	}



	@Override
	public boolean getSupportHtml() {
		return false;
	}

}

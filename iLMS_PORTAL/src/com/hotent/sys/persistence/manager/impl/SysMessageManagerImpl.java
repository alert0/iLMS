package com.hotent.sys.persistence.manager.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.msg.InnerMsgService;
import com.hotent.sys.api.msg.model.SysExecutor;
import com.hotent.sys.persistence.dao.MessageReceiverDao;
import com.hotent.sys.persistence.dao.SysMessageDao;
import com.hotent.sys.persistence.manager.SysMessageManager;
import com.hotent.sys.persistence.model.MessageReceiver;
import com.hotent.sys.persistence.model.SysMessage;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：sys_msg 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 09:03:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysMessageManager")
public class SysMessageManagerImpl extends AbstractManagerImpl<String, SysMessage> implements SysMessageManager,InnerMsgService{
	@Resource
	SysMessageDao sysMessageDao;
	@Resource
	MessageReceiverDao messageReceiverDao;
	
	@Override
	protected Dao<String, SysMessage> getDao() {
		return sysMessageDao;
	}
	@Override
	public List<SysMessage> getMsgByUserId(QueryFilter queryFilter) {
		return sysMessageDao.getMsgByUserId(queryFilter);
	}

	
	@Override
	public ResultMessage sendMsg(String subject, String content,String messageType, IUser sender ,
			List<SysExecutor> receivers) {
		ResultMessage resultMessage = new ResultMessage();
		try {
			String id = UniqueIdUtil.getSuid();
			SysMessage sysMessage = new SysMessage();
			sysMessage.setId(id);
			sysMessage.setSubject(subject);
			sysMessage.setContent(content);
			sysMessage.setOwnerId(sender==null?"-1":sender.getUserId());
			sysMessage.setOwner(sender==null?"x5系统":sender.getFullname());
			sysMessage.setMessageType(messageType);
			sysMessage.setCanReply((short)0);
			sysMessage.setIsPublic((short)0);
			sysMessage.setCreateTime(new Date());
			sysMessageDao.create(sysMessage);
			
			for (SysExecutor sysExecutor : receivers) {
				MessageReceiver receiver = new MessageReceiver();
				receiver.setMsgId(id);
				receiver.setReceiverId(sysExecutor.getId());
				receiver.setReceiver(sysExecutor.getName());
				receiver.setReceiverType(sysExecutor.getType());
				messageReceiverDao.create(receiver);
			}
			resultMessage.setMessage("发送消息成功!");
			resultMessage.setResult(ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMessage.setCause(e.getMessage());
			resultMessage.setMessage("发送消息失败!");
			resultMessage.setResult(ResultMessage.FAIL);
		}
		return resultMessage;
	}
	
	
	@Override
	public void addMessageSend(SysMessage sysMessage) {
		String messageId=sysMessage.getId();
		String receiverIds = sysMessage.getReceiverId();
		String receiverNames = sysMessage.getReceiverName();
		String receiverOrgIds = sysMessage.getReceiverOrgId();
		String receiverOrgNames = sysMessage.getReceiverOrgName();
		IUser sender = ContextUtil.getCurrentUser();
		
		messageId = UniqueIdUtil.getSuid();
		sysMessage.setId(messageId);
		sysMessage.setOwnerId(sender.getUserId());
		sysMessage.setOwner(sender.getFullname());
		sysMessage.setCreateTime(new Date());
		
		//如果是公告，则不存在接收人，不在接收人表中添加信息，不能回复，
		if (sysMessage.getIsPublic()==SysMessage.iS_PUBLIC_YES) {
			sysMessageDao.create(sysMessage);
			return;//直接返回，不做以后操作
		}
		//把发送给组织的也设置给人员字段，保存到数据库
		String receriver = "";
		if (StringUtil.isNotEmpty(receiverNames)&&StringUtil.isNotEmpty(receiverOrgNames)) {
			receriver = receiverNames+","+receiverOrgNames;
		}else if (StringUtil.isNotEmpty(receiverNames)&&StringUtil.isEmpty(receiverOrgNames)) {
			receriver=receiverNames;
		}else if(StringUtil.isEmpty(receiverNames)&&StringUtil.isNotEmpty(receiverOrgNames)){
			receriver=receiverOrgNames;
		}
		sysMessage.setReceiverName(receriver);
		sysMessageDao.create(sysMessage);
		
		
		//转换人员
		if (StringUtil.isNotEmpty(receiverIds)) {
			String[] idArr = receiverIds.split(",");
			String[] nameArr = receiverNames.split(",");
			for (int i = 0; i < idArr.length; i++) {
				String id=idArr[i];
				if (StringUtil.isEmpty(id)) continue;
				String name="";
				if (nameArr.length>i)
					name=nameArr[i];
				MessageReceiver receiver = new MessageReceiver();
				receiver.setMsgId(messageId);
				receiver.setReceiverType(MessageReceiver.TYPE_USER);
				receiver.setReceiverId(id);
				receiver.setReceiver(name);
				messageReceiverDao.create(receiver);
			}
		}
		//转换组织
		if (StringUtil.isNotEmpty(receiverOrgIds)) {
			String[] orgIdArr = receiverOrgIds.split(",");
			String[] orgNameArr = receiverOrgNames.split(",");
			for (int i = 0; i < orgIdArr.length; i++) {
				String id=orgIdArr[i];
				if (StringUtil.isEmpty(id)) continue;
				String name="";
				if (orgNameArr.length>i)
					name=orgNameArr[i];
				MessageReceiver receiver = new MessageReceiver();
				receiver.setMsgId(messageId);
				receiver.setReceiverType(MessageReceiver.TYPE_GROUP);
				receiver.setReceiverId(id);
				receiver.setReceiver(name);
				messageReceiverDao.create(receiver);
			}
		}
	}
	/**
	 * 获取最新一条的未读的消息
	 */
	@Override
	public SysMessage getNotReadMsg(String userId) {
		return sysMessageDao.getOneNotReadMsgByUserId(userId);
	}
	
	/**
	 * 获取未读消息的条数
	 */
	@Override
	public int getNotReadMsgNum(String userId) {
		return sysMessageDao.getNotReadMsgNum(userId);
	}
	@Override
	public int getMsgSize(String receiverId) {
		return sysMessageDao.getMsgSize(receiverId);
	}
	
	
}

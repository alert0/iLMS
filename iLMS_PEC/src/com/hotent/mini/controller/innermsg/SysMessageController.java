package com.hotent.mini.controller.innermsg;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.json.PageJson;
import com.hotent.mini.web.util.MessageTypeUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.manager.MessageReadManager;
import com.hotent.sys.persistence.manager.MessageReplyManager;
import com.hotent.sys.persistence.manager.SysMessageManager;
import com.hotent.sys.persistence.model.MessageRead;
import com.hotent.sys.persistence.model.MessageReply;
import com.hotent.sys.persistence.model.SysMessage;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：sys_msg 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 09:03:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/innermsg/sysMessage/")
public class SysMessageController extends GenericController{
	@Resource
	SysMessageManager sysMessageManager;
	@Resource
	MessageReadManager messageReadManager;
	@Resource
	MessageReplyManager messageReplyManager;
	
	/**
	 * sys_msg列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		IUser currentUser = ContextUtil.getCurrentUser();
		queryFilter.addFilter("owner_id_", currentUser.getUserId(), QueryOP.EQUAL);
		PageList<SysMessage> sysMessageList=(PageList<SysMessage>)sysMessageManager.query(queryFilter);
		//消息类型值得转换
		for (int i = 0; i < sysMessageList.size(); i++) {
			SysMessage sysMessage = sysMessageList.get(i);
			String messageType= MessageTypeUtil.getValue(sysMessage.getMessageType());
			sysMessage.setMessageType(messageType);
		}
		return new PageJson(sysMessageList);
	}
	
	/**
	 * 编辑sys_msg信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		SysMessage sysMessage=null;
		if(StringUtil.isNotEmpty(id)){
			sysMessage=sysMessageManager.get(id);
		}
		Map<String, String> disMsgType = MessageTypeUtil.getDisPlayMsgType();
		Map<String, String> replyMsgType = MessageTypeUtil.getReplyMsgType();
		
		return getAutoView().addObject("sysMessage", sysMessage)
				.addObject("disMsgType", disMsgType)
				.addObject("replyMsgType", replyMsgType)
				.addObject("returnUrl", preUrl);
	}
	
	/**
	 * sys_msg明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		SysMessage sysMessage=null;
		if(StringUtil.isNotEmpty(id)){
			sysMessage=sysMessageManager.get(id);
		}
		String messageType= MessageTypeUtil.getValue(sysMessage.getMessageType());
		sysMessage.setMessageType(messageType);
		return getAutoView().addObject("sysMessage", sysMessage).addObject("returnUrl", preUrl);
	}
	/**
	 * sys_msg明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("getInfo")
	public ModelAndView getInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		String canReply=RequestUtil.getString(request, "canReply");
		
		return getAutoView()
				.addObject("id", id)
				.addObject("canReply", canReply)
				.addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存sys_msg信息
	 * @param request
	 * @param response
	 * @param sysMessage
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg=null;
		String sysMessageJson = RequestUtil.getString(request, "sysMessage");
		SysMessage sysMessage = (SysMessage) JSONObject.toBean(JSONObject.fromObject(sysMessageJson), SysMessage.class);
		try {
			sysMessageManager.addMessageSend(sysMessage);
			resultMsg="发送消息成功!";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="发送消息失败!";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	

	/**
	 * 批量删除sys_msg记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			sysMessageManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除消息成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除消息失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 获取内部消息类型
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMessageType")
	@ResponseBody
	public  Object getMessageType(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> disMsgType = MessageTypeUtil.getDisPlayMsgType();
		Map<String, String> replyMsgType = MessageTypeUtil.getReplyMsgType();
		Map<String, String> allMsgType = MessageTypeUtil.getAllMsgType();
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		map.put("disMsgType", disMsgType);
		map.put("replyMsgType", replyMsgType);
		map.put("allMsgType", allMsgType);
		return map;
	}
	
	/**
	 * 进入已读明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("readDetail")
	public ModelAndView readDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String messageId=RequestUtil.getString(request,"messageId");
		int canReply = RequestUtil.getInt(request, "canReply");
		return getAutoView().addObject("messageId", messageId).addObject("canReply", canReply);
	}
	
	
	/**
	 * 获取已读明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMessageRead")
	@ResponseBody
	public PageJson getMessageRead(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String messageId=RequestUtil.getString(request,"messageId");
		List<MessageRead> messageReadList=messageReadManager.getByMessageId(messageId);
		 return new PageJson(messageReadList);
	}
	
	
	/**
	 * 查看回复明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("replyDetail")
	public ModelAndView replyDetail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messageId=RequestUtil.getString(request,"messageId");
		int canReply = RequestUtil.getInt(request, "canReply");
		return getAutoView().addObject("messageId", messageId).addObject("canReply", canReply);
	}
	
	/**
	 * 获取回复明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMessageReply")
	@ResponseBody
	public PageJson getMessageReply(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String messageId=RequestUtil.getString(request,"messageId");
		List<MessageReply> messageReplyList=messageReplyManager.getByMessageId(messageId);
		return new PageJson(messageReplyList);
	}
	
	@RequestMapping("readMsgDialog")
	public ModelAndView readMsgDialog(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id=RequestUtil.getString(request,"id");
		 IUser currentUser = ContextUtil.getCurrentUser();
		int size = 0;
		SysMessage sysMessage = null;
		if(StringUtil.isNotEmpty(id)){
			sysMessage = sysMessageManager.get(id);
		}else{
			sysMessage=sysMessageManager.getNotReadMsg(currentUser.getUserId());
			size = sysMessageManager.getNotReadMsgNum(currentUser.getUserId());
		}
		
		MessageReply msgReply = new MessageReply();
		if(sysMessage !=null){
			messageReadManager.addMessageRead(sysMessage.getId(), currentUser);
			msgReply.setMsgId(sysMessage.getId());
			msgReply.setIsPrivate(new Short("1"));
		}else{
			sysMessage = new SysMessage();
			sysMessage.setContent("<span style='color:red'>暂无内部消息。</span>"); 
		}
		return getAutoView().addObject("sysMessage",sysMessage )
							.addObject("flag", size>1?true:false)
							.addObject("msgReply",msgReply );
	}
	
	@RequestMapping("notReadMsg")
	@ResponseBody
	public int notReadMsg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		IUser currentUser = ContextUtil.getCurrentUser();
		int size = sysMessageManager.getNotReadMsgNum(currentUser.getUserId());
		return size;
	}
	
}

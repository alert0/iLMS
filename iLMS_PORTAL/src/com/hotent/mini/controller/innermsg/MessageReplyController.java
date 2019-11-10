package com.hotent.mini.controller.innermsg;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.json.PageJson;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.manager.MessageReplyManager;
import com.hotent.sys.persistence.manager.SysMessageManager;
import com.hotent.sys.persistence.model.MessageReply;
import com.hotent.sys.persistence.model.SysMessage;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：sys_msg_reply 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 15:32:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/innermsg/messageReply/")
public class MessageReplyController extends GenericController{
	@Resource
	MessageReplyManager messageReplyManager;
	@Resource
	SysMessageManager sysMessageManager;
	
	/**
	 * sys_msg_reply列表(分页条件查询)数据
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
		PageList<MessageReply> messageReplyList=(PageList<MessageReply>)messageReplyManager.query(queryFilter);
		return new PageJson(messageReplyList);
	}
	
	/**
	 * 编辑sys_msg_reply信息页面
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
		String messageId=RequestUtil.getString(request, "id");
		SysMessage sysMessage = sysMessageManager.get(messageId);
		return getAutoView().addObject("sysMessage", sysMessage).addObject("returnUrl", preUrl);
	}
	
	/**
	 * sys_msg_reply明细页面
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
		MessageReply messageReply=null;
		if(StringUtil.isNotEmpty(id)){
			messageReply=messageReplyManager.get(id);
		}
		return getAutoView().addObject("messageReply", messageReply).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存sys_msg_reply信息
	 * @param request
	 * @param response
	 * @param messageReply
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,MessageReply messageReply) throws Exception{
		String resultMsg=null;
		try {
			IUser currentUser = ContextUtil.getCurrentUser();
			messageReply.setId(UniqueIdUtil.getSuid());
			messageReply.setReply(currentUser.getFullname());
			messageReply.setReplyId(currentUser.getUserId());
			messageReply.setReplyTime(new Date());
			messageReplyManager.create(messageReply);
			resultMsg="回复消息成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="回复消息失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除sys_msg_reply记录
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
			messageReplyManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除sys_msg_reply成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除sys_msg_reply失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("getByMessageId")
	@ResponseBody
	public Object getByMessageId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String messageId = RequestUtil.getString(request, "messageId");
		SysMessage sysMessage = sysMessageManager.get(messageId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysMessage", sysMessage);
		return map;
	}
	
	
}

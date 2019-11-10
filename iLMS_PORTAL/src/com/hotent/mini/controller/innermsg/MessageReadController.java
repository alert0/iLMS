package com.hotent.mini.controller.innermsg;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.json.PageJson;
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
 * 描述：sys_msg_read 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/innermsg/messageRead/")
public class MessageReadController extends GenericController{
	@Resource
	MessageReadManager messageReadManager;
	@Resource
	SysMessageManager sysMessageManager;
	@Resource
	MessageReplyManager messageReplyManager;
	
	/**
	 * sys_msg_read列表(分页条件查询)数据
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
		PageList<MessageRead> messageReadList=(PageList<MessageRead>)messageReadManager.query(queryFilter);
		return new PageJson(messageReadList);
	}
	
	/**
	 * 编辑sys_msg_read信息页面
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
		MessageRead messageRead=null;
		if(StringUtil.isNotEmpty(id)){
			messageRead=messageReadManager.get(id);
		}
		return getAutoView().addObject("messageRead", messageRead).addObject("returnUrl", preUrl);
	}
	
	/**
	 * sys_msg_read明细页面
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
		String messageId=RequestUtil.getString(request, "id");
		IUser currentUser = ContextUtil.getCurrentUser();
		SysMessage sysMessage = sysMessageManager.get(messageId);
		
		QueryFilter queryFilter=getQueryFilter(request);
		queryFilter.addFilter("reply_id_", currentUser.getUserId(), QueryOP.EQUAL);
		List<MessageReply> messageReplyList = messageReplyManager.query(queryFilter);
		//保存到已读表
		messageReadManager.addMessageRead(messageId, currentUser);
		ModelAndView mv = new ModelAndView("/innermsg/messageReadGet");
		return mv.addObject("sysMessage", sysMessage)
				.addObject("messageReplyList", messageReplyList)
				.addObject("userId", currentUser.getUserId())
				.addObject("returnUrl", preUrl);
	}
	/**
	 * sys_msg_read明细页面
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
		String isPublic=RequestUtil.getString(request, "isPublic","0");
		String canReply=RequestUtil.getString(request, "canReply","0");
		
		return getAutoView()
				.addObject("isPublic", isPublic)
				.addObject("canReply", canReply)
				.addObject("id", id)
				.addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存sys_msg_read信息
	 * @param request
	 * @param response
	 * @param messageRead
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,MessageRead messageRead) throws Exception{
		String resultMsg=null;
		String id=messageRead.getId();
		try {
			if(StringUtil.isEmpty(id)){
				messageRead.setId(UniqueIdUtil.getSuid());
				messageReadManager.create(messageRead);
				resultMsg="添加sys_msg_read成功";
			}else{
				messageReadManager.update(messageRead);
				resultMsg="更新sys_msg_read成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对sys_msg_read操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除sys_msg_read记录
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
			messageReadManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除sys_msg_read成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除sys_msg_read失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}

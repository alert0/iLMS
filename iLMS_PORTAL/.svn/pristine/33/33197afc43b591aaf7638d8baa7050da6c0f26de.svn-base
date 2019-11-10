package com.hotent.mini.controller.innermsg;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
import com.hotent.mini.web.util.MessageTypeUtil;
import com.hotent.sys.persistence.manager.MessageReceiverManager;
import com.hotent.sys.persistence.manager.SysMessageManager;
import com.hotent.sys.persistence.model.MessageReceiver;
import com.hotent.sys.persistence.model.SysMessage;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：sys_msg_receiver 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-17 17:49:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/innermsg/messageReceiver/")
public class MessageReceiverController extends GenericController{
	@Resource
	MessageReceiverManager messageReceiverManager;
	@Resource
	SysMessageManager sysMessageManager;
	
	/**
	 * sys_msg_receiver列表(分页条件查询)数据
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
		queryFilter.addFilter("receiverId", ContextUtil.getCurrentUserId(), QueryOP.EQUAL);
		PageList<SysMessage> sysMessageList = (PageList<SysMessage>) sysMessageManager.getMsgByUserId(queryFilter);
		Collections.sort(sysMessageList, new ReceiveTimeComparator());
		PageList<SysMessage> newSysMessageList = new PageList<SysMessage>();
		//消息类型值得转换
		for (int i = 0; i < sysMessageList.size(); i++) {
			SysMessage sysMessage = sysMessageList.get(i);
			String messageType= MessageTypeUtil.getValue(sysMessage.getMessageType());
			sysMessage.setMessageType(messageType);
			newSysMessageList.add(sysMessage);
		}
		newSysMessageList.setPageResult(sysMessageList.getPageResult());
		return new PageJson(newSysMessageList);
	}
	
	//（1）未读的消息排在前面，未读的消息按照“发信时间”从后往前排序   （2）已读的消息只按照“发信时间”从后往前排序；
	static class ReceiveTimeComparator implements Comparator {  
		@Override
		public int compare(Object o1, Object o2) {
			SysMessage p1 = (SysMessage) o1; // 强制转换  
			SysMessage p2 = (SysMessage) o2;  
			Date date1 = p1.getReceiveTime();
			Date date2 = p2.getReceiveTime();
			int flag = 1;
			if((date1!=null&&date2!=null)||(date1==null&&date2==null)){
				flag = -p1.getCreateTime().compareTo(p2.getCreateTime());
			}else if(date2!=null){
				flag = -1;
			}
            return flag;  
		}  
    } 


	/*@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = messageReceiverManager.getMsgType();
		return getAutoView();
	}*/
	
	/**
	 * 编辑sys_msg_receiver信息页面
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
		MessageReceiver messageReceiver=null;
		if(StringUtil.isNotEmpty(id)){
			messageReceiver=messageReceiverManager.get(id);
		}
		return getAutoView().addObject("messageReceiver", messageReceiver).addObject("returnUrl", preUrl);
	}
	
	/**
	 * sys_msg_receiver明细页面
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
		MessageReceiver messageReceiver=null;
		if(StringUtil.isNotEmpty(id)){
			messageReceiver=messageReceiverManager.get(id);
		}
		return getAutoView().addObject("messageReceiver", messageReceiver).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存sys_msg_receiver信息
	 * @param request
	 * @param response
	 * @param messageReceiver
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,MessageReceiver messageReceiver) throws Exception{
		String resultMsg=null;
		String id=messageReceiver.getId();
		try {
			if(StringUtil.isEmpty(id)){
				messageReceiver.setId(UniqueIdUtil.getSuid());
				messageReceiverManager.create(messageReceiver);
				resultMsg="添加sys_msg_receiver成功";
			}else{
				messageReceiverManager.update(messageReceiver);
				resultMsg="更新sys_msg_receiver成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对sys_msg_receiver操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除sys_msg_receiver记录
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
			String[] aryIds=RequestUtil.getStringAryByStr(request, "rid");
			messageReceiverManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除消息成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除消息失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
	@RequestMapping("mark")
	public void mark(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		String[] lAryId =RequestUtil.getStringAryByStr(request, "ids");
		try{
			messageReceiverManager.updateReadStatus(lAryId);
			message=new ResultMessage(ResultMessage.SUCCESS, "成功标记为已读!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.FAIL, ex.getMessage());
		
		}
		writeResultMessage(response.getWriter(), message);
	}
}

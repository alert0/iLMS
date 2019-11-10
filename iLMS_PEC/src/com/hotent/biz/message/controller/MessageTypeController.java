package com.hotent.biz.message.controller;


import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.biz.message.manager.MessageTypeManager;
import com.hotent.biz.message.model.MessageType;
import com.hotent.bpmx.persistence.model.BpmDefUser;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：xq_message_type 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 15:26:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/business/xqmessagetype/messageType")
public class MessageTypeController extends GenericController{
	@Resource
	MessageTypeManager messageTypeManager;
	
	/**
	 * xq_message_type列表(分页条件查询)数据
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
		PageList<Map<String,Object>> messageTypeList = (PageList<Map<String,Object>>)messageTypeManager.queryAll(queryFilter);
		return new PageJson(messageTypeList);
	}
	
	
	
	/**
	 * xq_message_type明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody MessageType getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		MessageType messageType=messageTypeManager.get(id);
		messageType.setOwnerNameJson(messageTypeManager.getAuthority(id,BpmDefUser.BPMDEFUSER_OBJ_TYPE.INDICATOR_COLUMN).toJSONString());
		return messageType;
	}
	
	/**
	 * 保存xq_message_type信息
	 * @param request
	 * @param response
	 * @param messageType
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody MessageType messageType) throws Exception{
		//用户
		IUser user = ContextUtil.getCurrentUser();
		String userId = user.getUserId();
		String authId =messageType.getId();
		String classificationCode = messageType.getClassificationCode();
		// 判断别名是否存在。
		Boolean isExist = messageTypeManager.isExistCode(messageType.getClassificationCode(), messageType.getId());
		if (isExist) {
			writeResultMessage(response.getWriter(), "分类名称：[" + classificationCode + "]已存在",
					ResultMessage.FAIL);
			return;
		}
		if(StringUtils.isEmpty(authId)){
			messageType.setId("");
			messageType.setCreateTime(new Date());
			//增加分类管理授权查询判断
			messageType.setCreateBy(userId);
			messageType.setCreator(user.getFullname());
		}else{
			messageType.setId(authId);
		}
		
		String myId = messageTypeManager.saveOrUpdateAuthorize(messageType);
		if(StringUtil.isNotEmpty(myId)){
			writeResultMessage(response.getWriter(),"保存授权信息成功！",ResultMessage.SUCCESS);
		}else{
			writeResultMessage(response.getWriter(),"保存授权信息失败！",ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除xq_message_type记录
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
			String[] aryIds=RequestUtil.getStringAryByStr(request, "authorizeId");
			messageTypeManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除xq_message_type成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除xq_message_type失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	
}

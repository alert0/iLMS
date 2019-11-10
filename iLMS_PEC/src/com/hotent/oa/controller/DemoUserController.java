package com.hotent.oa.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.oa.persistence.manager.DemoUserManager;
import com.hotent.oa.persistence.model.DemoUser;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.msg.constants.MsgType;
import com.hotent.sys.api.msg.model.DefaultMsgVo;
import com.hotent.sys.jms.DefaultJmsProducer;


/**
 * 
 * <pre> 
 * 描述：DemoUser 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-02 11:05:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/oa/demo/demoUser")
@Api(tags="DemoUserController")
public class DemoUserController extends GenericController{
	@Resource
	DemoUserManager demoUserManager;
	@Resource
	IUserService userServiceImpl;
	/**
	 * DemoUser列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	@ApiOperation(value = "通过帐号查询用户信息", httpMethod = "POST", notes = "暂无")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<DemoUser> demoUserList=(PageList<DemoUser>)demoUserManager.query(queryFilter);
		return new PageJson(demoUserList);
	}
	
	
	
	/**
	 * 消息发送测试
	 * @Title: messageProducer 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return: void
	 */
	@RequestMapping("messageProducer")
	public void messageProducer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg="";
		try {
			Map<String, String> attachments=new HashMap<String, String>();
			attachments.put("测试附件发送.txt", "D:\\测试附件发送.txt");
			DefaultJmsProducer producer=(DefaultJmsProducer)AppUtil.getBean("messageProducer");
			List<IUser> listUsers=new ArrayList<IUser>();
		    IUser user=userServiceImpl.getUserByAccount("admin");
			listUsers.add(user);
			DefaultMsgVo vo=new DefaultMsgVo("邮件标题","邮件内容",user,listUsers,MsgType.MAIL.key(),attachments);
			producer.sendToQueue(vo);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对DemoUser操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * DemoUser明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody DemoUser get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new DemoUser();
		}
		DemoUser demoUser=demoUserManager.get(id);
		return demoUser;
	}
	
	/**
	 * 保存DemoUser信息
	 * @param request
	 * @param response
	 * @param demoUser
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody DemoUser demoUser) throws Exception{
		String resultMsg=null;
		String id=demoUser.getId();
		try {
			if(StringUtil.isEmpty(id)){
				demoUser.setId(UniqueIdUtil.getSuid());
				demoUserManager.create(demoUser);
				resultMsg="添加DemoUser成功";
			}else{
				demoUserManager.update(demoUser);
				resultMsg="更新DemoUser成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对DemoUser操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除DemoUser记录
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
			demoUserManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除DemoUser成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除DemoUser失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}

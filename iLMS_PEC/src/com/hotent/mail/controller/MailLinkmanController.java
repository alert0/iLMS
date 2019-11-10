package com.hotent.mail.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.mail.model.MailLinkman;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mail.persistence.manager.MailLinkmanManager;
import com.hotent.mail.persistence.manager.MailManager;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：外部邮件最近联系 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:19:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mail/mail/mailLinkman")
public class MailLinkmanController extends GenericController{
	@Resource
	MailLinkmanManager mailLinkmanManager;
	@Resource
	MailManager mailManager;
	
	/**
	 * 外部邮件最近联系列表(分页条件查询)数据
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
		PageList<MailLinkman> mailLinkmanList=(PageList<MailLinkman>)mailLinkmanManager.query(queryFilter);
		return new PageJson(mailLinkmanList);
	}
	
	
	
	/**
	 * 外部邮件最近联系明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody MailLinkman getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "LINKID");
		if(StringUtil.isEmpty(id)){
			return new MailLinkman();
		}
		MailLinkman mailLinkman=mailLinkmanManager.get(id);
		return mailLinkman;
	}
	
	/**
	 * 保存外部邮件最近联系信息
	 * @param request
	 * @param response
	 * @param mailLinkman
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody MailLinkman mailLinkman) throws Exception{
		String resultMsg=null;
		String LINKID=mailLinkman.getLINKID();
		try {
			if(StringUtil.isEmpty(LINKID)){
				mailLinkman.setId(UniqueIdUtil.getSuid());
				mailLinkmanManager.create(mailLinkman);
				resultMsg="添加外部邮件最近联系成功";
			}else{
				mailLinkmanManager.update(mailLinkman);
				resultMsg="更新外部邮件最近联系成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对外部邮件最近联系操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除外部邮件最近联系记录
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
			mailLinkmanManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除外部邮件最近联系成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除外部邮件最近联系失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 最近联系人树形列表的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMailLinkmanData")
	@ResponseBody
	public List<MailLinkman> getOutMailLinkmanData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		String userId = ContextUtil.getCurrentUserId();
		String condition = request.getParameter("condition");
		List<MailLinkman> mailLinkmans=mailLinkmanManager.getAllByUserId(userId,condition);
		List<MailLinkman> mailLinkmanList = new ArrayList<MailLinkman>();
		for(MailLinkman man :mailLinkmans){
			String linkName = mailManager.getNameByEmail(man.getLINKADDRESS());//查看联系人
			man.setLINKADDRESS(linkName+"("+man.getLINKADDRESS()+")");
			man.setLINKNAME(linkName);
			mailLinkmanList.add(man);
		}
		return mailLinkmanList;
	}
}

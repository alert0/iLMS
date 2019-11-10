package com.hotent.mail.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mail.persistence.manager.MailAttachmentManager;
import com.hotent.base.core.mail.model.MailAttachment;


/**
 * 
 * <pre> 
 * 描述：外部邮件附件表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:18:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mail/mail/mailAttachment")
public class MailAttachmentController extends GenericController{
	@Resource
	MailAttachmentManager mailAttachmentManager;
	
	/**
	 * 外部邮件附件表列表(分页条件查询)数据
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
		PageList<MailAttachment> mailAttachmentList=(PageList<MailAttachment>)mailAttachmentManager.query(queryFilter);
		return new PageJson(mailAttachmentList);
	}
	
	
	
	/**
	 * 外部邮件附件表明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody MailAttachment getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "FILEID");
		if(StringUtil.isEmpty(id)){
			return new MailAttachment();
		}
		MailAttachment mailAttachment=mailAttachmentManager.get(id);
		return mailAttachment;
	}
	
	/**
	 * 保存外部邮件附件表信息
	 * @param request
	 * @param response
	 * @param mailAttachment
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody MailAttachment mailAttachment) throws Exception{
		String resultMsg=null;
		String FILEID=mailAttachment.getId();
		try {
			if(StringUtil.isEmpty(FILEID)){
				mailAttachment.setId(UniqueIdUtil.getSuid());
				mailAttachmentManager.create(mailAttachment);
				resultMsg="添加外部邮件附件表成功";
			}else{
				mailAttachmentManager.update(mailAttachment);
				resultMsg="更新外部邮件附件表成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对外部邮件附件表操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除外部邮件附件表记录
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
			String[] aryIds=RequestUtil.getStringAryByStr(request, "FILEID");
			mailAttachmentManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS,"删除外部邮件附件表成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除外部邮件附件表失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}

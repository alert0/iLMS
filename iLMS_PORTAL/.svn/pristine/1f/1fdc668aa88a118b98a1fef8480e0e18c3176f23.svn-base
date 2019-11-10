package com.hotent.mail.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.mail.model.Mail;
import com.hotent.base.core.mail.model.MailAttachment;
import com.hotent.base.core.mail.model.MailLinkman;
import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ExceptionUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mail.persistence.manager.MailAttachmentManager;
import com.hotent.mail.persistence.manager.MailLinkmanManager;
import com.hotent.mail.persistence.manager.MailManager;
import com.hotent.mail.persistence.manager.MailSettingManager;
import com.hotent.mini.web.util.AppFileUtil;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：外部邮件 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:49:46
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mail/mail/mail")
public class MailController extends GenericController{
	@Resource
	MailManager mailManager;
	@Resource
	MailSettingManager mailSettingManager;
	@Resource
	MailAttachmentManager mailAttachmentManager;
	@Resource
	MailLinkmanManager mailLinkmanManager;
	@Resource
	FileManager fileManager;
	@Resource
	UserManager userManager;
	Properties properties;
	
	/**
	 * 外部邮件列表(分页条件查询)数据
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
		String types = RequestUtil.getString(request, "types");
		if(StringUtil.isNotEmpty(types)){
			String mailSetId = RequestUtil.getString(request, "mailSetId");
			queryFilter.addFilter("TYPE_", types, QueryOP.EQUAL);
			queryFilter.addFilter("SET_ID_", mailSetId, QueryOP.EQUAL);
		}
		PageList<Mail> mailList=(PageList<Mail>)mailManager.query(queryFilter);
		return new PageJson(mailList);
	}
	
	/**
	 * 取得邮件分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mailList")
	public ModelAndView mailList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String userId=ContextUtil.getCurrentUserId();
		MailSetting defaultMail= mailSettingManager.getByIsDefault(userId);
		if(BeanUtils.isEmpty(defaultMail)){
			ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL,"无默认邮箱！");
			/*return ServiceUtil.getTipInfo("无默认邮箱！");*/
			response.getWriter().print(resultMessage);
			return null;
		}
		String mailSetId = RequestUtil.getString(request, "id",defaultMail.getId());
		int type=RequestUtil.getInt(request, "types",1);
		MailSetting mail= mailSettingManager.get(mailSetId);
		
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addParamsFilter("userId", userId);
		queryFilter.addParamsFilter("setId", mailSetId);
		queryFilter.addParamsFilter("type", type);
		List<Mail> list=mailManager.getFolderList(queryFilter);
		
		ModelAndView mv=this.getAutoView().addObject("mailList",list)
										.addObject("mailSetId",mailSetId)
										.addObject("mailUserSet", mail)
										.addObject("types", type);
		return mv;
	}
	
	
	
	/**
	 * 
	 * 邮箱同步处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sync")
	public void sync(HttpServletRequest request,HttpServletResponse response) throws Exception 
	{	
		String id= RequestUtil.getString(request, "id");
		MailSetting mailSetting= mailSettingManager.get(id);
		try {
			//获取UID列表
			List<String> uidList= mailManager.getUIDBySetId(id);
			//读取邮件
			List<com.hotent.core.mail.model.Mail> mailList= mailManager.getMailListBySetting(mailSetting, uidList);
			//保存邮件
			mailManager.saveMail(mailList, id);
			
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, "同步邮件成功"));
		} catch (Exception e) {
			String str = ThreadMsgUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL,"同步邮件失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = "同步邮件失败，请检查邮箱设置是否正确！";
				ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL, message);
				response.getWriter().print(resultMessage);
				e.printStackTrace();
			}
		}
	}	
	
	
	
	/**
	 * 外部邮件明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody Mail getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new Mail();
		}
		Mail mail = mailManager.get(id);
		int type=mail.getType();
		if(type== Mail.Mail_InBox){
			mailManager.emailRead(mail);
		}
		List<MailAttachment> attachments = mailAttachmentManager.getByMailId(mail.getId());
		if(type==Mail.Mail_OutBox || type==Mail.Mail_DraftBox){
			attachments = mailAttachmentManager.getByOutMailFileIds(mail.getFileIds());
		}else {
			attachments = mailAttachmentManager.getByMailId(mail.getId());
		}
		mail.setMailAttachments(attachments);
		return mail;
	}
	
	/**
	 * 批量删除外部邮件记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] lAryId =RequestUtil.getStringAryByStr(request, "id");
		int type=RequestUtil.getInt(request, "types");
		
		ResultMessage message=null;
		try{
			if(type==4){
				mailManager.removeByIds(lAryId);
				message=new ResultMessage(ResultMessage.SUCCESS, "成功删除本地上邮件!");
			}else if(type==3||type==2){//直接删除本地草稿箱/发件箱中的邮件
				mailManager.removeByIds(lAryId);
				message=new ResultMessage(ResultMessage.SUCCESS, "成功删除本地上邮件!");
			}else{//将收件箱与发件箱邮件移至垃圾箱
				mailManager.addDump(lAryId);
				message=new ResultMessage(ResultMessage.SUCCESS, "成功将邮件移至垃圾箱");
			}
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.FAIL, "删除失败:" + ex.getMessage());
		}
		writeResultMessage(response.getWriter(), message.getMessage(), message.getResult());
	}
	
	/**
	 * 无邮件时的提示信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("warn")
	public ModelAndView warn(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int count=mailSettingManager.getCountByUserId(ContextUtil.getCurrentUserId());
		return getAutoView().addObject("count", count);
	}
	
	/**
	 * 回复邮件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("reply")
	public ModelAndView reply(HttpServletRequest request) throws Exception{
		String mailId=RequestUtil.getString(request, "mailId");
		String mailSetId=RequestUtil.getString(request, "mailSetId");
		MailSetting mailUserSeting= mailSettingManager.get(mailSetId);
		
		Mail mail=mailManager.getMailReply(mailId);
		return getAutoView().addObject("mail",mail)
							.addObject("mailUserSeting", mailUserSeting)
							.addObject("mailSetId", mailSetId);
	}
	
	
	/**
	 * 取得邮件明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String mailId=RequestUtil.getString(request,"mailId");
		Mail mail = mailManager.get(mailId);
		int type=mail.getType();
		String mailSetId=RequestUtil.getString(request,"mailSetId");
		List<MailAttachment> attachments = mailAttachmentManager.getByMailId(mailId);
		if(type== Mail.Mail_InBox){
			mailManager.emailRead(mail);
		}
		
		if(type==Mail.Mail_OutBox || type==Mail.Mail_DraftBox){
			attachments = mailAttachmentManager.getByOutMailFileIds(mail.getFileIds());
		}else {
			attachments = mailAttachmentManager.getByMailId(mailId);
		}
		
		return getAutoView().addObject("Mail", mail)
							.addObject("outMailSetId", mailSetId)
							.addObject("attachments",attachments);
	}
	
	/**
	 * 邮件编辑
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mailEdit")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		String mailId=RequestUtil.getString(request,"mailId");
		String userId=ContextUtil.getCurrentUserId();
		String returnUrl=RequestUtil.getPrePage(request);
		Mail mail=null;
		if(StringUtil.isNotZeroEmpty(mailId)){
			mail=new Mail();
		}else{
			mail= mailManager.get(mailId);
		}
		//SysAuditThreadLocalHolder.putParamerter("isAdd", isadd);
		List<MailSetting> list=mailSettingManager.getMailByUserId(userId);
		return getAutoView().addObject("mail",mail).addObject("returnUrl", returnUrl)
				.addObject("mailSettingList",list);
	}
	
	/**
	 * 获得邮箱树形列表的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMailTreeData")
	@ResponseBody
	public List<MailSetting> getMailTreeData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String userId= ContextUtil.getCurrentUserId();
		
		List<MailSetting> list= mailManager.getMailTreeData(userId);
		return list;
	}
	
	/**
	 * 获取邮件接收服务器类型的json数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getRecieveServerTypeData")
	@ResponseBody
	public String getRecieveServerType(HttpServletRequest request,HttpServletResponse response){
		String id=RequestUtil.getString(request, "mailSetId");
		MailSetting mailSetting= mailSettingManager.get(id);
		String type= mailSetting.getMailType();
		return type;
	}
	
	/**
	 * 下载邮件附件<br>
	 * 1、从SysFile对应的表中获取，发邮件时，上传的文件信息会记录在此表中<br>
	 * 2、从OutMailAttachment对应的表中获取，收取邮件时，附件的信息会记录在此表中<br>
	 * 这里有两种情况：<br>
	 * 一、OutMailAttachment表中的filePath字段不为空，则可以直接通过此路径下载<br>
	 * 二、否则，会重新连接邮箱，查找出对应的邮件，并下载其附件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("downLoadAttach")
	public void downLoadAttach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String fileId=RequestUtil.getString(request, "fileId");
		try {
			DefaultFile sysFile = fileManager.get(fileId);
			if(sysFile!=null){// 发件箱中的附件，会在SysFile中有记录
				String filePath = AppFileUtil.getBasePath()+File.separator+sysFile.getFilePath();
				String fileName = sysFile.getFileName()+"."+sysFile.getExt();
				if(StringUtil.isEmpty(filePath)){// 上传的文件存在数据库中
					RequestUtil.downLoadFileByByte(request, response, sysFile.getBytes(), fileName);
				}else{
					RequestUtil.downLoadFile(request, response, filePath, fileName);
				}
			}else {// 否则，为收件箱中的附件，在OutMailAttachment中取得
				MailAttachment entity = mailAttachmentManager.get(fileId);
				if(entity==null){
					ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL,"文件下载失败: 找不到此文件");
					response.getWriter().print(resultMessage);
					return;
				}
				String filePath = entity.getFilePath();
				if(StringUtil.isEmpty(filePath)) {
					filePath = mailManager.mailAttachementFilePath(entity);
				}
				RequestUtil.downLoadFile(request, response, filePath, entity.getFileName());
			}
		} catch (Exception e) {
			String str = ThreadMsgUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL,"文件下载失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
		
	
	/**
	 * 添加或更新邮件。
	 * @param request
	 * @param response
	 * @param outMail 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("send")
	public void save(HttpServletRequest request, HttpServletResponse response,@RequestBody Mail mail) throws Exception
	{
		ModelAndView mv= new ModelAndView();
		int type= mail.getType();
		String userId=ContextUtil.getCurrentUserId();
		int isReply=RequestUtil.getInt(request, "isReply",0);
		MailSetting mailSetting=mailSettingManager.getMailByAddress(mail.getSenderAddress());
		mail.setSendDate(new Date());
		mail.setIsReply((short)isReply);
		mail.setUserId(userId);
		mail.setSenderName(mailSetting.getNickName());
		mail.setSetId(mailSetting.getId());
		String context=request.getContextPath();
		String basePath=AppFileUtil.getBasePath();
		String msg = "邮件发送";
		ResultMessage message=null;
		try{
			//获取邮件地址
			Set<String> list= getMailAddress(mail);
			//发送邮件
			if(type==2){
				mailManager.sendMail(mail,userId,mail.getId(),isReply,context,basePath);
				handLinkMan(userId, list);
				message=new ResultMessage(ResultMessage.SUCCESS, msg+"成功!");
			}
			//草稿
			else{
				msg = "邮件保存";
				if(StringUtil.isZeroEmpty(mail.getId())){
					mail.setId(UniqueIdUtil.getSuid());
					//添加发出邮件
					mailManager.create(mail);
				}else{
					mailManager.update(mail);
				}
			}
			Set<String> set = checkAddress(list);
			mv.addObject("addrList", set);
			message=new ResultMessage(ResultMessage.SUCCESS, msg+"成功!");
		}catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, msg+"失败!",e.getMessage());
			e.printStackTrace();
		}

		writeResultMessage(response.getWriter(), message.getCause(), message.getResult());
		
	}
	
	/**
	 * 处理联系人
	 * @param userId
	 * @param list
	 * @throws Exception 
	 * void
	 */
	private void handLinkMan(String userId, Set<String> list) throws Exception{
		MailLinkman man =null;
		//向最近联系人中增加记录或更新记录
		for(String address:list){
			man = mailLinkmanManager.findLinkMan(address, userId);
			String linkName = mailManager.getNameByEmail(address);
			if(man!=null){//更新
				man.setSENDTIMES(man.getSENDTIMES()+1);
				man.setLINKNAME(linkName);
				Date date = new Date(System.currentTimeMillis());
				man.setSENDTIME(date);
				mailLinkmanManager.update(man);
			}else{//添加
				man=new MailLinkman();
				man.setSENDTIMES((long)1);
				man.setUSERID(userId);
				man.setLINKNAME(linkName);
				man.setSENDTIME(new Date());
				man.setLINKADDRESS(address);
				man.setLINKID(UniqueIdUtil.getSuid());
				mailLinkmanManager.create(man);
			}
		}
	}
	
	
	private Set<String> getMailAddress(Mail mail){
		String toAddess=mail.getReceiverAddresses();
		String ccAddress=mail.getCopyToAddresses();
		String bccAddress=mail.getBcCAddresses();
		List<String> list=new ArrayList<String>();
		addAddress(toAddess,list);
		addAddress(ccAddress,list);
		addAddress(bccAddress,list);
		Set<String> set = new HashSet(list);
		return set;
	}
	
	
	private void addAddress(String address,List<String> list){
		if(StringUtil.isEmpty(address)) return;
		address=StringUtil.trim(address, ",");
		String[] aryAddress=address.split(",");
		for(String addr:aryAddress){
			list.add(addr);
		}
	}
	
	/**
	 * 检查地址是否存在于联系人列表
	 * @param set
	 * @return 
	 * Set&lt;String>
	 */
	private Set<String> checkAddress(Set<String> set){
		String currentUserId=ContextUtil.getCurrentUserId();
		Set<String> rtnset = new HashSet<String>();
		for(String addr:set){
			//判断地址是否存在于联系人列表当中
			List<User> list = userManager.getByUserEmail(addr);
			if(BeanUtils.isNotEmpty(list)) continue;
			rtnset.add(addr);
		}
		return rtnset;
	}
			
	
	/**
	 * 发送系统错误报告至公司邮箱
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("sendError")
	public void sendError(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String errorMsg=RequestUtil.getString(request, "errorMsg");
		String errorUrl=RequestUtil.getString(request, "errorUrl");
		String content="<div>"+errorUrl+"</div><br><div>"+errorMsg+"</div>";
		
		MailSetting outUser=new MailSetting();
		
		outUser.setSmtpPort(properties.getProperty("port"));
		outUser.setSmtpHost(properties.getProperty("host"));
		String recieveAdress=properties.getProperty("recieveAdress");
		String sendMail=properties.getProperty("sendMail");
		outUser.setMailAddress(sendMail);
		outUser.setPassword(EncryptUtil.encrypt("htsoft"));
		outUser.setNickName("BPMX5错误中心");//BPMX5错误中心
		try {
			mailManager.sendError(content,recieveAdress,outUser);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.SUCCESS, "发送错误报告成功"));
		} catch (Exception ex) {
			String str = ThreadMsgUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL,"发送错误报告失败:"+ str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.FAIL, message);
				response.getWriter().print(resultMessage);
			}
		}
		
	}
	
}

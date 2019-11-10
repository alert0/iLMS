package com.hotent.mail.persistence.manager.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.mail.model.Mail;
import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.activiti.def.graph.ilog.TransformUtil;
import com.hotent.core.mail.MailUtil;
import com.hotent.core.mail.api.AttacheHandler;
import com.hotent.core.mail.model.MailAttachment;
import com.hotent.core.mail.model.MailSeting;
import com.hotent.mail.persistence.dao.MailDao;
import com.hotent.mail.persistence.manager.MailAttachmentManager;
import com.hotent.mail.persistence.manager.MailManager;
import com.hotent.mail.persistence.manager.MailSettingManager;
import com.hotent.mail.persistence.util.AppFileUtil;
import com.hotent.org.persistence.dao.UserDao;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.jms.DefaultJmsProducer;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <pre> 
 * 描述：外部邮件 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:49:46
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("mailManager")
public class MailManagerImpl extends AbstractManagerImpl<String, Mail> implements MailManager{
	
	protected static Logger logger = LoggerFactory.getLogger(TransformUtil.class);
	static short MAIL_NO_READ=0;//未读
	static short MAIL_IS_READ=1;//已读
	static Integer MAIL_IS_RECEIVE = 1;// 收件箱
	static Integer MAIL_IS_SEND = 2;// 发件箱
	static Integer MAIL_IS_DRAFT = 3;// 草稿箱
	static Integer MAIL_IS_DELETE = 4;// 垃圾箱
	@Resource
	MailDao mailDao;
	@Resource
	private MailSettingManager mailSettingManager;
	@Resource
	private MailSettingManagerImpl mailSettingService;
	@Resource
	MailAttachmentManager mailAttachmentManager;
	@Resource
	FileManager fileManager;
	@Resource
	UserDao userDao;
	@Resource
	private DefaultJmsProducer messageProducer;
	@Override
	protected Dao<String, Mail> getDao() {
		return mailDao;
	}
	
	/**
	 * 保存邮件至垃圾箱
	 * @param request
	 * @param MailSetting
	 * @return
	 */
	public void addDump(String[] lAryId) {
		for(String l:lAryId){
			Mail mail = mailDao.get(l);	
			mailDao.updateTypes(mail.getId(),MAIL_IS_DELETE);
		}
	}
	
	/**
	 * 批量删除(如果有附件，也同时删除)
	 */
	public void removeByIds(String[] ids){
		for(String id:ids){
			this.remove(id);
			//删除邮件附件
			delAttachmentsByMailId(id);
		}
	}
	
	
	private void delAttachmentsByMailId(String mailId){
		try {
			List<com.hotent.base.core.mail.model.MailAttachment> attachments = mailAttachmentManager.getByMailId(mailId);
			if(BeanUtils.isNotEmpty(attachments)){
				for (com.hotent.base.core.mail.model.MailAttachment mailAttachment : attachments) {
					FileUtil.deleteFile(mailAttachment.getFilePath());
				}
			}
			mailAttachmentManager.delByMailId(mailId);;
		} catch (Exception e) {}
	}
	
	/**
	 * 浏览邮件
	 * @param Mail
	 * @param MailSetting
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
	public void emailRead(Mail mail)throws NoSuchProviderException, MessagingException {
		if(Mail.Mail_IsNotRead.shortValue() == mail.getIsRead().shortValue()
				&& Mail.Mail_InBox.shortValue() != mail.getType().shortValue()) return;
		mail.setIsRead(Mail.Mail_IsRead);
		mailDao.update(mail);
	}	
	
	
	/**
	 * 根据setId获取邮件的唯一ID列表。
	 * @param setId
	 * @return
	 */
	public List<String> getUIDBySetId(String setId){
		List<String> uidList = mailDao.getUIDBySetId(setId);
		return uidList;
	}
	
	/**
	 * 根据邮箱设定获取邮件列表。
	 * @param MailSetting
	 * @param uidList
	 * @return
	 * @throws Exception
	 */
	public List<com.hotent.core.mail.model.Mail> getMailListBySetting(final MailSetting mailSetting,List<String> uidList) throws Exception{
		
		MailSeting seting = mailSettingService.getBymailSetting(mailSetting);
		MailUtil mailUtil = new MailUtil(seting);
		
		String latestEmailId = "";
	
		if(BeanUtils.isNotEmpty(uidList)){
			latestEmailId = uidList.get(0);
		}else if(uidList == null){
			uidList = new ArrayList<String>();
		}
		final List<String> finalList = uidList;
		List<com.hotent.core.mail.model.Mail> list = mailUtil.receive(new AttacheHandler() {
			
			@Override
			public Boolean isDownlad(String UID) {
				return !finalList.contains(UID);
			}
			
			@Override
			public void handle(Part part, com.hotent.core.mail.model.Mail mail) {
				try {
					saveAttach(part, mail, mailSetting.getMailAddress());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, latestEmailId);
		
		return list;
	}
	
	
	
	
	public void saveMail(List<com.hotent.core.mail.model.Mail>  list,String setId) throws Exception {
		for(com.hotent.core.mail.model.Mail mail:list){
			Mail bean = getOutMail(mail, setId);
			// 主键
			String mailId = UniqueIdUtil.getSuid();
			bean.setId(mailId);
			// 邮件标识
			bean.setMessageId(mail.getUID());
			mailDao.create(bean);
			logger.info("已下载邮件"+bean.getSubject());
			List<MailAttachment> attachments = mail.getMailAttachments();
			if(BeanUtils.isEmpty(attachments)) continue ;
			com.hotent.base.core.mail.model.MailAttachment mailAttachment ;
			for(MailAttachment attachment:attachments){
				String fileName = attachment.getFileName();
				String filePath = attachment.getFilePath();
				String ext = FileUtil.getFileExt(fileName);
				String fileId = StringUtil.isNotEmpty(filePath)?new String(new File(filePath).getName().replace("."+ext, "")):UniqueIdUtil.getSuid();
				mailAttachment = new com.hotent.base.core.mail.model.MailAttachment();
				mailAttachment.setId(fileId);
				mailAttachment.setFileName(attachment.getFileName());
				mailAttachment.setFilePath(filePath);
				mailAttachment.setMailId(mailId);
				mailAttachmentManager.create(mailAttachment);
			}
		}
	}
	
	/**
	 * 获得Mail实体
	 * @param message
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private Mail getOutMail(com.hotent.core.mail.model.Mail mail, String setId)throws Exception{
		Mail bean =new Mail();
		Date sentDate = null;
		if (mail.getSendDate() != null) {
			sentDate = mail.getSendDate();
		} else {
			sentDate = new Date();
		}
		//邮件发送时间
		bean.setSendDate(sentDate);
		bean.setSetId(setId);
		bean.setSubject(mail.getSubject());
		bean.setContent(mail.getContent());
		//发件人
		bean.setSenderAddress(mail.getSenderAddress());
		bean.setSenderName(mail.getSenderName());
		//接受者
		bean.setReceiverAddresses(mail.getReceiverAddresses());
		bean.setReceiverName(mail.getReceiverName());
		//暗送者
		bean.setBcCAddresses(mail.getBcCAddresses());
		bean.setBccName(mail.getBccName());
		//抄送者
		bean.setCopyToAddresses(mail.getCopyToAddresses());
		bean.setCopyToName(mail.getCopyToName());
		bean.setType(Mail.Mail_InBox);
		bean.setIsRead(Mail.Mail_IsNotRead);
		bean.setUserId(ContextUtil.getCurrentUserId());
		return bean;
	}
	
	/**
     * 将邮件中的附件保存在本地指定目录下
     * @param filename
     * @param in
     * @return
     */
    private void saveAttach(Part message, com.hotent.core.mail.model.Mail mail, String userAccount)throws Exception{
    	String filename=MimeUtility.decodeText(message.getFileName());
    	Calendar cal=Calendar.getInstance();//使用日历类
    	int year=cal.get(Calendar.YEAR);//得到年
    	int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
    	String filePath = AppFileUtil.getBasePath()+File.separator+ "emailAttachs"+File.separator
				+userAccount+File.separator+year+File.separator+month+File.separator
				+UniqueIdUtil.getUId()+"."+FileUtil.getFileExt(filename);
    	FileUtil.createFolderFile(filePath);
		FileUtil.writeFile(filePath, message.getInputStream());
		mail.getMailAttachments().add(new MailAttachment(filename, filePath));
    }
	
    /**
     * 邮箱树形列表的json数据
     * @param request
     * @param list
     * @param listTemp
     * @param listEnd
     * @throws Exception 
     */
	public List<MailSetting> getMailTreeData(String userId) throws Exception {
		List<MailSetting> list= mailSettingManager.getMailByUserId(userId);
		List<MailSetting> temp=new ArrayList<MailSetting>();
		MailSetting omus=null;
		for(MailSetting beanTemp:list){
			beanTemp.setParentId("0");
			String id=beanTemp.getId();
			temp.add(beanTemp);
		    for(int i=0;i<4;i++){
		    	omus=new MailSetting();
		    	if(i==0){ 
		    		omus.setNickName("收件箱("+getCount(id,MAIL_IS_RECEIVE)+")");
			    	omus.setTypes(MAIL_IS_RECEIVE);
		    	}else if(i==1){
		    		omus.setNickName("发件箱("+getCount(id,MAIL_IS_SEND)+")");
			    	omus.setTypes(MAIL_IS_SEND);
		    	}else if(i==2){
		    		omus.setNickName("草稿箱("+getCount(id,MAIL_IS_DRAFT)+")");
			    	omus.setTypes(MAIL_IS_DRAFT);
		    	}else {
		    		omus.setNickName("垃圾箱("+getCount(id,MAIL_IS_DELETE)+")");
			    	omus.setTypes(MAIL_IS_DELETE);
			    }
				omus.setId(UniqueIdUtil.getSuid());
				omus.setParentId(beanTemp.getId());
			    temp.add(omus);
		    }
		}
		return temp;
	}
	
	/**
	 * 获取邮箱的分类邮件，如收件箱，发件箱，草稿箱
	 * @param queryFilter
	 * @return
	 */
	public List<Mail> getFolderList(QueryFilter queryFilter){
		return mailDao.getFolderList(queryFilter);
	}
	
	/**
	 * 获取邮箱的分类邮件数
	 * @param address
	 * @param type
	 * @param userId
	 * @return
	 */
	private int getCount(String id,int type){
		return mailDao.getFolderCount(id, type);
	}
	
	/**
	 * 得到用户默认邮箱中的邮件列表
	 * @param queryFilter
	 * @return
	 */
	public List<Mail> getDefaultMailList(QueryFilter queryFilter) {
		return mailDao.getDefaultMailList(queryFilter);
	}

	/**
	 * 发送邮件,保存邮件信息至本地,添加/更新最近联系人
	 * @param outMail
	 * @param outMailFiles
	 * @param fileIds
	 * @throws Exception
	 */
	public String sendMail(Mail outMail,String userId,String mailId,int isReply,String context,String basePath) throws Exception {
		String content=outMail.getContent();
		if(StringUtil.isEmpty(mailId) || "0".equals(mailId)||isReply==1){
			outMail.setId(UniqueIdUtil.getSuid());
			create(outMail);
		}else{
			mailDao.updateTypes(mailId, 2);
		}
		outMail.setContent(content);
		getMailAttachments(outMail, basePath);
		sendToMQ(outMail ,outMail.getSetId());
		return outMail.getId();
	}
	
	/**
	 * 发送邮件到mq队列
	 * @param mail
	 * @param outMailUserSeting 
	 * void
	 * @throws Exception 
	 */
	public void sendToMQ(Mail mail,String mailUserSetingId) throws Exception{
		com.hotent.base.core.mail.MailUtil mailSender =(com.hotent.base.core.mail.MailUtil) AppUtil.getBean("mailSender");
		MailSetting mailSetting = mailSettingManager.get(mailUserSetingId);
		try {
			mailSender.send(mail,mailSetting);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 得到用于回复页面显示信息
	 * @param mailId
	 * @return
	 */
	public Mail getMailReply(String mailId) {
		Mail outMail= get(mailId);
		outMail.setIsReply(Mail.Mail_IsReplay);
		outMail.setSubject("回复:" + outMail.getSubject());
		return outMail;
	}
	
	/**
	 * 获取邮件附件
	 * @param outMail
	 * @param outMailUserSeting
	 * @return
	 * @throws Exception 
	 */
	private Mail getMailAttachments(Mail outMail, String basePath) throws Exception{
		if(BeanUtils.isNotEmpty(outMail)&&StringUtil.isNotEmpty(outMail.getFileIds())){
			String fileIds=outMail.getFileIds().replaceAll("quot;", "\"");
			JSONArray jsonArray = JSONArray.fromObject(fileIds);
			if(jsonArray.size()>0){
				DefaultFile sysFile = null ;
				List<com.hotent.base.core.mail.model.MailAttachment> attachments = outMail.getMailAttachments();
				for(Object obj:jsonArray){
					JSONObject json = (JSONObject)obj;
					sysFile= fileManager.get(json.getString("id"));
					String filePath = sysFile.getFilePath();
					String fileName = sysFile.getFileName()+"."+sysFile.getExt();
					if(StringUtil.isEmpty(filePath)){
						attachments.add(new com.hotent.base.core.mail.model.MailAttachment(fileName,sysFile.getBytes()));
						continue;
					}
					if(StringUtil.isEmpty(basePath)){
						//路径从配置文件中获取
						basePath= AppFileUtil.getBasePath();
					}
					filePath = basePath+File.separator+filePath;
					attachments.add(new com.hotent.base.core.mail.model.MailAttachment(fileName,filePath));
				}
			}
		}
		return outMail;
	}
	
	/**
	 * 发送系统错误报告至公司邮箱
	 * @param content
	 * @param recieveAdress
	 * @param mail
	 * @throws Exception
	 */
	public void sendError(String errorMsg, String recieveAdress, MailSetting mailSetting)throws Exception {
		MailSeting seting = mailSettingService.getBymailSetting(mailSetting);
		MailUtil mailUtil = new MailUtil(seting);
		com.hotent.core.mail.model.Mail mail = new com.hotent.core.mail.model.Mail();
		mail.setContent(errorMsg);
		mail.setSubject("BPMX5错误报告！");
		mail.setReceiverAddresses(recieveAdress);
		mailUtil.send(mail);
		
	}

	public void delBySetId(String setId) {
		mailDao.delBySetId(setId);
	}

	/**
	 * 返回附件下载地址
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public String mailAttachementFilePath(com.hotent.base.core.mail.model.MailAttachment entity) throws Exception {
		
		Mail outMail = get(entity.getId());
		String setId = outMail.getSetId(); 
		final String emailId = outMail.getMessageId();
		MailSetting outMailSetting= mailSettingManager.get(setId);
		final String mailAddress = outMailSetting.getMailAddress();
		MailSeting seting = mailSettingService.getBymailSetting(outMailSetting);
		seting.setIsHandleAttach(true);
		MailUtil mailUtil = new MailUtil(seting);
		List<com.hotent.core.mail.model.Mail> list = mailUtil.receive(new AttacheHandler() {
			
			@Override
			public Boolean isDownlad(String UID) {
				if(StringUtil.isEmpty(UID)) return false;
				return UID.equals(emailId);
			}
			
			@Override
			public void handle(Part part, com.hotent.core.mail.model.Mail mail) {
				try {
					saveAttach(part, mail, mailAddress);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},emailId);
		if(BeanUtils.isEmpty(list)) throw new Exception("找不到该邮件，可能邮件已被删除！");
		String mailId = outMail.getId();
		String attachFileName = entity.getFileName();
		String resultPath = "";
		com.hotent.core.mail.model.Mail mail = list.get(0);
		List<MailAttachment> attachments = mail.getMailAttachments();
		for(MailAttachment attachment:attachments){
			String fileName = attachment.getFileName();
			String filePath = attachment.getFilePath();
			if(fileName.equals(attachFileName)) resultPath = filePath;
			mailAttachmentManager.updateFilePath(fileName, mailId, filePath);
		}
		return resultPath;
	}
	
	
    public String getNameByEmail(String email){
    	String linkName = "陌生人";
    	List<User> users = userDao.getByUserEmail(email);
    	if(BeanUtils.isNotEmpty(users)){
    		linkName = users.get(0).getFullname();
    	}
    	return linkName; 
    }

}

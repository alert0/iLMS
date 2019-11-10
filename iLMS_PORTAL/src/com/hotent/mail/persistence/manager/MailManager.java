package com.hotent.mail.persistence.manager;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

import com.hotent.base.manager.api.Manager;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.mail.model.MailSetting;
import com.hotent.base.core.mail.model.Mail;
import com.hotent.base.core.mail.model.MailAttachment;

/**
 * 
 * <pre> 
 * 描述：外部邮件 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 10:49:46
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailManager extends Manager<String, Mail>{
	
	/**
	 * 保存邮件至垃圾箱
	 * @param request
	 * @param MailSetting
	 * @return
	 */
	public void addDump(String[] lAryId);
	
	/**
	 * 浏览邮件
	 * @param Mail
	 * @param MailSetting
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
	public void emailRead(Mail Mail)throws NoSuchProviderException, MessagingException;
	
	
	/**
	 * 根据setId获取邮件的唯一ID列表。
	 * @param setId
	 * @return
	 */
	public List<String> getUIDBySetId(String setId);
	
	/**
	 * 根据邮箱设定获取邮件列表。
	 * @param MailSetting
	 * @param uidList
	 * @return
	 * @throws Exception
	 */
	public List<com.hotent.core.mail.model.Mail> getMailListBySetting(final MailSetting mailSetting,List<String> uidList) throws Exception;
	
	
	
	
	/**
	 * 同步远程邮件，进行选择性下载
	 * @param MailSetting
	 * @throws Exception
	 */
	public void saveMail(List<com.hotent.core.mail.model.Mail>  list,String setId) throws Exception;
	
	/**
     * 邮箱树形列表的json数据
     * @param request
     * @param list
     * @param listTemp
     * @param listEnd
     * @throws Exception 
     */
	public List<MailSetting> getMailTreeData(String userId) throws Exception;
	
	/**
	 * 获取邮箱的分类邮件，如收件箱，发件箱，草稿箱
	 * @param queryFilter
	 * @return
	 */
	public List<Mail> getFolderList(QueryFilter queryFilter);
	
	/**
	 * 得到用户默认邮箱中的邮件列表
	 * @param queryFilter
	 * @return
	 */
	public List<com.hotent.base.core.mail.model.Mail> getDefaultMailList(QueryFilter queryFilter);
	
	/**
	 * 发送邮件,保存邮件信息至本地,添加/更新最近联系人
	 * @param Mail
	 * @param MailFiles
	 * @param fileIds
	 * @throws Exception
	 */
	public String sendMail(Mail Mail,String userId,String mailId,int isReply,String context,String basePath) throws Exception;
	
	/**
	 * 得到用于回复页面显示信息
	 * @param mailId
	 * @return
	 */
	public Mail getMailReply(String mailId);
	
	/**
	 * 发送系统错误报告至公司邮箱
	 * @param content
	 * @param recieveAdress
	 * @param mail
	 * @throws Exception
	 */
	public void sendError(String errorMsg, String recieveAdress, MailSetting mailSetting)throws Exception;

	public void delBySetId(String setId);

	/**
	 * 返回附件下载地址
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public String mailAttachementFilePath(MailAttachment entity) throws Exception;
	
	
    public String getNameByEmail(String email);
    /**
     * 删除邮件（如果有附件同时删除附件）
     */
    public void removeByIds(String[] ids);
}

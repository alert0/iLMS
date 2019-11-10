package com.hotent.mail.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.base.core.mail.model.MailAttachment;

/**
 * 
 * <pre> 
 * 描述：外部邮件附件表 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:18:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailAttachmentManager extends Manager<String, MailAttachment>{
	
	
	public List<MailAttachment> getByMailId(String mailId);

	/**
	 * 更新附件文件路径
	 * @param fileName
	 * @param mailId
	 * @param filePath
	 */
	public void updateFilePath(String fileName, String mailId, String filePath);

	/**
	 * 根据OutMail实体的fileIds，构建OutMailAttachment列表
	 * @param fileIds
	 * @return
	 */
	public List<MailAttachment> getByOutMailFileIds(String fileIds);
	/**
	 * 根据邮件ID删除附件
	 * @param mailId
	 */
	public void delByMailId(String mailId);
	
}

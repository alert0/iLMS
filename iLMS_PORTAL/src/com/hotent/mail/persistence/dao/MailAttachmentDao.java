package com.hotent.mail.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.base.core.mail.model.MailAttachment;

/**
 * 
 * <pre> 
 * 描述：外部邮件附件表 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:18:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MailAttachmentDao extends Dao<String, MailAttachment> {
	public List<MailAttachment> getByMailId(String mailId);

	public void updateFilePath(String fileName, String mailId, String filePath);
	
	/**
	 * 根据邮件ID删除附件
	 * @param mailId
	 */
	public void delByMailId(String mailId);
}

package com.hotent.mail.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.mail.persistence.dao.MailAttachmentDao;
import com.hotent.base.core.mail.model.MailAttachment;

/**
 * 
 * <pre> 
 * 描述：外部邮件附件表 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:18:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MailAttachmentDaoImpl extends MyBatisDaoImpl<String, MailAttachment> implements MailAttachmentDao{

    @Override
    public String getNamespace() {
        return MailAttachment.class.getName();
    }

	@Override
	public List<MailAttachment> getByMailId(String mailId) {
		return this.getByKey("getByMailId", mailId);
	}

	@Override
	public void updateFilePath(String fileName, String mailId, String filePath) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileName", fileName);
		params.put("mailId", mailId);
		params.put("filePath", filePath);
		updateByKey("updateFilePath", params);
	}

	@Override
	public void delByMailId(String mailId) {
		this.deleteByKey("delByEmailid",mailId);
	}
	
}


package com.hotent.mail.persistence.manager.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.mail.model.MailAttachment;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.mail.persistence.dao.MailAttachmentDao;
import com.hotent.mail.persistence.manager.MailAttachmentManager;
import com.hotent.mail.persistence.util.AppFileUtil;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <pre> 
 * 描述：外部邮件附件表 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:18:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("mailAttachmentManager")
public class MailAttachmentManagerImpl extends AbstractManagerImpl<String, MailAttachment> implements MailAttachmentManager{
	@Resource
	MailAttachmentDao mailAttachmentDao;
	@Resource
	FileManager fileManager;
	@Override
	protected Dao<String, MailAttachment> getDao() {
		return mailAttachmentDao;
	}
	@Override
	public List<MailAttachment> getByMailId(String mailId) {
		return mailAttachmentDao.getByMailId(mailId);
	}
	@Override
	public void updateFilePath(String fileName, String mailId, String filePath) {
		mailAttachmentDao.updateFilePath(fileName, mailId, filePath);
	}
	@Override
	public List<MailAttachment> getByOutMailFileIds(String fileIds) {
		List<MailAttachment> result = new ArrayList<MailAttachment>();
		if(StringUtil.isEmpty(fileIds)) return result;
		JSONArray jsonArray = JSONArray.fromObject(fileIds);
		for(Object obj:jsonArray){
			JSONObject json = (JSONObject)obj;
			String id = json.getString("id");
			DefaultFile file= fileManager.get(id);
			String filePath = AppFileUtil.getBasePath()+File.separator+file.getFilePath();
			MailAttachment attachment = new MailAttachment();
			attachment.setId(id);
			attachment.setFileName(json.getString("name"));
			attachment.setFilePath(filePath);
			result.add(attachment);
		}
		return result;
	}
	@Override
	public void delByMailId(String mailId) {
		mailAttachmentDao.delByMailId(mailId);
	}
}

package com.hotent.sys.persistence.manager.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.XmlUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
 
import com.hotent.org.api.model.IGroup;
import com.hotent.sys.persistence.dao.SysMsgTemplateDao;
import com.hotent.sys.persistence.manager.SysMsgTemplateManager;
import com.hotent.sys.persistence.model.SysMsgTemplate;
import com.hotent.sys.persistence.model.SysMsgTemplateXml;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：消息模版 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-03-10 09:20:11
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysMsgTemplateManager")
public class SysMsgTemplateManagerImpl extends AbstractManagerImpl<String, SysMsgTemplate> implements SysMsgTemplateManager{
	@Resource
	SysMsgTemplateDao sysMsgTemplateDao;
	@Override
	protected Dao<String, SysMsgTemplate> getDao() {
		return sysMsgTemplateDao;
	}
	

	@Override
	public void setDefault(String id) {
		SysMsgTemplate sysMsgTemplate = sysMsgTemplateDao.get(id);
		String typeKey = sysMsgTemplate.getTypeKey();
		sysMsgTemplateDao.setNotDefaultByType(typeKey);
		sysMsgTemplateDao.setDefault(id);
	}
	@Override
	public String exportXml(List<String> ids) {
		List<SysMsgTemplate> sysTemplates = sysMsgTemplateDao.getByIds(ids);
		String xml="";
		SysMsgTemplateXml sysMsgTemplateXml = new SysMsgTemplateXml();
		sysMsgTemplateXml.setSysMsgTemplate(sysTemplates);
		try {
			xml=JAXBUtil.marshall(sysMsgTemplateXml, SysMsgTemplateXml.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}


	@Override
	public void importXml(InputStream inputStream, boolean clearAll,
			boolean setDefault) throws Exception{
		Document doc = Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();
		// 验证格式是否正确
		XmlUtil.checkXmlFormat(root, "msgTemplates", "msgTemplate");
		String xmlStr = root.asXML();
		String currentUserId = ContextUtil.getCurrentUserId();
		IGroup group =ContextUtil.getCurrentGroup();
		String currentGroupId = "0";
		if(group!= null)currentGroupId = group.getGroupId();
		
		SysMsgTemplateXml sysMsgTemplateXml = (SysMsgTemplateXml) JAXBUtil
				.unmarshall(xmlStr, SysMsgTemplateXml.class);
		List<SysMsgTemplate> sysMsgTemplateList = sysMsgTemplateXml.getSysMsgTemplate();
		if (clearAll) {
			List<String> delTypeKey = new ArrayList<String>();
			for (SysMsgTemplate sysMsgTemplate : sysMsgTemplateList) {
				delTypeKey.add(sysMsgTemplate.getTypeKey());
			}
			sysMsgTemplateDao.delByTypeKey(delTypeKey);
		}
		
		for (SysMsgTemplate sysMsgTemplate : sysMsgTemplateList) {
			boolean exist =false;
			if (!clearAll) {
				exist= this.isExistByKeyAndTypeKey(sysMsgTemplate.getKey(),sysMsgTemplate.getTypeKey());
			}
			if (exist) continue;
			sysMsgTemplate.setId(UniqueIdUtil.getSuid());
			sysMsgTemplate.setCreateBy(currentUserId);
			sysMsgTemplate.setCreateOrgId(currentGroupId);
			sysMsgTemplate.setIsDefault(SysMsgTemplate.IS_DEFAULT_NO);
			sysMsgTemplateDao.create(sysMsgTemplate);
		}
		
		if (setDefault) {
			for (SysMsgTemplate sysMsgTemplate : sysMsgTemplateList) {
				sysMsgTemplateDao.setDefault(sysMsgTemplate.getId());
			}
		}
	}


	@Override
	public boolean isExistByKeyAndTypeKey(String key, String typeKey) {
		return sysMsgTemplateDao.isExistByKeyAndTypeKey(key,typeKey);
	}
	
}

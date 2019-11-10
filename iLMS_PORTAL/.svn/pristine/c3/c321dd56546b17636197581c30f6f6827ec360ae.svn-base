package com.hotent.sys.template;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.sys.api.template.TemplateService;
import com.hotent.sys.api.template.model.TemplateVo;
import com.hotent.sys.persistence.manager.MsgTemplateManager;
import com.hotent.sys.persistence.model.MsgTemplate;

/**
 * <pre> 
 * 构建组：x5-sys-core
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-27-上午11:12:23
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class TemplateServiceImpl implements TemplateService {

	private Log logger = LogFactory.getLog(TemplateServiceImpl.class);
	
	@Resource
	private FreemarkEngine freemarkEngine;
	
	@Resource
	private MsgTemplateManager msgTemplateManager;


	public TemplateVo getTemplate(String templateKey) {
		MsgTemplate msgTemplate = msgTemplateManager.getByKey(templateKey);
		return msgTemplate;
	}

	public TemplateVo getDefaultTemplate(String typeKey) {
		MsgTemplate msgTemplate = msgTemplateManager.getDefault(typeKey);
		if(msgTemplate==null)
			throw new RuntimeException("There is not a default msgTemplate in table.");
		return msgTemplate;
	}

	

	public String parseSubject(TemplateVo templateVo,Map<String,Object> vars) {
		String subject = "";
		try{
			subject = freemarkEngine.parseByStringTemplate(templateVo.getSubjectTemplate(),vars);
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return subject;
	}

	public String parsePlainContent(TemplateVo templateVo,Map<String,Object> vars) {
		String content = "";
		try{
			content = freemarkEngine.parseByStringTemplate(templateVo.getPlainTemplate(),vars);
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return content;
	}

	public String parseHtmlContent(TemplateVo templateVo,Map<String,Object> vars) {
		String content = "";
		try{
			content = freemarkEngine.parseByStringTemplate(templateVo.getHtmlTemplate(),vars);
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return content;
	}
		

}

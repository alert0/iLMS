package com.hotent.sys.api.template;

import java.util.Map;

import com.hotent.sys.api.template.model.TemplateVo;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-sys-api
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-23-下午3:13:05
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface TemplateService {
	
	
	public TemplateVo getTemplate(String templateKey);
	
	public TemplateVo getDefaultTemplate(String typeKey);
	
	public String parseSubject(TemplateVo templateVo,Map<String,Object> vars);
	
	public String parsePlainContent(TemplateVo templateVo,Map<String,Object> vars);
	
	public String parseHtmlContent(TemplateVo templateVo,Map<String,Object> vars);
}

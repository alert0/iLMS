package com.hotent.sys.persistence.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "msgTemplates")
@XmlAccessorType(XmlAccessType.FIELD)
public class SysMsgTemplateXml {
	/**
	 * 自定义表
	 */
	@XmlElement(name = "msgTemplate", type = SysMsgTemplate.class)
	private List<SysMsgTemplate> sysMsgTemplate;

	public List<SysMsgTemplate> getSysMsgTemplate() {
		return sysMsgTemplate;
	}

	public void setSysMsgTemplate(List<SysMsgTemplate> sysMsgTemplate) {
		this.sysMsgTemplate = sysMsgTemplate;
	}

	
	
	

}

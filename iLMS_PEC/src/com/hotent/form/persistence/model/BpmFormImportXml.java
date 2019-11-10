package com.hotent.form.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "bpmFormImport")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormImportXml {
	
	@XmlElement(name = "formXml", type = BpmFormXml.class)
	List<BpmFormXml> formXmlList = new ArrayList<BpmFormXml>();
	
	public List<BpmFormXml> getFormXmlList() {
		return formXmlList;
	}



	public void setFormXmlList(List<BpmFormXml> formXmlList) {
		this.formXmlList = formXmlList;
	}



	public void addFormXml(BpmFormXml formXml){
		formXmlList.add(formXml);
	}
	
	
}

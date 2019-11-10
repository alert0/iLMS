package com.hotent.form.persistence.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 此类用于表单的导入导出。
 *
 */
@XmlRootElement(name = "bpmForms")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormXml {
	

	@XmlElement(name = "bpmForm", type = BpmForm.class)
	private BpmForm bpmForm;
	
	@XmlElement(name = "bpmFormDef", type = BpmFormDef.class)
	private BpmFormDef bpmFormDef;
	
	@XmlElement(name="boCodes")
	private List<String> boCodes;
	

	public List<String> getBoCodes() {
		return boCodes;
	}


	public void setBoCodes(List<String> boCodes){
		this.boCodes = boCodes;
	}


	public BpmForm getBpmForm() {
		return bpmForm;
	}


	public void setBpmForm(BpmForm bpmForm){
		this.bpmForm = bpmForm;
	}


	public BpmFormDef getBpmFormDef(){
		return bpmFormDef;
	}


	public void setBpmFormDef(BpmFormDef bpmFormDef){
		this.bpmFormDef = bpmFormDef;
	}
	
	
}

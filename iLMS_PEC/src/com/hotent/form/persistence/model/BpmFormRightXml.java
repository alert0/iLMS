package com.hotent.form.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 此类用于bo的导入导出。
 * @author ray
 *
 */
@XmlRootElement(name = "bpmFormRights")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormRightXml {
	
	
	@XmlElement(name = "BpmFormRight", type = BpmFormRight.class)
	private List<BpmFormRight> rightList=new ArrayList<BpmFormRight>();
	
	public List<BpmFormRight> getRightList() {
		return rightList;
	}

	public void setRightList(List<BpmFormRight> rightList) {
		this.rightList = rightList;
	}


	/**
	 * @param formRights
	 */
	public void addBpmFormRight(List<BpmFormRight> formRights){
		this.rightList.addAll(formRights);
	}
	
	
	
	
}

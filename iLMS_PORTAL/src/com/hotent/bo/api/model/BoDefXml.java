package com.hotent.bo.api.model;

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
@XmlRootElement(name = "bodefs")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoDefXml {
	
	
	@XmlElement(name = "bodef", type = BaseBoDef.class)
	private List<BaseBoDef> defList=new ArrayList<BaseBoDef>();

	
	public List<BaseBoDef> getDefList() {
		return defList;
	}

	public void setDefList(List<BaseBoDef> defList) {
		this.defList = defList;
	}
	
	/**
	 * 添加bo定义列表。
	 * @param def
	 */
	public void addBodef(BaseBoDef def){
		this.defList.add(def);
	}
	
	
	
	
}

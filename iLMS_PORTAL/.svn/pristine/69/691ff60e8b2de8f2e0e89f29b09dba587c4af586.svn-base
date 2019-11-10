package com.hotent.bpmx.persistence.model;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.bpmx.api.constant.DesignerType;

/**
 * 对象功能:@名称：BPM_DEF_DATA 【流程定义大数据值】 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2013-12-13 16:17:48
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "BpmDefData")
public class BpmDefData extends AbstractModel<String> implements Cloneable{
	protected String  id; /*流程定义ID*/
	@XmlElement(name="defXml")
	protected String  defXml; /*流程定义XML*/
	@XmlElement(name="bpmnXml")
	protected String  bpmnXml; /*流程定义BPMN格式XML*/
	@XmlElement(name="defJson")
	protected String  defJson; /*流程定义的json*/
	
	public BpmDefData(){
		defXml = "";
		bpmnXml = "";
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	
	
	
	
	public void setDefXml(String defXml) 
	{
		this.defXml = defXml;
	}
	/**
	 * 返回 流程定义XML
	 * @return
	 */
	public String getDefXml() 
	{
		return this.defXml;
	}
	public void setBpmnXml(String bpmnXml) 
	{
		this.bpmnXml = bpmnXml;
	}
	/**
	 * 返回 流程定义BPMN格式XML
	 * @return
	 */
	public String getBpmnXml() 
	{
		return this.bpmnXml;
	}
	
	
	public String getDefJson() {
		return defJson;
	}
	public void setDefJson(String defJson) {
		this.defJson = defJson;
	}
	
	@Override
	public String toString() {
		return "BpmDefData [id=" + id + ", defXml=" + defXml + ", bpmnXml="
				+ bpmnXml + ", defJson=" + defJson + "]";
	}
	public Object clone() {
		BpmDefData obj=null;
		try{
			obj=(BpmDefData)super.clone();			
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}			
}
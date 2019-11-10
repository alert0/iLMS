package com.hotent.portal.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：sys_index_layout 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:14
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysIndexLayout extends AbstractModel<Long>{
	
	/**
	* ID
	*/
	protected Long id; 
	
	/**
	* NAME
	*/
	protected String name; 
	
	/**
	* MEMO
	*/
	protected String memo; 
	
	/**
	* TEMPLATE_HTML
	*/
	protected String templateHtml; 
	
	/**
	* SN
	*/
	protected Integer sn; 
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getTemplateHtml() {
		return templateHtml;
	}


	public void setTemplateHtml(String templateHtml) {
		this.templateHtml = templateHtml;
	}


	public Integer getSn() {
		return sn;
	}


	public void setSn(Integer sn) {
		this.sn = sn;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("memo", this.memo) 
		.append("templateHtml", this.templateHtml) 
		.append("sn", this.sn) 
		.toString();
	}

}
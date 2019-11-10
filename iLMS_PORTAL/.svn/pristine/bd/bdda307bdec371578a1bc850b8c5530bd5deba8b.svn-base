package com.hotent.base.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：关联资源 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class RelResource extends AbstractModel<String>{
	
	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 资源ID
	*/
	protected String resId; 
	
	/**
	* 名称
	*/
	protected String name; 
	
	/**
	* 资源地址
	*/
	protected String resUrl; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setResId(String resId) {
		this.resId = resId;
	}
	
	/**
	 * 返回 资源ID
	 * @return
	 */
	public String getResId() {
		return this.resId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	
	/**
	 * 返回 资源地址
	 * @return
	 */
	public String getResUrl() {
		return this.resUrl;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((resId == null) ? 0 : resId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelResource other = (RelResource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resId == null) {
			if (other.resId != null)
				return false;
		} else if (!resId.equals(other.resId))
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("resId", this.resId) 
		.append("name", this.name) 
		.append("resUrl", this.resUrl) 
		.toString();
	}
}
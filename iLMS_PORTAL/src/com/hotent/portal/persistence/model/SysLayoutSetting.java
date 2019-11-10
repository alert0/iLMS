package com.hotent.portal.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：布局设置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-07 16:18:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysLayoutSetting extends AbstractModel<String>{
	
	/**
	* ID_
	*/
	protected String id; 
	
	/**
	* 布局ID
	*/
	protected String layoutId; 
	
	/**
	* LOGO
	*/
	protected String logo; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}
	
	/**
	 * 返回 布局ID
	 * @return
	 */
	public String getLayoutId() {
		return this.layoutId;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	/**
	 * 返回 LOGO
	 * @return
	 */
	public String getLogo() {
		return this.logo;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("layoutId", this.layoutId) 
		.append("logo", this.logo) 
		.toString();
	}
}
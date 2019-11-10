package com.hotent.portal.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：布局工具设置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-07 09:16:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysLayoutTools extends AbstractModel<String>{
	
	public static final String TOOLS = "快捷工具";
	public static final String COLUMN = "统计栏目";
	/**
	* ID_
	*/
	protected String id; 
	
	/**
	* 布局ID
	*/
	protected String layoutId; 
	
	/**
	* 工具ID列表
	*/
	protected String toolsIds; 
	
	/**
	* 工具类型
	*/
	protected String toolsType; 
	
	
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
	
	public void setToolsIds(String toolsIds) {
		this.toolsIds = toolsIds;
	}
	
	/**
	 * 返回 工具ID列表
	 * @return
	 */
	public String getToolsIds() {
		return this.toolsIds;
	}
	
	public void setToolsType(String toolsType) {
		this.toolsType = toolsType;
	}
	
	/**
	 * 返回 工具类型
	 * @return
	 */
	public String getToolsType() {
		return this.toolsType;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("layoutId", this.layoutId) 
		.append("toolsIds", this.toolsIds) 
		.append("toolsType", this.toolsType) 
		.toString();
	}
}
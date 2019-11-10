package com.hotent.bo.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 存储bo实例数据。
 * <pre> 
 * 描述：业务对象实例 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-11 09:52:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BoInst extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9069698153502001622L;

	/**
	* 业务实例ID
	*/
	protected String id; 
	
	/**
	* 对象定义ID
	*/
	protected String defId; 
	
	/**
	* 实例数据
	*/
	protected String instData; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date createTime; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 业务实例ID
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setDefId(String defId) {
		this.defId = defId;
	}
	
	/**
	 * 返回 对象定义ID
	 * @return
	 */
	public String getDefId() {
		return this.defId;
	}
	
	public void setInstData(String instData) {
		this.instData = instData;
	}
	
	/**
	 * 返回 实例数据
	 * @return
	 */
	public String getInstData() {
		return this.instData;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("defId", this.defId) 
		.append("instData", this.instData) 
		.append("createTime", this.createTime) 
		.toString();
	}
}
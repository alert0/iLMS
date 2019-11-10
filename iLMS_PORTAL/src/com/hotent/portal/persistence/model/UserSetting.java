package com.hotent.portal.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：用户配置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-01 17:18:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class UserSetting extends AbstractModel<Long>{
	
	/**
	* ID_
	*/
	protected Long id; 
	
	/**
	* 用户ID
	*/
	protected String userId; 
	
	/**
	* 皮肤名称
	*/
	protected String skinName; 
	
	/**
	* 首页名称
	*/
	protected String indexName; 
	
	/**
	* 布局ID
	*/
	protected String layoutId; 
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 返回 ID_
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 用户ID
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}
	
	/**
	 * 返回 皮肤名称
	 * @return
	 */
	public String getSkinName() {
		return this.skinName;
	}
	
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	
	/**
	 * 返回 首页名称
	 * @return
	 */
	public String getIndexName() {
		return this.indexName;
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
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("userId", this.userId) 
		.append("skinName", this.skinName) 
		.append("indexName", this.indexName) 
		.append("layoutId", this.layoutId) 
		.toString();
	}
}
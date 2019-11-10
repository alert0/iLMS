package com.hotent.oa.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：DemoUser 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-02 11:05:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class DemoUser extends AbstractModel<String>{
	
	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 用户名
	*/
	protected String userName; 
	
	/**
	* 地址
	*/
	protected String address; 
	
	
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
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 返回 用户名
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 返回 地址
	 * @return
	 */
	public String getAddress() {
		return this.address;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("userName", this.userName) 
		.append("address", this.address) 
		.toString();
	}
}
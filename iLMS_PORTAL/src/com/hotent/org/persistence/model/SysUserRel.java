package com.hotent.org.persistence.model;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.api.model.Tree;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：用户关系 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-06-12 09:21:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysUserRel extends AbstractModel<String>  implements Tree{
	
	public static final String FA_USERS = " fa fa-users ";
	public static final String FA_USER = " fa fa-user ";
	
	private static final long serialVersionUID = 1L;

	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 用户id
	*/
	protected String userId; 
	
	/**
	 * 用户级别( 当一个汇报线上的用户有多个上级时， 此时可通过级别区分 )
	 */
	protected String level;
	
	/**
	* 父id
	*/
	protected String parentId; 
	
	/**
	* 用户名
	*/
	protected String userName; 
	
	/**
	 * 账号
	 */
	protected String account;
	
	/**
	* 分类
	*/
	protected String typeId; 
	
	protected String icon;
	
	protected List<SysUserRel> children = new ArrayList<SysUserRel>();
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 用户id 主要用于构建树
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 父id
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 返回 PARENT_USER_ID_
	 * @return
	 */
	public String getParentId() {
		return this.parentId;
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
	
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	/**
	 * 返回 分类
	 * @return
	 */
	public String getTypeId() {
		return this.typeId;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("userId", this.userId) 
		.append("parentId", this.parentId) 
		.append("userName", this.userName) 
		.append("typeId", this.typeId) 
		.toString();
	}

	@Override
	public String getText() {
		return this.userName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getChildren() {
		return children;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setChildren(List children) {
		this.children = children;
	}
	
	public String getIcon(){
		if(this.getParentId()=="-1"){
			return SysUserRel.FA_USERS;
		}
		return SysUserRel.FA_USER;
		
	}
	
	public String getAccount(){
		return this.account;
	}
}
package com.hotent.sys.persistence.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * 描述：OFFICE模版印章授权 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-11-07 16:28:46
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysSignRight extends AbstractModel<String> {
	protected String id; /* 主键 */
	protected String signId; /* 引用记录主键 */
	protected String rightId; /* 用户主键 */
	protected String rightName; /* 用户名称 */
	protected String type; /* 类型 */
	
	/**
	 * 权限类型
	 */
	public class Type{
		/**
		 * 没有
		 */
		public static final String NONE="none";
		/**
		 * 用户
		 */
		public static final String USER="user";
		/**
		 * 角色
		 */
		public static final String ROLE="role";
		/**
		 * 组织
		 */
		public static final String ORG="org";
		/**
		 * 所有
		 */
		public static final String ALL="all";
	}
	
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	/**
	 * 返回 引用记录主键
	 * 
	 * @return
	 */
	public String getSignId() {
		return this.signId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	/**
	 * 返回 用户主键
	 * 
	 * @return
	 */
	public String getRightId() {
		return this.rightId;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	/**
	 * 返回 用户名称
	 * 
	 * @return
	 */
	public String getRightName() {
		return this.rightName;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回 类型
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("signId", this.signId).append("rightId", this.rightId).append("rightName", this.rightName).append("type", this.type).toString();
	}
}
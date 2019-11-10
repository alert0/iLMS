package com.hotent.sys.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * 描述：日志模块管理 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 16:57:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class LogModule extends AbstractModel<String> {
	public static final Short DISABLED  = 0; // 禁用
	public static final Short ENABLED = 1; // 启用

	protected String id; /* 主键 */
	protected String name; /* 模块名称 */
	protected String alias; /* 模块别名 */
	protected Short enabled; /* 是否禁用 */

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

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 模块名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 返回 模块别名
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	/**
	 * 返回 是否禁用
	 * 
	 * @return
	 */
	public Short getEnabled() {
		return this.enabled;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("alias", this.alias)
				.append("enabled", this.ENABLED).toString();
	}
}
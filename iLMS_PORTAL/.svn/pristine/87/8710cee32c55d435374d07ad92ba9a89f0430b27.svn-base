package com.hotent.base.core.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.hotent.base.api.BaseModel;

/**
 * <pre>
 * 描述：实体的基础类
 * 构建组：x5-base-core
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-12-20-下午8:55:18
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractModel<T> implements BaseModel {
	// 创建人ID
	protected String createBy;
	// 创建人
	protected String creator;
	// 更新人
	protected String updateBy;
	// 创建时间
	@XmlTransient
	protected Date createTime;
	// 更新时间
	protected Date updateTime;
	// 创建人组织ID
	protected String createOrgId;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	/**
	 * 设置主键ID
	 * 
	 * @param id
	 */
	public abstract void setId(T id);

	/**
	 * 获取主键ID
	 * 
	 * @return
	 */
	public abstract T getId();

}

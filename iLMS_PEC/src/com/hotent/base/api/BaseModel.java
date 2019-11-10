package com.hotent.base.api;

import java.io.Serializable;
import java.util.Date;
/**
 * 实体基础接口。
 * @author csx
 *
 */
public interface BaseModel extends Serializable {
	/**
	 * 返回创建人ID
	 * @return
	 */
	String getCreateBy();
	/**
	 * 设置设置人ID
	 * @param createBy
	 */
	void setCreateBy(String createBy);
	/**
	 * 返回创建时间
	 * @return
	 */
	Date getCreateTime();
	/**
	 * 设置创建时间
	 * @param createtime
	 */
	void setCreateTime(Date createtime);
	/**
	 * 返回更新时间
	 * @return
	 */
	Date getUpdateTime();
	/**
	 * 设置更新时间
	 * @param updatetime
	 */
	void setUpdateTime(Date updatetime);
	/**
	 * 返回更新人ID
	 * @return
	 */
	String getUpdateBy();
	/**
	 * 设置更新人ID
	 * @param updateBy
	 */
	void setUpdateBy(String updateBy);
	/**
	 * 获取创建者组织ID
	 * @return
	 */
	String getCreateOrgId();
	/**
	 * 设置创建者组织ID
	 * @param createOrgId
	 * @return
	 */
	void setCreateOrgId(String createOrgId);
	
	
}

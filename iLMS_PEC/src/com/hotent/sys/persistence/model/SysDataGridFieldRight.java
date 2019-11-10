package com.hotent.sys.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：sys_datagrid_field_right 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-27 17:37:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysDataGridFieldRight extends AbstractModel<String>{
	protected String rightId; /*right_id_*/
	protected String gridId; /*grid_id_*/
	protected String fieldId; /*字段表ID*/
	protected String permission; /*授权内容*/
	protected String rightRemark; /*right_remark_*/
	@Override
	public void setId(String rightId) {
		this.rightId = rightId.toString();
	}
	@Override
	public String getId() {
		return rightId.toString();
	}	
	public void setRightId(String rightId) 
	{
		this.rightId = rightId;
	}
	/**
	 * 返回 right_id_
	 * @return
	 */
	public String getRightId() 
	{
		return this.rightId;
	}
	public void setGridId(String gridId) 
	{
		this.gridId = gridId;
	}
	/**
	 * 返回 grid_id_
	 * @return
	 */
	public String getGridId() 
	{
		return this.gridId;
	}
	public void setFieldId(String fieldId) 
	{
		this.fieldId = fieldId;
	}
	/**
	 * 返回 字段表ID
	 * @return
	 */
	public String getFieldId() 
	{
		return this.fieldId;
	}
	public void setPermission(String permission) 
	{
		this.permission = permission;
	}
	/**
	 * 返回 授权内容
	 * @return
	 */
	public String getPermission() 
	{
		return this.permission;
	}
	public void setRightRemark(String rightRemark) 
	{
		this.rightRemark = rightRemark;
	}
	/**
	 * 返回 right_remark_
	 * @return
	 */
	public String getRightRemark() 
	{
		return this.rightRemark;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("rightId", this.rightId) 
		.append("gridId", this.gridId) 
		.append("fieldId", this.fieldId) 
		.append("permission", this.permission) 
		.append("rightRemark", this.rightRemark) 
		.toString();
	}
}
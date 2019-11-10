package com.hotent.sys.persistence.model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.AppUtil;

 
 /**
 * 
 * <pre> 
 * 描述：sys_grid_config 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-04 09:09:04
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysDataGridFieldInfo extends SysDataGridField{
	protected String gridName; /*datagrid英文名，唯一值*/
	protected String dataSourceType; /*数据源类别：url(默认),mapkey,table,tableview*/
	protected String dataSource; /*数据源对象，url时为空*/
	protected String physicsTable; /*主物理表名*/
	protected String remark; /*备注*/
	protected boolean isFieldPower; /* 是否启动字段权限 */
	protected boolean isEnableCondition; /* 是否启是否开启条件过滤字段权限 */
	protected String condition; /* 过滤条件权限 */
	protected boolean isDefaultLoadData; /* 是否默认加载数据 */
	
	public void setIsEnableCondition(boolean isEnableCondition){this.isEnableCondition = isEnableCondition;}
	public boolean getIsEnableCondition(){return this.isEnableCondition;}
	
	public void setCondition(String condition){this.condition = condition;}
	public String getCondition(){return this.condition;}
	
	public void setIsDefaultLoadData(boolean isDefaultLoadData){this.isDefaultLoadData = isDefaultLoadData;}
	public boolean getIsDefaultLoadData(){return this.isDefaultLoadData;}
	
	public void setIsFieldPower(boolean isFieldPower)
	{
		this.isFieldPower = isFieldPower;
	}

	public void getIsFieldPower(boolean isFieldPower)
	{
		this.isFieldPower = isFieldPower;
	}
	public void setGridName(String gridName) 
	{
		this.gridName = gridName;
	}
	/**
	 * 返回 datagrid英文名，唯一值
	 * @return
	 */
	public String getGridName() 
	{
		return this.gridName;
	}
	public void setDataSourceType(String dataSourceType) 
	{
		this.dataSourceType = dataSourceType;
	}
	/**
	 * 返回 数据源类别：url(默认),mapkey,table,tableview
	 * @return
	 */
	public String getDataSourceType() 
	{
		return this.dataSourceType;
	}
	public void setDataSource(String dataSource) 
	{
		this.dataSource = dataSource;
	}
	/**
	 * 返回 数据源对象，url时为空
	 * @return
	 */
	public String getDataSource() 
	{
		return this.dataSource;
	}
	public void setPhysicsTable(String physicsTable) 
	{
		this.physicsTable = physicsTable;
	}
	/**
	 * 返回 主物理表名
	 * @return
	 */
	public String getPhysicsTable() 
	{
		return this.physicsTable;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}

	 
	
	
	
	
	
	protected String rightId; /*right_id_*/
	protected String fieldId; /*字段表ID*/
	protected String permission; /*授权内容*/
	protected String rightRemark; /*right_remark_*/
 
 	
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
}
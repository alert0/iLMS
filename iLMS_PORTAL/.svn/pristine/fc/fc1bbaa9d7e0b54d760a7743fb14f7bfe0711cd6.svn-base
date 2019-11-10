package com.hotent.sys.persistence.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * 描述：sys_datagrid 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:14:19
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysDataGrid extends AbstractModel<String>
{
	protected String gridid; /* 表id */
	protected String gridName; /* datagrid英文名，唯一值 */
	protected String dataSourceType; /* 数据源类别：url(默认),mapkey,table,tableview */
	protected String dataSource; /* 数据源对象，url时为空 */
	protected String physicsTable; /* 主物理表名 */
	protected String remark; /* 备注 */
	protected boolean isFieldPower=false; /* 是否启动字段权限 */
	
	protected boolean isEnableCondition=false; /* 是否启是否开启条件过滤字段权限 */
	protected String condition; /* 过滤条件权限 */
	protected boolean isDefaultLoadData=false;/* 是否默认加载数据 */
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	protected java.util.Date createdtime = new Date(); /* created_time_ */

	
	public void setIsEnableCondition(boolean isEnableCondition){this.isEnableCondition = isEnableCondition;}
	public boolean getIsEnableCondition(){return this.isEnableCondition;}
	
	public void setCondition(String condition){this.condition = condition;}
	public String getCondition(){return this.condition;}
	
	public void setIsDefaultLoadData(boolean isDefaultLoadData){this.isDefaultLoadData = isDefaultLoadData;}
	public boolean getIsDefaultLoadData()
	{
		return this.isDefaultLoadData;
	}
	
	@Override
	public void setId(String gridid)
	{
		this.gridid = gridid.toString();
	}

	@Override
	public String getId(){return gridid.toString();}
	public void setIsFieldPower(boolean isFieldPower)
	{
		this.isFieldPower = isFieldPower;
	}
	
	public boolean getIsFieldPower()
	{
		return this.isFieldPower;
	}

	public void setGridid(String gridid)
	{
		this.gridid = gridid;
	}

	/**
	 * 返回 表id
	 * 
	 * @return
	 */
	public String getGridid()
	{
		return this.gridid;
	}

	public void setGridName(String gridName)
	{
		this.gridName = gridName;
	}

	/**
	 * 返回 datagrid英文名，唯一值
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @return
	 */
	public String getRemark()
	{
		return this.remark;
	}

	public void setCreatedtime(java.util.Date createdtime)
	{
		this.createdtime = createdtime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreatedtime()
	{
		return this.createdtime;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("gridid", this.gridid).append("gridName", this.gridName).append("dataSourceType", this.dataSourceType).append("dataSource", this.dataSource).append("physicsTable", this.physicsTable).append("remark", this.remark).append("createdtime", this.createdtime).toString();
	}
}
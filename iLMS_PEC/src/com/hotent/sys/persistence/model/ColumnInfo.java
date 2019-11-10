package com.hotent.sys.persistence.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.AppUtil;

public class ColumnInfo
{
	//冻结列跨列分组列
	public List<SysDataGridField> FrozenGroupColumns=new ArrayList<SysDataGridField>();
	//冻结列
	public List<SysDataGridField>  FrozenColumns=new ArrayList<SysDataGridField>();

	//非冻结列跨列分组列
	public List<SysDataGridField>  GroupColumns=new ArrayList<SysDataGridField>();

	//非冻结列
	public List<SysDataGridField>  Columns=new ArrayList<SysDataGridField>();


}

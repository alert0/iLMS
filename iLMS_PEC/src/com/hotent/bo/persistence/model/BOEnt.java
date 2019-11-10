package com.hotent.bo.persistence.model;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.bo.api.model.BaseBoEnt;

/**
 * BO实体定义
 * 
 * <pre>
 * 描述：业务对象定义 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-01 16:41:22
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BOEnt extends BaseBoEnt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8222297414469618013L;

	
	/**
	 * 状态。enabled,forbidden
	 */
	protected String status ="enabled";
	
	
	/**
	 * 是否生成表
	 */
	protected Short isCreateTable=0;
	

	public BOEnt() {
	}

	public BOEnt(BaseBoEnt baseBoEnt) {
		this.setName(baseBoEnt.getName());
		this.setTableName(baseBoEnt.getTableName());
		this.setDesc(baseBoEnt.getDesc());
		this.setType(baseBoEnt.getType());
		this.setDsName(baseBoEnt.getDsName());
		this.setFk(baseBoEnt.getFk());
		this.setIsExternal(baseBoEnt.getIsExternal());
		this.setPk(baseBoEnt.getPk());
		this.setPkType(baseBoEnt.getPkType());
		this.setType(baseBoEnt.getType());
		this.setPackageId(baseBoEnt.getPackageId());
		this.setBoAttrList(baseBoEnt.getBoAttrList());
		this.setChildEntList(baseBoEnt.getChildEntList());
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 返回 状态。normal,forbidden
	 * 
	 * @return
	 */
	public String getStatus() {
		return this.status;
	}
	
	public void setIsCreateTable(Short isCreateTable) {
		this.isCreateTable = isCreateTable;
	}
	
	/**
	 * 是否创建表。
	 * @return
	 */
	public boolean isCreatedTable(){
		if(isCreateTable==null){
			return false;
		}
		return this.isCreateTable==1;
	}

	/**
	 * 返回 是否生成表
	 * 
	 * @return
	 */
	public Short getIsCreateTable() {
		return this.isCreateTable;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("status", this.status).append("createBy", this.createBy).append("createTime", this.createTime).append("isCreateTable", this.isCreateTable).append("dsName", this.dsName).append("tableName", this.tableName).append("isExternal", this.isExternal).append("pk", this.pk).append("fk", this.fk).append("pkType", this.pkType).toString();
	}
}
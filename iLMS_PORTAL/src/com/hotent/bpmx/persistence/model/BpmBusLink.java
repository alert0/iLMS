package com.hotent.bpmx.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.PropertyUtil;
import com.hotent.bpmx.persistence.dao.BpmBusLinkDao;

/**
 * 对象功能:业务数据关联 entity对象 开发公司:广州宏天软件有限公司 开发人员:zyg 创建时间:2014-06-05 16:55:41
 */
public class BpmBusLink extends AbstractModel<String>
{
	public static final String TABLE_UNCREATED = "TABLE_UNCREATED"; // 对应
																	// P_TABLE_UNCREATED
																	// ,未创建实体表的都放进去
	/**
	 * 主键 
	 */
	protected String id; 
	/**
	 *流程定义ID 
	 */
	protected String defId; 
	/**
	 *  流程实例ID 
	 */
	protected String procInstId; 
	/**
	 *业务键 
	 */
	protected Long businesskey; 
	/**
	 * 业务键字符型
	 */
	protected String businesskeyStr; 
	/**
	 * 业务系统编码
	 */
	protected String sysCode; 
	/**
	 * 表单标识
	 */
	protected String formIdentify; 
	/**
	 * 发起人 
	 */
	protected String startId; 
	/**
	 * 发起人 
	 */
	protected String startor; 
	/**
	 * 创建时间 
	 */
	protected java.util.Date createDate; 
	/**
	 * 发起组织ID 
	 */
	protected String startGroupId; 
	/**
	 * 发起组织 
	 */
	protected String startGroup; 
	/**
	 * 是否主表记录
	 */
	protected int isMain = 1;

	/**
	 * 暂时存放bodefcode数据。
	 */
	protected  String boDefCode = "";
	
	/**
	 * 保存模式 (boObject,database);
	 */
	protected  String saveMode = "";
	
	

	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public String getId()
	{
		return this.id;
	}

	public void setDefId(String defId)
	{
		this.defId = defId;
	}

	/**
	 * 返回 流程定义ID
	 * 
	 * @return
	 */
	public String getDefId()
	{
		return this.defId;
	}

	public void setProcInstId(String procInstId)
	{
		this.procInstId = procInstId;
	}

	/**
	 * 返回 流程实例ID
	 * 
	 * @return
	 */
	public String getProcInstId()
	{
		return this.procInstId;
	}

	public void setBusinesskey(Long businesskey)
	{
		this.businesskey = businesskey;
	}

	/**
	 * 返回 业务键
	 * 
	 * @return
	 */
	public Long getBusinesskey()
	{
		return this.businesskey;
	}

	public void setBusinesskeyStr(String businesskeyStr)
	{
		this.businesskeyStr = businesskeyStr;
	}

	/**
	 * 返回业务数据编码
	 * @return
	 */
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	/**
	 * 返回 业务键字符型
	 * 
	 * @return
	 */
	public String getBusinesskeyStr()
	{
		return this.businesskeyStr;
	}

	public void setFormIdentify(String formIdentify)
	{
		this.formIdentify = formIdentify;
	}

	/**
	 * 返回 表单标识
	 * 
	 * @return
	 */
	public String getFormIdentify()
	{
		return this.formIdentify;
	}

	public void setStartId(String startId)
	{
		this.startId = startId;
	}

	/**
	 * 返回 发起人
	 * 
	 * @return
	 */
	public String getStartId()
	{
		return this.startId;
	}

	public void setStartor(String startor)
	{
		this.startor = startor;
	}

	/**
	 * 返回 发起人
	 * 
	 * @return
	 */
	public String getStartor()
	{
		return this.startor;
	}

	public void setCreateDate(java.util.Date createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreateDate()
	{
		return this.createDate;
	}

	public void setStartGroupId(String startGroupId)
	{
		this.startGroupId = startGroupId;
	}

	/**
	 * 返回 发起组织ID
	 * 
	 * @return
	 */
	public String getStartGroupId()
	{
		return this.startGroupId;
	}

	public void setStartGroup(String startGroup)
	{
		this.startGroup = startGroup;
	}

	/**
	 * 返回 发起组织
	 * 
	 * @return
	 */
	public String getStartGroup()
	{
		return this.startGroup;
	}

	public int getIsMain()
	{
		return isMain;
	}

	public void setIsMain(int isMain)
	{
		this.isMain = isMain;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("id", this.id).append("defId", this.defId).append("procInstId", this.procInstId).append("businesskey", this.businesskey).append("businesskeyStr", this.businesskeyStr).append("formIdentify", this.formIdentify).append("startId", this.startId).append("startor", this.startor).append("createDate", this.createDate).append("startGroupId", this.startGroupId).append("startGroup", this.startGroup).toString();
	}

	private static Boolean supportPartition = null;

	/** 判断是否支持分区表 目前支持 oracle mysql5.5 以上版本 */
	public static boolean isSupportPartition()
	{
		String isSupportPartition = PropertyUtil.getProperty("supportPartition", "true");
		if (isSupportPartition.equals("false"))
			return false;
		if (supportPartition == null)
		{
			String dataType = PropertyUtil.getJdbcType();
			if (dataType.equals("oracle"))
			{
				supportPartition = true;
			} else if (dataType.equals("mysql"))
			{
				BpmBusLinkDao dao = AppUtil.getBean(BpmBusLinkDao.class);
				String mysqlVersion = dao.getMysqlVersion();
				double varsion = Double.parseDouble(mysqlVersion.substring(0, 3));
				if (varsion >= 5.5)
					supportPartition = true;
				else
					supportPartition = false;
			} else
			{
				supportPartition = false;
			}
		}
		return supportPartition;
	}

	public String getBoDefCode()
	{
		return boDefCode;
	}

	public void setBoDefCode(String boDefCode)
	{
		this.boDefCode = boDefCode;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
}
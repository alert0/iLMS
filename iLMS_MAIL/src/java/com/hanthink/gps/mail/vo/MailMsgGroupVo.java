package com.hanthink.gps.mail.vo;

import java.util.Date;

/**
 * 
 * 广汽乘用车邮件模块-邮件类型分组
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 03/31 0.01  anMin    新建
 * 
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-13	V1.1		zuosl		增加属性
 * 
 */
public class MailMsgGroupVo {
	
	/** 定时简报分组代码 */
	public final static String GROUP_CODE_DSJB = "DSJB";
	/** 异常订单报表分组代码 */
	public final static String GROUP_CODE_EXCE_ORDER = "EXCE_ORDER";
	/** 存储过程执行异常分组代码 */
	public final static String GROUP_CODE_PRO_ERROR = "PRO_ERROR";
	/** 解析ERP订单失败分组代码 */
	public final static String GROUP_CODE_PARSE_ORDER_FAIL = "PARSE_FAIL";
	
	/** 删除标识标识-已删除 */
	public final static String DEL_FLAG_YES = "1";
	/** 删除标识标识-未删除 */
	public final static String DEL_FLAG_NO = "0";
	
	
	private int id;
	
	private String groupCode;//分组代码
	
	private String groupName;//分组名称
	
	private String record;//备注 
	
	private String delFlag;//删除标识
	
	private Date entryDate;
	
	private String entryId;
	
	private Date modifyDate;
	
	private String modifyId;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	
	

}

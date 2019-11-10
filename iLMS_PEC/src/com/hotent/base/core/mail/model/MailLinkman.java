package com.hotent.base.core.mail.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：外部邮件最近联系 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-17 11:19:36
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class MailLinkman extends AbstractModel<String>{
	
	/**
	* 主键
	*/
	protected String LINKID; 
	
	/**
	* 用户ID
	*/
	protected String USERID; 
	
	/**
	* 邮件ID
	*/
	protected String MAILID; 
	
	/**
	* 送送时间
	*/
	protected java.util.Date SENDTIME; 
	
	/**
	* 联系人名称
	*/
	protected String LINKNAME; 
	
	/**
	* 联系人地址
	*/
	protected String LINKADDRESS; 
	
	/**
	* 发送次数
	*/
	protected Long SENDTIMES; 
	
	@Override
	public void setId(String LINKID) {
		this.LINKID = LINKID.toString();
	}
	@Override
	public String getId() {
		return LINKID.toString();
	}	
	
	public void setLINKID(String LINKID) {
		this.LINKID = LINKID;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getLINKID() {
		return this.LINKID;
	}
	
	public void setUSERID(String USERID) {
		this.USERID = USERID;
	}
	
	/**
	 * 返回 用户ID
	 * @return
	 */
	public String getUSERID() {
		return this.USERID;
	}
	
	public void setMAILID(String MAILID) {
		this.MAILID = MAILID;
	}
	
	/**
	 * 返回 邮件ID
	 * @return
	 */
	public String getMAILID() {
		return this.MAILID;
	}
	
	public void setSENDTIME(java.util.Date SENDTIME) {
		this.SENDTIME = SENDTIME;
	}
	
	/**
	 * 返回 送送时间
	 * @return
	 */
	public java.util.Date getSENDTIME() {
		return this.SENDTIME;
	}
	
	public void setLINKNAME(String LINKNAME) {
		this.LINKNAME = LINKNAME;
	}
	
	/**
	 * 返回 联系人名称
	 * @return
	 */
	public String getLINKNAME() {
		return this.LINKNAME;
	}
	
	public void setLINKADDRESS(String LINKADDRESS) {
		this.LINKADDRESS = LINKADDRESS;
	}
	
	/**
	 * 返回 联系人地址
	 * @return
	 */
	public String getLINKADDRESS() {
		return this.LINKADDRESS;
	}
	
	public void setSENDTIMES(Long SENDTIMES) {
		this.SENDTIMES = SENDTIMES;
	}
	
	/**
	 * 返回 发送次数
	 * @return
	 */
	public Long getSENDTIMES() {
		return this.SENDTIMES;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("LINKID", this.LINKID) 
		.append("USERID", this.USERID) 
		.append("MAILID", this.MAILID) 
		.append("SENDTIME", this.SENDTIME) 
		.append("LINKNAME", this.LINKNAME) 
		.append("LINKADDRESS", this.LINKADDRESS) 
		.append("SENDTIMES", this.SENDTIMES) 
		.toString();
	}
}
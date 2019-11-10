package com.hotent.base.core.mail.model;

import java.io.Serializable;

/**
 * <pre> 
 * 描述：邮箱设置实体类
 * 构建组：x5-base-core
 * 作者：gjh
 * 邮箱:guojh@jee-soft.cn
 * 日期:2014-10-30-下午3:25:15
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MailSetting implements Serializable{
	protected static final long serialVersionUID = -1563590072023033989L;
	
	/**
	* 主键
	*/
	protected String id; 
	
	// 邮件服务器的IP、端口
	protected String sendHost;
	protected String sendPort;
	
	// 邮件接收服务器的IP、端口和协议
	protected String receiveHost;
	protected String receivePort;
	protected String protocal;
	// smt主机
	protected String smtpHost;
	// smt端口
	protected String smtpPort;
	// pop主机
	protected String popHost;
	// pop端口
	protected String popPort;
	// imap主机
	protected String imapHost;
	// imap端口
	protected String imapPort;

	// 是否是SSL
	protected Boolean SSL = false ;

	// 是否需要身份验证
	protected Boolean validate = true ;

	// 登陆邮件服务器的用户名和密码
	protected String mailAddress;
	protected String password;
	
	// 用户昵称
	protected String nickName ;
	
	// 是否收取附件
	protected Boolean isHandleAttach = true;
	
	// 是否删除服务器上的邮件
	protected Boolean isDeleteRemote = false ;
	
	// 是否父类,主要用于树的展示时用
	protected String isParent;
	// 是否叶子结点(0否,1是),主要用于数据库保存
	protected Integer isLeaf;
	
	protected String open="true";
	//邮件类型，页面用;1:收件箱;2:发件箱;3:草稿箱;4:垃圾箱
	protected Integer types;
	// 是
	public final static int ENABLE = 1 ;
	// 否
	public final static int DISABLE = 0 ;
	
	/**
	* 用户ID
	*/
	protected String userId; 
	
	/**
	* 是否默认
	*/
	protected Short isDefault; 
	/**
	* 接收邮件服务器类型
	*/
	protected String mailType; 
	protected String parentId;
	
	public final static String SMTP_PROTOCAL = "smtp";
	public final static String POP3_PROTOCAL = "pop3";
	public final static String IMAP_PROTOCAL = "imap";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getSSL() {
		return SSL;
	}

	public void setSSL(Boolean SSL) {
		this.SSL = SSL;
	}

	public Boolean getValidate() {
		return validate;
	}

	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getReceiveHost() {
		return receiveHost;
	}

	public void setReceiveHost(String receiveHost) {
		this.receiveHost = receiveHost;
	}

	public String getReceivePort() {
		return receivePort;
	}

	public void setReceivePort(String receivePort) {
		this.receivePort = receivePort;
	}

	public Boolean getIsHandleAttach() {
		return isHandleAttach;
	}

	public void setIsHandleAttach(Boolean isHandleAttach) {
		this.isHandleAttach = isHandleAttach;
	}

	public String getSendHost() {
		return sendHost;
	}

	public void setSendHost(String sendHost) {
		this.sendHost = sendHost;
	}

	public String getSendPort() {
		return sendPort;
	}

	public void setSendPort(String sendPort) {
		this.sendPort = sendPort;
	}

	public Boolean getIsDeleteRemote() {
		return isDeleteRemote;
	}

	public void setIsDeleteRemote(Boolean isDeleteRemote) {
		this.isDeleteRemote = isDeleteRemote;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getPopHost() {
		return popHost;
	}

	public void setPopHost(String popHost) {
		this.popHost = popHost;
	}

	public String getPopPort() {
		return popPort;
	}

	public void setPopPort(String popPort) {
		this.popPort = popPort;
	}

	public String getImapHost() {
		return imapHost;
	}

	public void setImapHost(String imapHost) {
		this.imapHost = imapHost;
	}

	public String getImapPort() {
		return imapPort;
	}

	public void setImapPort(String imapPort) {
		this.imapPort = imapPort;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((SSL == null) ? 0 : SSL.hashCode());
		result = prime * result
				+ ((isDeleteRemote == null) ? 0 : isDeleteRemote.hashCode());
		result = prime * result
				+ ((isHandleAttach == null) ? 0 : isHandleAttach.hashCode());
		result = prime * result
				+ ((mailAddress == null) ? 0 : mailAddress.hashCode());
		result = prime * result
				+ ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((protocal == null) ? 0 : protocal.hashCode());
		result = prime * result
				+ ((receiveHost == null) ? 0 : receiveHost.hashCode());
		result = prime * result
				+ ((receivePort == null) ? 0 : receivePort.hashCode());
		result = prime * result
				+ ((sendHost == null) ? 0 : sendHost.hashCode());
		result = prime * result
				+ ((sendPort == null) ? 0 : sendPort.hashCode());
		result = prime * result
				+ ((smtpHost == null) ? 0 : smtpHost.hashCode());
		result = prime * result
				+ ((smtpPort == null) ? 0 : smtpPort.hashCode());
		result = prime * result
				+ ((popHost == null) ? 0 : popHost.hashCode());
		result = prime * result
				+ ((popPort == null) ? 0 : popPort.hashCode());
		result = prime * result
				+ ((imapHost == null) ? 0 : imapHost.hashCode());
		result = prime * result
				+ ((imapPort == null) ? 0 : imapPort.hashCode());
		result = prime * result
				+ ((validate == null) ? 0 : validate.hashCode());
		result = prime * result
				+ ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((isDefault == null) ? 0 : isDefault.hashCode());
		result = prime * result
				+ ((mailType == null) ? 0 : mailType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MailSetting))
			return false;
		MailSetting other = (MailSetting) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (SSL == null) {
			if (other.SSL != null)
				return false;
		} else if (!SSL.equals(other.SSL))
			return false;
		if (isDeleteRemote == null) {
			if (other.isDeleteRemote != null)
				return false;
		} else if (!isDeleteRemote.equals(other.isDeleteRemote))
			return false;
		if (isHandleAttach == null) {
			if (other.isHandleAttach != null)
				return false;
		} else if (!isHandleAttach.equals(other.isHandleAttach))
			return false;
		if (mailAddress == null) {
			if (other.mailAddress != null)
				return false;
		} else if (!mailAddress.equals(other.mailAddress))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (protocal == null) {
			if (other.protocal != null)
				return false;
		} else if (!protocal.equals(other.protocal))
			return false;
		if (receiveHost == null) {
			if (other.receiveHost != null)
				return false;
		} else if (!receiveHost.equals(other.receiveHost))
			return false;
		if (receivePort == null) {
			if (other.receivePort != null)
				return false;
		} else if (!receivePort.equals(other.receivePort))
			return false;
		if (sendHost == null) {
			if (other.sendHost != null)
				return false;
		} else if (!sendHost.equals(other.sendHost))
			return false;
		if (sendPort == null) {
			if (other.sendPort != null)
				return false;
		} else if (!sendPort.equals(other.sendPort))
			return false;
		if (imapHost == null) {
			if (other.imapHost != null)
				return false;
		} else if (!imapHost.equals(other.imapHost))
			return false;
		if (imapPort == null) {
			if (other.imapPort != null)
				return false;
		} else if (!imapPort.equals(other.imapPort))
			return false;
		
		if (smtpHost == null) {
			if (other.smtpHost != null)
				return false;
		} else if (!smtpHost.equals(other.smtpHost))
			return false;
		if (smtpPort == null) {
			if (other.smtpPort != null)
				return false;
		} else if (!smtpPort.equals(other.smtpPort))
			return false;
		if (popHost == null) {
			if (other.popHost != null)
				return false;
		} else if (!popHost.equals(other.popHost))
			return false;
		if (popPort == null) {
			if (other.popPort != null)
				return false;
		} else if (!popPort.equals(other.popPort))
			return false;
		if (validate == null) {
			if (other.validate != null)
				return false;
		} else if (!validate.equals(other.validate))
			return false;
		
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (isDefault == null) {
			if (other.isDefault != null)
				return false;
		} else if (!isDefault.equals(other.isDefault))
			return false;
		if (mailType == null) {
			if (other.mailType != null)
				return false;
		} else if (!mailType.equals(other.mailType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MailSeting [id=" + id + ",sendHost=" + sendHost + ", sendPort=" + sendPort
				+ ", receiveHost=" + receiveHost + ", receivePort="
				+ receivePort+ ", imapHost=" + imapHost + ", imapPort="
				+ imapPort+ ", smtpHost=" + smtpHost + ", smtpPort="
				+ smtpPort+ ", popHost=" + popHost + ", popPort="
				+ popPort + ", protocal=" + protocal + ", SSL=" + SSL
				+ ", validate=" + validate + ", mailAddress=" + mailAddress
				+ ", password=" + password + ", nickName=" + nickName
				+ ", isHandleAttach=" + isHandleAttach + ", isDeleteRemote="
				+ isDeleteRemote + ",userId=" + userId + ",isDefault=" + isDefault + ",mailType=" + mailType + "]";
	}
}
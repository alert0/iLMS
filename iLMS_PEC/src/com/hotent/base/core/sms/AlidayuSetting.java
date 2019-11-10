package com.hotent.base.core.sms;

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
public class AlidayuSetting implements Serializable{
	protected static final long serialVersionUID = -1563590072023033989L;
	 
	protected String freeSignName;
	protected String appkey;
	protected String secret;
	protected String url;
	protected String extend;
	protected String calledShowNum;
	public String getFreeSignName() {
		return freeSignName;
	}
	public String getAppkey() {
		return appkey;
	}
	public String getSecret() {
		return secret;
	}
	public String getUrl() {
		return url;
	}
	public String getExtend() {
		return extend;
	}
	public String getCalledShowNum() {
		return calledShowNum;
	}
	public void setFreeSignName(String freeSignName) {
		this.freeSignName = freeSignName;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public void setCalledShowNum(String calledShowNum) {
		this.calledShowNum = calledShowNum;
	}
 
	
 
}
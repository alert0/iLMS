package com.hanthink.util.verification;

import java.io.Serializable;

/**
 * 
 * @Desc		: 登录认证码
 * @FileName	: VerificationCode.java
 * @CreateOn	: 2019年7月19日 下午2:44:50
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2019年7月19日		V1.0		ZUOSL		新建
 */
public class VerificationCode implements Serializable{

	private static final long serialVersionUID = -7441636507275924382L;
	
	private String value;
	private long createTime = 0L;
	private static int OUT_TIME = 180; //60; //s 用超时时长，超过该时长则验证码失效
	
	public VerificationCode(String value){
		this.value = value;
		this.createTime = System.currentTimeMillis();
	}
	
	public boolean isTimeOut(){
		return ((System.currentTimeMillis() - createTime) > (OUT_TIME * 1000)); 
	}
	
	public String getVerificationCode(){
		return this.isTimeOut() ? null : this.value;
	}

}

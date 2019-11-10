package com.hanthink.util.verification;

import java.util.Set;

/**
 * 
 * @Desc		: 登录验证码保存操作工具类
 * @FileName	: IVerificationCodeUtil.java
 * @CreateOn	: 2019年7月19日 下午1:50:09
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2019年7月19日		V1.0		ZUOSL		新建
 */
public interface IVerificationCodeUtil {
	
	public void addVerificationCode(String key, String value);
	
	public String getVerificationCode(String key);
	
	public boolean removeVerificationCode(String key);
	
	public String getVerificationCode(String key, boolean remove);
	
	public Set<String> getVerificationMapKey();

}

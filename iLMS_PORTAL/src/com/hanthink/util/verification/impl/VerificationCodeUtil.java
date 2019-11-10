package com.hanthink.util.verification.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.hanthink.util.verification.IVerificationCodeUtil;
import com.hanthink.util.verification.VerificationCode;

/**
 * 
 * @Desc		: 登录验证码相关
 * @FileName	: VerificationCodeUtil.java
 * @CreateOn	: 2019年7月19日 下午2:24:16
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2019年7月19日		V1.0		ZUOSL		新建
 */
public class VerificationCodeUtil implements IVerificationCodeUtil{
	
	private Map<String, VerificationCode> map = Collections.synchronizedMap( new HashMap<String, VerificationCode>());

	@Override
	public void addVerificationCode(String key, String value) {
		map.put(key, new VerificationCode(value));
	}

	@Override
	public String getVerificationCode(String key) {
		return getVerificationCode(key, false);
	}

	@Override
	public boolean removeVerificationCode(String key) {
		VerificationCode vc = map.get(key);
		if(null != vc){
			map.remove(key);
			return true;
		}
		return false;
	}

	@Override
	public String getVerificationCode(String key, boolean remove) {
		VerificationCode vc = map.get(key);
		if(null != vc){
			if(remove){
				map.remove(key);
			}
			if(!vc.isTimeOut()){
				return vc.getVerificationCode();
			}
		}
		return null;
	}

	@Override
	public Set<String> getVerificationMapKey() {
		return map.keySet();
	}
	
}

package com.hanthink.gps.util.interceptor;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.opensymphony.xwork2.ActionContext;
import com.hanthink.gps.pub.vo.BaseVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.pub.vo.UserVO;

@Aspect
public class DaoMethodInterceptor{
	 @Pointcut("@annotation(com.hanthink.gps.util.annotation.VoInsert)")
	 public void insertMethod() {}
	 
	 @Pointcut("@annotation(com.hanthink.gps.util.annotation.VoUpdate)")
	 public void updateMethod() {}
	 
	@Before("insertMethod()")
	public void beforeAdd(JoinPoint jp){
		Object[] args = jp.getArgs();
		for(Object arg : args){
			if(arg instanceof BaseVO){
				BaseVO vo = (BaseVO)arg;
				
				Date dt = new Date();
				UserVO userInfo = (UserVO)ActionContext.getContext().getSession().get(Constants.USER_KEY);
				
				vo.setEntryDate(dt);
				vo.setModifyDate(dt);
				if(userInfo != null){
					vo.setEntryId(userInfo.getUserId());
					vo.setModifyId(userInfo.getUserId());	
				}
			}
		}
	}
	@Before("updateMethod()")
	public void beforeUpdate(JoinPoint jp){
		Object[] args = jp.getArgs();
		for(Object arg : args){
			if(arg instanceof BaseVO){
				BaseVO vo = (BaseVO)arg;
				
				Date dt = new Date();
				UserVO userInfo = (UserVO)ActionContext.getContext().getSession().get(Constants.USER_KEY);
				vo.setModifyDate(dt);
				if(userInfo != null){
					vo.setModifyId(userInfo.getUserId());	
				}
			}
		}
	}
}

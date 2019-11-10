package com.hanthink.gps.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.hanthink.gps.pub.dao.CommDao;
import com.hanthink.gps.pub.vo.SystemParamVO;

/**
 *  
 * @author qzhang
 *
 */
public class AppContextUtil {
	
	/**
	 * 获取session参数
	 * @param key
	 * @return
	 */
	public static Object getSesssionAttribute(String key){
		if(ActionContext.getContext().getSession().containsKey(key)){
			return ActionContext.getContext().getSession().get(key);	
		}
		return null;
	}
	/**
	 * 设定session参数
	 * @param key
	 * @return
	 */
	public static void setSesssionAttribute(String key,Object value){
		if(ActionContext.getContext().getSession().containsKey(key)){
			ActionContext.getContext().getSession().remove(key);	
		}
		ActionContext.getContext().getSession().put(key,value);
	}
	
	/**
	 * 获取application上下文参数
	 * @param key
	 * @return
	 */
	public static Object getApplicationAttribute(String key){
		if(ActionContext.getContext().getApplication().containsKey(key)){
			return ActionContext.getContext().getApplication().get(key);	
		}
		return null;
	}
	
	/**
	 * 设定application上下文参数
	 * @param key
	 * @return
	 */
	public static void setApplicationAttribute(String key,Object value){
		if(ActionContext.getContext().getApplication().containsKey(key)){
			ActionContext.getContext().getApplication().remove(key);	
		}
		ActionContext.getContext().getApplication().put(key,value);
	}
	
	
	
	
}

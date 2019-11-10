package com.hanthink.gps.util.sequence;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.hanthink.gps.util.DateUtil;
import com.hanthink.gps.util.StringUtil;

/**
 * Util类
 * @author qzhang
 *
 */
public class CommonUtil {
	
	/**
	 * json字符串转换成对应的Vo的实例
	 * @param <T> VO的类型
	 * @param beanClass VO的类型
	 * @param jsonStr json字符串
	 * @return 转换结果
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public static <T>  T jsonToBean(Class<T> beanClass, String jsonStr) throws JSONException {
		try {
			T bean = beanClass.newInstance();
			// 反序列化
			Map map = (Map)JSONUtil.deserialize(jsonStr);
			// 转换成bean
			mapToBean(bean, map);
			return bean;
		}catch(Exception e){
			throw new JSONException(e.getMessage());
		}
	}
	
	/**
	 * json字符串转换成对应的Vo的列表
	* @param <T> VO的类型
	 * @param beanClass VO的类型
	 * @param jsonStr json字符串
	 * @return 转换结果
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public static  <T>  List<T> jsonToBeanList(Class<T> beanClass, String jsonStr) throws JSONException {
		try {
			T bean;
			// 反序列化
			List<Map> listMap = (List<Map>)JSONUtil.deserialize(jsonStr);
			// 结果
			List<T> list = new ArrayList<T>();
			
			//转换
			for(int i = 0,len = listMap.size(); i < len; i++){
				bean = beanClass.newInstance();
				// 转换成bean
				mapToBean(bean, listMap.get(i));
				list.add(bean);
			}
			
			return list;
		}catch(Exception e){
			throw new JSONException(e.getMessage());
		}
	}
	
	
	/**
	 * map转换成bean
	 *
	 * @param bean
	 *            bean
	 * @param properties
	 *            map
	 */
	@SuppressWarnings("unchecked")
	public static void mapToBean(Object bean, Map properties) {
		if ((bean == null) || (properties == null)) {
			return;
		}

		Iterator entries = properties.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			if (name == null) {
				continue;
			}
			setProperty(bean, name, entry.getValue());
		}
	}
	
	/**
	 * 对像转换成String 
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj){
		return (obj == null)?"":obj.toString();
	}
	
	/**
	 * 设定bean的属性
	 *
	 * @param bean
	 *            bean
	 * @param name
	 *            bean property name
	 * @param value
	 *            bean property value
	 */
	@SuppressWarnings("unchecked")
	private static void setProperty(Object bean, String name, Object value) {

		try {
			PropertyDescriptor des = new PropertyDescriptor(name, bean
					.getClass());
			Class cla = des.getPropertyType();
			Object newValue = null;
			String strValue = objectToString(value);
			
			if (Long.class == cla) {
				if(!StringUtil.isNullOrEmpty(strValue)){
					newValue = Long.valueOf(strValue);	
				}
			} else if (String.class == cla) {
				newValue = strValue;
			} else if (Date.class == cla) {
				newValue = DateUtil.formatStringToDate(strValue);
			} else if(Double.class == cla){
				if(!StringUtil.isNullOrEmpty(strValue)){
					newValue = Double.valueOf(strValue);	
				}
			}else if(Boolean.class == cla){
				newValue = Boolean.valueOf(strValue);
			}else{
				newValue = value;
			}
			Method me = des.getWriteMethod();
			me.invoke(bean, newValue);
		}catch (Exception e) {
			return;
		}
	}
}

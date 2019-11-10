/**
 * 描述：TODO
 * 包名：com.hotent.base.core.util
 * 文件名：GsonUtil.java
 * 作者：User-mailto:chensx@jee-soft.cn
 * 日期2014-7-29-下午3:57:22
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.base.core.util;

import java.lang.reflect.Type;
import java.util.Date;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hotent.base.core.util.gson.BooleanSerializer;
import com.hotent.base.core.util.gson.DateSerializer;
import com.hotent.base.core.util.gson.JsonObjectSerializer;
import com.hotent.base.core.util.gson.SuperclassExclusionStrategy;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-base-core
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-7-29-下午3:57:22
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class GsonUtil {
	/**
	 * @param jsonObject
	 * @param cls
	 * @return 
	 * C
	 * @exception 
	 * @since  1.0.0
	 */
	public static <C>  C toBean(JSONObject jsonObject,Class<C> cls){
		return toBean(jsonObject.toString(), cls);
	}
	
	private static Gson getGson(){
		GsonBuilder gsonBuilder = new GsonBuilder();  
        gsonBuilder.registerTypeAdapter(String.class, new JsonObjectSerializer())
        		   .registerTypeAdapter(Date.class, new DateSerializer())
        		   .registerTypeAdapter(Boolean.class, new BooleanSerializer())
        		   .addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
        		   .addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
        Gson gson = gsonBuilder.create();
        return gson;
	}
	
	/**
	 * 把jsonStr生成一个cls对象
	 * @param jsonObject
	 * @param cls
	 * @return 
	 * C
	 * @exception 
	 * @since  1.0.0
	 */
	public static <C>  C toBean(String  jsonStr,Class<C> cls){
		if(cls.equals(String.class)){
			return (C)jsonStr;
		}
		C o = (C)getGson().fromJson(jsonStr,cls);
		return o;
	}
	
	/**
	 * 转换jsonStr为java bean
	 * @param jsonStr json格式的字符串
	 * @param type 待转换类型(可以是泛型)
	 *		  <pre>例如:new TypeToken&lt;ArrayList&lt;SysServiceParam>>(){}.getType()</pre>
	 * @return
	 */
	public static <C> C toBean(String jsonStr,Type type){
		C o = (C)getGson().fromJson(jsonStr,type);
		return o;
	}
	
	/**
	 * 将对象转化为JsonElement
	 * @param obj
	 * @return
	 */
	public static JsonElement toJsonTree(Object obj){
		return getGson().toJsonTree(obj);
	}
	
	/**
	 * 将字符串转化为JsonElement
	 * @param json json格式的字符串
	 * @return
	 */
	public static JsonElement parse(String json){
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(json);
	}
	
	/**
	 * 按照指定格式获取json对象中的属性值
	 * @param jobject json对象
	 * @param key 指定属性
	 * @param defaultVal 默认值
	 * @param cls 返回格式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <C> C getValue(JSONObject jobject,String key,Object defaultVal,Class<C> cls){
		Object object = jobject.get(key);
		if(BeanUtils.isEmpty(object)){
			if(defaultVal!=null){
				return toBean(defaultVal.toString(), cls);
			}
			else{
				return null;
			}
		}
		if(String.class.equals(cls)){
			return (C)object.toString();
		}
		return toBean(object.toString(), cls);
	}
}

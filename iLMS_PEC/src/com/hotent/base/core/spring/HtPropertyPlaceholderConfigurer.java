package com.hotent.base.core.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hotent.base.core.util.AppUtil;


/**
 * 扩展这个的目的是可以通过这个类去获取属性文件中某个键的具体值。
 * 
 * {@linkplain AppUtil }l
 * <pre> 
 * 扩展了方法：getValue(String key)
 * 
 * 
 * 构建组：x5-base-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-11-21-下午5:41:45
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class HtPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	
	/** 
     * 属性key,value 
     */  
    private static Map<String, String> properties;  
	
	public HtPropertyPlaceholderConfigurer() {  
        properties = new HashMap<String, String>();  
    }  
    
  
    /** 
     *  
     */  
    protected void convertProperties(Properties props) {  
        Set<String> keys = props.stringPropertyNames();  
        for (String key : keys) {  
            String value = props.getProperty(key);  
            properties.put(key, value);  
        }  
        super.convertProperties(props);  
    }  
  
    /** 
     *  根据建获取属性中的值。
     * @param key 
     * @return<br> 
     * @date 2013年8月11日上午1:46:07<br> 
     * @author <br> 
     */  
    public String getValue(String key) {  
        return properties.get(key);  
    }  

}

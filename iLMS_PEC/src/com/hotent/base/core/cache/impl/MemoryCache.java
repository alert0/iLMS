package com.hotent.base.core.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.hotent.base.core.cache.ICache;

/**
 * 内存的cache实现。
 * <pre> 
 * 构建组：x5-base-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2013-12-18-下午5:06:38
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MemoryCache<T> implements ICache<T> {
	
	private  Map<String,T> map=new HashMap<String, T>();

	@Override
	public void add(String key, T obj, int timeout) {
		map.put(key, obj);
		
	}

	@Override
	public void add(String key, T obj) {
		map.put(key, obj);
		
	}

	@Override
	public void delByKey(String key) {
		map.remove(key);
	}

	@Override
	public void clearAll() {
		map.clear();
	}

	@Override
	public T getByKey(String key) {
		return map.get(key);
	}

	@Override
	public boolean containKey(String key) {
		
		return map.containsKey(key);
	}

}

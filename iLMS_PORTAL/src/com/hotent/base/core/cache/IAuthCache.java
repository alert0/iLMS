package com.hotent.base.core.cache;

/**
 *  用户认证信息Cache
 * @Desc		: 
 * @FileName	: IAuthCache.java
 * @CreateOn	: 2019年5月27日 下午6:40:38
 * @author 		: ZUOSL
 *
 * @param <T>
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
public interface IAuthCache<T extends Object> {
	/**
	 * 添加缓存。
	 * @param key
	 * @param obj
	 * @param timeout
	 */
	void add(String key,T obj,int timeout);
	
	/**
	 * 添加缓存。
	 * @param key
	 * @param obj
	 */
	void add(String key,T obj);
	
	/**
	 * 根据键删除缓存
	 * @param key
	 */
	void delByKey(String key);
	
	/**
	 * 清除所有的缓存
	 */
	void clearAll();
	
	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	Object getByKey(String key);
	
	/**
	 * 包含key。
	 * @param key
	 * @return
	 */
	boolean containKey(String key);

	/**
	 * 重置缓存用户的超时时间
	 * @param key
	 * @author ZUOSL	
	 * @DATE	2019年5月29日 下午4:52:32
	 */
	void resetUserTimeout(String key);
}

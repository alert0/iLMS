package com.hotent.base.core.cache.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.hotent.base.core.cache.IAuthCache;
import com.hotent.sys.api.auth.IAuthUser;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * 认证信息Cache的实现
 * @Desc		: 
 * @FileName	: AuthCache.java
 * @CreateOn	: 2019年5月28日 下午6:42:49
 * @author 		: ZUOSL
 *
 * @param <T>
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
public class AuthCache implements IAuthCache<IAuthUser> {

	private int timeOut = 0;

	private MemcachedClient memcachedClient;
	
	private static String userAuthCachePrefix = "userAuthCacheKey";
	private static int memcachedMaxTimeOut = 48 * 3600;  //s MEMCACHED默认保存的最大时长 //60;
	
	private  Map<String,IAuthUser> map = Collections.synchronizedMap( new HashMap<String,IAuthUser>());
	
	public void setTimeOut(int timeOut){
		this.timeOut = timeOut;
	}
	
	public void setMemcachedClient(MemcachedClient tmp){
		this.memcachedClient = tmp;
	}
	
	public AuthCache(){
		try {
			
			//清理超时的用户信息
			new Timer().schedule(new TimerTask() { 
		      @Override
		      public void run() { 
		    	  try {
		    		  if(null != map){
		    			  List<String> uidList = new ArrayList<String>();
		    			  for(String tUid : map.keySet()){
		    				  uidList.add(tUid);
		    			  }
		    			  for(String tUid : uidList){
		    				  IAuthUser au = map.get(tUid);
		    				  if(null == au || au.isTimeOut()){
		    					  map.remove(tUid);
		    					  if(null != memcachedClient){
	    							memcachedClient.delete(userAuthCachePrefix+tUid);
	    						  }
		    				  }
		    			  }
//		    			  System.out.println("当前在线人数：" + map.size());
//		    			  for(String tUid : map.keySet()){
//		    				  System.out.println("uid:"+tUid);
//		    				  IAuthUser au = map.get(tUid);
//		    				  IAuthUser au2 = memcachedClient.get(userAuthCachePrefix+tUid);
//		    				  System.out.println(au);
//		    				  System.out.println(au2);
//		    				  if(null!=au){
//		    					  System.out.println("map:"+ au.getUser().getAccount()+","+au.isTimeOut());
//		    				  }
//		    				  if(null!=au2){
//		    					  System.out.println("mem:"+ au2.getUser().getAccount()+","+au2.isTimeOut());
//		    				  }
//		    				  System.out.println("------------");
//		    			  }
		    		  }
				} catch (Exception e) {
					e.printStackTrace();
				}
		      } 
		    },3600*1000, 7200*1000);  //  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized  void add(String key, IAuthUser obj, int timeout) {
		try {
			map.put(key, obj);
			if(null != memcachedClient){
				memcachedClient.set(userAuthCachePrefix+key, timeout, obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public synchronized  void delByKey(String key) {
		try {
			map.remove(key);
			if(null != memcachedClient){
				memcachedClient.delete(userAuthCachePrefix+key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public  void clearAll()  {
		try {
			map.clear();
			if(null != memcachedClient){
				memcachedClient.flushAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public synchronized  IAuthUser getByKey(String key) {
		try {
			IAuthUser obj = map.get(key);
			
			//超时时清空
			if(null != obj && obj.isTimeOut()){
				map.remove(key);
				obj = null;
			}
			
//			if(null != obj){
//				System.out.println("从map获取...");
//				System.out.println(obj);
//				System.out.println("map:"+ obj.getUser().getAccount()+","+obj.isTimeOut());
//			}
			if(null == obj && null != memcachedClient){
				obj = memcachedClient.get(userAuthCachePrefix+key);
				if(null != obj){
					map.put(key, obj);
//					System.out.println("从mem获取...");
//					System.out.println(obj);
//					System.out.println("mem:"+ obj.getUser().getAccount()+","+obj.isTimeOut());
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean containKey(String key) {
		Object obj = getByKey(userAuthCachePrefix+key);
		if(obj == null){
			return false;
		}
		return true;
	}

	@Override
	public synchronized  void add(String key, IAuthUser obj) {
		
		try {
			map.put(key, obj);
			if(null != memcachedClient){
				memcachedClient.set(userAuthCachePrefix+key, memcachedMaxTimeOut, obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetUserTimeout(String key) {
		IAuthUser obj = map.get(key);
		
		if(null != obj){
			obj.resetTimeout();
		}
		if(null != memcachedClient){
			try {
				IAuthUser memUser = memcachedClient.get(userAuthCachePrefix+key);
				if(null != memUser){
					memUser.resetTimeout();
					memcachedClient.set(userAuthCachePrefix+key, memcachedMaxTimeOut, memUser);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

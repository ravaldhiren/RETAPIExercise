package com.p2ptransfer.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.p2ptransfer.model.User;

public class UserCache {

	private UserCache(){
		
	}
	
	private volatile Map<Long , User> userCache  = new ConcurrentHashMap<Long, User>();
	
	public static UserCache getInstance(){
		return LazyUserCacheHolder.INSTANCE;
	}
	private static class LazyUserCacheHolder {
		static final UserCache INSTANCE = new UserCache();
	}
	
	public synchronized void addUserToCache(Long userId, User usr){
		userCache.put(userId, usr);
	}
	/**
	 * 
	 * @param id
	 * @return 
	 * 
	 */
	public User getUser(Long id){
		return userCache.get(id);
	}
}

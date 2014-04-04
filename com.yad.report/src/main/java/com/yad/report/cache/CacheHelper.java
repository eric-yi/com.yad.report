// com.yad.report.cache.CacheHelper.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:20:08 
 */
public class CacheHelper {
	private static final CacheHelper help = new CacheHelper();
	public static final CacheHelper getHelper() {
		return help;
	}
	
	public void addCache(String key, CacheContent content) {
		CacheValue cv = new CacheValue(System.currentTimeMillis(), content);
		cacheMap.put(key, cv);
	}
	
	public CacheContent getCache(String key) {
		CacheValue cv = cacheMap.get(key);
		if (cv != null)
			return cv.getContent();
		return null;
	}
	
	public void setCheck_period(long check_period) {
		this.check_period = check_period;
	}

	public void setExpire_time(long expire_time) {
		this.expire_time = expire_time;
	}
	
	public static String[] getCacheKeys() {
		List<String> keyList = new ArrayList<String>();
		for (String key : cacheMap.keySet()) keyList.add(key);
		return keyList.toArray(new String[keyList.size()]);
	}
	
	private static Map<String, CacheValue> cacheMap;
	private long check_period = 60 * 60 * 1000;			// interval of evey check, an hour by default
	private long expire_time = 10 * 60 * 1000;				// expire time of cache, 10 minutes by default
	private static final Object lock = new Object();
	private static volatile boolean looped;
	private CacheHelper() {
		init();
	}
	
	private void init() {
		looped = false;
		cacheMap = new ConcurrentHashMap<String, CacheValue>();
	}

	public void loopCheck() {
		synchronized (lock) {
			if (looped) return;
			
			new Thread() {
				public void run() {
					// sleep first
					try {
						Thread.sleep(check_period);
					} catch (InterruptedException e) { }
					// check
					List<String> removeList = new ArrayList<String>();
					for (String key : cacheMap.keySet()) {
						CacheValue cv = cacheMap.get(key);
						if ((System.currentTimeMillis()-cv.getStartMs()) > expire_time)
							removeList.add(key);
					}
					for (String remove : removeList)
						cacheMap.remove(remove);
				}
			}.start();
		}
	}
	
	private static class CacheValue {
		private long startMs;
		private CacheContent content;
		public CacheValue(long startMs, CacheContent content) {
			super();
			this.startMs = startMs;
			this.content = content;
		}
		public long getStartMs() {
			return startMs;
		}
		public void setStartMs(long startMs) {
			this.startMs = startMs;
		}
		public CacheContent getContent() {
			return content;
		}
		public void setContent(CacheContent content) {
			this.content = content;
		}
	}
}



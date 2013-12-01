package cn.hzbook.android.test.chapter6;

import java.util.HashMap;

public class ServiceLocator {

	private static HashMap<String, Object> _servicesCache;
	
	public static void init() {
		_servicesCache = new HashMap<String, Object>();
		_servicesCache.put("service://localhost/BookStore", new BookStore());
	}
	
	public static Object get(String serviceId) {
		return _servicesCache.get(serviceId);
	}
}

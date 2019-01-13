package pkg.cache.basic;

public class CacheEntry {
	
		Object value;
		int key;
		CacheEntry prev;
		CacheEntry next;
		long lastAccessedTime;
}

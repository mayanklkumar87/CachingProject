package pkg.cache.basic;

/**
* CacheEntry is the node of doubly linked list to which cache map keys point
*/
public class CacheEntry {
		/** Cache Object value */
		Object value;
		/** Cache Object key */
		int key;
		/** previous Cache Object reference */
		CacheEntry prev;
		/** next Cache Object reference */
		CacheEntry next;
		/** last accessed time of current cache entry */
		long lastAccessedTime;
}

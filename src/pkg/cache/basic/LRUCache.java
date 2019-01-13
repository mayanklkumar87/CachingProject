package pkg.cache.basic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
* LRUCache class allows to add cache object to a map which are
* removed using least recently used algorithm using a hashmap 
* and a doubly linked list and the cache has
* fix size and fix time for which the objects can stay in cache
* these properties are configurable.
*/

public class LRUCache {
	
	/** Cache Object start reference */
	CacheEntry start;
	/** Cache Object end reference */
	CacheEntry end;
	/** Map of property values fetched from test.properties file */
	Map<String, String> propValues = ReadPropertiesFile.property();
	/** maxSize is maximum size of hashmap or number of objects that can stay in a Cache simultaneously*/
	int maxSize = Integer.parseInt(propValues.get("LRUCache.CacheMap.maxSize")); 
	/** maximum time for which an object can stay without being accessed and without getting evicted using lru startegy */
	long maxTime = Long.parseLong(propValues.get("LRUCache.CacheMap.maxTime"));
	/** time after which evictCache method is called */
	long runInterval = Long.parseLong(propValues.get("LRUCache.CacheMap.runInterval"));

	/** Cache Map stores key and CacheEntry */
	private Map<Integer, CacheEntry> cacheEntryMap;
	
	/** Single instance of cache class */
	private static LRUCache LRUSingeltonCache = new LRUCache();
	
	/** gets the single instance */
	public static LRUCache getLRUCacheInstance() 
	{
		return LRUSingeltonCache;
	}

	/** cache class constructor with time eviction thread implementation */
	private LRUCache() 
	{
		cacheEntryMap = new HashMap<>();
		if(maxTime > 0 && runInterval > 0) {
			Runnable evictionTask = () -> {
				while(true) {
					try {
						Thread.sleep(runInterval);
					} catch (InterruptedException e) {
					}
					evictCache();
				}
			};
			new Thread(evictionTask).start();
			}
	}
	
	/** gets CacheEntry object synchronized on cacheEntryMap so that 
	*    only one method is called on cacheEntryMap object at a time
	*/
	public Object getCacheEntry(int key) {
		synchronized(cacheEntryMap){
			if (cacheEntryMap.containsKey(key)) 
			{
				CacheEntry entry = cacheEntryMap.get(key);
				removeNode(entry);
				addAtTop(entry);
				return entry.value;
			}
			return null;
		}
	}
	
	/** Puts a CacheEntry Object synchronized on cacheEntryMap */
	public void putCacheEntry(int key, Object value) {
		synchronized(cacheEntryMap){
			if (cacheEntryMap.containsKey(key)) 
			{
				CacheEntry entry = cacheEntryMap.get(key);
				entry.value = value;
				removeNode(entry);
				addAtTop(entry);
			} else {
				CacheEntry insertNewEntry = new CacheEntry();
				insertNewEntry.prev = null;
				insertNewEntry.next = null;
				insertNewEntry.value = value;
				insertNewEntry.key = key;
				if (cacheEntryMap.size() >= maxSize) 
				{
					cacheEntryMap.remove(end.key);
					removeNode(end);				
					addAtTop(insertNewEntry);

				} else {
					addAtTop(insertNewEntry);
				}
				cacheEntryMap.put(key, insertNewEntry);
			}
		}
	}
	/** Adds an CacheEntry object to start of list */
	public void addAtTop(CacheEntry node) {
		node.next = start;
		node.prev = null;
		if (start != null) {
			start.prev = node;
		}
		start = node;
		if (end == null) {
			end = start;
		}
		node.lastAccessedTime = System.currentTimeMillis();
	}
	
	/** removes the node provided as argument */
	public void removeNode(CacheEntry node) {

		if (node.prev != null) {
			node.prev.next = node.next;
		} else {
			start = node.next;
		}

		if (node.next != null) {
			node.next.prev = node.prev;
		} else {
			end = node.prev;
		}
	}
	
	/** Evicts cache based on time an object is removed after maxTime is passed after it had been accessed */
	public void evictCache(){
		ArrayList<Integer> deleteKey = new ArrayList<>();
		synchronized(cacheEntryMap) {
			Iterator<Map.Entry<Integer, CacheEntry>> itr = cacheEntryMap.entrySet().iterator();
			while(itr.hasNext()) {
				Map.Entry<Integer, CacheEntry> entry = itr.next(); 
				Integer key = entry.getKey();
				CacheEntry value = entry.getValue();
				if(value != null && System.currentTimeMillis() > maxTime + value.lastAccessedTime) {
					deleteKey.add(key);
				}
			}
		}
		if(deleteKey.size()>0) {
			for (Integer key : deleteKey) {
	            synchronized (cacheEntryMap) {
	            	removeNode(cacheEntryMap.get(key));
	            	cacheEntryMap.remove(key);
	            }
	            Thread.yield();
			}
		}
	}
}

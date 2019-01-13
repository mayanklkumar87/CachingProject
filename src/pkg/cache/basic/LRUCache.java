package pkg.cache.basic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LRUCache {

	CacheEntry start;
	CacheEntry end;
	Map<String, String> propValues = ReadPropertiesFile.property();
	int maxSize = Integer.parseInt(propValues.get("LRUCache.CacheMap.maxSize")); 
	long maxTime = Long.parseLong(propValues.get("LRUCache.CacheMap.maxTime"));
	long runInterval = Long.parseLong(propValues.get("LRUCache.CacheMap.runInterval"));

	private Map<Integer, CacheEntry> cacheEntryMap;

	private static LRUCache LRUSingeltonCache = new LRUCache();

	public static LRUCache getLRUCacheInstance() 
	{
		return LRUSingeltonCache;
	}

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
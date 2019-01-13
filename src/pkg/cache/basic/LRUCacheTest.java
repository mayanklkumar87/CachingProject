package pkg.cache.basic;

import java.util.Map;

public class LRUCacheTest {

	CacheEntry testEntry = new CacheEntry();
	LRUCache testLRUCache = LRUCache.getLRUCacheInstance();
	public String testGetCacheEntry() {
		String result="";
		if(testLRUCache.getCacheEntry(100) == "Test") {
			result="get from cache successfully";
		}else
			result="failed to get node at top";
		return result;
	}

	
	public String testPutCacheEntry() {
		String result="";
		testLRUCache.putCacheEntry(100, "Test");
		if(testLRUCache.start.value == "Test") {
			result="put in cache successfully";
		}else
			result="failed to add node at top";
		return result;
	}

	
	public String testAddAtTop() {
		String result="";
		testLRUCache.addAtTop(testEntry);
		if(testLRUCache.start == testEntry) {
			result="Added node at top";
		}else
			result="failed to add node at top";
		return result;
	}

	
	public String testRemoveNode() {
		String result="";
		testLRUCache.removeNode(testEntry);
		if(testLRUCache.start != testEntry) {
			result="Removed node";
		}else
			result="Could not remove node";
		return result;
	}
	
	public String testEvictCache() throws InterruptedException {
		String result="";
		testLRUCache.putCacheEntry(1, "Test Evict");
		Thread.sleep(2000);
		testLRUCache.evictCache();
		if(testLRUCache.getCacheEntry(1) != "Test Evict") {
			result="Cache Evicted";
		}else
			result="Cache still present";
		return result;
	}
	
	public static void main(String[] args) throws InterruptedException {
		LRUCacheTest test = new LRUCacheTest();
		System.out.println(test.testAddAtTop());
		System.out.println(test.testRemoveNode());
		System.out.println(test.testPutCacheEntry());
		System.out.println(test.testGetCacheEntry());
		System.out.println(test.testEvictCache());
		Map<String, String> propValues = ReadPropertiesFile.property();
		int maxSize = Integer.parseInt(propValues.get("LRUCache.CacheMap.maxSize")); 
		long maxTime = Long.parseLong(propValues.get("LRUCache.CacheMap.maxTime"));
		long runInterval = Long.parseLong(propValues.get("LRUCache.CacheMap.runInterval"));
		System.out.println("maxSize: "+ maxSize + ", maxTime : "+maxTime+", runInterval: "+runInterval);
	}
}


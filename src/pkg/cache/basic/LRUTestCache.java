package pkg.cache.basic;

import static org.junit.Assert.*;

import org.junit.Test;

/**
* LRUCacheTest has JUNIT tests for the basic functionality 
*  of all methods of LRUCache class
*/
public class LRUTestCache {

	
	CacheEntry testEntry = new CacheEntry();
	LRUCache testLRUCache = LRUCache.getLRUCacheInstance();
	
	@Test
	public void testGetCacheEntry() {
		assertSame("Test", testLRUCache.getCacheEntry(100));
	}

	@Test
	public void testPutCacheEntry() {
		testLRUCache.putCacheEntry(100, "Test");		
		assertSame("Test", testLRUCache.start.value);
	}

	@Test
	public void testAddAtTop() {
		testLRUCache.addAtTop(testEntry);
		assertSame(testEntry, testLRUCache.start);
	}

	@Test
	public void testRemoveNode() {
		testLRUCache.addAtTop(testEntry);
		testLRUCache.removeNode(testEntry);
		assertNotEquals(testEntry, testLRUCache.start);
	}


}

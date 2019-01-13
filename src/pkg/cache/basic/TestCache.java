package pkg.cache.basic;

public class TestCache {/*

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.nanoTime();
		
		LRUCache lrucache = LRUCache.getLRUCacheInstance();
		lrucache.putCacheEntry(1, "s");
		lrucache.putCacheEntry(10, 15);
		lrucache.putCacheEntry(15, 10);
		lrucache.putCacheEntry(10, 16);
		lrucache.putCacheEntry(12, 15);
		lrucache.putCacheEntry(18, 10);
		lrucache.putCacheEntry(13, "Y");
		System.out.println(lrucache.getCacheEntry(13));
		System.out.println(lrucache.getCacheEntry(1));
		System.out.println(lrucache.getCacheEntry(10));
		System.out.println(lrucache.getCacheEntry(15));
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				lrucache.putCacheEntry(10, 15);
				lrucache.putCacheEntry(12, 17);
				
			}
			
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.putCacheEntry(18, 10);
			}			
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.putCacheEntry(21, 22);
			}			
		});
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.putCacheEntry(189, 1000);
			}			
		});
		Thread t5 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.putCacheEntry(187, 105);
			}			
		});
		Thread t6 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.putCacheEntry(144, "abcd");
			}			
		});
		Thread t7 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.putCacheEntry(178, "kdkfkfkf");
			}			
		});
		Thread t8 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.getCacheEntry(178);
			}			
		});
		Thread t9 = new Thread(new Runnable() {
			@Override
			public void run() {
				lrucache.getCacheEntry(144);
				lrucache.getCacheEntry(187);
			}			
		});
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		t6.join();
		t7.join();
		t8.join();
		t9.join();
		System.out.println(lrucache.getCacheEntry(10));
		System.out.println(lrucache.getCacheEntry(12));
		//System.out.println(lrucache.getCacheEntry(10));
		System.out.println(lrucache.getCacheEntry(18));
		//code
				long endTime = System.nanoTime();
				System.out.println("Took "+(endTime - startTime) + " ns"); 
	}

*/public static void main(String[] args) {
	System.gc();}}

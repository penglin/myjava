package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSynch {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ThreadTest tt = new ThreadTest();
		ThreadTest tt2 = new ThreadTest();
		tt.start();
		tt2.start();
		tt.t1();
		tt.join();
		tt2.join();
		System.out.println(ThreadTest.getCount());
		Integer count = 0;
		System.out.println(count.hashCode());
		count++;
		System.out.println(count.hashCode());
	}

}

class ThreadTest extends Thread {
	private static volatile  Integer count = 0;
//	private static final byte[] lock = new byte[0];
//	private static Object obj = new Object();
	private static final Lock lock = new ReentrantLock();

	public void run() {
//		synchronized (count) {
			for (int i = 0; i < 1000000; i++) {
				count++;
			}
//		}
	}
	
	/*public void run() {
		for (int i = 0; i < 1000000; i++) {
			lock.lock();
			try {
				count++;
			} finally {
				lock.unlock();
			}
			count++;
		}
	}*/
	
	public synchronized void t1(){
		System.out.println("t1");
		t2();
	}
	public synchronized void t2(){
		System.out.println("t2");
	}


	public static int getCount() {
		return count;
	}
}

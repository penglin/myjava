package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadABCReentrantLock {
	private static Lock lock = new ReentrantLock();
	private static Condition c1 = lock.newCondition();
	private static Condition c2 = lock.newCondition();
	private static Condition c3 = lock.newCondition();
	protected static int state = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread a = new A();
		Thread b = new B();
		Thread c = new C();
		
		c.start();
		b.start();
		a.start();
	}
	
	static class A extends Thread{
		@Override
		public void run() {
			for(int i=0;i<10;i++){
				lock.lock();
				try {
					if(state%3!=0)
						c1.await();
					System.out.print("A");
					state++;
					c2.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.unlock();
			}
		}
	}
	
	static class B extends Thread{
		@Override
		public void run() {
			for(int i=0;i<10;i++){
				lock.lock();
				try {
					if(state%3!=1)
						c2.await();
					System.out.print("B");
					state++;
					c3.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.unlock();
			}
		}
	}
	
	static class C extends Thread{
		@Override
		public void run() {
			for(int i=0;i<10;i++){
				lock.lock();
				try {
					if(state%3!=2)
						c3.await();
					System.out.print("C\n");
					state++;
					c1.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.unlock();
			}
		}
	}
	
}

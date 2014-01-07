package thread;

import java.util.concurrent.Semaphore;

public class ThreadABCSemaphore {
	private final static Semaphore s1 = new Semaphore(1);
	private final static Semaphore s2 = new Semaphore(1);
	private final static Semaphore s3 = new Semaphore(1);
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		s2.acquire();
		s3.acquire();
		
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
				try {
					s1.acquire();
					System.out.print("A");
					s2.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	static class B extends Thread{
		@Override
		public void run() {
			for(int i=0;i<10;i++){
				try {
					s2.acquire();
					System.out.print("B");
					s3.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	static class C extends Thread{
		@Override
		public void run() {
			for(int i=0;i<10;i++){
				try {
					s3.acquire();
					System.out.print("C");
					s1.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

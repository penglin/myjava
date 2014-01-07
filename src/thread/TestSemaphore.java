package thread;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.Semaphore;

import org.junit.Test;

public class TestSemaphore {
	public static final Semaphore s = new Semaphore(3);
	public static void main(String[] args) {
		Thread master = new MasterThread();
		master.start();
		
		Thread minor1 = new MinorThread("a");
		minor1.start();
		Thread minor2 = new MinorThread("b");
		minor2.start();
		Thread minor3 = new MinorThread("c");
		minor3.start();
	}
	
	static class MasterThread extends Thread{
		
		@Override
		public void run() {
			try {
				s.acquire(3);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			int i=0;
			while(i<100){
				System.out.print(".");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			System.out.println();
			s.release(3);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			try {
				s.acquire(3);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i=0;
			while(i<100){
				System.out.print("+");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			s.release(3);
		}
		
		
	}
	
	static class MinorThread extends Thread{
		private String name ;
		
		public MinorThread(String name) {
			super();
			this.name = name;
		}

		@Override
		public void run() {
			try {
				s.acquire(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int i=0;
			while(i<100){
				System.out.println(name+"-");
				i++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			s.release(1);
		}
		
		
	}
}


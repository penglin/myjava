package test;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;


public class TestUUID extends Thread{
	public static Map<String,Integer> map = new Hashtable<String, Integer>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t = new TestUUID();
		Thread t2 = new TestUUID();
		Thread t3 = new TestUUID();
		t.start();
		t2.start();
//		t3.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<20;i++){
			String uuid = UUID.randomUUID().toString();
//			System.out.println(Thread.currentThread()+":"+uuid);
			map.put(uuid, i);
//			System.out.println(Thread.currentThread()+":"+map.size());
		}
		System.out.println(map.size());
	}
}

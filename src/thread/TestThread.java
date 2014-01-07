package thread;

import java.util.ArrayList;
import java.util.List;

public class TestThread implements Runnable{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*for(int i=0;i<10;i++){
			new Thread(new TestThread()).start();
		}*/
		TestThread tt = new TestThread();
		tt.test();
//		tt.monitorRunning();
//		System.out.println(Thread.currentThread());
		/*Thread tt = new Thread(new TestThread());
		tt.start();*/
		/*Thread listenThread = new Thread(tt);
		listenThread.start();*/
	}

	public synchronized void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(Thread.currentThread()+"xx");
			
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Thread thread = null;
	
	public  void test (){
		popThreadPools = new ArrayList<ArrayList<Thread>>();
		ArrayList<Thread> ff = new ArrayList<Thread>();
		Thread tt = new Thread(new TestThread());
		System.out.println(tt.getState()+"--"+"test method tt:"+tt.toString());
		tt.start();
		ff.add(tt);
		
		Thread listenThread = new Thread(tt);
		System.out.println(listenThread.getState()+"--"+"test method listenThread:"+listenThread.toString());
		listenThread.start();
		ff.add(listenThread);
		
		Thread listenThread2 = new Thread(listenThread);
		System.out.println(listenThread2.getState()+"--"+"test method listenThread2:"+listenThread2.toString());
		listenThread2.start();
		ff.add(listenThread2);
		
		//����ڴ˴�����monitorRunning������������TestThread.this�����캯���Ĳ�������������޷�ִ����(�������ַ���������)
		thread = new Thread(TestThread.this);
		System.out.println(thread.getState()+"--"+"test method thisThread:"+thread.toString());
		thread.start();
		ff.add(thread);
/*		Thread thisThread = new Thread(TestThread.this);
		System.out.println(thisThread.getState()+"--"+"test method thisThread:"+thisThread.toString());
		thisThread.start();
		ff.add(thisThread);
*/		popThreadPools.add(ff);

		monitorRunning();
	}
	
	/*public void run() {
		// TODO Auto-generated method stub
		try {
			synchronized(this){
				System.out.println(Thread.currentThread());;
				Thread.sleep(7000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(Thread.currentThread());;
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	private ArrayList<ArrayList<Thread>> popThreadPools = null;
	
	/**
	 * ��ظ����̵߳Ĺ���״̬�������̶߳��˳�������˳�
	 */
	private void monitorRunning()
	{
		System.out.println("-----------coming -----------");
		long currTime = System.currentTimeMillis();
		boolean isHasAlive = true;
		while (isHasAlive)
		{
			isHasAlive = false;
			// ��ǰ�߳���Ϣ2����
			try
			{
				Thread.sleep(2 * 1000);
			}
			catch (InterruptedException e)
			{
			}
			// ��ѵ�����������߳�״̬
			for (int i = 0; i < popThreadPools.size() && !isHasAlive; i++)
			{
				ArrayList<Thread> popThreads = popThreadPools.get(i);
				for (int j = 0; j < popThreads.size() && !isHasAlive; j++)
				{
					Thread popThread = popThreads.get(j);
					System.out.println(popThread.getName()+" ,current State:"+popThread.getState());
					isHasAlive |= !(popThread.getState() == Thread.State.TERMINATED);
				}
			}
		}
		currTime = System.currentTimeMillis() - currTime;
		System.out.println("ִ��ʱ�䣺"+currTime/1000+"��");
		System.out.println("------------out--------------");
	}
}
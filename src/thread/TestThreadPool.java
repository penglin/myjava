package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestThreadPool {

	@Test
	/**
	 * ����һ���ɻ����̳߳أ�����̳߳س��ȳ���������Ҫ���������տ����̣߳����޿ɻ��գ����½��߳�
	 * �̳߳�Ϊ���޴󣬵�ִ�еڶ�������ʱ��һ�������Ѿ���ɣ��Ḵ��ִ�е�һ��������̣߳�������ÿ���½��̡߳�
	 */
	public void testnewCachedThreadPool() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			cachedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println(index);
				}
			});
		}
	}

	@Test
	/**
	 * ����һ�������̳߳أ��ɿ����߳���󲢷������������̻߳��ڶ����еȴ���ʾ���������£�
	 * 
	 * ��Ϊ�̳߳ش�СΪ3��ÿ���������index��sleep 2�룬����ÿ�����ӡ3�����֡�
	 * �����̳߳صĴ�С��ø���ϵͳ��Դ�������á���Runtime.getRuntime().availableProcessors()���ɲο�PreloadDataCache��
	 */
	public void testnewFixedThreadPool() throws InterruptedException {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		// �ر������߳�
		fixedThreadPool.shutdown();
		// �ȴ����߳̽������ټ���ִ������Ĵ���
		fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("all thread complete");
	}

	@Test
	/**
	 * 
	 */
	public void testnewScheduledThreadPool() throws InterruptedException {
		// ����һ�������̳߳أ�֧�ֶ�ʱ������������ִ�С��ӳ�ִ��ʾ���������£�
		// ��ʾ�ӳ�3��ִ�С�
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		scheduledThreadPool.schedule(new Runnable() {

			@Override
			public void run() {
				System.out.println("delay 3 seconds");
			}
		}, 3, TimeUnit.SECONDS);

		// ����ִ��ʾ���������£�
		// ��ʾ�ӳ�1���ÿ3��ִ��һ�Ρ� ScheduledExecutorService��Timer����ȫ�����ܸ�ǿ�󣬺������һƪ�������жԱȡ�
		/*
		 * scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
		 * 
		 * @Override public void run() {
		 * System.out.println("delay 1 seconds, and excute every 3 seconds"); }
		 * }, 1, 3, TimeUnit.SECONDS);
		 */

		// �ر������߳�
		scheduledThreadPool.shutdown();
		// �ȴ����߳̽������ټ���ִ������Ĵ���
		scheduledThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("all thread complete");
	}

	@Test
	/**
	 * ����һ�����̻߳����̳߳أ���ֻ����Ψһ�Ĺ����߳���ִ�����񣬱�֤����������ָ��˳��(FIFO, LIFO, ���ȼ�)ִ��
	 * 
	 * �������������൱��˳��ִ�и�������
	 */
	public void testnewSingleThreadExecutor() throws InterruptedException {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		// ExecutorService singleThreadExecutor =
		// Executors.newSingleThreadScheduledExecutor();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			// System.out.println(i);
			singleThreadExecutor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		// �ر������߳�
		singleThreadExecutor.shutdown();
		// �ȴ����߳̽������ټ���ִ������Ĵ���
		singleThreadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("all thread complete");
	}
}

class MyThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		return null;
	}

}
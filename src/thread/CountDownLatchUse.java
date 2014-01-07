package thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchUse {
	final CountDownLatch downLatch;
	int n; // �����߳���

	public CountDownLatchUse(int n) {
		this.downLatch = new CountDownLatch(n);
		this.n = n;
	}

	class Worker implements Runnable {

		String name;
		int sleep;

		public Worker(String name, int sleep) {
			this.name = name;
			this.sleep = sleep;
		}

		public void run() {
			System.out.println(name + ", start to work.");
			try {
				Thread.sleep(sleep); // ���⹤��. 10s ���ʱ��
			} catch (InterruptedException e) {
				System.out.println(name + " interrupted.");
			}
			System.out.println(name + ", end to work [" + sleep + "] sleep.");
			meDone(); // ĳ�������߳����
		}
	}

	private void meDone() {
		downLatch.countDown();
	}

	public void run() {
		System.out.println("-------------main run start-------------");
		int sleepSaid = 10 * 1000; // ÿ�������߳����⹤�����ʱ��
		Random rm = new Random();
		for (int i = 0; i < n; i++) {
			new Thread(new Worker("worker-" + i, rm.nextInt(sleepSaid) + 1)).start();
		}

		try {
			downLatch.await(); // �ȴ����й����߳����.
		} catch (InterruptedException e) {
			System.out.println("main interrupted.");
		}
		System.out.println("---------------main run end--------------");
	}

	public static void main(String[] args) {
		CountDownLatchUse mtu = new CountDownLatchUse(10);
		mtu.run();
	}
}
package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import org.junit.Test;


public class TestCountDownLatch {
	public static int N = 10;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch doneSignal = new CountDownLatch(N);
		Executor e = new Executor() {

			@Override
			public void execute(Runnable command) {
				new Thread(command).start();
			}
		};

		for (int i = 0; i < N; ++i)
			// create and start threads
			e.execute(new WorkerRunnable(doneSignal, i));

		System.out.println("wait");
		doneSignal.await(); // wait for all to finish
		System.out.println("end");
	}
	
	
	@Test
	public void test() throws InterruptedException {
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(N);

		for (int i = 0; i < N; ++i)
			// create and start threads
			new Thread(new Worker(startSignal, doneSignal)).start();

		System.out.println("start..."); // don't let run yet
		startSignal.countDown(); // let all threads proceed
		System.out.println("startSignal release ...");
		doneSignal.await(); // wait for all to finish
		System.out.println("end");
	}
}

class WorkerRunnable implements Runnable {
	private final CountDownLatch doneSignal;
	private final int i;

	WorkerRunnable(CountDownLatch doneSignal, int i) {
		this.doneSignal = doneSignal;
		this.i = i;
	}

	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		doWork(i);
		doneSignal.countDown();
	}

	void doWork(int i) {
		System.out.println(i + " doWork...");
	}
}


class Worker implements Runnable {
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;

	Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}

	public void run() {
		try {
			startSignal.await();
			doWork();
			doneSignal.countDown();
		} catch (InterruptedException ex) {
		} // return;
	}

	void doWork() {
		System.out.println("dowork");
	}
}

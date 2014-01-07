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
	 * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
	 * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
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
	 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：
	 * 
	 * 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
	 * 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()。可参考PreloadDataCache。
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

		// 关闭启动线程
		fixedThreadPool.shutdown();
		// 等待子线程结束，再继续执行下面的代码
		fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("all thread complete");
	}

	@Test
	/**
	 * 
	 */
	public void testnewScheduledThreadPool() throws InterruptedException {
		// 创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
		// 表示延迟3秒执行。
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		scheduledThreadPool.schedule(new Runnable() {

			@Override
			public void run() {
				System.out.println("delay 3 seconds");
			}
		}, 3, TimeUnit.SECONDS);

		// 定期执行示例代码如下：
		// 表示延迟1秒后每3秒执行一次。 ScheduledExecutorService比Timer更安全，功能更强大，后面会有一篇单独进行对比。
		/*
		 * scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
		 * 
		 * @Override public void run() {
		 * System.out.println("delay 1 seconds, and excute every 3 seconds"); }
		 * }, 1, 3, TimeUnit.SECONDS);
		 */

		// 关闭启动线程
		scheduledThreadPool.shutdown();
		// 等待子线程结束，再继续执行下面的代码
		scheduledThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("all thread complete");
	}

	@Test
	/**
	 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
	 * 
	 * 结果依次输出，相当于顺序执行各个任务。
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
		// 关闭启动线程
		singleThreadExecutor.shutdown();
		// 等待子线程结束，再继续执行下面的代码
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

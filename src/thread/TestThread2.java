package thread;

/**
 * 
 * �������̣߳ɣķֱ��ǣ��£ã��ö��̱߳������Ļ��ѭ����ӡABCABC....ʮ��
 * 
 * @author luoxiaoyi
 * 
 */

public class TestThread2 {

	public static void main(String[] args) {

		new Thread(new OrderThread(0, 'A')).start();

		new Thread(new OrderThread(1, 'B')).start();

		new Thread(new OrderThread(2, 'C')).start();

	}

}

class OrderThread implements Runnable {

	// ������

	private static Object o = new Object();

	// ������¼���ĸ��߳̽�������

	private static int count = 0;

	// ÿ���̵߳ı�ʶ������

	private char ID;

	// �����������߳����еı�ʶ

	private int id;

	// ÿ���߳����еĴ���

	private int num = 0;

	public OrderThread(int id, char ID) {

		this.id = id;

		this.ID = ID;

	}

	public void run() {

		synchronized (o) {

			while (num < 10) {

				if (count % 3 == id) {

					System.out.print(ID);

					++count;

					++num;

					o.notifyAll();

				}else {

					try {
						o.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();

					}

				}

			}

		}

	}

}
package thread;

public class ThreadABC implements Runnable{
	private static Object obj = new Object();
	private String str;
	
	public static void main(String[] args) {
		ThreadABC a = new ThreadABC();
		a.str="A";
		ThreadABC b = new ThreadABC();
		b.str="B";
		ThreadABC c = new ThreadABC();
		c.str="C";
		
		Thread threadA = new Thread(a);
		Thread threadB = new Thread(b);
		Thread threadC = new Thread(c);
		threadA.start();
		threadB.start();
		threadC.start();
	}
	
	public void run(){
		for(int i=0;i<10;i++){
			print();
		}
	}
	
	public synchronized void print() {
		System.out.println(str);
	}
}

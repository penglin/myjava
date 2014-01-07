package thread;

public class ThreadABCSynchronized {
	protected static int state = 0;
	protected static final Object LOCK = new Object();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
			for(int i=0;i<10;){
				synchronized (LOCK) {
					if(state%3==0){
						System.out.print("A");
						state++;
						i++;
					}
				}
			}
			
//			System.out.println("A finished");
		}
	}
	
	static class B extends Thread{
		@Override
		public void run() {
			for(int i=0;i<10;){
				synchronized (LOCK) {
					if(state%3==1){
						System.out.print("B");
						state++;
						i++;
					}
				}
			}
			
//			System.out.println("B finished");
		}
	}
	
	static class C extends Thread{
		@Override
		public void run() {
			for(int i=0;i<10;){
				synchronized (LOCK) {
					if(state%3==2){
						System.out.print("C");
						state++;
						i++;
					}
				}
			}
			
//			System.out.println("C finished");
		}
	}

}

package thread;

public class TestSynchronized extends Thread{
	protected String invokeMethod = null;
	Container c = null;
	boolean operatoe;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t1 = new TestSynchronized(new Container(),true);
		Thread t2 = new TestSynchronized(new Container(),false);
		
		
		t1.start();
		t2.start();
		
		
	}
	
	public TestSynchronized(Container c,boolean operatoe) {
		super();
		this.c = c;
		this.operatoe = operatoe;
	}

	@Override
	public void run() {
		for(int i=0;i<100;i++){
//			Container.add2();
			if(operatoe){
				c.add3();
			}else{
				c.minus();
			}
			System.out.println(this.getName()+"-->"+c.getI());
		}
	}
}

class Container{
	static Object LOCK = new Object();
	private static int i =0;
	public void add(){
		synchronized (LOCK) {
			i++;
		}
	}
	
	public synchronized static void add2(){
		i++;
	}
	
	public synchronized static void minus2(){
		i--;
	}
	
	public synchronized void add3(){
		i++;
	}
	
	public synchronized void minus(){
		i--;
	}
	
	public int getI() {
		return i;
	}
	
}

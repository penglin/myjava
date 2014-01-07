package thread;

import java.util.Random;
import java.util.Set;

public class Start{
	/**
	 * 测试结果：
	 * 如果Key值越大，启动程序的内存所需就越大。如果内存不够大，put数据十分的慢
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new Start().startThread();
	}
	
	private void startThread() throws Exception{
		final TestHashMapEfficient tme = new TestHashMapEfficient();
		final Random r = new Random();
		for(int i=0;i<40;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					int count = 0;
					long t = System.currentTimeMillis();
					while(true){
						if(tme.size()>180000){
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						int rnum = r.nextInt(1800000);
						tme.put("fdksajflaksjdf;lkajsd;fljas;lajsd;jflaksdjfajsd;lfjasdl;fja;lsdjfl;asdjf;lasjdf;ljads;lfjasdl;fj"+rnum, (long)rnum);
						count ++;
						if(count>1000){
							System.out.println(this.toString()+"put "+count+" data container size:"+tme.size()+",cost time"+(System.currentTimeMillis() - t));
							count = 0;
							t = System.currentTimeMillis(); 
						}
					}
				}
			}).start();
		}
		
		while(tme.size()<180000){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					long t = System.currentTimeMillis();
					while(true){
						Set<String> keys = tme.mapKeys(1000);
						for(String key : keys){
							tme.get(key,false);
						}
						System.out.println(this.toString()+" get "+keys.size()+" data ,cost time"+(System.currentTimeMillis() - t));
						t = System.currentTimeMillis(); 
					}
				}
			}).start();
		}
	}
}
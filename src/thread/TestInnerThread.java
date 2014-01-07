package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestInnerThread {
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			final List list = new ArrayList();
			new Thread(new Runnable(){
				
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(new Random().nextInt(2000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int j=0;j<10;j++){
						list.add(j);
					}
					
					System.out.println(Thread.currentThread().getName()+",list size:"+list.size());
				}
				
			}).start();
		}
		List objs = new ArrayList();
		for(int i=0;i<24500;i++){
			objs.add(i);
		}
		new TestInnerThread().multiThreadInsert(objs, "fsadfds");
		objs = null;
	}
	
	private void multiThreadInsert(List objs,final String sql){
//		System.out.println("-------------multiThreadInsert  1------------");
		final List list = new ArrayList();
		for(int i=0;i<objs.size();i++){
			list.add(objs.get(i));
			if(list.size()>=10000||i==objs.size()-1){
//				System.out.println("-------------multiThreadInsert   2------------");
				final List innerList = new ArrayList();
				final List copyList = new ArrayList();
				copyList.addAll(list);
				list.clear();
				
				new Thread(new Runnable(){
					
					public void run() {
						// TODO Auto-generated method stub
						for(int j=0;j<copyList.size();j++){
							innerList.add(copyList.get(j));
							if(innerList.size()>=10000||copyList.size()-1==j){
								System.out.println("-------------multiThreadInsert   3------------");
//								videoPlayService.insert(innerList,sql);
								innerList.clear();
							}
						}
					}
					
				}).start();
			}
		}
	}   
}

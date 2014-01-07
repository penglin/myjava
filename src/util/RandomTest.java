package util;

import java.util.Random;

public class RandomTest {
	public static void main(String[] args) {
		System.out.println((int)Math.random()*2 + 1);
		double d = 0.8d;
		System.out.println((int)d);
		
		while(true){
			int ff = (int)(Math.random()*2) + 1 ;
			System.out.println(ff);
			if(ff==2)
				break;
		}
		
		Random r = new Random();
		for(int i=0;i<1000;i++){
			int random = r.nextInt(1000);
			if(random==1){
				System.out.println("ÃüÖÐÁË");
			}
		}
		System.out.println(r.nextInt(3));
	}
}

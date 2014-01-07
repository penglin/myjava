package util;

import java.util.HashSet;
import java.util.Set;

public class Testchufa {
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		for(int i=0;i<2000;i++){
			set.add((i%3)+"");
		}
		for(String tmp : set){
			System.out.println(tmp);
		}
	}
}

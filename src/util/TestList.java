package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> l1 = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();
		l1.add("fsdfsd");
		l1.add("fsd");
		l1.add("fs");
		l1.add("fsdfdsd");
		
		int index = l1.indexOf("fs");
		System.out.println(index);
		System.out.println(l1.get(index));
		
		l2.addAll(l1);
		l1.clear();
		System.out.println(l2.size());
		System.out.println(l1.size());
		System.out.println(new Random().nextInt(2)%2==0);
	}

}

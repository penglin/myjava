package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIterator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("fds");
		list.add("dff");
		list.add("dsfds");
		list.add("fsdfsdfsd");
		
		Iterator<String> it = list.iterator();
		while(it.hasNext())
			it.next();
		
		for(String t : list){
			System.out.println(t);
		}
		System.out.println("--------------------");
		
		Iterable<String> t2 = list;
		it = t2.iterator();
		while(it.hasNext())
			it.next();
		
		for(String t : t2){
			System.out.println(t);
		}
	}

}

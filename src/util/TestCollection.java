package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class TestCollection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add(null);
		list.add("lin");
		list.add("lin");
		list.add("lin");
		
		System.out.println(list.size());
		System.out.println(list.iterator().hasNext());
		System.out.println(listToString(list,","));
		System.out.println(listToString(list,"*").length());
		
		Set set = new HashSet();
		set.add("lin");
		set.add("lin");
		set.add("lin");
		set.add("lin");
		set.add("fsdf");
		System.out.println(set.size());
		System.out.println(listToString(set,","));
		
		Hashtable<String, User> hashtable = new Hashtable<String, User>();
		hashtable.put("1", new User("lin",23,new String[]{null,null,null,null}));
		hashtable.put("2", new User("linn",234));
		hashtable.put("3", new User("linnfd",2334));
		hashtable.put("3", new User("lindfdn",23224));
		
		User value = hashtable.get("1") ;
		value.setAge(2345);
		value.getValues()[2] = "hava a value";
		System.out.println(hashtable.get("1").getAge());
		System.out.println(hashtable.get("1").getValues()[2]);
	}

	public static String listToString(Collection list,String separator){
		if(list==null)
			return null;
		return list.toString().replaceAll("[\\]\\[ ]", "").replaceAll(",", separator);
	}
}

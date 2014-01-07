package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SetSort {
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		set.add("2010��05��21��");
		set.add("2010��04��21��");
		set.add("2009��05��21��");
		set.add("2011��05��21��");
		Object[] arr = set.toArray();
		Arrays.sort(arr,new Comparator<Object>(){

			public int compare(Object obj1,Object obj2) {
				// TODO Auto-generated method stub
				if(obj1 instanceof String && obj2 instanceof String){
					try {
						if(new SimpleDateFormat("yyyy��MM��dd��").parse(obj1.toString()).getTime()<new SimpleDateFormat("yyyy��MM��dd��").parse(obj2.toString()).getTime())
							return 1;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return 0;
			}
			
		});
		
		System.out.println(arr.toString());
		
	}
}
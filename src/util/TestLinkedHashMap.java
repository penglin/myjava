package util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestLinkedHashMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		int i = 0;
		map.put(i++,"ddd"+i);
		map.put(i++,"ddd"+i);
		map.put(i++,"ddd"+i);
		map.put(i++,"ddd"+i);
		map.put(i++,"ddd"+i);
		map.put(i++,"ddd"+i);
		map.put(i++,"ddd"+i);
		map.put(i++,"ddd"+i);
		String value = map.values().iterator().next();
		System.out.println(value);
		System.out.println(map.get(Integer.valueOf(3)));
		map.put(Integer.valueOf(3), "change");
		System.out.println(map.get(Integer.valueOf(3)));
		
		Map<String,Long[]> testLongs = new HashMap<String, Long[]>();
		Long[] longs = new Long[]{0L,1L,2L};
		testLongs.put("test", longs);
		Long[] longs2 = testLongs.get("test");
		System.out.println(longs.equals(longs2));
		longs2[1] = 3L;
		System.out.println(longs[1]);
	}

}

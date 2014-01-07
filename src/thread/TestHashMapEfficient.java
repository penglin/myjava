package thread;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class TestHashMapEfficient {
	private Map<String,Long> map = new LinkedHashMap<String,Long>();
	
	
	public synchronized void put (String key ,Long value){
		if(map.containsKey(key)){
			map.put(key, map.get(key) + value);
		}else{
			map.put(key, value);
		}
	}
	
	public synchronized Long get (String key){
		return map.remove(key);
	}
	
	public synchronized Long get (String key, boolean remove){
		if(remove){
			return map.remove(key);
		}else{
			return map.get(key);
		}
		
	}
	
	public synchronized int size (){
		return map.size();
	}
	
	public synchronized Set<String> mapKeys(int size){
		Set<String> keys = new HashSet<String>();
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()&&keys.size()<size){
			keys.add(it.next());
		}
		return keys;
	}
	
	public synchronized Set<String> mapKeys(){
		Set<String> keys = new HashSet<String>();
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			keys.add(it.next());
		}
		return keys;
	}
	
	
}



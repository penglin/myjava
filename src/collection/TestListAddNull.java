package collection;

import java.util.ArrayList;
import java.util.List;

public class TestListAddNull {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
//		list.add(null);
		for(int i=0;i<458;i++){
			list.add(""+i);
		}
		int batchCount = 200;
//		System.out.println(list.toString());
		TestListAddNull obj = new TestListAddNull();
//		obj.removeList(list);
//		System.out.println(list.toString());
		List<String> tmp = list.subList(0, batchCount);
		list = list.subList(batchCount, list.size());
		System.out.println(tmp.toString());
		System.out.println(list.toString());
	}
	
	public void removeList(List<String> list){
		list.remove(1);
	}
}

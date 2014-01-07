package util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestInnerClass {
	private Map<String,Object> map = new HashMap<String, Object>();
	
	public TestInnerClass() {
		System.out.println("Constructor");
	}
	
	@Test
	public void test(){
		Inner in = new Inner();
		System.out.println(this.getClass().getName());
		for(int i=0;i<10;i++){
			map.put(""+i, new Object());
		}
		
		System.out.println(map.size());
		
		in.test();
	}
	

	class Inner{
		public void test(){
			System.out.println(map.size());
			System.out.println(this.getClass().getName());
		}
	}
	
}

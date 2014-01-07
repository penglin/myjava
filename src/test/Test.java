package test;

public class Test {
	@org.junit.Test
	public void testPrint(){
		System.out.println(0x7FF);
		System.out.println(Integer.toBinaryString(Integer.parseInt("7FF", 16)));
		System.out.println(100 & 0x7FF);
		System.out.println(100 << 20);
		
	}
	
}

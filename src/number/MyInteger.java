package number;

public class MyInteger {
	public static final Integer API_TASK_STATE_VALID = 1;
	public static void main(String[] args) {
		Integer ii = new Integer(1);
		System.out.println(ii==API_TASK_STATE_VALID.intValue());
		
		
		String str = new String("123");
		str.concat("abc");
		
		String str2 = "123";
		str2.concat("abc");
		
		StringBuffer sb = new StringBuffer();
		sb.append("123abee");
		
		System.out.println("str="+str);
		System.out.println("str2="+str2);
		System.out.println("sb="+sb);
		
		System.out.println(String.valueOf(Long.MAX_VALUE).length());
		System.out.println(String.valueOf(Long.MAX_VALUE));
		System.out.println(String.valueOf(Double.MAX_VALUE));
		
	}
}

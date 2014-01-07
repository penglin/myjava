package test;

/**  
 * @author ×Óµ¯¸ç  
 *   
 */
public class ForwardReference {

	static int first = test();
	static int second = 2;

	static int test() {
		return second;
	}

	public static void main(String[] args) {
		System.out.println("first = " + first);
	}

}

package number;

public class MyNumber {
	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(0x000000ff));
		System.out.println(9&0x000000ff);
		String str = "ÖÐ¹úÈË";
		byte[] data = str.getBytes();
		for(byte d:data){
			System.out.println(Integer.toBinaryString(d));
			System.out.println(d);
			System.out.println(d&0x000000ff);
		}
		System.out.println(0x3c);
		System.out.println((int)'A');
		System.out.println((int)'a');
		changeNumber(45, 7);
		changeNumber2(45, 7);
		
		Float f = 11f;
		Integer i = 245;
		System.out.println(f/i);
		
		String numString = "86,"+

		"89,"+

		"100,"+

		"91,"+

		"76,"+

		"90,"+

		"93,"+

		"75,"+

		"92,"+

		"101,"+

		"87,"+

		"90,"+

		"90,"+

		"90,"+

		"99,"+

		"106,"+

		"75,"+

		"89,"+

		"89,"+

		"75,"+

		"73,"+

		"80,"+

		"95,"+

		"77,"+

		"100,"+

		"93,"+

		"88,"+

		"71,"+

		"83,"+

		"90,"+

		"88,"+

		"84,"+

		"96,"+

		"73,"+

		"88,"+

		"89,"+

		"85,"+

		"95,"+

		"87,"+

		"86,"+

		"84,"+

		"93,"+

		"106,"+

		"84,"+

		"85,"+

		"78,"+

		"81,"+

		"75,"+

		"93,"+

		"74,"+

		"101,"+

		"87,"+

		"95,"+

		"68,"+

		"91,"+

		"98,"+

		"100,"+

		"79,"+

		"78,"+

		"72,"+

		"96,"+

		"90,"+

		"76,"+

		"104,"+

		"88,"+

		"82,"+

		"86,"+

		"89,"+

		"72,"+

		"91,"+

		"91,"+

		"67,"+

		"81,"+

		"76,"+

		"88,"+

		"83,"+

		"69,"+

		"91,"+

		"80,"+

		"73,"+

		"80,"+

		"100,"+

		"87,"+

		"88,"+

		"90,"+

		"91,"+

		"82,"+

		"79,"+

		"105,"+

		"74,"+

		"82,"+

		"88,"+

		"83,"+

		"80,"+

		"66,"+

		"81,"+

		"94,"+

		"58,"+

		"91,"+

		"91";
		
		String[] arr = numString.split(",");
		int value = 0;
		for(i=0;i<arr.length;i++){
			value += Integer.parseInt(arr[i]);
		}
		System.out.println(value);
	}
	
	public static void changeNumber(int m ,int n){
		System.out.println("before change: "+m+","+n);
		if(m>n){
			n=m+n;
			m=n-m;
			n=n-m;
		}
		System.out.println("after change: "+m+","+n);
	}
	
	
	public static void changeNumber2(int m ,int n){
		System.out.println("before change: "+m+","+n);
		if(m>n){
			n=m-n;
			m=m-n;
			n=n+m;
		}
		System.out.println("after change: "+m+","+n);
	}
}

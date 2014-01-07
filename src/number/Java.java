package number;

public class Java {
	public static void main(String args[]) {
		System.out.println(2.00d - 1.60d);
		double dd = 1.10d;
		String hexString = Double.toHexString(dd);
		System.out.println(hexString);
		
		
		char x = 'X';
		System.out.println((int)x);
		int i = 0;
		System.out.println(true ? x : 0);
		System.out.println(false ? i : x);
		
		
		 float f = 20014999;     
		 double d = f;     
		 double d2 = 20014999;     
		 System.out.println("f=" + f);     
		 System.out.println("d=" + d);     
		 System.out.println("d2=" + d2);  
		 
		 String binnary = "0011000101100111100101110000000000000000000000000000";
		 char[] charArr = binnary.toCharArray();
		 System.out.println(charArr.length);
		 double ddd = 0D;
		 for(i=1;i<=charArr.length;i++){
			 double pow = Math.pow(2, i);
			 double tmp = Double.parseDouble(charArr[i-1]+"") * 1/pow;
			 ddd += tmp ;
			 System.out.println(tmp);
		 }
		 System.out.println(ddd);
	}
	
	
//	10.0000011010010010101001
}

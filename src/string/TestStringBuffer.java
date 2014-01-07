package string;


public class TestStringBuffer {
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("fasdfas,fdsaf,fdsf,\n");
//		if(sb.toString().lastIndexOf("\n")>0)
//			sb.deleteCharAt(sb.toString().lastIndexOf("\n"));
		System.out.println(sb.toString());
		
		String str = "fasdf,fadsf,afdsfasd,";
		if(str.endsWith(",")){
			System.out.println(str.substring(0, str.lastIndexOf(",")-1));
		}
		
		System.out.println("0.00%".matches("[0-9]*\\.[0-9]*\\%"));
		System.out.println("0.00%".contains("."));
		
		
		System.out.println("2011年02月24日 15:03:16".compareTo("2011年02月21日 19:33:16"));
		System.out.println("2011年02月24日 15:03:16".compareTo("2011年02月24日 15:08:16"));
		
		System.out.println(3&3);
		
		System.out.println("2011年05月04日 00:00:00".substring(0, 17));;
		
		System.out.println("1307000440447e6f0ns607ky5186szed_51540");
		System.out.println(Thread.currentThread().getId()+"1307000440447e6f0ns607ky5186szed_51540".substring(1));
		
		System.out.println("192.168.1.68,192.168.1.66,192.168.1.65,192.168.1.64,192.168.1.63,192.168.1.57,192.168.1.56,192.168.1.55,192.168.1.52,192.168.1.54,192.168.1.53,192.168.1.221,192.168.1.220,192.168.1.219,192.168.1.218,192.168.1.217,192.168.1.209,192.168.1.207,192.168.1.206,192.168.1.203,192.168.1.208,192.168.1.205,192.168.1.224,192.168.1.225,192.168.1.226,192.168.1.227,192.168.1.228,192.168.1.229".split(",").length);
		
		System.out.println("1017c9a6a6c1466b9cbd6ea058f7f2c5_71124".substring(33, 38));
		
		System.out.println("/jinzhunpai/MonitorPlayWorkspace/201203151737".substring(0, "/jinzhunpai/MonitorPlayWorkspace/201203151737".lastIndexOf("/")));
		char c = 1;
		System.out.println(c);
		
		String ffff = "q###e";
		System.out.println(ffff.split("#").length);
		String[] arr = ffff.split("#");
		for(String t : arr){
			System.out.println(t);
		}
	}
}

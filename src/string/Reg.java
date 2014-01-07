package string;

import java.util.UUID;

public class Reg {
	public static void main(String[] args) {
		String str = "1.总部管理员（王娟） 2010年11月02日 16:06:33 2010年11月02日 16:06:33 导入媒体";
		System.out.println(str.replaceAll("[年[月]]", "-").replaceAll("日 \\d*:\\d*:\\d*", ""));
		System.out.println("1.总部管理员（王娟） 2010-11-02日 16:06:33 导入媒体".replaceAll("[年[月]]", "-").replaceAll("日 \\d*:\\d*:\\d*", ""));
		
		System.out.println("fsdfds\nfsdfasd".replaceAll("\\n", "\\\\n"));
		System.out.println(UUID.randomUUID());
		 String Split_Char = new String(new char[]{1});
		 String tt = "p:1304601889095qvkknkeurhfsnr7r99e i:1337786917fv6wkn3s1b49r0hcek9gb3".replaceFirst(";|,| ", Split_Char);
		System.out.println(tt.split(Split_Char).length);
		
		String visitDate = "2012年06月23日";
		if(visitDate.compareTo("2012年06月18日")>=0&&visitDate.compareTo("2012年07月27日")<=0){
			System.out.println("jfkdsj");
		}
		System.out.println((char) 1);
		char t = 1;
		char t2 = '\001';
		String t3 = new String(new char[]{1});
		System.out.println(0);
		System.out.println(t2);
	}
}

package string;

import java.util.UUID;

public class Reg {
	public static void main(String[] args) {
		String str = "1.�ܲ�����Ա�����꣩ 2010��11��02�� 16:06:33 2010��11��02�� 16:06:33 ����ý��";
		System.out.println(str.replaceAll("[��[��]]", "-").replaceAll("�� \\d*:\\d*:\\d*", ""));
		System.out.println("1.�ܲ�����Ա�����꣩ 2010-11-02�� 16:06:33 ����ý��".replaceAll("[��[��]]", "-").replaceAll("�� \\d*:\\d*:\\d*", ""));
		
		System.out.println("fsdfds\nfsdfasd".replaceAll("\\n", "\\\\n"));
		System.out.println(UUID.randomUUID());
		 String Split_Char = new String(new char[]{1});
		 String tt = "p:1304601889095qvkknkeurhfsnr7r99e i:1337786917fv6wkn3s1b49r0hcek9gb3".replaceFirst(";|,| ", Split_Char);
		System.out.println(tt.split(Split_Char).length);
		
		String visitDate = "2012��06��23��";
		if(visitDate.compareTo("2012��06��18��")>=0&&visitDate.compareTo("2012��07��27��")<=0){
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
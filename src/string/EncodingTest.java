package string;
/**
 * <p>Title: LoonFramework</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: LoonFramework</p>
 * <p>License: http://www.apache.org/licenses/LICENSE-2.0</p>
 * @author chenpeng  
 * @email��ceponline@yahoo.com.cn 
 * @version 0.1
 */
public class EncodingTest {
	public static void main(String argc[]) {
		ParseEncoding parse;

		parse = new ParseEncoding();
		
		 System.out.println("�й���½��");
		 System.out.println("�����ַ����������ʽ="+parse.getEncoding("�ٶ�".getBytes()));
		 System.out.println("����վ�㣬�����ʽ="+parse.getEncoding("http://www.baidu.com"));
		 System.out.println();
		 System.out.println("�й�̨�壺");
		 System.out.println("�����ַ����������ʽ="+parse.getEncoding("���إ���".getBytes()));
		 System.out.println("����վ�㣬�����ʽ="+parse.getEncoding("http://tw.yahoo.com/"));
		 System.out.println("����վ��(�����֣�UTF����)�������ʽ="+parse.getEncoding("http://www.javaworld.com.tw/jute"));
		 System.out.println();
		 System.out.println("�ձ���");
		 System.out.println("�����ַ����������ʽ="+parse.getEncoding("���ΙC��".getBytes()));
		 System.out.println("����վ�㣬�����ʽ="+parse.getEncoding("http://www.4gamer.net"));
		 System.out.println();
		 System.out.println("�Գ���Ⱥ����Ⱥ������");
		 System.out.println("����վ�㣬�����ʽ="+parse.getEncoding("http://www.easyjava.co.kr/"));
		
	}
}


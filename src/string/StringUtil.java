package string;

import java.io.UnsupportedEncodingException;

public class StringUtil {
	/**
	 * дһ������ String left(String str ,int n) <br>
	 * str�ַ����п��ܰ������ģ�������2bytes��ʵ�ֵĹ�����<br>
	 * �磺����abc12�� n=4 ��÷������ء���ab��<br>
	 * ����abc��a�� n=6 �򷵻ء���abc��<br>
	 * ������һ��ʱ������
	 * 
	 * @author Fee Share
	 */
	private static String getSubString(String s, int n) {
		int count = 0;
		int offset = 0;
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] > 256) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				count++;
			}
			if (count == n) {
				return s.substring(0, i + 1);
			}
			if ((count == n + 1 && offset == 2)) {
				return s.substring(0, i);
			}
		}
		return "";
	}
	
	public static void main(String[] args) {
		String str = StringUtil.getSubString("fsd��F�ǵķ���fdsfd",7);
		System.out.println("fsd��F�ǵķ���fdsfd".substring(0,4));
		System.out.println(str);
		char[] c = "fsd��F�ǵķ���fdsfd".toCharArray();
		System.out.println(c);
		str = "fsd��F�ǵķ���fdsfd";
        int length = 8;    //ȡ��ô�� �ֽ�
        try {
            byte[] bs = str.getBytes("GBK");  //����ֱ�Ӷ����bs�ֽ�����
            System.out.println(bs.length);
            String result = new String(bs, 0, length, "GBK");
            char last = result.charAt(result.length()-1);
            System.out.println((int)last);
            System.out.println(Integer.toHexString((int)last));
            if (last==0xFFFD) result = result.substring(0, result.length()-1);
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        System.out.println(Integer.MAX_VALUE);
        System.out.println((int)Character.MAX_VALUE);
        System.out.println((char)65530);
        char test = (char)65530;
        System.out.println((int)test);
        String chinese = "fsdfds";
        System.out.println(chinese.toCharArray().length);
        System.out.println(chinese.getBytes().length);
        byte[] bytes = chinese.getBytes();
        System.out.println((int)bytes[0]);
        
        char fdd = '��';
        System.out.println((int)fdd);
	}
}
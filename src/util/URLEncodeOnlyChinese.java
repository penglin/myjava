package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLEncodeOnlyChinese {
	private static String zhPattern = "[\u4e00-\u9fbf]+";

	/**
	 * �滻�ַ�����
	 * 
	 * @param str
	 *            ���滻���ַ���
	 * @param charset
	 *            �ַ���
	 * @return �滻�õ�
	 * @throws UnsupportedEncodingException
	 *             ��֧�ֵ��ַ���
	 */
	public static String encode(String str, String charset) throws UnsupportedEncodingException {
		Pattern p = Pattern.compile(zhPattern);
		Matcher m = p.matcher(str);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
		}
		m.appendTail(b);
		return b.toString();
	}

	public static void testChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((c >= 0x4e00) && (c <= 0x9fbf)) {
				System.out.println("����");
			}
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "��������������������������";
		for(int i =0;i<str.length();i++){
			System.out.println(Integer.toHexString(str.charAt(i)));
		}
		testChinese(str);
		System.out.println(encode("www.���ø�.cn", "GB2312"));
	}
}

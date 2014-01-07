package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLEncodeOnlyChinese {
	private static String zhPattern = "[\u4e00-\u9fbf]+";

	/**
	 * 替换字符串卷
	 * 
	 * @param str
	 *            被替换的字符串
	 * @param charset
	 *            字符集
	 * @return 替换好的
	 * @throws UnsupportedEncodingException
	 *             不支持的字符集
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
				System.out.println("中文");
			}
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "《》？“：】【）（……￥！";
		for(int i =0;i<str.length();i++){
			System.out.println(Integer.toHexString(str.charAt(i)));
		}
		testChinese(str);
		System.out.println(encode("www.宫颐府.cn", "GB2312"));
	}
}

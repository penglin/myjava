package url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUrl {
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String url = "http://www.zhenpin.com?klkll";
		String url = "http://www.ds.com.cn?utmso[urce]=EDM&utm_medium=1&utm_campaign=DS5";
//				Pattern p = Pattern.compile("^((https|http|ftp|rtsp|mms)?://)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?://@&=+$,%#-]+)+/?)$",Pattern.CASE_INSENSITIVE );
		Pattern p = Pattern
				.compile(
						"^((https|http)?://)?(([0-9a-zA-Z_!~*'().&=+$%-]+?: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+\\.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\.[a-zA-Z]{2,6})(:[0-9]{1,4})?(/)?((?)|([0-9a-zA-Z_!~*'().;?://@&=+$,\\[\\]\\|%#-]+)+)$",
						Pattern.CASE_INSENSITIVE);
//		url = url.substring(0,url.indexOf("?"));
		System.out.println(url);
		Matcher m = p.matcher(url);
		boolean isValidUrl = m.matches();
		System.out.println(isValidUrl);
		
		
		String domain = TestUrl.getDomainName("http://192.168.1.123");
		System.out.println(domain);
		
		System.out.println(Integer.MAX_VALUE);
		
		StringBuffer sb = new StringBuffer("fsfsdfsf");
		sb.setLength(0);
		
		System.out.println(sb);
		
		System.out.println(URLEncoder.encode("http://ad-apac.doubleclick.net/click;h=v2|3F61|0|0|%2a|q;258216106;0-0;0;82139817;31-1|1;48607489|48605272|1;;%3fhttp://www.clarins.com.cn/Œ“ «V¡≥øÿ/Shaping-facial-lift-new,zh_CN,sc.html", "GBK"));;
	}

	public static String getDomainName(String url) {
		String domainName = null;
		Matcher m = Pattern.compile("http://.+?/").matcher(url);
		if (m.find()) {
			String result = m.group();
			domainName = m.group().substring(0, result.length() - 1);
		}
		return domainName;
	}
}

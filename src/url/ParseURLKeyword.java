package url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseURLKeyword {
	public static void main(String[] args) {
		String url = "http://www.sogou.com/s?cl=3&wd=ÊÔ¼Ý301ºÍê¿Èñ&fr=vid1000";
		System.out.println(ParseURLKeyword.getKeyword(url));
		System.out.println(ParseURLKeyword.getSearchEngin(url));
		System.out.println("");
		url = "http://www.baidu.com/s?wd=%E5%85%A8%E6%96%B0%E7%88%B1%E4%B8%BD%E8%88%8D+%E6%98%95%E9%94%90++%E6%A0%87%E5%BF%97301++%E5%85%A8%E6%96%B0%E6%8D%B7%E8%BE%BE&ie=utf-8&tn=11058086_1_pg";
		System.out.println(ParseURLKeyword.getKeyword(url));
		System.out.println(ParseURLKeyword.getSearchEngin(url));
		System.out.println("");
		url = "http://www.google.com.tw/search?hl=zh-CN&q=%E6%B9%98%E9%8B%BC%E4%B8%AD%E5%9C%8B%E9%A6%99%E7%85%99&btnG=Google+%E6%90%9C%E7%B4%A2&aq=f&oq=";
		System.out.println(ParseURLKeyword.getKeyword(url));
		System.out.println(ParseURLKeyword.getSearchEngin(url));
		System.out.println("");
		url = "http://www.baidu.com/s?word=%E6%BB%B4%E6%BB%B4%E6%B4%BE&tn=sitehao123&ie=utf-8";
		System.out.println(ParseURLKeyword.getKeyword(url));
		System.out.println(ParseURLKeyword.getSearchEngin(url));
		System.out.println("");
		url = "http://www.baidu.com/s?wd=%C6%F3%D2%B5%CD%C6%B9%E3";
		System.out.println(ParseURLKeyword.getKeyword(url));
		System.out.println(ParseURLKeyword.getSearchEngin(url));
		System.out.println("");
	}

	public static String getKeyword(String url) {
		String keywordReg = "(?:yahoo.+?[\\?|&]p=|openfind.+?query=|google.+?q=|lycos.+?query=|onseek.+?keyword=|search\\.tom.+?word=|search\\.qq\\.com.+?word=|zhongsou\\.com.+?word=|search\\.msn\\.com.+?q=|yisou\\.com.+?p=|sina.+?word=|sina.+?query=|sina.+?_searchkey=|sohu.+?word=|sohu.+?key_word=|sohu.+?query=|163.+?q=|baidu.+?wd=|baidu.+?word=|soso.+?w=|3721\\.com.+?p=|Alltheweb.+?q=|115.+?q=|youdao.+?q=|sogou.+?query=|bing.+?q=|114.+?kw=)([^&]*)";
		String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$";
		Pattern keywordPatt = Pattern.compile(keywordReg);
		StringBuffer keyword = new StringBuffer(20);
		Matcher keywordMat = keywordPatt.matcher(url);
		while (keywordMat.find()) {
			keywordMat.appendReplacement(keyword, "$1");
		}
		if (!keyword.toString().equals("")) {
			String keywordsTmp = keyword.toString().replace("http://www.", "");
			Pattern encodePatt = Pattern.compile(encodeReg);
			String unescapeString = ParseURLKeyword.unescape(keywordsTmp);
			Matcher encodeMat = encodePatt.matcher(unescapeString);
			String encodeString = "gbk";
			if (encodeMat.matches())
				encodeString = "utf-8";
			try {
				return URLDecoder.decode(keywordsTmp, encodeString);
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return "";
	}
	
	public static String getSearchEngin(String url){
//		baidu.+?wd=|baidu.+?word=|soso.+?w=|3721\\.com.+?p=|Alltheweb.+?q=)([^&]*)";
		String keywordReg = "yahoo|openfind|google|lycos|onseek|search\\.tom|zhongsou|search\\.msn\\.com|yisou\\.com|sina|sohu|163|baidu|soso|3721|Alltheweb|sogou|youdao|bing|114|115";
		Pattern keywordPatt = Pattern.compile(keywordReg);
		Matcher keywordMat = keywordPatt.matcher(url);
		String searchEngin = "Î´Öª";
		if(keywordMat.find()){
			searchEngin = keywordMat.group();
		}
		return searchEngin;
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
}
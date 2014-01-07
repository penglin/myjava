package url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class TestUrlEncode {
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String referUrl = "http://www.baidu.com/s?wd=%E9%9A%8F%E8%A7%86%E4%BC%A0%E5%AA%92&tn=ichuner_4_pg&ie=utf-8";
		String referUrl = "http://www.ds.com.cn?utm_source=EDM&utm_medium=1&utm_campaign=DS5";
		String[] valueArr =  referUrl.substring(referUrl.indexOf("?")+1).split("[&]");
		String encode = getValue(valueArr,new String[]{"ie"},"UTF-8");
		encode = encode==null||encode.trim().length()==0?"GBK":encode;
		String tt = URLDecoder.decode(referUrl, encode).toString();
		System.out.println(tt);
		System.out.println(encode);
		String dd = getValue(valueArr,new String[]{"wd"},encode);
		System.out.println(dd);
	}
	
	public static String getValue(String[] valueArr,String[] keys,String encode){
		String result = "";
		try {
			for(String tmp : valueArr){
				boolean isFind = false;
				for(String k : keys){
					String keyTmp = k+"=";
					if(tmp.trim().startsWith(keyTmp) && tmp.trim().length()>keyTmp.length()){
						String value = tmp.trim().substring(keyTmp.length());
						result = URLDecoder.decode(value, encode);
						isFind = true;
						break ;
					}
				}
				if(isFind){
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}

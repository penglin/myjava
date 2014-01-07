package string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlTest {
	public static void main(String[] args) {
		String url = "^((https|http)?://)"    
            + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" //ftp的user@   
            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184   
            + "|" // 允许IP和DOMAIN（域名）   
            + "([0-9a-zA-Z_!~*'()-]+\\.)*" // 域名- www.   
            + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\." // 二级域名   
            + "[a-zA-Z]{2,6})" // first level domain- .com or .museum   
            + "(:[0-9]{1,4})?" // 端口- :80   
            + "((/?)|"    
            + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";   
//		Pattern p = Pattern.compile("^((https|http)?://)?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+\\.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\.[a-zA-Z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$");
		Pattern p = Pattern.compile(url);
		Matcher m = p.matcher("http://keywords");
		System.out.println(m.matches());
		
	//"^((https|http)?://)?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+\\.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\.[a-zA-Z]{2,6})"(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";   
		
//		"数据库中已存在名为 'user_campaign_2902' 的对象";
		System.out.println("数据库中已存在名为 'user_campaign_2902' 的对象。".indexOf("数据库中已存在名为 'user_campaign_2902' 的对象"));
		Integer i = 1;
		Long t = 9L;
		if(t instanceof Number){
			System.out.println("空也可以");
		}
	}
}

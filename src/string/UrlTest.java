package string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlTest {
	public static void main(String[] args) {
		String url = "^((https|http)?://)"    
            + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" //ftp��user@   
            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP��ʽ��URL- 199.194.52.184   
            + "|" // ����IP��DOMAIN��������   
            + "([0-9a-zA-Z_!~*'()-]+\\.)*" // ����- www.   
            + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\." // ��������   
            + "[a-zA-Z]{2,6})" // first level domain- .com or .museum   
            + "(:[0-9]{1,4})?" // �˿�- :80   
            + "((/?)|"    
            + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";   
//		Pattern p = Pattern.compile("^((https|http)?://)?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+\\.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\.[a-zA-Z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$");
		Pattern p = Pattern.compile(url);
		Matcher m = p.matcher("http://keywords");
		System.out.println(m.matches());
		
	//"^((https|http)?://)?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+\\.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\.[a-zA-Z]{2,6})"(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";   
		
//		"���ݿ����Ѵ�����Ϊ 'user_campaign_2902' �Ķ���";
		System.out.println("���ݿ����Ѵ�����Ϊ 'user_campaign_2902' �Ķ���".indexOf("���ݿ����Ѵ�����Ϊ 'user_campaign_2902' �Ķ���"));
		Integer i = 1;
		Long t = 9L;
		if(t instanceof Number){
			System.out.println("��Ҳ����");
		}
	}
}
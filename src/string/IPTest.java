package string;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPTest {
	public static void main(String[] args) throws UnknownHostException {
		String dbNameSuffix = "panel_user_campaign_";
		
		String tableNameSuffix = "user_campaign_";
		InetAddress inet = InetAddress.getLocalHost();
		System.out.println("±¾»úµÄip=" + inet.getHostAddress());

		String dd = "2011-04-21|1;2011-04-29|1;;";

		System.out.println(dd.split(";").length);

		String uid = "fsdfjsdlkjflsdjfl09w3209jflsdj";

		String suffix = uid.substring(0, 5);
		
		int hasCode = Math.abs(suffix.hashCode());
		String dbName = dbNameSuffix + "00".substring(0,2 - ("" + hasCode % 100).length()) + hasCode % 100;
		hasCode = Math.abs(uid.hashCode());
		String tableName = tableNameSuffix + "0000".substring(0,4 - ("" + hasCode % 10000).length()) + hasCode % 10000;
		String key = "[" + dbName + "].[dbo].[" + tableName + "]";
		System.out.println(key);
	}
}

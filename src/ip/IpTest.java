package ip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import cn.adsit.common.ip.Ip;
import cn.adsit.common.ip.IpContainer;

public class IpTest {
	public static void main(String[] args) throws Exception {
		IpContainer ipContainer = new IpContainer();
		ipContainer.initialize("Ip.txt", "", 1);
		
//		String ips = "221.208.185.91,113.1.155.182,221.208.185.91,1.57.202.73,221.208.185.91,113.6.12.72,1.188.58.239,113.3.146.139,112.98.196.226,1.60.85.201,123.164.216.194,113.1.155.182,113.8.80.9,113.8.80.9,1.57.202.73,113.8.80.9,113.8.80.9,113.8.80.9,1.57.202.73,1.57.202.73";
//		String[] tmps = ips.split("\\,");
//		for(String tmp : tmps){
//			Ip ip = ipContainer.getIp(tmp);
//			System.out.println(ip.getProvinceCityName());
//			System.out.println(ip.getProvinceName());
//		}
		OutputStream out = new FileOutputStream("ip_check2.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ip_check.txt")));
		String line = null;
		while((line=br.readLine())!=null){
			String ipStr = line.trim();
			Ip ip = ipContainer.getIp(ipStr);
			String city = ip.getProvinceCityName();
			String province = ip.getProvinceName();
			System.out.println(ip.getProvinceCityName());
			System.out.println(ip.getProvinceName());
			out.write((ipStr+"-->"+province+"-->"+city+"\n").getBytes());
		}
		out.flush();
		out.close();
		br.close();
	}
}

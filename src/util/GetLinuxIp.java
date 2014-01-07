package util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetLinuxIp {
	public static void getIp() throws SocketException {
		String ip = "";
		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				if (!"eth0".equalsIgnoreCase(ni.getName())) {
					continue;
				}
				System.out.println("DisplayName:" + ni.getDisplayName());
				System.out.println("Name:" + ni.getName());
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					System.out.println("IP:" + ips.nextElement().getHostAddress());
					ip = ips.nextElement().getHostAddress();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
//		getIp();
		System.out.println(getLocalSettleIp());
		System.out.println(getLinuxIp());
	}

	/**
	 * 得到本机安置IP
	 * 
	 * @return 本机安置IP
	 * @throws Exception
	 *             处理异常
	 */
	public static String getLocalSettleIp() throws Exception {
		// 变量定义
		Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
		String ipAddr = "";
		// 得到所有IP地址
		while (enumeration.hasMoreElements()) {
			NetworkInterface ni = enumeration.nextElement();
			Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
			while (inetAddresses.hasMoreElements()) {
				ipAddr = inetAddresses.nextElement().toString();
				ipAddr = ipAddr.replaceAll("/", "");
				break;
			}
		}
		return ipAddr;
	}

	public static String getLinuxIp() throws SocketException {
		// 根据网卡取本机配置的IP
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			ip = (InetAddress) ni.getInetAddresses().nextElement();
			if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
				break;
			} else {
				ip = null;
			}
		}
		return ip==null?null:ip.getHostAddress();
	}
}

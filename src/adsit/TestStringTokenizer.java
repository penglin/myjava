package adsit;

import java.util.StringTokenizer;

public class TestStringTokenizer {
	/**
	 * ��ip���ַ�����ʽ�õ��ֽ�������ʽ
	 * 
	 * @param ip
	 *            �ַ�����ʽ��ip
	 * @return �ֽ�������ʽ��ip
	 */
	public static byte[] getIpByteArrayFromString(String ip) {
		byte[] ret = new byte[4];
		java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
		try {
			ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}

	/**
	 * @param ip
	 *            ip���ֽ�������ʽ
	 * @return �ַ�����ʽ��ip
	 */
	public static String getIpStringFromBytes(byte[] ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		return sb.toString();
	}
	
	public static void main(String args[]) {
		byte[] a = getIpByteArrayFromString("174.98.56.78");
		for (int i = 0; i < a.length; i++)
			System.out.println(a[i]);
		StringTokenizer token = new StringTokenizer("174.98.56.78",".",true);
		while(token.hasMoreTokens()){
			System.out.println(token.nextToken());
		}
		System.out.println((byte)(Integer.parseInt("174")&0xff));
		System.out.println(0xFF);
	}
}
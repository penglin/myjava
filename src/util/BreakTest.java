package util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BreakTest {
	{
		add();
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
//		for(int i=0;i<10;i++){
//			System.out.println(i);
//			for(int j=0;j<10;j++){
//				if(j==3){
//					System.out.println(j);
//					break;
//				}
//			}
//		}
		//String url = "http://search.360buy.com/search?keyword=�ƴ�&cid=1610";
//		String url = "�ƴ�";
//		System.out.println(url);
//		url = URLEncoder.encode(url,"GB2312");
//		//url = URLDecoder.decode(url);
//		System.out.println(url);
		
		//encode("�ƴ�","GB2312");
		//encode("善存","ISO-8859-1");
		
		//p();
		
	    String str = "http://search.360buy.com/search?keyword=�ƴ�&cid=1610";
	    System.out.println(str);
	    System.out.println(encode(str, "GB2312"));		
	}
	
	private static void encode1(String src,String code) throws UnsupportedEncodingException{
		System.out.println(src);
		System.out.println(URLEncoder.encode(src,code));		
	}
	
	static void p(){
		String str1 = "�ַ���aa";

		for (int i = 0; i < str1.length(); i++) {

			String test = str1.substring(i, i + 1);

			//System.out.println(test);

			if (test.matches("[\\u4E00-\\u9FA5]+")) {
				System.out.println("����");
			}
		}		
	}

	public void add() {

	}
	
	/**
	* �����滻�ַ�������ĺ��ֲ��֡�
	 * 
	 * @author ��ѧ��  www.java2000.net
	 */
	//public class URLEncoderHZ {
//	  public static void main(String[] args) throws Exception {
//	    String str = " http://192.168.1.1:8080/resources/�绰.xls";
//	    System.out.println(encode(str, "UTF-8"));
//	  }

	  private static String zhPattern = "[\u4e00-\u9fa5]+";

	  /**
	   * �滻�ַ�����
	   * 
	   * @param str ���滻���ַ���
	   * @param charset �ַ���
	   * @return �滻�õ�
	   * @throws UnsupportedEncodingException ��֧�ֵ��ַ���
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
	//}	

}

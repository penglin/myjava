package httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class TestHttpClent {

	public static void main(String[] args) throws IOException {
		HttpClient client = new HttpClient();
		// ���ô�����������ַ�Ͷ˿�
		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		for(int i =0 ;i<100; i++){
			// ʹ�� GET ���� �������������Ҫͨ�� HTTPS ���ӣ���ֻ��Ҫ������ URL �е� http ���� https
			HttpMethod method = new GetMethod("http://192.168.1.44:8080/preroll/pFAW.asx?requestAddress=http://www.91adv.com/&videoType=flv&workStationId=91adv*adPosId&lockedUserIp=9.9.9.9&displayMessage=1");
			// ʹ��POST����
			// HttpMethod method = new PostMethod("http://java.sun.com");
			client.executeMethod(method);
			
			// ��ӡ���������ص�״̬
			System.out.println(method.getStatusLine());
			// ��ӡ���ص���Ϣ
			System.out.println(method.getResponseBodyAsString());
			// �ͷ�����
			method.releaseConnection();
		}
	}

	/**
	 * ʹ�� GET ��ʽ�ύ����
	 * 
	 * @return
	 */

	private static HttpMethod getGetMethod() {
		return new GetMethod("/simcard.php?simcard=1330227");
	}

}
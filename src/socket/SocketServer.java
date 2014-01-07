package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class SocketServer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// ����socket���������󣬿�ʼ�����˿�
		ServerSocket srvSock = new ServerSocket(22222);
		// ��ʼ�����˳���Ϣ
		srvSock.setSoTimeout(2 * 60 * 60 * 1000);
		while (true)
		{
			// ����Timeoutʱ��Ϊ��Сʱ
			srvSock.setSoTimeout(2 * 60 * 60 * 1000);
			Socket commSock = null;
			try
			{
				// ���һ����������
				commSock = srvSock.accept();
			}
			catch (SocketTimeoutException sx)
			{
				continue;
			}
			// ���ý�����ʱʱ��Ϊ10��
			commSock.setSoTimeout(10 * 1000);
			// ��ȡͨѶ�ַ���
			BufferedReader reader = new BufferedReader(new InputStreamReader(commSock.getInputStream()));
			String command;
			// ���˿��ַ������
			while ((command = reader.readLine()) == null)
				continue;
			// �ж��Ƿ�Ϊ�˳�����
			if (command.toLowerCase().equals("shutdown"))
			{
				commSock.close();
				commSock = null;
				srvSock.close();
				srvSock = null;
				System.out.println("Socket Server �˳�");
				break;
			}
			// �Ƿ����ӣ��رո�����
			commSock.close();
			commSock = null;
		}
	}

}
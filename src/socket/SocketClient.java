package socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient {
	public static void main(String[] args) throws Exception, IOException {
		Socket commSock = new Socket("127.0.0.1", 22222);
		// �õ������
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(commSock.getOutputStream()));
		// �����˳�����
		writer.write("shutdown");
		writer.flush();
		commSock.close();
	}
}